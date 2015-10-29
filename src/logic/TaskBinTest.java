package logic;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TaskBinTest {
	
	private static TaskBin testBin = new TaskBin();
	
	@Before
	public void setUp() throws Exception {
		DateTime now = new DateTime();
		Task[] t = new Task[6];
		DateTime d0 = new DateTime(0,1,1,0,0);
		DateTime d1 = new DateTime(2015,10,21,0,0);
		DateTime d2 = new DateTime(2015,11,25,0,0);
		DateTime d3 = new DateTime(2015,2,22,0,0);
		DateTime d4 = new DateTime(2016,1,21,0,0);
		DateTime d5 = new DateTime(2014,3,23,0,0);
		t[0] = new Task("Apple produced Iphone", d0, d0);
        t[1] = new Task("Google produced Google Glass", d0,d1);
        t[2] = new Task("Vivado produced new Boards", d3, d1);
        t[3] = new Task("Go fishing" ,d5, d2);
        t[4] = new Task("Do homework", d3, d4);
        t[5] = new Task ("Love no War", d5, d3);
        for(int i = 0; i< 6; i++){
        	testBin.add(t[i]);
        }
    }
	
	@After
	public void reset() throws Exception {
		testBin = new TaskBin();
	}



	@Test
	public void testReturnTaskByType() throws InvalidTimeException {
		boolean pass = true;
		DateTime d1 = new DateTime(2015,10,21,0,0);
		DateTime d2 = new DateTime(2015,11,25,0,0);
		DateTime d3 = new DateTime(2015,2,22,0,0);
		DateTime d4 = new DateTime(2016,1,21,0,0);
		DateTime d5 = new DateTime(2014,3,23,0,0);
		ArrayList<Task> eventsList = testBin.returnTaskByType("event");
		ArrayList<Task> expectResult = new ArrayList<Task>();
		expectResult.add(new Task ("Love no War", d5, d3));
		expectResult.add(new Task("Vivado produced new Boards", d3, d1));
		expectResult.add(new Task("Go fishing" ,d5, d2));
		expectResult.add(new Task("Do homework", d3, d4));
		for(int i = 0; i< expectResult.size(); i++){
			//System.out.println(eventsList.get(i).getTitle());
			//System.out.println(expectResult.get(i).getTitle());
			if(!eventsList.get(i).equals(expectResult.get(i))){
				pass = false;
			}
		}
		assertTrue(pass);

		
	}
	//Includes all three partitions of task types: floating task t0, deadline task t1, and events 

	@Test
	public void testReturnOverdue() throws InvalidTimeException {
		boolean pass = true;
		DateTime d0 = new DateTime(0,1,1,0,0);
		DateTime d1 = new DateTime(2015,10,21,0,0);
		DateTime d2 = new DateTime(2015,11,25,0,0);
		DateTime d3 = new DateTime(2015,2,22,0,0);
		DateTime d4 = new DateTime(2016,1,21,0,0);
		DateTime d5 = new DateTime(2014,3,23,0,0);
		ArrayList<Task> eventsList = testBin.returnOverdue();
		ArrayList<Task> expectResult = new ArrayList<Task>();
		expectResult.add(new Task("Go fishing" ,d5, d2));
		expectResult.add(new Task("Do homework", d3, d4));
		System.out.println(eventsList.size());
		for(int i = 0; i< expectResult.size(); i++){
			System.out.println(eventsList.get(i).getTitle());
			System.out.println(expectResult.get(i).getTitle());
			if(!eventsList.get(i).equals(expectResult.get(i))){
				pass = false;
			}
		}
		assertTrue(pass);
		
	}
	//The test consists of cases include different dates

	
	@Test
	public void testIncludeAllWords() {
		String[] l1 = {"Hello", "World"};
		String[] l2 = {"Hello"};
		String[] l3 = {"World He say", "world ", "Helloworld"}; 
		String[] l4 = {"world", "hello", "He", "is"};
		String[] l5 = {"Hello world", "world"};
		String[][] listCases = {l2, l3, l4, l5};
		boolean[] results = new boolean[4];
		for(int i =0; i < 4; i++){
			results[i] = testBin.includeAllWords(l1, listCases[i]);
		}
		boolean[] expected = {false, false, true, false};
		assertArrayEquals(results, expected);
	}
	//Include different situations: with different capital and with or without space	


}
