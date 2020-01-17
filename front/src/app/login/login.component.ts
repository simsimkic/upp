import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
  }

  onSubmit(value, form){

    let o = new Object();
    for (var property in value) {
      o[property] = value[property];
    }

    let x = this.userService.login(o);
    x.subscribe(user => {
      this.updateLocalStorage(user);
      this.router.navigateByUrl('/panel');
    },
    err => {
      alert('Password or username is incorrect! Try again.');
    })
  }

  updateLocalStorage(user) {
    localStorage.user = JSON.stringify(user);
    switch (user.role) {
      case 1:
        localStorage.role = 'user';
        break;
      case 2:
        localStorage.role = 'reveiwer';
        break;
      case 3:
        localStorage.role = 'editor';
        break;
      case 4:
        localStorage.role = 'author';
        break;
      case 5:
        localStorage.role = 'admin';
        break;
    }
  }

}
