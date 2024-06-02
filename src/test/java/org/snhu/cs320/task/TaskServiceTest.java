package org.snhu.cs320.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snhu.cs320.exceptions.ValidationException;

public class TaskServiceTest {
	
	@BeforeEach
	void init() {
		TaskService.TASK_DATABASE.clear();
	}

	@Test
	void addSuccess() throws ValidationException {
		Task task = new Task("12345", "Name", "Description");
		TaskService.add(task);
		assertThat(TaskService.TASK_DATABASE)
			.containsEntry("12345", task);
	}
	
	@Test
	void delete() throws ValidationException {
		TaskService.add(new Task("12345", "Name", "Description"));
		TaskService.delete("12345");
		assertThat(TaskService.TASK_DATABASE)
			.doesNotContainKey("12345");
	}
	
	@Test
	void updateSuccess() throws ValidationException {
		TaskService.add(new Task("12345", "Name", "Description"));
		
		Task updated = new Task("12345", "Good Name", "Bad Description");
		assertThat(TaskService.update("12345", updated)).isTrue();
		
		assertThat(TaskService.TASK_DATABASE)
			.extracting("12345")
			.hasFieldOrPropertyWithValue("name", "Good Name");
	}
	
	@Test
	void updateFail() throws ValidationException {
		TaskService.add(new Task("12345", "Name", "Description"));
		
		Task updated = new Task("12345", "Good Name", "Description");
		updated.setDescription(null);
		
		assertThatThrownBy(() -> TaskService.update("12345", updated))
			.isInstanceOf(ValidationException.class);
	}

}
