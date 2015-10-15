/**
 * This class:
 * Creates an instance of the application in the system tray
 * Allows the app to run in the background
 *
 */

package ui;

import java.awt.AWTException;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;

import application.Constants;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class TrayService {

	public TrayIcon trayIcon;
	Stage stage;
	Scene scene;
	StackPane root;

	public TrayService(Stage stage) {
		this.stage = stage;
	}

	public TrayIcon createTrayIcon(final Stage stage) {

		PopUp about = new PopUp("About", Constants.MSG_ABOUT);

		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			java.awt.Image image = Toolkit.getDefaultToolkit().getImage(TrayService.class.getResource("/resource/icon1.png"));
			stage.getIcons().add(new Image("/resource/icon.png"));

			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent t) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							if (SystemTray.isSupported()) {
								stage.hide();
								showProgramIsMinimizedMsg();
							} else {
								System.exit(0);
							}
						}
					});
				}
			});

			final ActionListener closeListener = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					System.exit(0);
				}
			};

			ActionListener aboutListener = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							about.showAndWait();
						}
					});
				}
			};

			ActionListener showListener = new ActionListener() {
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							System.out.println("Clicked on show MenuItem");
							stage.show();
							stage.toFront();
						}
					});
				}
			};

			PopupMenu popup = new PopupMenu();

			MenuItem showItem = new MenuItem("Show");
			showItem.addActionListener(showListener);
			popup.add(showItem);

			MenuItem aboutItem = new MenuItem("About");
			aboutItem.addActionListener(aboutListener);
			popup.add(aboutItem);

			MenuItem closeItem = new MenuItem("Exit");
			closeItem.addActionListener(closeListener);
			popup.add(closeItem);

			trayIcon = new TrayIcon(image, Constants.APP_NAME, popup);
			trayIcon.setImageAutoSize(true);
			trayIcon.addActionListener(showListener);
			trayIcon.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(java.awt.event.MouseEvent e) {
					System.out.println("Clicked on tray icon :" + e.getButton());
					if (e.getButton() == 1) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								stage.show();
								stage.toFront();
							}
						});
					}
				};
			});


			try {
				tray.add(trayIcon);
			} catch (AWTException e) {
				System.err.println(e);
			}
		}
		return trayIcon;
	}

	public void showProgramIsMinimizedMsg() {
		trayIcon.displayMessage("Message.", Constants.APP_NAME + " is still running. You can access it from here.", TrayIcon.MessageType.INFO);
	}
}

