//@@author A0129708
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
		bin.init();
	}

	public ArrayList<Task> inputHandler(String input) throws ParseException, InvalidTimeException {
		String command = CommandParser.getCommand(input);

		if (command.equalsIgnoreCase((Constants.COMMAND_ADD))) {
			return addTask(input);
		} else if (command.equalsIgnoreCase(Constants.COMMAND_VIEW_HOMESCREEN)) {
			return displayHome();
		} else if (command.equalsIgnoreCase(Constants.COMMAND_SETPATH)) {
			Storage.setPath(input.split(" ")[1].trim());
			return displayHome();
		} else if (command.equalsIgnoreCase(Constants.COMMAND_DELETE)) {
			return deleteTaskByIndex(parser.getIndex(input));
		} else if (command.equals(Constants.COMMAND_EDIT)) {
			return editTask(parser.getIndex(input), parser.getField(input), TitleParser.getEditTitle(input));
		} else if (command.equals(Constants.COMMAND_MARK)) {
			return markTaskByIndex(parser.getIndexes(input));
		} else if (command.equals(Constants.COMMAND_UNMARK)) {
			return unMarkTaskByIndex(parser.getIndexes(input));
		} else if (command.equalsIgnoreCase(Constants.COMMAND_SEARCH)) {
			ArrayList<Task> result = searchEntries(parser.getTitle(input));
			return bin.returnDisplay();
		} else if (command.equalsIgnoreCase(Constants.COMMAND_UNDO)) {
			bin.undo();
			return bin.returnDisplay();
		} else if (command.equalsIgnoreCase(Constants.COMMAND_REDO)) {
			bin.redo();
			return bin.returnDisplay();
		} else if (command.equalsIgnoreCase(Constants.COMMAND_ENQUIREPATH)) {
			return bin.returnDisplay();
		} else if (command.equalsIgnoreCase(Constants.COMMAND_SHOW)) {
			
			if (parser.getTitle(input).equals(Constants.STATUS_INCOMPLETE)) {
				return bin.displayUnfinished();
			} else if (parser.getTitle(input).equals(Constants.STATUS_COMPLETE)) {
				return bin.displayFinished();
			}

			if (parser.getDateTime(input).getYear() == 0) {
				return bin.displayAll();
			}

			return searchEntries(parser.getDateTime(input));
		} else if (command.equalsIgnoreCase(Constants.COMMAND_EXIT)) {
			System.exit(0);
		} else if (command.equalsIgnoreCase(Constants.COMMAND_HELP)) {
			return displayHome();
		}

		return null;
	}

	public ArrayList<Task> addTask(String input) throws ParseException, InvalidTimeException {

		String title = parser.getTitle(input);
		Task newTask = new Task();
		if (title.length() != 0) {
			DateTime startTime = parser.getStartDateTime(input);
			DateTime endTime = parser.getEndDateTime(input);
			if (!parser.getRecurValue(input).equals("")) {
				String recurValue = parser.getRecurValue(input);
				DateTime endRecur = new DateTime();
				endRecur = endRecur.plusYears(1);
				newTask = new Task(title, startTime, endTime, endRecur, recurValue);
			} else {
				newTask = new Task(title, startTime, endTime);
			}

			bin.add(newTask);
		}
		ArrayList<Task> newList = bin.returnDisplay();
		return newList;
	}

	public ArrayList<Task> displayHome() {

		return bin.displayHome();
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
		case "start":
			if (toEdit.getType().equals(Constants.TYPE_RECUR)) {
				break;
			}
			bin.editStartingDate(toEdit, parser.getDateTime(info));
			break;
		case "end":
			if (toEdit.getType().equals(Constants.TYPE_RECUR)) {
				break;
			}
			bin.editEndingDate(toEdit, parser.getDateTime(info));
			break;
		case "time":
			if (toEdit.getType().equals(Constants.TYPE_RECUR)) {
				break;
			}
			System.out.println("The new starting time is: " + parser.getStartDateTime(info).toString() + ".  "
					+ "The ending Time is " + parser.getEndDateTime(info).toString() + " . ");
			bin.editTimeField(toEdit, parser.getStartDateTime(info), parser.getEndDateTime(info));
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
		return bin.returnDisplay();
	}

	public ArrayList<Task> deleteTaskByIndex(int index) {
		ArrayList<Task> display = bin.returnDisplay();
		Task toDel = new Task();
		toDel = display.get(index - 1);
		bin.delete(toDel);
		return bin.returnDisplay();
	}

	public void showOverdue() {
	}

	public ArrayList<Task> searchEntries(String keyWord) {
		ArrayList<Task> result = bin.findTaskByTitle(keyWord);
		return bin.returnDisplay();
	}

	public ArrayList<Task> searchEntries(DateTime date) {
		ArrayList<Task> result = bin.findTaskByDate(date);
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
			bin.unmarkTaskInstance(toMark);
		}
		return bin.returnDisplay();
	}

	public void changeDirectory() {

	}

	public void editSettings() {

	}
	
	public void clear(){
		bin.clear();
	}

	public ArrayList<Task> startupDisplay() {// display the initial screen
		ArrayList<Task> initDis = bin.displayHome();
		return initDis;
	}

	// @@author A0121442X
	public String getStatusBarText(String input) {
		switch (parser.getCommand(input)) {
		case Constants.COMMAND_ADD:

			if ((!(parser.getStartDateTime(input).getYear() == 0)) && (!(parser.getEndDateTime(input).getYear() == 0))
					&& (!parser.getRecurValue(input).equals(Constants.TYPE_RECUR))) {
				DateTime[] time = new DateTime[2];
				time[0] = parser.getStartDateTime(input);
				time[1] = parser.getEndDateTime(input);
				if (bin.isClashed(time)) {
					return parser.getTitle(input) + " has a time clash with existing events";
				}
			}

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
			if (input.split(" ")[1].trim().length() > Constants.MAX_PATH_LENGTH) {
				return Constants.FEEDBACK_SETPATH_LONGFILE;
			} else if (Storage.setPath(input.split(" ")[1].trim())) {
				return Constants.FEEDBACK_SETPATH_SUCCESS + input.split(Constants.SPACE)[1].trim();
			} else {
				return input.split(Constants.SPACE)[1].trim() + Constants.FEEDBACK_SETPATH_FAILURE;
			}
		case Constants.COMMAND_ENQUIREPATH:
			return Storage.enquirePath();
		case Constants.COMMAND_HELP:
			return Constants.FEEDBACK_VIEW_HELP;
		case Constants.COMMAND_SHOW:
			if (parser.getDateTime(input).getYear() == 0
					&& !parser.getTitle(input).equalsIgnoreCase(Constants.STATUS_COMPLETE)
					&& !parser.getTitle(input).equalsIgnoreCase(Constants.STATUS_INCOMPLETE)) {
				return Constants.FEEDBACK_SHOW_ALL_SUCCESS;
			} else if (parser.getTitle(input).equalsIgnoreCase(Constants.STATUS_COMPLETE)) {
				return Constants.FEEDBACK_SHOW_COMPLETED_SUCCESS;
			} else if (parser.getTitle(input).equalsIgnoreCase(Constants.STATUS_INCOMPLETE)) {
				return Constants.FEEDBACK_SHOW_INCOMPLETE_SUCCESS;
			} else {
				return Constants.FEEDBACK_SHOW_DATE_SUCCESS + parser.getDateTime(input).getDayOfMonth() + "/"
						+ parser.getDateTime(input).getMonthOfYear();
			}
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
}