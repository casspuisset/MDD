import { Component, inject, OnDestroy, OnInit, signal } from '@angular/core';
import { NavBar } from '../nav-bar/nav-bar';
import { MatButton } from '@angular/material/button';
import { RouterLink } from '@angular/router';
import { MatIcon } from '@angular/material/icon';
import { ArticlesService } from '../../services/articles/articles-service';
import { Article } from '../../interfaces/articles/article.interface';
import { Session } from '../../services/session/session';
import { DatePipe } from '@angular/common';
import { take } from 'rxjs';

@Component({
  selector: 'app-feed',
  imports: [NavBar, RouterLink, MatIcon, DatePipe],
  templateUrl: './feed.html',
  styleUrl: './feed.scss',
})
export class Feed implements OnInit {
  private articleService = inject(ArticlesService);
  private sessionService = inject(Session);
  public isSorted: boolean = false;
  public user = this.sessionService.user;

  articles = signal<Article[]>([]);

  ngOnInit(): void {
    this.loadArticle();
  }

  /**
   * Change order of articles in the feed
   */
  sort() {
    this.isSorted = !this.isSorted;
    this.loadArticle();
  }

  /**
   * Retrieve all articles from user's feed
   */
  loadArticle() {
    this.articleService
      .getArticlesFromFeed()
      .pipe(take(1))
      .subscribe((articlesArray: Article[]) => {
        this.articles.set(
          this.isSorted
            ? articlesArray.sort(this.verifyArrayOrder)
            : articlesArray.sort(this.verifyInverseOrder)
        );
      });
  }

  verifyArrayOrder(a: Article, b: Article) {
    if (a.createdAt.toString() < b.createdAt.toString()) {
      return -1;
    }
    if (a.createdAt.toString() > b.createdAt.toString()) {
      return 1;
    }
    return 0;
  }

  verifyInverseOrder(a: Article, b: Article) {
    if (a.createdAt.toString() < b.createdAt.toString()) {
      return 1;
    }
    if (a.createdAt.toString() > b.createdAt.toString()) {
      return -1;
    }
    return 0;
  }
}
