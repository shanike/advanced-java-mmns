package question1;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;

public class MultiChoiceTestController {

	private ArrayList<Question> questions = new ArrayList<Question>();

	static private int INITIAL_QUESTION_INDEX = 0;
	private int currQuestionIndex = INITIAL_QUESTION_INDEX;

	private ArrayList<RadioButton> answerButtons = new ArrayList<RadioButton>();

	static private int INITIAL_SCORE = 0;
	private int score = INITIAL_SCORE;

	@FXML
	private GridPane appContainer;

	@FXML
	private Label questionLabel;

	@FXML
	private Button nextButton;

	@FXML
	private GridPane answersGrid;

	public void initialize() {
		setQuestions();
		initializeAnswerNodes();
		showCurrentQuestion();
		answersGrid.setAlignment(Pos.CENTER);
		answersGrid.setPadding(new Insets(10, 50, 10, 50));
	}

	@FXML
	void handleNextButtonClick(ActionEvent event) {
		resetSelection();
		incCurrentQuestionIndex();
		showCurrentQuestion();
	}

	/**
	 * Set the questions list with pre-defined questions.
	 */
	private void setQuestions() {
		questions.add(new Question("באיזו שנה לבריאת העולם נולד אברהם אבינו?", "1948", "1000", "1", "1722"));
		questions.add(new Question("Question! the answer is 2?", "2", "not 2", "another not 2", "3"));
		questions.add(
				new Question("What is the capital city of Australia?", "Canberra", "Sydney", "Brisbane", "Melbourne"));
//		questions.add(new Question("In which year did the Titanic sink?", "1912", "1905", "1920", "1920"));
//		questions.add(new Question("Who wrote the play \"Romeo and Juliet\"?", "William Shakespeare", "Charles Dickens",
//				"Jane Austen", "Mark Twain"));
//		questions.add(new Question("What is the chemical symbol for gold?", "Au", "Cu", "Fe", "Ag"));
//		questions.add(new Question("Which planet is known as the Red Planet?", "Mars", "Venus", "Jupiter", "Saturn"));
	}

	/**
	 * Render empty answer nodes to the screen.
	 */
	private void initializeAnswerNodes() {
		// Render empty answers to screen
		for (int i = 0; i < Question.getNumberOfAnswers(); i++) {
			RadioButton btn = new RadioButton();
//	        btn.setAlignment(Pos.CENTER);
			answerButtons.add(btn);
			answersGrid.add(btn, 0, i);
		}
	}

	/**
	 * Set all buttons to {@code isDisabled}
	 */
	private void setButtonsDisabled(boolean isDisabled) {
		for (RadioButton btn : answerButtons) {
			btn.setDisable(isDisabled);
		}
	}

	/**
	 * Deselect all answers.
	 */
	private void resetSelection() {
		for (RadioButton btn : answerButtons) {
			btn.setSelected(false);
		}
	}

	/**
	 * Update screen to show current question
	 */
	private void showCurrentQuestion() {

		if (questions.size() <= currQuestionIndex) {
			// No more questions
			handleTestResults();
			return;
		}

		Question currentQ = questions.get(currQuestionIndex);

		// Set question label
		questionLabel.setText(currentQ.getQuestion());

		ArrayList<Integer> shuffledAnswersIndexes = getShuffledAnswerIndexes();
		for (Integer si : shuffledAnswersIndexes) {
			System.out.println("shuffled index: " + si);

		}
		int correctAnswerIndex = 0;
		int incorrectAnswersFirstIndex = correctAnswerIndex + 1;
		int incorrectAnswersLastIndex = shuffledAnswersIndexes.size() - 1;

		// Set first buttons to be the incorrect-answer-buttons
		int incorrectAnswerCounter = 0;
		for (int i = incorrectAnswersFirstIndex; i <= incorrectAnswersLastIndex; i++) {
			int incorrectButtonShuffledIndex = shuffledAnswersIndexes.get(i);
			RadioButton incorrectButton = answerButtons.get(incorrectButtonShuffledIndex);
			String incorrectAnswer = currentQ.getIncorrectAnswers().get(incorrectAnswerCounter);
			incorrectButton.setText(incorrectAnswer);
			incorrectButton.setOnAction(getAnswerClickHandler(false));
			incorrectAnswerCounter++;
		}

		// Set last button to be correct-answer-button
		RadioButton correctButton = answerButtons.get(shuffledAnswersIndexes.get(correctAnswerIndex));
		correctButton.setText(currentQ.getCorrectAnswer());
		correctButton.setOnAction(getAnswerClickHandler(true));

		// Enable buttons
		setButtonsDisabled(false);

		// Randomize order
		Collections.shuffle(answerButtons);
	}

	/**
	 * @return A shuffled list of the answers indexes
	 */
	private ArrayList<Integer> getShuffledAnswerIndexes() {
		ArrayList<Integer> answersOrder = new ArrayList<Integer>();
		for (int i = 0; i < Question.getNumberOfAnswers(); i++) {
			answersOrder.add(i);
		}
		Collections.shuffle(answersOrder);
		return answersOrder;
	}

	/**
	 * Handle the event when user selected an answer
	 * 
	 * @param isCorrect whether the selected answer is the correct answer
	 */
	private void handleAnswerClick(boolean isCorrect) {
		setButtonsDisabled(true);
		showAnswerResponseAlert(isCorrect);
		if (isCorrect) {
			score++;
		}
	}

	/**
	 * Creates an event handler and sets the handleAnswerClick as the handler with
	 * the provided {@code isCorrect} argument.
	 */
	private EventHandler<ActionEvent> getAnswerClickHandler(boolean isCorrect) {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				handleAnswerClick(isCorrect);
			}
		};
	}

	/**
	 * Show an alert to the user with information about whether the answer is
	 * in/correct
	 */
	private void showAnswerResponseAlert(boolean isAnswerCorrect) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("multi-choice-test -- response");
		String text = isAnswerCorrect ? "Correct!" : "Incorrect :/";
		alert.setHeaderText(text);
		alert.showAndWait();
	}

	/**
	 * Increment {@code currentQ} attribute by one.
	 */
	private void incCurrentQuestionIndex() {
		this.currQuestionIndex++;
	}

	private void handleTestResults() {
		answersGrid.getChildren().removeAll(answerButtons);
		answersGrid.getChildren().remove(nextButton);
		questionLabel.setText("You finished the test! Great job");
		Label scoreTitle = new Label("Your score is");
		answersGrid.add(scoreTitle, 0, 0);
		Label score = new Label(Double.toString(calculateScore()));
		answersGrid.add(score, 0, 1);
	}

	/**
	 * Calculates the score and converts it to percentage
	 * @return the calculated score.
	 */
	private double calculateScore() {
		double scorePerc = ((double) score / questions.size()) * 100;
		return formatToTwoDecimals(scorePerc);
	}

	/**
	 * @return a double formatted to have only 2 digits after dot
	 */
    private double formatToTwoDecimals(double value) {
        // 
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedValue = decimalFormat.format(value);

        return Double.parseDouble(formattedValue);
    }


}
