FROM maven:3.8-openjdk-8 AS builder
COPY pom.xml /src/bank-service/
WORKDIR /src/bank-service
RUN mvn dependency:resolve
COPY . /src/bank-service/
RUN mvn clean install

FROM openjdk:8-jdk
COPY --from=builder /src/bank-service/target/bankservice-0.0.1-SNAPSHOT.jar /app/bank-service/
WORKDIR /app/bank-service/
CMD java -jar bankservice-0.0.1-SNAPSHOT.jar
