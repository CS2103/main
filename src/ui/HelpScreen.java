package ui;

import org.joda.time.DateTime;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class HelpScreen extends StackPane {

	private GridPane helpScreenLayout;

	Label header;

	public HelpScreen(String title, DateTime startTime, DateTime endTime, String taskType) {

		header = new Label("Help");
		header.setId("taskPreviewHeader");

		ColumnConstraints columnConstraint = new ColumnConstraints(150);

		helpScreenLayout = new GridPane();
		GridPane.setConstraints(header, 0, 0, 2, 1);

		helpScreenLayout.setPrefSize(700, 50);
		helpScreenLayout.setPadding(new Insets(20, 20, 20, 20));
		helpScreenLayout.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius:20");

		helpScreenLayout.getColumnConstraints().add(columnConstraint);
		helpScreenLayout.getChildren().addAll(header);

		getChildren().addAll(helpScreenLayout);
		setPrefSize(700, 50);
		setStyle("-fx-background-color: rgba(255,255,255,0.5)");
		setPadding(new Insets(200, 50, 100, 50));
	}

}
