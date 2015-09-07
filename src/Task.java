import java.util.ArrayList;
import java.util.Calendar;

public class Task {

	/************** Data members **********************/
		private String taskName = null;
		private String taskDescription = null;
		private String startString = null;
		private String endString = null;
		//private String start = null;
		//private String end = null;
		private String priority = null; // 1-low, 2-medium, 3-high
		private ArrayList<String> labels = new ArrayList<String>();
		private boolean isRecurring;
		private boolean[] recurringDays = new boolean[7]; 
		private Calendar start;
		private Calendar end;

	/************** Constructors **********************/

	public Task() {
		this("Untitled Task", null, null);
	}

	public Task(String taskName, String taskDesription, String priority) {
		this.setTaskName(taskName);
		this.start = start;
		this.end = end;
		this.setTaskDescription(taskDescription);
		this.setPriority(priority);
		//this.setLabels()
	}
	

	/**************** Accessors ***********************/
	public String getTaskName() {
		return taskName;
	} 
	public String getStart() {
		return start.toString();
	}
	public String getEnd() {
		return end.toString();
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public String getPriority() {
		return priority;
	}
	
	/**************** Mutators ************************/
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public void setStart(int year, int month, int date, int hourOfDay, int minute) {
		this.start.set(year, month, date, hourOfDay, minute);
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public void setEnd(int year, int month, int date, int hourOfDay, int minute) {
		this.start.set(year, month, date, hourOfDay, minute);
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public void setLabels(ArrayList<String> labels) {
		this.labels = labels;
	}
	public void addLabel(String label) {
		this.labels.add(label);
	}
	public void setStartString(String startString) {
		this.startString = startString;
	}
	public void setEndString(String endString) {
		this.endString = endString;
	}

	/***************** Overriding methods ******************/
	// Overriding toString() method
	public String toString() {
		return "tasktostring";
	}
	
	public String details() {
		return "Task: " + taskName + "\nStart: " + startString + "\nEnd: " + endString + "\nPriority: " + priority;
	}

	// Overriding equals() method
	public boolean equals(Object obj) {
		if (obj instanceof Task) {
			return true;
		} else {
			return false;
		}
	}
}