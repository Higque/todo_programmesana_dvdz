import { Component, OnInit } from '@angular/core';
import { Todo } from './../../models/Todo';
import { TaskService } from './task.service';

@Component({
  selector: 'app-todos',
  templateUrl: './todos.component.html',
  styleUrls: ['./todos.component.css']
})
export class TodosComponent implements OnInit {
  todos:Todo[];

  inputTodo:string = "";
  /*tasks$;
  constructor(private taskService: TaskService) { }
  fetchTask() {
    this.tasks$ = this.taskService.fetchTask();
  }*/

  ngOnInit(): void {
  this.todos = [
    {
      content: 'First todo',
      completed: false
    },
    {
      content: 'Second todo',
      completed: false
    }
  ]
}
toggleDone (id:number) {
  this.todos.map((v,i)=> {
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
    completed: false
  });

  this.inputTodo = "";
}
}
