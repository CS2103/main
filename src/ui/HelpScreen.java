//@@author A0121442X
package ui;

import application.Constants;
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

		instantiateAndStyleComponents();
		setUpHelpScreenPopupLayout();
	}

	private void instantiateAndStyleComponents() {
		header = new Label(Constants.LABEL_HELP);
		header.setId(Constants.CSS_TASK_PREVIEW_HEADER);

		helpCommandsText = new Text(Constants.TEXT_HELP_COMMAND_LIST);
		helpCommandsText.setId(Constants.CSS_HELP_TEXT);

		helpFlexiText = new Text(Constants.TEXT_HELP_FLEXI_COMMAND_LIST);
		helpFlexiText.setId(Constants.CSS_HELP_TEXT);
	}

	private void setUpHelpScreenPopupLayout() {
		HBox helpTextLayout = new HBox(helpCommandsText, helpFlexiText);
		helpTextLayout.setSpacing(50);
		helpScreenLayout = new VBox();
		helpScreenLayout.setId(Constants.CSS_POPUP_LAYOUT);
		helpScreenLayout.getChildren().addAll(header, helpTextLayout);

		this.getChildren().addAll(helpScreenLayout);
		this.setId(Constants.CSS_POPUP_PANE);
		this.setPadding(new Insets(50, 50, 50, 50));
	}
}