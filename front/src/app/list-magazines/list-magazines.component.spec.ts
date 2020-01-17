import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ListMagazinesComponent } from './list-magazines.component';

describe('ListMagazinesComponent', () => {
  let component: ListMagazinesComponent;
  let fixture: ComponentFixture<ListMagazinesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ListMagazinesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ListMagazinesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
