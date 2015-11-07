//@@author A0121442X
package ui;

import application.Constants;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class EditTaskPreview extends StackPane {

	private GridPane taskPreviewLayout;

	private GridPane editTitleLayout;
	private GridPane editStartTimeLayout;
	private GridPane editEndTimeLayout;
	private GridPane editDurationLayout;

	private Label header, oldTitleLabel;

	Label oldTitle, oldStartTime, oldEndTime, oldRecurring, oldDurationStartTime, oldDurationEndTime;

	StackPane newDetailsLayout;

	Label newTitleField;
	Label newStartTimeField, newDurationStartTimeField;
	Label newEndTimeField, newDurationEndTimeField;

	ColumnConstraints columnConstraint = new ColumnConstraints(150);
	ColumnConstraints columnConstraint1 = new ColumnConstraints(400);

	public EditTaskPreview() {

		header = new Label("Edit task");
		header.setId("taskPreviewHeader");

		oldTitleLabel = new Label("Title:");
		oldTitleLabel.setId("taskPreviewLabel");

		oldTitle = new Label(Constants.COMMAND_INVALID);
		oldTitle.setId("taskPreviewDetails");

		oldStartTime = new Label();
		oldStartTime.setId("taskPreviewDetails");

		oldEndTime = new Label();
		oldEndTime.setId("taskPreviewDetails");

		oldDurationStartTime = new Label();
		oldDurationStartTime.setId("taskPreviewDetails");

		oldDurationEndTime = new Label();
		oldDurationEndTime.setId("taskPreviewDetails");

		newTitleField = new Label();
		newTitleField.setId("taskPreviewDetails");

		newStartTimeField = new Label();
		newStartTimeField.setId("taskPreviewDetails");

		newEndTimeField = new Label();
		newEndTimeField.setId("taskPreviewDetails");

		newDurationStartTimeField = new Label();
		newDurationStartTimeField.setId("taskPreviewDetails");

		newDurationEndTimeField = new Label();
		newDurationEndTimeField.setId("taskPreviewDetails");

		editTitleLayout = new GridPane();
		editStartTimeLayout = new GridPane();
		editEndTimeLayout = new GridPane();
		editDurationLayout = new GridPane();

		newDetailsLayout = new StackPane();

		taskPreviewLayout = new GridPane();
		GridPane.setConstraints(header, 0, 0, 2, 1);
		GridPane.setConstraints(oldTitleLabel, 0, 1);
		GridPane.setConstraints(oldTitle, 1, 1);
		GridPane.setConstraints(newDetailsLayout, 0, 2);

		setUpEditTitleLayout();
		setUpEditStartTimeLayout();
		setUpEditEndTimeLayout();
		setUpEditDurationLayout();

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
	}

	public void detailsToShow(String field) {
		newDetailsLayout.getChildren().clear();
		if (field.equalsIgnoreCase("title")) {
			newDetailsLayout.getChildren().addAll(editTitleLayout);
		} else if (field.equalsIgnoreCase("start")) {
			newDetailsLayout.getChildren().addAll(editStartTimeLayout);
		} else if (field.equalsIgnoreCase("end")) {
			newDetailsLayout.getChildren().addAll(editEndTimeLayout);
		} else if (field.equalsIgnoreCase("time")) {
			newDetailsLayout.getChildren().addAll(editDurationLayout);
		}
	}

	private void setUpEditTitleLayout() {
		Label newTitleLabel = new Label("New Title:");
		newTitleLabel.setId("taskPreviewLabel");
		editTitleLayout.add(newTitleLabel, 0, 0);
		editTitleLayout.add(newTitleField, 1, 0);
		editTitleLayout.getColumnConstraints().addAll(columnConstraint, columnConstraint1);
	}

	private void setUpEditStartTimeLayout() {
		Label newStartTimeLabel = new Label("New Start Time:");
		newStartTimeLabel.setId("taskPreviewLabel");
		Label oldStartTimeLabel = new Label("Current Start Time:");
		oldStartTimeLabel.setId("taskPreviewLabel");
		editStartTimeLayout.add(oldStartTimeLabel, 0, 0);
		editStartTimeLayout.add(oldStartTime, 1, 0);
		editStartTimeLayout.add(newStartTimeLabel, 0, 1);
		editStartTimeLayout.add(newStartTimeField, 1, 1);
		editStartTimeLayout.getColumnConstraints().addAll(columnConstraint, columnConstraint);
	}

	private void setUpEditEndTimeLayout() {
		Label newEndTimeLabel = new Label("New End Time:");
		newEndTimeLabel.setId("taskPreviewLabel");
		Label oldEndTimeLabel = new Label("Current End Time:");
		oldEndTimeLabel.setId("taskPreviewLabel");
		editEndTimeLayout.add(oldEndTimeLabel, 0, 0);
		editEndTimeLayout.add(oldEndTime, 1, 0);
		editEndTimeLayout.add(newEndTimeLabel, 0, 1);
		editEndTimeLayout.add(newEndTimeField, 1, 1);
		editEndTimeLayout.getColumnConstraints().addAll(columnConstraint, columnConstraint);
	}

	private void setUpEditDurationLayout() {
		Label newStartTimeLabel = new Label("New Start Time:");
		newStartTimeLabel.setId("taskPreviewLabel");
		Label oldStartTimeLabel = new Label("Current Start Time:");
		oldStartTimeLabel.setId("taskPreviewLabel");
		Label newEndTimeLabel = new Label("New End Time:");
		newEndTimeLabel.setId("taskPreviewLabel");
		Label oldEndTimeLabel = new Label("Current End Time:");
		oldEndTimeLabel.setId("taskPreviewLabel");

		editDurationLayout.add(oldStartTimeLabel, 0, 0);
		editDurationLayout.add(oldDurationStartTime, 1, 0);
		editDurationLayout.add(newStartTimeLabel, 2, 0);
		editDurationLayout.add(newDurationStartTimeField, 3, 0);
		editDurationLayout.add(oldEndTimeLabel, 0, 1);
		editDurationLayout.add(oldDurationEndTime, 1, 1);
		editDurationLayout.add(newEndTimeLabel, 2, 1);
		editDurationLayout.add(newDurationEndTimeField, 3, 1); //
		editDurationLayout.getColumnConstraints().addAll(columnConstraint, columnConstraint, columnConstraint,
				columnConstraint);
	}
}