import { Component, OnInit } from '@angular/core';

@Component({ selector: 'app-root', 
templateUrl: './app.component.html', 
styleUrls: ['./app.component.css']
})

export class AppComponent {
  title= "To-Do list";
}

//export class AppComponent implements OnInit {
    //postId;

    //constructor(private http: HttpClient) { }

    /*ngOnInit() {      
      
        // Simple get request with a JSON body and response type <any>
        const headers = { 'Authorization': 'Bearer my-token', 'Access-Control-Allow-Origin': '*' , 'Content-Type': 'application/json; charset=UTF-8'}
        this.http.get<any>('http://localhost:50911/api/tasks', { headers }).subscribe(data => {
            this.postId = data.id;
        })
    }*/