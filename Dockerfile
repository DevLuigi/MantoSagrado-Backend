FROM openjdk:22-jdk

WORKDIR /app

COPY target/manto-sagrado-1.0.jar /app/manto-sagrado.jar

EXPOSE 8080

CMD ["java", "-jar", "manto-sagrado.jar"]