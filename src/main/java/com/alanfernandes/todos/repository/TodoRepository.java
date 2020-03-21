package com.alanfernandes.todos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.alanfernandes.todos.models.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

	public Todo findById(long id);
	
	@Query("SELECT u FROM Todo u WHERE u.done = FALSE")
	public List<Todo> listActiveTodos();
	
	@Query("SELECT u FROM Todo u WHERE u.done = TRUE")
	public List<Todo> listDoneTodos();
}
