((fn [x] (+ x x)) 10)
(println (#(+ % %) 10))
(println (#(+ %1 %2) 10 20))
(defn rec-fib [n]
      (cond
        (== 0 n) 1
        (== 1 n) 1
        :else (+ (rec-fib (- n 1)) (rec-fib (- n 2)))
        )
      )
(println (rec-fib 20))
(def mem-fib
  (memoize
    (fn [n]
        (cond
          (== 0 n) 1
          (== 1 n) 1
          :esle (+ (mem-fib (- n 1)) (mem-fib (- n 2)))
          )
      )
    )
  )
(println (mem-fib 90))
(def vect [1 2])
(println (count vect))
(conj vect 3 4)
(println (count vect))
(println (peek vect))
(println (pop vect))
(assoc vect 0 10)
(println (peek vect))
