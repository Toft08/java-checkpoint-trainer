package com.checkpoint.trainer.service;

import com.checkpoint.trainer.model.Exercise;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ExerciseService {

    @Value("${app.checkpoint.base-path}")
    private String checkpointBasePath;
    
    private Path getBasePath() {
        // If relative path, resolve from project root
        Path basePath = Paths.get(checkpointBasePath);
        if (!basePath.isAbsolute()) {
            // Get project root (parent of backend folder)
            String projectRoot = new File(System.getProperty("user.dir")).getParent();
            basePath = Paths.get(projectRoot, checkpointBasePath);
        }
        return basePath;
    }

    public List<Exercise> getAllExercises() {
        List<Exercise> exercises = new ArrayList<>();
        Path basePath = getBasePath();
        
        for (int level = 1; level <= 4; level++) {
            String levelDir = "g" + level;
            Path levelPath = basePath.resolve(levelDir);
            
            if (Files.exists(levelPath) && Files.isDirectory(levelPath)) {
                try (Stream<Path> paths = Files.list(levelPath)) {
                    paths.filter(Files::isDirectory)
                         .forEach(exercisePath -> {
                             String exerciseName = exercisePath.getFileName().toString();
                             Exercise exercise = new Exercise(exerciseName, levelDir);
                             
                             // Load basic info
                             loadExerciseInfo(exercise, exercisePath);
                             exercises.add(exercise);
                         });
                } catch (IOException e) {
                    // Log error but continue
                    System.err.println("Error reading level directory: " + levelPath);
                }
            }
        }
        
        return exercises;
    }

    public Exercise getExercise(String level, String name) {
        Path basePath = getBasePath();
        Path exercisePath = basePath.resolve(level).resolve(name);
        
        if (!Files.exists(exercisePath) || !Files.isDirectory(exercisePath)) {
            return null;
        }
        
        Exercise exercise = new Exercise(name, level);
        loadExerciseInfo(exercise, exercisePath);
        loadExerciseFiles(exercise, exercisePath);
        
        return exercise;
    }

    public String getReadmeContent(String level, String name) {
        Path basePath = getBasePath();
        Path readmePath = basePath.resolve(level).resolve(name).resolve("README.md");
        
        if (!Files.exists(readmePath)) {
            return "No README.md found for this exercise.";
        }
        
        try {
            return Files.readString(readmePath);
        } catch (IOException e) {
            return "Error reading README.md: " + e.getMessage();
        }
    }

    private void loadExerciseInfo(Exercise exercise, Path exercisePath) {
        // Load README content
        Path readmePath = exercisePath.resolve("README.md");
        if (Files.exists(readmePath)) {
            try {
                String readmeContent = Files.readString(readmePath);
                exercise.setReadmeContent(readmeContent);
                
                // Extract description from README (first paragraph)
                String[] lines = readmeContent.split("\n");
                for (String line : lines) {
                    if (!line.trim().isEmpty() && !line.startsWith("#")) {
                        exercise.setDescription(line.trim());
                        break;
                    }
                }
            } catch (IOException e) {
                exercise.setReadmeContent("Error reading README.md");
            }
        }
        
        // All exercises have Docker tests available
        exercise.setHasTests(true);
    }

    private void loadExerciseFiles(Exercise exercise, Path exercisePath) {
        List<String> javaFiles = new ArrayList<>();
        
        try (Stream<Path> paths = Files.list(exercisePath)) {
            paths.filter(path -> path.getFileName().toString().endsWith(".java"))
                 .forEach(path -> javaFiles.add(path.getFileName().toString()));
        } catch (IOException e) {
            // Log error but continue
        }
        
        exercise.setJavaFiles(javaFiles);
    }

    public String getFileContent(String level, String exerciseName, String fileName) {
        Path basePath = getBasePath();
        Path filePath = basePath.resolve(level).resolve(exerciseName).resolve(fileName);
        
        if (!Files.exists(filePath)) {
            return "";
        }
        
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            return "// Error reading file: " + e.getMessage();
        }
    }
}