import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatChipsModule } from '@angular/material/chips';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { MatTabsModule } from '@angular/material/tabs';

import { ExerciseService } from '../../exercise.service';
import { Exercise } from '../../models';

@Component({
  selector: 'app-exercise-list',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule,
    MatCardModule,
    MatButtonModule,
    MatChipsModule,
    MatProgressSpinnerModule,
    MatTabsModule
  ],
  template: `
    <div class="exercise-list-container">
      <h1>Java Checkpoint Exercises</h1>
      
      <div *ngIf="loading" class="loading">
        <mat-spinner></mat-spinner>
        <p>Loading exercises...</p>
      </div>

      <mat-tab-group *ngIf="!loading">
        <mat-tab *ngFor="let level of levels" [label]="level.toUpperCase()">
          <div class="exercises-grid">
            <mat-card 
              *ngFor="let exercise of getExercisesByLevel(level)" 
              class="exercise-card"
              [routerLink]="['/exercise', exercise.level, exercise.name]">
              
              <mat-card-header>
                <mat-card-title>
                  <span [class]="'level-badge level-' + exercise.level">{{exercise.level.toUpperCase()}}</span>
                  {{exercise.name}}
                </mat-card-title>
              </mat-card-header>
              
              <mat-card-content>
                <p *ngIf="exercise.description">{{exercise.description}}</p>
                <p *ngIf="!exercise.description" class="no-description">Click to view exercise details</p>
                
                <div class="exercise-meta">
                  <mat-chip-set>
                    <mat-chip *ngIf="exercise.hasTests" color="accent" selected>
                      Has Tests
                    </mat-chip>
                    <mat-chip *ngIf="exercise.javaFiles && exercise.javaFiles.length > 0">
                      {{exercise.javaFiles.length}} files
                    </mat-chip>
                  </mat-chip-set>
                </div>
              </mat-card-content>
              
              <mat-card-actions>
                <button mat-button color="primary">
                  Start Exercise
                </button>
              </mat-card-actions>
            </mat-card>
          </div>
        </mat-tab>
      </mat-tab-group>
    </div>
  `,
  styles: [`
    .exercise-list-container {
      max-width: 1200px;
      margin: 0 auto;
      padding: 20px;
    }

    .loading {
      display: flex;
      flex-direction: column;
      align-items: center;
      margin-top: 50px;
    }

    .exercises-grid {
      display: grid;
      grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
      gap: 20px;
      margin-top: 20px;
    }

    .exercise-card {
      cursor: pointer;
      transition: transform 0.2s, box-shadow 0.2s;
    }

    .exercise-card:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 8px rgba(0,0,0,0.12);
    }

    .no-description {
      color: #666;
      font-style: italic;
    }

    .exercise-meta {
      margin-top: 12px;
    }

    mat-card-title {
      display: flex;
      align-items: center;
    }
  `]
})
export class ExerciseListComponent implements OnInit {
  exercises: Exercise[] = [];
  levels: string[] = ['g1', 'g2', 'g3', 'g4'];
  loading = true;

  constructor(private exerciseService: ExerciseService) {}

  ngOnInit() {
    this.loadExercises();
  }

  loadExercises() {
    this.exerciseService.getAllExercises().subscribe({
      next: (exercises) => {
        this.exercises = exercises;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error loading exercises:', error);
        this.loading = false;
      }
    });
  }

  getExercisesByLevel(level: string): Exercise[] {
    return this.exercises.filter(exercise => exercise.level === level);
  }
}