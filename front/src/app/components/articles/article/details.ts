import { Component, inject, OnInit } from '@angular/core';
import { NavBar } from '../../nav-bar/nav-bar';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { ArticlesService } from '../../../services/articles/articles-service';
import { CommentRequest } from '../../../interfaces/comments/commentRequest.interface';
import { CommentResponse } from '../../../interfaces/comments/commentResponse.interface';
import { CommentsService } from '../../../services/comments/comments-service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatIcon } from '@angular/material/icon';
import { MatIconButton } from '@angular/material/button';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-article',
  imports: [NavBar, MatIcon, ReactiveFormsModule, RouterLink, MatIconButton, DatePipe],
  templateUrl: './details.html',
  styleUrl: './details.scss',
})
export class Details {
  private route = inject(ActivatedRoute);
  private articlesService = inject(ArticlesService);
  private commentService = inject(CommentsService);
  private formBuilder = inject(FormBuilder);
  private router = inject(Router);
  public id = Number(this.route.snapshot.paramMap.get('id')!);

  article = toSignal(this.articlesService.getArticleById(this.id), {
    initialValue: null,
  });

  comments = toSignal(this.commentService.getComments(this.id));

  commentForm = this.formBuilder.nonNullable.group({
    comment: ['', [Validators.required, Validators.min(10)]],
  });

  //send a new comment
  public sendMessage(): void {
    if (this.commentForm.value.comment) {
      let newRequest: CommentRequest = {
        comment: this.commentForm.value.comment,
      };
      if (this.id !== undefined) {
        this.commentService
          .createComment(this.id, newRequest)
          .subscribe((commentResponse: CommentResponse) => {
            console.log(commentResponse.message);
            this.router.navigate(['/feed']);
          });
      } else {
        console.error('An error occured : url is not valid');
      }
    } else {
      console.error('Error: comment is empty');
    }
  }
}
