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

//	private static final int INPUTS_PER_ROW = 4;
//	private static final int INPUT_ROWS = 4;
	private static final String MINUS = "-";
	private static final String PLUS = "+";
	private static final String TIMES = "*";
	private static final String DIVIDE = "/";
	private static final String PLUS_MINUS = "+/-";
	private static final String DOT = ".";
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
	 * @param color background color rgb
	 * @return A lightened color of {@code bgColor} for being the background color
	 *         on hover
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
	 * Sets the output (solve) button properties
	 */
	private void setSolveButton() {
		// action
		outputButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					calculateOutput();
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

	private void createInputButtons() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Button btn = new Button(INPUTS[i][j]);
				btn.setOnAction(getInputHandler());
				styleButton(btn, Color.rgb(92, 131, 116));
				btn.setTextFill(Color.WHITE);
				if (j == 0) {
					GridPane.setHalignment(btn, HPos.LEFT);
				} else if (j == 1) {
					GridPane.setHalignment(btn, HPos.CENTER);
				} else {
					GridPane.setHalignment(btn, HPos.RIGHT);
				}
				inputGrid.add(btn, j, i);
			}
		}
	}

	private EventHandler<ActionEvent> getInputHandler() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Button btn = (Button) event.getSource();
				String btnText = btn.getText();
				if (isOutputShowingResult) {
					resetOutput();
				}
				isOutputShowingResult = false;
				boolean isFirstChar = output.length() <= 0;
				if (btnText.equals(PLUS_MINUS)) {
					char firstChar = output.charAt(0);
					if (Character.toString(firstChar).equals(MINUS)) {
						output.deleteCharAt(0);
					} else if (output.charAt(0) != ZERO_CHAR) {
						output.insert(0, MINUS);
					}
				} else if (btnText.equals(TIMES) || btnText.equals(DIVIDE) || btnText.equals(PLUS)) {
					// If isFirstChar then don't render the btnText.
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
									overrideCharToOutput(btnText);
								}
							} else if (!isPrevPrevAnAction) {
								// Won't be first character && prev prev is not an action character
								overrideCharToOutput(btnText);
							}
						}
					}
				} else if (btnText.equals(MINUS) && !isFirstChar) {
					// If isFirstChar then render btnText in {@code else}
					String prevCharInOutput = Character.toString(output.charAt(output.length() - 1));
					boolean isPrevCharAnAction = ACTIONS.indexOf(prevCharInOutput) >= 0;
					if (!isPrevCharAnAction) {
						writeCharToOutput(btnText);
					} else if (prevCharInOutput.equals(PLUS) || prevCharInOutput.equals(MINUS)) {
						overrideCharToOutput(btnText);
					} else {
						writeCharToOutput(btnText);
					}
				} else if (btnText.equals(DOT)) {
					if (!isFirstChar) {
						char prevCharInOutput = output.charAt(output.length() - 1);
						if (Character.isDigit(prevCharInOutput) && !isLastNonDigitADot()) {
							writeCharToOutput(btnText);
						}
					}
				} else {
					writeCharToOutput(btnText);
				}
				updateOutputLabel();
			}
		};
	}

	private void writeCharToOutput(String s) {
		output.append(s);
	}

	private void overrideCharToOutput(String s) {
		if (output.length() > 0) {
			output.deleteCharAt(output.length() - 1);
		}
		output.append(s);
	}

	private void updateOutputLabel() {
		outputLabel.setText(output.toString());
	}

	private boolean isLastNonDigitADot() {
		for (int i = output.length() - 1; i >= 0; i--) {
			char c = output.charAt(i);
			boolean isNonDigit = !Character.isDigit(c);
			if (isNonDigit) {
				return Character.toString(c).equals(DOT);
			}
		}
		return false;
	}

	private void calculateOutput() throws IllegalArgumentException {
		String expression = outputLabel.getText();
		char firstChar = expression.charAt(0);
		if (!Character.toString(firstChar).equals(MINUS)) {
			expression = "+" + expression;
		}

		double result = 0;
		char action = expression.charAt(0);
		String numberStringified = "";
		for (int i = 1; i < expression.length(); i++) {
			char c = expression.charAt(i);
			boolean isLastChar = i == expression.length() - 1;
			boolean isAction = ACTIONS.indexOf(Character.toString(c)) >= 0;

			if (isLastChar) {
				if (isAction) {
					throw new IllegalArgumentException("Invalid expression");
				}
				numberStringified += c;
			}

			if (isAction && Character.toString(c).equals(MINUS)) {
				if (i - 1 >= 0) {
					char prevC = expression.charAt(i - 1);
					boolean isPrevAnAction = ACTIONS.indexOf(Character.toString(prevC)) >= 0;
					if (isPrevAnAction) {
						isAction = false;
					}
				}
			}
			if (isAction || isLastChar) {
				// Found another action -> calculate prev and then set to curr
				result = calculateAction(Character.toString(action), result, Double.parseDouble(numberStringified));
				action = c;
				numberStringified = "";
			} else {
				// Add digit/dot
				numberStringified += c;
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

	private void updateOutputWithResult(double result) {
		output = new StringBuilder(Double.toString(result));
		if (output.length() >= 2 && output.charAt(output.length() - 1) == ZERO_CHAR
				&& output.charAt(output.length() - 2) == '.') {
			output.delete(output.length() - 2, output.length());
		}
		updateOutputLabel();
		isOutputShowingResult = true;
	}
}
