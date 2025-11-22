export interface Exercise {
  name: string;
  level: string;
  readmeContent?: string;
  javaFiles?: string[];
  description?: string;
  hasTests: boolean;
}

export interface TestResult {
  success: boolean;
  output: string;
  errorOutput?: string;
  compilationErrors?: string[];
  testFailures?: string[];
  exitCode: number;
  timestamp: string;
  executionTimeMs: number;
}

export interface ExerciseSubmission {
  exerciseName: string;
  level: string;
  files: { [fileName: string]: string };
}