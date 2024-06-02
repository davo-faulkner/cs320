package org.snhu.cs320.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.snhu.cs320.exceptions.ValidationException;

public class TaskService {
	
	static Map<String, Task> TASK_DATABASE = new ConcurrentHashMap<String, 
			Task>();
	
	private TaskService() {}
	
	public static boolean add(Task task) {
		if (TASK_DATABASE.containsKey(task.getId())) return false;
		TASK_DATABASE.putIfAbsent(task.getId(), task);
		return true;
	}
	
	public static boolean delete(String id) {
		return TASK_DATABASE.remove(id) != null;
	}
	
	public static boolean update(String id, Task updated) 
			throws ValidationException {
		Task existing = TASK_DATABASE.get(id);
		
		if (existing == null) return false;
		
		// Validates updated Task before adding it to database.
		updated.validate();
		
		existing.setName(updated.getName());
		existing.setDescription(updated.getDescription());
		
		existing.validate();
		
		return true;
	}

}
