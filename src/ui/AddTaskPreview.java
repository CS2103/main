package ui;

import org.joda.time.DateTime;

import application.Constants;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AddTaskPreview extends StackPane {

	Label addTaskHeader;
	Label titleLabel;
	Label startLabel;
	Label endLabel;
	Label recurringLabel;

	Label tempTitle;
	Label tempStartTime;
	Label tempEndTime;
	Label tempRecurring;

	public AddTaskPreview(String title, DateTime startTime, DateTime endTime) {

		this.addTaskHeader = new Label("Add new task");
		this.addTaskHeader.setTextFill(Color.LIGHTGREEN);
		this.addTaskHeader.setPrefHeight(30);
		this.addTaskHeader.setFont(Font.font("SansSerif", FontWeight.BOLD, 16));
		this.titleLabel = new Label("Title:");
		this.titleLabel.setId("taskPreviewLabel");
		this.startLabel = new Label("Start:");
		this.startLabel.setId("taskPreviewLabel");
		this.endLabel = new Label("End:");
		this.endLabel.setId("taskPreviewLabel");
		this.recurringLabel = new Label("Repeat:");
		this.recurringLabel.setId("taskPreviewLabel");

		this.tempTitle = new Label();
		this.tempTitle.setId("taskPreviewDetails");
		this.tempStartTime = new Label();
		this.tempStartTime.setId("taskPreviewDetails");
		this.tempEndTime = new Label();
		this.tempEndTime.setId("taskPreviewDetails");
		this.tempRecurring = new Label();

		GridPane taskPreviewLayout = new GridPane();
		GridPane.setConstraints(this.addTaskHeader, 0, 0, 2, 1);
		GridPane.setConstraints(this.titleLabel, 0, 1);
		GridPane.setConstraints(this.startLabel, 0, 2);
		GridPane.setConstraints(this.endLabel, 0, 3);
		GridPane.setConstraints(this.recurringLabel, 0, 4);
		GridPane.setConstraints(this.tempTitle, 1, 1);
		GridPane.setConstraints(this.tempStartTime, 1, 2);
		GridPane.setConstraints(this.tempEndTime, 1, 3);
		GridPane.setConstraints(this.tempRecurring, 1, 4);

		ColumnConstraints columnConstraint = new ColumnConstraints(80);

		taskPreviewLayout.setPrefSize(700, 50);
		taskPreviewLayout.setPadding(new Insets(20, 20, 20, 20));
		taskPreviewLayout.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-background-radius:20");
		taskPreviewLayout.getColumnConstraints().add(columnConstraint);
		taskPreviewLayout.getChildren().addAll(this.addTaskHeader, this.titleLabel, this.startLabel, this.endLabel,
				this.recurringLabel, this.tempTitle, this.tempStartTime, this.tempEndTime, this.tempRecurring);

		this.getChildren().addAll(taskPreviewLayout);
		this.setPrefSize(700, 50);
		this.setStyle("-fx-background-color: rgba(255,255,255,0.3)");
		this.setPadding(new Insets(200, 50, 100, 50));
	}

	public void clearAllDetails() {
		this.tempTitle.setText(Constants.EMPTY_STRING);
		this.tempStartTime.setText(Constants.EMPTY_STRING);
		this.tempEndTime.setText(Constants.EMPTY_STRING);
		this.tempRecurring.setText(Constants.EMPTY_STRING);
	}
}