FROM openjdk:17-jdk

CMD ["./gradlew", "clean", "build", "-x", "test"]

VOLUME /chat

ARG JAR_FILE=build/libs/kmu-toktok-webflux-0.0.1-SNAPSHOT.war

COPY ${JAR_FILE} chat.jar

EXPOSE 8000

ENTRYPOINT ["java", "-jar", "/chat.jar"]