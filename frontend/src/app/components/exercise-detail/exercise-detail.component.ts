import { Component, OnInit, HostListener } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatTabsModule } from '@angular/material/tabs';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatSnackBarModule, MatSnackBar } from '@angular/material/snack-bar';
import { MatIconModule } from '@angular/material/icon';
import { MatTooltipModule } from '@angular/material/tooltip';

import { ExerciseService } from '../../exercise.service';
import { Exercise, TestResult } from '../../models';
import { CodeEditorComponent } from '../code-editor/code-editor.component';
import { marked } from 'marked';
import { markedHighlight } from 'marked-highlight';
import hljs from 'highlight.js';

@Component({
  selector: 'app-exercise-detail',
  standalone: true,
  imports: [
    CommonModule,
    MatCardModule,
    MatButtonModule,
    MatTabsModule,
    MatProgressSpinnerModule,
    MatSnackBarModule,
    MatIconModule,
    MatTooltipModule,
    CodeEditorComponent
  ],
  templateUrl: './exercise-detail.component.html',
  styleUrls: ['./exercise-detail.component.scss']
})
export class ExerciseDetailComponent implements OnInit {
  exercise: Exercise | null = null;
  readmeHtml = '';
  codeFiles: { name: string; content: string }[] = [];
  originalTemplateFiles: { name: string; content: string }[] = []; // Store original templates
  testResult: TestResult | null = null;
  loading = false;
  resultsHeight = 200;
  isDragging = false;
  private startY = 0;
  private startHeight = 0;
  allExercises: Exercise[] = [];
  currentExerciseIndex = -1;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private exerciseService: ExerciseService,
    private snackBar: MatSnackBar
  ) {
    marked.use(markedHighlight({
      langPrefix: 'hljs language-',
      highlight(code, lang) {
        const language = hljs.getLanguage(lang) ? lang : 'plaintext';
        return hljs.highlight(code, { language }).value;
      }
    }));

    marked.setOptions({
      breaks: true,
      gfm: true
    });
  }

  ngOnInit() {
    // Load all exercises first
    this.exerciseService.getAllExercises().subscribe((exercises: Exercise[]) => {
      this.allExercises = exercises;
      
      // Check if we're in random test mode
      const randomTestExercises = localStorage.getItem('randomTestExercises');
      if (randomTestExercises) {
        try {
          this.allExercises = JSON.parse(randomTestExercises);
        } catch (e) {
          console.error('Error parsing random test exercises', e);
        }
      }
      
      // Then load the current exercise
      this.route.params.subscribe(params => {
        this.loadExercise(params['level'], params['name']);
      });
    });
  }

  updateCurrentIndex() {
    if (this.exercise && this.allExercises.length > 0) {
      this.currentExerciseIndex = this.allExercises.findIndex(
        ex => ex.level === this.exercise!.level && ex.name === this.exercise!.name
      );
    }
  }

  goToNextExercise() {
    if (this.currentExerciseIndex >= 0 && this.currentExerciseIndex < this.allExercises.length - 1) {
      const nextExercise = this.allExercises[this.currentExerciseIndex + 1];
      this.router.navigate(['/exercise', nextExercise.level, nextExercise.name]);
    } else {
      // Exit test - navigate to home
      this.router.navigate(['/']);
    }
  }

  goToPreviousExercise() {
    if (this.currentExerciseIndex > 0) {
      const prevExercise = this.allExercises[this.currentExerciseIndex - 1];
      this.router.navigate(['/exercise', prevExercise.level, prevExercise.name]);
    } else if (this.allExercises.length > 0) {
      // Wrap around to last exercise
      const lastExercise = this.allExercises[this.allExercises.length - 1];
      this.router.navigate(['/exercise', lastExercise.level, lastExercise.name]);
    }
  }

  hasNextExercise(): boolean {
    return this.allExercises.length > 0;
  }

  isLastExercise(): boolean {
    return this.currentExerciseIndex === this.allExercises.length - 1;
  }

  hasPreviousExercise(): boolean {
    return this.allExercises.length > 0;
  }

  loadExercise(level: string, name: string) {
    this.loading = true;
    this.exerciseService.getExercise(level, name).subscribe({
      next: (exercise) => {
        this.exercise = exercise;
        this.readmeHtml = this.convertMarkdownToHtml(exercise.readmeContent || '');
        this.loading = false;
        this.loadTemplateFiles();
        this.updateCurrentIndex(); // Update index after exercise is loaded
      },
      error: (error) => {
        console.error('Error loading exercise:', error);
        this.loading = false;
      }
    });
  }

  loadTemplateFiles() {
    if (!this.exercise) return;

    this.loading = true;
    this.codeFiles = [];

    if (this.exercise.javaFiles && this.exercise.javaFiles.length > 0) {
      const fileLoads = this.exercise.javaFiles.map(fileName =>
        this.exerciseService.getFileContent(this.exercise!.level, this.exercise!.name, fileName)
      );

      Promise.all(fileLoads.map(obs => obs.toPromise())).then(contents => {
        const files = this.exercise!.javaFiles!.map((fileName, index) => ({
          name: fileName,
          content: contents[index] || '// Error loading file content'
        }));
        
        // Store original templates
        this.originalTemplateFiles = JSON.parse(JSON.stringify(files));
        this.codeFiles = files;
        this.loading = false;
      }).catch(error => {
        console.error('Error loading template files:', error);
        this.snackBar.open('Error loading template files', 'Close', { duration: 3000 });
        this.loading = false;
      });
    } else {
      const defaultFile = {
        name: this.exercise.name + '.java',
        content: `public class ${this.exercise.name} {
    public static void main(String[] args) {
        // TODO: Implement your solution here
    }
}`
      };
      
      // Store original template
      this.originalTemplateFiles = [JSON.parse(JSON.stringify(defaultFile))];
      this.codeFiles = [defaultFile];
      this.loading = false;
    }
  }

  onCodeChange(fileName: string, newCode: string) {
    const file = this.codeFiles.find(f => f.name === fileName);
    if (file) {
      file.content = newCode;
    }
  }

  runTests() {
    if (!this.exercise || !this.exercise.hasTests || this.codeFiles.length === 0) {
      this.snackBar.open('Please create code files before running tests', 'Close', { duration: 3000 });
      return;
    }

    this.loading = true;
    this.testResult = null;

    const files: { [fileName: string]: string } = {};
    this.codeFiles.forEach(file => {
      files[file.name] = file.content;
    });

    this.exerciseService.runTests(this.exercise.level, this.exercise.name, files).subscribe({
      next: (result) => {
        this.testResult = result;
        this.loading = false;
        if (result.success) {
          this.snackBar.open('All tests passed! 🎉', 'Close', { duration: 3000 });
        } else {
          this.snackBar.open('Tests failed. Check the results below.', 'Close', { duration: 3000 });
        }
      },
      error: (error) => {
        console.error('Error running tests:', error);
        this.snackBar.open('Error running tests', 'Close', { duration: 3000 });
        this.loading = false;
      }
    });
  }

  trackByFileName(index: number, file: { name: string; content: string }) {
    return file.name;
  }

  getOriginalTemplate(fileName: string): string {
    const originalFile = this.originalTemplateFiles.find(f => f.name === fileName);
    return originalFile ? originalFile.content : '';
  }

  private convertMarkdownToHtml(markdown: string): string {
    try {
      return marked.parse(markdown) as string;
    } catch (error) {
      console.error('Error parsing markdown:', error);
      return markdown;
    }
  }

  onResizeStart(event: MouseEvent) {
    this.isDragging = true;
    this.startY = event.clientY;
    this.startHeight = this.resultsHeight;
    event.preventDefault();
  }

  @HostListener('document:mousemove', ['$event'])
  onMouseMove(event: MouseEvent) {
    if (this.isDragging) {
      const deltaY = this.startY - event.clientY;
      const newHeight = this.startHeight + deltaY;
      const minHeight = 100;
      const maxHeight = window.innerHeight * 0.6;
      this.resultsHeight = Math.max(minHeight, Math.min(maxHeight, newHeight));
    }
  }

  @HostListener('document:mouseup')
  onMouseUp() {
    this.isDragging = false;
  }

  getResultsStyle() {
    return {
      'height': `${this.resultsHeight}px`
    };
  }
}
