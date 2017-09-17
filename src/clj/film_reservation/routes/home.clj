(ns film-reservation.routes.home
  (:require [film-reservation.layout :as layout]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [film-reservation.db.core :as db]
            [struct.core :as st]
            [clj-time.core :as t]
            [clj-time.format :as f]
            [ring.util.response :refer [redirect]]
            [hiccup.page :as h]))

(defn main-page []
  (layout/render
    "main.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defn all-reservation-page []
  (layout/render "all-reservations.html" {:reservations (db/get-reservations)}))

(defn delete-reservation [{:keys [params]}]
  (db/delete-reservation params)
  (response/found "/all-reservations"))

(def custom-formatter (f/formatter "yyyy-MM-dd"))

(defn format-date
  [date-str]
  (f/parse custom-formatter date-str))

(def message-schema
  [[:first_name
    st/required
    st/string]

   [:last_name
    st/required
    st/string]

   [:email
    st/required
    st/string]

   [:number_of_seat
    st/required
    st/string]

   [:date
    st/required
    st/string]
   ])

(defn validate-message [params]
  (first (st/validate params message-schema)))

(defn save-reservation! [{:keys [params]}]
  (if-let [errors (validate-message params)]
    (-> (response/found "/make-reservation")
        (assoc :flash (assoc params :errors errors)))
    (do
      (db/save-reservation! (assoc params :date (format-date (get params :date))))
      (response/found "/all-reservations")
      )
    )
  )

(defn make-reservation-page [{:keys [params flash]}]
  (layout/render
    "make-reservation.html"
    (merge flash (when (contains? params :id_reservation)
                   {:reservation (db/get-reservation params)})
                 (when (contains? params :id_film)
                   {:getFilm (db/get-film params)})
           {:films (db/get-films)})))

(defn update-reservation! [{:keys [params]}]
  (db/update-reservation! (assoc params :date (format-date (get params :date))))
  (response/found "/all-reservations"))

(defn login-page []
  (layout/render
    "login.html"))

(defn login [{:keys [params]}]
  (if (nil? (db/get-user params)) (response/found "/")  (response/found "/main")))

(defn all-films-page []
  (layout/render "all-films.html" {:films(db/get-films)}))

(defroutes home-routes
  (GET "/" [] (login-page))
  (GET "/about" [] (about-page))
  (GET "/main" [] (main-page))
  (POST "/login" request (login request))
  (GET "/logout" [] (login-page))
  (GET "/all-reservations" [] (all-reservation-page))
  (GET "/make-reservation" request (make-reservation-page request))
  (POST "/save-reservation" request ((if (contains? (get request :params) :id_reservation) update-reservation! save-reservation!) request))
  (GET "/delete-reservation" request (delete-reservation request))
  (GET "/all-films" [] (all-films-page))
)
