package question1;

import java.util.TreeMap;
import java.util.Iterator;
import java.util.Map;

public class AssociationTable<T extends Comparable<T>, V> {
	private Map<T, V> map;

	public AssociationTable() {
		map = new TreeMap<>();
	}

	/**
	 * Creates an assiciation table with pairs according to the {@code keys} and
	 * {@code values} params.
	 * 
	 * @throws IllegalArgumentException if keys and values arrays are not of the
	 *                                  same size
	 */
	public AssociationTable(T[] keys, V[] values) throws IllegalArgumentException {
		if (keys.length != values.length) {
			throw new IllegalArgumentException();
		}

		int mapLength = keys.length;

		map = new TreeMap<>();
		for (int i = 0; i < mapLength; i++) {
			add(keys[i], values[i]);
		}
	}

	/**
	 * Add a new pair of key-value.
	 * 
	 * @param newKey   the key to add
	 * @param newValue the value of the key to add
	 */
	public void add(T newKey, V newValue) {
		map.put(newKey, newValue);
	}

	/**
	 * Get a value by key.
	 * 
	 * @param key to get the value of.
	 * @return the value to which the specified key is mapped, or null if the key
	 *         doesn't exist.
	 */
	public Object get(T key) {
		return map.get(key);
	}

	/**
	 * @param key
	 * @return whether or not the key was found.
	 */
	public boolean contains(T key) {
		return map.containsKey(key);
	}

	/**
	 * Remove {@code key} (and its value) from the map.
	 * 
	 * @param key to remove
	 * @return whether or not the key was found and removed successfully from map.
	 */
	public boolean remove(T key) {
		return map.remove(key) != null;
	}

	/**
	 * @return the number of pairs in map
	 */
	public int size() {
		return map.size();
	}

	/**
	 * @return an iterator which iterates on the keys of the map.
	 */
	public Iterator<T> keyIterator() {
		return map.keySet().iterator();
	}

	@Override
	public String toString() {
		String msg = "{\n";
		for (Map.Entry<T, V> pair : map.entrySet()) {
			T key = pair.getKey();
			Object value = pair.getValue();
			msg += "\t" + key + ": " + value + ",\n";
		}
		msg += "}";
		return msg;
	}

}
