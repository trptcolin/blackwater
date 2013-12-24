(ns black.water.korma
  (:require [clojure.java.jdbc :as j]
            [robert.hooke :refer [add-hook]]
            [korma.db :as kdb]
            [korma.core :as kc]
            [clansi.core :refer :all]
            [black.water.log :refer [log-sql]]
            [clj-time.core :as time]))

(defn korma-logger
  [sql-accessor params-accessor f args]
  (let [params     (if (empty? (params-accessor args))
                     "none"
                     (clojure.string/join ", " (params-accessor args)))
        start      (time/now)
        result     (apply f args)
        end        (time/now)
        time-taken (time/in-millis (time/interval start end))]
    (log-sql (sql-accessor args) params time-taken)
    result))

(defn korma-hook
  "Hook for korma, mercifully the library has a universal, singular
   function where all queries eventually end up. <3"
  [f & args]
  (korma-logger #(:sql-str (first %))
                #(:params (first %))
                f args))

(defn decorate-korma!
  "Hooks into Korma to log SQL that gets executed."
  []
  (add-hook #'kdb/exec-sql #'korma-hook))
