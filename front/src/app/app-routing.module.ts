import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { RegistrationComponent } from './register/registration.component';
import {Authorized} from './guard/authorized.guard';
import {Admin} from './guard/admin.guard';
import {Notauthorized} from './guard/notauthorized.guard';
import { RepositoryComponent } from './repository/repository.component';
import { VerificationComponent } from './verification/verification.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { PanelComponent } from './panel/panel.component';
import { TaskComponent } from './task/task.component';
import { Editor } from './guard/editor.guard';
import { MagazineComponent } from './magazine/magazine.component';
import { ListMagazinesComponent } from './list-magazines/list-magazines.component';
import { ListUsersComponent } from './list-users/list-users.component';

const routes: Routes = [
  {
    path: "registration",
    component: RegistrationComponent,
    canActivate: [Notauthorized]
  },
  {
    path: 'repository',
    component: RepositoryComponent,
    canActivate: [Authorized]
  },
  {
    path: 'verification/:token',
    component: VerificationComponent
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [Notauthorized]
  },
  {
    path: 'panel',
    component: PanelComponent,
    canActivate: [Authorized]
  },
  {
    path: 'tasks/:taskName/:taskId',
    component: TaskComponent,
    canActivate: [Authorized]
  },
  {
    path: 'new-magazine',
    component: MagazineComponent,
    canActivate: [Editor]
  },
  {
    path: 'magazines',
    component: ListMagazinesComponent
  },
  {
    path: 'users',
    component: ListUsersComponent,
    canActivate: [Admin]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [Admin, Authorized, Notauthorized, Editor],
})
export class AppRoutingModule { }
