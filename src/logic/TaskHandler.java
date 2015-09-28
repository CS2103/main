package logic;

import java.util.ArrayList;
import java.util.List;

public class TaskHandler {

	static List<Task> taskDatabase = new ArrayList<Task>();

	public TaskHandler() {
		// TODO Auto-generated constructor stub
	}

	public static boolean addTask(Task newTask) {
		taskDatabase.add(newTask);
		System.out.println("Task added!");
		//System.out.println(newTask.taskDetails());
		return true;
	}

}
