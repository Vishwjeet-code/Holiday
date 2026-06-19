import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProjectmppComponent } from './projectmpp.component';

describe('ProjectmppComponent', () => {
  let component: ProjectmppComponent;
  let fixture: ComponentFixture<ProjectmppComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ProjectmppComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProjectmppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
