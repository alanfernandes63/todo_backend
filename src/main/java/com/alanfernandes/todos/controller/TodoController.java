package com.alanfernandes.todos.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alanfernandes.todos.exceptions.TodoNotFoundExceptio;
import com.alanfernandes.todos.exceptions.ValidateTodoException;
import com.alanfernandes.todos.message.Message;
import com.alanfernandes.todos.message.MessageResponse;
import com.alanfernandes.todos.models.Todo;
import com.alanfernandes.todos.repository.TodoRepository;
import com.alanfernandes.todos.service.TodoService;

@RestController
@RequestMapping(value = "api/v1/")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value = "todos/search")
	public ResponseEntity todoFindById(@RequestParam long id) {
		try {
			return new ResponseEntity(todoService.findById(id), HttpStatus.OK);
		} catch (TodoNotFoundExceptio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MessageResponse message = new MessageResponse();
			message.setInfo(Message.TODO_NOT_FOUND);
			return new ResponseEntity(message, HttpStatus.NOT_FOUND);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "todos")
	public ResponseEntity<List<Todo>> listTodos(){
		return new ResponseEntity(todoService.findAll(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value="todos/actives")
	public ResponseEntity<List<Todo>> activeTodos(){
		return new ResponseEntity(todoService.listActiveTodos(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@GetMapping(value="todos/done")
	public ResponseEntity<List<Todo>> doneTodos(){
		return new ResponseEntity(todoService.listDoneTodos(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping(value = "todos/{id}")
	public ResponseEntity deleteTodo(@PathVariable long id) {
		try {
			return new ResponseEntity(todoService.delete(id), HttpStatus.OK);
		} catch (TodoNotFoundExceptio e) {
			//e.printStackTrace();
			
			MessageResponse message = new MessageResponse();
			message.setInfo(Message.TODO_NOT_FOUND);
			
			return new ResponseEntity(message, HttpStatus.NOT_FOUND);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping(value = "todos/{id}")
	public ResponseEntity doneTodo(@PathVariable long id,
	@RequestParam boolean done) {
		try {
			return new ResponseEntity(todoService.updateDone(id, done), HttpStatus.OK);
		} catch (TodoNotFoundExceptio e) {
			//e.printStackTrace();
			
			MessageResponse message = new MessageResponse();
			message.setInfo(Message.TODO_NOT_FOUND);
			
			return new ResponseEntity(message, HttpStatus.NOT_FOUND);
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@PostMapping(value = "todos")
	public ResponseEntity addTodo(@RequestBody Todo todo) {
		try {
			return new ResponseEntity(todoService.save(todo), HttpStatus.OK);
		} catch (ValidateTodoException e) {
			//e.printStackTrace();
			
			MessageResponse message = new MessageResponse();
			message.setInfo(Message.NAME_IS_REQUIRED);
			
			return new ResponseEntity(message,HttpStatus.BAD_REQUEST);
		}
	}

}
