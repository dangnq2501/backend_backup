FROM ubuntu:latest
LABEL authors="dang"


ENTRYPOINT ["top", "-b"]
FROM openjdk:22

ARG JAR_FILE=ecommerce-backend/target/*.jar

COPY ${JAR_FILE} backend-ecommerce.jar

ENTRYPOINT ["java", "-jar", "/backend-ecommerce.jar"]

EXPOSE 8080
