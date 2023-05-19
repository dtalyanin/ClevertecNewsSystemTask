FROM eclipse-temurin:17-jdk
COPY ./build/libs/*.jar configs.jar
EXPOSE 8888
ENTRYPOINT ["java", "-jar", "/configs.jar"]