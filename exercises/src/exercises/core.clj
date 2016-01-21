(ns exercises.core
  (:gen-class))

(def victor (vector "direction" "magnitude"))

(def to-do-list (list "Get home" "Cook dinner" "Check email" "Go to bed"))

(def victor-as-list (list victor))

(def to-do-as-vector (vector to-do-list))

(def locations (hash-map :hometown "Pallet Town, Kanto" 
                         :residence "Indigo Plateau"
                         :current nil))

(def types-of-pokeballs (hash-set "Poke Ball" "Great Ball" "Great Ball" "Ultra Ball" "Master Ball"))

(defn hello
  "Says hello to you."
  ([]
   (println "Hello there!"))

  ([name]
   (println (str "Hello, " name "!"))))

(defn inc100
  "Increments the parameter by 100."
  [number]
  (+ 100 number))
