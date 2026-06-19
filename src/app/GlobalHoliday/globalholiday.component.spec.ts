import { ComponentFixture, TestBed } from '@angular/core/testing';

import { globalholidayComponent } from './globalholiday.component';

describe('globalholidayComponnt', () => {
  let component: globalholidayComponent;
  let fixture: ComponentFixture<globalholidayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [globalholidayComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(globalholidayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
