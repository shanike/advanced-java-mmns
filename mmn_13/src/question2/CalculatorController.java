package question2;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class CalculatorController {
	@FXML
	private GridPane appGrid;

	@FXML
	private GridPane outputGrid;

	@FXML
	private Button outputButton;

	@FXML
	private Label outputLabel;

	@FXML
	private GridPane inputGrid;

	/* ------------------------- end of FXML attributes ------------------------- */

	private static final Insets BUTTONS_PADDING = new Insets(10, 15, 10, 15);
	private static final double BUTTONS_BORDER_RADIUS = 8;
	private static final Color OUTPUT_BG_COLOR = Color.rgb(158, 200, 185);

	private static final int OUTPUT_ROW_HEIGHT = 50;
	private static final int BUTTONS_HEIGHT = OUTPUT_ROW_HEIGHT;
	private static final int BUTTONS_WIDTH = 70;

	private static final int INPUT_ROWS = 4;
	private static final int INPUTS_PER_ROW = 4;
	private static final String MINUS = "-";
	private static final String PLUS = "+";
	private static final String TIMES = "*";
	private static final String DIVIDE = "/";
	private static final String PLUS_MINUS = "+/-";
	private static final String DOT = ".";
	private static final char DOT_CHAR = '.';
	private static final char ZERO_CHAR = '0';

	private static final ArrayList<String> ACTIONS = new ArrayList<String>() {
		{
			add(MINUS);
			add(PLUS);
			add(TIMES);
			add(DIVIDE);
		}
	};

	private static final String[][] INPUTS = { { "1", "2", "3", PLUS }, { "4", "5", "6", MINUS },
			{ "7", "8", "9", TIMES }, { PLUS_MINUS, "0", DOT, DIVIDE } };

	private StringBuilder output = new StringBuilder();

	private boolean isOutputShowingResult = false;

	/* ------------------------- end of attributes ------------------------- */

	public void initialize() {
		resetOutput();
		setOutputGridDimensions();
		setOutputLabelProperties();
		updateOutputLabel();
		setSolveButton();
		createInputButtons();
	}

	/**
	 * Reset output to initial value: an empty {@code StringBuilder}.
	 */
	private void resetOutput() {
		output = new StringBuilder();
	}

	/**
	 * Sets the width and height of the output grid
	 */
	private void setOutputGridDimensions() {
		outputGrid.setPrefWidth(appGrid.getPrefWidth());
		outputGrid.setPrefHeight(OUTPUT_ROW_HEIGHT);
	}

	/**
	 * Sets the default width and height to a button
	 */
	private void setButtonDimensions(Button btn) {
		btn.setPrefWidth(BUTTONS_WIDTH);
		btn.setPrefHeight(BUTTONS_HEIGHT);
	}

	/**
	 * Sets the output label UI properties:
	 */
	private void setOutputLabelProperties() {
		// Width & height
		outputLabel.setPrefWidth(outputGrid.getPrefWidth() - (2 * BUTTONS_WIDTH) - BUTTONS_PADDING.getLeft()
				- BUTTONS_PADDING.getRight());
		outputLabel.setPrefHeight(outputGrid.getPrefHeight());

		// Background
		outputLabel
				.setBackground(new Background(new BackgroundFill(OUTPUT_BG_COLOR, new CornerRadii(6), Insets.EMPTY)));

		// Padding
		final int horizontalPadding = 20;
		outputLabel.setPadding(new Insets(BUTTONS_PADDING.getTop(), horizontalPadding, BUTTONS_PADDING.getBottom(),
				horizontalPadding));

	}

	/**
	 * Create styled input buttons in the {@code inputGrid}
	 */
	private void createInputButtons() {
		for (int i = 0; i < INPUT_ROWS; i++) {
			for (int j = 0; j < INPUTS_PER_ROW; j++) {
				Button btn = new Button(INPUTS[i][j]);
				btn.setOnAction(getInputHandler());
				styleButton(btn, Color.rgb(92, 131, 116));
				btn.setTextFill(Color.WHITE);
				if (j == 0) {
					GridPane.setHalignment(btn, HPos.LEFT);
				} else if (j < INPUTS_PER_ROW - 1 - 1) {
					GridPane.setHalignment(btn, HPos.CENTER);
				} else {
					GridPane.setHalignment(btn, HPos.RIGHT);
				}
				inputGrid.add(btn, j, i);
			}
		}
	}

	/**
	 * Style a button with button width&height, with button padding, with background
	 * color and hover effects.
	 * 
	 * @param btn     to style
	 * @param bgColor to apply the button with
	 */
	private void styleButton(Button btn, Color bgColor) {
		// Width & Height
		setButtonDimensions(btn);

		// Padding
		btn.setPadding(BUTTONS_PADDING);

		// Background
		CornerRadii corderRadii = new CornerRadii(BUTTONS_BORDER_RADIUS);
		Color hoverBgColor = lightenColor(bgColor);

		btn.setBackground(new Background(new BackgroundFill(bgColor, corderRadii, Insets.EMPTY)));

		// Background:hover
		btn.setOnMouseEntered(
				e -> btn.setBackground(new Background(new BackgroundFill(hoverBgColor, corderRadii, Insets.EMPTY))));
		btn.setOnMouseExited(
				e -> btn.setBackground(new Background(new BackgroundFill(bgColor, corderRadii, Insets.EMPTY))));

		// Cursor
		btn.setCursor(Cursor.HAND);
	}

	/**
	 * A util function to lighten a color rgb.
	 * 
	 * @param color rgb
	 * @return A lightened color of {@code color}. For being the color on hover for
	 *         example
	 */
	private Color lightenColor(Color color) {
		double red = color.getRed() * 255;
		double green = color.getGreen() * 255;
		double blue = color.getBlue() * 255;

		int lightenedRed = (int) Math.min(red + 10, 255);
		int lightenedGreen = (int) Math.min(green + 10, 255);
		int lightenedBlue = (int) Math.min(blue + 10, 255);

		return Color.rgb(lightenedRed, lightenedGreen, lightenedBlue);
	}

	/**
	 * Sets the output (=solve) button properties
	 */
	private void setSolveButton() {
		// action
		outputButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					calculateExpression(outputLabel.getText());
				} catch (IllegalArgumentException e) {
					showIllegalExpressionAlert();
				}
			}
		});
		styleButton(outputButton, Color.rgb(158, 200, 185));
		GridPane.setHalignment(outputButton, HPos.RIGHT);
	}

	/**
	 * Alert the user that the expression is invalid
	 */
	private void showIllegalExpressionAlert() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Calculator -- Error");
		alert.setHeaderText("The expression is invalid");
		alert.showAndWait();
	}

	/**
	 * @return an event handler to handle validation and changes on any new input
	 */
	private EventHandler<ActionEvent> getInputHandler() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Button btn = (Button) event.getSource();
				String btnText = btn.getText();
				if (isOutputShowingResult) {
					/*
					 * If current output is a result of some other expression, then overide the
					 * whole output with current btnText
					 */
					resetOutput();
				}
				isOutputShowingResult = false;

				boolean isFirstChar = output.length() <= 0;

				switch (btnText) {
				case PLUS_MINUS:
					handlePlusMinusInput();
					break;

				case TIMES:
				case DIVIDE:
				case PLUS:
					handleNonMinusActionInput(btnText, isFirstChar);
					break;

				case MINUS:
					handleMinusInput(btnText, isFirstChar);
					break;

				case DOT:
					handleDotInput(btnText, isFirstChar);
					break;

				default:
					writeCharToOutput(btnText);
					break;
				}

				updateOutputLabel();
			}
		};
	}

	private void handlePlusMinusInput() {
		char firstChar = output.charAt(0);
		if (Character.toString(firstChar).equals(MINUS)) {
			output.deleteCharAt(0);
		} else if (output.charAt(0) != ZERO_CHAR) {
			output.insert(0, MINUS);
		}
	}

	private void handleNonMinusActionInput(String btnText, boolean isFirstChar) {
		// If isFirstChar then don't render the btnText. Unless btnText is a PLUS.
		if (isFirstChar) {
			if (btnText.equals(PLUS)) {
				writeCharToOutput(btnText);
			}
		} else {
			String prevCharInOutput = Character.toString(output.charAt(output.length() - 1));
			boolean isPrevCharAnAction = ACTIONS.indexOf(prevCharInOutput) >= 0;

			if (!isPrevCharAnAction) {
				writeCharToOutput(btnText);
			} else {
				// Prev character is an action
				boolean willBeFirstCharInOutput = output.length() <= 1;
				boolean isPrevPrevAnAction = output.length() >= 2
						? ACTIONS.indexOf(Character.toString(output.charAt(output.length() - 2))) >= 0
						: false;
				if (willBeFirstCharInOutput) {
					if (btnText.equals(PLUS)) {
						replacePrevCharInOutput(btnText);
					}
				} else if (!isPrevPrevAnAction) {
					// Won't be first character && prev prev is not an action character
					replacePrevCharInOutput(btnText);
				}
			}
		}
	}

	private void handleMinusInput(String btnText, boolean isFirstChar) {
		if (isFirstChar) {
			writeCharToOutput(btnText);
			return;
		}

		String prevCharInOutput = Character.toString(output.charAt(output.length() - 1));
		boolean isPrevCharAnAction = ACTIONS.indexOf(prevCharInOutput) >= 0;

		if (!isPrevCharAnAction) {
			writeCharToOutput(btnText);
		} else if (prevCharInOutput.equals(PLUS) || prevCharInOutput.equals(MINUS)) {
			replacePrevCharInOutput(btnText);
		} else {
			writeCharToOutput(btnText);
		}
	}

	private void handleDotInput(String btnText, boolean isFirstChar) {
		if (!isFirstChar) {
			char prevCharInOutput = output.charAt(output.length() - 1);
			if (Character.isDigit(prevCharInOutput) && !isLastNonDigitADot()) {
				writeCharToOutput(btnText);
			}
		}
	}

	/**
	 * Add param {@code s} to output
	 */
	private void writeCharToOutput(String s) {
		output.append(s);
	}

	/**
	 * override the previous char in output with param {@code s}
	 */
	private void replacePrevCharInOutput(String s) {
		if (output.length() > 0) {
			output.deleteCharAt(output.length() - 1);
		}
		output.append(s);
	}

	/**
	 * Set output label to show current {@code output}
	 */
	private void updateOutputLabel() {
		outputLabel.setText(output.toString());
	}

	/**
	 * @return whether or not last non-digit char in output is a dot
	 */
	private boolean isLastNonDigitADot() {
		for (int i = output.length() - 1; i >= 0; i--) {
			char c = output.charAt(i);
			boolean isNonDigit = !Character.isDigit(c);
			if (isNonDigit) {
				char prevNonDigitChar = c; // This is redundant to declare a new {@code char}, but the code documents
											// itself better this way
				return Character.toString(prevNonDigitChar).equals(DOT);
			}
		}
		return false;
	}

	/**
	 * Calculate the expression (to a mathmatical result).
	 * 
	 * @throws IllegalArgumentException if expression is not valid due to being
	 *                                  "cut-off"
	 */
	private void calculateExpression(String expression) throws IllegalArgumentException {
		char firstChar = expression.charAt(0);
		boolean isFirstCharNumeric = Character.isDigit(firstChar);
		if (isFirstCharNumeric) {
			// Set first character to be a PLUS
			expression = PLUS + expression;
		}

		double result = 0;
		char action = expression.charAt(0);
		/**
		 * Stringified number to perform the {@code action} with the {@code result} on.
		 */
		String numberStringified = "";
		for (int i = 1; i < expression.length(); i++) {
			char c = expression.charAt(i);
			boolean isLastChar = i == expression.length() - 1;
			boolean isAction = ACTIONS.indexOf(Character.toString(c)) >= 0;

			if (isLastChar && isAction) {
				throw new IllegalArgumentException("Invalid expression");
			}

			boolean isMinus = Character.toString(c).equals(MINUS);

			if (isMinus) {
				int prevIndex = i - 1;
				if (prevIndex >= 0) {
					char prevC = expression.charAt(i - 1);
					boolean isPrevAnAction = ACTIONS.indexOf(Character.toString(prevC)) >= 0;
					if (isPrevAnAction) {
						/*
						 * If curr char is a MINUS, and prev char is an action, then curr char shouldn't
						 * perform a subtraction action, but rather negate the {@code numberStringified}
						 * ==> act as a non-action
						 */
						isAction = false;
					}
				}
			}
			if (isAction) {
				// Found another action -> calculate prev and then set to curr
				result = calculateAction(Character.toString(action), result, Double.parseDouble(numberStringified));
				action = c;
				numberStringified = "";
			} else {
				// Add digit/dot
				numberStringified += c;
			}

			if (isLastChar) {
				// Calculate last action.
				result = calculateAction(Character.toString(action), result, Double.parseDouble(numberStringified));
			}
		}

		updateOutputWithResult(result);
	}

	private double calculateAction(String action, double rightNum, double leftNum) {
		switch (action) {
		case PLUS:
			return rightNum + leftNum;
		case MINUS:
			return rightNum - leftNum;
		case TIMES:
			return rightNum * leftNum;
		case DIVIDE:
			return rightNum / leftNum;
		default:
			System.err.println("Invalid action! " + action + " " + rightNum + " " + leftNum);
			return 0;
		}
	}

	/**
	 * Format result and display it in outpur label.
	 */
	private void updateOutputWithResult(double result) {
		output = new StringBuilder(Double.toString(result));
		if (output.length() >= 2) {
			boolean isPrevZero = output.charAt(output.length() - 1) == ZERO_CHAR;
			boolean isPrevPrevDot = output.charAt(output.length() - 2) == DOT_CHAR;
			if (isPrevZero && isPrevPrevDot) {
				// If output is actually an int, then remove the zero and the dot.
				output.delete(output.length() - 2, output.length());
			}
		}
		updateOutputLabel();
		isOutputShowingResult = true;
	}
}
