#FROM eclipse-temurin:17-jdk-alpine AS build
#COPY mvnw .
#COPY .mvn .mvn
#COPY pom.xml .
#COPY src src
##RUN ./mvnw -Dtest=AnimeApplicationTests test
#RUN echo '--- done with tests ---'
#RUN ./mvnw spring-boot:build-image

FROM eclipse-temurin:17-jdk-alpine as builder
COPY src /app/src
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