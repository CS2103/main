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

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class ConsoleView extends Pane{

	Pane consolePane;

	Label titleBar;
	Label dateDisplay;
	Label clockDisplay;
	Label currentDisplay;
	AutoCompleteTextField inputConsole;
	Label status;
	VBox list;
	ScrollPane sp;

	public ConsoleView() {

		consolePane = new Pane();

		titleBar = new Label();
		titleBar.setId("titleBar");
		titleBar.setText("TextBuddyAwesome");
		titleBar.setPrefHeight(25);
		titleBar.setPrefWidth(600);
		titleBar.setPadding(new Insets(0 ,0 , 0, 20));
		titleBar.setFont(Font.font("Georgia", 20));
		titleBar.setTextFill(Color.ORANGE);

		dateDisplay = new Label();
		dateDisplay.setId("timeDisplay");
		dateDisplay.setFont(new Font("Arial", 30));
		dateDisplay.setPrefWidth(300);
		dateDisplay.setPadding(new Insets(0,0,0,20));
		dateDisplay.setTextFill(Color.WHITE);

		clockDisplay = new Label();
		clockDisplay.setId("timeDisplay");
		clockDisplay.setFont(new Font("Arial", 30));
		clockDisplay.setTextAlignment(TextAlignment.CENTER);
		clockDisplay.setPrefWidth(300);
		clockDisplay.setPadding(new Insets(0,0,0,90));
		clockDisplay.setTextFill(Color.WHITE);

		currentDisplay = new Label();
		currentDisplay.setPrefWidth(600);
		currentDisplay.setTextAlignment(TextAlignment.CENTER);
		currentDisplay.setPrefHeight(25);

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

		list = new VBox();

		list.setStyle("-fx-background-color:linear-gradient( from 100.0% 0.0% to 100.0% 100.0%, rgb(51,51,51) 0.0, rgb(179,179,179) 40.0, rgb(51,51,51) 100.0)");
		list.setPrefWidth(598);
		list.autosize();

		sp = new ScrollPane();
		sp.setId("scrollPane");
		sp.setContent(list);
		sp.setMaxHeight(470);
		sp.setHbarPolicy(ScrollBarPolicy.NEVER);
		sp.setMaxWidth(600);
		sp.setFocusTraversable(false);

		inputConsole = new AutoCompleteTextField();
		inputConsole.setEditable(true);
		inputConsole.setPrefHeight(25);
		inputConsole.setPrefWidth(600);
		inputConsole.setStyle("-fx-background-color: rgba(255,255,255, 0.9);-fx-text-inner-color: black;-fx-font-size:15");
		inputConsole.setFocusTraversable(true);

		status = new Label();
		status.setPrefWidth(600);
		status.setPrefHeight(30);
		status.setContentDisplay(ContentDisplay.RIGHT);
		status.setPadding(new Insets(0,0,0, 20));
		status.setId("statusBar");
		status.setTextFill(Color.LIGHTGRAY);
		status.setFont(new Font("Arial", 20));

		HBox dateTime = new HBox();
		dateTime.getChildren().addAll(dateDisplay, clockDisplay);

		VBox consoleLayout = new VBox();
		consoleLayout.getChildren().addAll(dateTime, sp, inputConsole, status);
		consolePane.getChildren().add(consoleLayout);
	}
}