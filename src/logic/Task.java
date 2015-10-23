package logic;


import java.util.Calendar;

import org.joda.time.DateTime;


public class Task {
	private static int numberOfTask = 0;
	private String title;
	private boolean isFinished;
	private DateTime startingTime;
	private DateTime endingTime;
	private String type_tag;
	private boolean[] recurring = new boolean[7];

	public Task(Task task){
		this.title = task.getTitle();
		this.isFinished = task.getStatus();
		this.startingTime = task.getStartingTime();
		this.endingTime = task.getEndingTime();
		setTag();
	}

	public Task(String title){
		this.title = title;
		numberOfTask++;
		isFinished = false;
		setTag();
	}

	public Task(){
		String time = Calendar.getInstance().toString();
		this.title = "Untitled " + time;
		this.isFinished = false;
		numberOfTask++;
	}

	public Task(String title, DateTime startingTime, DateTime endingTime){
		this.title= title;
		this.startingTime = startingTime;
		this.endingTime = endingTime;
		setTag();
	}

	public Task(DateTime startingTime, DateTime endingTime){
		this();
		this.startingTime = startingTime;
		this.endingTime= endingTime;
		setTag();
	}

	public Task(String title, DateTime endingTime){
		this(title);
		this.endingTime= endingTime;
		setTag();
	}

	public Task(DateTime date){
		this();
		this.endingTime= date;
		setTag();
	}


	//Accessors
	public String getTitle(){
		return title;
	}
	public boolean getStatus(){
		return isFinished;
	}
	public int getTaskCount(){
		return numberOfTask;
	}
	public String getType(){
		return type_tag;
	}
	public DateTime getStartingTime(){
		return startingTime;
	}
	public DateTime getEndingTime(){
		return endingTime;
	}
	public boolean isAfterNow() {
		return endingTime.isBeforeNow() && !type_tag.equals("task");
	}

	//Mutators
	public void setTitle(String til){
		title = til;
	}

	public boolean mark(){
		numberOfTask--;
		isFinished = true;
		return isFinished;

	}
	public void unMark() {
		isFinished = false;
		numberOfTask++;
	}

	public void setStartingDate(DateTime startingTime){
		this.startingTime = startingTime;
		setTag();
	}

	public void setEndingDate(DateTime endingTime){
		this.endingTime = endingTime;
		setTag();
	}

	public void setTag(){
		if(isValidDate(endingTime) || isValidDate(startingTime)){
			if(isValidDate(startingTime)){
				this.type_tag = "event";			// Event Tasks (have start and end time)
			}
			else{
				this.type_tag = "deadline";		// Deadline Tasks (have end but no start time)
			}
		} else{
			this.type_tag = "task";				// Floating Tasks (no start and end date/time)
		}
	}
	
	public boolean isValidDate(DateTime date) {
		if (date.getYear() == 0){
			return false;
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			Task task = (Task) obj;
			return	(this.getTitle().equals(task.getTitle())) &&
					(this.getStatus() == task.getStatus()) &&
					(this.getEndingTime().equals(task.getEndingTime())) &&
					(this.getStartingTime().equals(task.getStartingTime())) &&
					(this.getType().equals(task.getType())
							);
		} else {
			return false;
		}
	}
}


