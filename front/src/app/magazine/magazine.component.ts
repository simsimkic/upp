import { Component, OnInit } from '@angular/core';
import { RepositoryService } from '../services/repository.service';
import { FieldService } from '../services/field.service';
import { UserService } from '../services/user.service';
import { Router } from '@angular/router';
import { MagazineService } from '../services/magazine.service';

@Component({
  selector: 'app-magazine',
  templateUrl: './magazine.component.html',
  styleUrls: ['./magazine.component.css']
})
export class MagazineComponent implements OnInit {

  private formFieldsDto = null;
  private formFields = [];
  private processInstance = "";
  private enumValues = [];

  constructor(private repositoryService: RepositoryService, private fieldService: FieldService,
    private userService: UserService, private router: Router, private magazineService: MagazineService) { }

  ngOnInit() {
    let x = this.repositoryService.startProcess('newMagazine');

    x.subscribe(
      res => {
        this.formFieldsDto = res;
        this.formFields = res.formFields;
        this.processInstance = res.processInstanceId;
        this.formFields.forEach( (field) =>{
          if( field.type.name=='enum'){
            this.enumValues = Object.keys(field.type.values);
          }
        });

        this.fieldService.fetchFields().subscribe(data => {
          let obj = new Object();
          obj['businessKey'] = false;
          obj['id'] = 'scientificFields';
          obj['label'] = 'Scientific fields';
          obj['type'] = new Object();
          obj['type']['name'] = 'enum';
          obj['typeName'] = 'enum';
          obj['type']['values'] = {};
          obj['value'] = new Object();
          obj['value']['value'] = null;

          data.forEach(d => {
            this.enumValues.push(d.code);
            let option = new Object();
            obj['type']['values'][d.code] = d.name;
          })
          this.formFields.push(obj);
        })
      },

      err => {
        alert('Error!')
      }
    );
  }

  onSubmit(value, form){

    let o = new Array();
    for (var property in value) {
      if (Array.isArray(value[property])) {
        value[property] = value[property].toString();
      }
      if (property == 'scientificFields' && value[property] == '') {
        alert('You need to select a scientific field.');
        return;
      }
      o.push({fieldId : property, fieldValue : value[property]});
    }

    let x = this.magazineService.createMagazine(o, this.formFieldsDto.taskId, JSON.parse(localStorage.user).username);

    x.subscribe(
      res => {
        this.repositoryService.completeTask(this.formFieldsDto.taskId);
        alert("New magazine created. Please check your tasks and set editors and reviewers for this magazine.");
        this.router.navigateByUrl('/panel');
      },
      err => {
        alert("Error occured, check data again!");
      }
    );
  }

}
