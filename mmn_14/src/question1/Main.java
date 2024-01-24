package question1;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		ArrayList<String> keys = new ArrayList<String>();
		keys.add("key2");
		keys.add("key3");
		keys.add("key3");
		keys.add("key1");
		
		ArrayList<String> values = new ArrayList<String>();
		values.add("val2");
		values.add("should be overriden");
		values.add("val4");
		values.add("val1");
		
		AssociationTable<String> assoTable;
		try {
			assoTable = new AssociationTable<String>(keys, values);
			System.out.println(assoTable);
		} catch (IllegalArgumentException e) {
			System.err.println("ERROR creating an association table: " + e.getMessage());
		}
	}
}
