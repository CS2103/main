package ui;

import application.Constants;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ListItem extends StackPane{

	private String[] colorArray = {
			"linear-gradient( from 100.0% 0.0% to 100.0% 100.0%, rgb(255,179,102) 0.0, rgb(255,230,179) 40.0, rgb(255,179,102) 100.0);",
			"linear-gradient( from 100.0% 0.0% to 100.0% 100.0%, rgb(128,179,128) 0.0, rgb(204,255,204) 40.0, rgb(128,179,128) 100.0);",
			"linear-gradient( from 100.0% 0.0% to 100.0% 100.0%, rgb(255,188,0) 0.0, rgb(255,230,128) 40.0, rgb(255,188,0) 100.0);",
			"linear-gradient( from 100.0% 0.0% to 100.0% 100.0%, rgb(27,255,0) 0.0, rgb(180,255,155) 40.0, rgb(27,255,0) 100.0);",
			"linear-gradient( from 100.0% 0.0% to 100.0% 100.0%, rgb(255,102,102) 0.0, rgb(255,204,204) 40.0, rgb(255,128,128) 100.0);",
	"linear-gradient( from 100.0% 0.0% to 100.0% 100.0%, rgb(102,128,230) 0.0, rgb(204,230,255) 40.0, rgb(102,128,230) 100.0);"};

	private String color = "Color.BLACK";
	private Circle statusIcon;
	private Label index;
	private Label title;
	private Label description;
	private Label startTime;
	private Label endTime;
	private Label startDate;
	private Label endDate;
	private Label isDone;
	private Label duration;
	private Label isOverDue;
	private Label startText;
	private Label endText;

	public ListItem(String taskTitle, String taskDescription, String taskStartDate, String taskStartTime, String taskEndDate, String taskEndTime, boolean isDone, boolean isOverdue, int index) {
		this.index = new Label(String.valueOf(index));
		this.index.setFont(Font.font("Georgia",FontWeight.BOLD,25));
		this.index.setTextFill(Color.WHITE);

		title = new Label(taskTitle);
		title.setFont(Font.font("SansSerif",FontWeight.BOLD,16));
		title.setPadding(new Insets(0,0,0,5));
		title.setTextFill(Color.ANTIQUEWHITE);

		description = new Label(taskDescription);

		statusIcon = new Circle();
		statusIcon.setRadius(5);

		if (isDone) {
			this.statusIcon.setFill(Color.GREENYELLOW);
		} else {
			this.statusIcon.setFill(Color.RED);
		}

		startText = new Label(Constants.LABEL_START);
		startText.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		startText.setPrefWidth(40);

		endText = new Label(Constants.LABEL_END);
		endText.setFont(Font.font("SansSerif", FontWeight.BOLD, 12));
		endText.setPrefWidth(40);

		startDate = new Label(taskStartDate);
		startDate.setPrefWidth(60);
		startDate.setFont(Font.font("SansSerif", FontWeight.SEMI_BOLD, 12));

		startTime  = new Label(taskStartTime);
		startTime.setPrefWidth(180);
		startTime.setTextFill(Color.GREY);
		startTime.setFont(Font.font("SansSerif", FontWeight.SEMI_BOLD, 12));

		endDate = new Label(taskEndDate);
		endDate.setPrefWidth(60);
		endDate.setFont(Font.font("SansSerif", FontWeight.SEMI_BOLD, 12));

		endTime  = new Label(taskEndTime);
		endTime.setFont(Font.font("SansSerif", FontWeight.SEMI_BOLD, 12));
		endTime.setTextFill(Color.GREY);

		duration = new Label();
		duration.setText(taskStartTime + " - " + taskEndTime);
		duration.setTextFill(Color.LIGHTGRAY);
		duration.setPadding(new Insets(0,0,0,15));

		HBox titleNstatus = new HBox();
		titleNstatus.setAlignment(Pos.CENTER_LEFT);
		titleNstatus.getChildren().addAll(statusIcon, title);

		HBox timeLayout = new HBox();
		timeLayout.getChildren().addAll(duration);

		VBox detailsLayout = new VBox();
		detailsLayout.setPrefWidth(500);
		detailsLayout.getChildren().addAll(titleNstatus, timeLayout);

		VBox statusLayout = new VBox();
		statusLayout.getChildren().addAll(this.index);

		HBox consoleLayout = new HBox();
		consoleLayout.getChildren().addAll(detailsLayout, statusLayout);
		this.setId("listItem");
		this.getChildren().add(consoleLayout);
		this.setPrefWidth(600);
		this.setPadding(new Insets(5, 5, 5, 10));

	}
	public String getTitle() {
		return this.title.getText();
	}
	public String getIsOverdue() {
		return this.title.getText();
	}
}