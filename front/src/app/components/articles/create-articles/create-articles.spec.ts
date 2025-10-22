import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateArticles } from './create-articles';

describe('CreateArticles', () => {
  let component: CreateArticles;
  let fixture: ComponentFixture<CreateArticles>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CreateArticles]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CreateArticles);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
