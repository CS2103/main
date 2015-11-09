//@@author A0129708

package logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;

import org.joda.time.DateTime;


import application.Constants;
import application.LogHandler;

public class Task {
	private String title;
	private DateTime startingTime;
	private DateTime endingTime;
	private DateTime recurEnd;
	private String type_tag;
	private String recurTag;
	private boolean isRecur;
	private boolean isFinished;
	private ArrayList<DateTime> recurDate;
	private ArrayList<DateTime> recurDone;

	public Task(Task task) {
		this.isRecur = task.isTypeRecur();
		this.recurTag = task.returnRecurTag();
		this.recurEnd = task.getRecurEnd();
		this.title = task.getTitle();
		this.type_tag = task.getType();
		this.isFinished = task.returnIsFinished();
		this.startingTime = task.getStartingTime();
		this.endingTime = task.getEndingTime();
		this.recurDate = task.getRecurDates();
		this.recurDone = task.getDoneDates();

	}

	public Task(String title) {
		this.recurTag = new String();
		this.title = title;
		isFinished = false;
		isRecur = false;
		setTag();
	}

	public Task() {
		this.recurTag = new String();
		String time = Calendar.getInstance().toString();
		isRecur = false;
		this.title = "Untitled " + time;
		this.isFinished = false;

	}

	public Task(String title, DateTime startingTime, DateTime endingTime) throws InvalidTimeException {
		this.title = title;
		this.recurTag = new String();
		this.startingTime = startingTime;
		this.endingTime = endingTime;
		isRecur = false;
		try {
			if (endingTime.isBefore(startingTime)) {
				InvalidTimeException e = new InvalidTimeException("The ending time is prior to the starting time");
				throw e;
			}
		} catch (InvalidTimeException e) {
			String warning = "Error" + e;
			LogHandler.log(Level.INFO,warning);
		}
		setTag();
	}

	public Task(DateTime startingTime, DateTime endingTime) {

		this();
		this.startingTime = startingTime;
		this.endingTime = endingTime;
		this.recurTag = new String();
		isRecur = false;
		try {
			if (endingTime.isBefore(startingTime)) {
				InvalidTimeException e = new InvalidTimeException("The ending time is prior to the starting time");
				throw e;
			}
		} catch (InvalidTimeException e) {
			String warning = "Error" + e;
			LogHandler.log(Level.INFO,warning);
		}
		setTag();
	}

	public Task(String title, DateTime endingTime) {
		this(title);
		this.endingTime = endingTime;
		isRecur = false;
		setTag();
	}

	public Task(DateTime date) {
		this();
		this.endingTime = date;
		isRecur = false;
		setTag();
	}
	
	//Construction method for creating a recurring task
	public Task(String title, DateTime startingTime, DateTime endingTime, DateTime recurEnding, String recurTag) {
		isRecur = true;
		isFinished = false;
		this.recurTag = recurTag;
		this.title = title;
		this.startingTime = startingTime;
		this.endingTime = endingTime;
		this.recurEnd = recurEnding;
		DateTime end = new DateTime(endingTime);
		this.type_tag = Constants.TYPE_RECUR;
		recurDate = new ArrayList<DateTime>();
		recurDone = new ArrayList<DateTime>();
		recurDate.add(end);
		switch (recurTag) {
		case Constants.tag_weekly:
			while (end.plusWeeks(1).isBefore(recurEnd)) {
				end = new DateTime(end.plusWeeks(1));
				recurDate.add(end);

			}
			break;
		case Constants.tag_daily:
			while (end.plusDays(1).isBefore(recurEnd)) {
				end = new DateTime(end.plusDays(1));
				recurDate.add(end);
			}
			break;
		case Constants.tag_monthly:
			while (end.plusMonths(1).isBefore(recurEnd)) {
				end = new DateTime(end.plusMonths(1));
				recurDate.add(end);
			}
			break;
		case Constants.tag_yearly:
			while (end.plusMonths(1).isBefore(recurEnd)) {
				end = new DateTime(end.plusYears(1));
				recurDate.add(end);
			}
			break;
		}
	}

	public String getTitle() {
		return title;
	}

	//Return whether a task is finished,
	//for a recurring task, if the recurring instance for today is finished, it will be marked as finishied
	public boolean getStatus() {
		if (!isTypeRecur()) {
			return isFinished;
		} else {
			return this.isDone();
		}
	}

	public String getType() {
		return type_tag;
	}

	public DateTime getStartingTime() {
		return startingTime;
	}
	
	//Return the ending time of a task
	//If the task is a recurring task, the task would return the upcoming recurring date
	public DateTime getEndingTime() {
		if(!isTypeRecur()){
			return endingTime;
		}
		else{
			DateTime earliest = new DateTime(9999,12,31,23,59); 
			for(DateTime date: recurDate){
				if(!recurDone.contains(date)){
					if(date.isBefore(earliest)){
						earliest = date;
					}
				}
			}
			if(earliest.getYear()!=9999){
				return earliest;
			}else{
				return null;
			}
		}
	}
	
	//Check whether the task is overdue 
	public boolean isOverDue() {
		if (!isTypeRecur()) {
			return endingTime.isBeforeNow() && !type_tag.equals(Constants.TYPE_FLOATING) && (!isFinished);
		} else {
			DateTime now = new DateTime();
			for (DateTime t : recurDate) {
				if (t.isBefore(now) && !isDone(t)) {
					return true;
				}
			}
		}
		return false;

	}

	public void setTitle(String title) {
		this.title = title;
		if(isTypeRecur()){
			isRecur = true;
		}
	}

	public void mark() {
		if (!type_tag.equals(Constants.TYPE_RECUR)) {
			isFinished = true;
		} else {
			DateTime now = new DateTime();
			for (DateTime t : recurDate) {
				if (t.getDayOfYear() == now.getDayOfYear()) {
					recurDone.add(t);
					return;
				}
			}
		}
	}

	public void unMark() {
		if (!type_tag.equals(Constants.TYPE_RECUR)) {
			isFinished = false;
		} else {
			DateTime now = new DateTime();
			for (DateTime t : recurDone) {
				if (t.getDayOfYear() == now.getDayOfYear()) {
					recurDone.remove(t);
					return;
				}
			}
		}
	}

	public void setStartingDate(DateTime startingTime) {
		this.startingTime = startingTime;
		setTag();
	}

	public void setEndingDate(DateTime endingTime) {
		this.endingTime = endingTime;
		setTag();
	}

	public void setTag() {
		if (isTypeRecur()) {
			this.type_tag = Constants.TYPE_RECUR;
		}
		if (isValidDate(endingTime) || isValidDate(startingTime)) {
			if (isValidDate(startingTime)) {
				this.type_tag = Constants.TYPE_EVENT; // Event Tasks (have start
														// and end time)
			} else {
				this.type_tag = Constants.TYPE_DEADLINE; // Deadline Tasks (have
															// end but no start
															// time)
			}
		} else {
			this.type_tag = Constants.TYPE_FLOATING; // Floating Tasks (no start
														// and end date/time)
		}
	}

	public boolean isValidDate(DateTime date) {
		if (date.getYear() == 0) {
			return false;
		}
		return true;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			Task task = (Task) obj;
			if(!task.isTypeRecur()){
			return (this.getTitle().equals(task.getTitle()))
					&& (this.getEndingTime().isEqual(task.getEndingTime()))
					&& (this.getEndingTime().isEqual(task.getEndingTime()))
					&& (this.getType().equals(task.getType()));}
			else{
				return (this.getTitle().equals(task.getTitle())) && (this.getStatus() == task.getStatus())
						&& (this.getRecurEnd().equals(task.getRecurEnd()))
						&& (this.returnRecurTag().equals(task.returnRecurTag()));}
			
		} else {
			return false;
		}
	}
	
	//Return the ending date of the recurring task
	public DateTime getRecurEnd(){
		return recurEnd;
	}
	//Check whether the type of the task is a recurring task
	public boolean isTypeRecur() {
		return this.isRecur;
	}
	//Return the list of dates the task is recurring at
	public ArrayList<DateTime> getRecurDates() {
		if (isTypeRecur())
			return recurDate;
		return null;
	}
	//Return the list of dates the has been finished in 
	public ArrayList<DateTime> getDoneDates() {
		if (isTypeRecur())
			return recurDone;
		return null;
	}
	//Check whether the task is recurring at the day
	public boolean isRecur() {
		if (!isTypeRecur()) {
			return false;
		}
		DateTime now = new DateTime();
		for (DateTime t : recurDate) {
			if (t.getDayOfYear() == now.getDayOfYear()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isRecur(DateTime time){
		if (!isTypeRecur()) {
			return false;
		}
		for (DateTime t : recurDate) {
			if (t.getDayOfYear() == time.getDayOfYear()) {
				return true;
			}
		}
		return false;
	}
	
	public String returnRecurTag() {
		return recurTag;
	}

	protected boolean isDone() {
		if (!isTypeRecur()) {
			return false;
		}
		DateTime now = new DateTime();
		for (DateTime t : recurDone) {
			if ((t.getDayOfYear() == now.getDayOfYear())) {
				return true;
			}
		}
		return false;
	}

	private boolean isDone(DateTime date) {
		if (!isTypeRecur()) {
			return false;
		}
		for (DateTime t : recurDone) {
			if (t.getDayOfYear() == date.getDayOfYear()) {
				return true;
			}
		}
		return false;
	}
	
	public boolean returnIsFinished(){
		return isFinished;
	}
	
	//delete the specific day from the recurring list 
	public DateTime deleteRecur(DateTime date) {
		if (!isTypeRecur()) {
			return null;
		}
		DateTime del = new DateTime(0, 1, 1, 0, 0);
		for (DateTime t : recurDate) {
			if (t.getDayOfYear() == date.getDayOfYear()) {
				recurDate.remove(t);
				recurDone.remove(t);
				del = t;
			}
		}
		return del;
	}

}
