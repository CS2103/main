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

import application.Constants;
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

	private static ArrayList<Task> currentTaskList = new ArrayList<Task>();

	public Storage() {
	}

	public static boolean setPath(String newPath) {
		File checkFile = new File(newPath);
		boolean canSetPath;
		if (!isValidPath(checkFile)) {
			if (!containSlash(newPath)) {
				canSetPath = false;
				return canSetPath;
			} else {
				canSetPath = processInvalidPath(newPath);
				return canSetPath;
			}
		} else {
			canSetPath = processValidPath(newPath, checkFile);
			return canSetPath;
		}
	}

	public static boolean containSlash(String path) {
		if (path.contains("\\") || path.contains("/")) {
			return true;
		} else {
			return false;
		}
	}

	public static String extractDirectory(String path) {
		int i = path.lastIndexOf("\\");
		String subPath = path.substring(0, i);
		return subPath;
	}

	public static String extractFilename(String path) {
		int i = path.lastIndexOf("\\");
		String subPath = path.substring(i + Constants.FIX_CORRECT_INDEX);
		return subPath;
	}

	// : * ? " < > |
	public static boolean containInvalidChar(String path) {
		if (path.contains(":") || path.contains("*") || path.contains("?") || path.contains("\"") || path.contains("<")
				|| path.contains(">") || path.contains("|")) {
			return true;
		} else {
			return false;
		}
	}

	private static boolean processValidPath(String newPath, File checkFile) {
		Storage.currentTaskList = read();

		if (checkFile.isDirectory()) {
			appendSaveName(newPath);
			writePathToFile();
		} else {
			Storage.path = newPath;
		}
		return cleanUpUnusedFile();
	}

	private static boolean cleanUpUnusedFile() {
		write(currentTaskList);
		savedTask.delete();
		return true;
	}

	private static boolean processInvalidPath(String newPath) {
		String filename = extractFilename(newPath);

		if (containInvalidChar(filename)) {
			return false;
		}

		String directory = extractDirectory(newPath);
		File file = new File(directory);

		if (!isValidPath(file)) {
			return false;
		} else {
			Storage.path = newPath;
			deleteOldSaveFile();
			writePathToFile();
			Storage.currentTaskList = read();
			return cleanUpUnusedFile();
		}
	}

	private static void deleteOldSaveFile() {
		try {
			FileReader fr = new FileReader(savedPath);
			BufferedReader br = new BufferedReader(fr);
			String oldPath = br.readLine();
			br.close();
			File file = new File(oldPath);
			file.delete();

		} catch (FileNotFoundException e) {
			Logger.getLogger("Log").log(Level.SEVERE, "Cannot find savedPath file at specified location");

		} catch (IOException e) {
			Logger.getLogger("Log").log(Level.SEVERE, "Unable to read from savedPath file");
		}
	}

	private static boolean isValidPath(File file) {
		if (file.exists()) {
			return true;
		} else {
			return false;
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
			handleNullPath();
			File file = new File(Storage.path);
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(gson.toJson(tasks));
			bw.close();

		} catch (IOException e) {
			Logger.getLogger("Log").log(Level.SEVERE, "Unable to write to save file");
		}
	}

	private static void handleNullPath() {
		if (Storage.path == null) {
			Storage.path = savedTask.getAbsolutePath();
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
		handleNullPath();

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
