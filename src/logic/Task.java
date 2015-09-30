/**
 *
 * Last edited by ming
 *
 */

package logic;

import java.sql.Date;
import java.sql.Time;


public class Task {
	private static int numberOfTask = 0;
	protected String title;
	protected String description;
	private Date startingDate;
	private Date endingDate;
	private Time startingTime;
	private Time endingTime;
	private boolean isFinished;

	public Task(String title){
		this.title = title;
		description = new String();
		numberOfTask++;
		isFinished = false;
	}
	public Task(String title, String description){
		this.title = title;
		this.description = description;
		numberOfTask++;
		isFinished = false;
	}
	public void setStartingDate(Date date){
		startingDate = date;
	}
	public Date getStartingDate(){
		return startingDate;
	}
	public void setEndingDate(Date date){
		endingDate = date;
	}
	public Time getStartingTime(){
		return startingTime;
	}
	public void setStartingTime(Time time){
		startingTime = time;
	}
	public Time getEndingTime(){
		return endingTime;
	}
	public void setEndingTime(Time time){
		endingTime = time;
	}
	public Date getEndingDate(){
		return endingDate;
	}
	public void setDescription(String des){
		description = des;
	}
	public void setTitle(String til){
		title = til;
	}
	public String getTitle(){
		return title;
	}
	public String getDescription(){
		return description;
	}
	public int getTaskCount(){
		return numberOfTask;
	}
	public void mark(){
		isFinished = true;
		numberOfTask--;
	}

	public boolean getStatus(){
		return isFinished;
	}


}
