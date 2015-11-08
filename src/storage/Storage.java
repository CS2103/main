package storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
	final static Gson gson = Converters.registerDateTime(new GsonBuilder().setPrettyPrinting().serializeNulls())
			.create();
	final DateTime original = new DateTime();
	final String json = gson.toJson(original);
	final DateTime reconstituted = gson.fromJson(json, DateTime.class);

	// attributes
	public static File savedTask = new File("TBAsave.json"); // public for
																// testing,
																// change after
																// done

	public static File savedPath = new File("TBApath.txt"); // public for
															// testing, change
															// after done

	public static String path; // public for testing, change after done

	private static ArrayList<Task> currentTasks = new ArrayList<Task>();

	public Storage() {
	}

	public static boolean setPath(String newPath) {
		File checkFile = new File(newPath);
		if (isInvalidPath(checkFile)) {
			return false;
		} else {
			Storage.currentTasks = read();
			if (checkFile.isDirectory()) {
				appendSaveName(newPath);
				writePathToFile();
			} else {
				Storage.path = newPath;
			}
			write(currentTasks);
			savedTask.delete();
			return true;
		}
	}

	private static boolean isInvalidPath(File file) {
		if (!file.exists()) {
			return true;
		} else {
			return false;
		}
	}

	public static void appendSaveName(String newPath) {
		Storage.path = newPath + "\\TBAsave.txt"; // "/TBAsave.txt" for macOS or
													// "\\TBAsave.txt" for
													// windows

	}

	public static void writePathToFile() {
		try {
			FileWriter fw = new FileWriter(savedPath.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(path);
			bw.close();

		} catch (IOException e) {
			Logger.getLogger("Log").log(Level.SEVERE, "Unable to write to savePath file");
		}
	}

	public static String enquirePath() {
		return Storage.path;
	}

	public static void write(ArrayList<Task> tasks) {
		System.out.println(path);
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
			Logger.getLogger("Log").log(Level.SEVERE, "Unable to write to save file");
		}
	}

	public static ArrayList<Task> read() {

		try {
			FileReader fr = new FileReader(savedPath);
			BufferedReader br = new BufferedReader(fr);
			Storage.path = br.readLine();
			br.close();
		} catch (FileNotFoundException e) {
			Logger.getLogger("Log").log(Level.SEVERE, "Cannot find savedPath file at specified location");
		} catch (IOException e) {
			Logger.getLogger("Log").log(Level.SEVERE, "Unable to read from savedPath file");
		}
		ArrayList<Task> taskList = new ArrayList<Task>();
		String line = "";
		if (path == null) {
			path = savedTask.getAbsolutePath();
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
			taskList = gson.fromJson(jsonString, new TypeToken<ArrayList<Task>>() {
			}.getType());
		} catch (FileNotFoundException e) {
			Logger.getLogger("Log").log(Level.SEVERE, "Cannot find save file at specified location");
		} catch (IOException e) {
			Logger.getLogger("Log").log(Level.SEVERE, "Unable to read from save file");

		}
		return taskList;
	}

}
