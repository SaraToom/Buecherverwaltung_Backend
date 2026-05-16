FROM eclipse-temurin:25-jdk AS build
COPY . /app
WORKDIR /app
RUN chmod +x ./gradlew
RUN ./gradlew build -x test --no-daemon

FROM eclipse-temurin:25-jdk
EXPOSE 8080
RUN mkdir /app
COPY --from=build /app/build/libs/*.jar /app/spring-boot-application.jar
ENTRYPOINT ["java", "-jar", "/app/spring-boot-application.jar"]