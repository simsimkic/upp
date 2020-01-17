import { Injectable } from '@angular/core';
import { CanActivate } from '@angular/router';

@Injectable()
export class Editor implements CanActivate {

  constructor() {}

  canActivate() {
    return localStorage.role == 'editor';
  }
}