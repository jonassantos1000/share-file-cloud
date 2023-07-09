FROM registry.access.redhat.com/ubi8/openjdk-17:1.16-1
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} gateway.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "gateway.jar"]