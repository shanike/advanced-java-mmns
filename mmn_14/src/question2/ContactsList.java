package question2;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import question1.IllegalArgumentException;

public class ContactsList {
	/**
	 * The keys of the map are the names and the values are the phone numbers.
	 */
	private Map<String, String> contacts;

	public ContactsList() {
		contacts = new TreeMap<String, String>();
	}

	/**
	 * 
	 * @param names
	 * @param phoneNumbers
	 * @throws IllegalArgumentException if names and phoneNumbers array are not the
	 *                                  same length.
	 */
	public ContactsList(String[] names, String[] phoneNumbers) throws IllegalArgumentException {
		contacts = new TreeMap<String, String>();
		if (names.length != phoneNumbers.length) {
			throw new IllegalArgumentException();
		}
		int listLength = names.length;
		for (int i = 0; i < listLength; i++) {
			contacts.put(names[i], phoneNumbers[i]);
		}
	}

	public void put(String name, String phoneNumber) {
		contacts.put(name, phoneNumber);
	}

	public Iterator<String> createNamesIterator() {
		return contacts.keySet().iterator();
	}

	public String phoneNumberByName(String name) {
		return contacts.get(name);
	}

	public void remove(String name) {
		contacts.remove(name);
	}
}
