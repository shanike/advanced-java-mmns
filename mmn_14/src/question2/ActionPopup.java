package question2;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;

/**
 * A utility class which helps create a popup with optional save and/or cancel
 * buttons, a title, text fields, and any other self-implemented content.
 */
public class ActionPopup {

	private static final String CANCEL_BUTTON_TEXT = "Cancel";
	private static final String SAVE_BUTTON_TEXT = "Save";

	private static final Color CANCEL_BUTTON_COLOR = Color.valueOf("#ADD8E6");
	private static final Color SAVE_BUTTON_COLOR = Color.valueOf("#FFB6C1");

	private Node container;
	private Popup popup;
	private VBox vbox;
	private Label title = new Label();
	final private ArrayList<Node> contentNodes = new ArrayList<Node>();
	private Button saveButton = new Button();
	private Button cancelButton = new Button();

	/**
	 * Creates a popup and creates a vertical box for the content.
	 * 
	 * @param container the Node on which to call {@code popup.show} with.
	 */
	public ActionPopup(Node container) {
		this.container = container;

		popup = new Popup();

		vbox = new VBox();
		vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		vbox.setPadding(new Insets(20));
		vbox.setSpacing(20);
	}

	/**
	 * Opens the popup with the generated content and with the buttons and centers
	 * it on the screen.
	 */
	public void open() {
		// Add title
		vbox.getChildren().add(title);

		// Add content
		vbox.getChildren().addAll(contentNodes);

		// Add buttons
		vbox.getChildren().add(saveButton);
		vbox.getChildren().add(cancelButton);

		// Add vbox to popup
		popup.getContent().addAll(vbox);

		// Open popup
		popup.show(container, 0, 0);
		popup.centerOnScreen();
	}

	/**
	 * Closes the popup.
	 */
	public void close() {
		popup.hide();
	}

	/**
	 * @param nodes - the popup content.
	 */
	public void addContent(Node... nodes) {
		contentNodes.addAll(Arrays.asList(nodes));
	}

	public Button createSaveButton() {
		Button saveButton = new Button(SAVE_BUTTON_TEXT);
		saveButton
				.setBackground(new Background(new BackgroundFill(SAVE_BUTTON_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
		saveButton.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		this.saveButton = saveButton;
		return saveButton;
	}

	/**
	 * Creates a styled button with {@code CANCEL_BUTTON_TEXT} text and adds a click
	 * event handler which closes the popup.
	 */
	public Button createCancelButton() {
		Button cancelButton = new Button(CANCEL_BUTTON_TEXT);
		cancelButton.setBackground(
				new Background(new BackgroundFill(CANCEL_BUTTON_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
		cancelButton.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		cancelButton.setOnAction(e -> {
			close();
		});
		this.cancelButton = cancelButton;
		return cancelButton;
	}

	public Label createTitle(String titleText) {
		title = new Label(titleText);
		title.setFont(new Font(22));
		return title;
	}

	/**
	 * Creates a styled text field node and adds it to the popup's content.
	 */
	public TextField createTextField(String text) {
		TextField textField = new TextField(text);
		textField.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		textField.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		addContent(textField);
		return textField;
	}

	/**
	 * Creates a label and adds it to the popup's content. Then creates a text field
	 * using {@code createTextFeild}
	 * 
	 * @return the text field.
	 */
	public TextField createTextFieldWithLabel(String labelText, String fieldText) {
		Label label = new Label(labelText);
		addContent(label);
		return createTextField(fieldText);
	}
}
