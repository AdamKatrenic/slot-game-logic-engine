# Amazon Corretto je stávka na istotu v enterprise prostredí
FROM amazoncorretto:25-alpine

WORKDIR /app

# Uisti sa, že názov JAR súboru súhlasí s tým, čo máš v target/
# Ak sa volá slot-logic-engine-1.0-SNAPSHOT.jar, prepíš to tu:
COPY target/*.jar app.jar
COPY src/main/resources/config.properties config.properties

ENTRYPOINT ["java", "-jar", "app.jar"]