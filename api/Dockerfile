FROM registry.access.redhat.com/ubi8/openjdk-17:1.16-1
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} api.jar
EXPOSE 8084
ENTRYPOINT ["java", "-jar", "api.jar"]