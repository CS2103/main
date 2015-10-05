package logic;

<<<<<<< HEAD

import java.util.Calendar;

//Storage class should store the static int numberOfTask; 
=======

import java.util.Calendar;

import org.joda.time.DateTime;

//Storage class should store the static int numberOfTask;
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
public class Task {
	private static int numberOfTask = 0;
	private String title;
	private String description;
	private boolean isFinished;
<<<<<<< HEAD
	/*private Date startingDate;
	private Date endingDate;
	private Time startingTime;
	private Time endingTime;*/
	private Calendar startingDate;
	private Calendar endingDate;
	//private Calendar startingTime;
	//private Calendar endingTime;
	private static String type_tag;
	//private int index; 
	
=======
	private DateTime startingTime;
	private DateTime endingTime;
	private DateTime startingDate;
	private DateTime endingDate;
	private static String type_tag;
	//private int index;

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	public Task(String title){
		this.title = title;
		description = new String();
		numberOfTask++;
		isFinished = false;
		type_tag = "task";
		//index = numberOfTask;
	}

	public Task(){
		String time = Calendar.getInstance().toString();
		title = "Untitled " + time;
		description = new String();
		numberOfTask++;
		isFinished = false;
		type_tag = "task";
		//index = numberOfTask;
	}
<<<<<<< HEAD
	
=======

<<<<<<< HEAD
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	/*public Task(String title, Calendar sdate, Time stime, Date edate, Time etime){
=======
	public Task(String title, DateTime sdate, DateTime stime, DateTime edate, DateTime etime){
>>>>>>> master
		this(title);
		startingDate= sdate;
		startingTime = stime;
		endingTime = etime;
		endingDate = edate;
		type_tag = "event";
	}

	/*public Task(DateTime sdate, DateTime stime, DateTime edate, DateTime etime){
		this();
		startingDate= sdate;
		startingTime = stime;
		endingTime = etime;
		endingDate = edate;
		type_tag = "event";
	}*/
<<<<<<< HEAD
	
	public Task(String title, Calendar startingDate, Calendar endingDate){
=======

	public Task(String title, DateTime startingDate, DateTime endingDate){
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		this(title);
		this.startingDate = startingDate;
		this.endingDate = endingDate;
		type_tag = "event";
	}
<<<<<<< HEAD
	
	public Task(Calendar sdate, Calendar edate){
=======

	public Task(DateTime sdate, DateTime edate){
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		this();
		startingDate = sdate;
		endingDate = edate;
		type_tag = "event";
	}
<<<<<<< HEAD
	
=======

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
	/*public Task(String title, Date date, Time time){
		this(title);
		endingTime = time;
		endingDate = date;
	}

	public Task(Date date, Time time){
		this();
		endingTime = time;
		endingDate = date;
	}*/
<<<<<<< HEAD
	
	public Task(String title, Calendar date){
		this(title);
		endingDate = date;
	}
	
	public Task(Calendar date){
=======

	public Task(String title, DateTime date){
		this(title);
		endingDate = date;
	}

	public Task(DateTime date){
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
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
<<<<<<< HEAD
	public Calendar getStartingDate(){
		return startingDate;
	}
	/*public Time getStartingTime(){
=======
	public DateTime getStartingDate(){
		return startingDate;
	}
<<<<<<< HEAD
	/*public DateTime getStartingTime(){
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
=======
	public DateTime getStartingTime(){
>>>>>>> master
		return startingTime;
	}
	public DateTime getEndingTime(){
		return endingTime;
<<<<<<< HEAD
	}*/
<<<<<<< HEAD
	public Calendar getEndingDate(){
=======
=======
	}
>>>>>>> master
	public DateTime getEndingDate(){
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		return endingDate;
	}
	/*public int getIndex(){
		return index;
	}*/


	//Mutators
	public void setDescription(String des){
		description = des;
	}
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
<<<<<<< HEAD
	
	public void setStartingDate(Calendar date){
=======

	public void setStartingDate(DateTime date){
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		startingDate = date;
		setTag();
	}

<<<<<<< HEAD
	public void setEndingDate(Calendar date){
=======
	public void setEndingDate(DateTime date){
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		endingDate = date;
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
<<<<<<< HEAD
	
	//override
	public boolean equals(Task task){
		if (title.equals(task.getTitle()) 
				&& description.equals(task.getDescription()) 
				&& (isFinished == task.getStatus()
				&& (startingDate.equals(task.getStartingDate()))
				&& (endingDate.equals(task.getEndingDate()))
				&& type_tag.equals(task.getType()))){
			return true;
		}
		else{
=======

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			Task task = (Task) obj;
			return (this.getDescription().equals(task.getDescription())) &&
					(this.getTitle().equals(task.getTitle())) &&
					(this.getStatus() == task.getStatus()) &&
					(this.getEndingDate().equals(task.getEndingDate())) &&
					(this.getStartingDate().equals(task.getStartingDate()));
		} else {
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
			return false;
		}
	}

<<<<<<< HEAD
	
=======

>>>>>>> f1408057840addec287f7fac076bfe841975c2fe

}


