import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StoryDinamicComponent } from './story-dinamic.component';

describe('StoryDinamicComponent', () => {
  let component: StoryDinamicComponent;
  let fixture: ComponentFixture<StoryDinamicComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [StoryDinamicComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(StoryDinamicComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
