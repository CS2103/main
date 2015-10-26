package logic;

import java.text.ParseException;
import java.util.ArrayList;

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
		this.bin.init();
	}

	public ArrayList<Task> inputHandler(String input) throws ParseException, InvalidTimeException {
		assert (input.length() > 0) : "input is null";

		String command = CommandParser.getCommand(input);
		if (command.equalsIgnoreCase((Constants.COMMAND_ADD))) {
			this.addTask(input);
			return this.bin.returnDisplay();
		} else if (command.equalsIgnoreCase("home")) {
			return this.displayHome();
		} else if (command.equalsIgnoreCase(Constants.COMMAND_SETPATH)) {
			Storage.setPath(input.split(" ")[1].trim());
			return this.displayHome();
		} else if (command.equalsIgnoreCase(Constants.COMMAND_DELETE)) {
			return this.deleteTaskByIndex(this.parser.getIndex(input));
		} else if (command.equals(Constants.COMMAND_EDIT)) {
			return this.editTask(this.parser.getIndex(input), this.parser.getField(input),
					TitleParser.getEditTitle(input));
		} else if (command.equals(Constants.COMMAND_MARK)) {
			return this.markTaskByIndex(this.parser.getIndexes(input));
		} else if (command.equals(Constants.COMMAND_UNMARK)) {
			return this.unMarkTaskByIndex(this.parser.getIndexes(input));
		} else if (command.equalsIgnoreCase("display")) {

		} else if (command.equalsIgnoreCase("recur")) {
			DateTime endTime = new DateTime(2015, 11, 25, 0, 0);
			this.addRecurTask(input, endTime);
		} else if (command.equalsIgnoreCase(Constants.COMMAND_SEARCH)) {
			System.out.println("DEBUG " + this.parser.getTitle(input));
			ArrayList<Task> result = this.searchEntries(this.parser.getTitle(input));
			this.bin.setDisplay(result);
			return this.bin.returnDisplay();
		} else if (command.equalsIgnoreCase(Constants.COMMAND_UNDO)) {
			this.bin.undo();
			return this.bin.returnDisplay();
		} else if (command.equalsIgnoreCase(Constants.COMMAND_REDO)) {
			this.bin.redo();
			return this.bin.returnDisplay();
		}
		return null;
	}

	public ArrayList<Task> addTask(String input) throws ParseException, InvalidTimeException {
		String title = this.parser.getTitle(input);
		if (title.length() != 0) {
			DateTime startTime = this.parser.getStartDateTime(input);
			DateTime endTime = this.parser.getEndDateTime(input);
			System.out.println("Title: " + title);
			System.out.println("StartTime: " + startTime);
			System.out.println("EndTime: " + endTime);

			Task newTask = new Task(title, startTime, endTime);
			this.bin.add(newTask);
			this.bin.setDisplay();
		}
		return this.bin.returnDisplay();
	}

	public ArrayList<Task> addRecurTask(String input, DateTime endDate) throws InvalidTimeException {
		return this.startupDisplay();
	}

	public ArrayList<Task> displayHome() {
		this.bin.setDisplay();
		return this.bin.returnDisplay();
	}

	public ArrayList<Task> displayCurrent() {
		return this.bin.returnDisplay();
	}

	public ArrayList<Task> editTask(int index, String field, String info) {
		Task toEdit = this.bin.returnDisplay().get(index - 1);
		switch (field) {
		case Constants.PARAMETER_TITLE:
			this.bin.editTitle(toEdit, info);
			break;
		case "description":
			this.bin.editDescription(toEdit, info);
			break;
		case Constants.PARAMETER_START:
			this.bin.editStartingDate(toEdit, this.parser.getDateTime(info));
			break;
		case Constants.PARAMETER_END:
			this.bin.editEndingDate(toEdit, this.parser.getDateTime(info));
			break;
		}
		return this.bin.returnDisplay();
	}

	public ArrayList<Task> undoChange() {
		this.bin.undo();
		return this.bin.returnDisplay();
	}

	public ArrayList<Task> redoChange() {
		this.bin.redo();
		return this.bin.returnDisplay();
	}

	public ArrayList<Task> deleteTaskByName(String input) throws ParseException {
		String title = this.parser.getTitle(input);
		DateTime endingDate = this.parser.getEndDateTime(input);
		if (endingDate == null) {
			endingDate = DateTime.now();
		}
		ArrayList<Task> result = this.bin.findTaskByTitle(title);
		if (result.size() > 1) {
			result = this.bin.findTaskByDate(result, endingDate);
		}
		this.bin.delete(result.get(0));
		this.bin.setDisplay();
		return this.bin.returnDisplay();
	}

	public ArrayList<Task> deleteTaskByIndex(int index) {
		ArrayList<Task> display = this.bin.returnDisplay();
		Task toDel = new Task();
		toDel = display.get(index - 1);
		this.bin.delete(toDel);
		this.bin.setDisplay();
		return this.bin.returnDisplay();
	}

	public void showOverdue() {
	}

	public ArrayList<Task> searchEntries(String keyWord) {
		ArrayList<Task> result = this.bin.findTaskByTitle(keyWord);
		this.bin.setDisplay(result);
		return this.bin.returnDisplay();
	}

	public ArrayList<Task> searchEntries(DateTime date) {
		ArrayList<Task> result = this.bin.findTaskByDate(date);
		this.bin.setDisplay(result);
		return this.bin.returnDisplay();
	}

	public ArrayList<Task> markTaskByIndex(ArrayList<Integer> indexArray) {
		for (int index : indexArray) {
			ArrayList<Task> display = this.bin.returnDisplay();
			Task toMark = new Task();
			toMark = display.get(index - 1);
			this.bin.markTaskInstance(toMark);
		}
		return this.bin.returnDisplay();
	}

	public ArrayList<Task> unMarkTaskByIndex(ArrayList<Integer> indexArray) {
		for (int index : indexArray) {
			ArrayList<Task> display = this.bin.returnDisplay();
			Task toMark = new Task();
			toMark = display.get(index - 1);
			this.bin.unMarkTaskInstance(toMark);
		}
		return this.bin.returnDisplay();
	}

	public void changeDirectory() {

	}

	public void editSettings() {

	}

	public ArrayList<Task> startupDisplay() {// display the initial screen
		ArrayList<Task> initDis = this.bin.displayInit();
		initDis = this.bin.sortArrayByTime(initDis);
		return initDis;
	}

	public String getStatusBarText(String input) {
		switch (this.parser.getCommand(input)) {
		case Constants.COMMAND_ADD:
			return this.parser.getTitle(input) + Constants.FEEDBACK_ADD_SUCCESS;
		case Constants.COMMAND_MARK:
			return this.bin.undoStack.peek().returnMani().getTitle() + Constants.FEEDBACK_DONE_SUCCESS;
		case Constants.COMMAND_UNMARK:
			return this.bin.undoStack.peek().returnMani().getTitle() + Constants.FEEDBACK_NOT_DONE_SUCCESS;
		case Constants.COMMAND_DELETE:
			return this.bin.undoStack.peek().returnMani().getTitle() + Constants.FEEDBACK_DEL_SUCCESS;
		case Constants.COMMAND_SEARCH:
			return Constants.FEEDBACK_SHOW_SUCCESS + this.parser.getTitle(input);
		case Constants.COMMAND_UNDO:
			return this.bin.redoStack.isEmpty() ? Constants.FEEDBACK_UNDO_FAILURE : Constants.FEEDBACK_UNDO_SUCCESS;
		case Constants.COMMAND_REDO:
			return this.bin.undoStack.isEmpty() ? Constants.FEEDBACK_REDO_FAILURE : Constants.FEEDBACK_REDO_SUCCESS;
		case Constants.COMMAND_EDIT:
			return this.bin.undoStack.peek().returnMani().getTitle() + Constants.FEEDBACK_EDIT_SUCCESS;
		case Constants.COMMAND_SETPATH:
			return Constants.FEEDBACK_SETPATH_SUCCESS + input.split(" ")[1].trim();
		default:
			return Constants.FEEDBACK_INVALID;
		}
	}

	public int getIndex(String input) {
		return this.parser.getIndex(input);
	}

	public String getCommand(String input) {
		return this.parser.getCommand(input);
	}
}