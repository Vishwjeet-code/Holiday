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
import { RadioButtonModule } from 'primeng/radiobutton';
import { Router } from '@angular/router';

@Component({
  selector: 'app-project-selection',
  imports: [CommonModule,
    FormsModule,

    ButtonModule,
    TableModule,
     RadioButtonModule,

    InputTextModule,
    InputIconModule,
    IconFieldModule,
    ProgressSpinnerModule,
    CheckboxModule,],
  templateUrl: './project-selection.component.html',
  styleUrl: './project-selection.component.css'
})
export class ProjectSelectionComponent {

  selectedProject: any;
  selectedProjectId: number | null = null;
selectedProjectName: string | null = null;
  filteredProjects: any[] = [];
  Projects: any;
  selectedFile: File | null = null;

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef,private router: Router) {}
  private baseUrl = 'https://exhaust-hash-activated-described.trycloudflare.com/api';


    ngOnInit(): void {

    this.loadProjects();
  }
  loadProjects() {
    this.http.get<any[]>(`${this.baseUrl}/projects/all`).subscribe((data) => {
      this.Projects = data;
    });
  }
    searchProjects(event: any) {
    const query = event.query?.toLowerCase() || '';
    this.filteredProjects = this.Projects.filter((P: any) =>
      P.projectName.toString().toLowerCase().includes(query)
    );
  }
    onProjectSelect() {

     this.selectedProjectId = this.selectedProject.projectDetailsId;

  this.selectedProjectName = this.selectedProject.projectName;
  localStorage.setItem(
    'selectedProject',
    JSON.stringify({
      projectId: this.selectedProjectId,
      projectName: this.selectedProjectName
    })
  );
  }

}
