#!/bin/bash

echo "🚀 Starting Java Checkpoint Trainer Setup..."

# Check if Docker is running
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker first."
    exit 1
fi

echo "✅ Docker is running"

# Check if the checkpoint-java directory exists
CHECKPOINT_DIR="/Users/ToftD/Desktop/checkpoint/checkpoint-java"
if [ ! -d "$CHECKPOINT_DIR" ]; then
    echo "❌ Checkpoint directory not found at: $CHECKPOINT_DIR"
    echo "Please update the path in backend/src/main/resources/application.properties"
    exit 1
fi

echo "✅ Checkpoint directory found"

# Test Docker image
echo "🐳 Testing Docker image..."
if docker run --rm ghcr.io/01-edu/test-java echo "Docker test successful" > /dev/null 2>&1; then
    echo "✅ Docker image is accessible"
else
    echo "❌ Cannot access Docker image. Please check your internet connection."
    echo "   Image: ghcr.io/01-edu/test-java"
fi

echo ""
echo "📋 Setup Summary:"
echo "   - Backend: Spring Boot (Port 8080)"
echo "   - Frontend: Angular (Port 4200)"
echo "   - Checkpoint Path: $CHECKPOINT_DIR"
echo ""
echo "🎯 Next Steps:"
echo "   1. Start the backend: cd backend && ./mvnw spring-boot:run"
echo "   2. Start the frontend: cd frontend && npm install && ng serve"
echo "   3. Open http://localhost:4200 in your browser"
echo ""
echo "✨ Happy coding!"