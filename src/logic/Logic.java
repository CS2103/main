package logic;

import java.text.ParseException;
import java.util.ArrayList;

import org.joda.time.DateTime;

import application.Constants;
import parser.CommandParser;
import parser.Parser;
import storage.Storage;

public class Logic {

	Parser parser = new Parser();
	TaskBin bin = new TaskBin();

	public Logic() {
		bin.init();
	}

	public ArrayList<Task> inputHandler(String input) throws ParseException{
		String command = CommandParser.getCommand(input);
		if(command.equals("add")){
			addTask(input);
			return bin.returnDisplay();
		}
		else if(command.equals("home")){
			return displayHome();
		}
		else if (command.equals("setpath")){
			Storage.setPath(input.split(" ")[1].trim());
			return displayHome();
		}
		else if(command.equals("delete")){
			return deleteTaskByIndex(parser.getIndex(input));
		}
		else if(command.equals("edit")){
			int index = parser.getIndex(input);
			return editTask(index, parser.getField(input), Parser.getEditTitle(input));
		}
		else if (command.equals("mark")){
			int index = parser.getIndex(input);
			System.out.println(index - index);
			return markTaskByIndex(index-1);
		}
		else if (command.equals("unmark")){
			int index = parser.getIndex(input);
			System.out.println(index - index);
			return unMarkTaskByIndex(index-1);
		}
		else if(command.equals("display")){

		}
		else if(command.equals("recur")){
			DateTime endTime = new DateTime(2015, 11, 25, 0, 0);
			addRecurTask(input, endTime);
		}

		else if(command.equals("search")){
			System.out.println("DEBUG " + parser.getTitle(input));
			ArrayList<Task> result = searchEntries(parser.getTitle(input));
			bin.setDisplay(result);

			return bin.returnDisplay();
		}
		else if(command.equals("undo")){
			bin.undo();
			return bin.returnDisplay();
		}
		else if(command.equals("redo")){
			bin.redo();
			return bin.returnDisplay();
		}

		return null;
	}

	public ArrayList<Task> addTask(String input) throws ParseException{
		String title = parser.getTitle(input);
		System.out.println("Title: " +title);
		DateTime startTime = parser.getStartTime(input);
		DateTime endTime = parser.getEndTime(input);
		System.out.println(startTime);
		System.out.println(endTime);

		Task newTask = new Task(title, startTime, endTime);
		bin.add(newTask);
		bin.setDisplay();
		return bin.returnDisplay();
	}

	public ArrayList<Task> addRecurTask(String input, DateTime endDate){
		Task newTask = new Task(parser.getTitle(input), parser.getStartTime(input), parser.getEndTime(input));
		//switch(TaskParser.getPeriod()){
		//case "weekly":
		bin.addWeeklyTask(newTask, endDate);
		//break;
		//}
		return startupDisplay();
	}
	public ArrayList<Task> displayHome(){
		bin.setDisplay();
		return bin.returnDisplay();
	}

	public ArrayList<Task> editTask(int index, String field, String info){
		ArrayList<Task> display = bin.returnDisplay();
		Task toEdit = display.get(index - 1);
		switch(field){
		case "title":
			bin.editTitle(toEdit, info);
			break;
		case "description":
			bin.editDescription(toEdit, info);
			break;
		case "start":
			break;
		case "end":
			break;
		}
		return bin.returnDisplay();
	}

	public ArrayList<Task> editTask(int index, String field, DateTime date){
		ArrayList<Task> display = bin.returnDisplay();
		Task toEdit = display.get(index - 1);
		switch(field){
		case "starting date":
			bin.editStartingDate(toEdit, date);
			break;
		case "ending date":
			bin.editEndingDate(toEdit, date);
			break;
		}
		return bin.returnDisplay();
	}

	public ArrayList<Task> undoChange(){
		bin.undo();
		return bin.returnDisplay();
	}

	public ArrayList<Task> redoChange(){
		bin.redo();
		return bin.returnDisplay();
	}

	public ArrayList<Task> deleteTaskByName(String input) throws ParseException{
		String title = parser.getTitle(input);
		DateTime endingDate = parser.getEndTime(input);
		if(endingDate == null){
			endingDate = DateTime.now();
		}
		ArrayList<Task> result = bin.findTaskByTitle(title);
		if(result.size() > 1){
			result = bin.findTaskByDate(result, endingDate);
		}
		bin.delete(result.get(0));
		bin.setDisplay();
		return bin.returnDisplay();
	}

	public ArrayList<Task> deleteTaskByIndex(int index){
		ArrayList<Task> display = bin.returnDisplay();
		Task toDel = new Task();
		toDel = display.get(index - 1);
		bin.delete(toDel);
		bin.setDisplay();
		return bin.returnDisplay();
	}

	public void showOverdue() {
	}

	public ArrayList<Task> searchEntries(String keyWord){
		ArrayList<Task> result = bin.findTaskByTitle(keyWord);
		bin.setDisplay(result);
		return bin.returnDisplay();
	}

	public ArrayList<Task> searchEntries(DateTime date){
		ArrayList<Task> result = bin.findTaskByDate(date);
		bin.setDisplay(result);
		return bin.returnDisplay();
	}
	public ArrayList<Task> markTaskByIndex(int index){
		ArrayList<Task> display = bin.returnDisplay();
		Task toMark = new Task();
		toMark = display.get(index);
		bin.markTaskInstance(toMark);
		return bin.returnDisplay();
	}
	public ArrayList<Task> unMarkTaskByIndex(int index){
		ArrayList<Task> display = bin.returnDisplay();
		Task toMark = new Task();
		toMark = display.get(index);
		bin.unMarkTaskInstance(toMark);
		return bin.returnDisplay();
	}

	public void changeDirectory(){

	}
	public void editSettings(){

	}



	public ArrayList<Task> startupDisplay(){//display the initial screen
		ArrayList <Task> initDis = bin.displayInit();
		initDis = bin.sortArrayByTime(initDis);
		return initDis;
	}

	public String getStatusBarText(String input){
		switch(CommandParser.getCommand(input)) {
		case Constants.COMMAND_ADD :
			return parser.getTitle(input) + Constants.FEEDBACK_ADD_SUCCESS;
		case Constants.COMMAND_MARK :
			return bin.undoStack.peek().returnMani().getTitle() + Constants.FEEDBACK_DONE_SUCCESS;
		case Constants.COMMAND_UNMARK :
			return bin.undoStack.peek().returnMani().getTitle() + Constants.FEEDBACK_NOT_DONE_SUCCESS;
		case Constants.COMMAND_DELETE :
			return bin.undoStack.peek().returnMani().getTitle() + Constants.FEEDBACK_DEL_SUCCESS;
		case Constants.COMMAND_SEARCH :
			return Constants.FEEDBACK_SHOW_SUCCESS + parser.getTitle(input);
		case Constants.COMMAND_UNDO :
			//return bin.redoStack.peek() != null ? Constants.FEEDBACK_UNDO_SUCCESS : Constants.FEEDBACK_UNDO_FAILURE;
		case Constants.COMMAND_REDO :
			//return bin.redoStack.peek() != null ? Constants.FEEDBACK_REDO_SUCCESS : Constants.FEEDBACK_REDO_FAILURE;
		case Constants.COMMAND_EDIT :
			return Constants.FEEDBACK_EDIT_SUCCESS;
		default :
			return Constants.FEEDBACK_INVALID;
		}
		//bin.undoStack.peek().returnMani().getTitle()
	}
	public int getIndex(String input) {
		return parser.getIndex(input);
	}
	public String getCommand(String input) {
		return parser.getCommand(input);
	}
}