//@@author A0121442X
package ui;

import java.awt.TrayIcon;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;

import org.joda.time.DateTime;

import application.Constants;
import application.LogHandler;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import logic.InvalidTimeException;
import logic.Logic;
import logic.Task;
import parser.CommandParser;
import parser.Parser;
import parser.TitleParser;

public class GuiService {

    protected StackPane content;
    protected ConsoleView consoleView;

    private Logic logic;
    private Parser parser;

    private static int taskIndex = 1;
    private static int themeIndex = 0;
    private TrayService trayService;
    private Stage stage;

    private static double xOffset = 0;
    private static double yOffset = 0;

    private static boolean isShowingHelpPopup = false;
    private static final int APP_DIMENSIONS_HEIGHT = 600;
    private static final int APP_DIMENSIONS_WIDTH = 805;
    private static final double SCROLL_SPEED = 0.2;

    public GuiService(Stage stage) {
	this.stage = stage;
	instantiateHelperClasses();
	applyDropShadowEffect();
	setTextFieldOnAction();
	setUpDraggableStage();
	setUpResponsivePopups();
	setUpTextFieldKeystrokeListeners();
	setUpMainDisplayKeystrokeListeners();
	populateList(logic.startupDisplay());
    }

    private void applyDropShadowEffect() {
	content = new StackPane();
	content.setId(Constants.CSS_CONTENT);
    }

    private void instantiateHelperClasses() {
	this.logic = new Logic();
	this.parser = new Parser();
	this.consoleView = new ConsoleView();
    }

    private void populateList(ArrayList<Task> tasksArr) {
	taskIndex = Constants.BEGINNING_OF_LIST;
	ObservableList<ListItem> timedTasks = FXCollections.observableArrayList();
	ObservableList<ListItem> floatingTasks = FXCollections.observableArrayList();
	for (Task task : tasksArr) {
	    ListItem newListItem = new ListItem(task.getTitle(), task.getStartingTime(), task.getEndingTime(),
		    task.getType(), task.getStatus(), task.isOverDue(), task.returnRecurTag(), taskIndex++);
	    if (task.getType().equalsIgnoreCase(Constants.TYPE_FLOATING)) {
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
	consoleView.showDefaultView();
    }

    private void setUpResponsivePopups() {

	consoleView.inputConsole.textProperty().addListener((observable, oldValue, newValue) -> {
	    if (checkIfInputConsoleEqualsCommand(newValue, Constants.COMMAND_ADD)) {
		consoleView.showAddPopup();
		consoleView.updateAddTaskPreviewDetails(TitleParser.getTitle(newValue),
			parser.getStartDateTime(newValue), parser.getEndDateTime(newValue),
			parser.getRecurValue(newValue));
	    } else if (checkIfInputConsoleEqualsCommand(newValue, Constants.COMMAND_INVALID)) {
		consoleView.showDefaultView();
	    } else if (checkIfInputConsoleEqualsCommand(newValue, Constants.COMMAND_DELETE)) {

	    } else if (checkIfInputConsoleEqualsCommand(newValue, Constants.COMMAND_EDIT)
		    && newValue.matches(Constants.REGEX_EDIT_POPUP)) {
		consoleView.showEditPopup();
		Task toEdit = logic.displayCurrent().get(parser.getIndex(newValue) - Constants.NUMBER_ONE);
		consoleView.updateEditTaskPreviewDetails(toEdit, newValue);
	    }
	});
    }

    private void setUpDraggableStage() {

	consoleView.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event) {
		xOffset = stage.getX() - event.getScreenX();
		yOffset = stage.getY() - event.getScreenY();
	    }
	});

	consoleView.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
	    @Override
	    public void handle(MouseEvent event) {
		stage.setX(event.getScreenX() + xOffset);
		stage.setY(event.getScreenY() + yOffset);
	    }
	});
    }

    private void setUpTextFieldKeystrokeListeners() {
	consoleView.inputConsole.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	    @Override
	    public void handle(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE) {
		    if (checkIfInputConsoleIsEmpty()) {
			System.exit(0);
		    } else {
			consoleView.clearInputConsole();
		    }
		} else if (event.getCode() == KeyCode.BACK_SPACE && checkIfInputConsoleIsEmpty()) {
		    consoleView.showDefaultView();
		    populateList(logic.displayHome());
		    updateStatusLabel(Constants.FEEDBACK_VIEW_TODAY
			    + DateTime.now().plusWeeks(1).toLocalDateTime().toString(Constants.FORMAT_DATE_VERBOSE));
		} else if (event.getCode() == KeyCode.DOWN) {
		    consoleView.scrollPane.setVvalue(consoleView.scrollPane.getVvalue() + SCROLL_SPEED);
		} else if (event.getCode() == KeyCode.UP) {
		    consoleView.scrollPane.setVvalue(consoleView.scrollPane.getVvalue() - SCROLL_SPEED);
		} else if (event.getCode() == KeyCode.TAB) {

		} else if (event.getCode() == KeyCode.BACK_QUOTE) {

		} else if (event.getCode() == KeyCode.F1) {
		    toggleDisplayHelpPopup();
		} else if (event.getCode() == KeyCode.F2) {
		    changeTheme();
		}
	    }
	});
    }

    private void setUpMainDisplayKeystrokeListeners() {
	consoleView.scrollPane.addEventHandler(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
	    @Override
	    public void handle(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE) {
		    System.exit(0);
		}
	    }
	});
    }

    private void setTextFieldOnAction() {
	consoleView.inputConsole.setOnAction(new EventHandler<ActionEvent>() {
	    @Override
	    public void handle(ActionEvent event) {
		String input = consoleView.inputConsole.getText();

		try {

		    populateList(logic.inputHandler(input));
		    consoleView.clearInputConsole();
		} catch (ParseException e) {
		    LogHandler.log(Level.WARNING, "Unable to parse user input");
		} catch (InvalidTimeException e) {
		} catch (NullPointerException e) {
		    LogHandler.log(Level.INFO, "Current user input not returning anything for GUI to display");
		}

		try {
		    updateStatusLabel(logic.getStatusBarText(input));
		} catch (NullPointerException e) {
		    LogHandler.log(Level.INFO, "Nothing for status bar to display");
		}

		if (CommandParser.getCommand(input).trim().equalsIgnoreCase(Constants.COMMAND_HELP)) {
		    consoleView.showHelpPopup();
		}
	    }
	});
    }

    public Scene buildScene(StackPane content) {
	content.getChildren().addAll(consoleView);
	Scene myScene = new Scene(content, APP_DIMENSIONS_WIDTH, APP_DIMENSIONS_HEIGHT);
	myScene.setFill(Color.TRANSPARENT);
	myScene.getStylesheets().clear();
	myScene.getStylesheets().add(this.getClass().getResource("style0.css").toExternalForm());
	showConsolePane();
	return myScene;
    }

    protected void changeTheme() {
	stage.getScene().getStylesheets().clear();
	stage.getScene().getStylesheets().add(this.getClass()
		.getResource(Constants.THEME_LIST[(++themeIndex) % Constants.THEME_LIST.length]).toExternalForm());
    }

    protected void showConsolePane() {
	consoleView.toFront();
	consoleView.setVisible(true);
	consoleView.setDisable(false);
    }

    public void showStage() {
	Platform.setImplicitExit(false);
	stage.setTitle(Constants.APP_NAME);
	stage.initStyle(StageStyle.TRANSPARENT);
	stage.setScene(buildScene(this.content));
	stage.sizeToScene();
	stage.show();
    }

    public TrayIcon showTray() {
	trayService = new TrayService(this.stage);
	return trayService.createTrayIcon(this.stage);
    }

    protected void updateStatusLabel(String text) {
	consoleView.status.setText(text);
    }

    protected void toggleDisplayHelpPopup() {
	if (!isShowingHelpPopup) {
	    consoleView.showHelpPopup();
	    updateStatusLabel(Constants.FEEDBACK_VIEW_HELP);
	} else {
	    consoleView.showDefaultView();
	}
	isShowingHelpPopup = !isShowingHelpPopup;
    }

    protected boolean checkIfInputConsoleEqualsCommand(String input, String command) {
	return CommandParser.getCommand(input).equalsIgnoreCase(command);
    }

    protected boolean checkIfInputConsoleIsEmpty() {
	return (consoleView.inputConsole.getText().length() == 0);
    }
}