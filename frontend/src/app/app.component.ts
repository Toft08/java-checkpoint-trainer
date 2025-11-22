import { Component } from '@angular/core';
import { RouterOutlet, Router, NavigationEnd, RouterModule } from '@angular/router';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs/operators';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule, MatToolbarModule, MatButtonModule, MatIconModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'Java Checkpoint Trainer';
  currentExercise: { level: string; name: string } | null = null;

  constructor(private router: Router) {
    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd)
    ).subscribe((event: any) => {
      // Parse the URL to extract exercise info
      const urlParts = event.url.split('/');
      if (urlParts.length >= 4 && urlParts[1] === 'exercise') {
        this.currentExercise = {
          level: urlParts[2],
          name: urlParts[3]
        };
      } else {
        this.currentExercise = null;
      }
    });
  }
}