package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// import Google Gson library
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import logic.Task;
import logic.TaskBin;
import storage.Converters;
import storage.Storage;

public class StorageTest {
	// initial methods to serialise/deserialise savedTask.json with DateTime
	// formats
	final static Gson gson = Converters.registerDateTime(new GsonBuilder().setPrettyPrinting().serializeNulls())
			.create();
	private static final int INPUTTASKS_LENGTH = 6;

	final DateTime original = new DateTime();
	final String json = gson.toJson(original);
	final DateTime reconstituted = gson.fromJson(json, DateTime.class);

	private static final ArrayList<Task> taskList = new ArrayList<Task>();
	private static ArrayList<Task> taskListForTest = new ArrayList<Task>();
	private static final File pathFile = new File("savedPath.txt");
	private static final String path = Storage.extractDirectory(pathFile.getAbsolutePath());
	private static final String newPath = path + "/TBAsave.txt";

	@Before
	public void setUp1() throws Exception {
		Task[] inputTasks = new Task[INPUTTASKS_LENGTH];
		DateTime d0 = new DateTime(0, 1, 1, 0, 0);
		DateTime d1 = new DateTime(2015, 10, 21, 0, 0);
		DateTime d2 = new DateTime(2015, 11, 25, 0, 0);
		DateTime d3 = new DateTime(2015, 2, 22, 0, 0);
		DateTime d4 = new DateTime(2016, 1, 21, 0, 0);
		DateTime d5 = new DateTime(2014, 3, 23, 0, 0);
		inputTasks[0] = new Task("Apple produced Iphone", d0, d0);
		inputTasks[1] = new Task("Google produced Google Glass", d0, d1);
		inputTasks[2] = new Task("Vivado produced new Boards", d3, d1);
		inputTasks[3] = new Task("Go fishing", d5, d2);
		inputTasks[4] = new Task("Do homework", d3, d4);
		inputTasks[5] = new Task("Love no War", d5, d3);
		for (int i = 0; i < INPUTTASKS_LENGTH; i++) {
			taskList.add(inputTasks[i]);
		}
		

	}

	/*
	@Before
	public void setUp2() throws Exception {
		Task[] inputTasks = new Task[INPUTTASKS_LENGTH];
		inputTasks[0] = new Task(new DateTime(0,1,1,0,0),new DateTime(0,1,1,0,0));
		inputTasks[1] = new Task(new DateTime(0,1,1,0,0),new DateTime(0,1,1,0,0));
		inputTasks[2] = new Task("The title of the task is super super super super super super super super super super super super super super super super long", new DateTime(0,1,1,0,0), new DateTime(0,1,1,0,0));
		inputTasks[3] = new Task("������֧��", new DateTime(0,1,1,0,0),new DateTime(0,1,1,0,0) );
		inputTasks[4] = new Task("Task starts and end at different year", new DateTime(2015,11,12,0,0), new DateTime(2016,1,15,0,1));
		inputTasks[5] = new Task("Task ends before it starts", new DateTime(2015,12,11,0,0), new DateTime(2015,11,15,0,0));
		for (int i = 0; i < INPUTTASKS_LENGTH; i++) {
			taskList.add(inputTasks[i]);
		}
	}
	 */

	@After
	public void reset() throws Exception {
		taskList.clear();
	}

	@Test
	public void testSetPath_fileCreated1() {
		boolean pass = true;
		Storage.setPath(path);
		File file = new File(newPath);
		if (!file.exists()) {
			pass = false;
		}
		assertTrue("test whether savefile is created (without inputting filename)", pass);
	}

	@Test
	public void testSetPath_fileCreated2() {
		boolean pass = true;
		Storage.setPath("/Users/hungngth/Downloads/mysave.txt");
		File file = new File("/Users/hungngth/Downloads/mysave.txt");
		if (!file.exists()) {
			pass = false;
		}
		assertTrue("test whether savefile is created (with given filename)", pass);
	}

	@Test
	public void testSetPath_invalidPath1() {
		boolean isValidPath, pass;
		isValidPath = Storage.setPath("aaa");
		if (isValidPath == false) {
			pass = true;
		} else {
			pass = false;
		}
		assertTrue("test correct processing of invalid path input", pass);
	}

	@Test
	public void testSetPath_invalidPath2() {
		boolean isValidPath, pass;
		isValidPath = Storage.setPath(" ");
		if (isValidPath == false) {
			pass = true;
		} else {
			pass = false;
		}
		assertTrue("test correct processing of invalid path input (space character)", pass);
	}

	// \ / : * ? " < > |
	@Test
	public void testSetPath_invalidPath3() {
		boolean isValidPath, pass;
		isValidPath = Storage.setPath("*");
		if (isValidPath == false) {
			pass = true;
		} else {
			pass = false;
		}
		assertTrue("test correct processing of invalid path input (null)", pass);
	}

	@Test
	public void testSetPath_invalidPathWithSlash1() {
		boolean isValidPath, pass;
		isValidPath = Storage.setPath("/Users/hungngth/Download/Document");
		if (isValidPath == false) {
			pass = true;
		} else {
			pass = false;
		}
		assertTrue("test correct processing of invalid path input with slash", pass);
	}

	@Test
	public void testSetPath_invalidPathWithSlash2() {
		boolean isValidPath, pass;
		isValidPath = Storage.setPath("/Users/hungngth/Downloads/Documents/ilovefood/mysave.txt");
		if (isValidPath == false) {
			pass = true;
		} else {
			pass = false;
		}
		assertTrue("test correct processing of invalid path input with slash", pass);
	}

	@Test
	public void testSetPath_validPathWithoutName() {
		boolean isValidPath, pass;
		isValidPath = Storage.setPath("/Users/hungngth/Downloads/Documents");
		if (isValidPath == true) {
			pass = true;
		} else {
			pass = false;
		}
		assertTrue("test correct processing of valid path input without given name", pass);
	}

	@Test
	public void testSetPath_validPathWithName1() {
		boolean isValidPath, pass;
		isValidPath = Storage.setPath("/Users/hungngth/Downloads/Documents/mysave.txt");
		if (isValidPath == true) {
			pass = true;
		} else {
			pass = false;
		}
		assertTrue("test correct processing of valid path input with name", pass);
	}

	@Test
	public void testSetPath_correctAssignment() {
		Storage.setPath("/Users/hungngth/Downloads");
		assertEquals("test correct assignment of path to Storage.path", Storage.path,
				"/Users/hungngth/Downloads/TBAsave.txt");
	}

	@Test
	public void testSetPath_correctPathWritten() {
		Storage.setPath("/Users/hungngth/Downloads/mysave.txt");
		try {
			FileReader fr = new FileReader("/Users/hungngth/Documents/workspace/TBA/main");
			BufferedReader br = new BufferedReader(fr);
			assertEquals("test correct path writing to savedPath.txt", "/Users/hungngth/Documents/workspace/TBA/main",
					br.readLine());
			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testWrite_correctContentWritten() {
		// System.out.println("taskList " + taskList.toString());
		Storage.write(taskList);
		String line = "";
		boolean pass = true;
		try {
			FileReader fr = new FileReader(Storage.path);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			br.close();
			String jsonString = stringBuilder.toString();
			// System.out.println("jsonString " + jsonString);
			taskListForTest = gson.fromJson(jsonString, new TypeToken<ArrayList<Task>>() {
			}.getType());

			for (int i = 0; i < taskList.size(); i++) {
				if (!taskListForTest.get(i).equals(taskList.get(i))) {
					pass = false;
				}
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue("test correct number of tasks written to savedTask.json", pass);
	}

	@Test
	public void testRead_correctContentRead() {
		// System.out.println("taskList " + taskList.toString());
		Storage.write(taskList);
		String line = "";
		boolean pass = true;
		try {
			FileReader fr = new FileReader(Storage.path);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			br.close();
			String jsonString = stringBuilder.toString();
			// System.out.println("jsonString " + jsonString);
			taskListForTest = gson.fromJson(jsonString, new TypeToken<ArrayList<Task>>() {
			}.getType());

			for (int i = 0; i < taskList.size(); i++) {
				if (!taskListForTest.get(i).equals(taskList.get(i))) {
					pass = false;
				}
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue("test correct number of tasks read from savedTask.json", pass);
	}

}
