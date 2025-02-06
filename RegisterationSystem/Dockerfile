
FROM openjdk:17.0.2-slim

WORKDIR /app


COPY target/RegisterationSystem-0.0.1-SNAPSHOT.jar app.jar

COPY .env .env

EXPOSE 9090


ENTRYPOINT ["java", "-jar", "app.jar"]
