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

public class Storage {

	// initial methods to serialise/deserialise savedTask.json with DateTime
	// formats
	final static Gson gson = Converters.registerDateTime(new GsonBuilder().setPrettyPrinting().serializeNulls()).create();
	final DateTime original = new DateTime();
	String json = gson.toJson(original);
	final DateTime reconstituted = gson.fromJson(json, DateTime.class);

	// attributes
	private static File tempSavedTask = new File("savedTask.json");
	private static File savedPath = new File("savedPath.txt");

	public static String path; // public for testing, change after done
	private static ArrayList<Task> currentTaskList = new ArrayList<Task>();

	public Storage() {

	}

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

		tempSavedTask.delete();
	}

	public static void write(ArrayList<Task> tasks) {
		try {
			if (path == null) {
				setPath(savedPath.getAbsolutePath());
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
			path = tempSavedTask.getAbsolutePath();
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
}
