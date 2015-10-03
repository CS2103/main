package logic;

import java.util.Date;
import java.sql.Time;
import java.util.Calendar;

//Storage class should store the static int numberOfTask; 
public class Task {
	private static int numberOfTask = 0;
	private String title;
	private String description;
	private boolean isFinished;
	private Date startingDate;
	private Date endingDate;
	private Time startingTime;
	private Time endingTime;
	private static String type_tag;
	private int index; 
	
	public Task(String title){
		this.title = title;
		description = new String();
		numberOfTask++;
		isFinished = false;
		type_tag = "task";
		index = numberOfTask;
	}
	
	public Task(){
		String time = Calendar.getInstance().toString();
		title = "Untitled " + time;
		description = new String();
		numberOfTask++;
		isFinished = false;
		type_tag = "task";
		index = numberOfTask;
	}
	
	public Task(String title, Date sdate, Time stime, Date edate, Time etime){
		this(title);
		startingDate= sdate;
		startingTime = stime;
		endingTime = etime;
		endingDate = edate;
		type_tag = "event";
	}
	
	public Task(Date sdate, Time stime, Date edate, Time etime){
		this();
		startingDate= sdate;
		startingTime = stime;
		endingTime = etime;
		endingDate = edate;
		type_tag = "event";
	}
	
	public Task(String title, java.util.Date startingDate, java.util.Date endingDate){
		this(title);
		this.startingDate = startingDate;
		this.endingDate = endingDate;
		type_tag = "event";
	}
	
	public Task(Date sdate, Date edate){
		this();
		startingDate = sdate;
		endingDate = edate;
		type_tag = "event";
	}
	
	public Task(String title, Date date, Time time){
		this(title);
		endingTime = time;
		endingDate = date;
	}
	
	public Task(Date date, Time time){
		this();
		endingTime = time;
		endingDate = date;
	}
	
	public Task(String title, Date date){
		this(title);
		endingDate = date;
	}
	
	public Task(Date date){
		this();
		endingDate = date;
	}
	
	
	//Accessors
	public String getTitle(){
		return title;
	}
	public String getDescription(){
		return description;
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
	public Date getStartingDate(){
		return startingDate;
	}
	public Time getStartingTime(){
		return startingTime;
	}
	public Time getEndingTime(){
		return endingTime;
	}
	public Date getEndingDate(){
		return endingDate;
	}
	public int getIndex(){
		return index;
	}


	//Mutators
	public void setDescription(String des){
		description = des;
	}
	public void setTitle(String til){
		title = til;
	}

	public void mark(){
		isFinished = true;
		numberOfTask--;
	}
	
	public void setStartingDate(Date date){
		startingDate = date;
		setTag();
	}

	public void setEndingDate(Date date){
		endingDate = date;
		setTag();
	}

	public void setStartingTime(Time time){
		startingTime = time;
		setTag();
	}

	public void setEndingTime(Time time){
		endingTime = time;
		setTag();
	}
	
	//decide on the tag
	
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
	
	//override
	public boolean equals(Task task){
		if (title.equals(task.getTitle()) 
				&& description.equals(task.getDescription()) 
				&& (isFinished == task.getStatus()
				&& (startingDate.equals(task.getStartingDate()))
				&& (startingTime.equals(task.getStartingTime()))
				&& (endingDate.equals(task.getEndingDate()))
				&& (endingTime.equals(task.getEndingTime()))
				&& type_tag.equals(task.getType()))){
			return true;
		}
		else{
			return false;
		}
	}
	
}

	
