package ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ListItem extends StackPane{

	private String[] colorArray = {"rgb(102,178,255)","rgb(255,153,51)","rgb(255,153,153)","rgb(153,255,51)","rgb(255,255,51)","rgb(255,51,255)"};
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
		} else {
			this.isDone.setText("Not Done");
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
		this.getChildren().add(consoleLayout);
		this.setPadding(new Insets(5, 5, 5, 5));

	}
}