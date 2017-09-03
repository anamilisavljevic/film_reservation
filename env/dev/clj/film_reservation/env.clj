(ns film-reservation.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [film-reservation.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[film_reservation started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[film_reservation has shut down successfully]=-"))
   :middleware wrap-dev})
