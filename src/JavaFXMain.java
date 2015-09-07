import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class JavaFXMain extends Application {

	protected static File file;
	protected static Path path;
	protected static Scene mainScene;
	protected static Scene taskScene;
	protected static Stage primaryStage;
	protected static MainController mainController;
	protected static MainController taskController;

	protected static String input;

	protected static String taskName;
	protected static String start;
	protected static String end;
	protected static String priority;
	protected static String taskDescription;

	private static String command;
	protected static Text text;
	private static String toDisplay;
	private static String[] inputArray;
	private int wordPosition;

	private static List<String> lines = new ArrayList<String>();
	protected static ArrayList<String> keywords = new ArrayList<String>();
	protected static ArrayList<String> keywords_priority = new ArrayList<String>();
	protected static ArrayList<String> keywords_add = new ArrayList<String>();
	protected static ArrayList<String> keywords_start = new ArrayList<String>();
	protected static ArrayList<String> keywords_end = new ArrayList<String>();
	protected static ArrayList<String> keywords_description = new ArrayList<String>();
	protected static ArrayList<Task> tasks_array = new ArrayList<Task>();

	private static final String MESSAGE_WELCOME = "Welcome to TextBuddyAwesome\n";
	private static final String MESSAGE_ENTER_COMMAND = "command: ";
	private static final String MESSAGE_ADD_SUCCESS = "added to %1$s: “%2$s”";
	private static final String MESSAGE_DISPLAY = "%1$s. %2$s\n";
	private static final String MESSAGE_DELETE = "deleted from %1$s: “%2$s”";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	private static final String MESSAGE_EMPTY_FILE = "%1$s is empty";

	private static final String ERROR_FILENAME_INVALID = "Filename is invalid";
	private static final String ERROR_COMMAND_TYPE_UNRECOGNIZED = "Unrecognized command type";
	private static final String ERROR_COMMAND_TYPE_NULL = "Command type string cannot be null";
	private static final String ERROR_INVALID_FORMAT = "invalid command format :%1$s";
	private static final String ERROR_RUNTIME = "Runtime error occured";
	private static final String ERROR_INVALID_COMMAND = "invalid command.\n";

	@Override
	public void start(Stage primaryStage) throws IOException {
		Parent mainFxml;
		Parent taskFxml;
		FXMLLoader mainLoader;
		FXMLLoader taskLoader;

		JavaFXMain.primaryStage = primaryStage;
		primaryStage.initStyle(StageStyle.TRANSPARENT);

		mainLoader = new FXMLLoader(getClass().getResource("mainfxml.fxml"));
		taskLoader = new FXMLLoader(getClass().getResource("taskfxml.fxml"));
		mainFxml = mainLoader.load();
		taskFxml = taskLoader.load();	
		mainController = (MainController)mainLoader.getController();
		taskController = (MainController)taskLoader.getController();
		
		System.out.println(mainFxml.getChildrenUnmodifiable());
		mainScene = new Scene(mainFxml);
		taskScene = new Scene(taskFxml);

		showMainScene(primaryStage);
		
		showToUser(MESSAGE_WELCOME);
	}

	public static void handleCommand(String input) {

		System.out.println("Processing input: " + input);
		input = input.trim();
		inputArray = input.split(" ");

		toConsole("No of words: " + inputArray.length);

		clearTaskVariables();

		//System.out.println("showed taskScene");
		/*    	
    	//add meet rongxiang start 0700 end 1000 priority high
    	//add meet rongxiang priority high start 0700 end 1000
		 */    	

		System.out.println();
		switch(inputArray[0].trim()) {
		case "debug" :
			System.out.println("INPUT MATCHES COMMAND TYPE: " + "debug");
			toDisplay = "DEBUGGING MODE\n";
			text = new Text(toDisplay);
			showToUserText(text);
			showToUser("No. of tasks: " + tasks_array.size() + "\n");
			break;
		case "add" :
			System.out.println("INPUT MATCHES COMMAND TYPE ADD");
			addTask();
			
			break;
		case "clear" :
			clearTextOut();
			break;
		case "" :
			break;
		case "exit" :
			exitProgram(null);
			break;
		default :
			showToUser(ERROR_INVALID_COMMAND);
			break;
		}
	}

	protected static void addTask() {
		for (int i = 0 ; i < inputArray.length-1; i++){

			if (isTaskName(inputArray[i])) {			// Obtain task name
				while (i<inputArray.length-1 && !isKeyword(inputArray[i+1])) {
					JavaFXMain.taskName += (" " + inputArray[++i]);
				}
			} else if (isPriority(inputArray[i])) {		// Obtain priority
				while (i<inputArray.length-1 && !isKeyword(inputArray[i+1])) {
					JavaFXMain.priority += (" " + inputArray[++i]);
				}
			} else if (isStart(inputArray[i])) {		// Obtain start
				while (i<inputArray.length-1 && !isKeyword(inputArray[i+1])) {
					JavaFXMain.start += (" " + inputArray[++i]);
				}
			} else if (isEnd(inputArray[i])) {			// Obtain end
				while (i<inputArray.length-1 && !isKeyword(inputArray[i+1])) {
					JavaFXMain.end += (" " + inputArray[++i]);
				}
			}
		}
		trimTaskVariables();

		toConsole("TaskName = " + JavaFXMain.taskName);
		toConsole("Priority = " + priority);
		toConsole("Start = " + start);
		toConsole("End = " + end);

		
		JavaFXMain.showTaskScene(JavaFXMain.primaryStage);
		taskController.updateTaskView(taskName);
	}

	public static void main(String[] args) {
		setFileName("textbuddyawesome.txt");
		readStoredData(file);
		initializeKeywordArrays();
		System.out.println("launched");
		launch(args);		
	}

	private static void setFileName(String fileName) {
		try {
			if (fileName.trim().equals("")) {
				throw new Error(ERROR_FILENAME_INVALID);
			}
			file = new File(fileName);
			path = Paths.get(fileName);
			Files.createFile(path);
		} catch (FileAlreadyExistsException x) {

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void initializeKeywordArrays() {
		Collections.addAll(keywords, "add", "new", "priority", "pr", "p", "pri", "start", "begin", "from", "end", "finish");
		Collections.addAll(keywords_add, "add", "new");
		Collections.addAll(keywords_priority, "priority", "pr", "p", "pri");
		Collections.addAll(keywords_start, "start", "st", "begin", "from");
		Collections.addAll(keywords_end, "end", "finish", "until", "due");
	}
	public static void readStoredData(File file) {
		try {
			lines = Files.readAllLines(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (lines.size() == 0) {
			System.out.println("No stored tasks");
		} else {

		}
	}
	public void createTasksFromFile() {
		String[] taskArguments;
		for (String taskData : lines) {
			taskArguments = taskData.split(" ");
			tasks_array.add(new Task(taskArguments[0], taskArguments[1], taskArguments[2]));
		}
	}
	public static void writeTaskToFile() {
		//TODO
	}
	public static void showTaskScene(Stage primaryStage) {
		primaryStage.setTitle("New Task");
		primaryStage.setScene(taskScene);
		primaryStage.sizeToScene();
		primaryStage.show();
		toConsole("SHOWED TASKSCENE");
	}
	public static void showMainScene(Stage primaryStage) {
		primaryStage.setTitle("TextBuddyAwesome");
		primaryStage.setScene(mainScene);		
		primaryStage.sizeToScene();
		primaryStage.show();
	}
	public static void clearTaskVariables() {
		JavaFXMain.taskName = "";
		start = "";
		end = "";
		priority = "";
		taskDescription = "";
	}
	public static void trimTaskVariables() {
		JavaFXMain.taskName = JavaFXMain.taskName.trim();
		start = start.trim();
		end = end.trim();
		priority = priority.trim();
		taskDescription = taskDescription.trim();
	}
	public static boolean isKeyword (String string) {
		return JavaFXMain.keywords.contains(string);
	}
	public static boolean isTaskName(String string) {
		return JavaFXMain.keywords_add.contains(string);
	}
	public static boolean isPriority(String string) {
		return JavaFXMain.keywords_priority.contains(string);
	}
	public static boolean isStart(String string) {
		return JavaFXMain.keywords_start.contains(string);
	}
	public static boolean isEnd(String string) {
		return JavaFXMain.keywords_end.contains(string);
	}
	public static void exitProgram(ActionEvent event) {
		System.exit(0);
	}
	public static void showToUser(String string) {
		mainController.showCustom(string);
	}
	public static void showToUserText(Text text) {
		mainController.showTextToUser(text);
	}
	public static void clearTextOut() {
		mainController.clearTextOut();
	}
	public static void toConsole(String text) {
		System.out.println(text);
	}
	public void toConsoleErr(String text) { 
		System.err.format(text);
		System.out.println();
	}
}