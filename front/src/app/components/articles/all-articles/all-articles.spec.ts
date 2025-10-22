import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllArticles } from './all-articles';

describe('Articles', () => {
  let component: AllArticles;
  let fixture: ComponentFixture<AllArticles>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AllArticles],
    }).compileComponents();

    fixture = TestBed.createComponent(AllArticles);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
