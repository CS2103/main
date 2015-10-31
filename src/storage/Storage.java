package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// import Joda Time library
import org.joda.time.DateTime;

// import Google Gson library
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import logic.Task;
import logic.RecurTask;

public class Storage {

	// initial methods to serialise/deserialise savedTask.json with DateTime
	// formats
	final static Gson gson = Converters.registerDateTime(new GsonBuilder().setPrettyPrinting().serializeNulls()).create();
	final DateTime original = new DateTime();
	final String json = gson.toJson(original);
	final DateTime reconstituted = gson.fromJson(json, DateTime.class);

	// attributes
	public static File normalTask = new File("savedTaskNormal.json"); // public for testing, change after done
	public static File recurTask = new File("savedTaskRecur.json"); // public for testing, change after done

	public static File savedPathNormal = new File("savedPathNormal.txt"); // public for testing, change after done
	public static File savedPathRecur = new File("savedPathRecur.txt"); // public for testing, change after done

	public static String pathNormal; // public for testing, change after done
	public static String pathRecur;
	public static String path; // public for testing, change after done

	private static ArrayList<Task> currentTaskListNormal = new ArrayList<Task>();
	private static ArrayList<RecurTask> currentTaskListRecur = new ArrayList<RecurTask>();

	public Storage() { }

	public static void setPath(String path) {

		Storage.path = path;

		File fileNormal = new File(pathNormal);
		File fileRecur = new File(pathRecur);
		/*
		if (!file.exists()) {
			System.out.println("file not exists");
		}
		 */
		Storage.currentTaskListNormal = read();
		Storage.currentTaskListRecur = readRecur();

		/*
		if (file.isDirectory()) {
			path = path + "/TBASaveNormal.txt"; // this is for mac or "\\TBAsave.txt" for windows
		}
		 */
		pathNormal = path + "/TBASaveNormal.txt";
		pathRecur = path + "/TBASaveRecur.txt";

		try {
			FileWriter fw = new FileWriter(savedPathNormal.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(pathNormal);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			FileWriter fw = new FileWriter(savedPathNormal.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(pathRecur);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		write(currentTaskListNormal);
		writeRecur(currentTaskListRecur);

		normalTask.delete();
		recurTask.delete();
	}

	public static String enquirePath() {
		return Storage.path;
	}

	public static void write(ArrayList<Task> tasks) {
		try {
			if (pathNormal == null) {
				pathNormal = savedPathNormal.getAbsolutePath();
			}
			File file = new File(pathNormal);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(gson.toJson(tasks));
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Task> read() {
		try {
			FileReader fr = new FileReader(savedPathNormal);
			BufferedReader br = new BufferedReader(fr);
			Storage.path = br.readLine();

			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Task> taskList = new ArrayList<Task>();
		String line = "";
		if (pathNormal == null) {
			pathNormal = normalTask.getAbsolutePath();
		}
		try {
			FileReader fr = new FileReader(pathNormal);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			br.close();
			String jsonString = stringBuilder.toString();
			taskList = gson.fromJson(jsonString, new TypeToken<ArrayList<Task>>(){}.getType());
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return taskList;
	}

	public static void writeRecur(ArrayList<RecurTask> tasks) {
		try {
			if (path == null) {
				path = savedPathRecur.getAbsolutePath();
			}
			File file = new File(path);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(gson.toJson(tasks));
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<RecurTask> readRecur() {
		try {
			FileReader fr = new FileReader(savedPathRecur);
			BufferedReader br = new BufferedReader(fr);
			Storage.pathRecur = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<RecurTask> taskList = new ArrayList<RecurTask>();
		String line = "";
		if (pathRecur == null) {
			pathRecur = recurTask.getAbsolutePath();
		}
		try {
			FileReader fr = new FileReader(pathRecur);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			br.close();
			String jsonString = stringBuilder.toString();
			taskList = gson.fromJson(jsonString, new TypeToken<ArrayList<RecurTask>>(){}.getType());
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return taskList;
	}
}

/*
package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// import Joda Time library
import org.joda.time.DateTime;

// import Google Gson library
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import logic.Task;
import logic.RecurTask;

public class Storage {

	// initial methods to serialise/deserialise savedTask.json with DateTime
	// formats
	final static Gson gson = Converters.registerDateTime(new GsonBuilder().setPrettyPrinting().serializeNulls()).create();
	final DateTime original = new DateTime();
	final String json = gson.toJson(original);
	final DateTime reconstituted = gson.fromJson(json, DateTime.class);

	// attributes
	public static File normalTask = new File("savedTaskNormal.json"); // public for testing, change after done
	public static File recurTask = new File("savedTaskRecur.json"); // public for testing, change after done

	public static File savedPath = new File("savedPath.txt"); // public for testing, change after done

	public static String path; // public for testing, change after done
	
	private static ArrayList<Task> currentTaskList = new ArrayList<Task>();

	public Storage() { }

	public static void setPath(String path) {
		File file = new File(path);
		if (!file.exists()) {
			System.out.println("file not exists");
		}
		
		Storage.currentTaskList = read();

		if (file.isDirectory()) {
			path = path + "/TBAsave.txt"; // this is for mac or "\\TBAsave.txt" for windows
		}

		try {
			FileWriter fw = new FileWriter(savedPath.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(path);
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		Storage.path = path;

		write(currentTaskList);

		normalTask.delete();
		recurTask.delete();
	}

	public static String enquirePath() {
		return Storage.path;
	}
	
	public static void write(ArrayList<Task> tasks) {
		try {
			if (path == null) {
				path = savedPath.getAbsolutePath();
			}
			File file = new File(path);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(gson.toJson(tasks));
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Task> read() {
		try {
			FileReader fr = new FileReader(savedPath);
			BufferedReader br = new BufferedReader(fr);
			Storage.path = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<Task> taskList = new ArrayList<Task>();
		String line = "";
		if (path == null) {
			path = normalTask.getAbsolutePath();
		}
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			br.close();
			String jsonString = stringBuilder.toString();
			taskList = gson.fromJson(jsonString, new TypeToken<ArrayList<Task>>(){}.getType());
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return taskList;
	}
	
	public static void writeRecur(ArrayList<RecurTask> tasks) {
		try {
			if (path == null) {
				path = savedPath.getAbsolutePath();
			}
			File file = new File(path);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(gson.toJson(tasks));
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<RecurTask> readRecur() {
		try {
			FileReader fr = new FileReader(savedPath);
			BufferedReader br = new BufferedReader(fr);
			Storage.path = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayList<RecurTask> taskList = new ArrayList<RecurTask>();
		String line = "";
		if (path == null) {
			path = recurTask.getAbsolutePath();
		}
		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			StringBuilder stringBuilder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				stringBuilder.append(line).append("\n");
			}
			br.close();
			String jsonString = stringBuilder.toString();
			taskList = gson.fromJson(jsonString, new TypeToken<ArrayList<RecurTask>>(){}.getType());
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			e.printStackTrace();
		}
		return taskList;
	}
}
*/
