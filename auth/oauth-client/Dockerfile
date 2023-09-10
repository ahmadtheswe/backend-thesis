FROM openjdk:17-oracle
EXPOSE 9095
RUN mkdir -p /app/
ADD build/libs/image-0.0.1-SNAPSHOT.jar /app/auth.jar
ENTRYPOINT ["java", "-jar", "/app/auth.jar"]