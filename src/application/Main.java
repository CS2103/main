//@@author A0121442X
package application;

import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import ui.GuiService;
import ui.HotKeyListener;

public class Main extends Application {

	public static Logger logger = Logger.getLogger("TBALog");

	@Override
	public void start(Stage stage) {

		GuiService myGui = new GuiService(stage);

		myGui.showStage(); // Loads the GUI application window
		HotKeyListener hkl = new HotKeyListener(stage);
		myGui.showTray();
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void setAllowOnlyOneInstance() {
	}
}