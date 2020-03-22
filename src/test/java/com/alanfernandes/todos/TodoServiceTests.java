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
	
	@Test
	public void saveTodo() {
		
		Todo todo = new Todo("to study", false);
		
		Todo newTodo;
		try {
			newTodo = todoService.save(todo);
			Assertions.assertThat(todo.getName()).isEqualTo("to study");
			Assertions.assertThat(todo.isDone()).isEqualTo(false);
		} catch (ValidateTodoException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
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
	
	@Test
	public void deleteTodo() {
		
		Todo todo = new Todo("to study", false);
		Todo newTodo;
		
		try {
			//cria o todo no banco
			newTodo = todoService.save(todo);
			
			//deleta o todo do banco
			todoService.delete(newTodo.getId());
			
			//busca o todo deletado do banco
			Todo oldTodo = todoService.findById(newTodo.getId());
			
			//espera que a busca retorne null
			Assertions.assertThat(oldTodo).isEqualTo(null);
			
		} catch (ValidateTodoException e) {
			// TODO Auto-generated catch block
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
}
