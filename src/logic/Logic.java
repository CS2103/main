/**
 * This class:
 * Will be called by the GUIService class to perform required operations:
 * Task addTask(String, String);
 * Todo addTodo(String, String, String);
 * Event addEvent(String)
 *
 * Task deleteTask(String);
 * Todo deleteTodo(String);
 * Event deleteEvent(String);
 *
 * boolean markTask(String, boolean);
 * boolean markTodo(String, boolean);
 * boolean markEvent(String, boolean);
 */

package logic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import logic.TaskBin;
import parser.Parser;

public class Logic {

	Parser myParser = new Parser();
	TaskBin bin = new TaskBin();

	public Logic() {
		bin.init();
	}
	
	

	public void addTask(String input) throws ParseException{
		String title = myParser.getDescription(input);
		String staDateStr = myParser.getStartDate(input);
		String endDateStr = myParser.getEndDate(input);
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date startingDate = dateFormat.parse(staDateStr);
		Date endingDate = dateFormat.parse(endDateStr);
		Task newTask = new Task(title, startingDate, endingDate);
		bin.add(newTask);
	}
	
	public void editTask(String input){
		
	}
	public void deleteTask(String input) throws ParseException{
		String title = myParser.getDescription(input);
		String dateStr = myParser.getEndDate(input);
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		Date endingDate = dateFormat.parse(dateStr);
		if(endingDate == null){
			endingDate = new Date();
		}
		ArrayList<Task> result = bin.findTaskByTitle(title);
		if(result.size() > 1){
			result = bin.findTaskByDate(result, endingDate);
		}
		bin.delete(result.get(0));
	}
	public void showOverdue() {
		
	}
	public ArrayList<Task> searchEntries(String keyWord){
		ArrayList<Task> result = bin.findTaskByTitle(keyWord);
		return result;
	}
	
	public void changeDirectory(){

	}
	public void editSettings(){

	}
}