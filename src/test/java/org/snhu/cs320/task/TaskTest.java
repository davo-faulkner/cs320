package org.snhu.cs320.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.snhu.cs320.exceptions.ValidationException;

public class TaskTest {
	
	static Task TASK;
	
	@BeforeEach
	void init() throws ValidationException {
		TASK = new Task("1", "Name", "Description");
	}
	
	@Test
	void testConstructorSuccessPath() throws ValidationException {
		assertThat(TASK)
			.isNotNull()
			.hasFieldOrPropertyWithValue("id", "1")
			.hasFieldOrPropertyWithValue("name", "Name")
			.hasFieldOrPropertyWithValue("description", "Description");
	}
	
	@ParameterizedTest
	@CsvSource({
		"'',Name,Description", // Blank ID
		",Name,Description", // Null ID
		"12345678901,Name,Description", // Too Long ID
		"12345,'',Description", // Blank Name
		"12345,,Description", // Null Name
		"12345,NameNameNameNameNameN,Description", // Too Long Name
		"12345,Name,''", // Blank Description
		"12345,Name,", // Null Description
		"12345,Name,DescriptionDescriptionDescriptionDescriptionDescrip",
			// Too Long Description
	})
	void testInvalidConstructorDataThrowsException(String id, String name,
			String description) {
		assertThatThrownBy(() -> new Task(id, name, description))
			.isInstanceOf(ValidationException.class);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"NewName"})
	void testNameSetterSuccessPath(String newName) 
			throws ValidationException {
		TASK.setName(newName);
		assertThat(TASK.getName())
			.isNotNull()
			.isEqualTo(newName);
	}
	
	@ParameterizedTest
	@CsvSource(value = {"''", // Blank Name
		"null", // Null Name
		"NameNameNameNameNameN"}, // Too Long Name
		nullValues = {"null"})
	void testNameSetterFailPaths(String newName) 
			throws ValidationException {
		assertThatThrownBy(() -> TASK.setName(newName))
			.isInstanceOf(ValidationException.class);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"NewDescription"})
	void testDescriptionSetterSuccessPath(String newDescription) 
			throws ValidationException {
		TASK.setDescription(newDescription);
		assertThat(TASK.getDescription())
			.isNotNull()
			.isEqualTo(newDescription);
	}
	
	@ParameterizedTest
	@CsvSource(value = {"''", // Blank Description
		"null", // Null Description
		"DescriptionDescriptionDescriptionDescriptionDescrip"}, // Too Long
			// Description
		nullValues = {"null"})
	void testDescriptionSetterFailPaths(String newDescription) 
			throws ValidationException {
		assertThatThrownBy(() -> TASK.setDescription(newDescription))
			.isInstanceOf(ValidationException.class);
	}

}
