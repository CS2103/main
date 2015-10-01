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

import java.util.ArrayList;
import java.util.Collections;

import application.Constants;
import javafx.application.Platform;
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
import parser.Parser;

public class GUIService {

	StackPane content;
	ConsoleView consoleView;

	private TrayService trayService;
	Stage stage;
	Parser myParser;

	public GUIService(Stage stage) {
		this.stage = stage;
		content = new StackPane();
		myParser = new Parser();
		consoleView = new ConsoleView();

		addListenersToConsoleView();

		printToConsole(Constants.WELCOME_MESSAGE, Constants.CALIBRI_BOLD_14);

		content.getChildren().addAll(consoleView.consolePane);
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
				}
				consoleView.inputConsole.clear();
			}
		});
	}


	public Scene returnScene() {
		Scene myScene  = new Scene(this.content, 600, 600);
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

	public void printToConsole(String output, Font font){
		Text text = new Text(output);
		text.setFont(font);
		consoleView.outputConsole.getChildren().add(text);
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