package ui;

import org.joda.time.DateTime;

import application.Constants;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

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

		header = new Label("Edit task");
		header.setId("taskPreviewHeader");

		oldTitleLabel = new Label("Title:");
		oldTitleLabel.setId("taskPreviewLabel");

		oldStartTimeLabel = new Label("Current Start Time:");
		oldStartTimeLabel.setId("taskPreviewLabel");

		oldEndTimeLabel = new Label("Current End Time:");
		oldEndTimeLabel.setId("taskPreviewLabel");

		oldRecurringLabel = new Label("Currently Repeating:");
		oldRecurringLabel.setId("taskPreviewLabel");

		oldTitle = new Label(Constants.COMMAND_INVALID);
		oldTitle.setId("taskPreviewDetails");

		oldStartTime = new Label();
		oldStartTime.setId("taskPreviewDetails");

		oldEndTime = new Label();
		oldEndTime.setId("taskPreviewDetails");

		oldRecurring = new Label();
		oldRecurring.setId("taskPreviewDetails");

		newTitleLabel = new Label("New Title: ");
		newTitleLabel.setId("taskPreviewLabel");

		newStartTimeLabel = new Label("New Start Time: ");
		newStartTimeLabel.setId("taskPreviewLabel");

		newEndTimeLabel = new Label("New End Time: ");
		newEndTimeLabel.setId("taskPreviewLabel");

		newRecurringLabel = new Label("New Repeating: ");
		newRecurringLabel.setId("taskPreviewLabel");

		newTitleField = new Label();
		newTitleField.setId("taskPreviewDetails");

		newStartTimeField = new Label();
		newStartTimeField.setId("taskPreviewDetails");

		newEndTimeField = new Label();
		newEndTimeField.setId("taskPreviewDetails");

		newRecurringField = new Label();
		newRecurringField.setId("taskPreviewDetails");

		ColumnConstraints columnConstraint = new ColumnConstraints(150);

		editTitleLayout = new GridPane();
		editTitleLayout.add(newTitleLabel, 0, 0);
		editTitleLayout.add(newTitleField, 1, 0);
		editTitleLayout.setVisible(false);
		editTitleLayout.getColumnConstraints().addAll(columnConstraint, columnConstraint);

		editStartTimeLayout = new GridPane();
		editStartTimeLayout.add(oldStartTimeLabel, 0, 0);
		editStartTimeLayout.add(oldStartTime, 1, 0);
		editStartTimeLayout.add(newStartTimeLabel, 0, 1);
		editStartTimeLayout.add(newStartTimeField, 1, 1);
		editStartTimeLayout.setVisible(false);
		editStartTimeLayout.getColumnConstraints().add(columnConstraint);

		editEndTimeLayout = new GridPane();
		editEndTimeLayout.add(oldEndTimeLabel, 0, 0);
		editEndTimeLayout.add(oldEndTime, 1, 0);
		editEndTimeLayout.add(newEndTimeLabel, 0, 1);
		editEndTimeLayout.add(newEndTimeField, 1, 1);
		editEndTimeLayout.setVisible(false);
		editEndTimeLayout.getColumnConstraints().addAll(columnConstraint, columnConstraint);

		newDetailsLayout = new StackPane(editTitleLayout, editStartTimeLayout, editEndTimeLayout);

		taskPreviewLayout = new GridPane();
		GridPane.setConstraints(header, 0, 0, 2, 1);
		GridPane.setConstraints(oldTitleLabel, 0, 1);
		GridPane.setConstraints(oldTitle, 1, 1);
		GridPane.setConstraints(newDetailsLayout, 0, 2);

		taskPreviewLayout.setPrefSize(700, 50);
		taskPreviewLayout.setPadding(new Insets(20, 20, 20, 20));
		taskPreviewLayout.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius:20");

		taskPreviewLayout.getColumnConstraints().add(columnConstraint);
		taskPreviewLayout.getChildren().addAll(header, oldTitleLabel, oldTitle, newDetailsLayout);

		getChildren().addAll(taskPreviewLayout);
		setPrefSize(700, 50);
		setStyle("-fx-background-color: rgba(255,255,255,0.5)");
		setPadding(new Insets(200, 50, 100, 50));
	}

	public void clearAllDetails() {
		newTitleField.setText(Constants.EMPTY_STRING);
		newRecurringField.setText(Constants.EMPTY_STRING);
	}

	public void detailsToShow(String taskType, String field) {
		if (field.equalsIgnoreCase("title")) {
			newDetailsLayout.getChildren().get(0).setVisible(true);
			newDetailsLayout.getChildren().get(1).setVisible(false);
			newDetailsLayout.getChildren().get(2).setVisible(false);
		} else if (field.equalsIgnoreCase("start")) {
			newDetailsLayout.getChildren().get(0).setVisible(false);
			newDetailsLayout.getChildren().get(1).setVisible(true);
			newDetailsLayout.getChildren().get(2).setVisible(false);
		} else if (field.equalsIgnoreCase("end")) {
			newDetailsLayout.getChildren().get(0).setVisible(false);
			newDetailsLayout.getChildren().get(1).setVisible(false);
			newDetailsLayout.getChildren().get(2).setVisible(true);
		}
	}

	public void formatNewEntry(String field, String newEntry) {
		if (field.equalsIgnoreCase("title")) {

		} else if (field.equalsIgnoreCase("start") || field.equalsIgnoreCase("end")) {

		}

	}
}
