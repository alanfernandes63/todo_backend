package com.alanfernandes.todos;

import static org.junit.jupiter.api.Assertions.assertThrows;
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
	
	//case success save todo
	@Test
	public void saveTodo() {
		
		Todo todo = new Todo("to study", false);
		
		Todo newTodo;
		try {
			newTodo = todoService.save(todo);
			Assertions.assertThat(todo.getName()).isEqualTo("to study");
			Assertions.assertThat(todo.isDone()).isEqualTo(false);
		} catch (ValidateTodoException e) {
		//	e.printStackTrace();
		}
	}
	
	@Test
	public void saveTodoNull() {
		Todo todo = null;
		Exception exception = assertThrows(ValidateTodoException.class, () ->{
			todoService.save(todo);
		});
	}
	
	@Test
	public void saveTodoNotName() {
		Todo todo = new Todo();
		todo.setDone(true);
		@SuppressWarnings("unused")
		Exception exception = assertThrows(ValidateTodoException.class, () -> {
			this.todoService.save(todo);
		});
	}
	
	// test case succsses delete todo
	@Test
	public void deleteTodo() {
		
		Todo todo = new Todo("to study", false);
		Todo newTodo;
		
		try {
			//create todo in database
			newTodo = todoService.save(todo);
			
			//delete todo in database
			todoService.delete(newTodo.getId());
			
			//search todo in database
			Todo oldTodo = todoService.findById(newTodo.getId());
			
			//expected the search to return null
			Assertions.assertThat(oldTodo).isEqualTo(null);
			
		} catch (ValidateTodoException e) {
			e.printStackTrace();
		}catch (TodoNotFoundExceptio e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteTodoNotFound() {
		Exception exception = assertThrows(TodoNotFoundExceptio.class, () ->{
			this.todoService.delete(0);
		});
	}
	
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
	
	@Test
	public void updateDoneTodoTrue() {
		Todo todo = new Todo("to study", true);
		try {
			Todo newTodo = todoService.save(todo);
			Todo todoUpdated = todoService.updateDone(newTodo.getId(), true);
			
			Assertions.assertThat(todoUpdated.isDone()).isEqualTo(true);
		} catch (ValidateTodoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TodoNotFoundExceptio e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
