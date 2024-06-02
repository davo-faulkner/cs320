package org.snhu.cs320.task;

import org.snhu.cs320.exceptions.ValidationException;
import org.snhu.cs320.validation.Validation;

public class Task {
	
	private String id;
	private String name;
	private String description;
	
	public Task(String id, String name, String description) throws 
			ValidationException {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		
		validate();
	}
	
	void validate() throws ValidationException {
		// ID
		Validation.validateNotBlank(id, "id");
		Validation.validateLength(id, "id", 1, 10);
		
		// Name
		Validation.validateNotBlank(id, "id");
		Validation.validateLength(id, "id", 1, 20);
		
		// Description
		Validation.validateNotBlank(id, "id");
		Validation.validateLength(id, "id", 1, 50);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	
}
