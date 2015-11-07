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
import javafx.scene.text.FontPosture;
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

		title.setFont(Font.font("SansSerif", FontWeight.BOLD, 16));
		title.setPadding(new Insets(0, 0, 0, 5));
		title.setMaxWidth(Double.MAX_VALUE);
		if (isOverdue) {

			title.setTextFill(Color.PALEVIOLETRED);
			title.setFont(Font.font("SansSerif", FontWeight.BOLD, FontPosture.ITALIC, 16));
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
				? taskStartTime.toLocalDate().toString("EEE dd MMM")
				: taskStartTime.toLocalDate().toString("EEE dd MMM YYYY");
		String startTime = taskStartTime.toLocalTime().toString("HHmm");
		String endDate = (taskEndTime.getYear() == DateTime.now().getYear())
				? taskEndTime.toLocalDate().toString("EEE dd MMM")
				: taskEndTime.toLocalDate().toString("EEE dd MMM YYYY");
		String endTime = taskEndTime.toLocalTime().toString("HHmm");

		if (taskEndTime.isBefore(taskStartTime)) {
			endDate = "????";
			endTime = "????";
		}

		taskDuration = new Label();
		taskDuration.setId("duration");
		if (taskType.equals("deadline")) {
			taskDuration.setText("By [" + endDate + "] " + endTime + " hrs");
		} else if (taskType.equals("event")) {
			taskDuration.setText("[" + startDate + "] " + startTime + " hrs  -  [" + endDate + "] " + endTime + " hrs");
		} else if (taskType.equals(Constants.TYPE_RECUR)) {
			if (recurValue.equalsIgnoreCase("daily")) {
				taskDuration.setText("Repeat " + recurValue + " " + startTime + " hrs - " + endTime + " hrs");
			} else if (recurValue.equalsIgnoreCase("weekly")) {
				taskDuration.setText("Repeat " + recurValue + " " + startTime + " hrs - " + endTime + " hrs");
			} else if (recurValue.equalsIgnoreCase("monthly")) {
				taskDuration.setText("Repeat " + recurValue + " " + startTime + " hrs - " + endTime + " hrs");
			}
		}

		taskDuration.setTextFill(Color.LIGHTGRAY);
		taskDuration.setFont(Font.font("SansSerif", FontPosture.ITALIC, 11));
		taskDuration.setPadding(new Insets(0, 0, 0, 15));

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
}