package question2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

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

	private final Insets buttonsPadding = new Insets(10, 15, 10, 15);
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

	/* ------------------------- end of attributes ------------------------- */

	public void initialize() {
		setOutputGridProperties();
		setOutputLabelProperties();
		updateOutputLabel();
		setSolveButton();
		createInputButtons();
	}

	private void setButtonDimensions(Button btn) {
		btn.setPrefWidth(BUTTONS_WIDTH);
		btn.setPrefHeight(BUTTONS_HEIGHT);
	}

	private void setOutputGridProperties() {
		outputGrid.setPrefWidth(appGrid.getPrefWidth());
		outputGrid.setPrefHeight(OUTPUT_ROW_HEIGHT);
	}

	private void setOutputLabelProperties() {
		outputLabel.setBackground(
				new Background(new BackgroundFill(Color.rgb(158, 200, 185), new CornerRadii(6), Insets.EMPTY)));
		int horizontalPadding = 20;
		outputLabel.setPadding(new Insets(10, horizontalPadding, 10, horizontalPadding));

		outputLabel.setPrefWidth(
				outputGrid.getPrefWidth() - (2 * BUTTONS_WIDTH) - buttonsPadding.getLeft() - buttonsPadding.getRight());
		outputLabel.setPrefHeight(outputGrid.getPrefHeight());

	}

	private void setSolveButton() {
		outputButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				calculateOutput();
			}
		});

		outputButton.setBackground(
				new Background(new BackgroundFill(Color.rgb(158, 200, 185), new CornerRadii(8), Insets.EMPTY)));
		outputButton.setPadding(buttonsPadding);
		setButtonDimensions(outputButton);
		GridPane.setHalignment(outputButton, HPos.RIGHT);

	}

	private void createInputButtons() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Button btn = new Button(INPUTS[i][j]);
				btn.setOnAction(getInputHandler());
				btn.setPadding(buttonsPadding);
				btn.setBackground(
						new Background(new BackgroundFill(Color.rgb(92, 131, 116), new CornerRadii(8), Insets.EMPTY)));
				btn.setTextFill(Color.WHITE);
				setButtonDimensions(btn);
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
							if (!willBeFirstCharInOutput && !isPrevPrevAnAction) {
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
//					if()
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

	private void calculateOutput() {

	}

}
