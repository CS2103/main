package logic;

import java.util.ArrayList;

public class Command {
	String COMMAND;
	Task taskOrigin;
	Task taskManipulation;
	ArrayList<Task> listMani = new ArrayList<Task>();
	ArrayList<Task> listOrigin = new ArrayList<Task>();

	
	public Command(String com, Task taskAltered){
		COMMAND = com;
		taskOrigin = null;
		taskManipulation = taskAltered; 
	}
	
	public Command(String com, Task taskAltered, Task origin){
		COMMAND = com;
		taskOrigin = origin;
		taskManipulation = taskAltered; 
	}
	
	public Command(String com, ArrayList<Task> tasklistAltered){
		COMMAND = com;
		taskOrigin = null;
		listOrigin = null;
		listMani = tasklistAltered; 
	}
	
	public Command(String com, ArrayList<Task> tasklistAltered, ArrayList<Task> tasklistOrigin){
		COMMAND = com;
		taskOrigin = null;
		listMani = tasklistAltered; 
		listOrigin = tasklistOrigin;
	}


	public ArrayList<Task> returnListMani(){
		return listMani;
	}
	public String returnCommand(){
		return COMMAND;		
	}
	
	public Task returnMani(){
		return taskManipulation;
	}
	
	public Task returnOrigin(){
		return taskOrigin;
	}
}
	

