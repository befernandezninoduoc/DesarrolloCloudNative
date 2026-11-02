FROM maven:3.9-eclipse-temurin-21 AS buildstage
WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=buildstage /app/target/TicketMaster-0.0.1-SNAPSHOT.jar ./app.jar
EXPOSE 8081
CMD ["java", "-jar", "/app/app.jar"]
