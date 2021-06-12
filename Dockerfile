FROM maven:3.8-openjdk-8 as builder
WORKDIR /app
COPY . .
RUN mvn clean package

FROM openjdk:8-jdk-alpine
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]