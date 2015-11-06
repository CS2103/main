//@@author A0121442X
package ui;

import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;

import application.Constants;
import javafx.application.Platform;
import javafx.stage.Stage;

@SuppressWarnings("serial")
public class HotKeyListener extends JFrame implements HotkeyListener {

	final JFrame frame = new JFrame(Constants.APP_NAME);

	public HotKeyListener(Stage stage) {
		this.enableHotKey(stage);
	}

	public void limitOneInstance() {
		if (!JIntellitype.isJIntellitypeSupported()) {
			JOptionPane.showMessageDialog(null, "Already running");
			System.exit(1);
		}
		if (JIntellitype.checkInstanceAlreadyRunning(Constants.APP_NAME)) {
			System.out.println("ds");
			JOptionPane.showMessageDialog(null, "Already running");
			JIntellitype.getInstance().cleanUp();
			System.exit(0);
		}
	}

	private void enableHotKey(Stage stage) {
		JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_CONTROL, 'L');
		JIntellitype.getInstance().registerHotKey(2, 0, KeyEvent.VK_ESCAPE);
		/*
		 * JIntellitype.getInstance().registerHotKey(3, 0, KeyEvent.VK_HOME);
		 * JIntellitype.getInstance().registerHotKey(4, 0, 46);
		 * JIntellitype.getInstance().registerHotKey(5,
		 * JIntellitype.MOD_CONTROL, 'Z');
		 * JIntellitype.getInstance().registerHotKey(6,
		 * JIntellitype.MOD_CONTROL, 'Y');
		 * JIntellitype.getInstance().registerHotKey(7,
		 * JIntellitype.MOD_CONTROL, 'F');
		 * JIntellitype.getInstance().registerHotKey(8,
		 * JIntellitype.MOD_CONTROL, 'D');
		 * JIntellitype.getInstance().registerHotKey(9,
		 * JIntellitype.MOD_CONTROL, 'U');
		 * JIntellitype.getInstance().registerHotKey(10, 0, 112);
		 */

		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
			@Override
			public void onHotKey(int aIdentifier) {
				if (aIdentifier == 1) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							stage.show();
							stage.toFront();
						}
					});

				} else if (aIdentifier == 2) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							stage.hide();
							stage.toBack();
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