import { Component, OnInit } from '@angular/core';
import { RouterLink, RouterOutlet } from '@angular/router';
import { MatButtonModule } from '@angular/material/button';
import { ArticlesService } from '../../../services/articles/articles-service';
import { Observable } from 'rxjs';
import { ArticlesResponse } from '../../../interfaces/api/articlesResponse.interface';

@Component({
  selector: 'app-articles',
  imports: [RouterLink, MatButtonModule],
  templateUrl: './all-articles.html',
  styleUrl: './all-articles.scss',
})
export class AllArticles implements OnInit {
  constructor(private articleService: ArticlesService) {
    this.articleService = articleService;
  }

  ngOnInit(): void {
    let articles$: Observable<ArticlesResponse> = this.articleService.all();
  }
}
