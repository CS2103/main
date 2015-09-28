/**
 *
 *
 */
package logic;

import java.io.File;
import java.sql.Date;
import java.util.ArrayList;

import storage.Storage;


public class TaskBin {
	ArrayList<Task> taskList;
	//need to import the storage class
	//Storage class with read() and write()
	//To initiate a Storage instance, the construction method should include a parameter: File
	//or a string of address which indicates the location of the storage file stores at
	//The read method should return a empty ArrayList if the file empty
	//save method should be a static method
	ArrayList<Task> undoList;
	ArrayList<Task> previousSearchResult;
	Storage taskStorage;

	public TaskBin(ArrayList<Task> taskList, Storage taskStorage){
		undoList = new ArrayList<Task>();
		previousSearchResult = null;
		this.taskList = taskList;
		this.taskStorage = taskStorage;
	}

	public TaskBin(File storageFile){
		undoList = new ArrayList<Task>();
		previousSearchResult = null;
		//taskStorage = new Storage(storageFile);		//@ming @hung: invalid constructor
		//taskList = taskStorage.read();				//@ming @hung: read() method not implemented
	}

	public TaskBin(){
		undoList = new ArrayList<Task>();
		taskStorage = new Storage();
		taskList = new ArrayList<Task>();
	}

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
		return inboxArr; 					// @ming: i added this, need return statement
	}

	public void add(Task newTask){
		undoList = taskList;
		taskList.add(newTask);
		taskList = sortArrayByAlpha(taskList);
		//taskStorage.save(taskList);						//@ming @hung: save() method not implemented
	}

	public ArrayList<Task> findTaskByTitle(String title){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:taskList){
			if(title.equals(task.getTitle())){
				result.add(task);
			}
		}
		previousSearchResult = result;
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

	public ArrayList<Task> getUnfinished(Date date){
		ArrayList<Task> result = new ArrayList<Task>();
		for(Task task:taskList){
			if((task.getStatus() == false)&&(task.getStartingDate().equals(date))){
				result.add(task);
			}
		}
		return result;
	}

	public void mark(String title, Date date){
		undoList = taskList;
		for(Task task:taskList){
			if(title.equals(task.getTitle())&&date.equals(task.getStartingDate())){
				task.mark();
			}
		}
	}

	public void undo(){
		taskList = undoList;
		//taskStorage.save(taskList);								//@ming @hung: save() method not implemented
	}

}
