import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserService } from '../services/user.service';
import { RepositoryService } from '../services/repository.service';
import { RegistrationComponent } from '../register/registration.component';
import { Subscription } from 'rxjs';
import {Router} from '@angular/router';

@Component({
  selector: 'app-verification',
  templateUrl: './verification.component.html',
  styleUrls: ['./verification.component.css']
})
export class VerificationComponent implements OnInit {

  token: String;
  processId: String

  constructor(
    private route: ActivatedRoute,
    private userService: UserService,
    private repositoryService: RepositoryService,
    private router: Router
  ) {}

  ngOnInit() {
    this.token = this.route.snapshot.paramMap.get('token');
  }

  verify() {
    this.repositoryService.getTaskId('registration_process').subscribe(taskId => {
      let x = this.userService.verifyAccount(this.token, taskId.taskId);
      x.subscribe(
        res => {
          this.repositoryService.completeTask(taskId.taskId).subscribe(res => {
            alert("Successfuly verified account.");
          });
        },
        err => {
          alert("Error occured!");
        }
      );
    })
  }

}
