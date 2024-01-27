FROM openjdk:17-slim
COPY target/fooddelivery-1.0-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]