# 示例 Dockerfile
FROM openjdk:22-jdk-slim
VOLUME /tmp
COPY target/msgserv-0.0.1.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
