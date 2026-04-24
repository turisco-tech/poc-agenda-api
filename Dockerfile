# Usa o Amazon Corretto 25 como base
FROM amazoncorretto:25-alpine

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o arquivo .jar gerado pelo Maven para dentro do container
COPY target/*.jar app.jar

# Expõe a porta que o Spring Boot vai rodar
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]