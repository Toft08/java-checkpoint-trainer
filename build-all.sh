#!/bin/bash

# Build script for Java Checkpoint Trainer
# Compiles both backend and frontend

set -e  # Exit on error

echo "🔨 Building Java Checkpoint Trainer..."
echo ""

# Check prerequisites
echo "📋 Checking prerequisites..."

# Check Java
if ! command -v java &> /dev/null; then
    echo "❌ Java not found. Please install Java 17 or higher."
    exit 1
fi
echo "✅ Java found: $(java -version 2>&1 | head -n 1)"

# Check Node
if ! command -v node &> /dev/null; then
    echo "❌ Node.js not found. Please install Node.js 18 or higher."
    exit 1
fi
echo "✅ Node.js found: $(node --version)"

# Check Docker
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker Desktop."
    exit 1
fi
echo "✅ Docker is running"

echo ""
echo "🏗️  Building Backend..."
cd backend

# Use mvnw if available, otherwise use system maven
if [ -f "./mvnw" ]; then
    ./mvnw clean package -DskipTests
elif command -v mvn &> /dev/null; then
    mvn clean package -DskipTests
else
    echo "❌ Maven not found. Please install Maven or generate Maven wrapper."
    exit 1
fi

if [ $? -eq 0 ]; then
    echo "✅ Backend built successfully"
else
    echo "❌ Backend build failed"
    exit 1
fi

cd ..
echo ""
echo "🏗️  Building Frontend..."
cd frontend

# Install dependencies if node_modules doesn't exist
if [ ! -d "node_modules" ]; then
    echo "📦 Installing dependencies..."
    npm install
fi

npm run build
if [ $? -eq 0 ]; then
    echo "✅ Frontend built successfully"
else
    echo "❌ Frontend build failed"
    exit 1
fi

cd ..
echo ""
echo "🎉 Build completed successfully!"
echo ""
echo "💡 To start the application, run: ./start-all.sh"
