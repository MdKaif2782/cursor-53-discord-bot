# Start with a base image containing Java runtime
FROM openjdk:17-jdk

# Add Maintainer Info
LABEL maintainer="MdKaif2782"

# Set the current working directory inside the image
WORKDIR /app

# Copy gradlew and gradle folder to the working directory
COPY gradlew .
COPY gradle gradle

# Give execute permission to gradlew
RUN chmod +x ./gradlew

# Copy the rest of the application to the working directory
COPY build.gradle .
COPY src src

# Build the application
RUN ./gradlew shadowJar

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "build/libs/cursor-53-discord-bot-1.0-SNAPSHOT-all.jar"]