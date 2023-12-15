package question1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {
	/**
	 * @REFACTOR: this can be changed to a {@code Set} data type. I'm not gonna do
	 *            so because I'm not sure it's allowed.
	 */
	private final static char[] ALPHABET = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
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
	 * Start playing the game! Prints all game components
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
	 * Prints and handles the "guessing" step, where user needs to guess the word
	 */
	public void handleSingleGuessing() {
		System.out.println("\n" + getPrettyHiddenWord());
		System.out.println("\n* Letters used: " + getPrettyLettersUsed());

		// Prompt user to guess a letter
		char guess = scanValidGuess();

		// Update usedLetters list
		addToUsedLetters(guess);

		// Update chosenWord
		boolean didUncover = chosenWord.uncoverLetter(guess);
		if (didUncover) {
			System.out.println("\nNice!");
		} else {
			System.out.println("\nHmm, try another guess");
		}
		// Increment guesses count
		guessesCount++;
	}

	/**
	 * Prints and handles when "success" step, when the user successfully guessed
	 * the word
	 */
	public void handleSuccess() {
		System.out.println("\n" + getPrettyHiddenWord() + "\n");
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
			String userInput = scanner.nextLine();
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
		if (Arrays.toString(ALPHABET).indexOf(Character.toUpperCase(guessChar)) == -1) {
			System.out.println("Invalid input: please enter an English letter");
			return INVALID_LETTER;
		}
		return guessChar;
	}

	/**
	 * @return a pretty, spaced-out string, representing the hidden word
	 */
	private String getPrettyHiddenWord() {
		String[] wordChars = chosenWord.getHiddenWord().split("");
		String prettyHiddenWord = wordChars[0];
		for (int i = 1; i < wordChars.length; i++) {
			String wordChar = wordChars[i];
			prettyHiddenWord += " " + wordChar;
		}
		return prettyHiddenWord;
	}

	/**
	 * @return a pretty, spaced-out and comma-separated string of a list of the used
	 *         letters
	 */
	private String getPrettyLettersUsed() {
		if (lettersUsed.size() == 0) {
			return "No letters were used yet (:";
		}
		String prettyUsedLetters = lettersUsed.get(0).toString();
		for (int i = 1; i < lettersUsed.size(); i++) {
			Character wordChar = lettersUsed.get(i);
			prettyUsedLetters += ", " + wordChar;
		}
		return prettyUsedLetters;
	}

	/**
	 * Adds the {@code letter} to the letterUsed array, unless the letter is already
	 * there.
	 */
	private void addToUsedLetters(char letter) {
		if (lettersUsed.indexOf(letter) == -1) {
			lettersUsed.add(letter);
		}
	}

}
