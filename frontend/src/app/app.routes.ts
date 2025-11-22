import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { ExerciseListComponent } from './components/exercise-list/exercise-list.component';
import { ExerciseDetailComponent } from './components/exercise-detail/exercise-detail.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'exercises', component: ExerciseListComponent },
  { path: 'exercise/:level/:name', component: ExerciseDetailComponent },
  { path: '**', redirectTo: '' }
];