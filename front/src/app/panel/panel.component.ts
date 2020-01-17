import { Component, OnInit } from '@angular/core';
import { RepositoryService } from '../services/repository.service';
import { Router } from '@angular/router';
import { FieldService } from '../services/field.service';

@Component({
  selector: 'app-panel',
  templateUrl: './panel.component.html',
  styleUrls: ['./panel.component.css']
})
export class PanelComponent implements OnInit {

  tasks = [];
  noTasks = false;
  role = '';

  constructor(private repositoryService: RepositoryService, private router: Router,
    private fieldService: FieldService) { }

  ngOnInit() {
    if (localStorage.role == 'editor') {
      this.role = 'editor';
    } else if(localStorage.role == 'admin') {
      this.role = 'admin';
    }
  }

  getTasks(){
    let x = this.repositoryService.getTasksForUser(JSON.parse(localStorage.user).username);

    x.subscribe(
      data => {
        data.forEach(element => {
          this.tasks.push(element);
        });
        if (this.tasks.length == 0) {
          this.noTasks = true;
        } else {
          this.noTasks = false;
        }
      },
      err => {
        alert('Error!');
      }
    );
   }

  seeTask(taskId, taskName) {
    if (taskName == 'Approve reviewer') {
      taskName = 'reviewer-approval';
    } else if (taskName == 'Add editors and reviewers') {
      taskName = 'add-editors-reviewers';
    } else if (taskName == 'Approve new magazine') {
      taskName = 'magazine-approval';
    } else if (taskName == 'Update data') {
      taskName = 'update-data';
    }
    this.router.navigateByUrl('/tasks/' + taskName + '/' + taskId);
  }

  seeUsers() {
    this.router.navigateByUrl('/users');
  }

  createMagazine() {
    this.router.navigateByUrl('/new-magazine');
  }

}
