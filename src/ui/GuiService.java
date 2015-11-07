//@@author A0121442X
package ui;

import java.awt.TrayIcon;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import logic.InvalidTimeException;
import logic.Logic;
import logic.Task;
import parser.CommandParser;
import parser.Parser;
import parser.TitleParser;

public class GuiService {

	StackPane content;
	ConsoleView consoleView;

	Logger LOGGER = Logger.getLogger("GUILog");

	Logic logic;
	Parser parser;

	int listIndex;
	private TrayService trayService;
	private Stage stage;

	private static double xOffset = 0;
	private static double yOffset = 0;

	public GuiService(Stage stage) {
		this.stage = stage;
		this.logic = new Logic();
		this.parser = new Parser();

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
		populateList(logic.startupDisplay());
		content.getChildren().addAll(consoleView);
	}

	private void populateList(ArrayList<Task> tasksArr) {
		int index = 1;
		ObservableList<ListItem> timedTasks = FXCollections.observableArrayList();
		ObservableList<ListItem> floatingTasks = FXCollections.observableArrayList();
		for (Task task : tasksArr) {
			ListItem newListItem = new ListItem(task.getTitle(), task.getStartingTime(), task.getEndingTime(),
					task.getType(), task.getStatus(), task.isOverDue(), task.returnRecurTag(), index++);
			if (task.getType().equalsIgnoreCase("task")) {
				floatingTasks.add(newListItem);
			} else {
				timedTasks.add(newListItem);
			}
		}
		consoleView.timedList.getChildren().setAll(timedTasks);
		consoleView.floatingList.getChildren().setAll(floatingTasks);

		if (floatingTasks.isEmpty() && !timedTasks.isEmpty()) {
			consoleView.listDisplay.getChildren().setAll(consoleView.timedList);
		} else if (timedTasks.isEmpty() && !floatingTasks.isEmpty()) {
			consoleView.listDisplay.getChildren().setAll(consoleView.floatingList);
		} else if (floatingTasks.isEmpty() && timedTasks.isEmpty()) {
			consoleView.listDisplay.getChildren().setAll(consoleView.timedList);
		} else {
			consoleView.listDisplay.getChildren().setAll(consoleView.timedList, consoleView.floatingList);
		}
		consoleView.addTaskPreview.toBack();
		consoleView.scrollPane.toFront();
	}

	private void addListenersToConsoleView(Stage stage) {
		consoleView.inputConsole.textProperty().addListener((observable, oldValue, newValue) -> {
			LOGGER.log(Level.FINEST, "Textfield changed from " + oldValue + " to " + newValue);
			if (newValue.equalsIgnoreCase(Constants.COMMAND_HELP)) {

			} else if (CommandParser.getCommand(newValue).equalsIgnoreCase(Constants.COMMAND_ADD)) {

				consoleView.showAddPopup();
				consoleView.updateAddTaskPreviewDetails(TitleParser.getTitle(newValue),
						parser.getStartDateTime(newValue), this.parser.getEndDateTime(newValue),
						parser.getRecurValue(newValue));
			} else if (CommandParser.getCommand(newValue).equalsIgnoreCase(Constants.COMMAND_INVALID)) {
				consoleView.showDefaultView();
			} else if (CommandParser.getCommand(newValue).equalsIgnoreCase(Constants.COMMAND_DELETE)) {

			} else if (CommandParser.getCommand(newValue).equalsIgnoreCase(Constants.COMMAND_EDIT)
					&& newValue.matches("\\D+\\s\\d+\\s\\D+.+\\z")) {
				consoleView.showEditPopup();
				Task toEdit = this.logic.displayCurrent().get(this.parser.getIndex(newValue) - 1);
				consoleView.updateEditTaskPreviewDetails(toEdit.getTitle(), toEdit.getStartingTime(),
						toEdit.getEndingTime(), this.parser.getField(newValue), TitleParser.getEditTitle(newValue),
						this.parser.getDateTime(TitleParser.getEditTitle(newValue)), "in progress",
						parser.getRecurValue(newValue));
			}
		});

		consoleView.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = stage.getX() - event.getScreenX();
				yOffset = stage.getY() - event.getScreenY();
			}
		});

		consoleView.floatingList.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
			}
		});

		consoleView.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() + xOffset);
				stage.setY(event.getScreenY() + yOffset);
			}
		});

		consoleView.scrollPane.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ESCAPE) {
					System.exit(0);
				}
			}
		});

		consoleView.inputConsole.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ESCAPE) {
					if (consoleView.inputConsole.getText().length() == 0) {
						System.exit(0);
					} else {
						consoleView.inputConsole.clear();
					}
				} else if (event.getCode() == KeyCode.BACK_SPACE && consoleView.inputConsole.getText().length() == 0) {
					consoleView.showDefaultView();
					populateList(logic.displayHome());
					updateStatusLabel(Constants.FEEDBACK_VIEW_TODAY);
				} else if (event.getCode() == KeyCode.DOWN) {

					consoleView.scrollPane.setVvalue(consoleView.scrollPane.getVvalue() + 0.2);
				} else if (event.getCode() == KeyCode.UP) {
					consoleView.scrollPane.setVvalue(consoleView.scrollPane.getVvalue() - 0.2);
				} else if (event.getCode() == KeyCode.TAB) {

				} else if (event.getCode() == KeyCode.BACK_QUOTE) {
					Node tempNode = consoleView.timedList.getChildren().get(0);
					TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), tempNode);
					translateTransition.setFromX(50);
					translateTransition.setToX(700);
					translateTransition.setCycleCount(1);
					translateTransition.setAutoReverse(false);
					translateTransition.play();
				} else if (event.getCode() == KeyCode.F1) {
					consoleView.showHelpPopup();
					updateStatusLabel(Constants.FEEDBACK_VIEW_HELP);
				}
			}
		});

		consoleView.inputConsole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String input = consoleView.inputConsole.getText();

				try {

					populateList(logic.inputHandler(input));
					consoleView.inputConsole.clear();
				} catch (ParseException e) {
					LOGGER.log(Level.SEVERE, "Unable to parse user input");
				} catch (InvalidTimeException e) {
				} catch (NullPointerException e) {
					LOGGER.log(Level.SEVERE, "Current user input not returning anything for GUI to display");
				}

				try {
					updateStatusLabel(logic.getStatusBarText(input));
				} catch (NullPointerException e) {
					LOGGER.log(Level.SEVERE, "Nothing for status bar to display");
				}

				if (CommandParser.getCommand(input).trim().equalsIgnoreCase(Constants.COMMAND_HELP)) {
					consoleView.showHelpPopup();
				}
			}
		});
	}

	public Scene buildScene(StackPane content) {
		Scene myScene = new Scene(content, 800, 600);
		myScene.setFill(Color.TRANSPARENT);
		myScene.getStylesheets().clear();
		myScene.getStylesheets().add(this.getClass().getResource("style6.css").toExternalForm());
		showConsolePane();
		return myScene;
	}

	public void showConsolePane() {
		consoleView.toFront();
		consoleView.setVisible(true);
		consoleView.setDisable(false);
	}

	public void addAutocompleteEntries(ArrayList<String> stringArrayList) {
		String[] stringArray = (String[]) stringArrayList.toArray();
		Collections.addAll(consoleView.inputConsole.entries, stringArray);
	}

	public void showStage() {
		Platform.setImplicitExit(false);
		stage.setTitle(Constants.APP_NAME);
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(buildScene(this.content));
		stage.sizeToScene();
		stage.show();
		stage.setAlwaysOnTop(true);
	}

	public TrayIcon showTray() {
		trayService = new TrayService(this.stage);
		return trayService.createTrayIcon(this.stage);
	}

	public void updateStatusLabel(String text) {
		consoleView.status.setText(text);
	}

	public void updateInterface(String input, ArrayList<Task> taskArray) {
		populateList(taskArray);
	}
}