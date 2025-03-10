# Use an official Java runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the compiled .jar file from the target folder (you can adjust the path if necessary)
COPY target/notification-service-0.0.1-SNAPSHOT.jar /app/notification-service-0.0.1-SNAPSHOT.jar

# Set environment variables for MongoDB and Kafka connection
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092

# Expose the port that the application will run on
EXPOSE 8087

# Run the application
ENTRYPOINT ["java", "-jar", "notification-service-0.0.1-SNAPSHOT.jar"]