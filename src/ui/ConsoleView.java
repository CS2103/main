/**
 * This class:
 * Creates and instantiates the individual components for
 * the console view
 * The console view is where the user inputs commands
 * The console textfield extends an autocomplete feature
 */

package ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.joda.time.DateTime;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class ConsoleView extends Pane {

	Label dateDisplay;
	Label clockDisplay;
	AutoCompleteTextField inputConsole;
	Label status;

	HBox listDisplay;
	AddTaskPreview addTaskPreview;
	EditTaskPreview editTaskPreview;

	VBox timedList;
	VBox floatingList;
	ScrollPane scrollPane;
	StackPane mainDisplay;

	public ConsoleView() {

		this.dateDisplay = new Label();
		this.clockDisplay = new Label();

		this.timedList = new VBox();
		this.floatingList = new VBox();
		this.listDisplay = new HBox();
		this.scrollPane = new ScrollPane();

		this.inputConsole = new AutoCompleteTextField();
		this.status = new Label();

		this.dateDisplay.setId("timeDisplay");
		this.dateDisplay.setFont(new Font("SansSerif", 30));
		this.dateDisplay.setAlignment(Pos.CENTER_LEFT);
		this.dateDisplay.setMaxWidth(Double.MAX_VALUE);
		this.dateDisplay.setPadding(new Insets(0, 0, 0, 20));
		this.dateDisplay.setTextFill(Color.WHITE);

		this.clockDisplay.setId("timeDisplay");
		this.clockDisplay.setFont(new Font("SansSerif", 30));
		this.clockDisplay.setAlignment(Pos.CENTER_RIGHT);
		this.clockDisplay.setMaxWidth(Double.MAX_VALUE);
		this.clockDisplay.setPadding(new Insets(0, 20, 0, 0));
		this.clockDisplay.setTextFill(Color.WHITE);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Timeline timeline = new Timeline();
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				ConsoleView.this.dateDisplay.setText(Calendar.getInstance().getTime().toString().split(" ")[0].trim()
						+ ", " + dateFormat.format(Calendar.getInstance().getTime()));
				ConsoleView.this.clockDisplay.setText(timeFormat.format(Calendar.getInstance().getTime()));
			}
		};
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), onFinished));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		this.timedList.setStyle(
				"-fx-background-color:linear-gradient( from 100.0% 0.0% to 100.0% 100.0%, rgb(51,51,51) 0.0, rgb(179,179,179) 40.0, rgb(51,51,51) 100.0)");
		this.timedList.setFocusTraversable(false);
		this.timedList.setFillWidth(true);

		this.floatingList.setStyle(
				"-fx-background-color: linear-gradient( from 100.0% 0.0% to 100.0% 100.0%, rgb(46,50,68) 0.0, rgb(51,51,51) 40.0, rgb(39,41,54) 100.0)");
		this.floatingList.setFocusTraversable(false);
		this.floatingList.setFillWidth(true);
		HBox.setHgrow(this.floatingList, Priority.ALWAYS);
		HBox.setHgrow(this.timedList, Priority.ALWAYS);

		this.listDisplay.setSpacing(0);
		this.listDisplay.setFillHeight(true);
		this.listDisplay.setPrefWidth(700);
		this.listDisplay.setPadding(new Insets(0, 0, 0, 0));
		// listDisplay.getChildren().addAll(timedList, floatingList);

		this.scrollPane.setId("scrollPane");
		this.scrollPane.setContent(this.listDisplay);
		this.scrollPane.setFitToHeight(true);
		this.scrollPane.setFitToWidth(true);
		this.scrollPane.setMaxHeight(470);
		this.scrollPane.setMinHeight(470);
		this.scrollPane.setPrefWidth(700);
		this.scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		this.scrollPane.setFocusTraversable(false);
		this.scrollPane.setBorder(null);
		this.scrollPane.setPickOnBounds(false);

		this.mainDisplay = new StackPane();
		this.addTaskPreview = new AddTaskPreview("", DateTime.now(), DateTime.now());
		this.editTaskPreview = new EditTaskPreview("", DateTime.now(), DateTime.now(), "");

		this.mainDisplay.getChildren().addAll(this.editTaskPreview, this.addTaskPreview, this.scrollPane);

		this.inputConsole.setId("inputConsole");
		this.inputConsole.setEditable(true);
		this.inputConsole.setPrefHeight(25);
		this.inputConsole.setPrefWidth(700);
		this.inputConsole
				.setStyle("-fx-background-color: rgba(255,255,255, 0.7);-fx-text-inner-color: black;-fx-font-size:15");
		this.inputConsole.setFocusTraversable(true);

		this.status.setMaxWidth(Double.MAX_VALUE);
		this.status.setPrefHeight(30);
		this.status.setId("statusBar");
		this.status.setAlignment(Pos.CENTER_LEFT);

		HBox dateTime = new HBox();
		HBox.setHgrow(this.clockDisplay, Priority.ALWAYS);
		dateTime.setMaxWidth(Double.MAX_VALUE);
		dateTime.setSpacing(0);
		dateTime.setPadding(new Insets(0, 0, 0, 0));
		dateTime.getChildren().addAll(this.dateDisplay, this.clockDisplay);

		VBox consoleLayout = new VBox();
		VBox.setVgrow(consoleLayout, Priority.ALWAYS);
		consoleLayout.getChildren().addAll(dateTime, this.mainDisplay, this.inputConsole, this.status);
		this.getChildren().add(consoleLayout);
	}

	public void updateAddTaskPreviewDetails(String title, DateTime startTime, DateTime endTime, String recurring) {
		this.addTaskPreview.clearAllDetails();
		this.addTaskPreview.tempTitle.setText(title);
		this.addTaskPreview.tempStartTime.setText(this.showIfValidDate(startTime));
		this.addTaskPreview.tempEndTime.setText(this.showIfValidDate(endTime));
	}

	public void updateEditTaskPreviewDetails(String oldTitle, DateTime startTime, DateTime endTime, String field,
			String newTitle, DateTime newDateTime) {
		this.editTaskPreview.clearAllDetails();
		this.editTaskPreview.oldTitle.setText(oldTitle);
		this.editTaskPreview.oldStartTime.setText(this.showIfValidDate(startTime));
		this.editTaskPreview.oldEndTime.setText(this.showIfValidDate(endTime));

		this.editTaskPreview.detailsToShow("", field);

		this.editTaskPreview.newTitleField.setText(newTitle);
		this.editTaskPreview.newStartTimeField.setText(this.showIfValidDate(newDateTime));
		this.editTaskPreview.newEndTimeField.setText(this.showIfValidDate(newDateTime));
	}

	private String showIfValidDate(DateTime dateTime) {
		if (dateTime.getYear() != 0000) {
			return dateTime.toLocalDateTime().toString("HHmm dd MMM yyyy");
		} else {
			return "N/A";
		}
	}
}