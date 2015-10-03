package logic;

import java.io.File;
import java.util.Date;
import java.util.ArrayList;
import java.util.Stack;

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
	Storage taskStorage;
	Stack<Command> undoStack;
	Stack<Command> redoStack;
	
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
		for(int m = 1; m < inboxArr.size(); m++){
			boolean isSorted = true;
			for(int i = 0; (i < inboxArr.size() - m); i++){   
				if(inboxArr.get(i).getStartingDate().compareTo(inboxArr.get(i+1).getStartingDate()) > 0){
					Task buffer = inboxArr.get(i);
					inboxArr.set(i, inboxArr.get(i+1));
					inboxArr.set(i+1, buffer);
					isSorted = false;
					break;
				}
			}
			if(isSorted){
				return inboxArr;
			}
		}
		return inboxArr;
	}
	/********************************Undo Methods************************************/
	public void undo(){
		Command previousComm = undoStack.pop();
		String command = previousComm.returnCommand();
		switch(command){
			case add_tag:
				taskList.remove(previousComm.returnMani());
			case delete_tag:
				taskList.add(previousComm.returnMani());
			case replace_tag:
				Command add = undoStack.pop();
				taskList.remove(add.returnMani());
				redoStack.push(add);
				Command del = undoStack.pop();
				taskList.add(del.returnMani());
				redoStack.push(del);
			case alter_tag:
				
			default:
				System.out.println("Error: Unable to identify the command type");
		}
			
		redoStack.push(previousComm);
		Storage.write(taskList);
	}
				
	/*********************************Search Methods************************************************/
	
	public ArrayList<Task> findTaskByTitle(String title){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:taskList){
			if(title.equals(task.getTitle())){
				result.add(task);
			}
		}
		return result;			
		
	}
	
	public ArrayList<Task> findTaskByTitle(ArrayList<Task> list, String title){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:list){
			if(title.equals(task.getTitle())){
				result.add(task);
			}
		}
		return result;					
	}
	
	public ArrayList<Task> findTaskByDate(Date date){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:taskList){
			if(date.equals(task.getEndingDate())){
				result.add(task);
			}
		}
		return result;	
	}
	
	public ArrayList<Task> findTaskByDate(ArrayList<Task> list, Date date){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:list){
			if(date.equals(task.getEndingDate())){
				result.add(task);
			}
		}
		return result;	
	}
	
	/*********************************************Manipulation Methods****************************************/
	public void add(Task newTask){
		Command add = new Command(add_tag, newTask);
		undoStack.push(add);
		taskList.add(newTask);
		taskList = sortArrayByTime(taskList);
		Storage.write(taskList);
	}
	

	public void delete(Task task){
		for(int i = 0; i< taskList.size(); i++){
			if(taskList.get(i).equals(task)){
				Command delete = new Command(delete_tag, taskList.get(i));
				undoStack.push(delete);
				taskList.remove(i);
				Storage.write(taskList);
			}
		}
	}

	
	public Task retrieveTask(int index) throws NoResultFound{
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
	
	/***********************************Implementation*******************************************/
	public void replace(Task original, Task updated){
		Command repla = new Command(replace_tag, original);
		undoStack.push(repla);
		delete(original);
		add(updated);
		Storage.write(taskList);
	}
	
	public void editStartingDate(Task task, Date date){
		Task tar = taskList.get(taskList.indexOf(task));
		Command editDate = new Command(alter_tag, task);
		undoStack.push(editDate);
		tar.setStartingDate(date);
		taskList = sortArrayByTime(taskList);
		Storage.write(taskList);
	}
	
	public void editEndingDate(Task task, Date date){
		Task tar = taskList.get(taskList.indexOf(task));
		Command editDate = new Command(alter_tag, task);
		undoStack.push(editDate);
		tar.setEndingDate(date);
		taskList = sortArrayByTime(taskList);
		Storage.write(taskList);
	}
		
	public void editTitle(Task task, String newTitle){
		Task tar = taskList.get(taskList.indexOf(task));
		Command editDate = new Command(alter_tag, task);
		undoStack.push(editDate);
		tar.setTitle(newTitle);
		Storage.write(taskList);
	}
		
		
	public void editDescription(Task task, String newDes){
		Task tar = taskList.get(taskList.indexOf(task));
		Command editDate = new Command(alter_tag, task);
		undoStack.push(editDate);
		tar.setDescription(newDes);
		Storage.write(taskList);
	}

	

}
