package com.alanfernandes.todos;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alanfernandes.todos.exceptions.TodoNotFoundExceptio;
import com.alanfernandes.todos.exceptions.ValidateTodoException;
import com.alanfernandes.todos.models.Todo;
import com.alanfernandes.todos.service.TodoService;

@SpringBootTest
public class TodoServiceTests {

	@Autowired
	private TodoService todoService;
	
	//test success
	@Test
	public void saveTodo() {
		
		Todo todo = new Todo("to study", false);
		
		
		try {
			Todo newTodo = todoService.save(todo);
			Assertions.assertThat(newTodo.getName()).isEqualTo("to study");
			Assertions.assertThat(newTodo.isDone()).isEqualTo(false);
		} catch (ValidateTodoException e) {
		//	e.printStackTrace();
		}
	}
	
	//test error
	@Test
	public void saveTodoNull() {
		Todo todo = null;
		assertThrows(ValidateTodoException.class, () ->{
			todoService.save(todo);
		});
	}
	
	//test error
	@Test
	public void saveTodoUnName() {
		Todo todo = new Todo();
		todo.setDone(true);
		assertThrows(ValidateTodoException.class, () -> {
			this.todoService.save(todo);
		});
	}
	
	// test succsses
	@Test
	public void deleteTodo() {
		
		Todo todo = new Todo("to study", false);
		Todo newTodo;
		
		try {
			//create todo in database
			newTodo = todoService.save(todo);
			
			//delete todo in database
			todoService.delete(newTodo.getId());
			
			assertThrows(TodoNotFoundExceptio.class,() ->{
				//search todo in database
				this.todoService.findById(newTodo.getId());
			});
			
		} catch (ValidateTodoException e) {
			//e.printStackTrace();
		}catch (TodoNotFoundExceptio e) {
			//e.printStackTrace();
		}
	}
	
	//test error
	@Test
	public void deleteTodoNotFound() {
		assertThrows(TodoNotFoundExceptio.class, () ->{
			this.todoService.delete(0);
		});
	}
	
	//test success
	@Test
	public void updateDoneFalse() {
		Todo todo = new Todo("to study", false);
		try {
			Todo newTodo = todoService.save(todo);
			Todo todoUpdated = todoService.updateDone(newTodo.getId(), true);
			
			Assertions.assertThat(todoUpdated.isDone()).isEqualTo(true);
		} catch (ValidateTodoException e) {
			e.printStackTrace();
		} catch (TodoNotFoundExceptio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//test success
	@Test
	public void updateDoneTodoTrue() {
		Todo todo = new Todo("to study", true);
		try {
			Todo newTodo = todoService.save(todo);
			Todo todoUpdated = todoService.updateDone(newTodo.getId(), true);
			
			Assertions.assertThat(todoUpdated.isDone()).isEqualTo(true);
		} catch (ValidateTodoException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		} catch (TodoNotFoundExceptio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//test success
	@Test
	public void listTodosActives() {
		List<Todo> todosActives = todoService.findAll("active");
		for(Todo todo: todosActives) {
			Assertions.assertThat(todo.isDone()).isEqualTo(false);
		}
	}
	
	//test success
	@Test
	public void listTodosDone() {
		List<Todo> todosDone = todoService.findAll("done");
		for(Todo todo:todosDone) {
			Assertions.assertThat(todo.isDone()).isEqualTo(true);
		}
	}
}
