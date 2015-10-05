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
<<<<<<< HEAD
=======
import parser.CommandParser;
<<<<<<< HEAD
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
=======
import parser.DateParser;
>>>>>>> master
import parser.Parser;

public class GUIService {

	StackPane content;
	ConsoleView consoleView;

	Logic myLogic;

<<<<<<< HEAD
=======
	int listIndex = 0;
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
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
		populateList(myLogic.startupDisplay());
<<<<<<< HEAD
		System.out.println(myLogic.startupDisplay());
=======
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe

		content.getChildren().addAll(consoleView.consolePane);
	}

	private void populateList(ArrayList<Task> tasksArr) {
<<<<<<< HEAD
		/*
		ArrayList<Task> toShow = myLogic.getStartupDisplay();
		ObservableList<StackPane> items =FXCollections.observableArrayList ();
		for (Task task : toShow) {
			//ListItem newListItem = new ListItem(task.getTitle(), task.getStart(), task.getEnd());
			items.add(newListItem);
		}
		 */

		//GUIServiceTest guiTest = new GUIServiceTest();
		//ArrayList<Task> toShow = guiTest.getStartupDisplay();
		ObservableList<ListItem> items =FXCollections.observableArrayList ();
		for (Task task : tasksArr) {
			ListItem newListItem = new ListItem(task.getTitle(), "this is where the task description will be", "1800", "2000");
			items.add(newListItem);
			//newListItem.setStyle("-fx-background-color: rgb(15,175,221); -fx-background-radius: 10px;");
=======
		int index = 1;
		ObservableList<ListItem> items =FXCollections.observableArrayList ();
		for (Task task : tasksArr) {
			ListItem newListItem = new ListItem(task.getTitle(), "this is where the task description will be",task.getStartingTime().toLocalTime().toString("HHmm"), task.getEndingTime().toLocalTime().toString("HHmm"), task.getStatus(),index++);
			items.add(newListItem);
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
		}
		consoleView.listView.setItems(items);
	}

	private void addListenersToConsoleView() {
		consoleView.listView.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
<<<<<<< HEAD
<<<<<<< HEAD
				if(event.getCode()==KeyCode.ESCAPE)
				{
=======
				if(event.getCode() == KeyCode.ESCAPE) {
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
=======
				if(event.getCode() == KeyCode.ESCAPE ) {
>>>>>>> master
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
					System.exit(0);
				} else if (consoleView.inputConsole.getText().length() == 0 && event.getCode() == KeyCode.BACK_SPACE) {
					populateList(myLogic.startupDisplay());
				} else if (event.getCode() == KeyCode.DOWN){
					consoleView.listView.getSelectionModel().select(++listIndex%consoleView.listView.getItems().size());
					consoleView.listView.scrollTo(listIndex);
				} else if (event.getCode() == KeyCode.UP) {
					consoleView.listView.getSelectionModel().select(--listIndex%consoleView.listView.getItems().size());
					consoleView.listView.scrollTo(listIndex);
				}
			}
		});

		consoleView.inputConsole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String input = consoleView.inputConsole.getText();
<<<<<<< HEAD
<<<<<<< HEAD
				System.out.println("[PARSED] the command is : " + myParser.getCommandName(input));//debug
				try {
					populateList(myLogic.inputHandler(input));
=======
System.out.println("[PARSED] the command is : " + CommandParser.getCommand(input));//debug
System.out.println("The End Date is: " + TaskParser.getEndDate(input));//debug
=======
				System.out.println("[PARSED] the command is : " + CommandParser.getCommand(input));//debug
				System.out.println("The End Date is: " + DateParser.getEndDate(input));//debug
>>>>>>> master
				try {
					populateList(myLogic.inputHandler(input));
					consoleView.listView.scrollTo(consoleView.listView.getItems().size()-1);
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
				} catch (ParseException e) {
					e.printStackTrace();
				}
				consoleView.inputConsole.clear();
			}
		});
	}

<<<<<<< HEAD
	public Scene returnScene() {
<<<<<<< HEAD
		Scene myScene  = new Scene(this.content, 605, 600);
=======
		Scene myScene  = new Scene(this.content, 605, 605);
>>>>>>> f1408057840addec287f7fac076bfe841975c2fe
=======
	public Scene buildScene(StackPane content) {
		Scene myScene  = new Scene(content, 605, 605);
>>>>>>> master
		myScene.setFill(Color.TRANSPARENT);
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
}