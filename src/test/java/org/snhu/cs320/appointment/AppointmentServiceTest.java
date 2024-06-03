package org.snhu.cs320.appointment;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.snhu.cs320.exceptions.ValidationException;

public class AppointmentServiceTest {
	
	@BeforeEach
	void init() {
		AppointmentService.APPOINTMENT_DATABASE.clear();
	}

	@Test
	void addSuccess() throws ValidationException {
		Appointment appointment = new Appointment("12345", LocalDate.now(), 
				"Description");
		AppointmentService.add(appointment);
		assertThat(AppointmentService.APPOINTMENT_DATABASE)
			.containsEntry("12345", appointment);
	}
	
	@Test
	void addExistingId() throws ValidationException {
		Appointment appointment = new Appointment("12345", LocalDate.now(), 
				"Description");
		AppointmentService.add(appointment);
		assertThat(AppointmentService.APPOINTMENT_DATABASE)
		.containsEntry("12345", appointment);
		assertThat(AppointmentService.add(appointment)).isFalse();
	}
	
	@Test
	void deleteSuccess() throws ValidationException {
		Appointment appointment = new Appointment("12345", LocalDate.now(), 
				"Description");
		assertThat(AppointmentService.add(appointment)).isTrue();
		assertThat(AppointmentService.delete("12345")).isTrue();
		assertThat(AppointmentService.APPOINTMENT_DATABASE)
			.doesNotContainKey("12345");
	}
	
	@Test
	void deleteNonExisting() throws ValidationException {
		assertThat(AppointmentService.delete("12345")).isFalse();
	}

}
