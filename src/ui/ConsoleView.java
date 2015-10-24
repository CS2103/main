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
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class ConsoleView extends Pane{

	Label dateDisplay;
	Label clockDisplay;
	AutoCompleteTextField inputConsole;
	Label status;

	HBox listDisplay;
	TaskPreview taskPreview;
	
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
		dateDisplay.setFont(new Font("SansSerif", 30));
		dateDisplay.setAlignment(Pos.CENTER_LEFT);
		dateDisplay.setMaxWidth(Double.MAX_VALUE);
		dateDisplay.setPadding(new Insets(0,0,0,20));
		dateDisplay.setTextFill(Color.WHITE);

		clockDisplay.setId("timeDisplay");
		clockDisplay.setFont(new Font("SansSerif", 30));
		clockDisplay.setAlignment(Pos.CENTER_RIGHT);
		clockDisplay.setMaxWidth(Double.MAX_VALUE);
		clockDisplay.setPadding(new Insets(0,20,0,0));
		clockDisplay.setTextFill(Color.WHITE);

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Timeline timeline = new Timeline();
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				dateDisplay.setText(Calendar.getInstance().getTime().toString().split(" ")[0].trim() + ", " + dateFormat.format(Calendar.getInstance().getTime()));
				clockDisplay.setText(timeFormat.format(Calendar.getInstance().getTime()));
			}
		};
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), onFinished));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();
		
		timedList.setStyle("-fx-background-color:linear-gradient( from 100.0% 0.0% to 100.0% 100.0%, rgb(51,51,51) 0.0, rgb(179,179,179) 40.0, rgb(51,51,51) 100.0)");
		timedList.setFocusTraversable(false);
		timedList.setFillWidth(true);
		
		floatingList.setStyle("-fx-background-color: linear-gradient( from 100.0% 0.0% to 100.0% 100.0%, rgb(46,50,68) 0.0, rgb(51,51,51) 40.0, rgb(39,41,54) 100.0)");
		floatingList.setFocusTraversable(false);
		floatingList.setFillWidth(true);
		HBox.setHgrow(floatingList, Priority.ALWAYS);
		HBox.setHgrow(timedList, Priority.ALWAYS);
	
		listDisplay.setSpacing(0);
		listDisplay.setFillHeight(true);
		listDisplay.setPrefWidth(700);
		listDisplay.setPadding(new Insets(0,0,0,0));
		//listDisplay.getChildren().addAll(timedList, floatingList);
		
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
		taskPreview = new TaskPreview("", DateTime.now(), DateTime.now());
		
		mainDisplay.getChildren().addAll(taskPreview, scrollPane);	
				
		inputConsole.setId("inputConsole");
		inputConsole.setEditable(true);
		inputConsole.setPrefHeight(25);
		inputConsole.setPrefWidth(700);
		inputConsole.setStyle("-fx-background-color: rgba(255,255,255, 0.7);-fx-text-inner-color: black;-fx-font-size:15");
		inputConsole.setFocusTraversable(true);
		
		status.setMaxWidth(Double.MAX_VALUE);
		status.setPrefHeight(30);
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
	
	public void updateTaskPreviewDetails(String title, DateTime startTime, DateTime endTime, String recurring) {
		taskPreview.tempTitle.setText(title);;
		if (startTime.getYear()!=0000) {
			taskPreview.tempStartTime.setText(startTime.toLocalDateTime().toString("HHmm dd MMM yyyy"));
		}
		if (endTime.getYear()!=0000) {
			taskPreview.tempEndTime.setText(endTime.toLocalDateTime().toString("HHmm dd MMM yyyy"));
		}	
	}
	public void clearTaskPreviewDetails() {
		taskPreview.tempTitle.setText("");
		taskPreview.tempStartTime.setText("");
		taskPreview.tempEndTime.setText("");
	}
	
}