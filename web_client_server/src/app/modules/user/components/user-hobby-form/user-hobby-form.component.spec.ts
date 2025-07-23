import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UserHobbyFormComponent } from './user-hobby-form.component';

describe('UserHobbyFormComponent', () => {
  let component: UserHobbyFormComponent;
  let fixture: ComponentFixture<UserHobbyFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UserHobbyFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UserHobbyFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
