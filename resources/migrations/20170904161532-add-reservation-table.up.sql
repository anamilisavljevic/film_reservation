CREATE TABLE reservation
(id_reservation INTEGER PRIMARY KEY AUTO_INCREMENT,
first_name VARCHAR(30),
last_name VARCHAR(30),
email VARCHAR(30),
date DATE,
number_of_seat INTEGER,
cinema VARCHAR(30),
id_film INTEGER,
CONSTRAINT FK_FilmID FOREIGN KEY (id_film) REFERENCES film(id_film)
);