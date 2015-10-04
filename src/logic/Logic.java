package logic;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import org.joda.time.DateTime;

import parser.CommandParser;
import parser.Parser;
import parser.TaskParser;
import storage.Storage;

public class Logic {

	Parser myParser = new Parser();
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
			int index = Parser.getIndex(input);
			return deleteTaskByIndex(index);

		}
		else if(command.equals("edit")){
			int index = Parser.getIndex(input);

		}
		else if (command.equals("mark")){
			int index = Parser.getIndex(input);
			System.out.println(index - index);
			return markTaskByIndex(index-1);
			//return bin.returnDisplay();
		}
		else if (command.equals("unmark")){
			int index = Parser.getIndex(input);
			System.out.println(index - index);
			return unMarkTaskByIndex(index-1);
		}
		else if(command.equals("display")){
		}
		else if(command.equals("search")){
			System.out.println("DEBUG " + TaskParser.getTitle(input));
			ArrayList<Task> result = searchEntries(TaskParser.getTitle(input));

			return result;
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
		DateTime startingDate = new DateTime();
		DateTime endingDate = new DateTime();
		String title = TaskParser.getTitle(input);
		/*
		String staDateStr = myParser.getStartDate(input);
		String endDateStr = myParser.getEndDate(input);
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		startingDate.setTime(dateFormat.parse(staDateStr));
		endingDate.setTime(dateFormat.parse(endDateStr));
		 */
		Task newTask = new Task(title, startingDate, endingDate);
		bin.add(newTask);
		bin.setDisplay();
		return bin.returnDisplay();

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
		return display;
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
		String title = TaskParser.getTitle(input);
		//		String dateStr = TaskParser.getEndDate(input);
		//		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date ending = TaskParser.getEndDate(input);
		DateTime endingDate = new DateTime(ending);
		if(ending == null){
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
		//display.remove(index - 1);
		//bin.setDisplay(display);
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
		System.out.print(bin.markTaskInstance(toMark));
		System.out.println(bin.markTaskInstance(toMark));
		bin.setDisplay();
		return bin.returnDisplay();
	}
	public ArrayList<Task> unMarkTaskByIndex(int index){
		ArrayList<Task> display = bin.returnDisplay();
		Task toMark = new Task();
		toMark = display.get(index);
		System.out.print(bin.unMarkTaskInstance(toMark));
		bin.setDisplay();
		return bin.returnDisplay();
	}

	public void changeDirectory(){

	}
	public void editSettings(){

	}

	public ArrayList<Task> startupDisplay(){//display the initial screen
		ArrayList <Task> initDis = bin.displayInit();
		//initDis = bin.sortArrayByTime(initDis);
		return initDis;
	}
}