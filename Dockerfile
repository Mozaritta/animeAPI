FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

FROM eclipse-temurin:17-jdk-alpine as builder
COPY . /app
WORKDIR /app
ARG JAR_FILE=/app/target/*.jar
RUN ls
COPY ${JAR_FILE} app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM eclipse-temurin:17-jdk-alpine
COPY . /app
WORKDIR /app
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/spring-boot-loader ./
COPY --from=builder /app/snapshot-dependencies ./
COPY --from=builder /app/app ./
EXPOSE 8000
ENTRYPOINT ["java", "-jar", "org.springframework.boot.loader.JarLauncher"]