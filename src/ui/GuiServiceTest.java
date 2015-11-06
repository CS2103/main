package ui;

import java.util.ArrayList;
import java.util.Collections;

import logic.Task;

public class GuiServiceTest {

	private ArrayList<Task> taskArray;

	public GuiServiceTest() {
		taskArray = new ArrayList<Task>();

		logic.Task task1 = new Task("Meet Xiaoming for dinner");
		Task task2 = new Task("Meet Hung for dinner");
		Task task3 = new Task("Meet Andy for dinner");
		Task task4 = new Task("Meet JR for dinner");

		Collections.addAll(taskArray, task1, task2, task3, task4);

	}

	public ArrayList<Task> getStartupDisplay() {
		return taskArray;
	}

}
