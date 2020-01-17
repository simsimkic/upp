import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private httpClient: HttpClient) { }

  registerUser(user, taskId) {
    return this.httpClient.post("http://localhost:8081/registration/".concat(taskId), user) as Observable<any>;
  }

  verifyAccount(token, taskId) {
    return this.httpClient.post("http://localhost:8081/registration/verification/".concat(taskId), token) as Observable<any>;
  }

  login(credentials) {
    return this.httpClient.post("http://localhost:8081/login", credentials) as Observable<any>;
  }

  approveReviewer(taskId) {
    return this.httpClient.get("http://localhost:8081/reviewer/approve/".concat(taskId)) as Observable<any>;
  }

  denyReviewer(taskId) {
    return this.httpClient.get("http://localhost:8081/reviewer/deny/".concat(taskId)) as Observable<any>;
  }

  getEditors(taskId) {
    return this.httpClient.get('http://localhost:8081/editors/'.concat(taskId)) as Observable<any>
  }

  getReviewers(taskId) {
    return this.httpClient.get('http://localhost:8081/reviewers/'.concat(taskId)) as Observable<any>
  }

  getAllUsers() {
    return this.httpClient.get('http://localhost:8081/users') as Observable<any>
  }
}
