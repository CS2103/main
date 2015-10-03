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
	
	

	public ArrayList<Task> addTask(String input) throws ParseException{
		Calendar startingDate = Calendar.getInstance();
		Calendar endingDate = Calendar.getInstance();
		String title = myParser.getDescription(input);
		String staDateStr = myParser.getStartDate(input);
		String endDateStr = myParser.getEndDate(input);
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		startingDate.setTime(dateFormat.parse(staDateStr));
		endingDate.setTime(dateFormat.parse(endDateStr));
		Task newTask = new Task(title, startingDate, endingDate);
		bin.add(newTask);
		bin.setDisplay();
		return bin.returnDisplay();
		
	}
	
	public void editTask(String input){
		
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
	
	public void changeDirectory(){

	}
	public void editSettings(){

	}
	
	public ArrayList<Task> startupDisplay(){//display the initial screen
		ArrayList <Task> initDis = bin.displayInit();
		initDis = bin.sortArrayByTime(initDis);
		return initDis;
	}
	
}