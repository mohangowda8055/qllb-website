# Build Stage
FROM maven:3.9.8-amazoncorretto-21-al2023 AS build
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

#Runtime Stage
FROM amazoncorretto:21-al2023
ARG APP_VERSION=1.0.0

WORKDIR /app
COPY --from=build /build/target/bi-service-*.jar /app/

EXPOSE 8080

ENV DB_URL=jdbc:mysql://mysql-bi-service:3306/bi_db
ENV JAR_VERSION=${APP_VERSION}

CMD java -jar -Dspring.datasource.url=${DB_URL} bi-service-${JAR_VERSION}.jar