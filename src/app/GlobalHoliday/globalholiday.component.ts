import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';




import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';
import { DialogModule } from 'primeng/dialog';

import { InputTextModule } from 'primeng/inputtext';
import { AutoComplete } from 'primeng/autocomplete';
import { DatePickerModule } from 'primeng/datepicker';
import { HttpClient } from '@angular/common/http';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { CheckboxModule } from 'primeng/checkbox';
import { ProgressSpinnerModule } from 'primeng/progressspinner';

@Component({
  selector: 'app-filterpage',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    AutoComplete,
    ButtonModule,
    TableModule,

    DialogModule,
    InputTextModule,
    DatePickerModule,
    InputIconModule,
    IconFieldModule,
    ProgressSpinnerModule,
    CheckboxModule,
  ],
  templateUrl: './globalholiday.component.html',
  styleUrl: './globalholiday.component.css',
})
export class globalholidayComponent implements OnInit {

 isAllCopied: boolean = false;
  selectAllChecked: boolean = false;
  holidayOptions: any[] = [];
  selectedHoliday: any = null;
  filteredHolidays: any[] = [];
  copyTableData: any[] = [];

  // ==================== INLINE EDIT STATE ====================
  // The dateId of the row currently being edited inline (null = no row editing)
  inlineEditingDateId: number | null = null;

  // Holds the mutable edit data for the row being edited:
  // { comment, holidays: [{ holidayId, holidayName, dateObj, dateId }] }
  inlineEditData: {
    comment: string;
    holidays: { holidayId: any; holidayName: string; dateObj: Date; dateId: number | null }[];
  } = { comment: '', holidays: [] };

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
  selectedYear: any;
  nextYear: any;
  filteredYears: any[] = [];
  holidays: any[] = [];
  tableData: any[] = [];
  copyDialogVisible: boolean = false;
  selectedProject: any;
  selectedProjectId: any;
  selectedProjectName: any;

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef) {}
  private baseUrl = 'https://exhaust-hash-activated-described.trycloudflare.com/api';

  ngOnInit(): void {
    this.loadCountries();
    this.loadYears();
     const stored = localStorage.getItem('selectedProject');
  if (stored) {
    const parsed = JSON.parse(stored);
    this.selectedProjectName = parsed.projectName;
    this.selectedProjectId = parsed.projectId;
  }
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

  onYearSelect() {
    const year = this.selectedYear.year;
    this.minDate = new Date(year, 0, 1);
    this.maxDate = new Date(year, 11, 31);
    this.onSubmit();
  }

  loadYears() {
    this.http.get<any[]>(`${this.baseUrl}/years/all`).subscribe((data) => {
      const currentYear = new Date().getFullYear();
      this.years = data.filter((y: any) => y.year >= currentYear);
      const current = this.years.find((y: any) => y.year == currentYear);
      this.selectedYear = current ? current : this.years[0];
      this.filteredYears = this.years;
      this.onYearSelect();
    });
  }


  onSubmit() {
    this.isLoading = true;
    this.isSubmitted = true;
    this.cdr.detectChanges();
    this.http
      .get(`${this.baseUrl}/holidays/${this.selectedCountry}/${this.selectedYear.yearId}`)
      .subscribe((data: any) => {
        this.holidays = data;
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

  addAnotherDate() {
    this.dateHolidayList.push({ date: null, dateId: null, comment: ' ', disabled: false, holidays: [{ holidayName: '' }] });
  }

  removeDate(dateIndex: number) {
    this.dateHolidayList.splice(dateIndex, 1);
  }

  addAnotherHoliday(dateIndex: number) {
    this.dateHolidayList[dateIndex].holidays.push({ holidayName: '' });
  }

  removeHoliday(dateIndex: number, holidayIndex: number) {
    this.dateHolidayList[dateIndex].holidays.splice(holidayIndex, 1);
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
      comment: row.comments ?? '',
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
    const dateMap = new Map<number, { dateId: number; comment: string; holidays: any[] }>();
    for (const h of this.inlineEditData.holidays) {
      if (!dateMap.has(h.dateId!)) {
        dateMap.set(h.dateId!, {
          dateId: h.dateId!,
          comment: this.inlineEditData.comment,
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

    this.http.put(`${this.baseUrl}/update`, payload, { responseType: 'text' }).subscribe({
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
    this.inlineEditData = { comment: '', holidays: [] };
  }

  // ==================== ADD SAVE ====================

  saveHoliday() {
    const normalize = (s: string) => s.trim().replace(/\s+/g, '').toLowerCase();

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
      countryId: this.selectedCountry,
      yearId: this.selectedYear.yearId,
      dates: this.dateHolidayList.map(dateEntry => ({
        dateId: dateEntry.dateId,
        comment: dateEntry.comment,
        holidays: dateEntry.holidays.map(h => ({
          holidayName: h.holidayName.trim(),
        })),
      })),
    };

    this.http.post(`${this.baseUrl}/save`, payload).subscribe({
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

  // ==================== COPY ====================

  openCopyDialog() {
   const prevYearNumber = this.selectedYear.year - 1;
    this.http.get<any[]>(`${this.baseUrl}/years/all`).subscribe((allYears) => {
    const prevYearObj = allYears.find((y) => y.year == prevYearNumber);

    if (!prevYearObj) {
      alert('Previous year data not found');
      return;
    }

    this.http
      .get(`${this.baseUrl}/holidays/${this.selectedCountry}/${prevYearObj.yearId}`)
      .subscribe((data: any) => {
        this.copyTableData = this.flattenCopyData(data);
        this.copyDialogVisible = true;
      });
  });
  }

  copySelectedHolidays() {
    const selectedHolidays = this.copyTableData
      .flatMap((row: any) =>
        row.holidays
          .filter((h: any) => h.selected && !h.isCopied)
          .map((h: any) => ({ row, holiday: h }))
      );
    if (selectedHolidays.length === 0) {
      alert('Please select at least one holiday');
      return;
    }
    const dateMap = new Map<string, any>();
    selectedHolidays.forEach(({ row, holiday }) => {
      if (!dateMap.has(row.date)) {
        dateMap.set(row.date, {
          dateId: row.dateId,
          date: row.date,
          holidays: [],
          comment: row.comments,
        });
      }
      dateMap.get(row.date).holidays.push({
        holidayId: holiday.holidayId,
        holidayName: holiday.holidayName,
      });
    });
    const payload = {
      fromYear: this.selectedYear.yearId,
      toYear: this.selectedYear.yearId,
      countryId: this.selectedCountry,
      holidays: [...dateMap.values()],
    };
    this.http
      .post(`${this.baseUrl}/copy-selected`, payload, { responseType: 'text' })
      .subscribe(() => {
        this.copyDialogVisible = false;
        this.cdr.detectChanges();
        setTimeout(() => {
          this.refreshCurrentTable();
          alert('Copied successfully');
        }, 150);
      });
  }

  refreshCurrentTable() {
    this.http
      .get(`${this.baseUrl}/holidays/${this.selectedCountry}/${this.selectedYear.yearId}`)
      .subscribe((data: any) => {
        this.holidays = data;
        this.isSubmitted = true;
        this.flattenData();
        this.cdr.detectChanges();
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

  selectAll(event: any) {
    const isChecked = event.checked ?? event.target?.checked;
    this.copyTableData.forEach((row) => {
      if (!row.isCopied) row.selected = isChecked;
    });
  }

  toggleSelectAll(event: any) {
    const isChecked = event.checked;
    this.copyTableData.forEach((row: any) => {
      row.holidays.forEach((h: any) => {
        if (!h.isCopied) h.selected = isChecked;
      });
      row.allSelected = isChecked && !this.isRowAllCopied(row);
    });
  }

  updateSelectAllState() {
    const allHolidays = this.copyTableData.flatMap((d: any) => d.holidays);
    const available = allHolidays.filter((h: any) => !h.isCopied);
    this.selectAllChecked = available.length > 0 && available.every((h: any) => h.selected);
    this.copyTableData.forEach((row: any) => {
      const rowAvailable = row.holidays.filter((h: any) => !h.isCopied);
      row.allSelected = rowAvailable.length > 0 && rowAvailable.every((h: any) => h.selected);
    });
  }

  closeCopyDialog() {
    this.copyDialogVisible = false;
  }

  toggleRowSelect(row: any, event: any) {
    const isChecked = event.checked;
    row.holidays.forEach((h: any) => {
      if (!h.isCopied) h.selected = isChecked;
    });
    row.allSelected = isChecked;
    this.updateSelectAllState();
  }

  isRowAllCopied(row: any): boolean {
    return row.holidays.every((h: any) => h.isCopied);
  }
}


