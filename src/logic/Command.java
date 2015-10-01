package logic;

public class Command {
	String COMMAND;
	Task taskManipulation;


	public Command(String com, Task taskAltered){
		COMMAND = com;
		taskManipulation = taskAltered;
	}


	public String returnCommand(){
		return COMMAND;
	}

	public Task returnMani(){
		return taskManipulation;
	}
}


