package logic;


public class Command {
	String COMMAND;
	Task taskOrigin;
	Task taskManipulation;

	
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
	

