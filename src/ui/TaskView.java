/**
 * This class:
 * Creates and instantiates the individual components for
 * the task view
 * The task view is where the user can edit or confirm details
 * for tasks
 *
 */

package ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class TaskView {

	Pane taskPane;

	Label taskBar;
	Label titleLabel;
	Label startLabel;
	Label endLabel;
	Label priorityLabel;
	Label locationLabel;
	Label tagLabel;
	TextField titleField;
	TextField startField;
	TextField endField;
	TextField priorityField;
	TextField locationField;
	TextField tagField;

	public TaskView() {

		taskPane = new Pane();

		taskBar = new Label();
		taskBar.setText("Add New Task: ");
		taskBar.setPrefHeight(25);
		taskBar.setPrefWidth(400);
		taskBar.setStyle("-fx-background-color: lightgreen;");
		taskBar.setFont(Font.font("Georgia", 20));

		titleLabel = new Label();
		titleLabel.setText("Title");
		titleLabel.setPrefHeight(25);

		startLabel = new Label();
		startLabel.setText("Start");
		startLabel.setPrefHeight(25);

		endLabel = new Label();
		endLabel.setText("End");
		endLabel.setPrefHeight(25);

		priorityLabel = new Label();
		priorityLabel.setText("Priority");
		priorityLabel.setPrefHeight(25);

		locationLabel = new Label();
		locationLabel.setText("Location");
		locationLabel.setPrefHeight(25);

		tagLabel = new Label();
		tagLabel.setText("Tags");
		tagLabel.setPrefHeight(25);

		startField = new TextField();
		startField.setEditable(true);
		startField.setPrefWidth(200);
		startField.setPrefHeight(25);

		endField = new TextField();
		endField.setEditable(true);
		endField.setPrefWidth(200);
		endField.setPrefHeight(25);

		priorityField = new TextField();
		priorityField.setEditable(true);
		priorityField.setPrefWidth(200);
		priorityField.setPrefHeight(25);

		titleField = new TextField();
		titleField.setEditable(true);
		titleField.setPrefWidth(200);
		titleField.setPrefHeight(25);

		locationField = new TextField();
		locationField.setEditable(true);
		locationField.setPrefWidth(200);
		locationField.setPrefHeight(25);

		tagField = new TextField();
		tagField.setEditable(true);
		tagField.setPrefWidth(200);
		tagField.setPrefHeight(25);

		VBox taskLabels = new VBox();
		taskLabels.getChildren().addAll(titleLabel, startLabel, endLabel, priorityLabel);
		taskLabels.setPrefWidth(200);
		taskLabels.setSpacing(20);
		taskLabels.setPadding(new Insets(40, 20, 10, 20));

		VBox taskFields = new VBox();
		taskFields.setPrefWidth(200);
		taskFields.setPadding(new Insets(40, 20, 10, 20));
		taskFields.setSpacing(20);
		taskFields.getChildren().addAll(titleField, startField, endField, priorityField);

		HBox taskPanel = new HBox();
		taskPanel.getChildren().addAll(taskLabels, taskFields);

		VBox taskLayout = new VBox();
		taskLayout.getChildren().addAll(taskBar, taskPanel);
		taskPane.getChildren().add(taskLayout);
	}

}
