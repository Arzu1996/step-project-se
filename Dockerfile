FROM openjdk:11.0-jdk-slim
EXPOSE 8080 8081
COPY build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
