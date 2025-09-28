# Use a base image that contains both Java 17 and Maven
FROM maven:3.8-openjdk-17

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project into the container
COPY . .

# Run the Maven command to build the project and create the executable .jar file
RUN mvn package -DskipTests

# Expose the port that the application runs on
EXPOSE 5100

# The command to run the application when the container starts
# Note: You may need to change the .jar file name if your artifactId or version is different.
CMD ["java", "-jar", "target/order-service-0.0.1-SNAPSHOT.jar"]
