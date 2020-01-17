import { Injectable } from '@angular/core';

import { Headers, RequestOptions, ResponseContentType } from '@angular/http';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Http, Response } from '@angular/http';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MagazineService {

  constructor(private httpClient: HttpClient) { }

  getAllMagazines() {
    return this.httpClient.get("http://localhost:8081/magazines") as Observable<any>;
  }

  createMagazine(magazine, taskId, username) {
    return this.httpClient.post("http://localhost:8081/magazines/" + taskId + "/" + username, magazine) as Observable<any>;
  }

  setEditorsAndReviewers(taskId, data) {
    return this.httpClient.post("http://localhost:8081/magazines/editorsAndReviewers/".concat(taskId), data) as Observable<any>;
  }

  getDataForMagazineApproval(taskId) {
    return this.httpClient.get('http://localhost:8081/magazines/data/'.concat(taskId)) as Observable<any>
  }

  approveMagazine(taskId) {
    return this.httpClient.get("http://localhost:8081/magazines/approve/".concat(taskId)) as Observable<any>;
  }

  denyMagazine(taskId) {
    return this.httpClient.get("http://localhost:8081/magazines/deny/".concat(taskId)) as Observable<any>;
  }

  getUpdateForm(taskId) {
    return this.httpClient.get('http://localhost:8081/magazines/updateForm/'.concat(taskId)) as Observable<any>
  }

  updateMagazine(magazine, issn) {
    return this.httpClient.put("http://localhost:8081/magazines/".concat(issn), magazine) as Observable<any>;
  }
}
