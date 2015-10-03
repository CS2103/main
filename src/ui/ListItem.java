package ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ListItem extends StackPane{
	private static int colorIndex = 0;
	//private Color[] colorArray = {Color.CORNFLOWERBLUE, Color.GOLD, Color.PINK, Color.GREENYELLOW, Color.KHAKI};

	private String[] colorArray = {"rgb(102,178,255)","rgb(255,153,51)","rgb(255,153,153)","rgb(153,255,51)","rgb(255,51,255)"};
	private Label index;
	private Label title;
	private Label description;
	private Label startTime;
	private Label endTime;
	private Label isDone;
	private Label isOverDue;

	public ListItem(String taskTitle, String taskDescription, String taskStartTime, String taskEndTime, int index) {
		this.index = new Label(String.valueOf(index));
		this.index.setFont(Font.font("Georgia",FontWeight.BOLD,15));

		title = new Label(taskTitle);
		title.setFont(Font.font("Georgia",FontWeight.BOLD,15));
		title.setPrefHeight(25);

		description = new Label(taskDescription);

		startTime  = new Label("Start: " + taskStartTime);
		startTime.setPrefWidth(300);

		endTime  = new Label("End: " + taskEndTime);
		//endTime.setPrefWidth();
		isDone = new Label();

		HBox consoleLayout = new HBox();
		VBox detailsLayout = new VBox();
		detailsLayout.setPrefWidth(550);
		HBox timeLayout = new HBox();
		timeLayout.getChildren().addAll(startTime, endTime);
		detailsLayout.getChildren().addAll(title, description, timeLayout);
		consoleLayout.getChildren().addAll(detailsLayout, this.index);
		this.setStyle("-fx-background-color: " + colorArray[colorIndex%5] + "; -fx-background-radius: 10px; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
		this.getChildren().add(consoleLayout);
		ListItem.colorIndex++;
		this.setPadding(new Insets(5, 5, 5, 5));

	}
}