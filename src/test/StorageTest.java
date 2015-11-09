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
	private static final String path = pathFile.getAbsolutePath();
	private static final String pathDir = Storage.extractDirectory(path);
	private static final String pathWithNewName = pathDir + "/TBAsave.txt";
	private static String pathWithNewName2 = pathDir;
	private static  String invalidPathWithSlash1;
	private static  String invalidPathWithSlash2;
	
	// 255 characters
	private static final String longName255 = "aaaaaaaaaaaasasasasasasasasasasasaaaaaaaaaaaasasasasasasasasasasasaaaaaaaaaaaasasasasasasasasasasasaaaaaaaaaaaasasasasasasasasasasasaaaaaaaaaaaasasasasasasasasasasasaaaaaaaaaaaasasasasasasasasasasasaaaaaaaaaaaasasasasasasasasasasasaasasasasasasasasasasasa";
	
	// 256 character
	private static final String longName256 = "Saaaaaaaaaaaasasasasasasasasasasasaaaaaaaaaaaasasasasasasasasasasasaaaaaaaaaaaasasasasasasasasasasasaaaaaaaaaaaasasasasasasasasasasasaaaaaaaaaaaasasasasasasasasasasasaaaaaaaaaaaasasasasasasasasasasasaaaaaaaaaaaasasasasasasasasasasasaasasasasasasasasasasasa";

	private static String validAnd255;
	private static String validAnd256;

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

	@Before
	public void getInvalidPathWithSlash() {
		invalidPathWithSlash2 = pathDir + "ilovefood/mysave.txt"; // for mac
		//	invalidPathWithSlash2 = pathDir + "ilovefood\\mysave.txt";  //for windows
		int length = pathDir.length();
		invalidPathWithSlash1 = new StringBuilder(pathDir).insert(length/3, "xyz").toString();
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa " + pathWithNewName);
		pathWithNewName2 += "mysave.txt";
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaa long " + longName255.length());
		validAnd255 = pathDir + longName255;
		validAnd256 = pathDir + longName256;
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
		boolean isValidPath, isSuccess;
		isValidPath = Storage.setPath("qwerty");
		if (isValidPath == false) {
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		assertTrue("test correct processing of invalid path input (random typing)", isSuccess);
	}

	@Test
	public void testSetPath_invalidPath2() {
		boolean isValidPath, isSuccess;
		isValidPath = Storage.setPath(" ");
		if (isValidPath == false) {
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		assertTrue("test correct processing of invalid path input (space character)", isSuccess);
	}

	// \ / : * ? " < > | are invalid filename characters
	@Test
	public void testSetPath_invalidPath3() {
		boolean isValidPath, isSuccess;
		isValidPath = Storage.setPath("*");
		if (isValidPath == false) {
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		assertTrue("test correct processing of invalid path input (star character)", isSuccess);
	}

	// \ / : * ? " < > | are invalid filename characters
	@Test
	public void testSetPath_invalidPath4() {
		boolean isValidPath, isSuccess;
		isValidPath = Storage.setPath("/Users/hungngth/Download/Document/<mysave>.txt");
		if (isValidPath == false) {
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		assertTrue("test correct processing of invalid path input (triangle bracket character)", isSuccess);
	}

	@Test
	public void testSetPath_invalidPathWithSlash1() {
		boolean isValidPath, isSuccess;
		isValidPath = Storage.setPath(invalidPathWithSlash1);
		if (isValidPath == false) {
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		assertTrue("test correct processing of invalid path input with slash", isSuccess);
	}

	@Test
	public void testSetPath_invalidPathWithSlash2() {
		boolean isValidPath, isSuccess;
		isValidPath = Storage.setPath(invalidPathWithSlash2);
		if (isValidPath == false) {
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		assertTrue("test correct processing of invalid path input with slash", isSuccess);
	}

	@Test
	public void testSetPath_validPathWithoutName() {
		boolean isValidPath, isSuccess;
		isValidPath = Storage.setPath(pathDir);
		if (isValidPath == true) {
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		assertTrue("test correct processing of valid path input without given name", isSuccess);
	}

	@Test
	public void testSetPath_validPathWithName1() {
		boolean isValidPath, isSuccess;
		isValidPath = Storage.setPath(pathWithNewName);
		if (isValidPath == true) {
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		assertTrue("test correct processing of valid path input with name", isSuccess);
	}

	@Test
	public void testSetPath_correctAssignment() {
		Storage.setPath("pathDir");
		assertEquals("test correct assignment of path to Storage.path", Storage.path,
				pathWithNewName);
	}

	@Test
	public void testSetPath_correctPathWritten() {
		Storage.setPath(pathWithNewName2);
		try {
			FileReader fr = new FileReader(pathFile);
			BufferedReader br = new BufferedReader(fr);
			assertEquals("test correct path writing to savedPath.txt", pathWithNewName2,
					br.readLine());
			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	@Test public void testSetPath_longFilename() {
		boolean isValidPath, isSuccess;
		isValidPath = Storage.setPath(longName255);
		if (isValidPath == true) {
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		assertFalse("test correct processing of valid path input with name", isSuccess);
	}
	
	@Test public void testSetPath_validAnd255() {
		boolean isValidPath, isSuccess;
		isValidPath = Storage.setPath(validAnd255);
		if (isValidPath == true) {
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		assertTrue("test correct processing of valid path input with name", isSuccess);
	}
	
	@Test public void testSetPath_validAnd256() {
		boolean isValidPath, isSuccess;
		isValidPath = Storage.setPath(validAnd256);
		if (isValidPath == true) {
			isSuccess = true;
		} else {
			isSuccess = false;
		}
		assertTrue("test correct processing of valid path input with name", isSuccess);
	}
	@Test
	public void testWrite_correctContentWritten() {
		// System.out.println("taskList " + taskList.toString());
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
