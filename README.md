# Java Checkpoint Trainer

A full-stack web application for training on Java checkpoint exercises with integrated Docker testing.

## Features

- Real Docker test execution using official 01-edu test images
- Code editor with Java syntax highlighting
- Markdown-rendered exercise instructions
- 25+ exercises across 4 difficulty levels (G1-G4)
- Progress tracking with local storage

## Architecture

- Frontend: Angular 17 with TypeScript
- Backend: Spring Boot 3 with Java 17+
- Testing: Docker integration with ghcr.io/01-edu/test-java

## Prerequisites

### Option 1: Docker (Recommended - Easiest)

- Docker Desktop (running)

That's it! No need for Java, Node, or Maven installations.

### Option 2: Local Development

- Java 17 or higher
- Node.js 18+ and npm
- Docker Desktop (running)
- Maven (included via mvnw wrapper)

## Project Structure

```
java-checkpoint-trainer/
├── backend/              # Spring Boot application
│   ├── src/
│   │   └── main/
│   │       ├── java/     # Controllers, Services, Models
│   │       └── resources/
│   │           └── application.properties
│   ├── mvnw              # Maven wrapper (no Maven install needed)
│   └── pom.xml
├── frontend/             # Angular application
│   ├── src/
│   │   └── app/
│   │       ├── components/
│   │       └── services/
│   └── package.json
├── g1/                   # Level 1 exercises (Fundamentals)
├── g2/                   # Level 2 exercises (Intermediate)
├── g3/                   # Level 3 exercises (Advanced)
├── g4/                   # Level 4 exercises (Expert)
└── test.sh              # Local test script
```

## Quick Start

### Using Docker (Recommended)

```bash
# Build and start everything with one command
docker-compose up --build

# Open http://localhost:4200 in your browser

# Stop with Ctrl+C, or in another terminal:
docker-compose down
```

### Using Shell Scripts (Local Development)

```bash
# Build and start both servers
./build-all.sh
./start-all.sh

# Open http://localhost:4200 in your browser

# Stop servers when done
./stop-all.sh
```

### Manual Start (Local Development)

```bash
# Backend
cd backend
./mvnw spring-boot:run

# Frontend (in separate terminal)
cd frontend
npm install
npm start
```

Open http://localhost:4200

## Testing

### Web Interface

1. Select an exercise
2. Write your solution
3. Click "Run Tests"
4. View results

### Command Line

```bash
./test.sh AgeFinder
```

All tests run using the official 01-edu Docker image.

## Exercises Included

### G1 - Fundamentals

- AgeFinder
- DayOfWeek
- MonthlyPeriod
- MultiplicationTable
- TimeTracker
- TodoList

### G2 - Intermediate

- AlmostPalindrome
- BreakdownURL
- ConfigProtector
- FactorialMaster
- Flexisort
- HTMLValidator
- NextPrime

### G3 - Advanced

- BuilderBlueprint
- CircularLinkedList
- DoubleLinkedList
- FactoryBlueprint
- SingleLinkedList
- SingletonBlueprint

### G4 - Expert

- DistinctSubstringLength
- FirstUnique
- HarmoniousFusion
- IsAnagram
- LongestCommonPrefix
- TopFrequents

## Scripts

- `./build-all.sh` - Build both backend and frontend
- `./start-all.sh` - Start both servers
- `./stop-all.sh` - Stop all running servers
- `./test.sh <ExerciseName>` - Test exercises locally

## Configuration

Backend: `backend/src/main/resources/application.properties`
Frontend: `frontend/src/environments/environment.ts`

## Deployment

### Docker Deployment (Production)

```bash
# Build and run with Docker Compose
docker-compose up -d

# View logs
docker-compose logs -f

# Stop
docker-compose down
```

### Traditional Deployment

This application requires a server environment with Docker. It cannot be deployed as a static site.

Deployment options:

- Docker Compose: Easiest, recommended for production
- Kubernetes: For scalable deployments
- Local development: Use the included scripts
- Server deployment: Deploy both Spring Boot backend and Angular frontend to a server with Docker installed

## Troubleshooting

**Docker**: "Cannot connect to Docker daemon"

- Ensure Docker Desktop is running

**Docker Compose**: "Port already in use"

- Stop conflicting services: `docker-compose down`
- Change ports in `docker-compose.yml` if needed

**Scripts**: "Permission denied"

- Run `chmod +x *.sh`

**Backend**: "Port 8080 already in use"

- Change `server.port` in application.properties or kill the process
- With Docker: `docker-compose down` and restart

**Frontend**: "Cannot connect to backend"

- Ensure backend is running on port 8080
- With Docker: Check `docker-compose logs backend`

**Maven Wrapper**: "mvnw not found"

- The Maven wrapper files should be committed to the repository
- If missing, run: `mvn wrapper:wrapper` in the backend directory

## About This Project

This project was created by a student for personal learning and training
purposes. It includes tools and helpers for practicing the Java checkpoint
exercises from 01 Edu System.

The exercises and related materials belong to 01 Edu System and are only used
here for educational purposes. I do not claim ownership of their content.

This repository is not intended for commercial use.
