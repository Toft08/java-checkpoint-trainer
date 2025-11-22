#!/bin/bash

# Stop script for Java Checkpoint Trainer
# Stops both backend and frontend servers

echo "🛑 Stopping Java Checkpoint Trainer..."

# Get the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

STOPPED=0

# Stop backend
if [ -f .backend.pid ]; then
    BACKEND_PID=$(cat .backend.pid)
    if ps -p $BACKEND_PID > /dev/null 2>&1; then
        kill $BACKEND_PID 2>/dev/null
        echo "✅ Stopped backend (PID: $BACKEND_PID)"
        STOPPED=1
    fi
    rm .backend.pid
fi

# Stop frontend
if [ -f .frontend.pid ]; then
    FRONTEND_PID=$(cat .frontend.pid)
    if ps -p $FRONTEND_PID > /dev/null 2>&1; then
        kill $FRONTEND_PID 2>/dev/null
        # Also kill any npm/ng processes
        pkill -P $FRONTEND_PID 2>/dev/null
        echo "✅ Stopped frontend (PID: $FRONTEND_PID)"
        STOPPED=1
    fi
    rm .frontend.pid
fi

# Try to find and kill by port if PID files don't exist
if [ $STOPPED -eq 0 ]; then
    echo "No PID files found, searching by port..."
    
    # Kill process on port 8080 (backend)
    BACKEND_PORT_PID=$(lsof -ti:8080)
    if [ ! -z "$BACKEND_PORT_PID" ]; then
        kill $BACKEND_PORT_PID 2>/dev/null
        echo "✅ Stopped backend on port 8080 (PID: $BACKEND_PORT_PID)"
        STOPPED=1
    fi
    
    # Kill process on port 4200 (frontend)
    FRONTEND_PORT_PID=$(lsof -ti:4200)
    if [ ! -z "$FRONTEND_PORT_PID" ]; then
        kill $FRONTEND_PORT_PID 2>/dev/null
        echo "✅ Stopped frontend on port 4200 (PID: $FRONTEND_PORT_PID)"
        STOPPED=1
    fi
fi

if [ $STOPPED -eq 0 ]; then
    echo "ℹ️  No running servers found"
else
    echo "👋 All servers stopped"
fi
