//@@author A0129708X
package test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import application.Constants;
import logic.BinSorter;
import logic.InvalidTimeException;
import logic.Task;
import logic.TaskBin;

public class SorterTest {
    static DateTime invalid = new DateTime(0, 1, 1, 0, 0);
    static DateTime date1 = new DateTime(2015, 11, 12, 0, 0);
    static DateTime date1_2 = new DateTime(2015, 11, 12, 22, 21);
    static DateTime date2 = new DateTime(2016, 1, 15, 0, 1);
    static DateTime date3 = new DateTime(2015, 12, 11, 0, 0);
    static DateTime date4 = new DateTime(2015, 11, 15, 0, 0);
    static DateTime date5 = new DateTime(2016, 10, 20, 0, 0);
    static BinSorter sorter = new BinSorter();

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testSortArrayByTime() throws InvalidTimeException {
	boolean equals = true;
	Task[] taskArr = new Task[8];
	taskArr[0] = new Task("Floating task 1", new DateTime(0, 1, 1, 0, 0), new DateTime(0, 1, 1, 0, 0));// U,0
	taskArr[1] = new Task("task that recurs", date1, date1_2, date5, Constants.tag_weekly);
	// R
	taskArr[2] = new Task("A task for the past", new DateTime(1994, 1, 1, 0, 0), new DateTime(2001, 1, 1, 0, 0));
	// P

	taskArr[3] = new Task("多语言支持", new DateTime(0, 1, 1, 0, 0), new DateTime(0, 1, 1, 0, 0));
	// 0
	taskArr[4] = new Task("zstarts and end at different year", new DateTime(2015, 11, 12, 0, 0),
		new DateTime(2016, 1, 15, 0, 1));
	// 16
	taskArr[5] = new Task("Task ends before it starts", new DateTime(2015, 12, 11, 0, 0),
		new DateTime(2015, 11, 15, 0, 0));
	// 15
	taskArr[6] = new Task("Zoo", invalid, invalid);
	taskArr[7] = new Task("task for future", new DateTime(2222, 12, 11, 12, 0), new DateTime(3222, 12, 11, 12, 1));
	// taskArr[8] = new
	TaskBin testBin = new TaskBin();
	testBin.add(taskArr[0]);
	testBin.add(taskArr[1]);
	testBin.add(taskArr[2]);
	testBin.add(taskArr[3]);
	testBin.add(taskArr[4]);
	testBin.add(taskArr[5]);
	testBin.add(taskArr[6]);
	testBin.add(taskArr[7]);

	ArrayList<Task> expected = new ArrayList<Task>();
	expected.add(taskArr[2]);
	expected.add(taskArr[5]);
	expected.add(taskArr[4]);
	expected.add(taskArr[7]);
	expected.add(taskArr[1]);
	expected.add(taskArr[0]);
	expected.add(taskArr[6]);
	expected.add(taskArr[3]);

	ArrayList<Task> output = sorter.sortArrayByTime(testBin.returnAllInbox());
	for (int i = 0; i < 6; i++) {
	    if (!output.get(i).equals(expected.get(i))) {
		equals = false;
	    }
	}
	assertTrue(equals);

    }

    @Test
    public void testSortArrayByAlpha() throws InvalidTimeException {
	Task task1 = new Task(new DateTime(0, 1, 1, 0, 0), new DateTime(0, 1, 1, 0, 0));
	Task task2 = new Task(
		"The title of the task is super super super super super super super super super super super super super super super super long",
		new DateTime(0, 1, 1, 0, 0), new DateTime(0, 1, 1, 0, 0));
	Task task3 = new Task("多语言支持", new DateTime(0, 1, 1, 0, 0), new DateTime(0, 1, 1, 0, 0));
	Task task4 = new Task("123LetsGO", new DateTime(2015, 11, 12, 0, 0), new DateTime(2016, 1, 15, 0, 1));
	Task task5 = new Task("#$%%^&*(", new DateTime(2015, 12, 11, 0, 0), new DateTime(2015, 11, 15, 0, 0));
	ArrayList<Task> output = new ArrayList<Task>();
	ArrayList<Task> expected = new ArrayList<Task>();
	output.add(task5);
	output.add(task4);
	output.add(task3);
	output.add(task2);
	output.add(task1);
	output = sorter.sortArrayByAlpha(output);
	expected.add(task5);
	expected.add(task4);
	expected.add(task2);
	expected.add(task1);
	expected.add(task3);
	assertTrue(expected.equals(output));

    }

}
