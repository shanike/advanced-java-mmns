package question1;

import java.util.Random;

public class WordBank {
	private final static String[] WORDS_LIST = new String[] { "hi" };
	// { "hello", "bye", "israel", "hebrew", "english", "open", "university",
	// "java", "typescript" };

	public String getRandomWord() {
		Random r = new Random();
		int randomIndex = r.nextInt(WORDS_LIST.length);
		return WORDS_LIST[randomIndex];
	}
}
