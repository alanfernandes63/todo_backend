package com.alanfernandes.todos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alanfernandes.todos.exceptions.TodoNotFoundExceptio;
import com.alanfernandes.todos.exceptions.ValidateTodoException;
import com.alanfernandes.todos.models.Todo;
import com.alanfernandes.todos.repository.TodoRepository;

@Service
public class TodoService {
	@Autowired
	TodoRepository todoRepository;
	
	public Todo findById(long id) throws TodoNotFoundExceptio {
		return getTodo(id);
	}
	
	public List<Todo> findAll(String type){
		if(type.equals("active")) {
		return todoRepository.listActiveTodos();
		}else if(type.equals("done")) {
			return todoRepository.listDoneTodos();
		}
		return todoRepository.findAll();
	}
	
	public Todo delete(long id) throws TodoNotFoundExceptio {
		Todo todo = getTodo(id);
		todoRepository.delete(todo);
		return todo;
	}
	
	public Todo save(Todo todo) throws ValidateTodoException {
		validateTodo(todo);
		return todoRepository.save(todo);
	}
	
	public Todo updateDone(long id, boolean done) throws TodoNotFoundExceptio {
		Todo todo = todoRepository.findById(id);
		getTodo(id);
		todo.setDone(done);
		return todoRepository.save(todo);
	}
	
	private Todo getTodo(long id) throws TodoNotFoundExceptio {
		
		Todo todo = todoRepository.findById(id);
		
		if(todo == null) {
			throw new TodoNotFoundExceptio("todo not found");
		}
		
		return todo;
	}
	
	private void validateTodo(Todo todo) throws ValidateTodoException {
		if(todo == null) {
			throw new ValidateTodoException("todo is null");
		}else if (todo.getName() == null || todo.getName().trim() == "") {
			throw new ValidateTodoException("todo name is empty");
		}
	}
}
