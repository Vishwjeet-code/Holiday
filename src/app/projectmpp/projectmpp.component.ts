import { CommonModule } from '@angular/common';
import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { FormsModule } from '@angular/forms';



import { ButtonModule } from 'primeng/button';
import { TableModule } from 'primeng/table';

import { InputTextModule } from 'primeng/inputtext';

import { HttpClient } from '@angular/common/http';
import { IconFieldModule } from 'primeng/iconfield';
import { InputIconModule } from 'primeng/inputicon';
import { CheckboxModule } from 'primeng/checkbox';
import { ProgressSpinnerModule } from 'primeng/progressspinner';
import { RadioButtonModule } from 'primeng/radiobutton';
import { Router } from '@angular/router';
import { TreeTableModule } from 'primeng/treetable';
import { TreeNode } from 'primeng/api';



@Component({
  selector: 'app-projectmpp',
  imports: [CommonModule,
    FormsModule,

    ButtonModule,
    TableModule,
     RadioButtonModule,
       FormsModule,
  TreeTableModule,

    InputTextModule,
    InputIconModule,
    IconFieldModule,
    ProgressSpinnerModule,
    CheckboxModule],
  templateUrl: './projectmpp.component.html',
  styleUrl: './projectmpp.component.css'
})
export class ProjectmppComponent {
selectedFile: File | null = null;
treeData: TreeNode[] = [];

constructor(private http: HttpClient, private cdr: ChangeDetectorRef,private router: Router) {}
  private baseUrl = 'https://exhaust-hash-activated-described.trycloudflare.com/api';


  ngOnInit(): void {
  this.loadTasks();
}


  onFileSelected(event: any) {
  if (event.target.files.length > 0) {
    this.selectedFile = event.target.files[0];
  }
}

uploadFile() {
  if (!this.selectedFile) {
    alert('Please select an MPP file');
    return;
  }

  const formData = new FormData();
  formData.append('file', this.selectedFile);

  this.http.post(
    `${this.baseUrl}/import`,
    formData,
    { responseType: 'text' }   // <-- tell Angular the body is plain text, not JSON
  ).subscribe({
    next: (res) => {
      console.log('import success:', res); // res is now just the string
      this.loadTasks();
      alert('MPP imported successfully');
    },
    error: (err) => {
      console.error('Full Error:', err);
      alert('Import failed: ' + (err.error ?? err.message));
    }
  });
}

loadTasks() {
  console.log('tasks fetch start', Date.now());
  this.http.get<any[]>(`${this.baseUrl}/tasks`).subscribe(data => {
    console.log('tasks fetch done', Date.now(), data.length, 'tasks');
    this.treeData = this.buildTree(data);
    this.cdr.detectChanges();
  });
}
buildTree(tasks: any[]): TreeNode[] {

  const map: any = {};
  const roots: TreeNode[] = [];

  tasks.forEach(task => {

    map[task.mppTaskId] = {
      data: task,
      children: []
    };

  });

  tasks.forEach(task => {

    if (task.parentTaskId) {

      const parent = map[task.parentTaskId];

      if (parent) {
        parent.children.push(
          map[task.mppTaskId]
        );
      }

    } else {

      roots.push(
        map[task.mppTaskId]
      );

    }

  });

  return roots;
}
}



