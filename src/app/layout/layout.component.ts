import { Component } from '@angular/core';
import { SidebarComponent } from "../sidebar/sidebar.component";
import { RouterOutlet } from "@angular/router";
import { NavbaarComponent } from "../navbaar/navbaar.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-layout',
  imports: [SidebarComponent, NavbaarComponent, CommonModule,RouterOutlet],
  templateUrl: './layout.component.html',
  styleUrl: './layout.component.css'
})
export class LayoutComponent {

}
