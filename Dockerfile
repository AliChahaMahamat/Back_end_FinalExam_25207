# Use an OpenJDK image as the base
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/Back_End_Banking_System-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port your app will run on
EXPOSE 8083

# Add a wait-for-it script (optional, useful for dependency readiness checks)
COPY wait-for-it.sh /app/wait-for-it.sh
RUN chmod +x /app/wait-for-it.sh

# Add a health check (optional but recommended)
HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --retries=3 \
  CMD curl --fail http://localhost:8083/actuator/health || exit 1

# Run the application with optional delay to wait for dependencies
ENTRYPOINT ["/app/wait-for-it.sh", "db:5432", "--", "java", "-jar", "/app/app.jar"]
