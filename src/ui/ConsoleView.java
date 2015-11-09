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

	protected Label dateDisplay;
	protected Label clockDisplay;
	protected AutoCompleteTextField inputConsole;
	protected Label status;

	protected HBox listDisplay;
	protected AddTaskPreview addTaskPreview;
	protected EditTaskPreview editTaskPreview;
	protected HelpScreen helpScreen;

	protected VBox timedList;
	protected VBox floatingList;
	protected ScrollPane scrollPane;
	private StackPane mainDisplay;

	private Parser parser = new Parser();

	public ConsoleView() {
		instantiateAndStyleComponents();
		setUpDynamicClock();
		layoutComponents();
	}

	private void layoutComponents() {
		mainDisplay = new StackPane(editTaskPreview, addTaskPreview, scrollPane, helpScreen);

		HBox dateTime = new HBox(dateDisplay, clockDisplay);
		HBox.setHgrow(clockDisplay, Priority.ALWAYS);
		dateTime.setMaxWidth(Double.MAX_VALUE);

		VBox consoleLayout = new VBox(dateTime, mainDisplay, inputConsole, status);
		VBox.setVgrow(consoleLayout, Priority.ALWAYS);
		this.getChildren().add(consoleLayout);
	}

	private void setUpDynamicClock() {
		dateDisplay = new Label();
		dateDisplay.setId("dateDisplay");
		dateDisplay.setAlignment(Pos.CENTER_LEFT);
		dateDisplay.setMaxWidth(Double.MAX_VALUE);

		clockDisplay = new Label();
		clockDisplay.setId("timeDisplay");
		clockDisplay.setAlignment(Pos.CENTER_RIGHT);
		clockDisplay.setMaxWidth(Double.MAX_VALUE);

		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.FORMAT_DATE);
		SimpleDateFormat timeFormat = new SimpleDateFormat(Constants.FORMAT_CLOCK);
		Timeline timeline = new Timeline();
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				dateDisplay.setText(Calendar.getInstance().getTime().toString().split(Constants.SPACE)[0].trim()
						+ Constants.COMMA + Constants.SPACE + dateFormat.format(Calendar.getInstance().getTime()));
				clockDisplay.setText(timeFormat.format(Calendar.getInstance().getTime()));
			}
		};
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), onFinished));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
	}

	private void instantiateAndStyleComponents() {
		timedList = new VBox();
		timedList.setId("timedList");

		floatingList = new VBox();
		floatingList.setId("floatingList");
		floatingList.setFillWidth(true);

		listDisplay = new HBox();
		listDisplay.setSpacing(0);
		listDisplay.setFillHeight(true);
		listDisplay.setPrefWidth(700);
		listDisplay.setPadding(new Insets(0, 0, 0, 0));

		scrollPane = new ScrollPane();

		inputConsole = new AutoCompleteTextField();
		status = new Label();

		HBox.setHgrow(floatingList, Priority.ALWAYS);
		HBox.setHgrow(timedList, Priority.ALWAYS);

		scrollPane.setId("scrollPane");
		scrollPane.setContent(listDisplay);
		scrollPane.setFitToHeight(true);
		scrollPane.setFitToWidth(true);
		scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		scrollPane.setBorder(null);
		scrollPane.setPickOnBounds(false);

		addTaskPreview = new AddTaskPreview();
		editTaskPreview = new EditTaskPreview();
		helpScreen = new HelpScreen();

		inputConsole.setId("inputConsole");
		inputConsole.setEditable(true);

		status.setMaxWidth(Double.MAX_VALUE);
		status.setId("statusBar");
	}

	protected void updateAddTaskPreviewDetails(String title, DateTime startTime, DateTime endTime, String recurring) {
		addTaskPreview.clearAllDetails();
		addTaskPreview.tempTitle.setText(title);
		addTaskPreview.tempStartTime.setText(this.showIfValidDate(startTime));
		addTaskPreview.tempEndTime.setText(this.showIfValidDate(endTime));
		addTaskPreview.tempRecurring.setText(recurring);
	}

	protected void updateEditTaskPreviewDetails(Task toEdit, String input) {

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

	protected void showEditPopup() {
		editTaskPreview.toFront();
	}

	protected void showAddPopup() {
		addTaskPreview.toFront();
	}

	protected void showHelpPopup() {
		helpScreen.toFront();
	}

	protected void showDefaultView() {
		addTaskPreview.toBack();
		editTaskPreview.toBack();
		helpScreen.toBack();
		scrollPane.toFront();
	}

	protected void clearInputConsole() {
		inputConsole.clear();
	}
}