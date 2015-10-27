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

		dateDisplay = new Label();
		clockDisplay = new Label();

		timedList = new VBox();
		floatingList = new VBox();
		listDisplay = new HBox();
		scrollPane = new ScrollPane();

		inputConsole = new AutoCompleteTextField();
		status = new Label();

		dateDisplay.setId("timeDisplay");
		dateDisplay.setAlignment(Pos.CENTER_LEFT);
		dateDisplay.setMaxWidth(Double.MAX_VALUE);
		dateDisplay.setPadding(new Insets(0, 0, 0, 20));
		dateDisplay.setTextFill(Color.WHITE);

		clockDisplay.setId("timeDisplay");
		clockDisplay.setAlignment(Pos.CENTER_RIGHT);
		clockDisplay.setMaxWidth(Double.MAX_VALUE);
		clockDisplay.setPadding(new Insets(0, 20, 0, 0));
		clockDisplay.setTextFill(Color.WHITE);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Timeline timeline = new Timeline();
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				dateDisplay.setText(Calendar.getInstance().getTime().toString().split(" ")[0].trim() + ", "
						+ dateFormat.format(Calendar.getInstance().getTime()));
				clockDisplay.setText(timeFormat.format(Calendar.getInstance().getTime()));
			}
		};
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), onFinished));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		timedList.setStyle(
				"-fx-background-color:linear-gradient( from 100.0% 0.0% to 100.0% 100.0%, rgb(51,51,51) 0.0, rgb(179,179,179) 40.0, rgb(51,51,51) 100.0)");
		timedList.setFocusTraversable(false);
		timedList.setFillWidth(true);

		floatingList.setStyle(
				"-fx-background-color: linear-gradient( from 100.0% 0.0% to 100.0% 100.0%, rgb(46,50,68) 0.0, rgb(51,51,51) 40.0, rgb(39,41,54) 100.0)");
		floatingList.setFocusTraversable(false);
		floatingList.setFillWidth(true);

		HBox.setHgrow(floatingList, Priority.ALWAYS);
		HBox.setHgrow(timedList, Priority.ALWAYS);

		listDisplay.setSpacing(0);
		listDisplay.setFillHeight(true);
		listDisplay.setPrefWidth(700);
		listDisplay.setPadding(new Insets(0, 0, 0, 0));

		scrollPane.setId("scrollPane");
		scrollPane.setContent(listDisplay);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setMaxHeight(470);
		scrollPane.setMinHeight(470);
		scrollPane.setPrefWidth(700);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setFocusTraversable(false);
		scrollPane.setBorder(null);
		scrollPane.setPickOnBounds(false);

		mainDisplay = new StackPane();

		addTaskPreview = new AddTaskPreview("", DateTime.now(), DateTime.now());
		editTaskPreview = new EditTaskPreview("", DateTime.now(), DateTime.now(), "");
		mainDisplay.getChildren().addAll(editTaskPreview, addTaskPreview, scrollPane);

		inputConsole.setId("inputConsole");
		inputConsole.setEditable(true);
		inputConsole.setFocusTraversable(true);

		status.setMaxWidth(Double.MAX_VALUE);
		status.setId("statusBar");
		status.setAlignment(Pos.CENTER_LEFT);

		HBox dateTime = new HBox();
		HBox.setHgrow(clockDisplay, Priority.ALWAYS);
		dateTime.setMaxWidth(Double.MAX_VALUE);
		dateTime.setSpacing(0);
		dateTime.setPadding(new Insets(0, 0, 0, 0));
		dateTime.getChildren().addAll(dateDisplay, clockDisplay);

		VBox consoleLayout = new VBox();
		VBox.setVgrow(consoleLayout, Priority.ALWAYS);
		consoleLayout.getChildren().addAll(dateTime, mainDisplay, inputConsole, status);
		this.getChildren().add(consoleLayout);
	}

	public void updateAddTaskPreviewDetails(String title, DateTime startTime, DateTime endTime, String recurring) {
		addTaskPreview.clearAllDetails();
		addTaskPreview.tempTitle.setText(title);
		addTaskPreview.tempStartTime.setText(this.showIfValidDate(startTime));
		addTaskPreview.tempEndTime.setText(this.showIfValidDate(endTime));
	}

	public void updateEditTaskPreviewDetails(String oldTitle, DateTime startTime, DateTime endTime, String field,
			String newTitle, DateTime newDateTime) {
		editTaskPreview.clearAllDetails();
		editTaskPreview.oldTitle.setText(oldTitle);
		editTaskPreview.oldStartTime.setText(showIfValidDate(startTime));
		editTaskPreview.oldEndTime.setText(showIfValidDate(endTime));
		editTaskPreview.detailsToShow("", field);
		editTaskPreview.newTitleField.setText(newTitle);
		editTaskPreview.newStartTimeField.setText(showIfValidDate(newDateTime));
		editTaskPreview.newEndTimeField.setText(showIfValidDate(newDateTime));
	}

	private String showIfValidDate(DateTime dateTime) {
		if (dateTime.getYear() != 0000) {
			return dateTime.toLocalDateTime().toString("HHmm dd MMM yyyy");
		} else {
			return "N/A";
		}
	}
}