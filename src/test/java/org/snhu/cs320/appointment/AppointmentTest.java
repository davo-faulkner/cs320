package org.snhu.cs320.appointment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.snhu.cs320.exceptions.ValidationException;

public class AppointmentTest {
	
	static Appointment APPOINTMENT;
	
	@BeforeEach
	void init() throws ValidationException {
		APPOINTMENT = new Appointment("1", LocalDate.now(), 
				"Description");
	}
	
	@Test
	void testConstructorSuccessPath() throws ValidationException {
		assertThat(APPOINTMENT)
			.isNotNull()
			.hasFieldOrPropertyWithValue("id", "1")
			.hasFieldOrPropertyWithValue("date", LocalDate.now())
			.hasFieldOrPropertyWithValue("description", "Description");
	}
	
	@ParameterizedTest
	@CsvSource({
		"'',2124-06-03,Description", // Blank ID
		",2124-06-03,Description", // Null ID
		"12345678901,2124-06-03,Description", // Too Long ID
		"12345,,Description", // Null Date
		"12345,2024-06-01,Description", // Date In Past
		"12345,2124-06-03,''", // Blank Description
		"12345,2124-06-03,", // Null Description
		"12345,2124-06-03,DescriptionDescriptionDescriptionDescriptionDescription",
			// Too Long Description
	})
	void testInvalidConstructorDataThrowsException(String id, LocalDate date, 
			String description) {
		assertThatThrownBy(() -> new Appointment(id, date, 
				description))
			.isInstanceOf(ValidationException.class);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"2124-06-04"})
	void testDateSetterSuccessPath(LocalDate newDate) 
			throws ValidationException {
		APPOINTMENT.setDate(newDate);
		assertThat(APPOINTMENT.getDate())
			.isNotNull()
			.isEqualTo(newDate);
	}
	
	@ParameterizedTest
	@CsvSource(value = {"null", // Null Date
		"2024-06-01"}, // Date in Past
		nullValues = {"null"})
	void testDateSetterFailPaths(LocalDate newDate) 
			throws ValidationException {
		assertThatThrownBy(() -> APPOINTMENT.setDate(newDate))
			.isInstanceOf(ValidationException.class);
	}
	
	@ParameterizedTest
	@ValueSource(strings = {"NewDescription"})
	void testDescriptionSetterSuccessPath(String newDescription) 
			throws ValidationException {
		APPOINTMENT.setDescription(newDescription);
		assertThat(APPOINTMENT.getDescription())
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
		assertThatThrownBy(() -> APPOINTMENT.setDescription(newDescription))
			.isInstanceOf(ValidationException.class);
	}
	
}
