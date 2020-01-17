import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { RegistrationComponent } from './register/registration.component';
import { FormsModule }   from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RepositoryComponent } from './repository/repository.component';
import { VerificationComponent } from './verification/verification.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { PanelComponent } from './panel/panel.component';
import { TaskComponent } from './task/task.component';
import { MagazineComponent } from './magazine/magazine.component';
import { ToolbarComponent } from './toolbar/toolbar.component';
import { ListMagazinesComponent } from './list-magazines/list-magazines.component';
import { ListUsersComponent } from './list-users/list-users.component';


@NgModule({
  declarations: [
    AppComponent,
    RegistrationComponent,
    RepositoryComponent,
    VerificationComponent,
    LoginComponent,
    HomeComponent,
    PanelComponent,
    TaskComponent,
    MagazineComponent,
    ToolbarComponent,
    ListMagazinesComponent,
    ListUsersComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule,
    FormsModule,
    HttpModule
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
