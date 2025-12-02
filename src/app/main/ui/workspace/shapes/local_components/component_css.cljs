(ns app.main.ui.workspace.shapes.local-components.component-css
  (:require
   [cuerdas.core :as str]
   [clojure.set :as set]))

;; --- From app.common.types.shape.attrs ---
(def default-color "#B1B2B5")

;; --- From app.common.colors ---

(def names
  {"aliceblue" "#f0f8ff"
   "antiquewhite" "#faebd7"
   "aqua" "#00ffff"
   "aquamarine" "#7fffd4"
   "azure" "#f0ffff"
   "beige" "#f5f5dc"
   "bisque" "#ffe4c4"
   "black" "#000000"
   "blanchedalmond" "#ffebcd"
   "blue" "#0000ff"
   "blueviolet" "#8a2be2"
   "brown" "#a52a2a"
   "burlywood" "#deb887"
   "cadetblue" "#5f9ea0"
   "chartreuse" "#7fff00"
   "chocolate" "#d2691e"
   "coral" "#ff7f50"
   "cornflowerblue" "#6495ed"
   "cornsilk" "#fff8dc"
   "crimson" "#dc143c"
   "cyan" "#00ffff"
   "darkblue" "#00008b"
   "darkcyan" "#008b8b"
   "darkgoldenrod" "#b8860b"
   "darkgray" "#a9a9a9"
   "darkgreen" "#006400"
   "darkgrey" "#a9a9a9"
   "darkkhaki" "#bdb76b"
   "darkmagenta" "#8b008b"
   "darkolivegreen" "#556b2f"
   "darkorange" "#ff8c00"
   "darkorchid" "#9932cc"
   "darkred" "#8b0000"
   "darksalmon" "#e9967a"
   "darkseagreen" "#8fbc8f"
   "darkslateblue" "#483d8b"
   "darkslategray" "#2f4f4f"
   "darkslategrey" "#2f4f4f"
   "darkturquoise" "#00ced1"
   "darkviolet" "#9400d3"
   "deeppink" "#ff1493"
   "deepskyblue" "#00bfff"
   "dimgray" "#696969"
   "dimgrey" "#696969"
   "dodgerblue" "#1e90ff"
   "firebrick" "#b22222"
   "floralwhite" "#fffaf0"
   "forestgreen" "#228b22"
   "fuchsia" "#ff00ff"
   "gainsboro" "#dcdcdc"
   "ghostwhite" "#f8f8ff"
   "gold" "#ffd700"
   "goldenrod" "#daa520"
   "gray" "#808080"
   "green" "#008000"
   "greenyellow" "#adff2f"
   "grey" "#808080"
   "honeydew" "#f0fff0"
   "hotpink" "#ff69b4"
   "indianred" "#cd5c5c"
   "indigo" "#4b0082"
   "ivory" "#fffff0"
   "khaki" "#f0e68c"
   "lavender" "#e6e6fa"
   "lavenderblush" "#fff0f5"
   "lawngreen" "#7cfc00"
   "lemonchiffon" "#fffacd"
   "lightblue" "#add8e6"
   "lightcoral" "#f08080"
   "lightcyan" "#e0ffff"
   "lightgoldenrodyellow" "#fafad2"
   "lightgray" "#d3d3d3"
   "lightgreen" "#90ee90"
   "lightgrey" "#d3d3d3"
   "lightpink" "#ffb6c1"
   "lightsalmon" "#ffa07a"
   "lightseagreen" "#20b2aa"
   "lightskyblue" "#87cefa"
   "lightslategray" "#778899"
   "lightslategrey" "#778899"
   "lightsteelblue" "#b0c4de"
   "lightyellow" "#ffffe0"
   "lime" "#00ff00"
   "limegreen" "#32cd32"
   "linen" "#faf0e6"
   "magenta" "#ff00ff"
   "maroon" "#800000"
   "mediumaquamarine" "#66cdaa"
   "mediumblue" "#0000cd"
   "mediumorchid" "#ba55d3"
   "mediumpurple" "#9370db"
   "mediumseagreen" "#3cb371"
   "mediumslateblue" "#7b68ee"
   "mediumspringgreen" "#00fa9a"
   "mediumturquoise" "#48d1cc"
   "mediumvioletred" "#c71585"
   "midnightblue" "#191970"
   "mintcream" "#f5fffa"
   "mistyrose" "#ffe4e1"
   "moccasin" "#ffe4b5"
   "navajowhite" "#ffdead"
   "navy" "#000080"
   "oldlace" "#fdf5e6"
   "olive" "#808000"
   "olivedrab" "#6b8e23"
   "orange" "#ffa500"
   "orangered" "#ff4500"
   "orchid" "#da70d6"
   "palegoldenrod" "#eee8aa"
   "palegreen" "#98fb98"
   "paleturquoise" "#afeeee"
   "palevioletred" "#db7093"
   "papayawhip" "#ffefd5"
   "peachpuff" "#ffdab9"
   "peru" "#cd853f"
   "pink" "#ffc0cb"
   "plum" "#dda0dd"
   "powderblue" "#b0e0e6"
   "purple" "#800080"
   "red" "#ff0000"
   "rosybrown" "#bc8f8f"
   "royalblue" "#4169e1"
   "saddlebrown" "#8b4513"
   "salmon" "#fa8072"
   "sandybrown" "#f4a460"
   "seagreen" "#2e8b57"
   "seashell" "#fff5ee"
   "sienna" "#a0522d"
   "silver" "#c0c0c0"
   "skyblue" "#87ceeb"
   "slateblue" "#6a5acd"
   "slategray" "#708090"
   "slategrey" "#708090"
   "snow" "#fffafa"
   "springgreen" "#00ff7f"
   "steelblue" "#4682b4"
   "tan" "#d2b48c"
   "teal" "#008080"
   "thistle" "#d8bfd8"
   "tomato" "#ff6347"
   "turquoise" "#40e0d0"
   "violet" "#ee82ee"
   "wheat" "#f5deb3"
   "white" "#ffffff"
   "whitesmoke" "#f5f5f5"
   "yellow" "#ffff00"
   "yellowgreen" "#9acd32"})

(def ^:private hex-color-re
  #"\#([0-9a-fA-F]{6}|[0-9a-fA-F]{3})")

(def ^:private rgb-color-re
  #"(?:|rgb)\((\d{1,3})\s*,\s*(\d{1,3})\s*,\s*(\d{1,3})\)")

(defn valid-hex-color?
  [color]
  (and (string? color)
       (some? (re-matches hex-color-re color))))

(defn parse-rgb
  [color]
  (let [result (re-matches rgb-color-re color)]
    (when (some? result)
      (let [r (js/parseInt (nth result 1))
            g (js/parseInt (nth result 2))
            b (js/parseInt (nth result 3))]
        (when (and (<= 0 r 255) (<= 0 g 255) (<= 0 b 255))
          [r g b])))))

(defn valid-rgb-color?
  [color]
  (if (string? color)
    (let [result (parse-rgb color)]
      (some? result))
    false))

(defn rgb->str
  [[r g b a]]
  (if (some? a)
    (str/fmt "rgba(%s,%s,%s,%s)" r g b a)
    (str/fmt "rgb(%s,%s,%s)" r g b)))

(defn hex->rgb
  [color]
  (if (and (string? color) (str/starts-with? color "#"))
    (let [rgb (js/parseInt (subs color 1) 16)
          r   (bit-shift-right rgb 16)
          g   (bit-and (bit-shift-right rgb 8) 255)
          b   (bit-and rgb 255)]
      [r g b])
    [0 0 0]))

(defn hex->rgba
  [data opacity]
  (-> (hex->rgb data)
      (conj opacity)))

;; --- From app.util.color ---

(defn calculate-angle [start-x start-y end-x end-y]
  (let [dx (- end-x start-x)
        dy (- end-y start-y)
        angle (Math/atan2 dy dx)
        degrees (* angle (/ 180 Math/PI))]
    (/ (Math/round (* degrees 100)) 100.0)))

(defn gradient->css
  [{:keys [type stops start-x start-y end-x end-y width] :as props}]
  (let [parse-stop
        (fn [{:keys [offset color opacity]}]
          (let [[r g b] (hex->rgb color)]
            (str/fmt "rgba(%s, %s, %s, %s) %s" r g b opacity (str (* offset 100) "%"))))

        stops-css (str/join "," (map parse-stop stops))
        degrees (calculate-angle start-x start-y end-x end-y)]
    (if (= type :linear)
      (str/fmt "linear-gradient(%sdeg, %s)" degrees stops-css)
      (str/fmt "radial-gradient(ellipse, %s)" stops-css))))

;; --- Original Component CSS Logic ---
(defn color-to-rgba-impl
  "输入各种颜色内容转成rgba"
  [color opacity]
  (cond
    (and (string? color) (= \# (nth color 0 nil)))
    (let [rgba (hex->rgba color opacity)]
      (rgb->str rgba))

    (valid-rgb-color? color)
    (let [rgb (parse-rgb color)]
      (if rgb
        (let [rgba (conj rgb opacity)]
          (rgb->str rgba))
        default-color))

    (contains? names (str/lower color))
    (let [hex (get names (str/lower color))
          rgba (hex->rgba hex opacity)]
      (rgb->str rgba))
    :else color))

(def color-to-rgba (memoize color-to-rgba-impl))

(defn- normalize-shadow
  "Normalize various shadow payloads into a flat map for CSS generation."
  [shadow]
  (when shadow
    (let [color-map (:color shadow)
          color     (or (:color color-map) (:color shadow))
          opacity   (or (:opacity color-map) (:opacity shadow) 1)]
      {:style    (:style shadow)
       :offset-x (or (:offset-x shadow) 0)
       :offset-y (or (:offset-y shadow) 0)
       :blur     (or (:blur shadow) 0)
       :spread   (or (:spread shadow) 0)
       :color    color
       :opacity  opacity})))

(def ^:private shadow->css
  "Memoized box-shadow builder to avoid repeating string/rgba work."
  (memoize
   (fn [{:keys [style offset-x offset-y blur spread color opacity] :as shadow}]
     (when shadow
       (let [inner-shadow-out (if (or (= :inner-shadow style) (= "inner-shadow" style))
                                "inset "
                                "")
             color-str        (color-to-rgba color opacity)]
         (str inner-shadow-out
              offset-x "px "
              offset-y "px "
              blur "px "
              spread "px "
              color-str))))))

(defn linear-radial-color
  "实现纯色,线性渐变,径向渐变
   线性渐变默认从左向右
   径向渐变从中间到两边"
  [color]
  (when color
    (cond
      ;; 处理直接传入的字符串颜色
      (string? color)
      color
      
      ;; 处理映射对象
      (map? color)
      (let [single-color (:color color)]
        (if single-color
          (color-to-rgba single-color (:opacity color))
          (gradient->css (:gradient color))))
      
      :else nil)))

(defn find-box-shadow
  "找box的阴影内容"
  ([shape]
   (when-let [shadow (normalize-shadow (nth (:shadow shape) 0 nil))]
     (shadow->css shadow)))
  ([shape shadows]
   (when-let [shadow (normalize-shadow shadows)]
     (shadow->css shadow))))

(def component-css-shape-keys
  [:rx :ry :r1 :r2 :r3 :r4 :fills :strokes :shadow :auto-width :auto-height])

(def component-css-property-keys
  [:click-down-color :click-down-image :script-set-image :graphic-list :fill
   :animation :advance-events-id :operation :hidden-color :shadow])

(defn prepare-component-css-args
  "提取组件样式所需的字段，避免无关数据触发重复计算"
  [{:keys [shape property] :as args}]
  (let [shape*    (when shape (select-keys shape component-css-shape-keys))
        property* (when property (select-keys property component-css-property-keys))
        ;; 预计算圆角字符串以便复用
        border-radius (when shape*
                        (if (contains? shape* :rx)
                          (str (or (:rx shape*) 0) "px "
                               (or (:ry shape*) 0) "px")
                          (str (or (:r1 shape*) 0) "px "
                               (or (:r2 shape*) 0) "px "
                               (or (:r3 shape*) 0) "px "
                               (or (:r4 shape*) 0) "px")))
        ;; 预计算默认阴影字符串
        shape-shadow (when shape*
                       (normalize-shadow (nth (:shadow shape*) 0 nil)))
        precomputed-shadow (when shape-shadow
                             (shadow->css shape-shadow))
        ;; 预计算基础背景色（不含按下态、动画等动态内容）
        fills             (:fills shape*)
        first-fill        (nth fills 0 nil)
        fill-opacity      (:fill-opacity first-fill)
        fill-color        (:fill-color first-fill)
        fill-gradient     (:fill-color-gradient first-fill)
        base-background   (cond
                            fill-gradient (gradient->css fill-gradient)
                            (and fill-color fill-opacity)
                            (let [[r g b a] (hex->rgba fill-color fill-opacity)]
                              (str "rgba(" r ", " g ", " b ", " a ")"))
                            :else nil)
        ;; 预计算描边字符串
        strokes           (nth (:strokes shape*) 0 nil)
        stroke-color      (:stroke-color strokes)
        stroke-opacity    (:stroke-opacity strokes)
        stroke-rgba       (when (and stroke-color stroke-opacity)
                            (hex->rgba stroke-color stroke-opacity))
        stroke-width      (:stroke-width strokes)
        stroke-style-raw  (:stroke-style strokes)
        border-style      (case stroke-style-raw
                            :dashed "dashed"
                            :solid  "solid"
                            :dotted "dotted"
                            :mixed  "double"
                            "solid")
        property-border   (when stroke-rgba
                            (let [[sr sg sb sa] stroke-rgba]
                              (str stroke-width "px "
                                   (str "rgba(" sr ", " sg ", " sb ", " sa ")")
                                   " " border-style)))]
    (cond-> args
      shape (assoc :shape (cond-> shape*
                            border-radius (assoc :border-radius-str border-radius)
                            precomputed-shadow (assoc :precomputed-shadow precomputed-shadow)
                            base-background (assoc :base-background base-background)
                            stroke-rgba (assoc :stroke-rgba stroke-rgba)
                            property-border (assoc :property-border property-border)
                            border-style (assoc :border-style border-style)))
      property (assoc :property property*))))

(def prepare-component-css-args-memoized
  "缓存版本，用于相同形状/属性的重复调用"
  (memoize prepare-component-css-args))

(defn component-css
  "组件CSS样式生成函数-处理组件的视觉样式
   根据预览模式和变量绑定情况，动态生成组件的CSS样式

   参数说明:
   `shape` - 控件的形状信息（位置、大小、填充等）
   `property` - 控件的属性信息（自定义属性、配置等）
   `viewer?` - 是否为预览模式
   `is-pressed?` - 按钮是否处于按下状态
  `external-variable-cache` - 外部变量缓存（用于存储动态变量值）
  `graphic-all` - 全局图片库 map
  `advance-button-status` - 高级控件状态 map"
  [{:keys [shape property viewer? is-pressed? external-variable-cache graphic-all advance-button-status]}]
  (let [;; === 边框圆角处理 ===
        ;; 支持两种圆角模式：统一圆角(rx/ry)和四角独立圆角(r1/r2/r3/r4)
        border-radius              (or (:border-radius-str shape)
                                       (if (contains? shape :rx)
                                         (str (or (:rx shape) 0) "px "
                                              (or (:ry shape) 0) "px")
                                         (str (or (:r1 shape) 0) "px "
                                              (or (:r2 shape) 0) "px "
                                              (or (:r3 shape) 0) "px "
                                              (or (:r4 shape) 0) "px")))
        
        ;; === 填充颜色处理 ===
        fills                      (get shape :fills) ; 获取填充配置数组
        ;; 缓存重复访问的填充数据
        first-fill                 (nth fills 0 nil)
        second-fill                (nth fills 1 nil)
        fill-opacity               (:fill-opacity first-fill)  ; 第一个填充的透明度
        fill-color                 (:fill-color first-fill)    ; 第一个填充的颜色
        fill-color-gradient        (:fill-color-gradient first-fill) ; 第一个填充的渐变色
        
        ;; === 按下状态数据准备 (Lazy) ===
        click-down-image           (when is-pressed? (:click-down-image property))
        click-image-opacity        (when is-pressed? (:opacity click-down-image))
        
        ;; 透明度优先级：按下状态的图片透明度 > 第二个填充的透明度 > 默认值1
        opacity                    (or (if (and is-pressed? click-image-opacity)
                                         click-image-opacity
                                         (:fill-opacity second-fill)) 1)
        ;; 将十六进制颜色转换为RGBA值
        [r g b a]                  (hex->rgba fill-color fill-opacity)
        ;; === 脚本设置的图片 ===
        script-set-image           (get property :script-set-image) ; 通过脚本动态设置的图片
        
        ;; === 缓存 external-variable-cache 访问 ===
        ;; 将重复的 get 调用提取为变量，提高性能
        cached-fill-image          (get external-variable-cache :fill-image)      ; 变量绑定的图片名
        cached-graphic-obj-var     (get external-variable-cache :graphic-obj-var) ; 图形对象变量
        cached-fill-color-single   (get external-variable-cache :fill-color-single) ; 表达式背景色
        cached-stroke-color-single (get external-variable-cache :stroke-color-single) ; 边框颜色变量
        cached-bg-open-var         (get external-variable-cache :animation-background-switch) ; 背景动画开关
        cached-operation-var       (get external-variable-cache :operation) ; 操作状态变量
        cached-shadow-var          (get external-variable-cache :shadow) ; 阴影变量
        ;; === 背景颜色计算 ===
        ;; 优先级：按下状态颜色 > 渐变色 > 普通填充色
        background-color           (let [click-down-color (:click-down-color property)]
                                      (if (and is-pressed? (not (str/blank? (:color click-down-color))))
                                        ;; 按下状态：使用按下时的颜色配置
                                        (let [click-color   (:color click-down-color)
                                              click-opacity (-> click-down-color :opacity)
                                              [cr cg cb ca] (hex->rgba click-color click-opacity)]
                                          (str "rgba(" cr ", " cg ", " cb ", " ca ")"))
                                        ;; 非按下状态：使用预计算或即时计算的背景色/渐变
                                        (or (:base-background shape)
                                            (when fill-color-gradient
                                              (gradient->css fill-color-gradient))
                                            (when (and (some? fill-color) (some? fill-opacity))
                                              (str "rgba(" r ", " g ", " b ", " a ")")))))
        ;; === 背景图片处理 ===
        ;; 背景图片的优先级和来源判断
        ;; 缓存重复计算的变量
        fill-image-data            (:fill-image second-fill)
        fill-image-mtype           (:mtype fill-image-data)
        fill-image-base64          (:img-base64 fill-image-data)
        
        ;; 缓存按下图片和属性相关数据（已在上面定义）
        click-down-image-data      (when is-pressed? (:image click-down-image))
        click-image-keep-aspect    (when is-pressed? (:keep-aspect-ratio click-down-image-data))

        background-image           (if (and is-pressed? (seq click-down-image-data))
                                     ;; 按下状态：使用按下时的图片
                                     (let [image-type (:mtype click-down-image-data)
                                           image-data (:img-base64 click-down-image-data)]
                                       (str "url(data:" image-type ";base64," image-data ")"))
                                     ;; 非按下状态：检查是否启用图片显示
                                     (let [use-image-flag  (:use-image (:graphic-list property))
                                           remove-img-flag (:remove-img (:fill property))] 
                                       (if (and
                                            ;; 检查图片显示开关（图形对象是true/false,其他控件没有这个字段）
                                            (or (nil? use-image-flag) use-image-flag)
                                            ;; 检查是否有图片源（脚本设置或填充图片）
                                            (or script-set-image
                                                (not-empty fill-image-data)))
                                         (if script-set-image
                                           ;; 脚本设置的图片：从全局图片库获取
                                           (str "url(" (get graphic-all (keyword script-set-image)) ")")
                                           ;; 填充图片：根据移除标志决定是否显示
                                           (if remove-img-flag
                                             ;; 移除图片：使用空的base64数据
                                             (str "url(data:" fill-image-mtype ";base64," "" ")")
                                             ;; 显示图片：使用完整的base64数据
                                             (str "url(data:" fill-image-mtype ";base64," fill-image-base64 ")")))
                                         ;; 不显示图片
                                         nil)))
        ;; === 预览模式下的背景图片处理 ===
        background-image-url       (if cached-fill-image
                                     ;; 变量绑定的图片：从全局图片库获取
                                     (str "url(" (get graphic-all (keyword cached-fill-image)) ")")
                                     ;; 检查图形对象变量
                                     (if (and (:use-image (:graphic-list property)) cached-graphic-obj-var)
                                       nil ; 有图形对象变量时不使用默认图片
                                       background-image)) ; 使用默认背景图片
        ;; === 表达式绑定的背景颜色 ===
        ;; 处理通过变量绑定的线性/径向渐变背景色
        view-expression-bg-color   (when cached-fill-color-single
                                     (linear-radial-color cached-fill-color-single))
        ;; === 图片填充方式处理 ===
        ;; 根据按下状态和保持宽高比设置决定图片填充方式
        keep-aspect-ratio          (if is-pressed?
                                     click-image-keep-aspect ; 按下状态的图片宽高比（已缓存）
                                     (-> second-fill :fill-image :keep-aspect-ratio)) ; 普通状态的图片宽高比
        ;; 填充方式：保持宽高比用contain，否则拉伸填充
        fill-method                (if keep-aspect-ratio
                                     " center / contain no-repeat " ; 保持宽高比，居中显示
                                     " center / 100% 100% no-repeat") ; 拉伸填充整个区域
        ;; === 最终背景样式组合 ===
        background                 (if viewer?
                                     ;; 预览模式：优先使用表达式背景色，然后是普通背景色
                                     {:background
                                      (str (or
                                            view-expression-bg-color  ; 表达式背景色
                                            background-color)         ; 普通背景色
                                           " "
                                           background-image-url       ; 背景图片URL
                                           fill-method)}              ; 填充方式
                                     ;; 编辑模式：根据是否有背景图片决定样式
                                     (if (not-empty background-image)
                                       {:background (str background-color " " background-image
                                                         fill-method)}
                                       {:background background-color}))
        ;; === 边框样式处理 ===
        ;; 获取第一个描边配置（通常只有一个描边）
        strokes                    (nth (:strokes shape) 0 nil)
        ;; 缓存 strokes 的属性，避免重复访问
        stroke-color               (:stroke-color strokes)      ; 描边颜色
        stroke-opacity             (:stroke-opacity strokes)    ; 描边透明度
        stroke-width               (:stroke-width strokes)      ; 描边宽度
        stroke-style-raw           (:stroke-style strokes)      ; 描边样式（原始值）
        ;; 将描边颜色转换为RGBA值
        [sr sg sb sa]              (or (:stroke-rgba shape)
                                      (hex->rgba stroke-color stroke-opacity))
        ;; 描边样式映射：将内部样式转换为CSS样式
        border-style               (or (:border-style shape)
                                       (case stroke-style-raw
                                         :dashed "dashed" ; 虚线
                                         :solid  "solid"  ; 实线
                                         :dotted "dotted" ; 点线
                                         :mixed  "double" ; 双线
                                         "solid"))          ; 默认实线
        ;; === 预览模式下的动态边框 ===
        ;; 处理变量绑定的边框属性，重命名键值以匹配内部格式
        viewer-border-fn           (fn [border-var]
                                     (set/rename-keys
                                      border-var
                                      {"Color"        :color ; 边框颜色
                                       "Transparency" :opacity ; 边框透明度
                                       "Width"        :width ; 边框宽度
                                       "BorderType"   :stroke-style})) ; 边框样式
        ;; 编辑模式下的边框样式字符串
        property-border            (or (:property-border shape)
                                       (str stroke-width "px "
                                            (str "rgba(" sr ", " sg ", " sb ", " sa ")")
                                            " " border-style))
        ;; 最终边框样式：预览模式优先使用变量值，否则使用默认值
        border                     (if (and viewer? cached-stroke-color-single)
                                     (let [viewer-border (viewer-border-fn cached-stroke-color-single)]
                                       (str (or (:width viewer-border)      ; 变量宽度
                                                stroke-width)               ; 默认宽度
                                            "px "
                                            (or (color-to-rgba (:color viewer-border)    ; 变量颜色
                                                               (:opacity viewer-border))  ; 变量透明度
                                                (str "rgba(" sr ", " sg ", " sb ", " sa ")")) ; 默认颜色
                                            " " (or (:stroke-style viewer-border) ; 变量样式
                                                    border-style)))            ; 默认样式
                                     property-border)
        ;; === 背景动画控制 ===
        ;; 控制背景动画的开关状态
        bg-open                    (when viewer?
                                     (if-not (nil? cached-bg-open-var)
                                       ;; 优先使用变量绑定的动画开关
                                       cached-bg-open-var
                                       ;; 否则使用属性配置的动画开关
                                       (:open (:background (:animation property)))))
        ;; === 禁用状态处理 ===
        ;; 检查是否绑定了高级控件
        has-advance-events?        (some? (:advance-events-id property))
        
        ;; === 操作状态判断 ===
        ;; 确定控件是否可操作，优先级：高级控件状态 > 变量绑定 > 属性配置
        operation                  (cond
                                     ;; 预览模式且有高级控件：使用高级控件状态
                                     (and viewer? has-advance-events?)
                                     (let [adv-op (get advance-button-status (keyword (:advance-events-id property)))]
                                       (= "false" (str adv-op)))
                                     ;; 预览模式且有变量绑定：使用变量值
                                     (and viewer? (some? cached-operation-var))
                                     cached-operation-var
                                     ;; 预览模式：使用属性配置
                                     viewer? (= :true (:operation property))
                                     ;; 编辑模式：使用属性配置
                                     :else   (= :true (:operation property)))
        ;; === 禁用时的颜色处理 ===
        ;; 只有在控件被禁用时才处理禁用颜色，避免不必要的计算
        hidden-color              (when (and (not (nil? operation)) (not operation))
                                    (let [hidden-color-property (:hidden-color property) ; 禁用颜色配置
                                          hidden-opacity        (or(:fill-opacity hidden-color-property)
                                                                   (:opacity hidden-color-property) 1) ; 禁用时透明度
                                          hicolor               (or (:fill-color hidden-color-property)
                                                                    (:color hidden-color-property) default-color) ; 禁用时颜色
                                          [hr hg hb ha]         (hex->rgba hicolor hidden-opacity)] ; 转换为RGBA
                                      (str "rgba(" hr ", " hg ", " hb ", " ha ")")))  ; 格式化颜色字符串
        ;; === 阴影效果处理 ===
        ;; 阴影属性键值重命名函数
        handle-shadow              (fn [shadow]
                                     (set/rename-keys shadow
                                                      {"ShadowType"   :style     ; 阴影类型
                                                       "X"            :offset-x  ; X轴偏移
                                                       "Y"            :offset-y  ; Y轴偏移
                                                       "Fuzzy"        :blur      ; 模糊半径
                                                       "Expand"       :spread    ; 扩展半径
                                                       "Color"        :color     ; 阴影颜色
                                                       "Transparency" :opacity})) ; 阴影透明度
        ;; 阴影变量配置
        shadow                     (when (and viewer? cached-shadow-var)
                                     (handle-shadow cached-shadow-var)) 
        ;; === 尺寸处理 ===
        ;; 宽度：自适应宽度或100%填充
        width                      (if (:auto-width shape) "fit-content" "100%")
        ;; 高度：自适应高度或100%填充
        height                     (if (:auto-height shape) "fit-content" "100%")
        
        ;; === Box Shadow 计算提前 ===
        box-shadow-val            (if shadow
                                    (find-box-shadow shape shadow) ; 自定义阴影
                                    (or (:precomputed-shadow shape)
                                        (find-box-shadow shape)))       ; 默认阴影
        
        ;; === Background 计算提前 ===
        background-val            (cond
                                    ;; -------- 禁用状态背景色 --------
                                    (and (some? operation)
                                         (not operation)
                                         hidden-color)
                                    hidden-color

                                    ;; -------- 背景动画开启：清空背景 --------
                                    bg-open
                                    ""

                                    ;; -------- 默认背景：来自 background 配置 --------
                                    :else
                                    (:background background))]
    ;; === 样式对象构建 ===
    ;; 使用cond->进行条件式样式合并，基础样式始终存在
    ;; 生成组件最终样式（无 cond->，一次性构造 map）
    {:position        "relative"           ; 相对定位，便于子组件绝对定位
     :width           width                ; 动态宽度
     :height          height               ; 动态高度
     :lineHeight      0                    ; 行高为 0，避免文本偏移
     :overflow        "hidden"             ; 隐藏溢出内容
     :borderRadius    border-radius        ; 圆角半径
     :boxShadow       box-shadow-val       ; 阴影
     :border          border               ; 边框样式
     :opacity         opacity              ; 控件透明度（影响背景/图片/内容）
     :background      background-val}))   ; 背景
