/**
 * This class:
 * Creates and instantiates the individual components for
 * the console view
 * The console view is where the user inputs commands
 * The console textfield extends an autocomplete feature
 */

package ui;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextFlow;

public class ConsoleView {

	Pane consolePane;

	Label applicationName;
	TextFlow outputConsole;
	AutoCompleteTextField inputConsole;

	public ConsoleView() {

		consolePane = new Pane();

		applicationName = new Label();
		applicationName.setText("TextBuddyAwesome");
		applicationName.setPrefHeight(25);
		applicationName.setPrefWidth(600);
		applicationName.setStyle("-fx-background-color: orange;");
		applicationName.setFont(Font.font("Georgia", 20));

		outputConsole = new TextFlow();
		outputConsole.setPrefHeight(545);
		outputConsole.setPrefWidth(600);
		outputConsole.setFocusTraversable(false);
		outputConsole.setStyle("-fx-background-color: white;");

		inputConsole = new AutoCompleteTextField();
		inputConsole.setEditable(true);
		inputConsole.setPrefHeight(25);
		inputConsole.setPrefWidth(600);

		VBox consoleLayout = new VBox();
		consoleLayout.setSpacing(1);
		//consoleLayout.setPadding(new Insets(1, 1, 1, 1));
		consoleLayout.getChildren().addAll(applicationName, outputConsole, inputConsole);
		consolePane.getChildren().add(consoleLayout);
	}
}
