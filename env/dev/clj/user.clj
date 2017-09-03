(ns user
  (:require [mount.core :as mount]
            film-reservation.core))

(defn start []
  (mount/start-without #'film-reservation.core/repl-server))

(defn stop []
  (mount/stop-except #'film-reservation.core/repl-server))

(defn restart []
  (stop)
  (start))


