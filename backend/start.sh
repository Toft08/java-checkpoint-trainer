#!/bin/bash

echo "🔥 Starting Java Checkpoint Trainer Backend..."

# Navigate to backend directory
cd "$(dirname "$0")"

# Check if Maven wrapper exists
if [ ! -f "./mvnw" ]; then
    echo "📦 Maven wrapper not found. Creating one..."
    mvn -N wrapper:wrapper
fi

# Make Maven wrapper executable
chmod +x mvnw

# Start the Spring Boot application
echo "🚀 Starting Spring Boot application on port 8080..."
./mvnw spring-boot:run