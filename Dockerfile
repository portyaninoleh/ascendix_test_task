FROM openjdk:21-jdk
ADD target/ascendix-app.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]