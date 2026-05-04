# --------------------------------------------------
# Estágio 1: Builder (Cache de dependências e compilação)
# --------------------------------------------------
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Copia os arquivos de configuração do Maven
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Copia os POMs dos sub-módulos para baixar dependências
COPY encontre-me-domain/pom.xml encontre-me-domain/
COPY encontre-me-application/pom.xml encontre-me-application/
COPY encontre-me-infrastructure/pom.xml encontre-me-infrastructure/

# Baixa as dependências e faz cache das camadas
RUN ./mvnw dependency:go-offline -B

# Copia os códigos-fonte de todos os módulos
COPY encontre-me-domain/src encontre-me-domain/src
COPY encontre-me-application/src encontre-me-application/src
COPY encontre-me-infrastructure/src encontre-me-infrastructure/src

# Compila e empacota todos os módulos ignorando os testes
RUN ./mvnw clean package -DskipTests

# --------------------------------------------------
# Estágio 2: Runner (Execução da aplicação)
# --------------------------------------------------
FROM eclipse-temurin:21-jre-alpine AS runner
WORKDIR /app

# Copia apenas o JAR gerado no módulo de infraestrutura (onde fica a classe Main do Spring Boot)
COPY --from=builder /app/encontre-me-infrastructure/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
