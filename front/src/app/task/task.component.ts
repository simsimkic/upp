import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { RepositoryService } from '../services/repository.service';
import { UserService } from '../services/user.service';
import { MagazineService } from '../services/magazine.service';
import { FieldService } from '../services/field.service';

@Component({
  selector: 'app-task',
  templateUrl: './task.component.html',
  styleUrls: ['./task.component.css']
})
export class TaskComponent implements OnInit {

  taskName = '';
  taskId = '';
  data: Object;
  private formFieldsDto = null;
  private formFields = [];
  private enumValues = [];
  magazineTitle = '';

  constructor(private route: ActivatedRoute, private repositoryService: RepositoryService,
    private userService: UserService, private magazineService: MagazineService, private router: Router,
    private fieldService: FieldService) { }

  ngOnInit() {
    this.taskName = this.route.snapshot.paramMap.get('taskName');
    this.taskId = this.route.snapshot.paramMap.get('taskId');

    if (this.taskName == 'reviewer-approval') {
      this.repositoryService.getDataForReviewerApproval(this.taskId).subscribe(data => {
        this.data = data;
      })
    } else if (this.taskName == 'add-editors-reviewers') {
      this.data = new Object();
      this.userService.getEditors(this.taskId).subscribe(editors => {
        this.data['editors'] = editors;
      })
      this.userService.getReviewers(this.taskId).subscribe(reviewers => {
        this.data['reviewers'] = reviewers;
      })
      this.repositoryService.getMagazineForStaffSetup(this.taskId).subscribe(res => {
        this.magazineTitle = res.title;
      })
    } else if (this.taskName == 'magazine-approval') {
      this.magazineService.getDataForMagazineApproval(this.taskId).subscribe(data => {
        this.data = data;
      })
    } else if (this.taskName == 'update-data') {
      this.magazineService.getDataForMagazineApproval(this.taskId).subscribe(data => {
        this.data = data;
      })
      this.getUpdateForm();
    }
  }

  getUpdateForm() {
    let x = this.magazineService.getUpdateForm(this.taskId);

    x.subscribe(
      res => {
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.formFields.forEach( (field) =>{
          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });
      },
      err => {
        alert('Error!');
      }
    );
  }

  approved() {
    if (this.taskName == 'reviewer-approval') {
      this.userService.approveReviewer(this.taskId).subscribe(res => {
        alert('Reviewer approved!');
        this.router.navigateByUrl('/panel');
      },
      err => {
        alert('Error ocurred!');
      })
    } else if (this.taskName == 'magazine-approval') {
      this.magazineService.approveMagazine(this.taskId).subscribe(res => {
        alert('Magazine approved!');
        this.router.navigateByUrl('/panel');
      },
      err => {
        alert('Error ocurred!');
      })
    }
  }

  denied() {
    if (this.taskName == 'reviewer-approval') {
      this.userService.denyReviewer(this.taskId).subscribe(res => {
        alert('Reviewer denied!');
        this.router.navigateByUrl('/panel');
      },
      err => {
        alert('Error ocurred!');
      })
    } else if (this.taskName == 'magazine-approval') {
      this.magazineService.denyMagazine(this.taskId).subscribe(res => {
        alert('Magazine denied!');
        this.router.navigateByUrl('/panel');
      },
      err => {
        alert('Error ocurred!');
      })
    }
  }

  update(value, form){
    let o = new Array();
    for (var property in value) {
      if (Array.isArray(value[property])) {
        value[property] = value[property].toString();
      }
      o.push({fieldId : property, fieldValue : value[property]});
    }
    let newMagazineData = new Object();
    o.forEach(element => {
        newMagazineData[element.fieldId] = element.fieldValue;
    });
    this.magazineService.updateMagazine(newMagazineData, this.data['issn']).subscribe(res=>{
      alert('Successfuly updated!');
      this.repositoryService.completeTask(this.taskId).subscribe(res=>{
        this.router.navigateByUrl('/panel');
      })
    },
    err => {
      alert('Error while updating!');
    })

  }

  add(value, form) {
    let o = new Array();
    let chosenEditors = new Array();
    for (var property in value) {
      if (Array.isArray(value[property])) {
        value[property] = value[property].toString();
      }
      if (chosenEditors.includes(value[property])) {
        alert('One editor cannot be chosen 2 times!')
        return;
      }
      if (property.includes('editors')) {
        chosenEditors.push(value[property]);
      }
      o.push({fieldId : property, fieldValue : value[property]});
    }
    let finalEditors = '';
    let finalReviewers = '';
    o.forEach(element => {
      if (element.fieldId.includes('editors')) {
        finalEditors += element.fieldValue + ',';
      } else if (element.fieldId == 'reviewers') {
        finalReviewers = element.fieldValue;
      }
    });
    if (finalReviewers.split(',').length < 2) {
      alert('You need to select at least 2 reviewers.');
      return;
    }
    finalEditors = finalEditors.substring(0, finalEditors.length-1);

    let chosen = new Object();
    chosen['editors'] = finalEditors;
    chosen['reviewers'] = finalReviewers;
    this.magazineService.setEditorsAndReviewers(this.taskId, chosen).subscribe(res => {
      this.repositoryService.completeTask(this.taskId).subscribe(res => {
        alert('Great!');
        this.router.navigateByUrl('/panel');
      })
    })
  }

}
