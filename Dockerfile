FROM openjdk:17-jdk

ADD /build/libs/*.jar app.jar

USER nobody

ENTRYPOINT ["java", "-jar", "app.jar"]