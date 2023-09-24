# Use a imagem oficial do OpenJDK 17 como base
FROM maven:3-openjdk-17-slim

# Diretório de trabalho dentro do contêiner
WORKDIR /app

# Copie o arquivo JAR do seu projeto para o contêiner (certifique-se de que o arquivo existe no mesmo diretório)
COPY target/rinhasemframe-0.0.1.jar /app/rinhasemframe-0.0.1.jar

# Comando para executar a aplicação Java (ajuste o nome do arquivo JAR conforme necessário)
CMD ["java", "-jar", "rinhasemframe-0.0.1.jar"]

EXPOSE 8080