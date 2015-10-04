package logic;

<<<<<<< HEAD
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
=======
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

import org.joda.time.DateTime;

<<<<<<< HEAD
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
	Task buffer; 
	
=======
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
	Task buffer;

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	private static final String add_tag = "ADD";
	private static final String delete_tag = "DELETE";
	private static final String replace_tag = "REPLACE";
	private static final String alter_tag = "ALTER";

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
					}
					else if(inboxArr.get(i).getTitle().charAt(j) < inboxArr.get(i+1).getTitle().charAt(j)){
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
<<<<<<< HEAD
		
		for(int m = 1; m < inboxArr.size() - 1; m++){
			boolean isSorted = true;
			for(int i = 0; (i < inboxArr.size() - m); i++){   
=======

		for(int m = 1; m < inboxArr.size() - 1; m++){
			boolean isSorted = true;
			for(int i = 0; (i < inboxArr.size() - m); i++){
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
				if(inboxArr.get(i).getEndingDate() == null){
					timeUndefined.add(inboxArr.get(i));
					inboxArr.remove(inboxArr.get(i));
					isSorted = false;
					break;
				}
				if(inboxArr.get(i).getEndingDate().compareTo(inboxArr.get(i+1).getEndingDate()) > 0){
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
				return task1.getStartingDate().compareTo(task2.getStartingDate());
			}
		});*/
		timeUndefined = sortArrayByAlpha(timeUndefined);
		inboxArr.addAll(timeUndefined);
		return inboxArr;
	}
	/********************************Undo Methods************************************/
	public void undo(){
		Command previousComm = this.undoStack.pop();
		String command = previousComm.returnCommand();
		switch(command){
<<<<<<< HEAD
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
				taskList.remove(previousComm.returnMani());
				taskList.add(previousComm.returnOrigin());
				activeList.remove(previousComm.returnMani());
				activeList.add(previousComm.returnOrigin());
				break;
	
			default:
				System.out.println("Error: Unable to identify the command type");
		}
		Storage.write(taskList);
	}
	
=======
		case add_tag:
			redoStack.push(previousComm);
			taskList.remove(previousComm.returnMani());
			setDisplay();
			break;
		case delete_tag:
			redoStack.push(previousComm);
			taskList.add(previousComm.returnMani());
			//activeList.add(previousComm.returnMani());
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
			taskList.remove(previousComm.returnMani());
			taskList.add(previousComm.returnOrigin());
			activeList.remove(previousComm.returnMani());
			activeList.add(previousComm.returnOrigin());
			break;

		default:
			System.out.println("Error: Unable to identify the command type");
		}
		Storage.write(taskList);
	}

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	public void redo(){
		Command redoComm = redoStack.pop();
		String command = redoComm.returnCommand();
		switch(command){
<<<<<<< HEAD
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
				taskList.add(redoComm.returnMani());
				activeList.add(redoComm.returnMani());
				break;
				
			
			default:
				System.out.println("Error: Unable to identify the command type");
		
		}
				
	}
	
				
	/*********************************Search Methods************************************************/
	
=======
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
			taskList.add(redoComm.returnMani());
			activeList.add(redoComm.returnMani());
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
			}
		}
		Storage.write(taskList);
		return taskList;
	}

	public ArrayList<Task> unMarkTaskInstance(Task task) {
		for (Task obj : taskList) {
			if (obj.equals(task)) {
				System.out.println("ADSSADAD");
				System.out.println(obj.getTitle());
				obj.unMark();
			}
		}
		Storage.write(taskList);
		return taskList;
	}



>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	public ArrayList<Task> findTaskByTitle(String title){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:taskList){
			if (task.getTitle().indexOf(title)>=0) {
				result.add(task);
			}
			/*
			if(title.equals(task.getTitle())){
				result.add(task);
			}
<<<<<<< HEAD
			*/
		}
		sortArrayByTime(result);
		return result;			
		
	}
	
=======
			 */
		}
		sortArrayByTime(result);
		return result;

	}

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	public ArrayList<Task> findTaskByTitle(ArrayList<Task> list, String title){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:list){
			if(title.equals(task.getTitle())){
				result.add(task);
			}
		}
		sortArrayByTime(result);
<<<<<<< HEAD
		return result;					
	}
	
	public ArrayList<Task> findTaskByDate(Calendar date){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:taskList){
			if((date.get(Calendar.MONTH) == task.getEndingDate().get(Calendar.MONTH))&& (date.get(Calendar.DATE) == task.getEndingDate().get(Calendar.DATE)) && (date.get(Calendar.YEAR) == task.getEndingDate().get(Calendar.YEAR))){
=======
		return result;
	}

	public ArrayList<Task> findTaskByDate(DateTime date){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:taskList){
			if(date.equals(task.getEndingDate())){
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
				result.add(task);
			}
		}
		sortArrayByTime(result);
<<<<<<< HEAD
		return result;	
	}
	
	public ArrayList<Task> findTaskByDate(ArrayList<Task> list, Calendar date){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:list){
			if((date.get(Calendar.MONTH) == task.getEndingDate().get(Calendar.MONTH))&& (date.get(Calendar.DATE) == task.getEndingDate().get(Calendar.DATE)) && (date.get(Calendar.YEAR) == task.getEndingDate().get(Calendar.YEAR))){
=======
		return result;
	}

	public ArrayList<Task> findTaskByDate(ArrayList<Task> list, DateTime date){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:list){
			if(date.equals(task.getEndingDate())){
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
				result.add(task);
			}
		}
		sortArrayByTime(result);
<<<<<<< HEAD
		return result;	
	}
	
=======
		return result;
	}

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
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
<<<<<<< HEAD
	
=======
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe

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

<<<<<<< HEAD
	
	
=======
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	public ArrayList<Task> getUnfinished(){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:taskList){
			if(task.getStatus() == false){
				result.add(task);
			}
		}
		return result;
	}
<<<<<<< HEAD
	
=======

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	public ArrayList<Task> getUnfinished(ArrayList<Task> list){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:list){
			if(task.getStatus() == false){
				result.add(task);
			}
		}
		return result;
	}
<<<<<<< HEAD
	
	/***********************************Implementation*******************************************/
=======


	/***********************************Implementation*******************************************/
	@Override
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	public void replace(Task original, Task updated){
		Command repla = new Command(replace_tag, original);
		undoStack.push(repla);
		delete(original);
		add(updated);
		Storage.write(taskList);
		redoStack.clear();
	}
<<<<<<< HEAD
	
	public void editStartingDate(Task task, Calendar date){
=======

//	@Override
	public void editStartingDate(Task task, DateTime date){
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		Task tar = taskList.get(taskList.indexOf(task));
		Task tarDis = activeList.get(activeList.indexOf(tar));
		buffer = task;
		tar.setStartingDate(date);
		tarDis.setEndingDate(date);
		Command editDate = new Command(alter_tag, tar, buffer);
		undoStack.push(editDate);
		taskList = sortArrayByTime(taskList);
<<<<<<< HEAD
		
=======

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		Storage.write(taskList);
		buffer = null;
		redoStack.clear();
	}
<<<<<<< HEAD
	
	public void editEndingDate(Task task, Calendar date){
=======

//	@Override
	public void editEndingDate(Task task, DateTime date){
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		Task tar = taskList.get(taskList.indexOf(task));
		Task tarDis = activeList.get(activeList.indexOf(tar));
		buffer = task;
		tar.setEndingDate(date);
		tarDis.setEndingDate(date);
		Command editDate = new Command(alter_tag, tar, buffer);
		undoStack.push(editDate);
		taskList = sortArrayByTime(taskList);
<<<<<<< HEAD
		
=======

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		Storage.write(taskList);
		buffer = null;
		redoStack.clear();
	}
<<<<<<< HEAD
		
=======

	@Override
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	public void editTitle(Task task, String newTitle){
		Task tar = taskList.get(taskList.indexOf(task));
		Task tarDis = activeList.get(activeList.indexOf(tar));
		tarDis.setTitle(newTitle);
		buffer = task;
		tar.setTitle(newTitle);
<<<<<<< HEAD
		
=======

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		Command editTil = new Command(alter_tag, tar, buffer);
		undoStack.push(editTil);
		Storage.write(taskList);
		buffer = null;
		redoStack.clear();
	}
<<<<<<< HEAD
		
		
=======


	@Override
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	public void editDescription(Task task, String newDes){
		Task tar = taskList.get(taskList.indexOf(task));
		buffer = task;
		tar.setDescription(newDes);
		Task tarDis = activeList.get(activeList.indexOf(tar));
		tarDis.setTitle(newDes);
		Command editDes = new Command(alter_tag, tar, buffer);
		undoStack.push(editDes);
<<<<<<< HEAD
		
=======

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		Storage.write(taskList);
		buffer = null;
		redoStack.clear();
	}
	/*****************************************Retrieve Different Displays**********************************/
	public ArrayList<Task> displayInit(){
		ArrayList<Task> result = new ArrayList<Task>();
<<<<<<< HEAD
		Calendar now = Calendar.getInstance();
		for(Task task: taskList){
			if((now.get(Calendar.MONTH) == task.getEndingDate().get(Calendar.MONTH))&& (now.get(Calendar.DATE) == task.getEndingDate().get(Calendar.DATE)) && (now.get(Calendar.YEAR) == task.getEndingDate().get(Calendar.YEAR)) && (task.getStatus() == false)){
				result.add(task);
			}
		}
		return result;	
	}
	
	public void setDisplay(ArrayList<Task> list){
		activeList = list;
	}
	
=======
		DateTime now = new DateTime();
		for(Task task: taskList){
			if(now.equals(task.getEndingDate())){
				result.add(task);
			}
		}
		return result;
	}

	public void setDisplay(ArrayList<Task> list){
		activeList = list;
	}

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	public void setDisplay(){
		activeList = sortArrayByTime(activeList);
		activeList = taskList;
	}
<<<<<<< HEAD
	
=======

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	public ArrayList<Task> returnDisplay(){
		activeList = sortArrayByTime(activeList);
		return activeList;
	}
	/******************************************Used Methods***************************************************/
	/*public Task retrieveTask(int index) throws NoResultFound{
	try{
		for(Task task:taskList){
			if(task.getIndex() == index){
				return task;
			}
			else{
				throw new NoResultFound("No such task found in the list");
			}
		}
	}
	catch(NoResultFound ex){
		System.out.println("No such task is found in the list");
	}
	return null;
<<<<<<< HEAD
	
=======

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
}*/




<<<<<<< HEAD
	
=======

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe

}
