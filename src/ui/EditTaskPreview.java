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

public class EditTaskPreview extends StackPane {

	private GridPane taskPreviewLayout;

	GridPane editTitleLayout;
	GridPane editStartTimeLayout;
	GridPane editEndTimeLayout;

	Label header;
	Label oldTitleLabel;
	Label oldStartTimeLabel;
	Label oldEndTimeLabel;
	Label oldRecurringLabel;

	Label oldTitle;
	Label oldStartTime;
	Label oldEndTime;
	Label oldRecurring;

	Label newTitleLabel;
	Label newStartTimeLabel;
	Label newEndTimeLabel;
	Label newRecurringLabel;

	StackPane newDetailsLayout;
	StackPane newField;

	Label newTitleField;
	Label newStartTimeField;
	Label newEndTimeField;
	Label newRecurringField;

	public EditTaskPreview(String title, DateTime startTime, DateTime endTime, String taskType) {

		this.header = new Label("Edit task");
		this.header.setTextFill(Color.LIGHTGREEN);
		this.header.setPrefHeight(30);
		this.header.setFont(Font.font("SansSerif", FontWeight.BOLD, 16));

		this.oldTitleLabel = new Label("Title:");
		this.oldTitleLabel.setId("taskPreviewLabel");
		this.oldStartTimeLabel = new Label("Current Start Time:");
		this.oldStartTimeLabel.setId("taskPreviewLabel");
		this.oldEndTimeLabel = new Label("Current End Time:");
		this.oldEndTimeLabel.setId("taskPreviewLabel");
		this.oldRecurringLabel = new Label("Currently Repeating:");
		this.oldRecurringLabel.setId("taskPreviewLabel");

		this.oldTitle = new Label("adf");
		this.oldTitle.setId("taskPreviewDetails");
		this.oldStartTime = new Label();
		this.oldStartTime.setId("taskPreviewDetails");
		this.oldEndTime = new Label();
		this.oldEndTime.setId("taskPreviewDetails");
		this.oldRecurring = new Label();
		this.oldRecurring.setId("taskPreviewDetails");

		this.newTitleLabel = new Label("New Title: ");
		this.newTitleLabel.setId("taskPreviewLabel");
		this.newStartTimeLabel = new Label("New Start Time: ");
		this.newStartTimeLabel.setId("taskPreviewLabel");
		this.newEndTimeLabel = new Label("New End Time: ");
		this.newEndTimeLabel.setId("taskPreviewLabel");
		this.newRecurringLabel = new Label("New Repeating: ");
		this.newRecurringLabel.setId("taskPreviewLabel");

		this.newTitleField = new Label();
		this.newTitleField.setId("taskPreviewDetails");
		this.newStartTimeField = new Label();
		this.newStartTimeField.setId("taskPreviewDetails");
		this.newEndTimeField = new Label();
		this.newEndTimeField.setId("taskPreviewDetails");

		this.newRecurringField = new Label();
		this.newRecurringField.setId("taskPreviewDetails");

		ColumnConstraints columnConstraint = new ColumnConstraints(150);

		this.editTitleLayout = new GridPane();
		this.editTitleLayout.add(this.newTitleLabel, 0, 0);
		this.editTitleLayout.add(this.newTitleField, 1, 0);
		this.editTitleLayout.setVisible(false);
		this.editTitleLayout.getColumnConstraints().addAll(columnConstraint, columnConstraint);

		this.editStartTimeLayout = new GridPane();
		this.editStartTimeLayout.add(this.oldStartTimeLabel, 0, 0);
		this.editStartTimeLayout.add(this.oldStartTime, 1, 0);
		this.editStartTimeLayout.add(this.newStartTimeLabel, 0, 1);
		this.editStartTimeLayout.add(this.newStartTimeField, 1, 1);
		this.editStartTimeLayout.setVisible(false);
		this.editStartTimeLayout.getColumnConstraints().add(columnConstraint);

		this.editEndTimeLayout = new GridPane();
		this.editEndTimeLayout.add(this.oldEndTimeLabel, 0, 0);
		this.editEndTimeLayout.add(this.oldEndTime, 1, 0);
		this.editEndTimeLayout.add(this.newEndTimeLabel, 0, 1);
		this.editEndTimeLayout.add(this.newEndTimeField, 1, 1);
		this.editEndTimeLayout.setVisible(false);
		this.editEndTimeLayout.getColumnConstraints().addAll(columnConstraint, columnConstraint);

		this.newDetailsLayout = new StackPane(this.editTitleLayout, this.editStartTimeLayout, this.editEndTimeLayout);

		this.taskPreviewLayout = new GridPane();
		GridPane.setConstraints(this.header, 0, 0, 2, 1);
		GridPane.setConstraints(this.oldTitleLabel, 0, 1);
		GridPane.setConstraints(this.oldTitle, 1, 1);
		GridPane.setConstraints(this.newDetailsLayout, 0, 2);

		this.taskPreviewLayout.setPrefSize(700, 50);
		this.taskPreviewLayout.setPadding(new Insets(20, 20, 20, 20));
		this.taskPreviewLayout.setStyle("-fx-background-color: rgba(0,0,0,0.3); -fx-background-radius:20");
		//
		this.taskPreviewLayout.getColumnConstraints().add(columnConstraint);
		this.taskPreviewLayout.getChildren().addAll(this.header, this.oldTitleLabel, this.oldTitle,
				this.newDetailsLayout);

		// taskPreviewLayout.getChildren().addAll(header, titleLabel,
		// startLabel, endLabel, recurringLabel, newTitle, newStartTime,
		// newEndTime, newRecurring);

		this.getChildren().addAll(this.taskPreviewLayout);
		this.setPrefSize(700, 50);
		this.setStyle("-fx-background-color: rgba(255,255,255,0.3)");
		this.setPadding(new Insets(200, 50, 100, 50));
	}

	public void clearAllDetails() {
		this.newTitleField.setText(Constants.EMPTY_STRING);
		this.newRecurringField.setText(Constants.EMPTY_STRING);
	}

	public void detailsToShow(String taskType, String field) {
		if (field.equalsIgnoreCase("title")) {
			this.newDetailsLayout.getChildren().get(0).setVisible(true);
			this.newDetailsLayout.getChildren().get(1).setVisible(false);
			this.newDetailsLayout.getChildren().get(2).setVisible(false);
		} else if (field.equalsIgnoreCase("start")) {
			this.newDetailsLayout.getChildren().get(0).setVisible(false);
			this.newDetailsLayout.getChildren().get(1).setVisible(true);
			this.newDetailsLayout.getChildren().get(2).setVisible(false);
		} else if (field.equalsIgnoreCase("end")) {
			this.newDetailsLayout.getChildren().get(0).setVisible(false);
			this.newDetailsLayout.getChildren().get(1).setVisible(false);
			this.newDetailsLayout.getChildren().get(2).setVisible(true);
		}
	}

	public void formatNewEntry(String field, String newEntry) {
		if (field.equalsIgnoreCase("title")) {

		} else if (field.equalsIgnoreCase("start") || field.equalsIgnoreCase("end")) {

		}

	}
}
