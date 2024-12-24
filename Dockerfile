# Use an OpenJDK image as the base
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/Back_End_Banking_System-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port your app will run on
EXPOSE 8083

# Add a health check (optional but recommended)
HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --retries=3 \
  CMD curl --fail http://localhost:8083/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
