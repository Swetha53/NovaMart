# Use an official Java runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the compiled .jar file from the target folder (you can adjust the path if necessary)
COPY target/search-service-0.0.1-SNAPSHOT.jar /app/search-service-0.0.1-SNAPSHOT.jar

# Set environment variables for Elasticsearch and Kafka connection
ENV SPRING_DATA_ELASTICSEARCH_CLUSTER_NODES=elasticsearch:9200
ENV SPRING_KAFKA_BOOTSTRAP_SERVERS=kafka:29092

# Expose the port that the application will run on
EXPOSE 8086

# Run the application
ENTRYPOINT ["java", "-jar", "search-service-0.0.1-SNAPSHOT.jar"]