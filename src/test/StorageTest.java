//@@author A0129699E
package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

// import Google Gson library
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import application.Constants;
import logic.Task;
import storage.Converters;
import storage.Storage;

public class StorageTest {
	// initial methods to serialise/deserialise savedTask.json with DateTime
	// formats
	final static Gson gson = Converters.registerDateTime(new GsonBuilder().setPrettyPrinting().serializeNulls())
			.create();
	private static final int TASKS_LENGTH = 6;

	final DateTime original = new DateTime();
	final String json = gson.toJson(original);
	final DateTime reconstituted = gson.fromJson(json, DateTime.class);

	private static final ArrayList<Task> taskList = new ArrayList<Task>();
	private static ArrayList<Task> taskListForTest = new ArrayList<Task>();

	private static final File pathFile = new File("savedPath.txt");

	private static final String path = pathFile.getAbsolutePath();
	private static final String pathDir = Storage.extractDirectory(path);
	private static final String pathWithNewName = pathDir + "/TBAsave.txt";
	private static String pathWithNewName2 = pathDir;
	private static  String invalidPathWithSlash1;
	private static  String invalidPathWithSlash2;

	private static String longPath259;
	private static String longPath260;
	private static String longPath261;

	@Before
	public void setUp1() throws Exception {
		Task[] inputTasks = new Task[TASKS_LENGTH];
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
		for (int i = 0; i < TASKS_LENGTH; i++) {
			taskList.add(inputTasks[i]);
		}
	}

	@Before
	public void getInvalidPathWithSlash() {

		//invalidPathWithSlash1 = new StringBuilder(pathDir).insert(pathDir.indexOf("/"), "xyz").toString(); // for mac
		invalidPathWithSlash1 = new StringBuilder(pathDir).insert(pathDir.indexOf("\\"), "xyz").toString(); // for windows

		//invalidPathWithSlash2 = pathDir + "ilovefood/mysave.txt"; // for mac
		invalidPathWithSlash2 = pathDir + "ilovefood\\mysave.txt"; // for windows
	}

	@Before
	public void getNewFilename() {
		pathWithNewName2 += "mysave.txt";
	}

	@Before
	public void getLongPath261() {
		longPath261 = pathDir;
		int length = pathDir.length();
		int numMoreChar = Constants.MAX_PATH_LENGTH - length;
		char[] repeat = new char[numMoreChar];
		Arrays.fill(repeat, 'c');
		//longPath261 += "/" + new String(repeat); // for mac
		longPath261 += "\\" + new String(repeat); // for windows
	}

	@Before
	public void getLongPath260() {
		longPath260 = pathDir;
		int length = pathDir.length();
		int numMoreChar = Constants.MAX_PATH_LENGTH - length - Constants.FIX_CORRECT_LENGTH;
		char[] repeat = new char[numMoreChar];
		Arrays.fill(repeat, 'c');
		//longPath260 += "/" + new String(repeat); // for mac
		longPath260 += "\\" + new String(repeat); // for windows
	}

	@Before
	public void getLongPath259() {
		longPath259 = pathDir;
		int length = pathDir.length();
		int numMoreChar = Constants.MAX_PATH_LENGTH - length - Constants.FIX_CORRECT_LENGTH - Constants.FIX_CORRECT_LENGTH;
		char[] repeat = new char[numMoreChar];
		Arrays.fill(repeat, 'c');
		//longPath259 += "/" + new String(repeat); // for mac
		longPath259 += "\\" + new String(repeat); // for windows
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
		boolean isSuccess = true;
		Storage.setPath(pathDir);
		File file = new File(pathWithNewName);
		if (!file.exists()) {
			isSuccess = false;
		}
		file.delete();
		assertTrue("test whether savefile is created (without inputting filename)", isSuccess);
	}

	@Test
	public void testSetPath_fileCreated2() {
		boolean isSuccess = true;
		Storage.setPath(pathWithNewName);
		File file = new File(pathWithNewName);
		if (!file.exists()) {
			isSuccess = false;
		}
		file.delete();
		assertTrue("test whether savefile is created (with given filename)", isSuccess);
	}

	@Test
	public void testSetPath_invalidPath1() {
		boolean isValidPath;
		isValidPath = Storage.setPath("qwerty");
		assertFalse("test correct processing of invalid path input (random typing)", isValidPath);
	}

	@Test
	public void testSetPath_invalidPath2() {
		boolean isValidPath;
		isValidPath = Storage.setPath(" ");
		assertFalse("test correct processing of invalid path input (space character)", isValidPath);
	}

	// \ / : * ? " < > | are invalid filename characters
	@Test
	public void testSetPath_invalidPath3() {
		boolean isValidPath;
		isValidPath = Storage.setPath("*");
		assertFalse("test correct processing of invalid path input (star character)", isValidPath);
	}

	// \ / : * ? " < > | are invalid filename characters
	@Test
	public void testSetPath_invalidPath4() {
		boolean isValidPath;
		isValidPath = Storage.setPath(pathDir + "/<mysave>.txt");
		assertFalse("test correct processing of invalid path input (triangle bracket character)", isValidPath);
	}

	@Test
	public void testSetPath_invalidPathWithSlash1() {
		boolean isValidPath;
		isValidPath = Storage.setPath(invalidPathWithSlash1);
		assertFalse("test correct processing of invalid path input with slash", isValidPath);
	}

	@Test
	public void testSetPath_invalidPathWithSlash2() {
		boolean isValidPath;
		isValidPath = Storage.setPath(invalidPathWithSlash2);
		assertFalse("test correct processing of invalid path input with slash", isValidPath);
	}

	@Test
	public void testSetPath_validPathWithoutName() {
		boolean isValidPath;
		isValidPath = Storage.setPath(pathDir);
		assertTrue("test correct processing of valid path input without given name", isValidPath);
	}

	@Test
	public void testSetPath_validPathWithName1() {
		boolean isValidPath;
		isValidPath = Storage.setPath(pathWithNewName);
		assertTrue("test correct processing of valid path input with name", isValidPath);
	}

	@Test
	public void testSetPath_validPathWithName2() {
		boolean isValidPath;
		isValidPath = Storage.setPath(pathWithNewName2);
		assertTrue("test correct processing of valid path input with name", isValidPath);
	}

	@Test
	public void testSetPath_validPathWithName3() {
		boolean isValidPath;
		isValidPath = Storage.setPath(pathDir + "1234.txt");
		assertTrue("test correct processing of valid path input with name", isValidPath);
	}

	@Test
	public void testSetPath_validPathWithSpaceBetween1() {
		boolean isValidPath;
		isValidPath = Storage.setPath(pathDir + "hi there.txt");
		assertTrue("test correct processing of valid path input with space in between filename", isValidPath);
	}

	@Test
	public void testSetPath_validPathWithSpaceBetween2() {
		//Storage.setPath(pathDir + "/hi there.txt"); // for mac
		Storage.setPath(pathDir + "\\hi there.txt"); // for windows
		
		// for mac
		//assertEquals("test correct processing of valid path input with space in between filename", pathDir + "/hi there.txt", Storage.enquirePath()); 

		// for windows
		assertEquals("test correct processing of valid path input with space in between filename", pathDir + "\\hi there.txt", Storage.enquirePath());
		Storage.deleteAllFiles();
	}

	@Test
	public void testSetPath_correctAssignment() {
		Storage.setPath(pathDir);
		assertEquals("test correct assignment of path to Storage.path", pathWithNewName, Storage.path);
	}

	@Test
	public void testSetPath_correctPathWritten() {
		Storage.setPath(pathWithNewName2);
		try {
			FileReader fr = new FileReader(pathFile);
			BufferedReader br = new BufferedReader(fr);
			assertEquals("test correct path writing to savedPath.txt", pathWithNewName2, br.readLine());
			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test public void testSetPath_validAnd261() {
		boolean isValidPath;
		isValidPath = Storage.setPath(longPath261);
		assertFalse("test correct processing of valid path input with name", isValidPath);
	}

	@Test public void testSetPath_validAnd259() {
		boolean isValidPath;
		isValidPath = Storage.setPath(longPath259);
		assertTrue("test correct processing of valid path input with name", isValidPath);
	}

	@Test public void testSetPath_validAnd260() {
		boolean isValidPath;
		isValidPath = Storage.setPath(longPath260);
		assertTrue("test correct processing of valid path input with name", isValidPath);
	}

	@Test 
	public void testEnquirePath_Positive1() {
		Storage.setPath(pathDir);
		assertEquals("test correct enquiring of path", pathWithNewName, Storage.enquirePath() );
	}

	@Test 
	public void testEnquirePath_Positive2() {
		Storage.setPath(pathDir + "/hi.txt");
		assertEquals("test correct enquiring of path", pathDir + "/hi.txt", Storage.enquirePath() );
	}

	@Test
	public void testWrite_correctContentWritten() {
		Storage.write(taskList);
		String line = "";
		boolean isSuccess = true;
		try {
			FileReader fr = new FileReader(Storage.path);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			br.close();
			String jsonString = stringBuilder.toString();
			taskListForTest = gson.fromJson(jsonString, new TypeToken<ArrayList<Task>>() {
			}.getType());

			for (int i = 0; i < taskList.size(); i++) {
				if (!taskListForTest.get(i).equals(taskList.get(i))) {
					isSuccess = false;
				}
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue("test correct tasks written to savedTask.json", isSuccess);
	}

	@Test
	public void testRead_correctContentRead() {
		Storage.write(taskList);
		String line = "";
		boolean isSuccess = true;
		try {
			FileReader fr = new FileReader(Storage.path);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			br.close();
			String jsonString = stringBuilder.toString();
			taskListForTest = gson.fromJson(jsonString, new TypeToken<ArrayList<Task>>() {
			}.getType());

			for (int i = 0; i < taskList.size(); i++) {
				if (!taskListForTest.get(i).equals(taskList.get(i))) {
					isSuccess = false;
				}
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue("test correct tasks read from savedTask.json", isSuccess);
	}

}
