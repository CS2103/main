//@@author A0121442X
package ui;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import application.Constants;
import javafx.application.Platform;
import javafx.stage.Stage;

@SuppressWarnings("serial")
public class HotKeyListener extends JFrame implements HotkeyListener {

    final JFrame frame = new JFrame(Constants.APP_NAME);
    static boolean isHidden = false;

    public HotKeyListener(Stage stage) {
	this.enableHotKey(stage);
    }

    public void limitOneInstance() {
	if (!JIntellitype.isJIntellitypeSupported()) {
	    System.exit(0);
	}
	if (JIntellitype.checkInstanceAlreadyRunning(Constants.APP_NAME)) {
	    JIntellitype.getInstance().cleanUp();
	    System.exit(0);
	}
    }

    private void enableHotKey(Stage stage) {
	JIntellitype.getInstance().registerHotKey(1, 0, KeyEvent.VK_F8);
	JIntellitype.getInstance().registerHotKey(2, 0, KeyEvent.VK_ESCAPE);

	JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
	    @Override
	    public void onHotKey(int aIdentifier) {
		if (aIdentifier == 1) {
		    Platform.runLater(new Runnable() {
			@Override
			public void run() {
			    if (isHidden == true) {
				stage.show();
				stage.toFront();
			    } else {
				stage.hide();
				stage.toBack();
			    }
			    isHidden = !isHidden;
			}
		    });

		} else if (aIdentifier == 2) {
		    Platform.runLater(new Runnable() {
			@Override
			public void run() {
			    stage.hide();
			    stage.toBack();
			    isHidden = true;
			}
		    });
		}
	    }
	});
    }

    @Override
    public void onHotKey(int arg0) {
	// TODO Auto-generated method stub
    }
}