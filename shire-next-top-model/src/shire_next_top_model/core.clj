(ns shire-next-top-model.core
  (:gen-class))

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6} 
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn matching-part
  "Return a map with a matching name and size."
  [part]
  ; The name will swap 'left-' for 'right-' in the parameter part, if applicable.
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})


(defn symmetrize-body-parts
  "Expects a sequence of maps that have a :name and :size."
  [asym-body-parts]
  (loop [remaining-asym-parts asym-body-parts ; arg1 of loop
         final-body-parts []]                 ; arg2 of loop
    (if (empty? remaining-asym-parts)
      final-body-parts ; we're done, return the new map.
      (let [[part & remaining] remaining-asym-parts]  ; otherwise...
                                        ; first element of remaining-asym-parts goes into 'part'
                                        ; other elements go into remaining
        (recur remaining ; arg1 of loop recursive call
               (into final-body-parts ; arg2 of loop recursive call - put into the final-body-parts vector...
                     (set [part (matching-part part)]))))))) ; the set made from the part and its matching part
  

(defn better-symmetrize-body-parts
  "Expects a sequence of maps that have a :name and :size."
  [asym-body-parts]
  (reduce (fn 
            [final-body-parts part] ; args for anonymous function: the list of accumulated parts, and the next part to process.
            (into final-body-parts (set [part (matching-part part)])))
          [] ; Initial value for 'reduce'.
          asym-body-parts)) ; List to reduce.



(defn hit
  "Hit a random body part in the parameter sequence of maps, each of which is expected
   to have a :name and a :size."
  [asym-body-parts]
  (let [sym-parts (better-symmetrize-body-parts asym-body-parts) ; get the full list of parts, including symmetrical ones
        body-part-size-sum (reduce + (map :size sym-parts)) ; get the list of sizes of the full list and add them
        target (inc (rand body-part-size-sum))] ; generate a random number from 1 to the (total sum-1)
    (loop [[part & remaining] sym-parts   ; get the head of the sym-parts, store the remaining ones.
           accumulated-size (:size part)] ; get the head part's size.
      (if (> accumulated-size target) 
        part
        (recur remaining (+ accumulated-size (:size (first remaining))))))))

