package storage;

import static org.junit.Assert.*;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import logic.Task;
import logic.TaskBin;

public class StorageTest {

	private static ArrayList<Task> taskList = new ArrayList<Task>();

	@Before
	public void setUp() throws Exception {
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
		for(int i = 0; i< t.length; i++){
			taskList.add(t[i]);
		}
	}

	@After
	public void reset() throws Exception {
		taskList.clear();
	}

	@Test
	public void testSetPath1() {
		boolean pass = true;
		Storage.setPath("/Users/hungngth/Downloads");
		File f = new File("/Users/hungngth/Downloads/TBAsave.txt");
		if (!f.exists()) {
			pass = false;
		}
		assertTrue("test whether savefile is created (without inputting filename)", pass);
	}

	@Test
	public void testsetPath2() {
		boolean pass = true;
		Storage.setPath("/Users/hungngth/Downloads/mysave.txt");
		File f = new File("/Users/hungngth/Downloads/mysave.txt");
		if (!f.exists()) {
			pass = false;
		}
		assertTrue("test whether savefile is created (with given filename)", pass);
	}

	@Test
	public void testsetPath3() {
		Storage.setPath("/Users/hungngth/Downloads");
		assertEquals("test correct assignment of path to Storage.path", Storage.path, "/Users/hungngth/Downloads/TBAsave.txt");
	}

	@Test
	public void testsetPath4() {
		Storage.setPath("/Users/hungngth/Downloads/mysave.txt");
		try {
			FileReader fr = new FileReader("/Users/hungngth/Documents/workspace/TBA/main");
			BufferedReader br = new BufferedReader(fr);
			assertEquals("test correct path writing to savedPath.txt", "/Users/hungngth/Documents/workspace/TBA/main", br.readLine());
			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWrite1() {
		Storage.write(taskList);
	}

	@Test
	public void testRead() {
		fail("Not yet implemented");
	}


}
