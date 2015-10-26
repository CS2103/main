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

import java.awt.TrayIcon;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
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

public class GUIService {

	StackPane content;
	ConsoleView consoleView;

	Logic logic;
	Parser parser;

	int listIndex;
	private TrayService trayService;
	private Stage stage;

	private static double xOffset = 0;
	private static double yOffset = 0;

	public GUIService(Stage stage) {
		this.stage = stage;
		this.logic = new Logic();
		this.parser = new Parser();

		this.content = new StackPane();
		this.consoleView = new ConsoleView();

		this.content.setStyle("-fx-background-color: rgba(255,255,255, 0); -fx-background-radius: 10px;");
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(4.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
		this.content.setEffect(dropShadow);
		this.addListenersToConsoleView(stage);
		this.populateList(this.logic.displayHome());
		this.content.getChildren().addAll(this.consoleView);
	}

	private void populateList(ArrayList<Task> tasksArr) {
		int index = 1;

		ObservableList<ListItem> timedTasks = FXCollections.observableArrayList();
		ObservableList<ListItem> floatingTasks = FXCollections.observableArrayList();
		for (Task task : tasksArr) {
			ListItem newListItem = new ListItem(task.getTitle(), task.getStartingTime(), task.getEndingTime(),
					task.getType(), task.getStatus(), task.isOverDue(), index++);
			assert task.getType() != null;

			if (task.getType().equalsIgnoreCase("task")) {
				floatingTasks.add(newListItem);
			} else {
				timedTasks.add(newListItem);
			}
		}

		this.consoleView.timedList.getChildren().setAll(timedTasks);
		this.consoleView.floatingList.getChildren().setAll(floatingTasks);

		if (floatingTasks.isEmpty() && !timedTasks.isEmpty()) {
			this.consoleView.listDisplay.getChildren().setAll(this.consoleView.timedList);
		} else if (timedTasks.isEmpty() && !floatingTasks.isEmpty()) {
			this.consoleView.listDisplay.getChildren().setAll(this.consoleView.floatingList);
		} else if (floatingTasks.isEmpty() && timedTasks.isEmpty()) {
			this.consoleView.listDisplay.getChildren().setAll(this.consoleView.timedList);

		} else {
			this.consoleView.listDisplay.getChildren().setAll(this.consoleView.timedList,
					this.consoleView.floatingList);
		}

		this.consoleView.addTaskPreview.toBack();
		this.consoleView.scrollPane.toFront();
	}

	private void addListenersToConsoleView(Stage stage) {
		this.consoleView.inputConsole.textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("textfield changed from " + oldValue + " to " + newValue);// debug
			if (newValue.equalsIgnoreCase("exit")) {
				System.exit(0);
			} else if (CommandParser.getCommand(newValue).equalsIgnoreCase("add")) {

				this.consoleView.addTaskPreview.toFront();
				this.consoleView.scrollPane.toBack();
				this.consoleView.editTaskPreview.toBack();
				this.consoleView.updateAddTaskPreviewDetails(TitleParser.getTitle(newValue),
						this.parser.getStartDateTime(newValue), this.parser.getEndDateTime(newValue), null);

			} else if (CommandParser.getCommand(newValue).equalsIgnoreCase(Constants.COMMAND_INVALID)) {
				this.consoleView.addTaskPreview.toBack();
				this.consoleView.scrollPane.toFront();
			} else if (CommandParser.getCommand(newValue).equalsIgnoreCase("delete")) {

			} else if (CommandParser.getCommand(newValue).equalsIgnoreCase("edit")
					&& newValue.matches("\\D+\\s\\d+\\s\\D+.+\\z")) {
				this.consoleView.editTaskPreview.toFront();
				this.consoleView.scrollPane.toBack();
				this.consoleView.addTaskPreview.toBack();
				Task toEdit = this.logic.displayCurrent().get(this.parser.getIndex(newValue) - 1);
				this.consoleView.updateEditTaskPreviewDetails(toEdit.getTitle(), toEdit.getStartingTime(),
						toEdit.getEndingTime(), this.parser.getField(newValue), TitleParser.getEditTitle(newValue),
						this.parser.getDateTime(TitleParser.getEditTitle(newValue)));
			}
		});

		this.consoleView.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = stage.getX() - event.getScreenX();
				yOffset = stage.getY() - event.getScreenY();
			}
		});

		this.consoleView.floatingList.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
			}
		});

		this.consoleView.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				stage.setX(event.getScreenX() + xOffset);
				stage.setY(event.getScreenY() + yOffset);
			}
		});

		this.consoleView.scrollPane.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ESCAPE) {
					System.exit(0);
				}
			}
		});

		this.consoleView.inputConsole.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				System.err.println(event.getCode());
				if (event.getCode() == KeyCode.ESCAPE) {
					if (GUIService.this.consoleView.inputConsole.getText().length() == 0) {
						System.exit(0);
					} else {
						GUIService.this.consoleView.inputConsole.clear();
					}
				} else if (GUIService.this.consoleView.inputConsole.getText().length() == 0
						&& event.getCode() == KeyCode.BACK_SPACE) {
					Logger logger = Logger.getLogger("TBALogger");
					GUIService.this.populateList(GUIService.this.logic.displayHome());
					GUIService.this.updateStatusLabel(Constants.FEEDBACK_VIEW_TODAY);
				} else if (event.getCode() == KeyCode.DOWN) {
					GUIService.this.consoleView.scrollPane
							.setVvalue(GUIService.this.consoleView.scrollPane.getVvalue() + 0.2);
				} else if (event.getCode() == KeyCode.UP) {
					GUIService.this.consoleView.scrollPane
							.setVvalue(GUIService.this.consoleView.scrollPane.getVvalue() - 0.2);
				} else if (event.getCode() == KeyCode.TAB) {

				} else if (event.getCode() == KeyCode.BACK_QUOTE) {
					Node tempNode = GUIService.this.consoleView.timedList.getChildren().get(0);
					TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), tempNode);
					translateTransition.setFromX(50);
					translateTransition.setToX(700);
					translateTransition.setCycleCount(1);
					translateTransition.setAutoReverse(false);
					translateTransition.play();
				}
			}
		});

		this.consoleView.inputConsole.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String input = GUIService.this.consoleView.inputConsole.getText();
				try {

					GUIService.this.populateList(GUIService.this.logic.inputHandler(input));
					GUIService.this.updateStatusLabel(GUIService.this.logic.getStatusBarText(input));
					GUIService.this.consoleView.inputConsole.clear();

				} catch (ParseException e) {
					System.err.println("Input error!");
				} catch (InvalidTimeException e) {
					e.printStackTrace();
				}

			}
		});
	}

	public Scene buildScene(StackPane content) {
		Scene myScene = new Scene(content, 700, 600);
		myScene.setFill(Color.TRANSPARENT);
		myScene.getStylesheets().clear();
		myScene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
		this.showConsolePane();
		return myScene;
	}

	public void showConsolePane() {
		this.consoleView.toFront();
		this.consoleView.setVisible(true);
		this.consoleView.setDisable(false);
	}

	public void addAutocompleteEntries(ArrayList<String> stringArrayList) {
		String[] stringArray = (String[]) stringArrayList.toArray();
		Collections.addAll(this.consoleView.inputConsole.entries, stringArray);
	}

	public void showStage() {
		Platform.setImplicitExit(false);
		this.stage.setTitle(Constants.APP_NAME);
		this.stage.initStyle(StageStyle.TRANSPARENT);
		this.stage.setScene(this.buildScene(this.content));
		this.stage.sizeToScene();
		this.stage.show();
	}

	public TrayIcon showTray() {
		this.trayService = new TrayService(this.stage);
		return this.trayService.createTrayIcon(this.stage);
	}

	public void onEscapePressed() {
	}

	public void updateStatusLabel(String text) {

		this.consoleView.status.setText(text);
	}

	public void updateInterface(String input, ArrayList<Task> taskArray) {
		this.populateList(taskArray);

	}
}