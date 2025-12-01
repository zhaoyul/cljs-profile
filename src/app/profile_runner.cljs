(ns app.profile-runner
  (:require [taoensso.tufte :as tufte :refer (defnp p profile)]
            [app.main.ui.workspace.shapes.local-components.component-css :as ccss]))

(tufte/add-basic-println-handler! {})

(def mock-args
  {:shape {:id #uuid "db76b28c-5ecb-80a1-8007-0d1c946a20dc", :name "Local-components", :type :local-component, :x 1216.1999999999998, :y 1020.215909090909, :width 43.80000000000018, :height 12.784090909090992, :rotation 0, :selrect {:x 1216.1999999999998, :y 1020.215909090909, :width 43.80000000000018, :height 12.784090909090992, :x1 1216.1999999999998, :y1 1020.215909090909, :x2 1260, :y2 1033}, :points [{:x 1216.1999999999998, :y 1020.215909090909} {:x 1260, :y 1020.215909090909} {:x 1260, :y 1033} {:x 1216.1999999999998, :y 1033}], :transform {:a 1, :b 0, :c 0, :d 1, :e 0, :f 0}, :transform-inverse {:a 1, :b 0, :c 0, :d 1, :e 0, :f 0}, :parent-id #uuid "db76b28c-5ecb-80a1-8007-0d1c946a20b4", :frame-id #uuid "c916f717-fdfa-80b8-8007-093960f5fbe8", :flip-x nil, :flip-y nil, :click-draw? true, :min-width 100, :rx 4, :group-title "", :min-height 42, :group-operation "auto", :meta-info-item {:property {:animation {:background {:open false, :background-open {:color "#b1b2b5"}, :background-close {:color "#b1b2b5"}, :background-frequency :fast}, :animation-text {:open false, :text-open {:color "#b1b2b5"}, :text-close {:color "#b1b2b5"}, :text-frequency :fast}, :move {:open false, :move-position-x 10, :move-position-y 10, :move-speed 3}, :rotation {:open false, :rotation-direction :clockwise, :rotation-speed 3}, :floating-line {:open false, :floating-direction :from-left-to-right, :floating-speed 3}}, :local-type :button, :object-type "按钮", :operation :true, :csharp-com-name "button602", :layer :0, :object-name "按钮602", :visible :true, :fonts {:line-height "1.2", :font-style "normal", :text-align "center", :vertical-align "center", :font-id "MicrosoftYaHei", :font-size "14", :font-weight "400", :font-variant-id "regular", :text-decoration "none", :letter-spacing "0", :fills [{:fill-color "#000000", :fill-opacity 1}], :font-family "MicrosoftYaHei"}, :text {:prompt-text {:zh_cn "", :en_gb "", :id 9637}, :text-text {:zh_cn "按钮", :en_gb "", :id 9638}}}, :permission {:trigger-events [:default-event]}}, :local-type :button, :group-permission nil, :content [{:line-height "1.2", :font-style "normal", :text-align "center", :vertical-align "center", :font-size "14", :font-weight "400", :font-variant-id "regular", :text-decoration "none", :letter-spacing "0", :fills [{:fill-color "#000000", :fill-opacity 1}], :font-family "MicrosoftYaHei"}], :hide-in-viewer false, :proportion-lock false, :init-meta {}, :group-events nil, :meta-info-id #uuid "db76b28c-5ecb-80a1-8007-0d1c4e1fb13c", :strokes [{:stroke-alignment :inner, :stroke-style :solid, :stroke-color "#000000", :stroke-opacity 1, :stroke-width 1}], :initialized? true, :proportion 1, :shadow [{:color {:color "#000000", :opacity 0}, :spread 0, :offset-y 0, :style :drop-shadow, :blur 0, :hidden false, :id #uuid "db76b28c-5ecb-80a1-8007-0d1b70d48885", :offset-x 0}], :fills [{:fill-color "#b1b2b5", :fill-opacity 1}], :ry 4}, :property {:animation {:background {:open false, :background-open {:color "#b1b2b5"}, :background-close {:color "#b1b2b5"}, :background-frequency :fast}, :animation-text {:open false, :text-open {:color "#b1b2b5"}, :text-close {:color "#b1b2b5"}, :text-frequency :fast}, :move {:open false, :move-position-x 10, :move-position-y 10, :move-speed 3}, :rotation {:open false, :rotation-direction :clockwise, :rotation-speed 3}, :floating-line {:open false, :floating-direction :from-left-to-right, :floating-speed 3}}, :local-type :button, :object-type "按钮", :operation :true, :csharp-com-name "button962", :layer :0, :object-name "按钮962", :visible :true, :fonts {:line-height "1.2", :font-style "normal", :text-align "center", :vertical-align "center", :font-id "MicrosoftYaHei", :font-size "14", :font-weight "400", :font-variant-id "regular", :text-decoration "none", :letter-spacing "0", :fills [{:fill-color "#000000", :fill-opacity 1}], :font-family "MicrosoftYaHei"}, :text {:prompt-text {:zh_cn "", :en_gb "", :id 10447, :status 1, :text_name ""}, :text-text {:zh_cn "按钮", :en_gb "", :id 10448, :status 1, :text_name "按钮"}}}, :viewer? true, :is-pressed? false, :external-variable-cache {}, :graphic-all {}, :advance-button-status {}})

(defn run-profile []
  (println "Warming up...")
  (dotimes [_ 10000]
    (ccss/prepare-component-css-args mock-args)
    (ccss/component-css mock-args)
    (ccss/hex->rgba "#ff0000" 0.5))
  
  (println "Profiling...")
  (profile
   {}
   (dotimes [_ 50000]
     (p :component-css
        (ccss/component-css mock-args)))))

(defn main []
  (run-profile))
