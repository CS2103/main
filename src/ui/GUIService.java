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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Task;
import logic.TaskHandler;
import parser.Parser;
import storage.Storage;

public class GUIService {

	StackPane content;
	ConsoleView consoleView;
	TaskView taskView;
	TrayService trayService;
	Stage stage;
	Parser myParser;

	public GUIService(Stage stage) {
		this.stage = stage;
		content = new StackPane();
		myParser = new Parser();
		consoleView = new ConsoleView();
		taskView = new TaskView();

		addListenersToConsoleView();
		addListenersToTaskView();

		printToConsole(Constants.WELCOME_MESSAGE, Constants.CALIBRI_BOLD_14);

		content.getChildren().addAll(consoleView.consolePane, taskView.taskPane);
	}

	private void addListenersToConsoleView() {
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
				if (myParser.getCommandName(input).trim().equals("add")) {
					System.out.println("[DEBUG] displaying taskpane");//debug
					showTaskPane();
					try {
						taskView.titleField.setText(myParser.getDescription(input));
						taskView.startField.setText(myParser.getStartDate(input).toString());
						taskView.endField.setText(myParser.getEndDate(input).toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}

				}
				consoleView.inputConsole.clear();

			}
		});
	}

	private void addListenersToTaskView() {
		taskView.titleField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				checkProceedOrReturn(event);
			}
		});

		taskView.startField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				checkProceedOrReturn(event);
			}
		});

		taskView.endField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				checkProceedOrReturn(event);
			}
		});

		taskView.priorityField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				checkProceedOrReturn(event);
			}
		});

		taskView.locationField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				checkProceedOrReturn(event);
			}
		});

		taskView.tagField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				checkProceedOrReturn(event);
			}
		});
	}

	public Scene returnScene() {
		Scene myScene  = new Scene(this.content, 400, 400);
		showConsolePane();
		return myScene;
	}

	public void showConsolePane() {
		consoleView.consolePane.toFront();
		taskView.taskPane.toBack();
		taskView.taskPane.setDisable(true);
		taskView.taskPane.setVisible(false);
		consoleView.consolePane.setVisible(true);
		consoleView.consolePane.setDisable(false);
	}

	public void showTaskPane() {
		taskView.taskPane.toFront();
		consoleView.consolePane.toBack();
		consoleView.consolePane.setDisable(true);
		consoleView.consolePane.setVisible(false);
		taskView.taskPane.setVisible(true);
		taskView.taskPane.setDisable(false);
	}

	public void printToConsole(String output, Font font){
		Text text = new Text(output);
		text.setFont(font);
		consoleView.outputConsole.getChildren().add(text);
	}

	protected void checkProceedOrReturn(KeyEvent event) {
		System.err.println("[KEYBOARD INPUT] " + event.getCode()); //debug
		if(event.getCode()==KeyCode.ESCAPE) {
			showConsolePane();
		} else if (event.getCode()==KeyCode.ENTER) {
			Task newTask = new Task(taskView.titleField.getText());			// refactor this
			TaskHandler.addTask(newTask);									//
			Storage.write(newTask.taskDetails());
			showConsolePane();
			printToConsole(newTask.taskDetails(), Constants.CALIBRI_BOLD_16);
		}
	}

	public void addAutocompleteEntries (ArrayList<String> stringArrayList) {
		String[] stringArray = (String[]) stringArrayList.toArray();
		Collections.addAll(consoleView.inputConsole.entries, stringArray);
	}

	public void showStage() {
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(returnScene());
		stage.show();
	}

	public void showTray() {
		trayService = new TrayService(stage);
		trayService.createTrayIcon(stage);
	}
}