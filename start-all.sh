#!/bin/bash

# Start script for Java Checkpoint Trainer
# Starts both backend and frontend servers

echo "🚀 Starting Java Checkpoint Trainer..."
echo ""

# Check Docker
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker is not running. Please start Docker Desktop first."
    exit 1
fi
echo "✅ Docker is running"

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo ""
echo "🔧 Starting Backend on http://localhost:8080..."
cd "$SCRIPT_DIR/backend"

# Start backend in background
./mvnw spring-boot:run > backend.log 2>&1 &
BACKEND_PID=$!
echo "   Backend PID: $BACKEND_PID"

# Wait for backend to start
echo "   Waiting for backend to start..."
for i in {1..30}; do
    if curl -s http://localhost:8080/api/exercises > /dev/null 2>&1; then
        echo "✅ Backend started successfully"
        break
    fi
    if [ $i -eq 30 ]; then
        echo "❌ Backend failed to start within 30 seconds"
        echo "   Check backend.log for details"
        kill $BACKEND_PID 2>/dev/null
        exit 1
    fi
    sleep 1
done

cd "$SCRIPT_DIR"
echo ""
echo "🎨 Starting Frontend on http://localhost:4200..."
cd "$SCRIPT_DIR/frontend"

# Check if node_modules exists
if [ ! -d "node_modules" ]; then
    echo "📦 Installing dependencies..."
    npm install
fi

# Start frontend in background
npm start > frontend.log 2>&1 &
FRONTEND_PID=$!
echo "   Frontend PID: $FRONTEND_PID"

cd "$SCRIPT_DIR"

echo ""
echo "✅ Both servers started successfully!"
echo ""
echo "📝 Server Information:"
echo "   Backend:  http://localhost:8080/api"
echo "   Frontend: http://localhost:4200"
echo ""
echo "   Backend PID:  $BACKEND_PID"
echo "   Frontend PID: $FRONTEND_PID"
echo ""
echo "💡 Tips:"
echo "   - Open http://localhost:4200 in your browser"
echo "   - Press Ctrl+C to stop both servers"
echo "   - Logs: backend/backend.log and frontend/frontend.log"
echo ""

# Save PIDs for cleanup
echo $BACKEND_PID > .backend.pid
echo $FRONTEND_PID > .frontend.pid

# Function to cleanup on exit
cleanup() {
    echo ""
    echo "🛑 Stopping servers..."
    
    if [ -f .backend.pid ]; then
        BACKEND_PID=$(cat .backend.pid)
        kill $BACKEND_PID 2>/dev/null
        echo "   Stopped backend (PID: $BACKEND_PID)"
        rm .backend.pid
    fi
    
    if [ -f .frontend.pid ]; then
        FRONTEND_PID=$(cat .frontend.pid)
        kill $FRONTEND_PID 2>/dev/null
        # Also kill any npm/ng processes
        pkill -P $FRONTEND_PID 2>/dev/null
        echo "   Stopped frontend (PID: $FRONTEND_PID)"
        rm .frontend.pid
    fi
    
    echo "👋 Servers stopped"
    exit 0
}

# Trap Ctrl+C and cleanup
trap cleanup INT TERM

# Keep script running
echo "⏳ Servers running... Press Ctrl+C to stop"
wait
