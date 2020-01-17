import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { RepositoryService } from '../services/repository.service';
import { FieldService } from '../services/field.service';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {

  private repeated_password = "";
  private categories = [];
  private formFieldsDto = null;
  private formFields = [];
  private choosen_category = -1;
  private processInstance = "";
  private enumValues = [];
  private tasks = [];

  constructor(private userService : UserService, private repositoryService : RepositoryService,
              private fieldService: FieldService) {
    let x = repositoryService.startProcess('registration');

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
        alert('Error!');
      }
    );
   }

  ngOnInit() {
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

    let x = this.userService.registerUser(o, this.formFieldsDto.taskId);

    x.subscribe(
      res => {
        this.repositoryService.completeTask(this.formFieldsDto.taskId);
        alert("Please check your email for verification link.");
      },
      err => {
        alert("Error occured, check data again!");
      }
    );
  }

}
