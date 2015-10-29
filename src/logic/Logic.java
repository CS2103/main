package logic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;

import application.Constants;
import parser.CommandParser;
import parser.Parser;
import parser.TitleParser;
import storage.Storage;

public class Logic {

	Parser parser = new Parser();
	TaskBin bin = new TaskBin();

	public Logic() {
		bin.init();
	}

	public ArrayList<Task> inputHandler(String input) throws ParseException, InvalidTimeException {
		String command = CommandParser.getCommand(input);
		if (command.equalsIgnoreCase(("add"))) {
			if (parser.getRecurValue(input).equals(null)) {
				addTask(input);
			} else {
				addRecur(input);
			}
			return bin.returnDisplay();
		} else if (command.equalsIgnoreCase("home")) {
			return displayHome();
		} else if (command.equalsIgnoreCase("setpath")) {
			Storage.setPath(input.split(" ")[1].trim());
			return displayHome();
		} else if (command.equalsIgnoreCase("delete")) {
			return deleteTaskByIndex(parser.getIndex(input));
		} else if (command.equals("edit")) {
			return editTask(parser.getIndex(input), parser.getField(input), TitleParser.getEditTitle(input));
		} else if (command.equals("mark")) {
			return markTaskByIndex(parser.getIndexes(input));
		} else if (command.equals("unmark")) {
			return unMarkTaskByIndex(parser.getIndexes(input));
		} else if (command.equalsIgnoreCase("display")) {

		} else if (command.equalsIgnoreCase("recur")) {
			DateTime endTime = new DateTime(2015, 11, 25, 0, 0);
			addRecurTask(input, endTime);
		} else if (command.equalsIgnoreCase("search")) {
			System.out.println("DEBUG " + parser.getTitle(input));
			ArrayList<Task> result = searchEntries(parser.getTitle(input));
			bin.setDisplay(result);
			return bin.returnDisplay();
		} else if (command.equalsIgnoreCase("undo")) {
			bin.undo();
			return bin.returnDisplay();
		} else if (command.equalsIgnoreCase("redo")) {
			bin.redo();
			return bin.returnDisplay();
		}
		return null;
	}

	private ArrayList<Task> addRecur(String input) throws InvalidTimeException {
		DateTime now = new DateTime();
		DateTime endDate = now.plusYears(1);
		String recurValue = parser.getRecurValue(input);
		String title = parser.getTitle(input);
		if (title.length() != 0) {
			DateTime startTime = parser.getStartDateTime(input);
			DateTime endTime = parser.getEndDateTime(input);
			System.out.println("Title: " + title);
			System.out.println("StartTime: " + startTime);
			System.out.println("EndTime: " + endTime);
			RecurTask newTask = new RecurTask(title, startTime, endTime, endDate, recurValue);
			bin.add(newTask);
			bin.setDisplay();
		}
		return bin.returnDisplay();

		// TODO Auto-generated method stub

	}

	public ArrayList<Task> addTask(String input) throws ParseException, InvalidTimeException {
		String title = parser.getTitle(input);
		if (title.length() != 0) {
			DateTime startTime = parser.getStartDateTime(input);
			DateTime endTime = parser.getEndDateTime(input);
			System.out.println("Title: " + title);
			System.out.println("StartTime: " + startTime);
			System.out.println("EndTime: " + endTime);

			Task newTask = new Task(title, startTime, endTime);
			bin.add(newTask);
			bin.setDisplay();
		}
		return bin.returnDisplay();
	}

	public ArrayList<Task> addRecurTask(String input, DateTime endDate) throws InvalidTimeException {

		return startupDisplay();
	}

	public ArrayList<Task> displayHome() {
		bin.setDisplay();
		return bin.returnDisplay();
	}

	public ArrayList<Task> displayCurrent() {
		return this.bin.returnDisplay();
	}

	public ArrayList<Task> editTask(int index, String field, String info) {
		Task toEdit = bin.returnDisplay().get(index - 1);
		switch (field) {
		case "title":
			bin.editTitle(toEdit, info);
			break;
		case "description":
			bin.editDescription(toEdit, info);
			break;
		case "start":
			bin.editStartingDate(toEdit, parser.getDateTime(info));
			break;
		case "end":
			bin.editEndingDate(toEdit, parser.getDateTime(info));
			break;
		}
		return bin.returnDisplay();
	}

	public ArrayList<Task> undoChange() {
		bin.undo();
		return bin.returnDisplay();
	}

	public ArrayList<Task> redoChange() {
		bin.redo();
		return bin.returnDisplay();
	}

	public ArrayList<Task> deleteTaskByName(String input) throws ParseException {
		String title = parser.getTitle(input);
		DateTime endingDate = parser.getEndDateTime(input);
		if (endingDate == null) {
			endingDate = DateTime.now();
		}
		ArrayList<Task> result = bin.findTaskByTitle(title);
		if (result.size() > 1) {
			result = bin.findTaskByDate(result, endingDate);
		}
		bin.delete(result.get(0));
		bin.setDisplay();
		return bin.returnDisplay();
	}

	public ArrayList<Task> deleteTaskByIndex(int index) {
		ArrayList<Task> display = bin.returnDisplay();
		Task toDel = new Task();
		toDel = display.get(index - 1);
		bin.delete(toDel);
		bin.setDisplay();
		return bin.returnDisplay();
	}

	public void showOverdue() {
	}

	public ArrayList<Task> searchEntries(String keyWord) {
		ArrayList<Task> result = bin.findTaskByTitle(keyWord);
		bin.setDisplay(result);
		return bin.returnDisplay();
	}

	public ArrayList<Task> searchEntries(DateTime date) {
		ArrayList<Task> result = bin.findTaskByDate(date);
		bin.setDisplay(result);
		return bin.returnDisplay();
	}

	public ArrayList<Task> markTaskByIndex(ArrayList<Integer> indexArray) {
		for (int index : indexArray) {
			ArrayList<Task> display = bin.returnDisplay();
			Task toMark = new Task();
			toMark = display.get(index - 1);
			bin.markTaskInstance(toMark);
		}
		return bin.returnDisplay();
	}

	public ArrayList<Task> unMarkTaskByIndex(ArrayList<Integer> indexArray) {
		for (int index : indexArray) {
			ArrayList<Task> display = bin.returnDisplay();
			Task toMark = new Task();
			toMark = display.get(index - 1);
			bin.unMarkTaskInstance(toMark);
		}
		return bin.returnDisplay();
	}

	public void changeDirectory() {

	}

	public void editSettings() {

	}

	public ArrayList<Task> startupDisplay() {// display the initial screen
		ArrayList<Task> initDis = bin.displayInit();
		initDis = bin.sortArrayByTime(initDis);
		return initDis;
	}

	public String getStatusBarText(String input) {
		switch (parser.getCommand(input)) {
		case Constants.COMMAND_ADD:
			return parser.getTitle(input) + Constants.FEEDBACK_ADD_SUCCESS;
		case Constants.COMMAND_MARK:
			return bin.undoStack.peek().returnMani().getTitle() + Constants.FEEDBACK_DONE_SUCCESS;
		case Constants.COMMAND_UNMARK:
			return bin.undoStack.peek().returnMani().getTitle() + Constants.FEEDBACK_NOT_DONE_SUCCESS;
		case Constants.COMMAND_DELETE:
			return bin.undoStack.peek().returnMani().getTitle() + Constants.FEEDBACK_DEL_SUCCESS;
		case Constants.COMMAND_SEARCH:
			return Constants.FEEDBACK_SHOW_SUCCESS + parser.getTitle(input);
		case Constants.COMMAND_UNDO:
			return bin.undoStack.isEmpty() ? Constants.FEEDBACK_UNDO_FAILURE : Constants.FEEDBACK_UNDO_SUCCESS;
		case Constants.COMMAND_REDO:
			return bin.redoStack.isEmpty() ? Constants.FEEDBACK_REDO_FAILURE : Constants.FEEDBACK_REDO_SUCCESS;
		case Constants.COMMAND_EDIT:
			return bin.undoStack.peek().returnMani().getTitle() + Constants.FEEDBACK_EDIT_SUCCESS;
		case Constants.COMMAND_SETPATH:
			return Constants.FEEDBACK_SETPATH_SUCCESS + input.split(" ")[1].trim();
		case Constants.COMMAND_ENQUIREPATH:
			return 
		default:
			return Constants.FEEDBACK_INVALID;
		}
	}

	public int getIndex(String input) {
		return parser.getIndex(input);
	}

	public String getCommand(String input) {
		return parser.getCommand(input);
	}
	
	public String parseDate(String input){
		String dateString = new String();
		Pattern dp;
		Matcher dm;

		for (String regex: Constants.dateRegex) {
			try{
				dp = Pattern.compile(regex);
				dm = dp.matcher(input);
				if (dm.find()) {
					dateString = dm.group().trim();
					System.out.println("[DATEPARSER] Date Match: " + dateString);
					break;
				}
			} catch (NullPointerException e) {
			} catch (IllegalArgumentException e) {
			}
		}
		return dateString;
	}

}	