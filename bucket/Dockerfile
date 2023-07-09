FROM registry.access.redhat.com/ubi8/openjdk-17:1.16-1
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} storage.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "storage.jar"]