import { Component, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { User } from '../../models/User';
import { LoginService } from './login.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{
  users;
  email: string;
  password: string;
  userdata: string;
  test;

  constructor(private service: LoginService,private router : Router) { }
  Login(token) {
    let email;
    let password;
    password = this.password;
    email = this.email;
    token=this.service.Login(btoa(email+':'+password));
  };
  
  
  /*Login(token) {
    let email;
    let password;
    password = this.password;
    email = this.email;
    token=this.service.Login(btoa(email+':'+password));  
  }*/

}
