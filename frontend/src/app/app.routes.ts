import { Routes } from '@angular/router';
import { ExerciseListComponent } from './components/exercise-list/exercise-list.component';
import { ExerciseDetailComponent } from './components/exercise-detail/exercise-detail.component';

export const routes: Routes = [
  { path: '', redirectTo: '/exercises', pathMatch: 'full' },
  { path: 'exercises', component: ExerciseListComponent },
  { path: 'exercises/:level/:name', component: ExerciseDetailComponent },
  { path: '**', redirectTo: '/exercises' }
];