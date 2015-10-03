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
import parser.Parser;

public class GUIService {

	StackPane content;
	ConsoleView consoleView;

	Logic myLogic;

	private TrayService trayService;
	Stage stage;
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
		populateList();
		content.getChildren().addAll(consoleView.consolePane);
	}

	private void populateList() {
		/*
		ArrayList<Task> toShow = myLogic.getStartupDisplay();
		ObservableList<StackPane> items =FXCollections.observableArrayList ();
		for (Task task : toShow) {
			//ListItem newListItem = new ListItem(task.getTitle(), task.getStart(), task.getEnd());
			items.add(newListItem);
		}
		 */

		GUIServiceTest guiTest = new GUIServiceTest();
		ArrayList<Task> toShow = guiTest.getStartupDisplay();
		ObservableList<ListItem> items =FXCollections.observableArrayList ();
		for (Task task : toShow) {
			ListItem newListItem = new ListItem(task.getTitle(), "this is where the task description will be", "1800", "2000");
			items.add(newListItem);
			//newListItem.setStyle("-fx-background-color: rgb(15,175,221); -fx-background-radius: 10px;");
		}
		consoleView.listView.setItems(items);
	}

	private void addListenersToConsoleView() {
		consoleView.listView.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ESCAPE)
				{
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
				if(event.getCode()==KeyCode.ESCAPE)
				{
					System.exit(0);
				}
			}
		});

		consoleView.inputConsole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String input = consoleView.inputConsole.getText();
				System.out.println("[PARSED] the command is : " + myParser.getCommandName(input));//debug
				Logic myLogic = new Logic();
				try {
					ArrayList<Task> toShow = myLogic.inputHandler(input);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				consoleView.inputConsole.clear();
			}
		});
	}

	public Scene returnScene() {
		Scene myScene  = new Scene(this.content, 605, 600);
		myScene.setFill(Color.TRANSPARENT);
		showConsolePane();
		return myScene;
	}

	public void showConsolePane() {
		consoleView.consolePane.toFront();
		consoleView.consolePane.setVisible(true);
		consoleView.consolePane.setDisable(false);
	}

	public void showTaskPane() {
		consoleView.consolePane.toBack();
		consoleView.consolePane.setDisable(true);
		consoleView.consolePane.setVisible(false);
	}

	public void addAutocompleteEntries (ArrayList<String> stringArrayList) {
		String[] stringArray = (String[]) stringArrayList.toArray();
		Collections.addAll(consoleView.inputConsole.entries, stringArray);
	}

	public void showStage() {
		stage.initStyle(StageStyle.TRANSPARENT);
		Platform.setImplicitExit(false);
		stage.setScene(returnScene());
		stage.show();
	}

	public void showTray() {
		trayService = new TrayService(stage);
		trayService.createTrayIcon(stage);
	}
}