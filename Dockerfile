# We select the base image from. Locally available or from https://hub.docker.com/
FROM openjdk:8-jdk-alpine
RUN apk add --no-cache bash
WORKDIR /ktorProjects/CoreApi
CMD DRIVER=org.postgresql.Driver URL=jdbc:postgresql://database:5432/coreapi PSQL_PASSWORD=postgres PSQL_USER=postgres ./gradlew run