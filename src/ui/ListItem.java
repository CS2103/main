//@@author A0121442X
package ui;

import org.joda.time.DateTime;

import application.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ListItem extends StackPane {

    private Circle statusIcon;
    private Label index;
    private Label title;
    private Label taskDuration;

    public ListItem(String taskTitle, DateTime taskStartTime, DateTime taskEndTime, String taskType, boolean isDone,
	    boolean isOverdue, String recurValue, int taskIndex) {

	title = new Label(taskTitle);
	title.setPadding(new Insets(0, 0, 0, 5));
	title.setMaxWidth(Double.MAX_VALUE);
	if (isOverdue) {
	    title.setId(Constants.CSS_TITLE_OVERDUE);
	} else {
	    if (taskType.equals(Constants.TYPE_FLOATING)) {
		title.setId(Constants.CSS_TITLE_TASK);
	    } else if (taskType.equals(Constants.TYPE_EVENT)) {
		title.setId(Constants.CSS_TITLE_EVENT);
	    } else if (taskType.equals(Constants.TYPE_DEADLINE)) {
		title.setId(Constants.CSS_TITLE_DEADLINE);
	    } else if (taskType.equals(Constants.TYPE_RECUR)) {
		title.setId(Constants.CSS_TITLE_EVENT);
	    }
	}

	index = new Label(String.valueOf(taskIndex));
	index.setId(Constants.CSS_INDEX);

	statusIcon = new Circle();
	statusIcon.setId(Constants.CSS_STATUS_ICON);
	statusIcon.setRadius(5);

	DropShadow dropShadow = new DropShadow();
	dropShadow.setRadius(5.0);
	dropShadow.setOffsetX(2.0);
	dropShadow.setOffsetY(2.0);
	dropShadow.setColor(Color.color(0.3, 0.3, 0.3));
	statusIcon.setEffect(dropShadow);

	if (isDone) {
	    statusIcon.setFill(Color.GREENYELLOW);
	} else {
	    statusIcon.setFill(Color.RED);
	}

	String startDate = (taskStartTime.getYear() == DateTime.now().getYear())
		? taskStartTime.toLocalDate().toString(Constants.FORMAT_DATE_WITHOUT_YEAR)
		: taskStartTime.toLocalDate().toString(Constants.FORMAT_FULL_DATE);
	String startTime = taskStartTime.toLocalTime().toString(Constants.FORMAT_TWENTYFOURHOUR);
	String endDate = (taskEndTime.getYear() == DateTime.now().getYear())
		? taskEndTime.toLocalDate().toString(Constants.FORMAT_DATE_WITHOUT_YEAR)
		: taskEndTime.toLocalDate().toString(Constants.FORMAT_FULL_DATE);
	String endTime = taskEndTime.toLocalTime().toString(Constants.FORMAT_TWENTYFOURHOUR);

	if (taskEndTime.isBefore(taskStartTime)) {
	    endDate = Constants.FORMAT_UNKNOWN;
	    endTime = Constants.FORMAT_UNKNOWN;
	}

	taskDuration = new Label();
	taskDuration.setId(Constants.CSS_DURATION);
	if (taskType.equals(Constants.TYPE_DEADLINE)) {
	    taskDuration.setText(String.format(Constants.FORMAT_DEADLINE, endDate, endTime));
	} else if (taskType.equals(Constants.TYPE_EVENT)) {
	    if (checkIfOnSameDay(taskStartTime, taskEndTime)) {
		if (startTime.equals(Constants.TIME_START_OF_DAY) && endTime.equals(Constants.TIME_END_OF_DAY)) {
		    taskDuration.setText(String.format(Constants.FORMAT_WHOLE_DAY, endDate));
		} else {
		    taskDuration.setText(String.format(Constants.FORMAT_SAME_DAY, endDate, startTime, endTime));
		}
	    } else {
		taskDuration.setText(String.format(Constants.FORMAT_EVENT, startDate, startTime, endDate, endTime));
	    }
	} else if (taskType.equals(Constants.TYPE_RECUR)) {
	    taskDuration.setText(String.format(Constants.FORMAT_RECURRING, recurValue, startDate, startTime, endTime));
	}

	HBox titleNstatus = new HBox();
	titleNstatus.setAlignment(Pos.CENTER_LEFT);
	titleNstatus.getChildren().addAll(statusIcon, title);

	HBox timeLayout = new HBox();
	timeLayout.getChildren().addAll(taskDuration);

	VBox detailsLayout = new VBox();

	if (taskType.equals(Constants.TYPE_FLOATING)) {
	    detailsLayout.getChildren().addAll(titleNstatus);
	} else {
	    detailsLayout.getChildren().addAll(titleNstatus, timeLayout);
	}

	detailsLayout.setPadding(new Insets(0, 0, 0, 5));
	detailsLayout.setAlignment(Pos.CENTER);

	VBox statusLayout = new VBox();
	statusLayout.setPadding(new Insets(0, 0, 0, 0));
	statusLayout.setAlignment(Pos.CENTER_RIGHT);
	statusLayout.setPrefWidth(30);
	statusLayout.getChildren().addAll(this.index);

	HBox consoleLayout = new HBox();
	HBox.setHgrow(detailsLayout, Priority.ALWAYS);
	consoleLayout.getChildren().addAll(detailsLayout, statusLayout);
	this.setId("listItem");
	this.getChildren().add(consoleLayout);
	this.setPadding(new Insets(2, 10, 2, 2));
	this.setMinHeight(40);
    }

    public String getTitle() {
	return this.title.getText();
    }

    public String getIsOverdue() {
	return this.title.getText();
    }

    public boolean checkIfOnSameDay(DateTime dateTime1, DateTime dateTime2) {
	if (dateTime1.getDayOfMonth() == dateTime2.getDayOfMonth()
		&& dateTime1.getMonthOfYear() == dateTime2.getMonthOfYear()
		&& dateTime1.getYear() == dateTime2.getYear()) {
	    return true;
	} else {
	    return false;
	}
    }
}