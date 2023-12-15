package question2;

import java.util.Random;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * Controller for creating random (10) shapes using JavaFX
 */
public class RandomShapesController {

	private final int NUM_OF_SHAPE_VARIANTS = 3;
	private final int NUM_OF_SHAPES = 10;

	private final int MAX_RGB_VALUE = 255;

	private int canvasHeight = -1;
	private int canvasWidth = -1;
	private int maxShapeHeight = -1;
	private int maxShapeWidth = -1;

	@FXML
	private Canvas canvas;

	@FXML
	private Button refreshButton;

	private GraphicsContext gc;

	public void initialize() {
		gc = canvas.getGraphicsContext2D();
		canvasHeight = (int) canvas.getHeight();
		canvasWidth = (int) canvas.getWidth();
		maxShapeHeight = canvasHeight / 4;
		maxShapeWidth = canvasWidth / 4;
		drawRandomShapes();
	}

	/**
	 * @returns a randomly generated color component (i.e a single R, G or B)
	 */
	private int generateRandomColorComponent() {
		return (new Random()).nextInt(MAX_RGB_VALUE + 1);
	}

	/**
	 * @returns a randomly generated color, using an RGB value
	 */
	private Color generateRandomColor() {
		return Color.rgb(generateRandomColorComponent(), generateRandomColorComponent(),
				generateRandomColorComponent());
	}

	/**
	 * Draws `NUM_OF_SHAPES` random shapes in random colors.
	 */
	private void drawRandomShapes() {
		for (int i = 0; i < NUM_OF_SHAPES; i++) {
			drawRandomShape();
		}
	}

	/**
	 * Draws a random shape in a random color
	 */
	private void drawRandomShape() {
		if (canvasHeight <= 0 || canvasWidth <= 0 || maxShapeHeight <= 0 || maxShapeWidth <= 0) {
			/*
			 * In case it wasn't initialized properly or in case of an unexpected edge case
			 * ¯\_(ツ)_/¯
			 */
			System.out.println("ERROR: Canvas width/height not set");
			return;
		}
		// Calculate random position
		Random r = new Random();
		int x = r.nextInt(canvasWidth + 1);
		int y = r.nextInt(canvasHeight + 1);
		int width = r.nextInt(maxShapeWidth + 1);
		int height = r.nextInt(maxShapeHeight + 1);

		// Calculate random color
		Color color = generateRandomColor();

		// Draw a random shape on canvas
		switch (r.nextInt(NUM_OF_SHAPE_VARIANTS)) {
		case 0:
			gc.setFill(color);
			gc.fillRect(x, y, width, height);
			break;
		case 1:
			gc.setFill(color);
			gc.fillOval(x, y, width, height);
			break;
		case 2:
			gc.setStroke(color);
			gc.strokeLine(x, y, width, height);
			break;
		default:
			break;
		}
	}

	/**
	 * Clears any paint off the canvas
	 */
	private void clearCanvas() {
		gc.clearRect(0, 0, canvasWidth, canvasHeight);
	}

	@FXML
	private void handleRefreshButtonClick(ActionEvent event) {
		clearCanvas();
		drawRandomShapes();
	}

}
