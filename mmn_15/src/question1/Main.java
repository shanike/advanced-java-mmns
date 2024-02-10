package question1;

public class Main {
	public static void main(String[] args) throws Exception {
		PrimesFinderManager primesFinder = new PrimesFinderManager(1000, 10);
		primesFinder.start();
	}
}