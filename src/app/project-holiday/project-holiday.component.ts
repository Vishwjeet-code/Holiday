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


@Component({
  selector: 'app-project-holiday',
  imports: [CommonModule,
    FormsModule,
    AutoComplete,
    ButtonModule,
    TableModule,

    Dialog,
    InputTextModule,
     DatePicker,

    InputIconModule,
    IconFieldModule,
    ProgressSpinnerModule,
    CheckboxModule,],
  templateUrl: './project-holiday.component.html',
  styleUrl: './project-holiday.component.css'
})
export class ProjectHolidayComponent implements OnInit {
    isAllCopied: boolean = false;
  globalHolidayData: any[] = [];
  selectAllChecked: boolean = false;
  holidayOptions: any[] = [];
  selectedHoliday: any = null;
  filteredHolidays: any[] = [];
  copyTableData: any[] = [];
  groupedEditHolidayList: any[] = [];

  // ==================== INLINE EDIT STATE ====================
  inlineEditingDateId: number | null = null;
  inlineEditData: {
    holidays: { holidayId: any; holidayName: string; dateObj: Date; dateId: number | null }[];
  } = { holidays: [] };

  // ==================== ADD DIALOG STATE ====================
  dateHolidayList: {
    date: Date | null;
    dateId: number | null;
    disabled: boolean;
    comment: string;
    holidays: { holidayName: string }[];
  }[] = [{ date: null, dateId: null, comment: ' ', disabled: false, holidays: [{ holidayName: '' }] }];

  isEditMode = false;
  editingRow: any = null;
  minDate!: Date;
  maxDate!: Date;
  isSubmitted = false;
  isLoading: boolean = false;

  addVisible: boolean = false;
  editVisible: boolean = false;

  isCommentDisabled: boolean = false;

  holidayForm = {
    holidayName: '',
    comment: '',
    createdBy: '',
  };

  dates: any[] = [];
  selectedDate!: number;
  selectedDateObj!: Date;
  enabledDates: Date[] = [];
  countries: any[] = [];
  years: any[] = [];
  previousYearData: any[] = [];

  selectedCountry: number = 403;
  selectedProject: any = null;
  selectedCityName: string = '';

  selectedYear: any;
  nextYear: any;
  filteredYears: any[] = [];
  filteredProjects: any[] = [];
  holidays: any[] = [];
  tableData: any[] = [];
  copyDialogVisible: boolean = false;
  Projects: any;
  selectedIndustry: any;
  selectedCity: any;

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef,private router: Router) {}
  private baseUrl = 'https://exhaust-hash-activated-described.trycloudflare.com/api';

  ngOnInit(): void {


    this.loadCountries();
    this.loadYears();
    this.loadProjects();
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

        this.loadCityName(selectedProject.projectCityId);

      }
    }
      if (this.selectedYear) {
        this.onSubmit();
      }
    });
  }

  loadCityName(cityId: number) {
    this.http.get<any>(`${this.baseUrl}/cities/${cityId}`).subscribe((data) => {
      this.selectedCityName = data?.cityName || '';
      this.cdr.detectChanges();
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
    if (!this.selectedProject || !this.selectedYear) return;
    this.isLoading = true;
    this.isSubmitted = true;
    this.cdr.detectChanges();
    const projectId = this.selectedProject?.projectDetailsId ?? this.selectedProject;
    this.http
      .get(`${this.baseUrl}/projectHoliday/${projectId}/${this.selectedCountry}/${this.selectedYear.yearId}`)
      .subscribe((data: any) => {
        this.holidays = data;
        if (data && data.length > 0) {
          this.selectedCityName = data[0]?.cityId?.cityName || '';
        }
        this.flattenData();
        setTimeout(() => {
          this.isLoading = false;
          this.cdr.detectChanges();
        }, 200);
      });
  }

  flattenData() {
    this.tableData = [];
    this.holidays.forEach((res: any) => {
      res.dates.forEach((d: any) => {
        const holidayNames = d.holidays.map((h: any) => h.holidayName).join(', ');
        const firstHoliday = d.holidays[0];
        this.tableData.push({
          dateId: d.dateId,
          countryName: res.country.countryName,
          year: res.year.year,
          date: d.date,
          day: d.day,
          holidays: holidayNames,
          holidayList: d.holidays.map((h: any) => ({
            holidayId: h.HolidayId || h.holidayId,
            holidayName: h.holidayName,
          })),
          comments: d.comment,
          statusId: firstHoliday?.statusId || null,
          statusCode: firstHoliday?.statusCode || '',
          statusDescription: firstHoliday?.statusDescription || '',
        });
      });
    });
    this.tableData.sort((a, b) => new Date(a.date).getTime() - new Date(b.date).getTime());
  }

  loadDates() {
    if (!this.selectedYear) return;
    this.http.get<any[]>(`${this.baseUrl}/date/${this.selectedYear.yearId}`).subscribe((data) => {
      this.dates = data;
      this.enabledDates = this.dates.map((d: any) => new Date(d.date));
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
    // Cancel any previous inline edit without saving
    this.cancelInlineEdit();

    // Load available dates for the datepicker
    this.loadDates();

    // Set the row being edited
    this.inlineEditingDateId = row.dateId;

    // Deep-clone the holiday list so edits don't mutate tableData directly
    this.inlineEditData = {
      holidays: row.holidayList.map((h: any) => ({
        holidayId: h.holidayId,
        holidayName: h.holidayName,
        dateObj: new Date(row.date),
        dateId: row.dateId,
      })),
    };
  }

  /** Returns true if the given row is currently being edited inline */
  isInlineEditing(row: any): boolean {
    return this.inlineEditingDateId === row.dateId;
  }

  /** Add a new blank holiday entry to the inline edit row */
  inlineAddHoliday() {
    const firstHoliday = this.inlineEditData.holidays[0];
    this.inlineEditData.holidays.push({
      holidayId: null,
      holidayName: '',
      dateObj: firstHoliday ? new Date(firstHoliday.dateObj) : new Date(),
      dateId: firstHoliday ? firstHoliday.dateId : null,
    });
  }

  /** Remove a holiday entry from the inline edit row */
  inlineRemoveHoliday(index: number) {
    if (this.inlineEditData.holidays.length > 1) {
      this.inlineEditData.holidays.splice(index, 1);
    }
  }

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
    // Validate
    for (const h of this.inlineEditData.holidays) {
      if (!h.holidayName.trim()) {
        alert('Please fill all holiday names');
        return;
      }
      if (!h.dateId) {
        alert('Please select a valid date for all holidays');
        return;
      }
    }

    // Build payload — group holidays by dateId
    const dateMap = new Map<number, { dateId: number; holidays: any[] }>();
    for (const h of this.inlineEditData.holidays) {
      if (!dateMap.has(h.dateId!)) {
        dateMap.set(h.dateId!, {
          dateId: h.dateId!,
          holidays: [],
        });
      }
      dateMap.get(h.dateId!)!.holidays.push({
        holidayId: h.holidayId,
        holidayName: h.holidayName.trim(),
      });
    }

    const payload = {
      countryId: this.selectedCountry,
      yearId: this.selectedYear.yearId,
      dates: [...dateMap.values()],
    };

    this.http.put(`${this.baseUrl}/update/projectHoliday`, payload, { responseType: 'text' }).subscribe({
      next: () => {
        alert('Updated successfully');
        this.inlineEditingDateId = null;
        this.onSubmit();
      },
      error: (err) => {
        const message = err?.error?.message || err?.message || 'Something went wrong';
        alert(message);
      },
    });
  }

  /** Discard inline edits and restore view mode */
  cancelInlineEdit() {
    this.inlineEditingDateId = null;
    this.inlineEditData = { holidays: [] };
  }

  // ==================== ADD MODE ====================

  onAdd() {
    if (!this.selectedYear) {
      alert('Select year first');
      return;
    }
    this.loadDates();
    this.isEditMode = false;
    this.editingRow = null;
    this.selectedDate = null as any;
    this.selectedDateObj = null as any;
    this.holidayForm = { holidayName: '', comment: '', createdBy: '' };
    this.isCommentDisabled = false;

    this.dateHolidayList = [
      { date: null, dateId: null, comment: ' ', disabled: false, holidays: [{ holidayName: '' }] }
    ];

    this.addVisible = true;
  }

  onDateSelectForIndex(dateIndex: number) {
    const selectedObj = this.dateHolidayList[dateIndex].date;
    if (!selectedObj) return;

    const year = selectedObj.getFullYear();
    const month = String(selectedObj.getMonth() + 1).padStart(2, '0');
    const day = String(selectedObj.getDate()).padStart(2, '0');
    const selectedDateStr = `${year}-${month}-${day}`;

    const selected = this.dates.find((d: any) => d.date === selectedDateStr);
    if (selected) {
      this.dateHolidayList[dateIndex].dateId = selected.dateId;
      const existingRow = this.tableData.find(row => row.dateId === selected.dateId);
      if (existingRow) {
        this.dateHolidayList[dateIndex].comment = existingRow.comments ?? '';
        this.dateHolidayList[dateIndex].disabled = true;
      } else {
        this.dateHolidayList[dateIndex].comment = '';
        this.dateHolidayList[dateIndex].disabled = false;
      }
    } else {
      this.dateHolidayList[dateIndex].dateId = null;
      this.dateHolidayList[dateIndex].disabled = false;
    }
  }

  removeHoliday(dateIndex: number, holidayIndex: number) {
    this.dateHolidayList[dateIndex].holidays.splice(holidayIndex, 1);
  }

  addAnotherHoliday(dateIndex: number) {
    this.dateHolidayList[dateIndex].holidays.push({ holidayName: '' });
  }

  removeDate(dateIndex: number) {
    this.dateHolidayList.splice(dateIndex, 1);
  }

  addAnotherDate() {
    this.dateHolidayList.push({ date: null, dateId: null, comment: ' ', disabled: false, holidays: [{ holidayName: '' }] });
  }

  saveHoliday() {
    const normalize = (s: string) => s.trim().replace(/\s+/g, '').toLowerCase();

    // ==================== ADD MODE ====================
    const missingDate = this.dateHolidayList.find(d => !d.dateId);
    if (missingDate) {
      alert('Please select date for all entries');
      return;
    }

    for (const dateEntry of this.dateHolidayList) {
      const empty = dateEntry.holidays.find(h => !h.holidayName.trim());
      if (empty) {
        alert('Please fill all holiday names');
        return;
      }
    }

    const seen = new Map<number, string[]>();
    for (const dateEntry of this.dateHolidayList) {
      if (!dateEntry.dateId) continue;
      const key = dateEntry.dateId;
      const names = dateEntry.holidays.map(h => normalize(h.holidayName));
      if (!seen.has(key)) seen.set(key, []);
      for (const name of names) {
        if (seen.get(key)!.includes(name)) {
          alert(`Duplicate holiday in same submission: "${name}"`);
          return;
        }
        seen.get(key)!.push(name);
      }
    }

    for (const dateEntry of this.dateHolidayList) {
      const existingRow = this.tableData.find(row => row.dateId === dateEntry.dateId);
      if (existingRow) {
        const existingHolidays = existingRow.holidays
          .split(',')
          .map((h: string) => normalize(h));
        const duplicate = dateEntry.holidays.find(h =>
          existingHolidays.includes(normalize(h.holidayName))
        );
        if (duplicate) {
          alert(`Holiday already exists: ${duplicate.holidayName}`);
          return;
        }
      }
    }

    const payload = {
      projectId: this.selectedProject?.projectDetailsId ?? this.selectedProject,
      countryId: this.selectedCountry,
      yearId: this.selectedYear.yearId,
      dates: this.dateHolidayList.map(dateEntry => ({
        dateId: dateEntry.dateId,
        comment: dateEntry.comment,
        createdBy: 'admin',
        holidays: dateEntry.holidays.map(h => ({
          holidayName: h.holidayName.trim(),
        })),
      })),
    };

    this.http.post(`${this.baseUrl}/projectHolidaySave`, payload, { responseType: 'text' }).subscribe({
      next: () => {
        alert('Saved successfully');
        this.addVisible = false;
        this.onSubmit();
      },
      error: (err) => {
        const message = err?.error?.message || err?.error || 'Something went wrong';
        alert(message);
      },
    });
  }

  onHolidaySelect() {
    this.holidayForm.holidayName = this.selectedHoliday.holidayName;
  }

  searchHoliday(event: any) {
    const query = event.query?.toLowerCase() || '';
    this.filteredHolidays = this.holidayOptions.filter((h: any) =>
      h.holidayName.toLowerCase().includes(query)
    );
  }

  onDateSelectEdit() {
    const year = this.selectedDateObj.getFullYear();
    const month = String(this.selectedDateObj.getMonth() + 1).padStart(2, '0');
    const day = String(this.selectedDateObj.getDate()).padStart(2, '0');
    const selectedDateStr = `${year}-${month}-${day}`;
    const selected = this.dates.find((d: any) => d.date === selectedDateStr);
    if (selected) {
      this.selectedDate = selected.dateId;
      const existing = this.tableData.find((row: any) => row.dateId === this.selectedDate);
      if (existing) {
        this.holidayForm.comment = existing.comments;
        this.isCommentDisabled = true;
      } else {
        this.holidayForm.comment = '';
        this.isCommentDisabled = false;
      }
    } else {
      this.selectedDate = null as any;
      this.selectedDateObj = null as any;
    }
  }

  // ==================== COPY ====================

  openCopyDialog() {
    this.http
      .get(`${this.baseUrl}/holidays/${this.selectedCountry}/${this.selectedYear.yearId}`)
      .subscribe((data: any) => {
        const today = new Date();
        today.setHours(0, 0, 0, 0);

        const filteredData = data.map((res: any) => ({
          ...res,
          dates: res.dates.filter((d: any) => {
            const holidayDate = new Date(d.date);
            holidayDate.setHours(0, 0, 0, 0);
            return holidayDate >= today;
          }),
        })).filter((res: any) => res.dates.length > 0);

        this.globalHolidayData = this.flattenCopyData(filteredData);
        this.copyDialogVisible = true;
      });
  }

  flattenCopyData(data: any[]): any[] {
    const existingKeys = new Set(
      this.tableData.flatMap((row: any) => {
        const monthDay = row.date.substring(5);
        return row.holidays.split(',').map((h: string) =>
          `${monthDay}_${h.trim().toLowerCase()}`
        );
      })
    );

    const dateMap = new Map<string, any>();
    data.forEach((res: any) => {
      res.dates.forEach((d: any) => {
        const dateKey = d.date;
        if (!dateMap.has(dateKey)) {
          dateMap.set(dateKey, {
            date: d.date,
            day: d.day,
            dateId: d.dateId,
            comments: d.comment,
            holidayList: d.holidays,
            holidays: [],
          });
        }
        d.holidays.forEach((h: any) => {
          const monthDay = d.date.substring(5);
          const key = `${monthDay}_${h.holidayName.trim().toLowerCase()}`;
          const isCopied = existingKeys.has(key);
          dateMap.get(dateKey).holidays.push({
            holidayName: h.holidayName,
            holidayId: h.holidayId || h.HolidayId,
            selected: false,
            isCopied: isCopied,
          });
        });
      });
    });

    this.isAllCopied = [...dateMap.values()].every(d => d.holidays.every((h: any) => h.isCopied));

    return [...dateMap.values()].sort(
      (a, b) => new Date(a.date).getTime() - new Date(b.date).getTime()
    );
  }

  copySelectedHolidays() {
    const selectedRows = this.globalHolidayData
      .flatMap((d: any) => d.holidays)
      .filter((h: any) => h.selected && !h.isCopied);

    if (selectedRows.length === 0) {
      alert('Please select at least one holiday');
      return;
    }

    const payload = {
      projectId: this.selectedProject?.projectDetailsId ?? this.selectedProject,
      countryId: this.selectedCountry,
      yearId: this.selectedYear.yearId,
      createdBy: 'admin',
      holidayIds: selectedRows.map((row) => row.holidayId),
    };

    this.http.post(`${this.baseUrl}/copyProjectHoliday`, payload).subscribe({
      next: () => {
        this.copyDialogVisible = false;
        this.selectAllChecked = false;
        this.cdr.detectChanges();
        setTimeout(() => {
          this.onSubmit();
          alert('Holidays copied to project successfully');
        }, 150);
      },
      error: (err) => {
        const message = err?.error?.message || err?.error || 'Something went wrong';
        alert(message);
      },
    });
  }

  selectAll(event: any) {
    const isChecked = event.checked ?? event.target?.checked;
    this.copyTableData.forEach((row) => {
      if (!row.isCopied) row.selected = isChecked;
    });
  }

  toggleSelectAll(event: any) {
    const isChecked = event.checked;
    this.globalHolidayData.forEach((row: any) => {
      row.holidays.forEach((h: any) => {
        if (!h.isCopied) h.selected = isChecked;
      });
      row.allSelected = isChecked && !this.isRowAllCopied(row);
    });
  }

  updateSelectAllState() {
    const allHolidays = this.globalHolidayData.flatMap((d: any) => d.holidays);
    const available = allHolidays.filter((h: any) => !h.isCopied);
    this.selectAllChecked = available.length > 0 && available.every((h: any) => h.selected);
    this.globalHolidayData.forEach((row: any) => {
      const rowAvailable = row.holidays.filter((h: any) => !h.isCopied);
      row.allSelected = rowAvailable.length > 0 && rowAvailable.every((h: any) => h.selected);
    });
  }

  closeCopyDialog() {
    this.copyDialogVisible = false;
  }

  isRowAllCopied(row: any): boolean {
    return row.holidays.every((h: any) => h.isCopied);
  }

  toggleRowSelect(row: any, event: any) {
    const isChecked = event.checked;
    row.holidays.forEach((h: any) => {
      if (!h.isCopied) h.selected = isChecked;
    });
    this.updateSelectAllState();
  }
}
