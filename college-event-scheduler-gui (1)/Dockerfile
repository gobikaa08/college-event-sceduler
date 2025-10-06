FROM eclipse-temurin:17-jdk-alpine AS build
WORKDIR /app
COPY . /app
RUN mvn -B package -DskipTests

FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/college-event-scheduler-gui-1.0.0-jar-with-dependencies.jar app.jar
VOLUME /app
ENTRYPOINT ["java","-jar","/app/app.jar"]