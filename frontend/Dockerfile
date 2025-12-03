# Build stage
FROM node:24-alpine AS build

WORKDIR /app

# Copy package files first for dependency caching
COPY package*.json ./

# Install dependencies
RUN npm ci

# Copy source code
COPY . .

# Build the Angular application
RUN npm run build

# Runtime stage - nginx for serving static files
FROM nginx:1.27-alpine

# Copy built Angular app to nginx html directory
COPY --from=build /app/dist/checkpoint-trainer-frontend /usr/share/nginx/html

# Copy custom nginx configuration if needed
# COPY nginx.conf /etc/nginx/nginx.conf

# Expose port 80
EXPOSE 80

# Nginx will start automatically
CMD ["nginx", "-g", "daemon off;"]
