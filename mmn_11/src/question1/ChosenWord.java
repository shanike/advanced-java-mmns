package question1;

/**
 * The ChosenWord class hold all logic for a random word (generate with a
 * WordBank instance), which is the chosen word. And a {@code String} that
 * represents the chosen word for the user to "guess"
 * 
 * Note: Debated whether the name "ChosenWord" is correct for storing both the
 * random-word and the hidden-word. But if the hidden-word logic would have been
 * in the Game class, 1) there would be nothing here, and 2) the Game class
 * would have been too big
 */
public class ChosenWord {

	private final static String HIDDEN_CHAR = "_";
	private final static String NULL_CHAR = "\0";

	// A WordBank instance per ChosenWord instance
	private final WordBank wordBank = new WordBank();

	private String word;
	private String hiddenWord;

	public ChosenWord() {
		word = wordBank.getRandomWord();
		hiddenWord = populateWith(HIDDEN_CHAR, word.length());
	}

	public String getHiddenWord() {
		return hiddenWord;
	}

	/**
	 * Uncovers the given {@code letter} from the {@code hiddenWord} and
	 * 
	 * @return the number of characters that were uncovered
	 */
	public int uncoverLetter(char letter) {
		int uncoveredCount = 0;
		int index = word.indexOf(letter);
		while (index >= 0) {
			uncoveredCount++;	
			hiddenWord = hiddenWord.substring(0, index) + letter + hiddenWord.substring(index + 1);
			index = word.indexOf(letter, index + 1);
		}
		return uncoveredCount;
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
