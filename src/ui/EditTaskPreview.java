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

    protected Label oldTitle, oldStartTime, oldEndTime, oldRecurring, oldDurationStartTime, oldDurationEndTime;

    protected StackPane newDetailsLayout;

    protected Label newTitleField;
    protected Label newStartTimeField, newDurationStartTimeField;
    protected Label newEndTimeField, newDurationEndTimeField;

    private ColumnConstraints columnConstraint = new ColumnConstraints(150);
    private ColumnConstraints columnConstraint1 = new ColumnConstraints(400);

    public EditTaskPreview() {

	instantiateAndStyleComponents();
	setUpEditTitleLayout();
	setUpEditStartTimeLayout();
	setUpEditEndTimeLayout();
	setUpEditDurationLayout();
	setUpEditPopupLayout();

    }

    private void setUpEditPopupLayout() {
	taskPreviewLayout.getColumnConstraints().add(columnConstraint);
	taskPreviewLayout.getChildren().addAll(header, oldTitleLabel, oldTitle, newDetailsLayout);
	taskPreviewLayout.setId(Constants.CSS_POPUP_LAYOUT);
	this.getChildren().addAll(taskPreviewLayout);
	this.setId(Constants.CSS_POPUP_PANE);
	this.setPadding(new Insets(200, 50, 100, 50));
    }

    private void instantiateAndStyleComponents() {
	header = new Label(Constants.LABEL_EDIT_TASK);
	header.setId(Constants.CSS_TASK_PREVIEW_HEADER);

	oldTitleLabel = new Label(Constants.LABEL_TITLE);
	oldTitleLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);

	oldTitle = new Label(Constants.COMMAND_INVALID);
	oldTitle.setId(Constants.CSS_TASK_PREVIEW_DETAILS);

	oldStartTime = new Label();
	oldStartTime.setId(Constants.CSS_TASK_PREVIEW_DETAILS);

	oldEndTime = new Label();
	oldEndTime.setId(Constants.CSS_TASK_PREVIEW_DETAILS);

	oldDurationStartTime = new Label();
	oldDurationStartTime.setId(Constants.CSS_TASK_PREVIEW_DETAILS);

	oldDurationEndTime = new Label();
	oldDurationEndTime.setId(Constants.CSS_TASK_PREVIEW_DETAILS);

	newTitleField = new Label();
	newTitleField.setId(Constants.CSS_TASK_PREVIEW_DETAILS);

	newStartTimeField = new Label();
	newStartTimeField.setId(Constants.CSS_TASK_PREVIEW_DETAILS);

	newEndTimeField = new Label();
	newEndTimeField.setId(Constants.CSS_TASK_PREVIEW_DETAILS);

	newDurationStartTimeField = new Label();
	newDurationStartTimeField.setId(Constants.CSS_TASK_PREVIEW_DETAILS);

	newDurationEndTimeField = new Label();
	newDurationEndTimeField.setId(Constants.CSS_TASK_PREVIEW_DETAILS);

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
	Label newTitleLabel = new Label(Constants.LABEL_NEW_TITLE);
	newTitleLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);
	editTitleLayout.add(newTitleLabel, 0, 0);
	editTitleLayout.add(newTitleField, 1, 0);
	editTitleLayout.getColumnConstraints().addAll(columnConstraint, columnConstraint1);
    }

    private void setUpEditStartTimeLayout() {
	Label newStartTimeLabel = new Label(Constants.LABEL_NEW_START_TIME);
	newStartTimeLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);
	Label oldStartTimeLabel = new Label(Constants.LABEL_CURRENT_END_TIME);
	oldStartTimeLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);
	editStartTimeLayout.add(oldStartTimeLabel, 0, 0);
	editStartTimeLayout.add(oldStartTime, 1, 0);
	editStartTimeLayout.add(newStartTimeLabel, 0, 1);
	editStartTimeLayout.add(newStartTimeField, 1, 1);
	editStartTimeLayout.getColumnConstraints().addAll(columnConstraint, columnConstraint);
    }

    private void setUpEditEndTimeLayout() {
	Label newEndTimeLabel = new Label(Constants.LABEL_NEW_END_TIME);
	newEndTimeLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);
	Label oldEndTimeLabel = new Label(Constants.LABEL_CURRENT_END_TIME);
	oldEndTimeLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);
	editEndTimeLayout.add(oldEndTimeLabel, 0, 0);
	editEndTimeLayout.add(oldEndTime, 1, 0);
	editEndTimeLayout.add(newEndTimeLabel, 0, 1);
	editEndTimeLayout.add(newEndTimeField, 1, 1);
	editEndTimeLayout.getColumnConstraints().addAll(columnConstraint, columnConstraint);
    }

    private void setUpEditDurationLayout() {
	Label newStartTimeLabel = new Label(Constants.LABEL_NEW_START_TIME);
	newStartTimeLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);
	Label oldStartTimeLabel = new Label(Constants.LABEL_CURRENT_START_TIME);
	oldStartTimeLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);
	Label newEndTimeLabel = new Label(Constants.LABEL_NEW_END_TIME);
	newEndTimeLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);
	Label oldEndTimeLabel = new Label(Constants.LABEL_CURRENT_END_TIME);
	oldEndTimeLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);

	editDurationLayout.add(oldStartTimeLabel, 0, 0);
	editDurationLayout.add(oldDurationStartTime, 1, 0);
	editDurationLayout.add(newStartTimeLabel, 2, 0);
	editDurationLayout.add(newDurationStartTimeField, 3, 0);
	editDurationLayout.add(oldEndTimeLabel, 0, 1);
	editDurationLayout.add(oldDurationEndTime, 1, 1);
	editDurationLayout.add(newEndTimeLabel, 2, 1);
	editDurationLayout.add(newDurationEndTimeField, 3, 1);
	editDurationLayout.getColumnConstraints().addAll(columnConstraint, columnConstraint, columnConstraint,
		columnConstraint);
    }
}