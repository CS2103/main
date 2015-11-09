package logic;

import java.util.ArrayList;

import org.joda.time.DateTime;

import application.Constants;

//@@Liu Xiaoming
public class DisplayTasks {
	ArrayList<Task> bufferList;
	ArrayList<Task> displayList;

	public DisplayTasks() {
		bufferList = new ArrayList<Task>();
		displayList = new ArrayList<Task>();
	}

	public ArrayList<Task> initDisplay(ArrayList<Task> taskList) {
		bufferList = taskList;
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
			if (isWithinOneWeek(t) || (!t.getStatus())) {
				dis.add(t);
			}
		}

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

	public void updateDisplay(Command command, boolean todo) {
		if (todo) {
			switch (command.returnCommand()) {
			case Constants.alter_tag:
				displayList.remove(displayList.get(displayList.indexOf(command.returnOrigin())));
				displayList.add(command.returnMani());
				break;

			case Constants.mark_tag:
				displayList.get(displayList.indexOf(command.returnMani())).mark();
				break;
			case Constants.unmark_tag:
				displayList.get(displayList.indexOf(command.returnMani())).unMark();
				break;
			}
		} else {
			switch (command.returnCommand()) {
			case Constants.alter_tag:
				displayList.add(displayList.get(displayList.indexOf(command.returnOrigin())));
				displayList.remove(command.returnMani());
				break;

			case Constants.mark_tag:
				displayList.get(displayList.indexOf(command.returnMani())).unMark();
				break;
			case Constants.unmark_tag:
				displayList.get(displayList.indexOf(command.returnMani())).mark();
				break;
			}
		}
	}
}
