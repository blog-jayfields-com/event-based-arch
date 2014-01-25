(ns s.coordinator
  (:require channel s.transform))

(defmacro subscribe [topic an-atom pure-fn & args]
  `(channel/subscribe ~topic
                      (fn [msg#]
                        (swap! ~an-atom ~pure-fn msg# ~@args)
                        (channel/publish (keyword ~(str an-atom)) (deref ~an-atom)))))

(defn start []
  (subscribe :m/webbit-message s.state/last-message s.transform/sync-last-message)
  (subscribe :main/start s.state/start-time
             s.transform/sync-start-time (System/currentTimeMillis)))
