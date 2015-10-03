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
import javafx.scene.control.ListView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class ConsoleView extends Pane{

	Pane consolePane;

	Label applicationName;
	Label clock;
	//TextFlow outputConsole;
	ListView<ListItem> listView;
	AutoCompleteTextField inputConsole;

	public ConsoleView() {

		consolePane = new Pane();

		applicationName = new Label();
		applicationName.setText("TextBuddyAwesome");
		applicationName.setContentDisplay(ContentDisplay.CENTER);
		applicationName.setPrefHeight(25);
		applicationName.setPrefWidth(600);
		applicationName.setPadding(new Insets(0 ,0 , 0, 20));
		applicationName.setStyle("-fx-background-color: orange; -fx-background-radius: 30 30 0 0;");
		applicationName.setFont(Font.font("Georgia", 20));

		clock = new Label();
		clock.setFont(new Font("Arial", 30));
		clock.setTextAlignment(TextAlignment.CENTER);
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
		Timeline timeline = new Timeline();
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				Calendar.getInstance().getTime().toString().split(" ")[1].trim();
				clock.setText(Calendar.getInstance().getTime().toString().split(" ")[0].trim() + ", " + sdf.format(Calendar.getInstance().getTime()));
			}
		};
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), onFinished));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		listView = new ListView<ListItem>();
		listView.setPrefHeight(495);
		listView.setPrefWidth(600);
		listView.setStyle("-fx-background-color: transparent;");

		inputConsole = new AutoCompleteTextField();
		inputConsole.setEditable(true);
		inputConsole.setPrefHeight(25);
		inputConsole.setPrefWidth(600);
		//inputConsole.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1);");


		VBox consoleLayout = new VBox();
		consoleLayout.setSpacing(1);
		//consoleLayout.setPadding(new Insets(1, 1, 1, 1));
		consoleLayout.getChildren().addAll(applicationName, clock, listView, inputConsole);
		consolePane.getChildren().add(consoleLayout);
		//this.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1);");
		//consolePane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.1);");

	}
}
