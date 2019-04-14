
(def pts [[1 2] [3 4] [5 6]])

(doseq [pt pts]
  (println pt))

(def pts (build_bh [[1 1]] 10 0.2 0.3))

(def pts (build_bh [[1 1]] 1000 -0.9 0.86))

(q/ellipse 100 100 100 100)


( doseq [pt pts]
     (map * pt 10)
     (apply #(println %1 %2 %3 %4) (conj pt 100 100)))

(doseq [pt pts]
  (println
   (map * pt (repeat (count pt) 10))))

(flatten (take 5 pts))


(apply max (flatten
            (let [xs []
                  pts (take 5 pts)]
             (for [pt pts]
               (conj xs (get pt 1))))))


;;;ADD SECOND PARAMETER FOR WHICH COLUMN TO MAX
(defn maxcol
    [pts]
    (apply max (flatten
                (let [xs []]
                  (for [pt pts]
                    (conj xs (get pt 1)))))))
