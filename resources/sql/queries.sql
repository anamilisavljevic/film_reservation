-- :name save-reservation! :! :n
-- :doc creates a reservation
INSERT INTO reservation
(first_name, last_name, email, date, number_of_seat, cinema, film)
VALUES (:first_name, :last_name, :email, :date, :number_of_seat, :cinema, :film)

-- :name get-reservations :?
-- :doc selects all reservations
SELECT * from reservation

-- :name get-reservation :? :1
SELECT *
FROM reservation
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
film = :film
WHERE id_reservation = :id_reservation

-- :name delete-reservation :! :n
DELETE FROM reservation
WHERE id_reservation = :id_reservation
