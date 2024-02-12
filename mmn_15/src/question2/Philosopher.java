package question2;

public class Philosopher implements Runnable {
	private static int numOfPhilosophers = 0;
	private int id = 0;
	private boolean isEating = false;

	private PhilosophersMealMonitorController philosophersMealMonitor;

	public Philosopher(PhilosophersMealMonitorController philosophersMealMonitor) {
		this.philosophersMealMonitor = philosophersMealMonitor;
		isEating = false;
		id = numOfPhilosophers;
		numOfPhilosophers++;
	}

	public int getId() {
		return this.id;
	}

	public void setIsEating(boolean isEating) {
		this.isEating = isEating;
	}

	public boolean getIsEating() {
		return this.isEating;
	}

	@Override
	public void run() {
		while (true) {
			boolean isSuccess = philosophersMealMonitor.attemptPickingUpTwoSticks(id);
			if (isSuccess) {
				// Wait a random amount of time, as if "eating"
				randomSleep("EATING");
				// Done "eating" -> return the sticks
				philosophersMealMonitor.returnSticks(id);
				// Wait a random amount of time, as if "thinking"
				// Letting other philosophers use the sticks
				randomSleep("THINKING");
			}
		}

	}

	/**
	 * @param action - the action being mimicked , for log, in present participle.
	 */
	private void randomSleep(String action) {
		try {
			double sleepDurationSeconds = randomBetween(3, 6);
			System.out.println(
					"[" + id + "] TOOK -> is " + action + " for " + sleepDurationSeconds + " seconds");
			Thread.sleep((long) secondsToMilliseconds(sleepDurationSeconds));
		} catch (InterruptedException e) {
			System.out.println("ERROR sleeping:");
			e.printStackTrace();
		}
	}

	private static int randomBetween(int min, int max) {
		return min + (int) (Math.random() * ((max - min) + 1));
	}

	private static double secondsToMilliseconds(double sec) {
		return sec * 1000;
	}
}
