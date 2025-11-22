package com.checkpoint.trainer.model;

import java.time.LocalDateTime;
import java.util.List;

public class TestResult {
    private boolean success;
    private String output;
    private String errorOutput;
    private List<String> compilationErrors;
    private List<String> testFailures;
    private int exitCode;
    private LocalDateTime timestamp;
    private long executionTimeMs;
    
    public TestResult() {
        this.timestamp = LocalDateTime.now();
    }
    
    public TestResult(boolean success, String output) {
        this();
        this.success = success;
        this.output = output;
    }
    
    // Getters and setters
    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    
    public String getOutput() { return output; }
    public void setOutput(String output) { this.output = output; }
    
    public String getErrorOutput() { return errorOutput; }
    public void setErrorOutput(String errorOutput) { this.errorOutput = errorOutput; }
    
    public List<String> getCompilationErrors() { return compilationErrors; }
    public void setCompilationErrors(List<String> compilationErrors) { this.compilationErrors = compilationErrors; }
    
    public List<String> getTestFailures() { return testFailures; }
    public void setTestFailures(List<String> testFailures) { this.testFailures = testFailures; }
    
    public int getExitCode() { return exitCode; }
    public void setExitCode(int exitCode) { this.exitCode = exitCode; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public long getExecutionTimeMs() { return executionTimeMs; }
    public void setExecutionTimeMs(long executionTimeMs) { this.executionTimeMs = executionTimeMs; }
}