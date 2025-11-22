package com.checkpoint.trainer.controller;

import com.checkpoint.trainer.model.Exercise;
import com.checkpoint.trainer.model.ExerciseSubmission;
import com.checkpoint.trainer.model.TestResult;
import com.checkpoint.trainer.service.DockerTestService;
import com.checkpoint.trainer.service.ExerciseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/exercises")
public class ExerciseController {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private DockerTestService dockerTestService;

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        List<Exercise> exercises = exerciseService.getAllExercises();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/{level}/{name}")
    public ResponseEntity<Exercise> getExercise(@PathVariable String level, @PathVariable String name) {
        Exercise exercise = exerciseService.getExercise(level, name);
        
        if (exercise == null) {
            return ResponseEntity.notFound().build();
        }
        
        return ResponseEntity.ok(exercise);
    }

    @GetMapping("/{level}/{name}/readme")
    public ResponseEntity<String> getReadme(@PathVariable String level, @PathVariable String name) {
        String readmeContent = exerciseService.getReadmeContent(level, name);
        return ResponseEntity.ok(readmeContent);
    }

    @GetMapping("/{level}/{name}/files/{fileName}")
    public ResponseEntity<String> getFileContent(
            @PathVariable String level,
            @PathVariable String name,
            @PathVariable String fileName) {
        
        String content = exerciseService.getFileContent(level, name, fileName);
        return ResponseEntity.ok(content);
    }

    @PostMapping("/{level}/{name}/test")
    public ResponseEntity<TestResult> runTests(
            @PathVariable String level,
            @PathVariable String name,
            @RequestBody Map<String, String> files) {
        
        ExerciseSubmission submission = new ExerciseSubmission(name, level, files);
        TestResult result = dockerTestService.runTests(submission);
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/levels")
    public ResponseEntity<List<String>> getLevels() {
        List<String> levels = List.of("g1", "g2", "g3", "g4");
        return ResponseEntity.ok(levels);
    }
}