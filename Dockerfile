FROM openjdk:11
ADD target/ServiceB-0.0.1-SNAPSHOT.jar ServiceB.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "ServiceB.jar"]