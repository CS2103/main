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
		DateTime d1 = new DateTime(2015,10,25,0,0);
		DateTime d2 = new DateTime(2015,11,25,0,0);
		DateTime d3 = new DateTime(2015,2,22,0,0);
		DateTime d4 = new DateTime(2016,1,21,0,0);
		DateTime d5 = new DateTime(2014,3,23,0,0);
		t[0] = new Task("Apple produced Iphone", d0, d0);
        t[1] = new Task("Google produced Google Glass", d0,now);
        t[2] = new Task("Vivado produced new Boards", now, d1);
        t[3] = new Task("Go fishing" ,now, d2);
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
	public void testReturnTaskByType() {
		boolean pass = true;
		DateTime now = new DateTime();
		DateTime d1 = new DateTime(2015,10,25,0,0);
		DateTime d2 = new DateTime(2015,11,25,0,0);
		DateTime d3 = new DateTime(2015,2,22,0,0);
		DateTime d4 = new DateTime(2016,1,21,0,0);
		DateTime d5 = new DateTime(2014,3,23,0,0);
		ArrayList<Task> eventsList = testBin.returnTaskByType("event");
		ArrayList<Task> expectResult = new ArrayList<Task>();
		expectResult.add(new Task ("Love no War", d5, d3));
		expectResult.add(new Task("Vivado produced new Boards", now, d1));
		expectResult.add(new Task("Go fishing" ,now, d2));
		
		expectResult.add(new Task("Do homework", d3, d4));
		for(int i = 0; i< expectResult.size(); i++){
			if(!eventsList.get(i).equals(expectResult.get(i))){
				pass = false;
				break;
			}
		}
		assertTrue(pass);

		
	}
/*	
	@Test
	public void testSortArrayByTime() {
		
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testReturnOverdue() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testAddWeeklyTask() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testDisplayInit() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public void testIncludeAllWords() {
		fail("Not yet implemented"); // TODO
	}*/

}
