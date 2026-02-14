FROM eclipse-temurin:17-jdk
WORKDIR /app
# allow overriding which jar to copy (useful if version changes)
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]
