package logic;


import java.util.Calendar;

import org.joda.time.DateTime;


public class Task {
	private static int numberOfTask = 0;
	private static String type_tag;
	private String title;
	private String description;
	private boolean isFinished;
	private DateTime startingTime;
	private DateTime endingTime;
	private DateTime startingDate;
	private DateTime endingDate;
	private boolean[] recurring = new boolean[7];

	public Task(){
		String time = Calendar.getInstance().toString();
		title = "Untitled " + time;
		description = new String();
		numberOfTask++;
		isFinished = false;
		type_tag = "task";
	}

	public Task(DateTime date){
		this();
		endingDate = date;
		type_tag = "deadline";
	}

	public Task(DateTime sdate, DateTime edate){
		this();
		startingDate = sdate;
		endingDate = edate;
		type_tag = "event";
	}

	public Task(String title){
		this.title = title;
		description = new String();
		numberOfTask++;
		isFinished = false;
		type_tag = "task";
	}

	public Task(String title, DateTime date){
		this(title);
		endingDate = date;
		type_tag = "deadline";
	}

	public Task(String title, DateTime startingDate, DateTime endingDate){
		this(title);
		this.startingDate = startingDate;
		this.endingDate = endingDate;
		type_tag = "event";
	}

	public Task(String title, DateTime sdate, DateTime stime, DateTime edate, DateTime etime){
		this(title);
		startingDate= sdate;
		startingTime = stime;
		endingTime = etime;
		endingDate = edate;
		type_tag = "event";
	}


	public Task(Task task){
		this.title = task.getTitle();
		this.description = task.getDescription();
		this.startingDate = task.getStartingDate();
		this.endingDate = task.getEndingDate();
		this.isFinished = task.getStatus();
		this.startingTime = task.getStartingTime();
		this.endingTime = task.getEndingTime();
		this.recurring = task.getRecurring();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			Task task = (Task) obj;
			return (this.getDescription().equals(task.getDescription())) &&
					(this.getTitle().equals(task.getTitle())) &&
					(this.getStatus() == task.getStatus()) &&
					(this.getEndingDate().equals(task.getEndingDate())) &&
					(this.getStartingDate().equals(task.getStartingDate())) &&
					(this.getRecurring().equals(task.getRecurring()));
		} else {
			return false;
		}
	}

	public String getDescription(){
		return description;
	}
	public DateTime getEndingDate(){
		return endingDate;
	}
	public DateTime getEndingTime(){
		return endingTime;
	}
	public boolean[] getRecurring() {
		return recurring;
	}
	public DateTime getStartingDate(){
		return startingDate;
	}
	public DateTime getStartingTime(){
		return startingTime;
	}
	public boolean getStatus(){
		return isFinished;
	}
	public int getTaskCount(){
		return numberOfTask;
	}

	//Accessors
	public String getTitle(){
		return title;
	}

	public String getType(){
		return type_tag;
	}
	public boolean mark(){
		numberOfTask--;
		this.isFinished = true;
		return isFinished;
	}

	//Mutators
	public void setDescription(String des){
		description = des;
	}
	public void setEndingDate(DateTime date){
		endingDate = date;
		setTag();
	}

	public void setStartingDate(DateTime date){
		startingDate = date;
		setTag();
	}

	public void setTag(){
		if((startingDate == null)){
			if(endingDate == null){
				type_tag = "task";
			}
			else{
				type_tag = "deadline";
			}
		}
		else{
			type_tag = "event";
		}
	}

	public void setTitle(String title){
		this.title = title;
	}

	public void unMark() {
		this.isFinished = false;
		numberOfTask++;
	}
}