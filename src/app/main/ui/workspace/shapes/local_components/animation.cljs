(ns app.main.ui.workspace.shapes.local-components.animation
  (:require
   [app.main.ui.workspace.shapes.local-components.component-css :as ccss]
   [cuerdas.core :as str]))

 

(defn handle-animation-setting
  [{:keys [shape listen property all-variables variable-prefix external-variable-cache]}]
  (let [animation-config (:animation property)
        bg-config (:background animation-config)
        text-config (:animation-text animation-config)
        move-config (:move animation-config)
        rotation-config (:rotation animation-config)

        background-open? (get bg-config :open)
        text-open? (get text-config :open)
        move-open? (get move-config :open)
        rotate-open? (get rotation-config :open)

        shape-id (:id shape)
        shape-x (get shape :x 0)
        shape-y (get shape :y 0)
        shape-width (get shape :width 0)
        shape-height (get shape :height 0)
        has-dynamic-data? (and external-variable-cache (not-empty external-variable-cache))

        variable-cache external-variable-cache

        get-cached-var (fn [attr fallback]
                         (if has-dynamic-data?
                           (get variable-cache attr fallback)
                           fallback))

        handle-color-result (if has-dynamic-data?
                              (fn [attr fallback-color]
                                (if-let [variable-value (get variable-cache attr)]
                                  (ccss/color-to-rgba
                                   (get variable-value "Color")
                                   (get variable-value "Transparency"))
                                  fallback-color))
                              (fn [_attr fallback-color] fallback-color))

        handle-switch-result (fn [attr fallback]
                               (if-let [value (get variable-cache attr)]
                                 (cond
                                   (boolean? value) value
                                   (number? value) (not (zero? value))
                                   (string? value) (let [s (str/lower (str/trim value))]
                                                     (not (#{"false" "0" ""} s)))
                                   :else true)
                                 fallback))

        bg-open? (handle-switch-result :animation-background-switch background-open?)
        txt-open? (handle-switch-result :animation-text-switch text-open?)
        move? (handle-switch-result :animation-move-switch move-open?)
        rotate? (handle-switch-result :animation-rotate-switch rotate-open?)

        bg-open-color (get bg-config :background-open)
        bg-close-color (get bg-config :background-close)
        txt-open-color (get text-config :text-open)
        txt-close-color (get text-config :text-close)

        bg-open-color-value (or (:color bg-open-color) ccss/default-color)
        bg-open-opacity-value (or (:opacity bg-open-color) 1)
        bg-close-color-value (or (:color bg-close-color) ccss/default-color)
        bg-close-opacity-value (or (:opacity bg-close-color) 1)
        txt-open-color-value (or (:color txt-open-color) ccss/default-color)
        txt-open-opacity-value (or (:opacity txt-open-color) 1)
        txt-close-color-value (or (:color txt-close-color) ccss/default-color)
        txt-close-opacity-value (or (:opacity txt-close-color) 1)

        open-color (when bg-open?
                     (let [[r g b a] (ccss/hex->rgba bg-open-color-value bg-open-opacity-value)]
                       (handle-color-result :animation-background-color-open
                                            (str "rgba(" r ", " g ", " b ", " a ")"))))

        close-color (when bg-open?
                      (let [[r g b a] (ccss/hex->rgba bg-close-color-value bg-close-opacity-value)]
                        (handle-color-result :animation-background-color-close
                                             (str "rgba(" r ", " g ", " b ", " a ")"))))

        text-open-color (when txt-open?
                          (let [[r g b a] (ccss/hex->rgba txt-open-color-value txt-open-opacity-value)]
                            (handle-color-result :animation-text-color-open
                                                 (str "rgba(" r ", " g ", " b ", " a ")"))))

        text-close-color (when txt-open?
                           (let [[r g b a] (ccss/hex->rgba txt-close-color-value txt-close-opacity-value)]
                             (handle-color-result :animation-text-color-close
                                                  (str "rgba(" r ", " g ", " b ", " a ")"))))

        bg-frequency-config (get bg-config :background-frequency)
        txt-frequency-config (get text-config :text-frequency)

        bg-frequency (get-cached-var :animation-background-frequency bg-frequency-config)
        txt-frequency (get-cached-var :animation-text-frequency txt-frequency-config)

        bg-time (case bg-frequency
                  :fast 0.5
                  :middle 1
                  :slow 2
                  1)

        txt-time (case txt-frequency
                   :fast 0.5
                   :middle 1
                   :slow 2
                   1)

        move-x-config (get move-config :move-position-x)
        move-y-config (get move-config :move-position-y)
        move-speed-config (get move-config :move-speed)
        rotate-direction-config (get rotation-config :rotation-direction)
        rotate-speed-config (get rotation-config :rotation-speed)

        move-x (get-cached-var :animation-move-position-x move-x-config)
        move-y (get-cached-var :animation-move-position-y move-y-config)
        move-speed (get-cached-var :animation-move-speed move-speed-config)

        rotate-direct-varvalue (get-cached-var :animation-rotation-direction "")
        rotate-direct (case rotate-direct-varvalue
                        1 :clockwise
                        2 :anti-clockwise
                        rotate-direction-config)
        rotate-speed (get-cached-var :animation-rotation-speed rotate-speed-config)

        x (get-cached-var :x-position shape-x)
        y (get-cached-var :y-position shape-y)
        width (get-cached-var :width shape-width)
        height (get-cached-var :height shape-height)

        rotation-cx (when rotate? (+ x (/ width 2)))
        rotation-cy (when rotate? (+ y (/ height 2)))
        cx (+ (+ x (js/Number (or (when move? move-x) 0))) (/ width 2))
        cy (+ (+ y (js/Number (or (when move? move-y) 0))) (/ height 2))

        is-move-or-group? (when (or move? (#{:component :group} (:local-type property)))
                            (str " translate(" move-x "px, " move-y "px);"
                                 "transform-origin:" cx "px " cy "px"))

        rotate-keyframe-name (if (= :clockwise rotate-direct)
                               (str "rotate-cw" shape-id)
                               (str "rotate-ccw" shape-id))
        rotate-degrees (if (= :clockwise rotate-direct) [0 360] [0 -360])

        none-open? (not (or bg-open? txt-open? move? rotate?))

        parts (when-not none-open?
                (cond-> []
                  bg-open? (conj (str "@keyframes blink" shape-id " { 0% { background-color: " open-color "; } 50% { background-color: " close-color "; } 100% { background-color: " open-color "; } } "))
                  txt-open? (conj (str ".text" shape-id " { animation: blinktext" shape-id " " txt-time "s infinite; } "))
                  txt-open? (conj (str "@keyframes blinktext" shape-id " { 0% { color: " text-open-color " ; } 50% { color: " text-close-color "; } 100% { color: " text-open-color " ; } } "))
                  (or move? rotate?) (conj (str "@keyframes moveAndRotate" shape-id " { 0% {transform: translate(0, 0) "
                                                 (when rotate? (str " rotate(" (first rotate-degrees) "deg); transform-origin:" rotation-cx "px " rotation-cy "px "))
                                                 "; }100% {transform: translate(" move-x "px, " move-y "px) "
                                                 (when rotate? (str " rotate(" (second rotate-degrees) "deg); transform-origin:" rotation-cx "px " rotation-cy "px "))
                                                 "; } }"))
                  rotate? (conj (str "@keyframes rotate-cw" shape-id " { from { transform: rotate(0deg)" is-move-or-group? "; } to { transform: rotate(360deg)" is-move-or-group? "; } }"))
                  rotate? (conj (str "@keyframes rotate-ccw" shape-id " { from { transform: rotate(0deg)" is-move-or-group? " ;} to { transform: rotate(-360deg)" is-move-or-group? ";}}"))
                  rotate? (conj (str ".rotate-cw" shape-id " { animation: rotate-cw" shape-id " " rotate-speed "s linear infinite ;}"))
                  rotate? (conj (str ".rotate-ccw" shape-id " { animation: rotate-ccw" shape-id " " rotate-speed "s linear infinite; } "))))

        anim-items (when-not none-open?
                     (cond-> []
                       bg-open? (conj (str "blink" shape-id " " bg-time "s infinite"))
                       txt-open? (conj (str "blinktext" shape-id " " txt-time "s infinite"))
                       move? (conj (str "moveAndRotate" shape-id " " move-speed "s linear forwards"))
                       rotate? (conj (str rotate-keyframe-name " " rotate-speed "s linear " (when move? (str move-speed "s ")) "infinite"))))

        animation-line (when (and anim-items (not-empty anim-items))
                         (str "animation:" (str/join ", " anim-items) ";"))
        transition-line (when (and (not none-open?) move?) (str " transition: transform " move-speed "s ease-in-out; "))
        final-style (if none-open?
                      ""
                      (str (str/join "" parts)
                           ".animation-all" shape-id "{" animation-line transition-line "}"))]
    final-style))
