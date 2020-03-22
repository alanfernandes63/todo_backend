package com.alanfernandes.todos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
public class TodoController {
	
	@Autowired
	private TodoService todoService;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GetMapping(value = "todos")
	public ResponseEntity<List<Todo>> listTodo(){
		return new ResponseEntity(todoService.findAll(), HttpStatus.OK);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@DeleteMapping(value = "todos")
	public ResponseEntity deleteTodo(@RequestParam long id) {
		try {
			return new ResponseEntity(todoService.delete(id), HttpStatus.OK);
		} catch (TodoNotFoundExceptio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MessageResponse message = new MessageResponse();
		message.setInfo("Tarefa não encontrada");
		return new ResponseEntity(message, HttpStatus.NOT_FOUND);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PutMapping(value = "todos")
	public ResponseEntity doneTodo(@RequestParam long id,
	@RequestParam boolean done) {
		return new ResponseEntity(todoService.doneTodo(id, done), HttpStatus.OK);
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
