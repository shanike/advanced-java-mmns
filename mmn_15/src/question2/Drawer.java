package question2;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class Drawer {
	private AnchorPane container;

	private ArrayList<Group> philosophersNodes;
	private ArrayList<Line> sticksNodes;
	private int numOfPhilosophers;
	private int numOfSticks;

	/*
	 * ---------------------- UI Positioning attributes ----------------------
	 */
	/* ---- Philosophers attributes ---- */
	private int philosopherRadius;
	private int spaceBetweenPhilosophers;
	private int eyeRadius;
	/**
	 * (The name coresponds to the css rule {@code top})
	 */
	private double philosophersTop;

	/* ---- Sticks attributes ---- */
	/**
	 * (The name coresponds to the css rule {@code top})
	 */
	private double sticksTop;

	public Drawer(AnchorPane container, int numOfPhilosophers) {
		this.container = container;
		this.numOfPhilosophers = numOfPhilosophers;
		numOfSticks = numOfPhilosophers;
		initContainer();
		initNodesLists();
		initPositioningAttributes();
	}

	private void initContainer() {
		this.container.setPrefWidth(900);
		container.setPadding(new Insets(50));
	}

	/**
	 * Fill nodes lists with {@code null}.
	 * 
	 * This is important for updating the nodes array - to always be able to
	 * .set(anyIndex) (as long as anyIndex < numOfPhilosophers/numOfSticks)
	 */
	private void initNodesLists() {
		philosophersNodes = new ArrayList<Group>(numOfPhilosophers);
		sticksNodes = new ArrayList<Line>(numOfSticks);
		for (int i = 0; i < Math.max(numOfPhilosophers, numOfSticks); i++) {
			philosophersNodes.add(null);
			sticksNodes.add(null);
		}
	}

	private void initPositioningAttributes() {
		philosopherRadius = 50;
		spaceBetweenPhilosophers = 50;
		eyeRadius = 10;
		philosophersTop = container.getPrefHeight() / 3;
		sticksTop = container.getPrefHeight() * 2 / 3;
	}

	/*
	 * ---------------------- Functionality public methods ----------------------
	 */

	// ---------------------- philosopher ----------------------

	/**
	 * Draw philosopher in its initial state, which is not eating.
	 */
	public void drawPhilosopher(int philosopherIndex) {
		Platform.runLater(() -> {
			renderPhilosopher(philosopherIndex, createPhilosopher(philosopherIndex, false));
		});
	}

	/**
	 * Update philosopher's state to eating.
	 */
	public void updatePhilosopherToEating(int pIndex) {
		updatePhilosopher(pIndex, true);
	}

	/**
	 * Update philosopher's state to not eating.
	 */
	public void updatePhilosopherToNotEating(int pIndex) {
		updatePhilosopher(pIndex, false);
	}

	// ---------------------- stick ----------------------

	/**
	 * Draw stick in its initial state, which is not taken.
	 */
	public void drawStick(int stickIndex) {
		Platform.runLater(() -> {
			renderStick(stickIndex, createStick(stickIndex));
		});
	}

	/**
	 * Update the stick to a "not taken" state, i.e "returned" state.
	 * 
	 * @param stickIndex - the stick to update.
	 */
	public void returnStick(int stickIndex) {
		updateStick(stickIndex, -1, false);
	}

	/**
	 * Update the stick to a "taken" state.
	 * 
	 * @param stickIndex    - the stick to update.
	 * @param philosopherId - Used to place the stick closer to the philosopher who
	 *                      took the stick.
	 */
	public void takeStick(int stickIndex, int philosopherId) {
		updateStick(stickIndex, philosopherId, true);
	}

	/*
	 * ---------------------- Functionality private methods ----------------------
	 */

	/**
	 * Update philosopher's state.
	 * 
	 * @param pIndex
	 * @param isEating - the nnew state.
	 */
	private void updatePhilosopher(int pIndex, boolean isEating) {
		Platform.runLater(() -> {
			Group newPhi = createPhilosopher(pIndex, isEating);
			Group currPhi = this.philosophersNodes.get(pIndex);
			container.getChildren().remove(currPhi);
			renderPhilosopher(pIndex, newPhi);
		});
	}

	/**
	 * Updates the stick.
	 * 
	 * @param stickIndex    - stick to update.
	 * @param philosopherId - Used to place the stick closer to the philosopher who
	 *                      took the stick.
	 * @param isEating      - the new state.
	 */
	private void updateStick(int stickIndex, int philosopherId, boolean isEating) {
		Platform.runLater(() -> {
			Line newStick = createStick(stickIndex, philosopherId, isEating);
			Line currStick = sticksNodes.get(stickIndex);
			container.getChildren().remove(currStick);
			renderStick(stickIndex, newStick);
		});
	}

	/**
	 * ---------------------- Creating philosophers ----------------------
	 */

	/**
	 * Create and return a {@code Group} representing a philosopher.
	 */
	private Group createPhilosopher(int philosopherId, boolean isEating) {
		final double x = calculatePhilosopherX(philosopherId);
		final double y = philosophersTop;

		// Draw the philosopher's head
		final Circle head = new Circle(x, y, philosopherRadius);
		head.setFill(Color.YELLOW);

		// Draw the philosopher's eyes
		final Circle leftEye = new Circle(x - philosopherRadius / 2, y, eyeRadius);
		leftEye.setFill(Color.BLACK);
		final Circle rightEye = new Circle(x + philosopherRadius / 2, y, eyeRadius);
		rightEye.setFill(Color.BLACK);

		// Draw the philosopher's mouth
		final Arc mouth = new Arc(x, y + philosopherRadius / 2.7, philosopherRadius / 2, philosopherRadius / 5,
				180 + (30 / 2), isEating ? 360 : 180 - 30);
		mouth.setType(ArcType.OPEN);
		mouth.setFill(null); // Transparent fill so we see the stroke
		mouth.setStroke(Color.BLACK);
		mouth.setStrokeWidth(2);

		return new Group(head, leftEye, rightEye, mouth);
	}

	/**
	 * Calculates the x position of the philosopher.
	 * 
	 * @return the x position of the philosopher.
	 */
	private double calculatePhilosopherX(int phIndex) {
		return (phIndex + 1) * (philosopherRadius * 2 + spaceBetweenPhilosophers);
	}

	/**
	 * Renders and saves a philosopher node.
	 * 
	 * @param ph - the philosopher node to render and save.
	 */
	private void renderPhilosopher(int pIndex, Group ph) {
		philosophersNodes.set(pIndex, ph);
		container.getChildren().add(ph);
	}

	/**
	 * ---------------------- Creating sticks ----------------------
	 */

	/**
	 * Creates and returns a {@code Line} representing a stick in the default state,
	 * i.e not taken.
	 * 
	 * @param stickIndex
	 */
	private Line createStick(int stickIndex) {
		return createStick(stickIndex, -1, false);
	}

	/**
	 * Creates and returns a {@code Line} representing a stick.
	 * 
	 * @param philosopherId - If the stick is being used or is returned from/to a
	 *                      specific philosopher, this param is used for placing the
	 *                      stick in the right place.
	 * @param stickIndex    - the stick index to create.
	 * @param isTaken       - the state of the stick: whether or not the stick is
	 *                      currently being used to eat.
	 */
	private Line createStick(int stickIndex, int philosopherId, boolean isTaken) {
		double xLineStart = calculateStickX(stickIndex);
		double xLineEnd = xLineStart;
		double yLineStart = sticksTop;
		double yLineEnd = sticksTop + 100;
		Paint color = Color.BROWN;

		if (isTaken) {
			// Bring stick a little higher
			yLineStart -= 50;
			yLineEnd -= 50;

			color = Color.GREY;
			if (philosopherId > -1) {
				if (philosopherId == stickIndex) {
					// Stick is to the left of the head
					// -> point stick a little right
					xLineStart += 20;
				} else {
					// Stick is to the right of the head
					// -> point stick a little left
					xLineStart -= 20;
				}
			}
		}

		Line stick = new Line(xLineStart, yLineStart, xLineEnd, yLineEnd);
		stick.setStroke(color);

		return stick;
	}

	/**
	 * Calculates the starting x position of the stick.
	 * 
	 * @return the starting x position of the stick.
	 */
	private double calculateStickX(int stickIndex) {
		return calculatePhilosopherX(stickIndex) - philosopherRadius - (spaceBetweenPhilosophers / 2);
	}

	/**
	 * Renders and saves a stick node.
	 * 
	 * @param stick - the stick node to render and save.
	 */
	private void renderStick(int stickIndex, Line stick) {
		sticksNodes.set(stickIndex, stick);
		container.getChildren().add(stick);
	}

}
