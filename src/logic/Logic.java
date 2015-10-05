package logic;

<<<<<<< HEAD
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import logic.TaskBin;
=======
import java.text.ParseException;
import java.util.ArrayList;
import org.joda.time.DateTime;

import parser.CommandParser;
<<<<<<< HEAD
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
=======
import parser.DateParser;
>>>>>>> master
import parser.Parser;
import parser.TaskParser;
import parser.TimeParser;
import storage.Storage;

public class Logic {

	Parser myParser = new Parser();
	TaskBin bin = new TaskBin();

	public Logic() {
		bin.init();
<<<<<<< HEAD
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
	
	
	
=======
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
		DateTime startingDate = DateParser.getStartDate(input);
		DateTime endingDate = DateParser.getEndDate(input);
		DateTime startTime = TimeParser.getStartTime(input);
		DateTime endTime = TimeParser.getEndTime(input);
		
		String title = TaskParser.getTitle(input);
		
		Task newTask = new Task(title, startingDate, startTime, endingDate , endTime);
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
		DateTime endingDate = DateParser.getEndDate(input);
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

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	public void changeDirectory(){

	}
	public void editSettings(){

	}
<<<<<<< HEAD
	
=======

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	public ArrayList<Task> startupDisplay(){//display the initial screen
		ArrayList <Task> initDis = bin.displayInit();
		//initDis = bin.sortArrayByTime(initDis);
		return initDis;
	}
<<<<<<< HEAD
	
=======
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
}