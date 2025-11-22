package com.checkpoint.trainer.model;

import java.util.List;

public class Exercise {
    private String name;
    private String level;
    private String readmeContent;
    private List<String> javaFiles;
    private String description;
    private boolean hasTests;
    
    public Exercise() {}
    
    public Exercise(String name, String level) {
        this.name = name;
        this.level = level;
    }
    
    // Getters and setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    
    public String getReadmeContent() { return readmeContent; }
    public void setReadmeContent(String readmeContent) { this.readmeContent = readmeContent; }
    
    public List<String> getJavaFiles() { return javaFiles; }
    public void setJavaFiles(List<String> javaFiles) { this.javaFiles = javaFiles; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public boolean isHasTests() { return hasTests; }
    public void setHasTests(boolean hasTests) { this.hasTests = hasTests; }
}