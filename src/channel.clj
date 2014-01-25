(ns channel)

(defonce atom-masquerading-as-a-channel (atom nil))

(defn subscribe [topic f]
  (add-watch atom-masquerading-as-a-channel (gensym)
             (fn [_ _ _ msg]
               (when (= topic (:topic msg))
                 (f (:payload msg))))))

(defn publish [topic anything]
  (reset! atom-masquerading-as-a-channel {:topic topic :payload anything}))
