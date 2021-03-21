import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import { map } from 'rxjs/operators';
import { Observable, of} from 'rxjs';
import { tap} from 'rxjs/operators';
import { IUSer } from '../IUser';


@Injectable({
  providedIn: 'root',
})
export class LoginService {
  url: string = 'http://localhost:5000/api/users';
  private myData: string[]=[];

  constructor(private http: HttpClient) {}

  Login(token) {
    this.http.post(this.url+'/login?loginToken='+token,{
      responseType: 'text'
    }).subscribe(response =>{
      localStorage.setItem('currentUser',response.toString());
     console.log(response);
    });
  }
  logout() {
    localStorage.removeItem('currentUser');
  }
}