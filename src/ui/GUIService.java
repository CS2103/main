/**
 * This class:
 * Instantiates all UI views
 * Add listeners to UI components
 * Listeners will call the corresponding logic methods on event received
 *
 * void printToConsole(String, Font);
 * void addAutoCompleteEntries(ArrayList<String>);
 */

package ui;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;

import application.Constants;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import logic.Logic;
import logic.Task;

public class GUIService {

	StackPane content;
	ConsoleView consoleView;

	Logic myLogic;

	int listIndex;
	private TrayService trayService;
	private Stage stage;

	private static double xOffset = 0;
	private static double yOffset = 0;

	public GUIService(Stage stage) {
		this.stage = stage;
		this.myLogic = new Logic();

		content = new StackPane();
		consoleView = new ConsoleView();

		content.setStyle("-fx-background-color: rgba(255,255,255, 0); -fx-background-radius: 10px;");
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(4.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
		content.setEffect(dropShadow);
		addListenersToConsoleView(stage);
		populateList(myLogic.displayHome());
		content.getChildren().addAll(consoleView.consolePane);
	}

	private void populateList(ArrayList<Task> tasksArr) {
		int index = 1;
		ObservableList<ListItem> items =FXCollections.observableArrayList ();
		for (Task task : tasksArr) {
			ListItem newListItem = new ListItem(task.getTitle(),
					null ,
					task.getStartingDate().toLocalDate().toString("dd/MM/yy"),
					task.getStartingTime().toLocalTime().toString("HHmm"),
					task.getStartingDate().toLocalDate().toString("dd/MM/yy"),
					task.getEndingTime().toLocalTime().toString("HHmm"),
					task.getStatus(),
					false,
					index++);
			items.add(newListItem);
		}
		consoleView.list.getChildren().setAll(items);
	}

	private void addListenersToConsoleView(Stage stage) {
		consoleView.inputConsole.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("textfield changed from " + oldValue + " to " + newValue);//debug
			if (newValue.equalsIgnoreCase("exit")) {
				System.exit(0);
			}
		});

		consoleView.consolePane.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent> (){
			@Override
			public void handle(MouseEvent event) {
				xOffset = stage.getX() - event.getScreenX();
				yOffset = stage.getY() - event.getScreenY();
			}
		});

		consoleView.consolePane.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent> (){
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() + xOffset);
				stage.setY(event.getScreenY() + yOffset);
			}
		});

		consoleView.inputConsole.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				System.err.println(event.getCode());
				if(event.getCode() == KeyCode.ESCAPE) {
					if (consoleView.inputConsole.getText().length() == 0) {
						System.exit(0);
					} else {
						consoleView.inputConsole.clear();
					}
				} else if (consoleView.inputConsole.getText().length() == 0 && event.getCode() == KeyCode.BACK_SPACE) {
					populateList(myLogic.displayHome());
					updateStatusLabel(Constants.FEEDBACK_VIEW_TODAY);
				} else if (event.getCode() == KeyCode.DOWN ){
					consoleView.list.getChildren().get(0);
				} else if (event.getCode() == KeyCode.UP ) {

				} else if (event.getCode() == KeyCode.BACK_QUOTE) {
					Node tempNode = consoleView.list.getChildren().get(0);

					TranslateTransition translateTransition =
							new TranslateTransition(Duration.millis(1000), tempNode);
					translateTransition.setFromX(50);
					translateTransition.setToX(700);
					translateTransition.setCycleCount(1);
					translateTransition.setAutoReverse(false);
					translateTransition.play();
				}
				System.out.println(listIndex);
			}
		});

		consoleView.inputConsole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String input = consoleView.inputConsole.getText();
				try {
					updateInterface(input, myLogic.inputHandler(input));
				} catch (ParseException e) {
				}
				updateStatusLabel(myLogic.getStatusBarText(input));
				consoleView.inputConsole.clear();
			}
		});
	}

	public Scene buildScene(StackPane content) {
		Scene myScene  = new Scene(content, 605, 605);
		myScene.setFill(Color.TRANSPARENT);
		myScene.getStylesheets().clear();
		myScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
		showConsolePane();
		return myScene;
	}

	public void showConsolePane() {
		consoleView.consolePane.toFront();
		consoleView.consolePane.setVisible(true);
		consoleView.consolePane.setDisable(false);
	}

	public void addAutocompleteEntries (ArrayList<String> stringArrayList) {
		String[] stringArray = (String[]) stringArrayList.toArray();
		Collections.addAll(consoleView.inputConsole.entries, stringArray);
	}

	public void showStage() {
		stage.setAlwaysOnTop(true);
		stage.initStyle(StageStyle.TRANSPARENT);
		Platform.setImplicitExit(false);
		stage.setScene(buildScene(this.content));
		stage.show();
	}

	public void showTray() {
		trayService = new TrayService(this.stage);
		trayService.createTrayIcon(this.stage);
	}

	public void onEscapePressed() {
	}

	public void updateStatusLabel(String text) {
		consoleView.status.setText(text);
	}
	public void updateDisplayLabel(String text) {
		consoleView.currentDisplay.setText(text);
	}
	public void updateInterface(String input, ArrayList<Task> taskArray) {
		String command = myLogic.getCommand(input);
		if (command.equals(Constants.DICTIONARY_ADD[0])) {
			populateList(taskArray);
		} else if (command.equals(Constants.DICTIONARY_DELETE[0])) {
			int index = myLogic.getIndex(input);
			Node tempNode = consoleView.list.getChildren().get(index-1);
			populateList(taskArray);
			consoleView.list.getChildren().add(index-1, tempNode);

			TranslateTransition translateTransition =
					new TranslateTransition(Duration.millis(800), tempNode);
			translateTransition.setFromX(50);
			translateTransition.setToX(700);
			translateTransition.setCycleCount(1);
			translateTransition.setAutoReverse(false);
			translateTransition.play();

			translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					populateList(taskArray);
				}
			});
		} else {
			populateList(taskArray);
		}
	}


}