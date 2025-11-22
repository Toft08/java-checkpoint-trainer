package com.checkpoint.trainer.model;

import java.util.Map;

public class ExerciseSubmission {
    private String exerciseName;
    private String level;
    private Map<String, String> files; // filename -> content
    
    public ExerciseSubmission() {}
    
    public ExerciseSubmission(String exerciseName, String level, Map<String, String> files) {
        this.exerciseName = exerciseName;
        this.level = level;
        this.files = files;
    }
    
    // Getters and setters
    public String getExerciseName() { return exerciseName; }
    public void setExerciseName(String exerciseName) { this.exerciseName = exerciseName; }
    
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    
    public Map<String, String> getFiles() { return files; }
    public void setFiles(Map<String, String> files) { this.files = files; }
}