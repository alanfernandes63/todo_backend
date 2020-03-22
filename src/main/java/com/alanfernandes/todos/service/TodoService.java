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
	
	public Todo findById(long id) {
		return todoRepository.findById(id);
	}
	
	public List<Todo> findAll(){
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
	
	public Todo doneTodo(long id, boolean done) {
		Todo todo = todoRepository.findById(id);
		todo.setDone(done);
		return todoRepository.save(todo);
	}
	
	public List<Todo> listActiveTodos(){
		return todoRepository.listActiveTodos();
	}
	
	public List<Todo> listDoneTodos(){
		return todoRepository.listDoneTodos();
	}
	
	private Todo getTodo(long id) throws TodoNotFoundExceptio {
		
		Todo todo = todoRepository.findById(id);
		
		if(todo == null) {
			throw new TodoNotFoundExceptio("todo not found");
		}
		
		return todo;
	}
	
	@SuppressWarnings("unused")
	private void validateTodo(Todo todo) throws ValidateTodoException {
		if(todo.getName() == null || todo.getName().trim() == "") {
			throw new ValidateTodoException("todo name is empty");
		}
	}

}
