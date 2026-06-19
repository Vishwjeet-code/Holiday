import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectWorkingDayComponent } from './project-working-day.component';

describe('ProjectWorkingDayComponent', () => {
  let component: ProjectWorkingDayComponent;
  let fixture: ComponentFixture<ProjectWorkingDayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjectWorkingDayComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjectWorkingDayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
