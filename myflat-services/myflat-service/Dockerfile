# Use the official Maven image with a tag specifying JDK 17
FROM maven:3.8.4-openjdk-17

# Copy the local code to the container
COPY . /usr/src/myapp

# Set the working directory
WORKDIR /usr/src/myapp

# Use Maven to build the application.
# The 'dependency:go-offline' command is used to cache the dependencies and avoid redownloading them every build.
RUN mvn dependency:go-offline

# Build your application
RUN mvn package

# The application's jar file will be produced in the 'target' directory. 
# Use the 'java -jar' command to run the jar file
# The jar file name you can find in your pom.xml
CMD ["java", "-jar", "target/carRental-0.0.1-SNAPSHOT.jar"]
