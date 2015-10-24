package logic;

import java.util.ArrayList;
import java.util.Stack;

import org.joda.time.DateTime;

import storage.Storage;

class NoResultFound extends Exception{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public NoResultFound(){}
	public NoResultFound(String message){
		super(message);
	}
}

public class TaskBin implements editTaskInfo{
	ArrayList<Task> taskList;
	ArrayList<Task> activeList;
	Storage taskStorage;
	Stack<Command> undoStack;
	Stack<Command> redoStack;

	private static final String add_tag = "ADD";
	private static final String delete_tag = "DELETE";
	private static final String replace_tag = "REPLACE";
	private static final String alter_tag = "ALTER";
	private static final String mark_tag = "MARK";
	private static final String unmark_tag = "UNMARK";
	private static final String recur_tag = "ADD_RECUR";

	/********************************Construction Methods************************************/

	//This construction is used to success the past data from the storage file when the program is reopened
	public TaskBin(ArrayList<Task> taskList){
		undoStack = new Stack<Command>();
		redoStack = new Stack<Command>();
		this.taskList = taskList;
	}

	public TaskBin(){
		undoStack = new Stack<Command>();
		redoStack = new Stack<Command>();
		taskList = new ArrayList<Task>();
	}
	/*******************************************initialization*************************************/
	public void init(){
		taskList = Storage.read();
		activeList = Storage.read();
	}
	/******************************************Sorting Methods************************************/
	public ArrayList<Task> sortArrayByAlpha(ArrayList<Task> inboxArr){
		for(int m = 1; m < inboxArr.size(); m++){
			boolean isSorted = true;
			for(int i = 0; (i < inboxArr.size() - m); i++){
				boolean isCompared = false;
				for(int j = 0; (j < inboxArr.get(i).getTitle().length() - 1)&&(isCompared != true); j++){
					if(inboxArr.get(i).getTitle().charAt(j) > inboxArr.get(i+1).getTitle().charAt(j)){
						Task buffer = inboxArr.get(i);
						inboxArr.set(i, inboxArr.get(i+1));
						inboxArr.set(i+1, buffer);
						isSorted = false;
						isCompared = true;
						break;
					} else if(inboxArr.get(i).getTitle().charAt(j) < inboxArr.get(i+1).getTitle().charAt(j)){
						isCompared = true;
						break;
					}
				}

			}
			if(isSorted == true){
				return inboxArr;
			}
		}
		return inboxArr;
	}

	public ArrayList<Task> sortArrayByTime(ArrayList<Task> inboxArr){
		ArrayList<Task> timeUndefined = new ArrayList<Task>();
		if(inboxArr.size() <= 1){
			return inboxArr;
		}

		for(int m = 1; m < inboxArr.size() - 1; m++){
			boolean isSorted = true;
			for(int i = 0; (i < inboxArr.size() - m); i++){
				if(inboxArr.get(i).getEndingTime() == null){
					timeUndefined.add(inboxArr.get(i));
					inboxArr.remove(inboxArr.get(i));
					isSorted = false;
					break;
				}
				if(inboxArr.get(i).getEndingTime().compareTo(inboxArr.get(i+1).getEndingTime()) > 0){
					Task buffer = inboxArr.get(i);
					inboxArr.set(i, inboxArr.get(i+1));
					inboxArr.set(i+1, buffer);
					isSorted = false;
					break;
				}
			}
			if(isSorted){
				inboxArr.addAll(timeUndefined);
				return inboxArr;
			}
		}
		/*Collections.sort(inboxArr, new Comparator<Task>(){
			public int compare(Task task1, Task task2){
				return task1.getStartingTime().compareTo(task2.getStartingTime());
			}
		});*/
		timeUndefined = sortArrayByAlpha(timeUndefined);
		inboxArr.addAll(timeUndefined);
		return inboxArr;
	}
	/********************************Undo Methods************************************/
	public void undo(){
		if (this.undoStack.isEmpty()) {
			return;
		}
		Command previousComm = this.undoStack.pop();
		String command = previousComm.returnCommand();
		switch(command){
		case add_tag:
			redoStack.push(previousComm);
			taskList.remove(previousComm.returnMani());
			setDisplay();
			break;
		case delete_tag:
			redoStack.push(previousComm);
			taskList.add(previousComm.returnMani());
			activeList.add(previousComm.returnMani());
			break;
		case replace_tag:
			Command add = undoStack.pop();
			taskList.remove(add.returnMani());
			activeList.remove(add.returnMani());
			redoStack.push(add);
			Command del = undoStack.pop();
			taskList.add(del.returnMani());
			activeList.add(del.returnMani());
			redoStack.push(del);
			redoStack.push(previousComm);
			break;

		case alter_tag:
			redoStack.push(previousComm);
			System.out.println(previousComm.returnMani().getTitle() + previousComm.returnOrigin().getTitle());
			System.out.println(taskList.get(activeList.indexOf(previousComm.returnMani())));
			taskList.remove(taskList.get(taskList.indexOf(previousComm.returnMani())));
			taskList.add(previousComm.returnOrigin());
			if(activeList != taskList){
				System.out.println(activeList.get(activeList.indexOf(previousComm.returnMani())));
				activeList.remove(activeList.get(activeList.indexOf(previousComm.returnMani())));
				activeList.add(previousComm.returnOrigin());
			}
			break;

		case recur_tag:
			redoStack.push(previousComm);
			ArrayList<Task> added = previousComm.returnListMani();
			for(Task t: added){
				taskList.remove(t);
			}
			break;

		case mark_tag:
			redoStack.push(previousComm);
			taskList.get(taskList.indexOf(previousComm.returnMani())).unMark();
			activeList.get(activeList.indexOf(previousComm.returnMani())).unMark();
			break;

		case unmark_tag:
			redoStack.push(previousComm);
			taskList.get(taskList.indexOf(previousComm.returnMani())).mark();
			activeList.get(activeList.indexOf(previousComm.returnMani())).mark();
			break;

		default:
			System.out.println("Error: Unable to identify the command type");
		}
		Storage.write(taskList);
	}

	public void redo(){
		if (this.redoStack.isEmpty()) {
			return;
		}
		Command redoComm = redoStack.pop();
		String command = redoComm.returnCommand();
		switch(command){
		case add_tag:
			undoStack.push(redoComm);
			taskList.add(redoComm.returnMani());
			setDisplay();
			break;
		case delete_tag:
			undoStack.push(redoComm);
			taskList.remove(redoComm.returnMani());
			activeList.remove(redoComm.taskManipulation);
			break;
		case alter_tag:
			undoStack.push(redoComm);
			taskList.remove(redoComm.returnOrigin());
			activeList.remove(redoComm.returnOrigin());
			if (taskList != activeList) {
				taskList.add(redoComm.returnMani());
				activeList.add(redoComm.returnMani());
			} else {
				taskList.add(redoComm.returnMani());
			}
			break;
			
		case mark_tag:
			undoStack.push(redoComm);
			taskList.get(taskList.indexOf(redoComm.returnMani())).mark();
			activeList.get(activeList.indexOf(redoComm.returnMani())).mark();
			break;

		case unmark_tag:
			undoStack.push(redoComm);
			taskList.get(taskList.indexOf(redoComm.returnMani())).unMark();
			activeList.get(activeList.indexOf(redoComm.returnMani())).unMark();
			break;

		default:
			System.out.println("Error: Unable to identify the command type");

		}

	}


	/*********************************Search Methods************************************************/

	public ArrayList<Task> markTaskInstance(Task task) {
		for (Task obj : taskList) {
			if (obj.equals(task)) {
				System.out.println("ADSSADAD");
				System.out.println(obj.getTitle());
				obj.mark();
				Command mark = new Command(mark_tag, obj);
				undoStack.push(mark);
			}
		}
		for(Task obj : activeList){
			if (obj.equals(task)) {
				System.out.println("ADSSADAD");
				System.out.println(obj.getTitle());
				obj.mark();
			}
		}
		Storage.write(taskList);
		return activeList;
	}

	public ArrayList<Task> unMarkTaskInstance(Task task) {
		for (Task obj : taskList) {
			if (obj.equals(task)) {
				System.out.println("ADSSADAD");
				System.out.println(obj.getTitle());
				obj.unMark();
				Command mark = new Command(unmark_tag, obj);
				undoStack.push(mark);
			}
		}
		for(Task obj : activeList){
			if (obj.equals(task)) {
				System.out.println("ADSSADAD");
				System.out.println(obj.getTitle());
				obj.unMark();
			}
		}
		Storage.write(taskList);
		return activeList;
	}



	public ArrayList<Task> findTaskByTitle(String title){
		ArrayList<Task> result = new ArrayList<Task>();
		String[] keywords = title.split(" ");
		for(Task task:taskList){
			String[] listOfWord = task.getTitle().split(" ");
			if(includeAllWords(keywords, listOfWord)){
				result.add(task);
			}
		}
		sortArrayByTime(result);
		return result;

	}

	public ArrayList<Task> returnTaskByType(String type_tag){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task t: taskList){
			if(t.getType().equals(type_tag)){
				result.add(t);
			}
		}
		return result;
	}

	public ArrayList<Task> findTaskByTitle(ArrayList<Task> list, String title){
		ArrayList<Task> result = new ArrayList<Task>();
		String[] keywords = title.split(" ");
		for(Task task:list){
			String[] listOfWord = task.getTitle().split(" ");
			if(includeAllWords(keywords, listOfWord)){
				result.add(task);
			}
		}
		sortArrayByTime(result);
		return result;
	}

	public DateTime returnToday(){
		DateTime date = DateTime.now();
		int day = date.getDayOfMonth();
		int year = date.getYear();
		int month = date.getMonthOfYear();
		DateTime today = new DateTime(year, month, day, 0, 0);
		return today;

	}

	public ArrayList<Task> returnOverdue(){
		ArrayList<Task> overdue = new ArrayList<Task>();
		DateTime now = DateTime.now();
		ArrayList<Task> undone = getUnfinished();
		for(Task t:undone){
			if((t.getEndingTime().isAfter(now))&&(!t.getType().equals("task"))){
				overdue.add(t);
			}
		}
		return overdue;

	}



	public ArrayList<Task> findTaskByDate(DateTime date){
		ArrayList<Task> result = new ArrayList<Task>();
		DateTime day = returnToday();

		for(Task task:taskList){
			if((day.isAfter(task.getEndingTime()))&&(day.isBefore(day.plusDays(1)))){
				result.add(task);
			}
		}
		sortArrayByTime(result);
		return result;
	}

	public ArrayList<Task> findTaskByDate(ArrayList<Task> list, DateTime date){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:list){
			if(date.equals(task.getEndingTime())){
				result.add(task);
			}
		}
		sortArrayByTime(result);
		return result;
	}

	/*********************************************Manipulation Methods****************************************/
	public void add(Task newTask){
		Command add = new Command(add_tag, newTask);
		this.undoStack.push(add);
		System.out.println(undoStack.size());
		this.taskList.add(newTask);
		taskList = sortArrayByTime(taskList);
		Storage.write(taskList);
		redoStack.clear();
	}

	//New Method
	/*public void addWeeklyTask(Task newTask, DateTime endTime){
		ArrayList<Task> taskAdded = new ArrayList<Task>();
		DateTime start = newTask.getEndingTime();
		int i = 0;
		while(newTask.getEndingTime().isBefore(endTime.getMillis())){
			Task tskcpy = new Task(newTask);
			tskcpy.setEndingDate(start.plusWeeks(i));
			if(newTask.getStartingTime()!=null){
				tskcpy.setStartingDate(tskcpy.getStartingTime().plusWeeks(1));
			}
			i++;
			taskList.add(tskcpy);
			taskAdded.add(tskcpy);
		}
		Storage.write(taskList);
		Command redoRecur = new Command(recur_tag, taskAdded);
		redoStack.push(redoRecur);
	}

	public void addMonthlyTask(Task newTask, DateTime endTime){
		DateTime start = newTask.getEndingTime();
		int i = 0;
		while(newTask.getEndingTime().isBefore(endTime.getMillis())){
			Task tskcpy = new Task(newTask);

			tskcpy.setEndingDate(start.plusMonths(i));
			if(newTask.getStartingTime()!=null){
				tskcpy.setStartingDate(tskcpy.getStartingTime().plusWeeks(1));
			}
			i++;
			taskList.add(tskcpy);
		}
		Storage.write(taskList);
	}

	public void addYearlyTask(Task newTask, DateTime endTime){
		DateTime start = newTask.getEndingTime();
		int i = 0;
		while(newTask.getEndingTime().isBefore(endTime.getMillis())){
			Task tskcpy = new Task(newTask);

			tskcpy.setEndingDate(start.plusYears(i));
			if(newTask.getStartingTime()!=null){
				tskcpy.setStartingDate(tskcpy.getStartingTime().plusWeeks(1));
			}
			i++;
			taskList.add(tskcpy);
		}
		
	}*/




	public void delete(Task task){
		for(int i = 0; i< taskList.size(); i++){
			if(taskList.get(i).equals(task)){
				Command delete = new Command(delete_tag, taskList.get(i));
				undoStack.push(delete);
				taskList.remove(i);
				Storage.write(taskList);
				break;
			}
		}
		redoStack.clear();
	}



	public ArrayList<Task> getUnfinished(){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:taskList){
			if(task.getStatus() == false){
				result.add(task);
			}
		}
		return result;
	}

	public ArrayList<Task> getUnfinished(ArrayList<Task> list){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:list){
			if(task.getStatus() == false){
				result.add(task);
			}
		}
		return result;
	}


	/***********************************Implementation*******************************************/
	@Override
	public void replace(Task original, Task updated){
		Command repla = new Command(replace_tag, original);
		undoStack.push(repla);
		delete(original);
		add(updated);
		Storage.write(taskList);
		redoStack.clear();
	}

	@Override
	public void editStartingDate(Task task, DateTime date){
		Task tar = taskList.get(taskList.indexOf(task));
		Task tarDis = activeList.get(activeList.indexOf(tar));
		Task buffer = new Task(task);
		tar.setStartingDate(date);
		tarDis.setStartingDate(date);
		Command editDate = new Command(alter_tag, tar, buffer);
		undoStack.push(editDate);
		taskList = sortArrayByTime(taskList);

		Storage.write(taskList);
		redoStack.clear();
	}


	@Override
	public void editEndingDate(Task task, DateTime date){
		Task tar = taskList.get(taskList.indexOf(task));
		Task tarDis = activeList.get(activeList.indexOf(tar));
		Task buffer = new Task(task);
		tar.setEndingDate(date);
		tarDis.setEndingDate(date);
		Command editDate = new Command(alter_tag, tar, buffer);
		undoStack.push(editDate);
		taskList = sortArrayByTime(taskList);

		Storage.write(taskList);
		redoStack.clear();
	}

	@Override
	public void editTitle(Task task, String newTitle){
		Task tar = taskList.get(taskList.indexOf(task));
		Task tarDis = activeList.get(activeList.indexOf(tar));
		Task buffer = new Task(task);
		tarDis.setTitle(newTitle);
		tar.setTitle(newTitle);
		Command editTil = new Command(alter_tag, tar, buffer);
		undoStack.push(editTil);
		Storage.write(taskList);
		redoStack.clear();
	}


	@Override
	public void editDescription(Task task, String newDes){
		Task tar = taskList.get(taskList.indexOf(task));
		Task buffer = task;

		Task tarDis = activeList.get(activeList.indexOf(tar));
		tarDis.setTitle(newDes);
		Command editDes = new Command(alter_tag, tar, buffer);
		undoStack.push(editDes);

		Storage.write(taskList);
		redoStack.clear();
	}
	/*****************************************Retrieve Different Displays**********************************/
	public ArrayList<Task> displayInit(){
		ArrayList<Task> result = new ArrayList<Task>();
		DateTime now = new DateTime();
		DateTime startOfDay = now.withTimeAtStartOfDay();
		DateTime endOfDay = startOfDay.plusHours(24);
		for(Task task: taskList){
			switch(task.getType()){
			case "task":
				result.add(task);
				break;

			case "event":
				if(task.getEndingTime().isBefore(endOfDay) && (task.getEndingTime().isAfter(startOfDay))){
					result.add(task);
				}
				break;

			case "deadline":
				if(task.getEndingTime().isBefore(endOfDay)){
					result.add(task);
				}
				break;
			}
		}

		return result;
	}

	public void setDisplay(ArrayList<Task> list){
		activeList = list;
	}
	
	public void setDisplayAll(){
		activeList = taskList;
	}

	public void setDisplay(){
		ArrayList<Task> dis = new ArrayList<Task>();
		for(Task t:taskList){
			if(isWithinOneWeek(t)){
				dis.add(t);
			}
		}
		dis = sortArrayByTime(dis);
		activeList = dis;
	}

	public ArrayList<Task> returnDisplay(){
		activeList = sortArrayByTime(activeList);
		return activeList;
	}
	/******************************************Used Methods***************************************************/
	//All words in keywords list are included in the title
	public boolean includeAllWords(String[] keywords, String[] title){
		boolean isFound;
		for(String key:keywords){
			isFound = false;
			for(String til: title){
				if(key.equalsIgnoreCase(til)){
					isFound = true;
				}
			}
			if(isFound == false){
				return false;
			}
		}
		return true;
	}
	
	public boolean isWithinOneWeek(Task t){
		DateTime now = new DateTime();
		if(t.getEndingTime().isAfter(now.plusWeeks(1))){
			return false;
		}
		else{
			return true;
		}
	}
	
	public ArrayList<Task> taskFilter(ArrayList<Task> arr){
		ArrayList<Task> filteredResults = new ArrayList<Task>();
		for(Task t: arr){
			if(t.isAfterNow()&& t.getStatus()){
				filteredResults.add(t);
			}
		}
		return filteredResults;
	}
		
}
