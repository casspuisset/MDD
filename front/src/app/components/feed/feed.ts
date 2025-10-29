import { Component, inject, OnInit, signal } from '@angular/core';
import { NavBar } from '../nav-bar/nav-bar';
import { MatButton } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { MatIcon } from '@angular/material/icon';
import { ArticlesService } from '../../services/articles/articles-service';
import { Article } from '../../interfaces/articles/article.interface';
import { Session } from '../../services/session/session';
import { toSignal } from '@angular/core/rxjs-interop';
import { UsersService } from '../../services/users/users-service';

@Component({
  selector: 'app-feed',
  imports: [NavBar, MatButton, RouterLink, MatIcon],
  templateUrl: './feed.html',
  styleUrl: './feed.scss',
})
export class Feed implements OnInit {
  private articleService = inject(ArticlesService);
  private sessionService = inject(Session);
  private userService = inject(UsersService);
  public isSorted: boolean = false;

  public user = toSignal(this.userService.getById(this.sessionService.user!.id.toString()), {
    initialValue: null,
  });

  articles = signal<Article[]>([]);

  ngOnInit(): void {
    this.loadArticle();
  }

  noSort() {
    this.isSorted = false;
    this.loadArticle();
  }

  inverseSort() {
    this.isSorted = true;
    this.loadArticle();
  }

  loadArticle() {
    this.articleService.getArticlesFromFeed().subscribe((articlesArray: Article[]) => {
      this.articles.set(this.isSorted ? articlesArray.reverse() : articlesArray);
    });
  }
}
