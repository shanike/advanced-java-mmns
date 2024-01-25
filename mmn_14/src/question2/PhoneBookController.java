package question2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import question1.IllegalArgumentException;

// TODO: add a new contact
// TODO: remove a contact

// TODO: search by name

public class PhoneBookController {

	private static final String EDIT_BUTTON_TEXT = "Edit";
	private static final String DELETE_BUTTON_TEXT = "Delete";

	private ContactsList contactsList;

	@FXML
	private VBox appContainer;

	@FXML
	private VBox studentsVertBox;

	@FXML
	void handleNewContactClick(ActionEvent event) {
		// TODO: new contact
	}

	public void initialize() {
		setContactsList();
		renderContactsList();
	}

	private void setContactsList() {
		String[] names = new String[] { "person1", "person2" };
		String[] phoneNumbers = new String[] { "0555", "0554" };

		try {
			contactsList = new ContactsList(names, phoneNumbers);
		} catch (IllegalArgumentException e) {
			System.err.println(
					"ERROR creating contacts list. falling back to an empty list. The error: " + e.getMessage());
			contactsList = new ContactsList();
		}

//		Iterator<String> contactsIt = contactsList.createNamesIterator();
//		while (contactsIt.hasNext()) {
//			String contactName = contactsIt.next();
//			System.out.println(contactName + ": " + contactsList.phoneNumberByName(contactName));
//		}
	}

	private void handleUpdateContact(String contactName) {
		final Popup popup = new Popup();

		VBox vbox = new VBox();
		vbox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		vbox.setPadding(new Insets(20));
		vbox.setSpacing(20);

		Label label = new Label("Edit " + contactName + "'s phone number:");

		String currentPhoneNumber = contactsList.phoneNumberByName(contactName);
		TextField newPhoneNumberField = new TextField(currentPhoneNumber);
		newPhoneNumberField
				.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
		newPhoneNumberField.setBorder(new Border(
				new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

		Button saveButton = new Button("Save");
		saveButton.setBackground(
				new Background(new BackgroundFill(Color.valueOf("#ADD8E6"), CornerRadii.EMPTY, Insets.EMPTY)));

		Button cancelButton = new Button("Cancel");
		cancelButton.setBackground(
				new Background(new BackgroundFill(Color.valueOf("#FFB6C1"), CornerRadii.EMPTY, Insets.EMPTY)));

		saveButton.setOnAction(e -> {
			contactsList.put(contactName, newPhoneNumberField.getText());
			renderContactsList();
			popup.hide();
		});

		cancelButton.setOnAction(e -> {
			popup.hide();
		});

		vbox.getChildren().addAll(label, newPhoneNumberField, saveButton, cancelButton);
		popup.getContent().addAll(vbox);

		popup.show(studentsVertBox, 0, 0);
		popup.centerOnScreen();
	}

	private void deleteContact(String contactName) {
		contactsList.remove(contactName);
		renderContactsList();
	}

	private void renderContactsList() {
		clearContactsList();
		Iterator<String> contactsIt = contactsList.createNamesIterator();
		int index = 0;
		while (contactsIt.hasNext()) {
			index++;
			String contactName = contactsIt.next();
			String contactPhoneNumber = contactsList.phoneNumberByName(contactName);
			renderContact(index, contactName, contactPhoneNumber);
		}

	}
	
	private void clearContactsList() {
		studentsVertBox.getChildren().removeAll(studentsVertBox.getChildren());
	}

	private void renderContact(int index, String contactName, String contactPhoneNumber) {
		Text contactText = createContactText(index, contactName, contactPhoneNumber);
		Button contactEditButton = createContactEditButton(contactName);
		Button contactDeleteButton = createContactDeleteButton(contactName);

		HBox contactRow = createContactRow();
		contactRow.getChildren().addAll(contactText, contactEditButton, contactDeleteButton);

		studentsVertBox.getChildren().add(contactRow);
	}

	private Text createContactText(int index, String contactName, String contactPhoneNumber) {
		String text = index + ") " + contactName + ": " + contactPhoneNumber;
		return new Text(text);
	}

	private Button createContactEditButton(String contactName) {
		Button contactEditButton = new Button(EDIT_BUTTON_TEXT);
		contactEditButton.setOnMouseClicked(e -> handleUpdateContact(contactName));
		return contactEditButton;
	}

	private Button createContactDeleteButton(String contactName) {
		Button contactDeleteButton = new Button(DELETE_BUTTON_TEXT);
		contactDeleteButton.setOnMouseClicked(e -> deleteContact(contactName));
		return contactDeleteButton;
	}

	private HBox createContactRow() {
		HBox contactRow = new HBox();
		contactRow.setAlignment(Pos.CENTER);
		contactRow.setSpacing(20);
		return contactRow;
	}

}
