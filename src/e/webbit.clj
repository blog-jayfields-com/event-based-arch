(ns e.webbit
  (:require [clojure.data.json :as json]
            channel)
  (:import [org.webbitserver WebServer WebServers WebSocketHandler]
           [org.webbitserver.handler StaticFileHandler]))

(defn on-message [connection json-message publish-fn topic]
  (let [message (-> json-message json/read-json (get-in [:data :message]))
        send-fn #(.send connection (json/json-str %))]
    (publish-fn topic {:message message :send-fn send-fn})))

(defn start [_ publish-fn topic]
  (format "webserver started at uri: %s"
          (.getUri ^org.webbitserver.WebServer
                   (doto (WebServers/createWebServer 8080)
                     (.add "/websocket"
                           (proxy [WebSocketHandler] []
                             (onOpen [c] (println "opened" c))
                             (onClose [c] (println "closed" c))
                             (onMessage [c j] (on-message c j publish-fn topic))))

                     (.add (StaticFileHandler. "web"))
                     (.start)))))

(defn send-message [{:keys [send-fn upcased]}]
  (send-fn {:type "upcased" :message upcased}))
