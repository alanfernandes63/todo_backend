package com.alanfernandes.todos.managedBeans;

import java.util.List;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.RequestAction;
import org.ocpsoft.rewrite.el.ELBeanName;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.annotation.IgnorePostback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alanfernandes.todos.exceptions.TodoNotFoundExceptio;
import com.alanfernandes.todos.exceptions.ValidateTodoException;
import com.alanfernandes.todos.message.Message;
import com.alanfernandes.todos.message.MessageBean;
import com.alanfernandes.todos.models.Todo;
import com.alanfernandes.todos.service.TodoService;

@Scope(value = "session")
@Component(value = "todoBean")
@ELBeanName(value = "todoBean")
@Join(path = "/", to = "/todos.jsf")
public class TodoBean {
	
	private List <Todo> todos;
	private Todo newTodo;
	private String todoName;
	@Autowired
	private TodoService todoService;
	
	private boolean allSelected;
	private boolean activeSelected;
	private boolean doneSelected;
	
	public TodoBean() {
	}
	
	@Deferred
	@RequestAction
	@IgnorePostback
	public void init() {
		this.todos = todoService.findAll();
		listTodos();
		
	}
	
	public void doneTodo(Todo todo) {
		todoService.doneTodo(todo.getId(), todo.isDone());
		
		if(!allSelected) {
			todos.remove(todo);
		}
	}
	
	public void addTodo() {
		Todo newTodo;
		try {
			newTodo = todoService.save(new Todo(todoName,false));
			todos.add(newTodo);
			todoName = "";
		} catch (ValidateTodoException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			new MessageBean("erro de validacao", Message.NAME_IS_REQUIRED);
		}
	}
	
	public void delete(Todo todo) {
		try {
			todoService.delete(todo.getId());
		} catch (TodoNotFoundExceptio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		todos.remove(todo);
	}
	
	public void listActiveTodos() {
		todos = todoService.listActiveTodos();
		this.allSelected = false;
		this.activeSelected = true;
		this.doneSelected = false;
	}
	
	public void listTodos() {
		todos = todoService.findAll();
		this.allSelected = true;
		this.activeSelected = false;
		this.doneSelected = false;
	}
	
	public void listDoneTodos() {
		todos = todoService.listDoneTodos();
		this.allSelected = false;
		this.activeSelected = false;
		this.doneSelected = true;
	}
	
	public List<Todo> getTodos() {
		return todos;
	}
	
	public void setTodos(List<Todo> todos) {
		this.todos = todos;
	}
	
	public Todo getNewTodo() {
		return newTodo;
	}
	
	public void setNewTodo(Todo newTodo) {
		this.newTodo = newTodo;
	}

	public String getTodoName() {
		return todoName;
	}

	public void setTodoName(String todoName) {
		this.todoName = todoName;
	}

	public boolean isAllSelected() {
		return allSelected;
	}

	public void setAllSelected(boolean allSelected) {
		this.allSelected = allSelected;
	}

	public boolean isActiveSelected() {
		return activeSelected;
	}

	public void setActiveSelected(boolean activeSelected) {
		this.activeSelected = activeSelected;
	}

	public boolean isDoneSelected() {
		return doneSelected;
	}

	public void setDoneSelected(boolean doneSelected) {
		this.doneSelected = doneSelected;
	}
}
