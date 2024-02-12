package question2;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

public class PhilosophersMealMonitorController {
	private static final int NUM_OF_PHILOSOPHERS = 5;
	private static final int NUM_OF_STICKS = NUM_OF_PHILOSOPHERS;

	@FXML
	private AnchorPane container;

	private Drawer drawer;

	private ArrayList<Stick> sticks;
	private ArrayList<Philosopher> philosophers;

	public void initialize() {
		drawer = new Drawer(container, NUM_OF_PHILOSOPHERS);
		initPhilosophers();
		initSticks();
		startPhilosopherThreads();
	}

	private void initSticks() {
		sticks = new ArrayList<Stick>();
		for (int i = 0; i < NUM_OF_STICKS; i++) {
			sticks.add(new Stick());
			drawer.drawStick(i);
		}
	}

	private void initPhilosophers() {
		philosophers = new ArrayList<Philosopher>();
		ArrayList<Thread> philosopherThreads = new ArrayList<Thread>();
		for (int i = 0; i < NUM_OF_PHILOSOPHERS; i++) {
			Philosopher ph = new Philosopher(this);
			philosophers.add(ph);
			philosopherThreads.add(new Thread(ph));
			drawer.drawPhilosopher(ph.getId());
		}
	}

	private void startPhilosopherThreads() {
		for (Philosopher ph : philosophers) {
			new Thread(ph).start();
		}
	}

	/**
	 * Check whether philosopher can take both the sticks on each side. If can't ->
	 * is added to the threads' waiting list. If can -> updates the GUI.
	 * 
	 * @param pIndex
	 * @return whether or not the philosopher can take both sticks.
	 */
	public synchronized boolean attemptPickingUpTwoSticks(int pIndex) {
		int stickIndex1 = pIndex;
		int stickIndex2 = (pIndex + 1) % sticks.size();
		System.out.println("[" + pIndex + "] is attempting to get " + stickIndex1 + " and " + stickIndex2);
		if (this.sticks.get(stickIndex1).isTaken() || this.sticks.get(stickIndex2).isTaken()) {
			System.out.println("[" + pIndex + "] CAN'T -> is waiting");
			try {
				wait();
			} catch (InterruptedException e) {
				System.err.println("ERROR thinking, philosopher id " + pIndex + ":");
				e.printStackTrace();
			}
			return false;
		}
		// Both sticks are free to use
		System.out.println("[" + pIndex + "] TAKING sticks");
		takeStick(stickIndex1, pIndex);
		takeStick(stickIndex2, pIndex);
		drawer.updatePhilosopherToEating(pIndex);
		return true;
	}

	/**
	 * Updates the GUI and notifies waiting threads.
	 * 
	 * @param pIndex
	 */
	public synchronized void returnSticks(int pIndex) {
		int stickIndex1 = pIndex;
		int stickIndex2 = (pIndex + 1) % sticks.size();
		System.out.println("[" + pIndex + "] returned the sticks " + stickIndex1 + " and " + stickIndex2);
		returnStick(stickIndex1);
		returnStick(stickIndex2);
		drawer.updatePhilosopherToNotEating(pIndex);
		notifyAll();
	}

	/**
	 * Take stick by updating both the sticks list and the GUI
	 * 
	 * @param stickIndex
	 * @param pIndex
	 */
	private void takeStick(int stickIndex, int pIndex) {
		sticks.get(stickIndex).takeStick();
		drawer.takeStick(stickIndex, pIndex);
	}

	/**
	 * Return stick by updating both the sticks list and the GUI
	 * 
	 * @param stickIndex
	 */
	private void returnStick(int stickIndex) {
		sticks.get(stickIndex).returnStick();
		drawer.returnStick(stickIndex);
	}

}
