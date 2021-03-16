import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
@Injectable()
export class TaskService {
    constructor(private http: HttpClient){}

    fetchTask(): Observable<Object> {
        return this.http.get('http://localhost:5000/api/tasks');
}   }