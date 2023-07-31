FROM eclipse-temurin:17-jdk-alpine as builder
COPY . /app
WORKDIR /app
ARG JAR_FILE=/app/target/*.jar
COPY ${JAR_FILE} app.jar
RUN ls /app/target
RUN ls ${JAR_FILE}
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