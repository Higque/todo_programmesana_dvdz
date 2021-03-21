import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from 'rxjs/operators';
import { TestBed } from '@angular/core/testing';
import { LoginComponent } from 'src/app/component/login/login.component';
import { Observable } from 'rxjs-compat/Observable';
import {LoginService} from '../../component/login/login.service'

@Injectable({
  providedIn: 'root',
})
export class TodoService {
  url: string = 'http://localhost:5000/api/tasks';
  url2: string = 'http://localhost:5000/api/users';

  constructor(private readonly myExampleService: LoginService, private http: HttpClient) {}
  GetTodos(){
    return this.http.get(this.url2 +'/'+'380225e7-63e9-4485-8a48-4ea3025dbe9e'+'/tasks',{
      observe: 'response',
    }).pipe(map((response) => {
      return response.body;
    }));
  }
  PostTodo(todo){
    this.http.post(this.url,todo,{
      responseType: 'text'
    }).subscribe(response =>{
      console.log(response);
    });
  }
  DeleteTodo(id){
    this.http.delete(this.url +'/'+id)
    .subscribe(response =>{
      console.log(response);
    });
  }
}