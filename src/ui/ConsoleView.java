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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class ConsoleView extends Pane{

	Pane consolePane;

	Label applicationName;
	Label dateLabel;
	Label clock;
	ListView<ListItem> listView;
	AutoCompleteTextField inputConsole;
	Label status;

	public ConsoleView() {

		consolePane = new Pane();

		applicationName = new Label();
		applicationName.setText("TextBuddyAwesome");
		applicationName.setContentDisplay(ContentDisplay.CENTER);
		applicationName.setPrefHeight(25);
		applicationName.setPrefWidth(600);
		applicationName.setPadding(new Insets(0 ,0 , 0, 20));
<<<<<<< HEAD
		applicationName.setStyle("-fx-background-color: orange; -fx-background-radius: 30 30 0 0;");
=======
		applicationName.setStyle("-fx-background-color: linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%), linear-gradient(#020b02, #3a3a3a), linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%); -fx-background-radius: 30 30 0 0;");
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		applicationName.setFont(Font.font("Georgia", 20));
		applicationName.setTextFill(Color.WHITE);

		dateLabel = new Label();
		dateLabel.setStyle("-fx-background-color: rgba(255,255,255, 0.6); ");
		dateLabel.setFont(new Font("Arial", 30));
		dateLabel.setPrefWidth(300);
		dateLabel.setPadding(new Insets(0,0,0,20));

		clock = new Label();
		clock.setStyle("-fx-background-color: rgba(255,255,255, 0.6); ");
		clock.setFont(new Font("Arial", 30));
		clock.setTextAlignment(TextAlignment.CENTER);
		clock.setPrefWidth(300);
		clock.setPadding(new Insets(0,0,0,90));

<<<<<<< HEAD
		dateLabel = new Label();
		dateLabel.setStyle("-fx-background-color: rgba(255,255,255, 0.8); ");
		dateLabel.setFont(new Font("Arial", 30));
		dateLabel.setPrefWidth(300);
		dateLabel.setPadding(new Insets(0,0,0,20));


		clock = new Label();
		clock.setStyle("-fx-background-color: rgba(255,255,255, 0.8); ");
		clock.setFont(new Font("Arial", 30));
		clock.setTextAlignment(TextAlignment.CENTER);
		clock.setPrefWidth(300);
		clock.setPadding(new Insets(0,0,0,90));

=======
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		Timeline timeline = new Timeline();
		EventHandler<ActionEvent> onFinished = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent t) {
				dateLabel.setText(Calendar.getInstance().getTime().toString().split(" ")[0].trim() + ", " + dateFormat.format(Calendar.getInstance().getTime()));
				clock.setText(timeFormat.format(Calendar.getInstance().getTime()));
			}
		};
		timeline.getKeyFrames().add(new KeyFrame(Duration.millis(500), onFinished));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		listView = new ListView<ListItem>();
		listView.setPrefHeight(470);
		listView.setPrefWidth(600);
<<<<<<< HEAD
<<<<<<< HEAD
		listView.setStyle("-fx-background-color: transparent;");
=======
		listView.setStyle("-fx-background-color: gray;");
		listView.setFocusTraversable(false);
		//listView.setStyle("-fx-background-color: linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%), linear-gradient(#020b02, #3a3a3a), linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%;");
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
=======
		listView.setStyle("-fx-background-color: rgba(255,255,255, 0.6);");
		listView.setFocusTraversable(false);
>>>>>>> master

		inputConsole = new AutoCompleteTextField();
		inputConsole.setEditable(true);
		inputConsole.setPrefHeight(25);
		inputConsole.setPrefWidth(600);
<<<<<<< HEAD
		inputConsole.setStyle("-fx-background-color: lightgray;");
		inputConsole.setFocusTraversable(true);

=======
		//inputConsole.setFont(value);
		inputConsole.setStyle("-fx-background-color: rgba(255,255,255, 0.6);-fx-text-inner-color: black;-fx-font-size:15");
		inputConsole.setFocusTraversable(true);


>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		status = new Label();
		status.setPrefWidth(600);
		status.setPrefHeight(25);
		status.setPadding(new Insets(0,0,0, 80));
<<<<<<< HEAD
		status.setStyle("-fx-background-color: white; -fx-background-radius: 0 0 30 30;");
=======
		//status.setStyle("-fx-background-color: rgba(255,255,255, 0.8); -fx-background-radius: 0 0 30 30;");
		status.setStyle("-fx-background-color: linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%), linear-gradient(#020b02, #3a3a3a), linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%); -fx-background-radius: 0 0 30 30;");
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe

		HBox dateTime = new HBox();
		dateTime.getChildren().addAll(dateLabel, clock);
		VBox consoleLayout = new VBox();
		consoleLayout.setSpacing(1);
		consoleLayout.getChildren().addAll(applicationName, dateTime, listView, inputConsole, status);
		consolePane.getChildren().add(consoleLayout);
	}
}