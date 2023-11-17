FROM maven:3.9.5-amazoncorretto-11 AS builder
COPY pom.xml /app/
COPY src /app/src
RUN --mount=type=cache,target=/root/.m2 mvn -f /app/pom.xml clean package -DskipTests

FROM openjdk:11
COPY --from=builder  /app/target/service_b.jar service_b.jar
ADD https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar .
ENV JAVA_TOOL_OPTIONS="-javaagent:./opentelemetry-javaagent.jar"
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "service_b.jar"]