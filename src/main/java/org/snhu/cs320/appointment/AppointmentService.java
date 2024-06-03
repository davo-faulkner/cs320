package org.snhu.cs320.appointment;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AppointmentService {
	
	static Map<String, Appointment> APPOINTMENT_DATABASE = 
			new ConcurrentHashMap<String, Appointment>();
	
	private AppointmentService() {}
	
	public static boolean add(Appointment appointment) {
		if (APPOINTMENT_DATABASE.containsKey(appointment.getId())) return false;
		APPOINTMENT_DATABASE.putIfAbsent(appointment.getId(), appointment);
		return true;
	}
	
	public static boolean delete(String id) {
		return APPOINTMENT_DATABASE.remove(id) != null;
	}

}
