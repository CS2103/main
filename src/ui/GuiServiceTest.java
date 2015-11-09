//@@author A0121442X
package ui;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.stage.Stage;

public class GuiServiceTest extends ApplicationTest {

    private GuiService gui;

    // private static GuiTest controller = new GuiTest();
    @Override
    public void start(Stage arg0) throws Exception {
	gui = new GuiService(arg0);
	gui.showStage();
	gui.showTray();
    }

    @Test
    public void testGuiCreated() {
	gui.updateStatusLabel("this is a test string");
	assertTrue(gui.consoleView.status.getText().equals("this is a test string"));
	assertTrue(gui.consoleView.getChildren() != null);
	assertTrue(gui.content.getChildren() != null);

    }

}
