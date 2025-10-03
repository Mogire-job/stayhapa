# Use a lightweight OpenJDK 21 image
FROM eclipse-temurin:21-jdk-alpine

# Set working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR (assumes you build it first)
COPY target/stayhapa-backend.jar /app/stayhapa-backend.jar

# Copy the JKS keystore into the image
COPY stayhapa.jks /app/stayhapa.jks

# Expose ports for HTTP and HTTPS
EXPOSE 8080 8443

# Run the Spring Boot app with SSL enabled
ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom \
 -Dserver.ssl.key-store=/app/stayhapa.jks \
 -Dserver.ssl.key-store-password=your_keystore_password \
 -Dserver.ssl.key-alias=your_key_alias \
 -jar /app/stayhapa-backend.jar"]
