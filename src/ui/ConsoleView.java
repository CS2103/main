//@@author A0121442X
package ui;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.joda.time.DateTime;

import application.Constants;
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
import javafx.util.Duration;
import logic.Task;
import parser.Parser;
import parser.TitleParser;

public class ConsoleView extends Pane {

	Label dateDisplay;
	Label clockDisplay;
	AutoCompleteTextField inputConsole;
	Label status;

	HBox listDisplay;
	AddTaskPreview addTaskPreview;
	EditTaskPreview editTaskPreview;
	HelpScreen helpScreen;

	VBox timedList;
	VBox floatingList;
	ScrollPane scrollPane;
	StackPane mainDisplay;

	Parser parser = new Parser();

	public ConsoleView() {

		dateDisplay = new Label();
		clockDisplay = new Label();

		timedList = new VBox();
		floatingList = new VBox();
		listDisplay = new HBox();
		scrollPane = new ScrollPane();

		inputConsole = new AutoCompleteTextField();
		status = new Label();

		dateDisplay.setId("dateDisplay");
		dateDisplay.setAlignment(Pos.CENTER_LEFT);
		dateDisplay.setMaxWidth(Double.MAX_VALUE);

		clockDisplay.setId("timeDisplay");
		clockDisplay.setAlignment(Pos.CENTER_RIGHT);
		clockDisplay.setMaxWidth(Double.MAX_VALUE);

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

		timedList.setId("timedList");

		floatingList.setId("floatingList");
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
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setBorder(null);
		scrollPane.setPickOnBounds(false);

		mainDisplay = new StackPane();

		addTaskPreview = new AddTaskPreview();
		editTaskPreview = new EditTaskPreview();
		helpScreen = new HelpScreen();
		mainDisplay.getChildren().addAll(editTaskPreview, addTaskPreview, scrollPane, helpScreen);

		inputConsole.setId("inputConsole");
		inputConsole.setEditable(true);

		status.setMaxWidth(Double.MAX_VALUE);
		status.setId("statusBar");

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

	public void updateEditTaskPreviewDetails(Task toEdit, String input) {

		editTaskPreview.clearAllDetails();
		editTaskPreview.detailsToShow(parser.getField(input));
		editTaskPreview.oldTitle.setText(toEdit.getTitle());
		editTaskPreview.newTitleField.setText(TitleParser.getEditTitle(input));

		editTaskPreview.oldStartTime.setText(showIfValidDate(toEdit.getStartingTime()));
		editTaskPreview.oldEndTime.setText(showIfValidDate(toEdit.getEndingTime()));

		editTaskPreview.newStartTimeField.setText(showIfValidDate(parser.getDateTime(input)));
		editTaskPreview.newEndTimeField.setText(showIfValidDate(parser.getDateTime(input)));

		editTaskPreview.oldDurationStartTime.setText(showIfValidDate(toEdit.getStartingTime()));
		editTaskPreview.oldDurationEndTime.setText(showIfValidDate(toEdit.getEndingTime()));
		editTaskPreview.newDurationStartTimeField.setText(showIfValidDate(parser.getStartDateTime(input)));
		editTaskPreview.newDurationEndTimeField.setText(showIfValidDate(parser.getEndDateTime(input)));
	}

	private String showIfValidDate(DateTime dateTime) {
		if (dateTime.getYear() != 0000) {
			return dateTime.toLocalDateTime().toString("HHmm dd MMM yyyy");
		} else {
			return Constants.NOT_APPLICABLE;
		}
	}

	public void showEditPopup() {
		editTaskPreview.toFront();
	}

	public void showAddPopup() {
		addTaskPreview.toFront();
	}

	public void showHelpPopup() {
		helpScreen.toFront();
	}

	public void showDefaultView() {
		addTaskPreview.toBack();
		editTaskPreview.toBack();
		helpScreen.toBack();
		scrollPane.toFront();
	}
}