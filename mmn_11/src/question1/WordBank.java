package question1;

import java.util.Random;

/**
 * The WordBank class provides a method to get a random word from a stored
 * {@code final static WORD_LIST}.
 */
public class WordBank {
	private final static String[] WORDS_LIST = new String[] { "hello", "bye", "israel", "hebrew", "english", "open",
			"university", "java", "typescript" };

	public String getRandomWord() {
		Random r = new Random();
		int randomIndex = r.nextInt(WORDS_LIST.length);
		return WORDS_LIST[randomIndex];
	}
}
