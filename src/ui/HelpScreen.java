//@@author A0121442X
package ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class HelpScreen extends StackPane {

	VBox helpScreenLayout;

	Label header;
	Text helpCommandsText;
	Text helpFlexiText;

	public HelpScreen() {

		header = new Label("Help");
		header.setId("taskPreviewHeader");

		helpCommandsText = new Text(
				"add\ndelete\nedit\nmark\nunmark\nundo\nredo\nsearch\nshow\nsetpath\nenquirepath\nexit");
		helpCommandsText.setStyle("-fx-fill: rgba(255,255,255,1); -fx-font-size:18; -fx-font-weight:400");

		helpFlexiText = new Text(
				"a [title] from [start] to [end]\ndel [index]\ne [index] [field] [new value]\nm [index]\num [index]\nun\nre\nsearch [keyword]\nshow [date]\nset [filepath]\nen\nexit");
		helpFlexiText.setStyle("-fx-fill: rgba(255,255,255,1); -fx-font-size:18; -fx-font-weight:400");
		HBox helpTextLayout = new HBox(helpCommandsText, helpFlexiText);
		helpTextLayout.setSpacing(50);
		helpScreenLayout = new VBox();
		helpScreenLayout.setPrefSize(700, 50);
		helpScreenLayout.setPadding(new Insets(20, 20, 20, 20));
		helpScreenLayout.getChildren().addAll(header, helpTextLayout);
		helpScreenLayout.setStyle("-fx-background-color: rgba(0,0,0,0.5); -fx-background-radius:20");

		this.getChildren().addAll(helpScreenLayout);
		this.setPrefSize(700, 50);
		this.setStyle("-fx-background-color: rgba(255,255,255,0.5)");
		this.setPadding(new Insets(50, 50, 50, 50));
	}
}