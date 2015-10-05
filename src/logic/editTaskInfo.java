package logic;

import org.joda.time.DateTime;


public interface editTaskInfo {

	public void editStartingDate(Task task, DateTime date);
	public void editEndingDate(Task task, DateTime date);
	public void editTitle(Task task, String tit);
	public void editDescription(Task task, String des);
	public void replace(Task task1, Task task2);

}
