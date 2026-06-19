import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink, RouterOutlet } from '@angular/router';

import { Select } from 'primeng/select';

import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { Dialog } from 'primeng/dialog';
import { InputTextModule } from 'primeng/inputtext';
import { AutoComplete } from 'primeng/autocomplete';
import { DatePicker } from 'primeng/datepicker';
import { HttpClient } from '@angular/common/http';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { CheckboxModule } from 'primeng/checkbox';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { Router } from '@angular/router';
import { SelectModule } from 'primeng/select';
import { DropdownModule } from 'primeng/dropdown';
import { TooltipModule } from 'primeng/tooltip';
@Component({
  selector: 'app-project-working-day',
  imports: [CommonModule,
    FormsModule,

    ButtonModule,
    TableModule,
    Dialog,
    SelectModule,
    DropdownModule,
    TooltipModule,

    InputTextModule,
     DatePicker,

    InputIconModule,
    IconFieldModule,
    ProgressSpinnerModule,
    CheckboxModule,],
  templateUrl: './project-working-day.component.html',
  styleUrl: './project-working-day.component.css'
})
export class ProjectWorkingDayComponent implements OnInit {





  // ==================== INLINE EDIT STATE ====================
  inlineEditingDateId: number | null = null;
  inlineEditData: {
    holidays: { holidayId: any; holidayName: string; dateObj: Date; dateId: number | null }[];
  } = { holidays: [] };

  // ==================== ADD DIALOG STATE ====================
workingDayList: {
   dayId: number | null;

  workingFlag: string;
  startTime: string;
  endTime: string;
}[] = [];
allDays: any[] = [];

editingRowData: any = null;

  isEditMode = false;
  editingRow: any = null;
  minDate!: Date;
  maxDate!: Date;
  isSubmitted = false;
  isLoading: boolean = false;

  addVisible: boolean = false;
  editVisible: boolean = false;

  isCommentDisabled: boolean = false;



  dates: any[] = [];
  selectedDate!: number;
  selectedDateObj!: Date;
  enabledDates: Date[] = [];
  countries: any[] = [];
  years: any[] = [];

  selectedCountry: number = 403;
  selectedProject: any = null;
  selectedCityName: string = '';

  selectedYear: any;

  filteredYears: any[] = [];
  filteredProjects: any[] = [];

  tableData: any[] = [];

  Projects: any;

  selectedCity: any;

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef,private router: Router) {}
  private baseUrl = 'https://exhaust-hash-activated-described.trycloudflare.com/api';

  ngOnInit(): void {


    this.loadCountries();
    this.loadYears();
    this.loadProjects();
    this.loadAllDays();
  }

  loadCountries() {
    this.http.get<any[]>(`${this.baseUrl}/countries/all`).subscribe((data) => {
      this.countries = data;
    });
  }

  get selectedCountryName(): string {
    const countryObj = this.countries.find(c => c.countryId === this.selectedCountry || c.id === this.selectedCountry);
    return countryObj ? countryObj.countryName : 'Select Country';
  }

  searchYear(event: any) {
    const query = event.query?.toLowerCase() || '';
    this.filteredYears = this.years.filter((y: any) =>
      y.year.toString().toLowerCase().includes(query)
    );
  }

  get selectedProjectName(): string {
    return this.selectedProject?.projectName || '';
  }
  loadAllDays() {
  this.http.get<any[]>(`${this.baseUrl}/AllDays`).subscribe((data) => {
    this.allDays = data;
  });
}

  loadProjects() {
    this.http.get<any[]>(`${this.baseUrl}/projects/all`).subscribe((data) => {
      this.Projects = data;
      const storedProject = localStorage.getItem('selectedProject');

    if (storedProject) {

      const projectData = JSON.parse(storedProject);

      // Find matching project from API list
      const selectedProject = data.find(
        (p: any) => p.projectDetailsId === projectData.projectId
      );

      if (selectedProject) {

        this.selectedProject = selectedProject;



      }
    }
      if (this.selectedYear) {
        this.onSubmit();
      }
    });
  }



  searchProjects(event: any) {
    const query = event.query?.toLowerCase() || '';
    this.filteredProjects = this.Projects.filter((P: any) =>
      P.projectName.toString().toLowerCase().includes(query)
    );
  }

  onYearSelect() {
    const year = this.selectedYear.year;
    this.minDate = new Date(year, 0, 1);
    this.maxDate = new Date(year, 11, 31);
    if (this.selectedProject) {
      this.onSubmit();
    }
  }

  loadYears() {
    this.http.get<any[]>(`${this.baseUrl}/years/all`).subscribe((data) => {
      const currentYear = new Date().getFullYear();
      this.years = data.filter((y: any) => y.year >= currentYear);
      const current = this.years.find((y: any) => y.year == currentYear);
      this.selectedYear = current ? current : this.years[0];
      this.filteredYears = this.years;
      const year = this.selectedYear.year;
      this.minDate = new Date(year, 0, 1);
      this.maxDate = new Date(year, 11, 31);
      if (this.selectedProject) {
        this.onSubmit();
      }
    });
  }

  onSubmit() {
    if (!this.selectedProject) return;

  this.isLoading = true;

  const projectId =
    this.selectedProject?.projectDetailsId ??
    this.selectedProject;

  this.http
    .get<any[]>(
      `${this.baseUrl}/AllProjectWorkingDay/${projectId}`
    )
    .subscribe({

      next: (data) => {

        this.flattenWorkingDays(data);

        this.isSubmitted = true;

        this.isLoading = false;

        this.cdr.detectChanges();
      },

      error: () => {

        this.tableData = [];

        this.isLoading = false;
      }
    });
  }

  flattenWorkingDays(data: any[]) {

  this.tableData = [];

  data.forEach((project: any) => {

    project.dates.forEach((d: any) => {

      this.tableData.push({

        projectId: project.projectId,

  projectName: project.projectName,

  dayId: d.dayId,

  dateId: d.dateId,

  date: d.date,

  day: d.day,

  workingFlag: d.workingFlag,

  startTime: d.startTime,

  endTime: d.endTime,

  createdBy: d.createdBy
      });
    });
  });


}


  // ==================== INLINE EDIT ====================

  isPastDate(dateStr: string): boolean {
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const rowDate = new Date(dateStr);
    rowDate.setHours(0, 0, 0, 0);
    return rowDate < today;
  }

  /** Called when user clicks the Edit button on a table row */
onEdit(row: any) {

  this.cancelInlineEdit();

  this.inlineEditingDateId = row.dayId;

  this.editingRowData = {
    projectId: row.projectId,
    dayId: row.dayId,
    workingFlag: row.workingFlag,
    startTime: this.timeStringToDate(row.startTime),
    endTime: this.timeStringToDate(row.endTime)
  };
}

  /** Returns true if the given row is currently being edited inline */
 isInlineEditing(row: any): boolean {
 return this.inlineEditingDateId === row.dayId;
}

  /** Add a new blank holiday entry to the inline edit row */


  /** Remove a holiday entry from the inline edit row */



  /** When user changes the date for a specific inline holiday entry */
  onInlineDateChange(holidayIndex: number) {
    const holiday = this.inlineEditData.holidays[holidayIndex];
    const selectedObj = holiday.dateObj;
    if (!selectedObj) return;

    const year = selectedObj.getFullYear();
    const month = String(selectedObj.getMonth() + 1).padStart(2, '0');
    const day = String(selectedObj.getDate()).padStart(2, '0');
    const selectedDateStr = `${year}-${month}-${day}`;

    const found = this.dates.find((d: any) => d.date === selectedDateStr);
    if (found) {
      holiday.dateId = found.dateId;
    } else {
      holiday.dateId = null;
    }
  }

  /** Save the inline-edited row via PUT API */
saveInlineEdit() {
  const payload = {
    projectId: this.editingRowData.projectId,
    dates: [
      {
        dayId: this.editingRowData.dayId,
        workingFlag: this.editingRowData.workingFlag,
        startTime: this.formatTime(this.editingRowData.startTime),
        endTime: this.formatTime(this.editingRowData.endTime),
        created: 'admin'
      }
    ]
  };

  this.http.put(
    `${this.baseUrl}/update/ProjectWorkingDays`,
    payload,
    { responseType: 'text' }
  ).subscribe({
    next: () => {
      const index = this.tableData.findIndex(
        x => x.dayId === this.editingRowData.dayId
      );

      if (index !== -1) {
        // ✅ Update in-place — don't recreate the array
        this.tableData[index].workingFlag = this.editingRowData.workingFlag;
        this.tableData[index].startTime = this.formatTime(this.editingRowData.startTime) + ':00';
        this.tableData[index].endTime = this.formatTime(this.editingRowData.endTime) + ':00';
      }

      this.inlineEditingDateId = null;
      this.editingRowData = null;

      // ✅ Tell Angular to detect changes WITHOUT recreating the array
      this.cdr.detectChanges();

      alert('Updated Successfully');
    },
    error: (err) => {
      alert(err?.error || 'Update failed');
    }
  });
}

  /** Discard inline edits and restore view mode */
cancelInlineEdit() {
  this.inlineEditingDateId = null;
  this.editingRowData = null;
}

  // ==================== ADD MODE ====================

  onAdd() {

  this.workingDayList = [];

  // create 7 rows initially
  for (let i = 0; i < 7; i++) {
    this.workingDayList.push({
      dayId: null,

      workingFlag: 'Y',
      startTime: '',
      endTime: ''
    });
  }

  this.addVisible = true;
  }


  saveWorkingDays() {

  const invalid = this.workingDayList.find(
   x => !x.dayId || !x.startTime || !x.endTime
  );

  if (invalid) {
    alert('Please fill all fields');
    return;
  }

  const payload = {
    projectId: this.selectedProject?.projectDetailsId,

    dates: this.workingDayList.map(x => ({
      dayId: x.dayId,
      workingFlag: x.workingFlag,
       startTime: this.formatTime(x.startTime),   // ✅ Convert here
      endTime: this.formatTime(x.endTime),         // ✅ Convert here
      createdBy: 'admin'
    }))
  };

  this.http.post(
    `${this.baseUrl}/SavePojectWorkingDays`,
    payload,
    { responseType: 'text' }
  ).subscribe({

    next: () => {

      alert('Saved Successfully');

      this.addVisible = false;

      this.onSubmit();
    },

    error: (err) => {

      alert(err?.error || 'Something went wrong');
    }
  });
}

// ✅ Convert Date object OR "HH:mm" string → "HH:mm" string
formatTime(time: any): string {
  if (!time) return '';

  // Already a string like "09:30"
  if (typeof time === 'string') {
    return time.substring(0, 5); // Trim seconds if any
  }

  // It's a Date object
  if (time instanceof Date) {
    const hours = time.getHours().toString().padStart(2, '0');
    const mins = time.getMinutes().toString().padStart(2, '0');
    return `${hours}:${mins}`;
  }

  return '';
}

// ✅ Convert "HH:mm" string → Date (for p-datepicker binding)
timeStringToDate(timeStr: any): Date | null {
  if (!timeStr) return null;
  if (timeStr instanceof Date) return timeStr; // Already a Date

  const [hours, mins] = timeStr.split(':').map(Number);
  const date = new Date();
  date.setHours(hours, mins, 0, 0);
  return date;
}







  // ==================== COPY ====================








}
