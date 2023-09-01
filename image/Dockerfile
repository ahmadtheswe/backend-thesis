FROM openjdk:17-oracle
EXPOSE 9090
RUN mkdir -p /app/
ADD build/libs/image-0.0.1-SNAPSHOT.jar /app/image.jar
ENTRYPOINT ["java", "-jar", "/app/image.jar"]