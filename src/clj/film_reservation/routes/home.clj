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

(defn login-page []
  (layout/render
    "login.html"))

(defn login [{:keys [params]}]
  (if (nil? {:user (db/get-user params)}) (response/found "/")  (response/found "/main")))


(defroutes home-routes
  (GET "/" [] (login-page))
  (GET "/about" [] (about-page))
  (GET "/main" [] (main-page))
  (POST "/login" request (login request))
  (GET "/logout" [] (login-page))
  (GET "/all-reservations" [] (all-reservation-page))
  (GET "/delete-reservation" request (delete-reservation request))
)
