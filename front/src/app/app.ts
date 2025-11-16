import { afterNextRender, Component, inject, OnInit, signal } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { RouterOutlet } from '@angular/router';
import { Session } from './services/session/session';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatButtonModule],
  templateUrl: './app.html',
  styleUrl: './app.scss',
})
export class App {
  protected readonly title = signal('front');
  private session = inject(Session);

  constructor() {
    afterNextRender(() => {
      this.session.checkAuth();
    });
  }
}
