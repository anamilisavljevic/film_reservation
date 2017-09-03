(ns film-reservation.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[film_reservation started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[film_reservation has shut down successfully]=-"))
   :middleware identity})
