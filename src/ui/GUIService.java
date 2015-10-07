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
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Logic;
import logic.Task;
import parser.CommandParser;
import parser.DateParser;
import parser.Parser;

public class GUIService {

	StackPane content;
	ConsoleView consoleView;

	Logic myLogic;

	int listIndex;
	private TrayService trayService;
	private Stage stage;
	Parser myParser;

	public GUIService(Stage stage) {
		this.stage = stage;
		this.myLogic = new Logic();

		content = new StackPane();
		myParser = new Parser();
		consoleView = new ConsoleView();

		content.setStyle("-fx-background-color: rgba(255,255,255, 0); -fx-background-radius: 10px;");
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(4.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
		content.setEffect(dropShadow);
		addListenersToConsoleView();
		populateList(myLogic.displayHome());
		consoleView.listView.getSelectionModel().select(0);
		listIndex = consoleView.listView.getItems().size();
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
		consoleView.listView.setItems(items);
	}

	private void addListenersToConsoleView() {
		consoleView.listView.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.ESCAPE ) {
					System.exit(0);
				}
			}
		});
		consoleView.inputConsole.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("textfield changed from " + oldValue + " to " + newValue);//debug
			if (newValue.equalsIgnoreCase("exit")) {
				System.exit(0);
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
					listIndex++;
					consoleView.listView.getSelectionModel().select(listIndex%consoleView.listView.getItems().size());
					consoleView.listView.scrollTo(listIndex%consoleView.listView.getItems().size());
					consoleView.inputConsole.setText(consoleView.listView.getItems().get(listIndex%consoleView.listView.getItems().size()).getTitle());
					consoleView.inputConsole.positionCaret(consoleView.inputConsole.getText().length());
				} else if (event.getCode() == KeyCode.UP ) {
					listIndex--;
					consoleView.listView.getSelectionModel().select(listIndex%consoleView.listView.getItems().size());
					consoleView.listView.scrollTo(listIndex%consoleView.listView.getItems().size());
					consoleView.inputConsole.setText(consoleView.listView.getItems().get(listIndex%consoleView.listView.getItems().size()).getTitle());
					consoleView.inputConsole.positionCaret(consoleView.inputConsole.getText().length());
				}

				if (listIndex == 0) {
					listIndex = consoleView.listView.getItems().size();
				}

				System.out.println(listIndex);
			}
		});

		consoleView.inputConsole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String input = consoleView.inputConsole.getText();
				System.out.println("[PARSED] the command is : " + CommandParser.getCommand(input));//debug
				System.out.println("The End Date is: " + DateParser.getEndDate(input));//debug
				try {
					populateList(myLogic.inputHandler(input));
					consoleView.listView.scrollTo(consoleView.listView.getItems().size()-1);
					updateStatusLabel(myLogic.getStatusBarText(input));
				} catch (ParseException e) {
					e.printStackTrace();
				}
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
}