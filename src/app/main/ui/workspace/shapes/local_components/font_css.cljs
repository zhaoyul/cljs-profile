(ns app.main.ui.workspace.shapes.local-components.font-css
  (:require
   [app.main.ui.workspace.shapes.local-components.component-css :as ccss]))

(defn fontcss
  [{:keys [style viewer? padding animation-text operation hidden-font-color external-variable-cache]}]
  (let [cached-font-color (get external-variable-cache :font-color)
        cached-animation-text-switch (get external-variable-cache :animation-text-switch)
        cached-operation-var (get external-variable-cache :operation)

        {:keys [font-family font-size font-weight font-style line-height text-align vertical-align text-decoration fills text-transform]} (or style {})
        {:keys [fill-color fill-opacity]} (first fills)
        fill-color (or fill-color ccss/default-color)
        fill-opacity (if (some? fill-opacity) fill-opacity 1)

        animation-text-open (get animation-text :open)

        padding-map (or padding {})
        padding-top (get padding-map :padding-top 3)
        padding-right (get padding-map :padding-right 0)
        padding-bottom (get padding-map :padding-bottom 0)
        padding-left (get padding-map :padding-left 0)

        hidden-color-property hidden-font-color
        hidden-color (or (:color hidden-color-property) ccss/default-color)
        hidden-opacity (or (:opacity hidden-color-property) 1)

        txt-open? (and viewer?
                       (if (not (nil? cached-animation-text-switch))
                         cached-animation-text-switch
                         animation-text-open))

        operation-val (if viewer?
                        (if-not (nil? cached-operation-var)
                          cached-operation-var
                          (= :true operation))
                        (= :true operation))

        color-value (when-not txt-open?
                      (if operation-val
                        (or (when cached-font-color (ccss/linear-radial-color cached-font-color))
                            (let [[r g b a] (ccss/hex->rgba fill-color fill-opacity)]
                              (str "rgba(" r ", " g ", " b ", " a ")")))
                        (let [[hr hg hb ha] (ccss/hex->rgba hidden-color hidden-opacity)]
                          (str "rgba(" hr ", " hg ", " hb ", " ha ")"))))

        vertical (case vertical-align
                   "top" "flex-start"
                   "bottom" "flex-end"
                   "center" "center"
                   "center")
        text-algin (case text-align
                     "left" "flex-start"
                     "center" "center"
                     "right" "flex-end"
                     "space-between")

        padding-str (if (seq padding-map)
                      (str padding-top "px "
                           padding-right "px "
                           padding-bottom "px "
                           padding-left "px ")
                      (str "0.07rem 0px 0px 0px"))]

    {:width "100%"
     :height "100%"
     :padding padding-str
     :fontFamily font-family
     :fontSize (str font-size "px")
     :fontStyle font-style
     :fontWeight font-weight
     :textDecoration text-decoration
     :lineHeight line-height
     :display "flex"
     :alignItems vertical
     :justifyContent text-algin
     :textTransform text-transform
     :color color-value}))
