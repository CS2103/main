package logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import logic.TaskBin;
import parser.Parser;

public class Logic {

	Parser myParser = new Parser();
	TaskBin bin = new TaskBin();

	public Logic() {
		bin.init();
	}
	
	public ArrayList<Task> inputHandler(String input) throws ParseException{
		String command = myParser.getCommandName(input);
		if(command.equals("add")){
			addTask(input);
			return bin.returnDisplay();
		}
		else if(command.equals("home")){
			return displayHome();
		}
		/*else if(command.equals("delete")){
			int index = myParser.getIndex();
			deleteTaskByIndex(index);
		}
		else if(command.equals("edit")){
			int index = myParser.getIndex();
			
		}
		else if(command.equals("display")){
		}*/
		else if(command.equals("search")){
			ArrayList<Task> result = searchEntries(myParser.getDescription(input));
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
		Calendar startingDate = Calendar.getInstance();
		Calendar endingDate = Calendar.getInstance();
		String title = myParser.getDescription(input);
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
		}
		return bin.returnDisplay();
	}
	
	public ArrayList<Task> editTask(int index, String field, Calendar date){
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
	
	public ArrayList<Task> undoChange(){
		bin.undo();
		return bin.returnDisplay();
	}
	
	public ArrayList<Task> redoChange(){
		bin.redo();
		return bin.returnDisplay();
	}
	 
	public ArrayList<Task> deleteTaskByName(String input) throws ParseException{
		String title = myParser.getDescription(input);
		String dateStr = myParser.getEndDate(input);
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date ending = dateFormat.parse(dateStr);
		Calendar endingDate = Calendar.getInstance();
		endingDate.setTime(ending);
		if(ending == null){
			endingDate = Calendar.getInstance();
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
		Task toDel = display.get(index - 1);
		bin.delete(toDel);
		display.remove(index - 1);
		bin.setDisplay(display);
		return bin.returnDisplay();
	}
	
	public void showOverdue() {
	}
	
	public ArrayList<Task> searchEntries(String keyWord){
		ArrayList<Task> result = bin.findTaskByTitle(keyWord);
		bin.setDisplay(result);
		return bin.returnDisplay();
	}
	
	public ArrayList<Task> searchEntries(Calendar date){
		ArrayList<Task> result = bin.findTaskByDate(date);
		bin.setDisplay(result);
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