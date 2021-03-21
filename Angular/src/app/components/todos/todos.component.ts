import { Component, OnInit } from '@angular/core';
import { Todo } from '../../models/Todo';
import { TodoService } from './todo.service';

@Component({
  selector: 'app-todos',
  templateUrl: './todos.component.html',
  styleUrls: ['./todos.component.css']
})
export class TodosComponent {
  todos;
  content: string;
  inputTodo:string = "";


  constructor(private service: TodoService) {}

  GetTodos() {
    this.service.GetTodos().subscribe((data) => {
      this.todos = data;
      this.todos.userId = '380225e7-63e9-4485-8a48-4ea3025dbe9e';
      console.log(this.todos);
    });
  }

  PostTodo() {
    let todo = new Todo();
    todo.content = this.content;
    todo.userId = '380225e7-63e9-4485-8a48-4ea3025dbe9e'
    if (todo.content) {
      this.service.PostTodo(todo);
    }
    setTimeout(() => {
      this.GetTodos();
    }, 1000);
  }

  DeleteTodo(taskId){
    this.service.DeleteTodo(taskId);
    setTimeout(() => {
      this.GetTodos();
    }, 1000);
  }
}






 /* ngOnInit(): void {
  this.todos = [
    {
      taskId: '8211b894-ec03-49f0-92c2-a2776b67858c',
      content: 'First todo',
      createdDate: '11.02.98',
      userId: 'a40d1e27-f1a4-4dd2-9f58-14230a941fe4'  
    },
    {
      taskId: '8211b894-ec03-49f0-92c2-a2776b67858c',
      content: 'Second todo',
      createdDate: '11.02.98',
      userId: 'a40d1e27-f1a4-4dd2-9f58-14230a941fe4'
    }
  ]
}
/*toggleDone (id:number) {
  this.todo.map((v,i)=> {
    if (i == id) v.completed = !v.completed;

    return v;
  })
}
deleteTodo(id:number) {
  this.todos = this.todos.filter((v,i)=> i !== id);
}
addTodo() {
  this.todos.push({
    content: this.inputTodo,
    taskId: '8211b894-ec03-49f0-92c2-a2776b67858c',
      createdDate: '11.02.98',
      userId: 'a40d1e27-f1a4-4dd2-9f58-14230a941fe4',
  });

  this.inputTodo = "";
}*/
