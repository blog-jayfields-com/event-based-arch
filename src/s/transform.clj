(ns s.transform)

(defn sync-start-time [_ _ time]
  time)

(defn sync-last-message [state e]
  (conj state (dissoc e :send-fn)))
