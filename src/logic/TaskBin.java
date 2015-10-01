import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Stack;

import storage.Storage;



public class TaskBin {
	ArrayList<Task> taskList;
	Storage taskStorage;
	Stack<Command> undoStack;
	Stack<Command> redoStack;
	
	private static final String add_tag = "ADD";
	private static final String delete_tag = "DELETE";
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
	
	/********************************Sorting Methods************************************/
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
	}
	/********************************Undo Methods************************************/
	public void undo(){
		Command previousComm = undoStack.peek();
		redoStack.push(undoStack.pop());
		switch(previousComm.returnCommand()){
			case add_tag:
				taskList.remove(previousComm.returnTask());
			case delete_tag:
				taskList.add(previousComm.returnTask());
			
		}
		
	}
	
	
	/********************************Manipulation Methods************************************/
	public void add(Task newTask){
		Command add = new Command(add_tag, newTask);
		undoStack.push(add);
		taskList.add(newTask);
		taskList = sortArrayByAlpha(taskList);
		Storage.write(taskList);
	}
	
	public Task delete(Task task){
		for(int i = 0; i< taskList.size(); i++){
			if(taskList.get(i).equals(task)){
				Command delete = new Command(delete_tag, taskList.get(i));
				undoStack.push(delete);
				taskList.remove(i);
				Storage.write(taskList);
			}
		}
	}
	public ArrayList<Task> findTaskByTitle(String title){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:taskList){
			if(title.equals(task.getTitle())){
				result.add(task);
			}
		}
		return result;			
		
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
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
