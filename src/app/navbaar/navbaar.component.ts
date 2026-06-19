import { Component } from '@angular/core';
import { Toolbar } from 'primeng/toolbar';
import { AvatarModule } from 'primeng/avatar';
import { SharedModule } from 'primeng/api';
import { ButtonModule } from 'primeng/button';
import { RouterLink } from "@angular/router";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-navbaar',
  imports: [Toolbar, AvatarModule, ButtonModule,CommonModule],
  templateUrl: './navbaar.component.html',
  styleUrl: './navbaar.component.css'
})
export class NavbaarComponent {
  selectedTab = 'admin';

}
