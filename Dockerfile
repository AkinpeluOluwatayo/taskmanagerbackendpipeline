# Use a lightweight OpenJDK image
FROM openjdk:17-jdk-slim

# Create a non-root user for security (DevOps best practice!)
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Set the location of the JAR file
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Start the application
ENTRYPOINT ["java", "-jar", "/app.jar"]