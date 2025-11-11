import { Component, inject } from '@angular/core';
import { NavBar } from '../../nav-bar/nav-bar';
import { MatIcon } from '@angular/material/icon';
import { MatIconButton } from '@angular/material/button';
import { Router, RouterLink } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { TopicsService } from '../../../services/topics/topics-service';
import { ArticlesService } from '../../../services/articles/articles-service';
import { toSignal } from '@angular/core/rxjs-interop';
import { createArticle } from '../../../interfaces/articles/createArticle.interface';

@Component({
  selector: 'app-create',
  imports: [NavBar, MatIcon, MatIconButton, RouterLink, ReactiveFormsModule],
  templateUrl: './create.html',
  styleUrl: './create.scss',
})
export class Create {
  private formBuilder = inject(FormBuilder);
  private articleService = inject(ArticlesService);
  private topicService = inject(TopicsService);
  private router = inject(Router);
  topics = toSignal(this.topicService.getAllTopics());

  createForm = this.formBuilder.nonNullable.group({
    title: ['', [Validators.required, Validators.min(3)]],
    description: ['', [Validators.required, Validators.min(3)]],
    topicId: ['', [Validators.required]],
  });

  //create a new article from the form
  onSubmit() {
    if (
      this.createForm.valid &&
      this.createForm.value.title &&
      this.createForm.value.description &&
      this.createForm.value.topicId
    ) {
      let newRequest: createArticle = {
        title: this.createForm.value.title,
        description: this.createForm.value.description,
        topicId: Number(this.createForm.value.topicId),
      };
      this.articleService.createArticle(newRequest).subscribe({
        next: () => {
          this.router.navigate(['/feed']);
        },
        error: (error) => {
          console.error('Error when submitted:', error);
        },
      });
    } else {
      console.error('Invalid form:', this.createForm.errors);
    }
  }
}
