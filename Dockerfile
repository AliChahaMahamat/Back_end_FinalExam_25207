# Use an official OpenJDK image
FROM openjdk:21-jdk-slim as builder

# Set working directory
WORKDIR /app

# Copy Maven wrapper and pom.xml
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Copy source code
COPY src src

# Build the application (creates JAR file in target directory)
RUN ./mvnw clean package -DskipTests

# Final image for running the application
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/Back_End_Banking_System-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8083
EXPOSE 8083

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
