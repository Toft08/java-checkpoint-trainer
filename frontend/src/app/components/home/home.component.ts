import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { ExerciseService } from '../../exercise.service';
import { Exercise } from '../../models';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule],
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  exercises: Exercise[] = [];
  
  constructor(
    private exerciseService: ExerciseService,
    private router: Router
  ) {}

  ngOnInit() {
    this.exerciseService.getAllExercises().subscribe((exercises: Exercise[]) => {
      this.exercises = exercises;
    });
  }

  startRandomTest() {
    const levels = ['g1', 'g2', 'g3', 'g4'];
    const selectedExercises: Exercise[] = [];

    levels.forEach(level => {
      const levelExercises = this.exercises.filter(ex => ex.level === level);
      if (levelExercises.length > 0) {
        const random = levelExercises[Math.floor(Math.random() * levelExercises.length)];
        selectedExercises.push(random);
      }
    });

    if (selectedExercises.length > 0) {
      // Store the random test exercises in localStorage
      localStorage.setItem('randomTestExercises', JSON.stringify(selectedExercises));
      
      // Navigate to the first random exercise
      const first = selectedExercises[0];
      this.router.navigate(['/exercise', first.level, first.name]);
    }
  }

  goToExercises() {
    this.router.navigate(['/exercises']);
  }
}
