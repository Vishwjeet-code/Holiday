import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectHolidayComponent } from './project-holiday.component';

describe('ProjectHolidayComponent', () => {
  let component: ProjectHolidayComponent;
  let fixture: ComponentFixture<ProjectHolidayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjectHolidayComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjectHolidayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
