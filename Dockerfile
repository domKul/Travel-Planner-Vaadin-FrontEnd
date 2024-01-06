## Etap 1: Budowanie aplikacji
FROM gradle:7.3.3-jdk17 AS build
WORKDIR /app
COPY build.gradle settings.gradle ./
COPY src ./src
RUN gradle build
