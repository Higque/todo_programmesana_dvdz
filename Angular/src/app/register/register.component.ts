import { Component, OnInit } from '@angular/core';
import { User } from '../models/User';
import { UserService } from './user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  users;
  email: string;
  username: string;
  password: string;
  
  constructor(private service: UserService) { }

  PostUsers() {
    let users = new User();
    users.userName = this.username;
    users.email= btoa(this.email);
    users.password= btoa(this.password);
    if (users.userName && users.email && users.password) {
      this.service.PostUsers(users);
    }
  }
  
}
