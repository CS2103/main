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

import application.Constants;

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

	private static ArrayList<Task> currentTaskList = new ArrayList<Task>();

	public Storage() {
	}

	public static boolean containSlash(String path) {
		if (path.contains("\\") || path.contains("/")) {
			return true;
		} else {
			return false;
		}
	}

	public static String extractDirectory(String path) {
		int i = path.lastIndexOf("/");
		String subPath = path.substring(0, i);
		return subPath;
	}

	public static String extractFilename(String path) {
		int i = path.lastIndexOf("/");
		String subPath = path.substring(i + Constants.FIX_CORRECT_INDEX);
		return subPath;
	}

	// : * ? " < > |
	public static boolean containInvalidChar(String path) {
		if (path.contains(":") || path.contains("*") || path.contains("?") || path.contains("\"") || path.contains("<") || path.contains(">") || path.contains("|")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean setPath(String newPath) {
		File checkFile = new File(newPath);
		if (isInvalidPath(checkFile)) {

			if (!containSlash(newPath)) {
				return false;

			} else {
				String filename = extractFilename(newPath);
				System.out.println("filename " + filename);
				if (containInvalidChar(filename)) {
					return false;
				}
				String directory = extractDirectory(newPath);
				System.out.println("directory " + directory);
				File file = new File(directory);
				if (isInvalidPath(file)) {
					return false;
				} else {
					Storage.path = newPath;
					deleteOldSave();
					writePathToFile();

					System.out.println("newPath " + newPath);
					System.out.println("path1 " + path);
					Storage.currentTaskList = read();
					System.out.println("path2 " + path);
					write(currentTaskList);
					savedTask.delete();
					return true;
				}
			}

		} else {
			Storage.currentTaskList = read();
			if (checkFile.isDirectory()) {
				appendSaveName(newPath);
				writePathToFile();
			} else {
				Storage.path = newPath;
			}
			write(currentTaskList);
			savedTask.delete();
			return true;
		}
	}

	private static void deleteOldSave() {
		try {
			FileReader fr = new FileReader(savedPath);
			BufferedReader br = new BufferedReader(fr);
			System.out.println("here2 " + Storage.path);
			String oldPath = br.readLine();
			System.out.println("here3 " + Storage.path);
			br.close();
			File f = new File(oldPath);
			f.delete();
		} catch (FileNotFoundException e) {
			Logger.getLogger("Log").log(Level.SEVERE, "Cannot find savedPath file at specified location");
		} catch (IOException e) {
			Logger.getLogger("Log").log(Level.SEVERE, "Unable to read from savedPath file");
		}
		
	}

	private static boolean isInvalidPath(File file) {
		if (file.exists()) {
			return false;
		} else {
			return true;
		}
	}

	public static void appendSaveName(String newPath) {
		// Storage.path = newPath + "\\TBAsave.txt"; // for windows
		Storage.path = newPath + "/TBAsave.txt"; // for macOS

	}

	public static void writePathToFile() {
		try {
			FileWriter fw = new FileWriter(savedPath.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Storage.path);
			bw.close();

		} catch (IOException e) {
			Logger.getLogger("Log").log(Level.SEVERE, "Unable to write to savePath file");
		}
	}

	public static String enquirePath() {
		return Storage.path;
	}

	public static void write(ArrayList<Task> tasks) {
		try {
			if (Storage.path == null) {
				Storage.path = savedPath.getAbsolutePath();
			}
			File file = new File(Storage.path);
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
			System.out.println("here2 " + Storage.path);
			Storage.path = br.readLine();
			System.out.println("here3 " + Storage.path);
			br.close();
		} catch (FileNotFoundException e) {
			Logger.getLogger("Log").log(Level.SEVERE, "Cannot find savedPath file at specified location");
		} catch (IOException e) {
			Logger.getLogger("Log").log(Level.SEVERE, "Unable to read from savedPath file");
		}
		ArrayList<Task> taskList = new ArrayList<Task>();
		String line = "";
		if (Storage.path == null) {
			System.out.println("here " + Storage.path);
			Storage.path = savedTask.getAbsolutePath();
		}
		try {
			FileReader fr = new FileReader(Storage.path);
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
