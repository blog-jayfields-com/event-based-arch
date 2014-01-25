(ns main
  (:require e.coordinator m.coordinator s.coordinator channel))

(defn -main []
  (e.coordinator/start)
  (m.coordinator/start)
  (s.coordinator/start)

  (channel/publish :main/start {}))
