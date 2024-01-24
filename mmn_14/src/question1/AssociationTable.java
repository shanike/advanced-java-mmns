package question1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssociationTable<T extends Comparable<T>> {
	private Map<T, Object> map;

	public AssociationTable() {
		map = new HashMap<>();
	}

	/**
	 * Creates an assiciation table with pairs according to the {@code keys} and
	 * {@code values} params.
	 * 
	 * @throws IllegalArgumentException if keys and values arrays are not of the
	 *                                  same size
	 */
	public AssociationTable(ArrayList<T> keys, ArrayList<?> values) throws IllegalArgumentException {
		if (keys.size() != values.size()) {
			throw new IllegalArgumentException();
		}

		map = new HashMap<>();
		for (int i = 0; i < keys.size(); i++) {
			map.put(keys.get(i), values.get(i));
		}
	}

	@Override
	public String toString() {
		String msg = "{\n";
		for (Map.Entry<T, Object> pair : map.entrySet()) {
			T key = pair.getKey();
			Object value = pair.getValue();
			msg += "    " + key + ": " + value + ",\n";
		}
		msg += "}";
		return msg;
	}

}
