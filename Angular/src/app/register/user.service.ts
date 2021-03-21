import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  url: string = 'http://localhost:5000/api/users';

  constructor(private http: HttpClient) {}
  
  PostUsers(users){
    this.http.post(this.url,users,{
      responseType: 'text'
    }).subscribe(response =>{
      console.log(response);
    });
  }
}