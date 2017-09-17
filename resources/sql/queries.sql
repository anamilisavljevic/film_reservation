-- :name save-reservation! :! :n
-- :doc creates a reservation
INSERT INTO reservation
(first_name, last_name, email, date, number_of_seat, cinema, id_film)
VALUES (:first_name, :last_name, :email, :date, :number_of_seat, :cinema, :id_film)

-- :name get-reservations :?
-- :doc selects all reservations
SELECT *,(r.number_of_seat*f.price) as suma from reservation as r JOIN film as f ON r.id_film=f.id_film

-- :name get-reservation :? :1
SELECT *
FROM reservation as r JOIN film as f ON r.id_film=f.id_film
WHERE id_reservation = :id_reservation

-- :name update-reservation! :! :1
-- :doc update an existing reservation
UPDATE reservation SET
first_name = :first_name,
last_name = :last_name,
email = :email,
date = :date,
number_of_seat = :number_of_seat,
cinema = :cinema,
id_film = :id_film
WHERE id_reservation = :id_reservation

-- :name delete-reservation :! :n
DELETE FROM reservation
WHERE id_reservation = :id_reservation

-- :name get-user :? :1
SELECT * FROM user WHERE username = :username AND password = :password

-- :name get-films :?
-- :doc selects all films
SELECT * FROM film

-- :name get-film :? :1
SELECT * FROM film WHERE id_film = :id_film