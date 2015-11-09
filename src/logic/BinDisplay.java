//@@author A0129708
package logic;

import java.util.ArrayList;

import org.joda.time.DateTime;

import application.Constants;

public class BinDisplay {
	ArrayList<Task> bufferList;
	ArrayList<Task> displayList;
	BinSorter sorter;

	public BinDisplay() {
		bufferList = new ArrayList<Task>();
		displayList = new ArrayList<Task>();
		sorter = new BinSorter();
	}

	public ArrayList<Task> initDisplay(ArrayList<Task> taskList) {
		bufferList = taskList;
		sorter.sortArrayByTime(bufferList);
		setDisplay(bufferList);
		return bufferList;
	}

	// To display all the tasks today
	public ArrayList<Task> displayToday(ArrayList<Task> taskList) {
		ArrayList<Task> result = new ArrayList<Task>();
		DateTime now = new DateTime();
		DateTime startOfDay = now.withTimeAtStartOfDay();
		DateTime endOfDay = startOfDay.plusHours(24);
		for (Task task : taskList) {
			switch (task.getType()) {
			case Constants.TYPE_FLOATING:
				result.add(task);
				break;

			case Constants.TYPE_EVENT:
				if (task.getEndingTime().isBefore(endOfDay) && (task.getEndingTime().isAfter(startOfDay))) {
					result.add(task);
				}
				break;

			case Constants.TYPE_DEADLINE:
				if (task.getEndingTime().isBefore(endOfDay)) {
					result.add(task);
				}
				break;

			case Constants.TYPE_RECUR:
				for (DateTime date : task.getRecurDates()) {
					if (date.getDayOfYear() == now.getDayOfYear()) {
						result.add(task);
					}
				}
				break;
			}
		}

		return result;
	}

	// to display all tasks within one week and unfinished tasks
	public void setDisplay(ArrayList<Task> taskList) {
		ArrayList<Task> dis = new ArrayList<Task>();
		for (Task t : taskList) {
			if (isWithinOneWeek(t) && (!t.getStatus())) {
				dis.add(t);
			}
		}
		
		for(int i = 0; i < dis.size();i++ ){
			System.out.println("The titles in display list is: " + dis.get(i).getTitle());
		}
		dis = sorter.sortArrayByTime(dis);
		displayList = dis;
	}

	// display all tasks at one specific date
	public ArrayList<Task> setDisplay(ArrayList<Task> taskList, DateTime date) {
		ArrayList<Task> display = new ArrayList<Task>();
		for (Task t : taskList) {
			if (t.getEndingTime().getDayOfYear() == date.getDayOfYear()) {
				display.add(t);
			}
		}
		displayList = display;
		return display;
	}

	// return the display list back
	public ArrayList<Task> returnDisplay() {
		displayList = sorter.sortArrayByTime(displayList);
		System.out.println("The size of the activeList is:" + displayList.size());
		return displayList;
	}

	// display all tasks in the designated list
	public void setDisplayAll(ArrayList<Task> taskList) {
		displayList = taskList;
	}

	// Check whether the ending date of the task is within one week from now
	public boolean isWithinOneWeek(Task t) {
		DateTime now = new DateTime();
		DateTime weekAfter = now.plusDays(7);
		if (t.getType().equals(Constants.TYPE_RECUR)) {
			ArrayList<DateTime> dates = t.getRecurDates();
			for (DateTime date : dates) {
				if (date.isAfter(now) && date.isBefore(weekAfter)) {
					return true;
				}
			}
			return false;
		} else if (t.getType().equals(Constants.TYPE_FLOATING)) {
			return true;
		} else {
			if (t.getEndingTime().isAfter(now.plusWeeks(1))) {
				return false;
			} else {
				return true;
			}
		}
	}
	//Update the display list after each action is performed
	public void updateDisplay(Command command, boolean todo) {
		if (todo) {
			switch (command.returnCommand()) {
			case Constants.alter_tag:
				displayList.remove(command.returnOrigin());
				displayList.add(command.returnMani());
				break;

			case Constants.mark_tag:
				displayList.get(displayList.indexOf(command.returnMani())).mark();
				break;
			case Constants.unmark_tag:
				displayList.get(displayList.indexOf(command.returnMani())).unMark();
				break;
			default:
				System.out.println("Error: Unable to identify the command type");
			}
		} else {
			switch (command.returnCommand()) {
			case Constants.alter_tag:
				if(displayList.contains(command.returnOrigin())){
					displayList.add(displayList.get(displayList.indexOf(command.returnOrigin())));
					displayList.remove(command.returnMani());
				}
				break;

			case Constants.mark_tag:
				displayList.get(displayList.indexOf(command.returnMani())).unMark();
				break;
			case Constants.unmark_tag:
				displayList.get(displayList.indexOf(command.returnMani())).mark();
				break;
			default:
				System.out.println("Error: Unable to identify the command type");
			}
		}
	}
}
