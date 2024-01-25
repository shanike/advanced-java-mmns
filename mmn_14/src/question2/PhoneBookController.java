package question2;

import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import question1.IllegalArgumentException;

public class PhoneBookController {

	private static final String EDIT_BUTTON_TEXT = "Edit";
	private static final String DELETE_BUTTON_TEXT = "Delete";

	private ContactsList contactsList;

	@FXML
	private VBox appContainer;

	@FXML
	private VBox studentsVertBox;

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
	}

	@FXML
	void handleNewContactClick(ActionEvent event) {
		final ActionPopup popup = new ActionPopup(studentsVertBox);

		// Title
		popup.createTitle("New contact");

		// Fields
		TextField contactNameField = popup.createTextFieldWithLabel("Contact Name:", "");
		TextField contactPhoneNumberField = popup.createTextFieldWithLabel("Contact Phone Number:", "");

		// Cancel button
		popup.createCancelButton();
		// Save button
		Button saveButton = popup.createSaveButton();
		saveButton.setOnAction(e -> {
			contactsList.put(contactNameField.getText(), contactPhoneNumberField.getText());
			renderContactsList();
			popup.close();
		});

		popup.open();
	}

	@FXML
	void handleSearchChange(KeyEvent event) {
		TextField target = (TextField) event.getTarget();
		renderContactsList(target.getText());
	}

	private void handleUpdateContact(String contactName) {
		final ActionPopup popup = new ActionPopup(studentsVertBox);

		// Title
		popup.createTitle("Edit " + contactName + "'s phone number:");

		// Phone number text field
		String currentPhoneNumber = contactsList.phoneNumberByName(contactName);
		TextField newPhoneNumberField = popup.createTextField(currentPhoneNumber);

		// Cancel button
		popup.createCancelButton();
		// Save button
		Button saveButton = popup.createSaveButton();
		saveButton.setOnAction(e -> {
			contactsList.put(contactName, newPhoneNumberField.getText());
			renderContactsList();
			popup.close();
		});

		// Open
		popup.open();

	}

	private void deleteContact(String contactName) {
		contactsList.remove(contactName);
		renderContactsList();
	}

	/**
	 * Render ALL contacts
	 */
	private void renderContactsList() {
		renderContactsList("");
	}

	/**
	 * Render contacts which match the param {@code search}
	 */
	private void renderContactsList(String search) {
		clearContactsList();
		Iterator<String> contactsIt = contactsList.createNamesIterator();
		int index = 0;
		while (contactsIt.hasNext()) {
			String contactName = contactsIt.next();
			if (contactName.contains(search)) {
				index++;
				String contactPhoneNumber = contactsList.phoneNumberByName(contactName);
				renderContact(index, contactName, contactPhoneNumber);
			}
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
