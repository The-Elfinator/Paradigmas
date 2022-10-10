;FunctionalExpressions

(defn constant [myConst] (constantly myConst))
(defn variable [myVar] (fn [args] (get args myVar)))

(defn abstrBinFunc [f] (fn [expr1 expr2] (fn [args] (f (expr1 args) (expr2 args)))))
(defn abstrUnFunc [f] (fn [expr] (fn [args] (f (expr args)))))

(defn myPow [a b] (Math/pow a b))
(defn myLog [a b] (/ (Math/log (Math/abs b)) (Math/log (Math/abs a))))

(def add (abstrBinFunc +))
(def subtract (abstrBinFunc -))
(def multiply (abstrBinFunc *))
(defn divide [expr1 expr2] (fn [args] (/ (double (expr1 args)) (double (expr2 args)))))
(def pow (abstrBinFunc myPow))
(def log (abstrBinFunc myLog))

(def negate (abstrUnFunc -))

(def myMap {
            '+ add
            '- subtract
            '* multiply
            '/ divide
            'negate negate
            'pow pow
            'log log
})

(defn myParse [myExp] (
        cond
          (list? myExp) (apply (get myMap (first myExp)) (mapv myParse (rest myExp)))
          (number? myExp) (constant myExp)
          :else (variable (str myExp)) 
    )
)

(defn parseFunction [line] (myParse (read-string line)))

;ObjectExpressions

(load-file "proto.clj")
(load-file "parser.clj")

(def _expr1 (field :expr1))
(def _expr2 (field :expr2))
(def _expr (field :expr))
(def _result (method :result))
(def _evaluate (method :evaluate))
(def _tag (field :tag))
(def _toString (method :toString))
(def _toStringSuffix (method :toStringSuffix))
(def _diff (method :diff))
(def _differ (method :differ))

(defn Constant [c] 
  {
    :evaluate (fn [this vars] c)
    :toString (fn [this] (str c))
    :toStringSuffix (fn [this] (str c))
    :diff (fn [this variable] (Constant 0))
  })

(defn Variable [v]
  {
    :evaluate (fn [this vars] (get vars (str (Character/toLowerCase (get (str v) 0)))))
    :toString (fn [this] (str v))
    :toStringSuffix (fn [this] (str v))
    :diff (fn [this variable] (
        cond
          (= variable (str (Character/toLowerCase (get (str v) 0)))) (Constant 1)
          :else (Constant 0)
      ))
  }
  )

(def AbstrBinOp
  {
    :evaluate (fn [this vars] (_result this (_evaluate (_expr1 this) vars) (_evaluate (_expr2 this) vars)))
    :toString (fn [this] (str "(" (_tag this) " " (_toString (_expr1 this)) " " (_toString (_expr2 this)) ")"))
    :toStringSuffix (fn [this] (str "(" (_toStringSuffix (_expr1 this)) " " (_toStringSuffix (_expr2 this)) " " (_tag this) ")"))
    :diff (fn [this variable] (_differ this (_expr1 this) (_expr2 this) (_diff (_expr1 this) variable) (_diff (_expr2 this) variable)))
   }
  )

(defn Add [expr1 expr2]
  {
    :prototype AbstrBinOp
    :expr1 expr1
    :expr2 expr2
    :tag '+
    :result (fn [this a b] (+ a b))
    :differ (fn [this ex1 ex2 diffEx1 diffEx2] (Add diffEx1 diffEx2))
   }
  )

(defn Subtract [expr1 expr2]
  { 
    :prototype AbstrBinOp
    :expr1 expr1
    :expr2 expr2
    :tag '-
    :result (fn [this a b] (- a b))
    :differ (fn [this ex1 ex2 diffEx1 diffEx2] (Subtract diffEx1 diffEx2))
  })

(defn Multiply [expr1 expr2]
  {
    :prototype AbstrBinOp
    :expr1 expr1
    :expr2 expr2
    :tag '*
    :result (fn [this a b] (* a b))
    :differ (fn [this ex1 ex2 diffEx1 diffEx2] (Add (Multiply diffEx1 ex2) (Multiply ex1 diffEx2)))
   })

(defn Divide [expr1 expr2]
  {
    :prototype AbstrBinOp
    :expr1 expr1
    :expr2 expr2
    :tag '/
    :result (fn [this a b] (/ (double a) (double b)))
    :differ (fn [this ex1 ex2 diffEx1 diffEx2] (Divide (Subtract (Multiply diffEx1 ex2) (Multiply ex1 diffEx2)) (Multiply ex2 ex2)))
   })

(defn Log [expr1 expr2]
  {
    :prototype AbstrBinOp
    :expr1 expr1
    :expr2 expr2
    :tag 'log
    :result (fn [this a b] (/ (Math/log (Math/abs b)) (Math/log (Math/abs a))))
    :differ (fn [this ex1 ex2 diffEx1 diffEx2] (Divide (Subtract (Divide (Multiply diffEx2 (Log (Constant Math/E) ex1)) ex2) (Divide (Multiply diffEx1 (Log (Constant Math/E) ex2)) ex1)) (Multiply (Log (Constant Math/E) ex1) (Log (Constant Math/E) ex1))))
  })

(defn Pow [expr1 expr2]
  {
    :prototype AbstrBinOp
    :expr1 expr1
    :expr2 expr2
    :tag 'pow
    :result (fn [this a b] (Math/pow a b))
    :diff (fn [this variable] (Multiply (Pow (_expr1 this) (_expr2 this)) (_diff (Multiply (_expr2 this) (Log (Constant Math/E) (_expr1 this))) variable)))
  })

(defn BitAnd [expr1 expr2]
  {
    :prototype AbstrBinOp
    :expr1 expr1
    :expr2 expr2
    :tag '&
    :result (fn [this a b] (Double/longBitsToDouble (bit-and (Double/doubleToLongBits a) (Double/doubleToLongBits b))))
  })

(defn BitOr [expr1 expr2]
  {
    :prototype AbstrBinOp
    :expr1 expr1
    :expr2 expr2
    :tag '|
    :result (fn [this a b] (Double/longBitsToDouble (bit-or (Double/doubleToLongBits a) (Double/doubleToLongBits b))))
  })

(defn BitXor [expr1 expr2]
  {
    :prototype AbstrBinOp
    :expr1 expr1
    :expr2 expr2
    :tag (symbol "^")
    :result (fn [this a b] (Double/longBitsToDouble (bit-xor (Double/doubleToLongBits a) (Double/doubleToLongBits b))))
  })

(defn Negate [expr]
  {
    :expr expr
    :tag 'negate
    :evaluate (fn [this vars] (- (_evaluate (_expr this) vars)))
    :toString (fn [this] (str "(" (_tag this) " " (_toString (_expr this)) ")"))
    :toStringSuffix (fn [this] (str "(" (_toStringSuffix (_expr this)) " " (_tag this) ")"))
    :diff (fn [this variable] (Negate (_diff (_expr this) variable)))
   })

(defn evaluate [expression vars] (_evaluate expression vars))
(defn toString [expression] (_toString expression))
(defn toStringSuffix [expression] (_toStringSuffix expression))
(defn diff [expression variable] (_diff expression variable))

(def myMapObj
  {
    '+ Add
    '- Subtract
    '* Multiply
    '/ Divide
    'negate Negate
    'pow Pow
    'log Log
    '& BitAnd
    '| BitOr
    (symbol "^") BitXor
  })

(defn myParseObj [myExp]
  (cond 
      (list? myExp) (apply (get myMapObj (first myExp)) (mapv myParseObj (rest myExp)))
      (number? myExp) (Constant myExp)
      :else (Variable (str myExp))
   )
  )

(defn parseObject [line] (myParseObj (read-string line)))



(defn parseExp [r]
   (cond
    (vector? r) (cond
      (nil? (second r)) ((get myMapObj (symbol (str (last r)))) (parseExp (first r)))
      :else ((get myMapObj (symbol (str (last r)))) (parseExp (first r)) (parseExp (second r)))
    )
    :else r
    )
)

(def *digit (+char "1234567890"))
(def *number (+map #(Constant (read-string %)) (+seqf #(str %1 (apply str %2) %3 (apply str %4)) (+opt (+char "-")) (+plus *digit) (+opt (+char ".")) (+star *digit))))

(def *variable (+map Variable (+str (+plus (+char "xyzXYZ")))))

(def *space (+char " \t\r\n"))
(def *ws (+ignore (+star *space)))

(def *negate (+seqf str (+char "n") (+char "e") (+char "g") (+char "a") (+char "t") (+char "e")))

(def *operations (+or (+char "+-*/&|^") *negate))

(def *expr (+map parseExp
  (+or *variable *number
    (+seqn 1 
      (+char "(")
        (+seq
          *ws
          (delay (+or *variable *number *expr))
          *ws
          (+opt (+map #(get % 0) (+seq (delay (+or *variable *number *expr)) *ws)))
          *operations
          *ws
        )
      (+char ")")
    )
  ))
)

(defn myParseObjSuf [myExp]
  (cond
    (list? myExp) (apply (get myMapObj (last (vec myExp))) (mapv myParseObjSuf (reverse (rest (reverse (vec myExp))))))
    (number? myExp) (Constant myExp)
    :else (Variable (str (Character/toLowerCase (get (str myExp) 0))))
  ))


(def parseObjectSuffix (+parser (+seqn 0 *ws *expr *ws)))


