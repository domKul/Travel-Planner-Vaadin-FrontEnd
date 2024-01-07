# Etap budowania
FROM gradle:7.3.3-jdk17 AS build

WORKDIR /app

COPY . .

# Kopiowanie wrappera Gradle
COPY gradlew .

# Uruchomienie builda
RUN ./gradlew build

# Etap uruchamiania
FROM openjdk:17-alpine

WORKDIR /app

# Skopiowanie zbudowanego pliku JAR z etapu budowania
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
