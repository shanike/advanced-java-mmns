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

	static private final int INITIAL_QUESTION_INDEX = 0;
	private int currQuestionIndex = INITIAL_QUESTION_INDEX;

	private ArrayList<RadioButton> answerButtons = new ArrayList<RadioButton>();

	private ArrayList<Label> resultsLabels = new ArrayList<Label>();

	static private final int INITIAL_SCORE = 0;
	private int score = INITIAL_SCORE;

	private final String NEXT_BUTTON_TEXT = "NEXT";

	@FXML
	private GridPane appContainer;

	@FXML
	private Label questionLabel;

	@FXML
	private Button nextButton;

	@FXML
	private GridPane answersGrid;

	public void initialize() {
		answersGrid.setPrefWidth(appContainer.getPrefWidth());
		setQuestions();
		initializeTest();
	}

	private void initializeTest() {
		currQuestionIndex = INITIAL_QUESTION_INDEX;
		score = INITIAL_SCORE;
		initializeAnswerNodes();
		initializeNextButton();
		showCurrentQuestion();
		answersGrid.setAlignment(Pos.CENTER);
		answersGrid.setPadding(new Insets(10, 50, 10, 50));
	}

	/**
	 * Set the questions list with pre-defined questions.
	 */
	private void setQuestions() {
		questions.add(new Question("באיזו שנה לבריאת העולם נולד אברהם אבינו?", "1948", "1000", "1", "1722"));
		questions.add(new Question("A question!? The answer is 2", "2", "not 2", "another not 2", "3"));
		questions.add(
				new Question("What is the capital city of Australia?", "Canberra", "Sydney", "Brisbane", "Melbourne"));
		questions.add(new Question("In which year did the Titanic sink?", "1912", "1905", "1920", "1920"));
		questions.add(new Question("Who wrote the play \"Romeo and Juliet\"?", "William Shakespeare", "Charles Dickens",
				"Jane Austen", "Mark Twain"));
		questions.add(new Question("What is the chemical symbol for gold?", "Au", "Cu", "Fe", "Ag"));
		questions.add(new Question("Which planet is known as the Red Planet?", "Mars", "Venus", "Jupiter", "Saturn"));
	}

	/**
	 * Render empty answer nodes to the screen.
	 */
	private void initializeAnswerNodes() {
		removeResultLabels();
		for (int i = 0; i < Question.getNumberOfAnswers(); i++) {
			RadioButton btn = new RadioButton();
			/*
			 * For UX (User Experience): Set each button to a **third** of the grid, so a
			 * click close-enough to the button will also trigger the button's onClick.
			 * 
			 * - The literal number here is a 3 for dividing by 3 (i.e a third). If extracting
			 * it to a final int, it would be named "THREE".
			 */
			btn.setPrefWidth(answersGrid.getPrefWidth() / 3);
			answerButtons.add(btn);
			answersGrid.add(btn, 0, i);
		}
	}

	private void removeAnswerNodes() {
		answersGrid.getChildren().removeAll(answerButtons);
		answerButtons.removeAll(answerButtons);
	}

	private void removeResultLabels() {
		answersGrid.getChildren().removeAll(resultsLabels);
		resultsLabels.removeAll(resultsLabels);
	}

	/**
	 * Set next button properties
	 */
	private void initializeNextButton() {
		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				handleNextButtonClick();
			}
		});
		nextButton.setText(NEXT_BUTTON_TEXT);
	}

	/**
	 * Set all buttons to {@code isDisabled}
	 */
	private void setAnswerButtonsDisabled(boolean isDisabled) {
		for (RadioButton btn : answerButtons) {
			btn.setDisable(isDisabled);
		}
	}

	private void setNextButtonDisaled(boolean isDisabled) {
		nextButton.setDisable(isDisabled);
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

		// Set question label
		setCurrentQuestionLabel();

		// Set answer buttons
		setCurrentQuestionAnswers();

		// Enable answer buttons
		setAnswerButtonsDisabled(false);

		// Disable next button
		setNextButtonDisaled(true);

		// Randomize answers order
		Collections.shuffle(answerButtons);
	}

	private void setCurrentQuestionLabel() {
		Question currentQ = questions.get(currQuestionIndex);
		questionLabel.setText(currentQ.getQuestion());

	}

	private void setCurrentQuestionAnswers() {
		Question currentQ = questions.get(currQuestionIndex);
		ArrayList<Integer> shuffledAnswersIndexes = getShuffledAnswerIndexes();

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
			incorrectButton.setOnAction(createAnswerClickHandler(false));
			incorrectAnswerCounter++;
		}

		// Set last button to be correct-answer-button
		RadioButton correctButton = answerButtons.get(shuffledAnswersIndexes.get(correctAnswerIndex));
		correctButton.setText(currentQ.getCorrectAnswer());
		correctButton.setOnAction(createAnswerClickHandler(true));
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
		setAnswerButtonsDisabled(true);
		setNextButtonDisaled(false);
		showAnswerResponseAlert(isCorrect);
		if (isCorrect) {
			score++;
		}
	}

	/**
	 * Creates an event handler and sets the handleAnswerClick as the handler with
	 * the provided {@code isCorrect} argument.
	 */
	private EventHandler<ActionEvent> createAnswerClickHandler(boolean isCorrect) {
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

	void handleNextButtonClick() {
		resetSelection();
		incCurrentQuestionIndex();
		showCurrentQuestion();
	}

	/**
	 * Increment {@code currentQ} attribute by one.
	 */
	private void incCurrentQuestionIndex() {
		this.currQuestionIndex++;
	}

	/**
	 * After user answered all questions in test
	 */
	private void handleTestResults() {
		removeAnswerNodes();
		nextButton.setText("RE-TAKE TEST");
		nextButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				initializeTest();
			}
		});
		questionLabel.setText("You finished the test! Great job");
		addCenteredLabelToAnswersGrid("Your score is");
		double scorePerc = calculateScorePercentage();
		String scoreToDisplay = Double.toString(formatToTwoDecimals(scorePerc));
		addCenteredLabelToAnswersGrid(scoreToDisplay);
		if (scorePerc >= 70) {
			addCenteredLabelToAnswersGrid("Excellent!");
		}
	}

	private void addCenteredLabelToAnswersGrid(String text) {
		Label l = new Label(text);
		l.setPrefWidth(answersGrid.getWidth());
		l.setAlignment(Pos.CENTER);
		answersGrid.add(l, 0, answersGrid.getChildren().size());
		resultsLabels.add(l);
	}

	/**
	 * Calculates the score and converts it to percentage
	 * 
	 * @return the calculated score.
	 */
	private double calculateScorePercentage() {
		return ((double) score / questions.size()) * 100;
	}

	/**
	 * @return a double formatted to have only 2 digits after dot
	 */
	private double formatToTwoDecimals(double value) {
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		String formattedValue = decimalFormat.format(value);

		return Double.parseDouble(formattedValue);
	}

}
