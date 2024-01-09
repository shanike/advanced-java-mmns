package question1;

import java.util.ArrayList;

public class Question {
	static private int NUMBER_OF_ANSWERS = 4;
	static private int NUMBER_OF_INCORRECT_ANSWERS = 3;
	private String question;
	private String correctAnswer;
	private String incorrectAnswer1;
	private String incorrectAnswer2;
	private String incorrectAnswer3;

	public Question(String question, String correctAnswer, String incorrectAnswer1, String incorrectAnswer2,
			String incorrectAnswer3) {
		this.question = question;
		this.correctAnswer = correctAnswer;
		this.incorrectAnswer1 = incorrectAnswer1;
		this.incorrectAnswer2 = incorrectAnswer2;
		this.incorrectAnswer3 = incorrectAnswer3;
	}

	public String getQuestion() {
		return this.question;
	}

	public String getCorrectAnswer() {
		return this.correctAnswer;
	}
	public static int getNumberOfIncorrectAnswers() {
		return NUMBER_OF_INCORRECT_ANSWERS;
	}
	public static int getNumberOfAnswers() {
		return NUMBER_OF_ANSWERS;
	}

	public ArrayList<String> getIncorrectAnswers() {
		ArrayList<String> list = new ArrayList<String>();
		list.add(incorrectAnswer1);
		list.add(incorrectAnswer2);
		list.add(incorrectAnswer3);
		return list;
	}


	public String toString() {
		return "Question: " + this.question + "; Correct answer: " + this.correctAnswer + "; Incorrect answers: "
				+ this.incorrectAnswer1 + ", " + this.incorrectAnswer2 + ", " + this.incorrectAnswer3;
	}
}
