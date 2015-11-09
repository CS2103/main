//@@author A0121442X
package ui;

import application.Constants;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class AddTaskPreview extends StackPane {

    protected Label header;
    protected Label titleLabel;
    protected Label startLabel;
    protected Label endLabel;
    protected Label recurringLabel;

    protected Label tempTitle;
    protected Label tempStartTime;
    protected Label tempEndTime;
    protected Label tempRecurring;

    public AddTaskPreview() {
	instantiateAndStyleComponents();
	setUpAddPopupLayout();
    }

    private void instantiateAndStyleComponents() {
	header = new Label(Constants.LABEL_ADD_TASK);
	header.setId(Constants.CSS_TASK_PREVIEW_HEADER);

	titleLabel = new Label(Constants.LABEL_TITLE);
	titleLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);
	startLabel = new Label(Constants.LABEL_START);
	startLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);
	endLabel = new Label(Constants.LABEL_END);
	endLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);
	recurringLabel = new Label(Constants.LABEL_RECURRING);
	recurringLabel.setId(Constants.CSS_TASK_PREVIEW_LABEL);

	tempTitle = new Label();
	tempTitle.setId(Constants.CSS_TASK_PREVIEW_DETAILS);
	tempStartTime = new Label();
	tempStartTime.setId(Constants.CSS_TASK_PREVIEW_DETAILS);
	tempEndTime = new Label();
	tempEndTime.setId(Constants.CSS_TASK_PREVIEW_DETAILS);
	tempRecurring = new Label();
	tempRecurring.setId(Constants.CSS_TASK_PREVIEW_DETAILS);
    }

    private void setUpAddPopupLayout() {
	GridPane taskPreviewLayout = new GridPane();
	GridPane.setConstraints(header, 0, 0, 2, 1);
	GridPane.setConstraints(titleLabel, 0, 1);
	GridPane.setConstraints(startLabel, 0, 2);
	GridPane.setConstraints(endLabel, 0, 3);
	GridPane.setConstraints(recurringLabel, 0, 4);
	GridPane.setConstraints(tempTitle, 1, 1);
	GridPane.setConstraints(tempStartTime, 1, 2);
	GridPane.setConstraints(tempEndTime, 1, 3);
	GridPane.setConstraints(tempRecurring, 1, 4);

	taskPreviewLayout.getChildren().addAll(header, titleLabel, startLabel, endLabel, recurringLabel, tempTitle,
		tempStartTime, tempEndTime, tempRecurring);
	taskPreviewLayout.setId(Constants.CSS_POPUP_LAYOUT);

	this.getChildren().addAll(taskPreviewLayout);
	this.setId(Constants.CSS_POPUP_PANE);
	this.setPadding(new Insets(200, 50, 100, 50));
    }

    public void clearAllDetails() {
	this.tempTitle.setText(Constants.EMPTY_STRING);
	this.tempStartTime.setText(Constants.EMPTY_STRING);
	this.tempEndTime.setText(Constants.EMPTY_STRING);
	this.tempRecurring.setText(Constants.EMPTY_STRING);
    }
}