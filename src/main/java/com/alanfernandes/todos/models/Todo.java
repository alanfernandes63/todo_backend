package com.alanfernandes.todos.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Todo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String name;
	private boolean done;
	
	public Todo() {
		super();
	}
	
	public Todo(long id, String name, boolean done) {
		super();
		this.id = id;
		this.name = name;
		this.done = done;
	}
	
	public Todo(String name, boolean done) {
		super();
		this.name = name;
		this.done = done;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
