import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Exercise, TestResult } from './models';

@Injectable({
  providedIn: 'root'
})
export class ExerciseService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) {}

  getAllExercises(): Observable<Exercise[]> {
    return this.http.get<Exercise[]>(`${this.baseUrl}/exercises`);
  }

  getExercise(level: string, name: string): Observable<Exercise> {
    return this.http.get<Exercise>(`${this.baseUrl}/exercises/${level}/${name}`);
  }

  getReadme(level: string, name: string): Observable<string> {
    return this.http.get(`${this.baseUrl}/exercises/${level}/${name}/readme`, { responseType: 'text' });
  }

  getFileContent(level: string, exerciseName: string, fileName: string): Observable<string> {
    return this.http.get(`${this.baseUrl}/exercises/${level}/${exerciseName}/files/${fileName}`, { responseType: 'text' });
  }

  runTests(level: string, exerciseName: string, files: { [fileName: string]: string }): Observable<TestResult> {
    return this.http.post<TestResult>(`${this.baseUrl}/exercises/${level}/${exerciseName}/test`, files);
  }

  getLevels(): Observable<string[]> {
    return this.http.get<string[]>(`${this.baseUrl}/exercises/levels`);
  }
}