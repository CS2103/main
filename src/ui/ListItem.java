package ui;

import org.joda.time.DateTime;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class ListItem extends StackPane{

	private Circle statusIcon;
	private Label index;
	private Label title;
	//private Label isDone;
	private Label taskDuration;
	//private Label isOverDue;

	public ListItem(String taskTitle, DateTime taskStartTime, DateTime taskEndTime, String taskType, boolean isDone, boolean isOverdue, int taskIndex) {

		title = new Label(taskTitle);
		title.setId("title");
		title.setFont(Font.font("SansSerif",FontWeight.BOLD,16));
		title.setPadding(new Insets(0,0,0,5));
		title.setTextFill(Color.ANTIQUEWHITE);

		index = new Label(String.valueOf(taskIndex));
		index.setId("index");
		index.setFont(Font.font("SansSerif",FontWeight.LIGHT,22));
		index.setTextFill(Color.WHITESMOKE);
		index.setTextAlignment(TextAlignment.CENTER);
		index.setMinWidth(30);

		statusIcon = new Circle();
		statusIcon.setId("statusIcon");
		statusIcon.setRadius(5);

		if (isDone) {
			statusIcon.setFill(Color.GREENYELLOW);
		} else {
			statusIcon.setFill(Color.RED);
		}

		String startDate = (taskStartTime.getYear() == DateTime.now().getYear()) ? 
				taskStartTime.toLocalDate().toString("EEE dd MMM") : taskStartTime.toLocalDate().toString("EEE dd MMM YYYY");
		String startTime = taskStartTime.toLocalTime().toString("HHmm");
		String endDate = (taskEndTime.getYear() == DateTime.now().getYear()) ?
				taskEndTime.toLocalDate().toString("EEE dd MMM") : taskEndTime.toLocalDate().toString("EEE dd MMM YYYY");
		String endTime = taskEndTime.toLocalTime().toString("HHmm");

		taskDuration = new Label();
		if (taskType.equals("deadline")) {
			taskDuration.setText("By [" + endDate + "] " + endTime + " hrs");
		} else if (taskType.equals("event")){
			taskDuration.setText("[" + startDate + "] " + startTime + " hrs  -  [" + endDate + "] " + endTime + " hrs");
		}
		taskDuration.setTextFill(Color.LIGHTGRAY);
		taskDuration.setFont(Font.font("SansSerif", FontPosture.ITALIC, 11));
		taskDuration.setPadding(new Insets(0,0,0,15));

		HBox titleNstatus = new HBox();
		titleNstatus.setAlignment(Pos.CENTER_LEFT);
		titleNstatus.getChildren().addAll(statusIcon, title);

		HBox timeLayout = new HBox();
		timeLayout.getChildren().addAll(taskDuration);

		VBox detailsLayout = new VBox();
		detailsLayout.setPrefWidth(700);

		if (taskType.equals("task")) {
			detailsLayout.getChildren().addAll(titleNstatus);
		} else {
			detailsLayout.getChildren().addAll(titleNstatus, timeLayout);
		}

		detailsLayout.setPadding(new Insets(0,0,0,5));
		detailsLayout.setAlignment(Pos.CENTER);

		VBox statusLayout = new VBox();
		statusLayout.setPadding(new Insets(0,0,0,0));
		statusLayout.setAlignment(Pos.CENTER_RIGHT);
		statusLayout.setPrefWidth(30);
		statusLayout.getChildren().addAll(this.index);

		HBox consoleLayout = new HBox();
		consoleLayout.getChildren().addAll(detailsLayout, statusLayout);
		this.setId("listItem");
		this.getChildren().add(consoleLayout);
		//this.setPrefWidth(600);
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