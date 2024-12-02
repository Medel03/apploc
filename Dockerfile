FROM maven:3.9.9-eclipse-temurin-17 as build
WORKDIR /app
COPY . .
RUN mvn clean install



FROM eclipse-temurin:21.0.2_13-jdk
WORKDIR /app
COPY --from=build /app/target/location-app-1.0-SNAPSHOT.jar /app/
EXPOSE 8081
CMD ["java", "-jar","location-app-1.0-SNAPSHOT.jar"]

