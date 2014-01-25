(ns m.transform
  (:require [clojure.string :as s]))

(defn handle-webbit-msg [e]
  (assoc e :upcased (s/upper-case (:message e))))
