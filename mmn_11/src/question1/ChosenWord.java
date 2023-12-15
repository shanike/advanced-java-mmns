package question1;

public class ChosenWord {

	private final static String HIDDEN_CHAR = "_";
	private final static String NULL_CHAR = "\0";

	// A WordBank instance per ChosenWord instance
	private final WordBank wordBank = new WordBank();

	private String word;
	private String hiddenWord;

	public ChosenWord() {
		word = wordBank.getRandomWord();
		System.out.println("#DEBUG# word: " + word);
		hiddenWord = populateWith(HIDDEN_CHAR, word.length());
	}

	public String getHiddenWord() {
		return hiddenWord;
	}

	/**
	 * Uncovers the given {@code letter} from the {@code hiddenWord} and
	 * 
	 * @return whether or not the {@code letter} was in the chosen {@code word}
	 */
	public boolean uncoverLetter(char letter) {
		boolean foundLetter = false;
		int index = word.indexOf(letter);
		while (index >= 0) {
			foundLetter = true;
			hiddenWord = hiddenWord.substring(0, index) + letter + hiddenWord.substring(index + 1);
			index = word.indexOf(letter, index + 1);
		}
		return foundLetter;
	}

	/**
	 * @return whether or not the {@code hiddenWord} has fully been uncovered
	 */
	public boolean isWordUncovered() {
		return hiddenWord.indexOf(HIDDEN_CHAR) == -1;
	}

	/**
	 * @param str           the string to multiply
	 * @param numberOfTimes number of occurrences of the {@code str}
	 * @return a string in which the {@code str} string appears an X number of times
	 *         (according to {@code numOfTimes}).
	 * @example {@code populateWith("*", 5); // == "*****"}
	 */
	private String populateWith(String str, int numberOfTimes) {
		return new String(new char[numberOfTimes]).replace(NULL_CHAR, str);
	}
}
