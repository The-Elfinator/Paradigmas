; :NOTE: можно одну общую функцию на три типа
(defn coordVecOp [f] (fn [v1 v2] (
    if (not-empty v1)
      (vec (cons
          (f (first v1) (first v2))
          ((coordVecOp f) (rest v1) (rest v2))
        )
      )
      []
    )
  )
)

(defn coordMatOp [f] (fn [m1 m2] (
  if (not-empty m1)
    (vec (cons
        ((coordVecOp f) (first m1) (first m2))
        ((coordMatOp f) (rest m1) (rest m2))
      )
    )
    []
  ))
)

(defn shapelessFunc [f] (fn [s1 s2] (
    if (number? s1) (f s1 s2) (
      if (not-empty s1)
      (vec (cons
          ((shapelessFunc f) (first s1) (first s2))
          ((shapelessFunc f) (rest s1) (rest s2))
        )
      ) []
    )
  ))
)

(def v+ (coordVecOp +))
(def v- (coordVecOp -))
(def v* (coordVecOp *))
(def vd (coordVecOp /))

(def m+ (coordMatOp +))
(def m- (coordMatOp -))
(def m* (coordMatOp *))
(def md (coordMatOp /))

(def s+ (shapelessFunc +))
(def s- (shapelessFunc -))
(def s* (shapelessFunc *))
(def sd (shapelessFunc /))

(defn sumCoord [v] (
    if (not-empty v)
    (+ (first v) (sumCoord (rest v)))
    0
  )
)

(defn v*s [v s] (vec (for [x v] (* x s))))
(defn scalar [v1 v2] (sumCoord (v* v1 v2)))

(defn vect [v1 v2] (vector
      (- (* (nth v1 1) (nth v2 2)) (* (nth v1 2) (nth v2 1)))
      (- (* (nth v1 2) (nth v2 0)) (* (nth v1 0) (nth v2 2))) 
      (- (* (nth v1 0) (nth v2 1)) (* (nth v1 1) (nth v2 0)))
  )
)

(defn m*s [m s] (vec (for [x m] (vec (for [y x] (* y s))))))

(defn m*v [m v] (vec (for [x m] (scalar x v))))

(defn transpose [m] (vec (apply map vector m)))

(defn m*m [m1 m2] (transpose (vec (for [y (transpose m2)] (vec (for [x m1] (scalar x y)))))))

