FROM openjdk:11.0.13-slim
# ARG JAR_FILE=/build/libs/Waffle-0.0.1-SNAPSHOT.jar
# COPY ${JAR_FILE} /app.jar
COPY build/libs/*.jar /app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
