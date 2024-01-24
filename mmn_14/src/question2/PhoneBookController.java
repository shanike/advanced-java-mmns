package question2;

import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import question1.IllegalArgumentException;

// TODO: add a new contact
// TODO: remove a contact
// TODO: update contact's phone number

// TODO: search by name

public class PhoneBookController {

	private ContactsList contactsList;

	@FXML
	private VBox studentsVertBox;

	public void initialize() {
		String[] names = new String[] { "person1", "person2" };
		String[] phoneNumbers = new String[] { "0555", "0554" };

		try {
			contactsList = new ContactsList(names, phoneNumbers);
		} catch (IllegalArgumentException e) {
			System.err.println(
					"ERROR creating contacts list. falling back to an empty list. The error: " + e.getMessage());
			contactsList = new ContactsList();
		}

		Iterator<String> contactsIt = contactsList.createNamesIterator();

		while (contactsIt.hasNext()) {
			String contactName = contactsIt.next();
			System.out.println(contactName + ": " + contactsList.phoneNumberByName(contactName));
		}

		// TODO render in javafx a label inside the contactsVertBox

	}

}
