package org.snhu.cs320.task;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.snhu.cs320.exceptions.ValidationException;

public class TaskTest {
	
	@Test
	void testSuccessPath() throws ValidationException {
		Task task = new Task("1", "Name", "Description");
		assertThat(task)
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
		"12345,NameNameName,Description", // Too Long Name
		"12345,Name,''", // Blank Description
		"12345,Name,", // Null Description
		"12345,Name,DescriptionDescriptionDescriptionDescriptionDescription",
			// Too Long Description
	})
	void invalidIdThrowsException() {
		assertThatThrownBy(() -> new Task("", "Name", "Description"))
			.isInstanceOf(ValidationException.class);
	}

}
