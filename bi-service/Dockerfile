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

ENV JAR_VERSION=${APP_VERSION}

CMD java -jar bi-service-${JAR_VERSION}.jar