import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {

  categories = [];
  languages = [];
  books = [];

  constructor(private httpClient: HttpClient, private http : Http) {}

  startProcess(processName){
    return this.httpClient.get('http://localhost:8081/welcome/' + processName) as Observable<any>
  }

  getTasksForUser(username){
    return this.httpClient.get('http://localhost:8081/welcome/get/userTasks/'.concat(username)) as Observable<any>
  }

  claimTask(taskId){
    return this.httpClient.post('http://localhost:8081/welcome/tasks/claim/'.concat(taskId), null) as Observable<any>
  }

  completeTask(taskId){
    return this.httpClient.post('http://localhost:8081/welcome/tasks/complete/'.concat(taskId), null) as Observable<any>
  }

  getTaskId(processDefinitionKey: string) {
    return this.httpClient.get('http://localhost:8081/welcome/get/tasks/'.concat(processDefinitionKey)) as Observable<any>
  }

  getDataForReviewerApproval(taskId) {
    return this.httpClient.get('http://localhost:8081/welcome/data/'.concat(taskId)) as Observable<any>
  }

  getMagazineForStaffSetup(taskId) {
    return this.httpClient.get('http://localhost:8081/welcome/data/magazineName/'.concat(taskId)) as Observable<any>
  }

}
