package logic;

import java.util.Calendar;


public interface editTaskInfo {
	
	public void editStartingDate(Task task, Calendar date);
	public void editEndingDate(Task task, Calendar date);
	public void editTitle(Task task, String tit);
	public void editDescription(Task task, String des);
	public void replace(Task task1, Task task2);

}
