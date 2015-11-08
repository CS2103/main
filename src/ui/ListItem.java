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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

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
			title.setId("titleOverdue");
		} else {
			if (taskType.equals("task")) {
				title.setId("titleTask");
			} else if (taskType.equals("event")) {
				title.setId("titleEvent");
			} else if (taskType.equals("deadline")) {
				title.setId("titleDeadline");
			}
		}

		index = new Label(String.valueOf(taskIndex));
		index.setId("index");
		index.setFont(Font.font("SansSerif", FontWeight.LIGHT, 22));
		index.setTextAlignment(TextAlignment.RIGHT);
		index.setAlignment(Pos.CENTER_RIGHT);
		index.setPadding(new Insets(0, 20, 0, 0));
		index.setMinWidth(50);

		statusIcon = new Circle();
		statusIcon.setId("statusIcon");
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
		taskDuration.setId("duration");
		if (taskType.equals("deadline")) {
			taskDuration.setText(String.format(Constants.FORMAT_DEADLINE, endDate, endTime));
		} else if (taskType.equals("event")) {
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
			taskDuration.setText(String.format(Constants.FORMAT_RECURRING, recurValue, startTime, endTime));
		}

		HBox titleNstatus = new HBox();
		titleNstatus.setAlignment(Pos.CENTER_LEFT);
		titleNstatus.getChildren().addAll(statusIcon, title);

		HBox timeLayout = new HBox();
		timeLayout.getChildren().addAll(taskDuration);

		VBox detailsLayout = new VBox();

		if (taskType.equals("task")) {
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