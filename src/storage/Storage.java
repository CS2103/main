//@@author A0129699E
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

// import Joda Time library
import org.joda.time.DateTime;

// import Google Gson library
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import application.Constants;
import application.LogHandler;
import logic.Task;

public class Storage {

    // initial methods to serialise/deserialise savedTask.json with DateTime
    final static Gson gson = Converters.registerDateTime(new GsonBuilder().setPrettyPrinting().serializeNulls())
	    .create();
    final DateTime original = new DateTime();
    final String json = gson.toJson(original);
    final DateTime reconstituted = gson.fromJson(json, DateTime.class);

    // attributes
    public static File savedTask = new File("TBAsave.json");

    public static File savedPath = new File("TBApath.txt");

    public static File savedTheme = new File("TBAtheme.txt");

    public static int themeIndex;

    public static String path;

    private static ArrayList<Task> currentTaskList = new ArrayList<Task>();

    // change saving location to "newPath"
    public static boolean setPath(String newPath) {
	assert (newPath != null);
	if (!isValidLength(newPath)) {
	    return false;
	} else {
	    return processNewPath(newPath);
	}
    }

    // check "newPath"
    private static boolean processNewPath(String newPath) {
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

    private static boolean isValidLength(String newPath) {
	assert (newPath != null);
	if (newPath.length() > Constants.MAX_PATH_LENGTH) {
	    return false;
	}
	return true;
    }

    public static boolean containSlash(String path) {
	if (path.contains("\\") || path.contains("/")) {
	    return true;
	} else {
	    return false;
	}
    }

    public static String extractDirectory(String path) {
	// int i = path.lastIndexOf("/"); // for mac
	assert (path != null);
	int i = path.lastIndexOf("\\"); // for windows

	String subPath = path.substring(0, i);
	return subPath;
    }

    public static String extractFilename(String path) {
	// int i = path.lastIndexOf("/"); // for mac
	int i = path.lastIndexOf("\\"); // for windows

	String subPath = path.substring(i + Constants.FIX_CORRECT_INDEX);
	return subPath;
    }

    // : * ? " < > | are invalid characters for filename
    public static boolean containInvalidChar(String path) {
	if (path.contains(":") || path.contains("*") || path.contains("?") || path.contains("\"") || path.contains("<")
		|| path.contains(">") || path.contains("|")) {
	    return true;
	} else {
	    return false;
	}
    }

    // process the "newPath" if it passes the isValidPath check
    private static boolean processValidPath(String newPath, File checkFile) {
	assert (newPath != null);
	Storage.currentTaskList = read();
	deleteOldSaveFile();

	if (checkFile.isDirectory()) {
	    appendSaveName(newPath);
	    writePathToFile();
	} else {
	    Storage.path = newPath;
	    writePathToFile();
	}
	return cleanUpUnusedFile();
    }

    private static boolean cleanUpUnusedFile() {
	write(currentTaskList);
	savedTask.delete();
	return true;
    }

    // process the "newPath" if it passes the isValidPath check
    private static boolean processInvalidPath(String newPath) {
	String filename = extractFilename(newPath);
	Storage.currentTaskList = read();

	if (containInvalidChar(filename)) {
	    return false;
	}

	String directory = extractDirectory(newPath);
	File file = new File(directory);

	if (!isValidPath(file)) {
	    return false;
	} else {
	    deleteOldSaveFile();
	    Storage.path = newPath;
	    writePathToFile();
	    return cleanUpUnusedFile();
	}
    }

    // delete old save file after calling setPath()
    private static void deleteOldSaveFile() {
	try {
	    FileReader fr = new FileReader(savedPath);
	    BufferedReader br = new BufferedReader(fr);
	    String oldPath = br.readLine();
	    br.close();
	    File file = new File(oldPath);
	    file.delete();

	} catch (FileNotFoundException e) {
	    LogHandler.log(Level.SEVERE, "Cannot find savedPath file at specified location");

	} catch (IOException e) {
	    LogHandler.log(Level.SEVERE, "Unable to read from savedPath file");
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
	Storage.path = newPath + "\\TBAsave.txt"; // for windows
	// Storage.path = newPath + "/TBAsave.txt"; // for macOS
    }

    // write Storage.path to TBAsave.txt
    public static void writePathToFile() {
	try {
	    FileWriter fw = new FileWriter(savedPath.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(Storage.path);
	    bw.close();

	} catch (IOException e) {
	    LogHandler.log(Level.SEVERE, "Unable to write to savePath file");
	}
    }

    // return current saving location
    public static String enquirePath() {
	return Storage.path;
    }

    // main method to call when writing data to save file
    public static void write(ArrayList<Task> tasks) {
	handleNullPath();
	writeTasksToFile(tasks);
    }

    // write data to file in json format
    private static void writeTasksToFile(ArrayList<Task> tasks) {
	try {
	    File file = new File(path);
	    FileWriter fw = new FileWriter(file);
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(gson.toJson(tasks));
	    bw.close();

	} catch (IOException e) {
	    LogHandler.log(Level.SEVERE, "Unable to write to save file");
	}
    }

    // update Storage.path if it is null
    private static void handleNullPath() {
	if (Storage.path == null) {
	    Storage.path = savedTask.getAbsolutePath();
	}
    }

    // main method to call when reading data from save file
    public static ArrayList<Task> read() {
	getSavePath();
	return readFromSaveFile();
    }

    private static ArrayList<Task> readFromSaveFile() {
	ArrayList<Task> taskList = new ArrayList<Task>();
	handleNullPath();
	return convertSaveData(taskList);
    }

    // read save file and convert data read to ArrayList<Task>
    private static ArrayList<Task> convertSaveData(ArrayList<Task> taskList) {
	String line;
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
	    LogHandler.log(Level.SEVERE, "Cannot find save file at specified location");

	} catch (IOException e) {
	    LogHandler.log(Level.SEVERE, "Unable to read from save file");
	}
	return taskList;
    }

    // Read path directory in savedPath and update Storage.path
    private static void getSavePath() {
	try {
	    FileReader fr = new FileReader(savedPath);
	    BufferedReader br = new BufferedReader(fr);
	    Storage.path = br.readLine();
	    br.close();

	} catch (FileNotFoundException e) {
	    LogHandler.log(Level.SEVERE, "Cannot find savedPath file at specified location");

	} catch (IOException e) {
	    LogHandler.log(Level.SEVERE, "Unable to read from savedPath file");
	}
    }

    // for testing purpose
    public static void deleteAllFiles() {
	File f = new File(Storage.path);
	f.delete();
	savedPath.delete();
    }

    public static void saveThemeIndex(int themeIndex) {
	assert (themeIndex < Constants.THEME_LIST.length && themeIndex >= 0) : Constants.ERROR_INVALID_THEME_INDEX;
	try {
	    FileWriter fw = new FileWriter(savedTheme.getAbsoluteFile());
	    BufferedWriter bw = new BufferedWriter(fw);
	    bw.write(String.valueOf(themeIndex));
	    bw.close();

	} catch (IOException e) {
	    LogHandler.log(Level.SEVERE, "Unable to write to savedTheme file");
	}
    }

    public static int readThemeIndex() {
	try {
	    FileReader fr = new FileReader(savedTheme.getAbsolutePath());
	    BufferedReader br = new BufferedReader(fr);
	    String themeIndexStr = br.readLine();
	    Storage.themeIndex = Integer.parseInt(themeIndexStr);
	    br.close();
	} catch (FileNotFoundException e) {
	    LogHandler.log(Level.SEVERE, "Cannot find savedPath file at specified location");

	} catch (IOException e) {
	    LogHandler.log(Level.SEVERE, "Unable to read from savedPath file");
	}
	return themeIndex;
    }
}
