package com.checkpoint.trainer.service;

import com.checkpoint.trainer.model.ExerciseSubmission;
import com.checkpoint.trainer.model.TestResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class DockerTestService {

    @Value("${app.checkpoint.docker.image}")
    private String dockerImage;

    @Value("${app.checkpoint.docker.timeout:60}")
    private int timeoutSeconds;

    public TestResult runTests(ExerciseSubmission submission) {
        TestResult result = new TestResult();
        long startTime = System.currentTimeMillis();
        
        // Create temporary directory for this test run
        Path tempDir = null;
        try {
            tempDir = Files.createTempDirectory("checkpoint-test-");
            
            // Create exercise subdirectory to match expected Docker structure
            Path exerciseDir = tempDir.resolve(submission.getExerciseName());
            Files.createDirectories(exerciseDir);
            
            // Write submitted files to exercise subdirectory
            writeFilesToExerciseDirectory(submission.getFiles(), exerciseDir);
            
            // Run Docker test from the temp directory (which acts as the group directory)
            result = executeDockerTest(submission.getExerciseName(), tempDir);
            
        } catch (IOException e) {
            result.setSuccess(false);
            result.setErrorOutput("Failed to create test environment: " + e.getMessage());
        } finally {
            // Cleanup temp directory
            if (tempDir != null) {
                try {
                    deleteDirectory(tempDir);
                } catch (IOException e) {
                    System.err.println("Failed to cleanup temp directory: " + e.getMessage());
                }
            }
            
            result.setExecutionTimeMs(System.currentTimeMillis() - startTime);
        }
        
        return result;
    }

    private void writeFilesToExerciseDirectory(Map<String, String> files, Path exerciseDirectory) throws IOException {
        for (Map.Entry<String, String> entry : files.entrySet()) {
            Path filePath = exerciseDirectory.resolve(entry.getKey());
            // Ensure parent directories exist in case of nested files
            if (filePath.getParent() != null && !Files.exists(filePath.getParent())) {
                Files.createDirectories(filePath.getParent());
            }
            Files.writeString(filePath, entry.getValue(), StandardOpenOption.CREATE);
        }
    }

    private TestResult executeDockerTest(String exerciseName, Path workingDir) {
        TestResult result = new TestResult();
        
        try {
            // Build Docker command
            List<String> command = new ArrayList<>();
            command.add("docker");
            command.add("run");
            command.add("--rm");
            command.add("-e");
            command.add("EXERCISE=" + exerciseName);
            command.add("-v");
            command.add(workingDir.toAbsolutePath() + ":/app/student");
            command.add("-w");
            command.add("/app");
            command.add(dockerImage);

            // Execute command
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(workingDir.toFile());
            Process process = pb.start();

            // Wait for completion with timeout
            boolean finished = process.waitFor(timeoutSeconds, TimeUnit.SECONDS);
            
            if (!finished) {
                process.destroyForcibly();
                result.setSuccess(false);
                result.setErrorOutput("Test execution timed out after " + timeoutSeconds + " seconds");
                result.setExitCode(-1);
                return result;
            }

            // Read output
            String output = new String(process.getInputStream().readAllBytes());
            String errorOutput = new String(process.getErrorStream().readAllBytes());
            
            result.setOutput(output);
            result.setErrorOutput(errorOutput);
            result.setExitCode(process.exitValue());
            result.setSuccess(process.exitValue() == 0);
            
            // Parse compilation errors and test failures
            parseTestOutput(result, output, errorOutput);
            
        } catch (IOException | InterruptedException e) {
            result.setSuccess(false);
            result.setErrorOutput("Failed to execute Docker test: " + e.getMessage());
            result.setExitCode(-1);
        }
        
        return result;
    }

    private void parseTestOutput(TestResult result, String output, String errorOutput) {
        List<String> compilationErrors = new ArrayList<>();
        List<String> testFailures = new ArrayList<>();
        
        // Parse compilation errors (look for .java:line: error patterns)
        String combinedOutput = output + "\n" + errorOutput;
        String[] lines = combinedOutput.split("\n");
        
        for (String line : lines) {
            if (line.contains(".java:") && line.contains("error:")) {
                compilationErrors.add(line.trim());
            } else if (line.contains("FAIL") || line.contains("AssertionError") || line.contains("Exception")) {
                testFailures.add(line.trim());
            }
        }
        
        result.setCompilationErrors(compilationErrors);
        result.setTestFailures(testFailures);
    }

    private void deleteDirectory(Path directory) throws IOException {
        Files.walk(directory)
             .sorted((path1, path2) -> -path1.compareTo(path2)) // Reverse order for deletion
             .forEach(path -> {
                 try {
                     Files.delete(path);
                 } catch (IOException e) {
                     // Ignore individual file deletion errors
                 }
             });
    }
}