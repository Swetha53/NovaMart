# Use an official Java runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the compiled .jar file from the target folder (you can adjust the path if necessary)
COPY target/order-service-0.0.1-SNAPSHOT.jar /app/order-service-0.0.1-SNAPSHOT.jar

# Set environment variables for MongoDB and Kafka connection
ENV SPRING_DATA_MONGODB_URI=jdbc:mysql://localhost:3306/order_service
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092

# Expose the port that the application will run on
EXPOSE 8081

# Run the application
ENTRYPOINT ["java", "-jar", "order-service-0.0.1-SNAPSHOT.jar"]