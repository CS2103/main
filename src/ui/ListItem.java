package ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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
	private Label index;
	private Label title;
	private Label description;
	private Label startTime;
	private Label endTime;
	private Label isDone;
	private Label isOverDue;

	public ListItem(String taskTitle, String taskDescription, String taskStartTime, String taskEndTime, boolean isDone, int index) {
		this.index = new Label(String.valueOf(index));
		this.index.setFont(Font.font("Georgia",FontWeight.BOLD,25));

		title = new Label(taskTitle);
		title.setFont(Font.font("Georgia",FontWeight.BOLD,15));
		title.setPrefHeight(25);

		description = new Label(taskDescription);

		startTime  = new Label("Start: " + taskStartTime);
		startTime.setPrefWidth(300);

		endTime  = new Label("End: " + taskEndTime);
		//endTime.setPrefWidth();
		this.isDone = new Label();
		if (isDone) {
			this.isDone.setText("Done");
			this.isDone.setTextFill(Color.GREEN);
		} else {
			this.isDone.setText("Not Done");
			this.isDone.setTextFill(Color.RED);
		}


		HBox timeLayout = new HBox();
		timeLayout.getChildren().addAll(startTime, endTime);

		VBox detailsLayout = new VBox();
		detailsLayout.setPrefWidth(500);
		detailsLayout.getChildren().addAll(title, description, timeLayout);

		VBox statusLayout = new VBox();
		statusLayout.getChildren().addAll(this.index, this.isDone);

		HBox consoleLayout = new HBox();
		consoleLayout.getChildren().addAll(detailsLayout, statusLayout);
		this.setStyle("-fx-background-color: " + colorArray[index%6] + "; -fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");

		//this.setStyle("background: linear-gradient(45deg, rgba(153,218,255,1) 0%, rgba(0,128,128,1) 100%);-fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
		this.getChildren().add(consoleLayout);
		this.setPadding(new Insets(5, 5, 5, 5));

	}
	public String getTitle() {
		return this.title.getText();
	}
}