# Java Checkpoint Trainer - Development Guide

## Quick Start

1. **Run Setup Check**
   ```bash
   ./setup.sh
   ```

2. **Start Backend** (Terminal 1)
   ```bash
   cd backend
   ./start.sh
   ```

3. **Start Frontend** (Terminal 2)
   ```bash
   cd frontend
   ./start.sh
   ```

4. **Open Application**
   - Navigate to: http://localhost:4200
   - Backend API: http://localhost:8080/api

## Manual Setup

### Backend (Spring Boot)

1. **Generate Maven Wrapper** (if needed)
   ```bash
   cd backend
   mvn -N wrapper:wrapper
   ```

2. **Start Spring Boot**
   ```bash
   ./mvnw spring-boot:run
   ```

### Frontend (Angular)

1. **Install Dependencies**
   ```bash
   cd frontend
   npm install
   ```

2. **Install Angular CLI** (if needed)
   ```bash
   npm install -g @angular/cli
   ```

3. **Start Development Server**
   ```bash
   ng serve
   ```

## Configuration

### Backend Configuration
File: `backend/src/main/resources/application.properties`

- **Checkpoint Path**: Now uses relative path (.) - exercises are included in this repo
- **CORS**: Add your frontend URL to `app.cors.allowed-origins`
- **Docker**: Configure timeout and image name if needed

**Note**: The project is now self-contained. All exercises (g1, g2, g3, g4) are included in the repository root.

### Frontend Configuration
File: `frontend/src/app/exercise.service.ts`

- **API URL**: Update `baseUrl` if backend runs on different port

## API Endpoints

### Exercise Management
- `GET /api/exercises` - List all exercises
- `GET /api/exercises/{level}/{name}` - Get specific exercise
- `GET /api/exercises/{level}/{name}/readme` - Get README content
- `GET /api/exercises/{level}/{name}/files/{fileName}` - Get file content

### Testing
- `POST /api/exercises/{level}/{name}/test` - Run Docker tests

### Health Check
- `GET /api/health` - Service health status

## Features

### Current Features ✅
- Exercise browser with level grouping
- README viewer with markdown support
- Code editor with Java syntax highlighting
- Docker test integration
- Real-time test results
- File management (load templates)
- Responsive design

### Planned Features 🚧
- User authentication and progress tracking
- Code sharing and collaboration
- Hint system
- Advanced code editor features (autocomplete, error highlighting)
- Export/import functionality
- Leaderboard and statistics

## Architecture

```
┌─────────────────┐    HTTP/REST    ┌─────────────────┐
│   Angular SPA   │◄───────────────►│  Spring Boot    │
│   (Port 4200)   │                 │   (Port 8080)   │
└─────────────────┘                 └─────────────────┘
                                             │
                                             ▼
                                    ┌─────────────────┐
                                    │  Docker Engine  │
                                    │ (Test Execution)│
                                    └─────────────────┘
                                             │
                                             ▼
                                    ┌─────────────────┐
                                    │ g1/ g2/ g3/ g4/ │
                                    │   (Exercises)   │
                                    └─────────────────┘
```

## Dependencies

### Backend
- Spring Boot 3.2.0
- Docker Java API 3.3.4
- FlexMark (Markdown processing)
- Commons IO & Lang3

### Frontend
- Angular 17
- Angular Material
- Monaco Editor (VS Code editor)
- RxJS

## Development Tips

### Testing the Docker Integration
```bash
# Test manually
docker run --rm -e EXERCISE=AgeFinder -v "$(pwd)":/app/student -w /app ghcr.io/01-edu/test-java
```

### Debugging Backend
- Check logs for Docker execution errors
- Verify checkpoint directory path
- Ensure Docker image is accessible

### Debugging Frontend
- Check browser console for API errors
- Verify CORS configuration
- Check Monaco Editor loading

## Deployment

### Production Build (Frontend)
```bash
cd frontend
ng build --configuration production
```

### Docker Deployment (Backend)
```bash
cd backend
./mvnw spring-boot:build-image
```

## Troubleshooting

### Common Issues

1. **Docker not found**
   - Ensure Docker Desktop is running
   - Check Docker installation

2. **CORS errors**
   - Update `app.cors.allowed-origins` in application.properties
   - Restart backend service

3. **Monaco Editor not loading**
   - Check browser console for asset loading errors
   - Verify Angular build configuration

4. **Checkpoint directory not found**
   - Update `app.checkpoint.base-path` in application.properties
   - Ensure path uses forward slashes

### Getting Help

1. Check the application logs
2. Verify all prerequisites are met
3. Review the setup script output
4. Check API endpoints with browser or Postman

## Contributing

1. Create feature branch
2. Make changes
3. Test thoroughly
4. Create pull request

## License

This project is for educational purposes as part of the checkpoint training system.