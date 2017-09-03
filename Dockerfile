FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/film_reservation.jar /film_reservation/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/film_reservation/app.jar"]
