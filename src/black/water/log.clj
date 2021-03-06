(ns black.water.log
  (:require [clansi.core :refer :all]
            [clj-time.core :as time]))

;; Color global variables, rebind as thou wilt.
(def sql-color :green)
(def params-color :cyan)
(def time-color :red)

(def logga nil)

(defn set-logger! [fun]
  (alter-var-root
   (var logga)
   (fn [f]
     fun)))

(defn sanitize [sql]
  (let [single-line (apply str (replace {\newline " "} sql))]
    (clojure.string/replace
     single-line #" {2,}"
                  " ")))

(defn log-sql
  "Given the sql string, parameters, and the milliseconds it took to
   execute, print a (possibly) colorized readout of the string and the
   millis."
  ([sql millis] (log-sql sql nil millis))
  ([sql params millis]
   (let [clean-sql (sanitize sql)
         formatted (str (style clean-sql sql-color)
                        (when params
                          (str " | params: "
                               (style params params-color)))
                        " | took: "
                        (style millis time-color)
                        " ms")]
     (if (and logga (fn? logga))
       (logga clean-sql millis)
       (println formatted)))))
