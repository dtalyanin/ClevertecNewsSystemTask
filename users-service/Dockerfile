FROM eclipse-temurin:17-jdk
COPY ./build/libs/*.jar users.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/users.jar"]