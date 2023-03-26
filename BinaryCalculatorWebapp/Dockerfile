FROM eclipse-temurin:17-jdk-alpine
VOLUME /tmp
COPY target/*.war app.war
ENTRYPOINT ["java","-jar","/app.war"]