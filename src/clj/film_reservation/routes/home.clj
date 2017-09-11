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

(defn home-page []
  (layout/render
    "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))

(defn about-page []
  (layout/render "about.html"))

(defn login-page []
  (layout/render
    "login.html"))

(defn login [{:keys [params]}]
  (if (nil? {:user (db/get-user params)})
  (response/found "/") (response/found "/about")))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (POST "/login" request (login request))
  (GET "/logout" [] (login-page)))

