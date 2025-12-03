(ns app.main.ui.workspace.shapes.local-components.foreign-object-css
  (:require
   [clojure.set :as set]
   [app.main.ui.workspace.shapes.local-components.component-css :refer [find-box-shadow]]))

(defn- change-not-zero-true
  "将所有非0值转化为true"
  [v]
  (if (boolean? v)
    v
    (let [val (js/parseInt v)]
      (if (js/isNaN val)
        true
        (not= val 0)))))

(defn foreign-object-css
  [{:keys [shape property viewer? external-variable-cache]}]
  (let [is-viewer? (boolean viewer?)
        has-dynamic-data? (and is-viewer? external-variable-cache (not-empty external-variable-cache))
        variable-cache external-variable-cache

        get-cached-var (fn [attr fallback]
                         (if has-dynamic-data?
                           (get variable-cache attr fallback)
                           fallback))

        get-boolean-result (fn [attr]
                             (if (and has-dynamic-data? (contains? variable-cache attr))
                               (change-not-zero-true (get variable-cache attr))
                               (= :true (attr property))))

        shape-rotation (:rotation shape)
        shape-rx (or (:rx shape) 0)
        shape-ry (or (:ry shape) 0)
        shape-flip-x (:flip-x shape)
        shape-flip-y (:flip-y shape)

        property-animation (:animation property)
        property-x-position (:x-position property)
        property-y-position (:y-position property)

        animation-move (get property-animation :move)
        animation-move-open (:open animation-move)
        animation-move-x (:move-position-x animation-move)
        animation-move-y (:move-position-y animation-move)

        visible (get-boolean-result :visible)
        operation (get-boolean-result :operation)
        rotation (get-cached-var :rotation shape-rotation)

        border-radius (str shape-rx "px " shape-ry "px")

        cached-shadow-var (when has-dynamic-data? (get variable-cache :shadow))
        shadow-transform-fn (fn [shadow]
                              (set/rename-keys shadow
                                               {"ShadowType"   :style
                                                "X"            :offset-x
                                                "Y"            :offset-y
                                                "Fuzzy"        :blur
                                                "Expand"       :spread
                                                "Color"        :color
                                                "Transparency" :opacity}))
        shadow (when (and is-viewer? cached-shadow-var)
                 (shadow-transform-fn cached-shadow-var))

        move? (get-cached-var :animation-move-switch animation-move-open)
        move-x (get-cached-var :animation-move-position-x animation-move-x)
        move-y (get-cached-var :animation-move-position-y animation-move-y)

        x-position (when is-viewer?
                     (if move?
                       move-x
                       (get-cached-var :x-position property-x-position)))
        y-position (when is-viewer?
                     (if move?
                       move-y
                       (get-cached-var :y-position property-y-position)))

        flip-transform (str "scale(" (if shape-flip-x -1 1) "," (if shape-flip-y -1 1) ")")

        has-transform? (or x-position y-position rotation)
        transform-str (when has-transform?
                        (str "translate("
                             (if x-position (str x-position "px") "0px") ", "
                             (if y-position (str y-position "px") "0px")
                             ") rotate("
                             (if rotation (str rotation "deg") "0deg")
                             ") " flip-transform))

        display-value (if (and is-viewer? (not visible)) "none" "block")
        pointer-events-value (if operation "auto" "none")]

    {:borderRadius border-radius
     :boxShadow (if shadow
                  (find-box-shadow shape shadow)
                  (find-box-shadow shape))
     :display display-value
     :pointerEvents pointer-events-value
     :transform transform-str}))

