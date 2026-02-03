package com.manuelruizg.demo;

public class PersonaNotFoundException extends RuntimeException{
	public PersonaNotFoundException(long id) {
		super("Could not find persona "+id);
	}
}
