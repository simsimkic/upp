import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';

@Injectable()
export class Admin implements CanActivate {

  constructor() {}

  canActivate() {
    return localStorage.role == 'admin';
  }
}