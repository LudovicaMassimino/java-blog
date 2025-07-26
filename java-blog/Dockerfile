# Usa un'immagine base con Java 17 JDK
FROM openjdk:17-jdk-slim

# Cartella di lavoro dentro il container
WORKDIR /app

# Copia il jar costruito da Maven dentro il container
COPY target/java-blog-0.0.1-SNAPSHOT.jar app.jar

RUN ls -l /app


# Espone la porta 8080 (default di Spring Boot)
EXPOSE 8080

# Comando per avviare l'app
ENTRYPOINT ["java","-jar","app.jar"]
