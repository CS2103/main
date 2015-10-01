package logic;

import java.sql.Time;
import java.util.Date;

import application.Constants;

public class Task {

	private String name;
	private String location;
	private Date dueDate;
	private Date startDate;
	private Time startTime;
	private Time endTime;
	private String priority;
	private Boolean isDone;
	private Boolean isOverdue;


	/********** Constructors **********/
	public Task(String name) {
		setName(name);
		setLocation(null);
		setStartTime(null);
		setEndTime(null);
		setStartDate(null);
		setDueDate(null);
		setPriority(null);
		setIsDone(false);
		setIsOverdue(false);
	}

	public Task(String name, String location, Date dueDate) {
		setName(name);
		setLocation(location);
		setStartTime(null);
		setEndTime(null);
		setStartDate(null);
		setDueDate(dueDate);
		setPriority(null);
		setIsDone(false);
		setIsOverdue(false);
	}

	public Task(String name, String location, Date dueDate, Time endTime) {
		setName(name);
		setLocation(location);
		setStartTime(null);
		setEndTime(endTime);
		setStartDate(null);
		setDueDate(dueDate);
		setPriority(null);
		setIsDone(false);
		setIsOverdue(false);
	}
	/************ Accessors *************/
	public String getName() {
		return name;
	}

	public String getLocation() {
		return location;
	}

	public Date getTaskDueDate() {
		return dueDate;
	}

	public Date getEndDate() {
		return dueDate;
	}

	public Time getStartTime() {
		return startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public boolean getIsDone() {
		return isDone;
	}

	public boolean getIsOverdue() {
		return isOverdue;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getPriority() {
		return priority;
	}

	/************ Mutators *************/
	public void setName(String name) {
		this.name = name;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setStartDate(Date date) {
		this.startDate = date;
	}

	public void setDueDate(Date date) {
		this.dueDate = date;
	}

	public void setEndDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public void setIsDone(boolean isDone) {
		this.isDone = isDone;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public void setIsOverdue(boolean isOverdue) {
		this.isOverdue = isOverdue;
	}

	/********** Overriding methods ***********/
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean equals(Object obj) {
		return true;
	}

	/********** Formatting methods **********/
	public String taskDetails() {
		return Constants.LABEL_TITLE + name + Constants.LINEBREAK
				+ Constants.LABEL_START_DATE + startDate + Constants.LINEBREAK
				+ Constants.LABEL_START_TIME + startTime + Constants.LINEBREAK
				+ Constants.LABEL_DUE_DATE + dueDate + Constants.LINEBREAK
				+ Constants.LABEL_END_TIME + endTime + Constants.LINEBREAK
				+ Constants.LABEL_LOCATION + location + Constants.LINEBREAK
				+ Constants.LABEL_PRIORITY + priority + Constants.LINEBREAK;
	}
}
