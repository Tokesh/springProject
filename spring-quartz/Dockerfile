FROM arm64v8/openjdk:21
VOLUME /tmp
EXPOSE 8080
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/spring-quartz-0.0.1-SNAPSHOT.jar /app/appq.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=container", "-jar", "/app/appq.jar"]