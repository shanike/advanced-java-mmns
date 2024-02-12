package question2;

public class Stick {
	private static int numOfSticks = 0;
	private int id = 0;
	private boolean isTaken = false;

	public Stick() {
		isTaken = false;
		numOfSticks++;
		id = numOfSticks;
	}

	public boolean isTaken() {
		return this.isTaken;
	}
	
	public void takeStick() {
		this.isTaken = true;
	}
	
	public void returnStick() {
		this.isTaken = false;
	}

	public int getId() {
		return this.id;
	}

}
