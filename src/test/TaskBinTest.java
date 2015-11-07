package test;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import application.Constants;
import logic.Task;
import logic.TaskBin;

public class TaskBinTest {


	@Before
	public void setUp() throws Exception {
		
		Task task1 = new Task();
		Task task2 = new Task("The title of the task is super super super super super super super super super super super super super super super super long");
		Task task3 = new Task("∂‡”Ô—‘÷ß≥÷");
		Task task4 = new Task("Task starts and end at different year", new DateTime(2015,11,12,0,0), new DateTime(2016,1,15,0,1));
		Task task5 = new Task("Task ends before it starts", new DateTime(2015,12,11,0,0), new DateTime(2015,11,15,0,0));
		TaskBin testBin = new TaskBin();
		testBin.add(task1);
		testBin.add(task2);
		testBin.add(task3);
		
	}

	@Test
	public void testAdd() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testDeleteTask() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testMarkTaskInstance() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testUnmarkTaskInstance() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testEditTitle() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testEditStartingDate() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testIsClashedDateTimeArray() {
		fail("Not yet implemented"); // TODO
	}

}
