
public class Command {
	String COMMAND;
	Task taskManipulation;
	Events eventsManipulation;
	Deadlines deadlineManipulation;
	
	public Command(String com, Task taskAltered){
		COMMAND = com;
		taskManipulation = taskAltered; 
	}
	
	public Command(String com, Events eventsAltered){
		COMMAND = com;
		eventsManipulation = eventsAltered; 
	}
	
	public Command(String com, Deadlines deadlinesAltered){
		COMMAND = com;
		deadlineManipulation = deadlinesAltered; 
	}

	public String returnCommand(){
		return COMMAND;		
	}
	
	
	
	public  returnMani(){
		if(taskManipulation != null){
			return taskManipulation;
		}
		else if()
	}
	
	public Deadlines returnDeadlineMani(){
		return deadlineManipulation;
	}
	public Events returnEventMani(){
		return eventManipulation;
	}
}
