import { Routes } from '@angular/router';
import { globalholidayComponent } from './GlobalHoliday/globalholiday.component';
import { NavbaarComponent } from './navbaar/navbaar.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { LayoutComponent } from './layout/layout.component';
import { ProjectHolidayComponent } from './project-holiday/project-holiday.component';
import { ProjectSelectionComponent } from './project-selection/project-selection.component';
import { Component } from '@angular/core';
import { ProjectWorkingDayComponent } from './project-working-day/project-working-day.component';
import { ProjectmppComponent } from './projectmpp/projectmpp.component';


export const routes: Routes = [

 {path:'', component:LayoutComponent,

  children:[
    {path:'globalholiday', component:globalholidayComponent},
    {path:'projectholiday', component:ProjectHolidayComponent},
    {path:'projectselection', component: ProjectSelectionComponent},
    {path:'ProjectWorkingDay', component: ProjectWorkingDayComponent},
    {path:'Projectmpp', component:ProjectmppComponent}

  ]
 },




];
