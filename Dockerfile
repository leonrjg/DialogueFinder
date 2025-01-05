# Build stage
FROM maven:3.8.7-openjdk-18-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mkdir -p /mnt/media/subs /mnt/media/videos /mnt/media/covers /mnt/media/clips
RUN mvn -f /home/app/pom.xml clean test package

# Package stage
FROM openjdk:18-jdk-alpine
COPY --from=build /home/app/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]