import { Component, OnInit } from '@angular/core';
import { MagazineService } from '../services/magazine.service';

@Component({
  selector: 'app-list-magazines',
  templateUrl: './list-magazines.component.html',
  styleUrls: ['./list-magazines.component.css']
})
export class ListMagazinesComponent implements OnInit {

  magazines = [];

  constructor(private magazineService: MagazineService) { }

  ngOnInit() {
    this.getAllMagazines();
  }

  getAllMagazines() {
    this.magazineService.getAllMagazines().subscribe(data => {
      this.magazines = data;
    })
  }

}
