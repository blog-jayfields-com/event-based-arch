(ns m.coordinator
  (:require m.transform channel))

(defmacro subscribe [topic pure-fn publish-event-name]
  `(channel/subscribe ~topic
                      (fn [msg#] (channel/publish ~publish-event-name (~pure-fn msg#)))))
(defn start []
  (subscribe :e.webbit/message m.transform/handle-webbit-msg :m/webbit-message))
