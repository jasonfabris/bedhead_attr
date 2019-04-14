(ns bedhead_attr.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(defn bh
  "GENERATES A BEDHEAD ATTRACTOR PT"
  [[x y] alpha beta]
 (let [xn (+ (* y (q/sin (/ (* x y) beta)))
           (q/cos (- (* alpha x) y)))
       yn (+ x (/ (q/sin y) beta))]
      [xn yn]))

(defn build_bh
  "Builds a list of points to plot"
  [[[x y]] num_pts alpha beta]
  ;add a recur version that doesn't pass the alpha beta over and over
  (loop [pts [[x y]] cnt num_pts]
  ;(into [[x y]] (bh (last [[x y]]) alpha beta))
   (if (= cnt 0)
     (do
       ;(print "done")
       pts)
     (do ;(print cnt)
       (recur (conj pts (bh (last pts) alpha beta)) (dec cnt))))))
; 0.27 -0.89
;50000 0.67 -0.3 or -0.39
(def pts (build_bh [[1 1]] 25000 0.67 -0.39))

(defn setup []
  ; Set frame rate to 30 frames per second.
  (q/frame-rate 30)
  ; Set color mode to HSB (HSV) instead of default RGB.
  (q/color-mode :hsb)
  ; setup function returns initial state. It contains
  ; circle color and position.
  {:color 0
   :angle 0})

(defn update-state [state]
  ; Update sketch state by changing circle color and position.
  {:color (mod (+ (:color state) 0.0) 255)
   :angle (+ (:angle state) 0.1)})

(defn draw-state [state]
  ; Clear the sketch by filling it with light-grey color.
  (q/background 240)
  ; Set circle color.
  (q/stroke (:color state) 255 255 10)
  (q/fill nil)
  ; Calculate x and y coordinates of the circle.
  (let [angle (:angle state)
        x (* 150 (q/cos angle))
        y (* 150 (q/sin angle))]
    ; Move origin point to the center of the sketch.
    (q/with-translation [(/ (q/width) 2)
                         (/ (q/height) 2)]
      ; Draw the circle.
      ;(q/ellipse x y 100 100)

     ;( doseq [pt pts]
    ;      (apply #(q/ellipse %1 %2 %3 %4) (conj pt 100 100))
     ( doseq [pt pts]
            (let [mp (map * pt (repeat (count pt) 50))]
              (apply #(q/point %1 %2) mp))))))
     ;(q/rect x y 100 100)
     ; (doseq [rng1 (range(* x 0.5) (* x 1.25) 0.25)
     ;         rng2 (range(* x 0.5) (* x 1.25) 0.25)]
     ;   (let [w 200
     ;         h 200]
     ;      (q/ellipse rng1 rng2 w h))))))



                     ;(let [rng (range 10 300 5)]
     ;(q/stroke 0 100 200)
     ;(q/fill nil)
                            ;(map #(print %1 %2) rng rng)))
     ;(map #(q/ellipse ((/ (q/width) 2) (/ (q/height) 2) %1 %2)) rng rng))

(q/defsketch bedhead_attr
  :title "Attractor"
  :size [1000 1000]
  ; setup function called only once, during sketch initialization.
  :setup setup
  ; update-state is called on each iteration before draw-state.
  :update update-state
  :draw draw-state
  :features [:keep-on-top]
  :middleware [m/fun-mode])
