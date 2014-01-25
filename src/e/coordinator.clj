(ns e.coordinator
  (:require channel e.transform s.state))

(defmacro subscribe [topic side-effect-fn pure-fn & args]
  `(channel/subscribe ~topic
                      (fn [msg#] (~side-effect-fn (~pure-fn msg# ~@args)))))

(defn start []
  (subscribe :main/start println e.transform/start-str)
  (subscribe :s.state/start-time println
             e.transform/start-time-str @s.state/start-time (System/currentTimeMillis)))
