# Start with a base image containing Java runtime
FROM openjdk:17-jdk

# Add Maintainer Info
LABEL maintainer="MdKaif2782"

# Set the current working directory inside the image
WORKDIR /app

# Copy the jar file into the image
COPY build/libs/cursor-53-discord-bot-1.0-SNAPSHOT-all.jar .

# Expose the port your app runs on
EXPOSE 8080

# Run the application
ENTRYPOINT ["java","$JAVA_OPTS","-Dserver.port=$PORT", "-jar", "cursor-53-discord-bot-1.0-SNAPSHOT-all.jar"]