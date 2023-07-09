FROM registry.access.redhat.com/ubi8/openjdk-17:1.16-1
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} server-eureka.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "server-eureka.jar"]