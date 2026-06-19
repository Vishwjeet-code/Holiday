import { RouterLink, RouterLinkActive } from '@angular/router';
import { Component, ViewChild } from '@angular/core';
import { DrawerModule } from 'primeng/drawer';
import { ButtonModule } from 'primeng/button';
import { Ripple } from 'primeng/ripple';
import { AvatarModule } from 'primeng/avatar';
import { StyleClass } from 'primeng/styleclass';
import { Drawer } from 'primeng/drawer';

@Component({
  selector: 'app-sidebar',
  imports: [DrawerModule, ButtonModule, AvatarModule,  RouterLink, RouterLinkActive],
  templateUrl: './sidebar.component.html',
  standalone: true,
  styleUrls:['./sidebar.component.css']
})
export class SidebarComponent {
  @ViewChild('drawerRef') drawerRef!: Drawer;

    closeCallback(e: Event): void {
        this.drawerRef.close(e);
    }

    visible: boolean = true;

}
