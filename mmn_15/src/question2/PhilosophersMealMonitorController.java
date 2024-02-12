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
		sticks.get(stickIndex1).takeStick();
		sticks.get(stickIndex2).takeStick();
		drawer.takeStick(stickIndex1, pIndex);
		drawer.takeStick(stickIndex2, pIndex);
		drawer.updatePhilosopher(pIndex, true);
		return true;
	}

	public synchronized void returnSticks(int pIndex) {
		int stickIndex1 = pIndex;
		int stickIndex2 = (pIndex + 1) % sticks.size();
		System.out.println("[" + pIndex + "] returned the sticks " + stickIndex1 + " and " + stickIndex2);
		this.sticks.get(stickIndex1).returnStick();
		this.sticks.get(stickIndex2).returnStick();
		drawer.returnStick(stickIndex1);
		drawer.returnStick(stickIndex2);
		drawer.updatePhilosopher(pIndex, false);
		notifyAll();
	}

}
