# Use an official OpenJDK runtime as a parent image
FROM openjdk:21-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the target directory to the container
COPY target/Back_End_Banking_System-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the correct port (8083)
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
