(ns film-reservation.test.db.core
  (:require [film-reservation.db.core :refer [*db*] :as db]
            [luminus-migrations.core :as migrations]
            [clojure.test :refer :all]
            [clojure.java.jdbc :as jdbc]
            [film-reservation.config :refer [env]]
            [mount.core :as mount]))
(use-fixtures
  :once
  (fn [f]
    (mount/start
      #'film-reservation.config/env
      #'film-reservation.db.core/*db*)
    (migrations/migrate ["migrate"] (select-keys env [:database-url]))
    (f)))
(deftest test-messages
  (jdbc/with-db-transaction [t-conn *db*]
                            (jdbc/db-set-rollback-only! t-conn)
                            (let [timestamp (org.joda.time.DateTime.)]
                              (is (= 1 (db/save-reservation!
                                         t-conn
                                         {:first_name "Tamara"
                                          :last_name "Radevic"
                                          :email "taca93@yahoo.com"
                                          :date timestamp
                                          :number_of_seat 12
                                          :cinema "Usce"
                                          :film  "Dunkirk"}
                                         {:connection t-conn})))
                              (is (=
                                    {:first_name "Tamara"
                                     :last_name "Radevic"
                                     :email "taca93@yahoo.com"
                                     :date  timestamp
                                     :number_of_seat  12
                                     :cinema "Usce"
                                     :film  "Dunkirk"}
                                    (-> (db/get-reservations t-conn {})
                                        (first)
                                        (select-keys [:first_name :last_name :email :date :number_of_seat :cinema :film])))))))
