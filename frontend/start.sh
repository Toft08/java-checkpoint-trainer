#!/bin/bash

echo "🎨 Starting Java Checkpoint Trainer Frontend..."

# Navigate to frontend directory
cd "$(dirname "$0")"

# Check if node_modules exists
if [ ! -d "node_modules" ]; then
    echo "📦 Installing npm dependencies..."
    npm install
fi

# Check if Angular CLI is available
if ! command -v ng &> /dev/null; then
    echo "📦 Installing Angular CLI globally..."
    npm install -g @angular/cli
fi

# Start the Angular development server
echo "🚀 Starting Angular development server on port 4200..."
ng serve --open