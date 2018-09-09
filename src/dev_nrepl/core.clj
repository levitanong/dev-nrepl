(ns dev-nrepl
  (:gen-class)
  (:require [cider-nrepl.main]
            [clojure.tools.cli :refer [parse-opts]]))

(def cli-options
  [["-s" "--use-shadow-cljs" "Use shadow cljs"]
   ["-s4" "--use-shadow-cljs-04" "Use shadow cljs with nrepl 0.4.x"]])

(def shadow-middleware-04
  ["shadow.cljs.devtools.server.nrepl04/cljs-load-file"
   "shadow.cljs.devtools.server.nrepl04/cljs-eval"
   "shadow.cljs.devtools.server.nrepl04/cljs-select"
   "cider.piggieback/wrap-cljs-repl"])

(def shadow-middleware
  ["shadow.cljs.devtools.server.nrepl/cljs-load-file"
   "shadow.cljs.devtools.server.nrepl/cljs-eval"
   "shadow.cljs.devtools.server.nrepl/cljs-select"
   "cider.piggieback/wrap-cljs-repl"])

(def default-middleware
  ["cider.nrepl/cider-middleware"
   "refactor-nrepl.middleware/wrap-refactor"])

(defn -main [& args]
  (let [{:keys [options]}            (parse-opts args cli-options)
        {:keys [use-shadow-cljs
                use-shadow-cljs-04]} options]
    (println "Initializing cider nrepl with the following options: " options)
    (cider-nrepl.main/init
     (into []
           (concat default-middleware
                   (when use-shadow-cljs
                     shadow-middleware)
                   (when use-shadow-cljs-04
                     shadow-middleware-04))))))
