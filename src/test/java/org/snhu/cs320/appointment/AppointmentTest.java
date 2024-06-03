package org.snhu.cs320.appointment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.snhu.cs320.exceptions.ValidationException;

public class AppointmentTest {
	
	@Test
	void testSuccessPath() throws ValidationException {
		Appointment appt = new Appointment("1", LocalDate.now(), "Description");
		assertThat(appt)
			.isNotNull()
			.hasFieldOrPropertyWithValue("id", "1")
			.hasFieldOrPropertyWithValue("date", LocalDate.now())
			.hasFieldOrPropertyWithValue("description", "Description");
	}
	
	// Note that JUnit implicitly converts CsvSource data into LocalDate.
	@ParameterizedTest
	@CsvSource({
		"'',2124-06-03,Description", // Blank ID
		",2124-06-03,Description", // Null ID
		"12345678901,2124-06-03,Description", // Too Long ID
		"12345,'',Description", // Blank Date
		"12345,,Description", // Null Date
		"12345,2024-06-01,Description", // Date In Past
		"12345,2124-06-03,''", // Blank Description
		"12345,2124-06-03,", // Null Description
		"12345,2124-06-03,DescriptionDescriptionDescriptionDescriptionDescription",
			// Too Long Description
	})
	void invalidIdThrowsException() {
		assertThatThrownBy(() -> new Appointment("", LocalDate.now(), 
				"Description")).isInstanceOf(ValidationException.class);
	}
	
}
