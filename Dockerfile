# Dockerfile for Spring Boot with Java 21 and Maven

# Etapa de build usando Maven e JDK 21
FROM maven:3.9.5-eclipse-temurin-21 AS builder
WORKDIR /app

# Copia o arquivo pom.xml e faz download das dependências
COPY pom.xml ./ 
RUN mvn dependency:go-offline

# Copia o código-fonte do projeto e compila
COPY . .
RUN mvn package -DskipTests

# Etapa final com JRE 21 para execução
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copia o artefato gerado na etapa anterior
COPY --from=builder /app/target/*.jar app.jar


# Expondo a porta padrão do Spring Boot
EXPOSE 8080

# Comando de execução da aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]