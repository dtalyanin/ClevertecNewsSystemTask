FROM eclipse-temurin:17-jdk
COPY ./build/libs/*.jar news.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/news.jar"]