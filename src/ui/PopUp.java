package ui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class PopUp{

	private Alert alert;

	public PopUp (String title, String message) {
		alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
	}

	public void show() {
		alert.show();
	}

	public void showAndWait() {
		alert.showAndWait();
	}
}
