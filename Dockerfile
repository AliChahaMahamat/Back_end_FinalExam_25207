# Use an OpenJDK image as the base
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/Back_End_Banking_System-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your app will run on
EXPOSE 8083

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
