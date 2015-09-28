package application;

import java.util.Collections;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.Task;
import logic.TaskHandler;
import parser.Parser;
import storage.Storage;
import ui.AutoCompleteTextField;

public class Main extends Application{

	StackPane content;
	Pane consolePane;
	Pane taskPane;

	Label applicationName;
	TextFlow outputConsole;
	AutoCompleteTextField inputConsole;

	Label taskBar;
	Label titleLabel;
	Label startLabel;
	Label endLabel;
	Label priorityLabel;
	Label locationLabel;
	Label tagLabel;
	TextField titleField;
	TextField startField;
	TextField endField;
	TextField priorityField;
	TextField locationField;
	TextField tagField;

	@Override
	public void start(Stage stage) {

		Parser myParser = new Parser();
		content = new StackPane();
		consolePane = new Pane();
		taskPane = new Pane();

		applicationName = new Label();
		applicationName.setText("TextBuddyAwesome");
		applicationName.setPrefHeight(25);
		applicationName.setPrefWidth(400);
		applicationName.setStyle("-fx-background-color: orange;");
		applicationName.setFont(Font.font("Georgia", 20));

		outputConsole = new TextFlow();
		outputConsole.setPrefHeight(345);
		outputConsole.setPrefWidth(400);
		outputConsole.setFocusTraversable(false);
		outputConsole.setStyle("-fx-background-color: white;");

		inputConsole = new AutoCompleteTextField();
		inputConsole.setEditable(true);
		inputConsole.setPrefWidth(400);

		taskBar = new Label();
		taskBar.setText("Add New Task: ");
		taskBar.setPrefHeight(25);
		taskBar.setPrefWidth(400);
		taskBar.setStyle("-fx-background-color: lightgreen;");
		taskBar.setFont(Font.font("Georgia", 20));

		titleLabel = new Label();
		titleLabel.setText("Title");
		titleLabel.setPrefHeight(25);

		startLabel = new Label();
		startLabel.setText("Start");
		startLabel.setPrefHeight(25);

		endLabel = new Label();
		endLabel.setText("End");
		endLabel.setPrefHeight(25);

		priorityLabel = new Label();
		priorityLabel.setText("Priority");
		priorityLabel.setPrefHeight(25);

		locationLabel = new Label();
		locationLabel.setText("Location");
		locationLabel.setPrefHeight(25);

		tagLabel = new Label();
		tagLabel.setText("Tags");
		tagLabel.setPrefHeight(25);

		startField = new TextField();
		startField.setEditable(true);
		startField.setPrefWidth(200);
		startField.setPrefHeight(25);

		endField = new TextField();
		endField.setEditable(true);
		endField.setPrefWidth(200);
		endField.setPrefHeight(25);

		priorityField = new TextField();
		priorityField.setEditable(true);
		priorityField.setPrefWidth(200);
		priorityField.setPrefHeight(25);

		titleField = new TextField();
		titleField.setEditable(true);
		titleField.setPrefWidth(200);
		titleField.setPrefHeight(25);

		locationField = new TextField();
		locationField.setEditable(true);
		locationField.setPrefWidth(200);
		locationField.setPrefHeight(25);

		tagField = new TextField();
		tagField.setEditable(true);
		tagField.setPrefWidth(200);
		tagField.setPrefHeight(25);

		printToConsole(Constants.WELCOME_MESSAGE, Constants.CALIBRI_BOLD_14);

		inputConsole.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("textfield changed from " + oldValue + " to " + newValue);//debug
			if (newValue.equalsIgnoreCase("exit")) {
				System.exit(0);
			}
		});

		inputConsole.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ESCAPE)
				{
					System.exit(0);
				}
			}
		});

		inputConsole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String input = inputConsole.getText();
				System.out.println("[Parsed] the command is : " + myParser.getCommandName(input));//debug
				if (myParser.getCommandName(input).trim().equals("add")) {
					System.out.println("displaying taskpane");//debug
					showTaskPane();
					//titleField.setText("hello");
					//titleField.setText(myParser.getDescription(input));
					System.out.println(myParser.getDescription(input));
				}
				inputConsole.clear();

			}
		});

		titleField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Task newTask = new Task(titleField.getText());
				TaskHandler.addTask(newTask);
				Storage.write(newTask.taskDetails());
				showConsolePane();
				printToConsole(newTask.taskDetails(), Constants.CALIBRI_BOLD_16);
			}
		});

		titleField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ESCAPE)
				{
					showConsolePane();
				}
			}
		});

		startField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ESCAPE)
				{
					showConsolePane();
				}
			}
		});

		endField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ESCAPE)
				{
					showConsolePane();
				}
			}
		});

		priorityField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ESCAPE)
				{
					showConsolePane();
				}
			}
		});

		locationField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ESCAPE)
				{
					showConsolePane();
				}
			}
		});

		tagField.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>(){
			@Override
			public void handle(KeyEvent event) {
				if(event.getCode()==KeyCode.ESCAPE)
				{
					showConsolePane();
				}
			}
		});
		Collections.addAll(inputConsole.entries, "Australia", "Saudi Arabia", "Iceland", "edit");

		VBox consoleLayout = new VBox();
		consoleLayout.setSpacing(1);
		//consoleLayout.setPadding(new Insets(1, 1, 1, 1));
		consoleLayout.getChildren().addAll(applicationName, outputConsole, inputConsole);
		consolePane.getChildren().add(consoleLayout);

		VBox taskLabels = new VBox();
		taskLabels.getChildren().addAll(titleLabel, startLabel, endLabel, priorityLabel);
		taskLabels.setPrefWidth(200);
		taskLabels.setSpacing(20);
		taskLabels.setPadding(new Insets(40, 20, 10, 20));

		VBox taskFields = new VBox();
		taskFields.setPrefWidth(200);
		taskFields.setPadding(new Insets(40, 20, 10, 20));
		taskFields.setSpacing(20);
		taskFields.getChildren().addAll(titleField, startField, endField, priorityField);

		HBox taskPanel = new HBox();
		taskPanel.getChildren().addAll(taskLabels, taskFields);

		VBox taskLayout = new VBox();
		taskLayout.getChildren().addAll(taskBar, taskPanel);
		taskPane.getChildren().add(taskLayout);

		content.getChildren().addAll(consolePane, taskPane);
		Scene myScene  = new Scene(content, 400, 400);
		showConsolePane();

		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(myScene);
		stage.show();
	}

	public static void main(String[] args) {
		Storage.createFile("database.txt");
		launch(args);
	}


	public void showConsolePane() {
		consolePane.toFront();
		taskPane.toBack();
		taskPane.setDisable(true);
		taskPane.setVisible(false);
		consolePane.setVisible(true);
		consolePane.setDisable(false);
	}

	public void showTaskPane() {
		taskPane.toFront();
		consolePane.toBack();
		consolePane.setDisable(true);
		consolePane.setVisible(false);
		taskPane.setVisible(true);
		taskPane.setDisable(false);
	}

	public void printToConsole(String output, Font font){
		Text text = new Text(output);
		text.setFont(font);
		outputConsole.getChildren().add(text);
	}

}