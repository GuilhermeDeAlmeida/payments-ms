FROM openjdk:17-oracle
EXPOSE 8080
ADD /target/payments-ms-0.0.1-SNAPSHOT.jar payments-ms.jar
ENTRYPOINT ["java", "-jar", "payments-ms.jar"]