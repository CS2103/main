package test;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import application.Constants;
import logic.InvalidTimeException;
import logic.Task;
import logic.TaskBin;

public class TaskBinTest {


	@Before
	public void setUp() throws Exception {


	}

	@Test
	public void testAdd() throws InvalidTimeException {
		boolean success = true;
		Task task1 = new Task(new DateTime(0,1,1,0,0),new DateTime(0,1,1,0,0));
		Task task2 = new Task("The title of the task is super super super super super super super super super super super super super super super super long", new DateTime(0,1,1,0,0), new DateTime(0,1,1,0,0));
		Task task3 = new Task("ï¿½ï¿½ï¿½ï¿½ï¿½ï¿½Ö§ï¿½ï¿½", new DateTime(0,1,1,0,0),new DateTime(0,1,1,0,0) );
		Task task4 = new Task("Task starts and end at different year", new DateTime(2015,11,12,0,0), new DateTime(2016,1,15,0,1));
		Task task5 = new Task("Task ends before it starts", new DateTime(2015,12,11,0,0), new DateTime(2015,11,15,0,0));
		TaskBin testBin = new TaskBin();
		testBin.add(task1);
		testBin.add(task2);
		testBin.add(task3);
		testBin.add(task4);
		testBin.add(task5);
		ArrayList<Task> output1 = new ArrayList<Task>();
		output1.add(task3);
		output1.add(task2);
		output1.add(task1);
		output1.add(task5);
		output1.add(task4);
	
		for(int i = 0; i < 5; i++){
			System.out.println(testBin.returnAllInbox().get(i).getTitle());
			for(int j = 0; j < 5; j++){
				if(output1.get(i).equals(testBin.returnAllInbox().get(j))){
					success = true;
					break;
				}else{
					success = false;
				}
			}
		}
		
	}

	@Test
	public void testDeleteTask() throws InvalidTimeException {
		boolean success = true;
		Task task1 = new Task(new DateTime(0,1,1,0,0),new DateTime(0,1,1,0,0));
		Task task2 = new Task("The title of the task is super super super super super super super super super super super super super super super super long", new DateTime(0,1,1,0,0), new DateTime(0,1,1,0,0));
		Task task3 = new Task("¶àÓïÑÔÖ§³Ö", new DateTime(0,1,1,0,0),new DateTime(0,1,1,0,0) );
		Task task4 = new Task("Task starts and end at different year", new DateTime(2015,11,12,0,0), new DateTime(2016,1,15,0,1));
		Task task5 = new Task("Task ends before it starts", new DateTime(2015,12,11,0,0), new DateTime(2015,11,15,0,0));
		TaskBin testBin = new TaskBin();
		testBin.add(task1);
		testBin.add(task2);
		testBin.add(task3);
		testBin.add(task4);
		testBin.add(task5);
		testBin.delete(task1);
		testBin.delete(task3);
		ArrayList<Task> output1 = new ArrayList<Task>();
		output1.add(task4);
		output1.add(task2);
		output1.add(task5);
	
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 5; j++){
				if(output1.get(i).equals(testBin.returnAllInbox().get(j))){
					success = true;
					break;
				}else{
					success = false;
				}
			}
		}
		assertTrue(success);
	}

	@Test
	public void testMarkTaskInstance() throws InvalidTimeException {
		Task task1 = new Task(new DateTime(0,1,1,0,0),new DateTime(0,1,1,0,0));
		Task task2 = new Task("The title of the task is super super super super super super super super super super super super super super super super long", new DateTime(0,1,1,0,0), new DateTime(0,1,1,0,0));
		Task task3 = new Task("¶àÓïÑÔÖ§³Ö", new DateTime(0,1,1,0,0),new DateTime(0,1,1,0,0) );
		Task task4 = new Task("Task starts and end at different year", new DateTime(2015,11,12,0,0), new DateTime(2016,1,15,0,1));
		Task task5 = new Task("Task ends before it starts", new DateTime(2015,12,11,0,0), new DateTime(2015,11,15,0,0));
		TaskBin testBin = new TaskBin();
		testBin.add(task1);
		testBin.add(task2);
		testBin.add(task3);
		testBin.add(task4);
		testBin.add(task5);
		testBin.markTaskInstance(task2);
		testBin.markTaskInstance(task5);
		testBin.markTaskInstance(task3);
		testBin.markTaskInstance(task1);
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task t:testBin.returnAllInbox()){
			if(t.getStatus()){
				result.add(t);
			}
		}
		//System.out.println("UNIT TEST INFO: " + result.size() + " " + result.get(0).getTitle());
		assertTrue((result.size() == 4));
	}

	@Test
	public void testUnmarkTaskInstance() throws InvalidTimeException {
		Task task1 = new Task(new DateTime(0,1,1,0,0),new DateTime(0,1,1,0,0));
		Task task2 = new Task("The title of the task is super super super super super super super super super super super super super super super super long", new DateTime(0,1,1,0,0), new DateTime(0,1,1,0,0));
		Task task3 = new Task("¶àÓïÑÔÖ§³Ö", new DateTime(0,1,1,0,0),new DateTime(0,1,1,0,0) );
		Task task4 = new Task("Task starts and end at different year", new DateTime(2015,11,12,0,0), new DateTime(2016,1,15,0,1));
		Task task5 = new Task("Task ends before it starts", new DateTime(2015,12,11,0,0), new DateTime(2015,11,15,0,0));
		TaskBin testBin = new TaskBin();
		testBin.add(task1);
		testBin.add(task2);
		testBin.add(task3);
		testBin.add(task4);
		testBin.add(task5);
		testBin.markTaskInstance(task2);
		testBin.markTaskInstance(task5);
		testBin.markTaskInstance(task3);
		testBin.markTaskInstance(task1);
		testBin.unmarkTaskInstance(task5);
		testBin.unmarkTaskInstance(task4);
		testBin.unmarkTaskInstance(task1);
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task t:testBin.returnAllInbox()){
			if(t.getStatus()){
				result.add(t);
			}
		}
		System.out.println(result.size());
		assertTrue(result.size() == 2);
	}

	@Test
	public void testEditTitle() throws InvalidTimeException {
		Task[] task = new Task[5];
		task[1] = new Task(new DateTime(0,1,1,0,0),new DateTime(0,1,1,0,0));
		task[2] = new Task("The title of the task is super super super super super super super super super super super super super super super super long", new DateTime(0,1,1,0,0), new DateTime(0,1,1,0,0));
		task[3] = new Task("¶àÓïÑÔÖ§³Ö", new DateTime(0,1,1,0,0),new DateTime(0,1,1,0,0) );
		task[4] = new Task("Task starts and end at different year", new DateTime(2015,11,12,0,0), new DateTime(2016,1,15,0,1));
		task[5] = new Task("Task ends before it starts", new DateTime(2015,12,11,0,0), new DateTime(2015,11,15,0,0));
		TaskBin testBin = new TaskBin();
		testBin.add(task[1]);
		testBin.add(task[2]);
		testBin.add(task[3]);
		testBin.add(task[4]);
		testBin.add(task[5]);
		for(int i = 0; i < 5; i++){
			String newTil = new Integer(i).toString();
			testBin.editTitle(task[i],newTil );
		}
		String output = new String();
		for(int i = 0; i < 5; i++){
			output = output + testBin.returnAllInbox().get(i).getTitle();
		}
		System.out.println("WARNING!!!!!!!!!!!!!" + output);
		assertEquals(output, "12345");
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
