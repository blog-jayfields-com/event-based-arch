(ns e.transform)

(defn start-str [event]
  (format "%s <= :main/start event" event))

(defn start-time-str [event derefed-start-time handle-time]
  (format (str "start-time (via event): %s\n"
               "start-time (via deref): %s\n"
               "handle-time:            %s")
          event
          derefed-start-time
          handle-time))

(defn last-message-str [_ derefed-last-message]
  (format "last-messages %s saved in s.state" derefed-last-message))
