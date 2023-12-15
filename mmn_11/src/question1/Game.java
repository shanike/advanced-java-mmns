package question1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Our main class which runs rounds of our game!
 */
public class Game {
	/**
	 * @refactor: this can be changed to a {@code Set} data type. Although i'm not
	 *            gonna do so because I'm not 100% sure it's allowed.
	 */
	private final static ArrayList<Character> ALPHABET = new ArrayList<Character>(Arrays.asList('A', 'B', 'C', 'D', 'E',
			'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'));
	private final static char INVALID_LETTER = '!';

	private ChosenWord chosenWord = new ChosenWord();

	private int guessesCount = 0;
	private ArrayList<Character> lettersUsed = new ArrayList<Character>();

	// Single scanner for the the whole class to prevent conflicts
	private Scanner scanner = new Scanner(System.in);

	/**
	 * Plays the game and at the end prompts the user for a new round
	 */
	public static void main(String[] args) {
		// Greet user
		System.out.println("\nHello!");

		while (true) {
			Game game = new Game();

			// Start playing the game
			game.start();

			// Prompt user with a new game choice
			System.out.println(
					"\nWould you like to play again? \n(Enter Y or YES for a new game and any other key to exit)");

			String userInput = game.scanner.nextLine().trim().toUpperCase();

			if (!userInput.equals("Y") && !userInput.equals("YES")) {
				System.out.println("Ok, bye bye!\n");
				return;
			}
			System.out.println("Great, let's do it!\n");
		}
	}

	/**
	 * Start playing the game! Prints all components for a single game
	 * 
	 * * The game is case-insensitive
	 */
	public void start() {
		System.out.println("Welcome to our game!\n\nGuess the word!\n===============");
		while (!chosenWord.isWordUncovered()) {
			// user didn't guess the whole word
			handleSingleGuessing();
		}
		handleSuccess();
	}

	/**
	 * Prints and handles the "guessing" step, where user needs to guess a letter
	 */
	public void handleSingleGuessing() {
		prettyPrintHiddenWord();
		prettyPrintUnusedLetters();

		// Prompt user to guess a letter
		char guess = scanValidGuess();

		// Update used-letters list
		boolean isNewGuess = addToUsedLetters(guess);

		System.out.print("\n> ");
		if (!isNewGuess) {
			System.out.println("Hmm, you already guessed this letter though... let's try a new guess!");
		} else {
			// Update chosenWord
			int uncoveredCount = chosenWord.uncoverLetter(guess);
			if (uncoveredCount <= 0) {
				System.out.println("Hmm, nope... Let's try another guess!");
			} else {
				String msg = "Nice! You uncovered " + uncoveredCount + " letter";
				if (uncoveredCount > 1) {
					msg += "s";
				}
				msg += "!";
				System.out.println(msg);
			}
		}
		// Increment guesses count
		guessesCount++;
	}

	/**
	 * Prints and handles when "success" step, when the user successfully guessed
	 * the word
	 */
	public void handleSuccess() {
		prettyPrintHiddenWord();
		System.out.println("Hurray! You guessed the word!");
		System.out.print("The word was: \"" + chosenWord.getHiddenWord() + "\"!");
		String guessesText = "guess";
		if (guessesCount >= 1) {
			/*
			 * Even though a word should contain more than one letter, and therefore
			 * guessesCount should always be more that 1... Just in case ¯\_(ツ)_/¯
			 */
			guessesText += "es";
		}
		System.out.println(" and it took you " + guessesCount + " " + guessesText + "!");

	}

	/**
	 * Prompts the user to guess a letter until user enter a valid guess.
	 * 
	 * @return the valid letter
	 */
	private char scanValidGuess() {
		while (true) {
			System.out.print("\nWhat's your guess? ");
			String userInput = scanner.nextLine().toLowerCase();
			char guessChar = validateGuess(userInput);
			if (guessChar != INVALID_LETTER) {
				return guessChar;
			}
		}
	}

	/**
	 * @return the valid letter, or an invalid indicator (= the INVALID_LETTER
	 *         final)
	 */
	private char validateGuess(String guessStr) {
		if (guessStr.length() != 1) {
			System.out.println("Invalid input: please enter exactly one letter");
			return INVALID_LETTER;
		}
		char guessChar = guessStr.charAt(0);
		// Validate the guess is in the alphabet
		if (ALPHABET.indexOf(Character.toUpperCase(guessChar)) == -1) {
			System.out.println("Invalid input: please enter an English letter");
			return INVALID_LETTER;
		}
		return guessChar;
	}

	/**
	 * Prints a pretty, spaced-out string; representing the hidden word
	 */
	private void prettyPrintHiddenWord() {
		String[] wordChars = chosenWord.getHiddenWord().split("");
		String prettyHiddenWord = wordChars[0];
		for (int i = 1; i < wordChars.length; i++) {
			String wordChar = wordChars[i];
			prettyHiddenWord += " " + wordChar;
		}
		System.out.println("\n" + prettyHiddenWord + "\n");
	}

	/**
	 * @return a list of letters that were not used yet
	 */
	private ArrayList<Character> getUnusedLetters() {
		ArrayList<Character> unusedLetters = new ArrayList<Character>();

		// Loop over alphabet
		for (Character letter : ALPHABET) {
			// For every letter, check if used
			boolean wasLetterUsed = false;
			if (lettersUsed.size() != 0) {
				for (Character usedLetter : lettersUsed) {
					if (letter.equals(Character.toUpperCase(usedLetter))) {
						// This alphabet letter was used
						wasLetterUsed = true;
						break;
					}
				}
			}

			if (!wasLetterUsed) {
				unusedLetters.add(letter);
			}
		}

		return unusedLetters;
	}

	/**
	 * Prints a pretty, spaced-out and comma-separated string; representing the list
	 * of letters not used yet
	 */
	private void prettyPrintUnusedLetters() {
		System.out.print("* Letters not used: ");
		ArrayList<Character> unusedLetters = getUnusedLetters();
		String prettyUnsedLetters = "None..."; // Initialize
		if (unusedLetters.size() > 0) {
			prettyUnsedLetters = unusedLetters.get(0).toString();
			for (int i = 1; i < unusedLetters.size(); i++) {
				Character unusedLetter = unusedLetters.get(i);
				prettyUnsedLetters += ", " + unusedLetter;
			}
		}

		System.out.println(prettyUnsedLetters);
	}

	/**
	 * Adds the {@code letter} to the letterUsed array, unless the letter is already
	 * there.
	 * 
	 * @return whether the letter wasn't used yet or not
	 */
	private boolean addToUsedLetters(char letter) {
		if (lettersUsed.indexOf(letter) == -1) {
			lettersUsed.add(letter);
			return true;
		}
		return false;
	}

}
