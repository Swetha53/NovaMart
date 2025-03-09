# Use an official Java runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the compiled .jar file from the target folder (you can adjust the path if necessary)
COPY target/user-service-0.0.1-SNAPSHOT.jar /app/user-service-0.0.1-SNAPSHOT.jar

# Set environment variables for MongoDB and Kafka connection
ENV SPRING_DATA_MONGODB_URI=mongodb://root:password@mongodb:27017/user-service
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092

# Expose the port that the application will run on
EXPOSE 8085

# Run the application
ENTRYPOINT ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]