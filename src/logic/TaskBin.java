package logic;

import java.util.ArrayList;
import java.util.Stack;

import org.joda.time.DateTime;

import application.Constants;
import storage.Storage;

public class TaskBin {
	ArrayList<Task> taskList;
	ArrayList<Task> displayList;
	BinDisplay display = new BinDisplay();
	BinSorter sorter = new BinSorter();
	Storage taskStorage;
	Stack<Command> undoStack;
	Stack<Command> redoStack;

	public TaskBin(ArrayList<Task> taskList) {
		displayList = new ArrayList<Task>();
		undoStack = new Stack<Command>();
		redoStack = new Stack<Command>();
		this.taskList = taskList;
	}

	public TaskBin() {
		displayList = new ArrayList<Task>();
		undoStack = new Stack<Command>();
		redoStack = new Stack<Command>();
		taskList = new ArrayList<Task>();
	}

	/******************************************* initialization *************************************/
	public void init() {
		taskList = Storage.read();
		display.setDisplay(taskList);
		displayList = display.returnDisplay();
	}

	public ArrayList<Task> returnDisplay() {
		displayList = display.returnDisplay();
		return displayList;
	}

	public ArrayList<Task> displayHome() {
		display.setDisplay(taskList);
		displayList = display.returnDisplay();
		return displayList;
	}
	
	public ArrayList<Task> displayUnfinished(){
		ArrayList<Task> results = new ArrayList<Task>();
		for(Task t:taskList){
			if(t.getStatus() == false){
				results.add(t);
			}
		}
		display.setDisplayAll(results);
		displayList = display.returnDisplay();
		return results;
	}
	
	public ArrayList<Task> displayFinished(){
		ArrayList<Task> results = new ArrayList<Task>();
		for(Task t:taskList){
			if((t.getStatus() == true)&&(t.isTypeRecur() == false)){
				results.add(t);
			}
		}
		display.setDisplayAll(results);
		displayList = display.returnDisplay();
		return results;
	}
	
	public ArrayList<Task> displayAll(){
		display.setDisplayAll(taskList);
		displayList = display.returnDisplay();
		return displayList;
	}
	
	public void add(Task newTask) {
		Command add = new Command(Constants.add_tag, newTask);
		this.undoStack.push(add);
		this.taskList.add(newTask);
		taskList = sorter.sortArrayByTime(taskList);
		display.setDisplay(taskList);
		displayList = display.returnDisplay();
		Storage.write(taskList);
		redoStack.clear();
	}

	public void delete(Task task) {
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).equals(task)) {
				Command delete = new Command(Constants.delete_tag, taskList.get(i));
				undoStack.push(delete);
				taskList.remove(i);
				Storage.write(taskList);
				display.setDisplay(taskList);
				displayList = display.returnDisplay();
				break;
			}
		}

		redoStack.clear();
	}

	public void delete(Task task, DateTime date) {
		if (!task.getType().equals(Constants.TYPE_RECUR)) {
			return;
		}
		for (int i = 0; i < taskList.size(); i++) {
			if (taskList.get(i).equals(task)) {
				taskList.get(i).deleteRecur(date);
			}
		}
	}

	public ArrayList<Task> markTaskInstance(Task task) {

		for (Task obj : taskList) {
			boolean isFoundRecur = false;
			if(obj.isTypeRecur()){
				for(DateTime t:obj.getRecurDates()){
					if(t.getDayOfYear() == DateTime.now().getDayOfYear()){
						isFoundRecur = true;
						break;
					}
				}
				if(!isFoundRecur){
					return null;
				}
			}
			if (obj.equals(task)) {
				obj.mark();
				Command mark = new Command(Constants.mark_tag, obj);
				undoStack.push(mark);
				display.updateDisplay(mark, true);
			}
		}
		Storage.write(taskList);
		displayList = display.returnDisplay();
		return displayList;

	}

	public ArrayList<Task> unmarkTaskInstance(Task task) {
		for (Task obj : taskList) {
			if (obj.equals(task)) {
				obj.unMark();
				Command unmark = new Command(Constants.unmark_tag, obj);
				undoStack.push(unmark);
				display.updateDisplay(unmark, true);
			}
		}

		Storage.write(taskList);
		displayList = display.returnDisplay();
		return displayList;

	}

	public void undo() {
		if (this.undoStack.isEmpty()) {
			return;
		}
		Command previousComm = this.undoStack.pop();
		String command = previousComm.returnCommand();
		switch (command) {
		case Constants.add_tag:
			redoStack.push(previousComm);
			taskList.remove(previousComm.returnMani());
			display.setDisplay(taskList);
			displayList = display.returnDisplay();
			break;
		case Constants.delete_tag:
			redoStack.push(previousComm);
			taskList.add(previousComm.returnMani());
			display.setDisplay(taskList);
			displayList = display.returnDisplay();
			break;
		case Constants.replace_tag:
			Command add = undoStack.pop();
			taskList.remove(add.returnMani());
			redoStack.push(add);
			Command del = undoStack.pop();
			taskList.add(del.returnMani());
			redoStack.push(del);
			redoStack.push(previousComm);
			display.setDisplay(taskList);
			displayList = display.returnDisplay();
			break;

		case Constants.alter_tag:
			
			redoStack.push(previousComm);
			displayList = display.returnDisplay();
			taskList.remove(previousComm.returnMani());
			taskList.add(previousComm.returnOrigin());
			if(!displayList.equals(taskList)){
				displayList.remove(displayList.get(displayList.indexOf(previousComm.returnMani())));
				displayList.add(previousComm.returnOrigin());
			}
			display.setDisplayAll(displayList);
			break;

		case Constants.mark_tag:
			redoStack.push(previousComm);
			taskList.get(taskList.indexOf(previousComm.returnMani())).unMark();
			display.updateDisplay(previousComm, false);
			break;

		case Constants.unmark_tag:
			redoStack.push(previousComm);
			taskList.get(taskList.indexOf(previousComm.returnMani())).mark();
			display.updateDisplay(previousComm, false);
			break;

		default:
			System.out.println("Error: Unable to identify the command type");
		}
		Storage.write(taskList);
	}

	public void redo() {
		if (this.redoStack.isEmpty()) {
			return;
		}
		Command redoComm = redoStack.pop();
		String command = redoComm.returnCommand();
		switch (command) {
		case Constants.add_tag:
			undoStack.push(redoComm);
			taskList.add(redoComm.returnMani());
			display.setDisplay(taskList);
			displayList = display.returnDisplay();
			break;
		case Constants.delete_tag:
			undoStack.push(redoComm);
			taskList.remove(redoComm.returnMani());
			display.setDisplay(taskList);
			displayList = display.returnDisplay();
			break;
		case Constants.alter_tag:
			displayList = display.returnDisplay();
			undoStack.push(redoComm);
			taskList.remove(taskList.get(taskList.indexOf(redoComm.returnOrigin())));
			taskList.add(redoComm.returnMani());
			
			if(!displayList.equals(taskList)){
				displayList.remove(displayList.get(displayList.indexOf(redoComm.returnOrigin())));
				displayList.add(redoComm.returnMani());
				
			}
			display.setDisplayAll(displayList);
			break;
			
		case Constants.mark_tag:
			undoStack.push(redoComm);
			taskList.get(taskList.indexOf(redoComm.returnMani())).mark();
			display.updateDisplay(redoComm, true);
			displayList = display.returnDisplay();
			break;

		case Constants.unmark_tag:
			undoStack.push(redoComm);
			taskList.get(taskList.indexOf(redoComm.returnMani())).unMark();
			display.updateDisplay(redoComm, true);
			displayList = display.returnDisplay();
			break;

		default:
			System.out.println("Error: Unable to identify the command type");

		}

	}

	public ArrayList<Task> findTaskByTitle(ArrayList<Task> list, String title) {
		ArrayList<Task> result = new ArrayList<Task>();
		String[] keywords = title.split(" ");
		for (Task task : list) {
			String[] listOfWord = task.getTitle().split(" ");
			if (sorter.includeAllWords(keywords, listOfWord)) {
				result.add(task);
			}
		}
		sorter.sortArrayByTime(result);
		display.setDisplayAll(result);
		return result;
	}

	public ArrayList<Task> findTaskByTitle(String title) {
		ArrayList<Task> result = new ArrayList<Task>();
		String[] keywords = title.split(" ");
		for (Task task : taskList) {
			String[] listOfWord = task.getTitle().split(" ");
			if (sorter.includeAllWords(keywords, listOfWord)) {
				result.add(task);
			}
		}
		sorter.sortArrayByTime(result);
		display.setDisplayAll(result);
		return result;

	}

	public ArrayList<Task> findTaskByDate(ArrayList<Task> list, DateTime date) {
		ArrayList<Task> result = new ArrayList<Task>();
		for (Task task : list) {
			if (task.getType().equals(Constants.TYPE_RECUR)) {
				for (DateTime t : task.getRecurDates()) {
					if (t.getDayOfYear() == date.getDayOfYear()) {
						result.add(task);
					}
				}
			} else if (!task.getType().equals(Constants.TYPE_FLOATING)) {
				if (date.getDayOfYear() == task.getEndingTime().getDayOfYear()) {
					result.add(task);
				}
			}
		}
		sorter.sortArrayByTime(result);
		display.setDisplayAll(result);
		return result;
	}

	public ArrayList<Task> findTaskByDate(DateTime date) {
		ArrayList<Task> result = new ArrayList<Task>();
		for (Task task : taskList) {
			if (task.getType().equals(Constants.TYPE_RECUR)) {
				for (DateTime t : task.getRecurDates()) {
					if (t.getDayOfYear() == date.getDayOfYear()) {
						result.add(task);
					}
				}
			} else if (!task.getType().equals(Constants.TYPE_FLOATING)) {
				if (date.getDayOfYear() == task.getEndingTime().getDayOfYear()) {
					result.add(task);
				}
			}
		}
		sorter.sortArrayByTime(result);
		display.setDisplayAll(result);
		return result;
	}

	public void editTitle(Task task, String newTitle) {
				
		Task buffer = new Task(task);
		Task tar = new Task (taskList.get(taskList.indexOf(task)));
		Task tarDis = new Task (displayList.get(displayList.indexOf(tar)));
		taskList.remove(taskList.get(taskList.indexOf(task)));
		displayList.remove(displayList.get(displayList.indexOf(task)));
		tarDis.setTitle(newTitle);
		tar.setTitle(newTitle);
		taskList.add(tar);
		displayList.add(tarDis);
		Command editTil = new Command(Constants.alter_tag, tar, buffer);
		display.setDisplayAll(displayList);
		undoStack.push(editTil);
		Storage.write(taskList);
		redoStack.clear();
	}
	
	public void editTimeField(Task task, DateTime startDate, DateTime endDate){
		if (task.getType().equals(Constants.recur_tag)) {
			return;
		}
		if(startDate.getYear() == 0){
			startDate = task.getStartingTime();
		}
		if(endDate.getYear() == 0){
			endDate = task.getEndingTime();
		}
		Task buffer = new Task(task);
		Task tar = new Task (taskList.get(taskList.indexOf(task)));
		Task tarDis = new Task (displayList.get(displayList.indexOf(tar)));
		taskList.remove(taskList.get(taskList.indexOf(task)));
		displayList.remove(displayList.get(displayList.indexOf(task)));
		tar.setStartingDate(startDate);
		tarDis.setStartingDate(startDate);
		tar.setEndingDate(endDate);
		tarDis.setEndingDate(endDate);
		taskList.add(tar);
		displayList.add(tarDis);
		Command editDate = new Command(Constants.alter_tag, tar, buffer);
		undoStack.push(editDate);
		taskList = sorter.sortArrayByTime(taskList);
		display.setDisplayAll(displayList);
		Storage.write(taskList);
		redoStack.clear();
	}

	public void editStartingDate(Task task, DateTime date){
		if (task.getType().equals(Constants.recur_tag)) {
			return;
		}
		
		Task buffer = new Task(task);
		Task tar = new Task (taskList.get(taskList.indexOf(task)));
		Task tarDis = new Task (displayList.get(displayList.indexOf(tar)));
		taskList.remove(taskList.get(taskList.indexOf(task)));
		displayList.remove(displayList.get(displayList.indexOf(task)));
		tar.setStartingDate(date);
		tarDis.setStartingDate(date);
		taskList.add(tar);
		displayList.add(tarDis);
		Command editDate = new Command(Constants.alter_tag, tar, buffer);
		display.setDisplayAll(displayList);
		undoStack.push(editDate);
		taskList = sorter.sortArrayByTime(taskList);
		Storage.write(taskList);
		redoStack.clear();
	}

	public void editEndingDate(Task task, DateTime date){
		if (task.getType().equals(Constants.recur_tag)) {
			return;
		}
		Task buffer = new Task(task);
		Task tar = new Task (taskList.get(taskList.indexOf(task)));
		Task tarDis = new Task (displayList.get(displayList.indexOf(tar)));
		taskList.remove(taskList.get(taskList.indexOf(task)));
		displayList.remove(displayList.get(displayList.indexOf(task)));
		tar.setEndingDate(date);
		tarDis.setEndingDate(date);
		taskList.add(tar);
		displayList.add(tarDis);
		Command editDate = new Command(Constants.alter_tag, tar, buffer);
		display.setDisplayAll(displayList);
		undoStack.push(editDate);
		taskList = sorter.sortArrayByTime(taskList);

		Storage.write(taskList);
		redoStack.clear();
	}

	public ArrayList<Task> returnOverdue() {
		ArrayList<Task> overdue = new ArrayList<Task>();
		DateTime now = DateTime.now();
		ArrayList<Task> undone = getUnfinished();
		for (Task t : undone) {
			if ((t.getEndingTime().isAfter(now)) && (!t.getType().equals(Constants.TYPE_FLOATING))) {
				overdue.add(t);
			}
		}
		return overdue;

	}

	public ArrayList<Task> getUnfinished() {
		ArrayList<Task> result = new ArrayList<Task>();
		for (Task task : taskList) {
			if (task.isDone() == false) {
				result.add(task);
			}
		}
		return result;
	}

	public boolean isClashed(Task task) {
		if (!task.getType().equals(Constants.TYPE_FLOATING)) {
			DateTime start = task.getStartingTime();
			DateTime end = task.getEndingTime();
			for (Task t : taskList) {
				if((t.getStartingTime().getYear() == 0) || (t.getEndingTime().getYear()==0)){
					return false;
				}else if ((t.getStartingTime().isBefore(end)) && (t.getStartingTime().isAfter(start))) {
					return true;
				}else if ((t.getEndingTime().isAfter(start)) && (t.getEndingTime().isBefore(end))) {
					return true;
				}
			}
			return false;
		}
		return false;
	}

	public boolean isClashed(DateTime[] time) {
		DateTime start = time[0];
		DateTime end = time[1];
		for (Task t : taskList) {
			if((t.getType().equals(Constants.TYPE_FLOATING))||(t.getType().equals(Constants.TYPE_RECUR))){
				continue;
			}
			if((t.getStartingTime().getSecondOfDay() == start.getSecondOfDay()) && (t.getEndingTime().getSecondOfDay() == end.getSecondOfDay())){
				continue;
			}
			if ((t.getStartingTime().isBefore(end)) && (t.getStartingTime().isAfter(start))) {
				return true;
			}
			if ((t.getEndingTime().isAfter(start)) && (t.getEndingTime().isBefore(end))) {
				return true;
			}
		}
		return false;
	}
	
	public ArrayList<Task> returnAllInbox(){
		return new ArrayList<Task>(taskList);
	}

	public void clear() {
		taskList.clear();
		Storage.write(taskList);
	}

}
