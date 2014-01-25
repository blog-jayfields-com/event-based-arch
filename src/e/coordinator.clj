(ns e.coordinator
  (:require channel e.transform s.state e.webbit))

(defmacro subscribe [topic side-effect-fn pure-fn & args]
  `(channel/subscribe ~topic
                      (fn [msg#] (~side-effect-fn (~pure-fn msg# ~@args)))))

(defn start []
  (subscribe :main/start println e.webbit/start channel/publish :e.webbit/message)
  (subscribe :main/start println e.transform/start-str)
  (subscribe :s.state/start-time println
             e.transform/start-time-str @s.state/start-time (System/currentTimeMillis))
  (subscribe :s.state/last-message println e.transform/last-message-str @s.state/last-message)
  (subscribe :m/webbit-message println identity)
  (subscribe :m/webbit-message e.webbit/send-message identity))
