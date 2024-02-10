package question1;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * The class finds all prime integers from 1 to {@code max}
 */
public class PrimesFinderManager {
	public static int MIN = 1;

	private int max;
	private int numOfThreads;

	private int numToCheck;

	private TreeSet<Integer> primes;

	/**
	 * Create an instance and set values
	 * 
	 * @param max          max prime number to find
	 * @param numOfThreads to use while finding all prime numbers
	 */
	public PrimesFinderManager(int max, int numOfThreads) {
		if (max < 1) {
			throw new IllegalArgumentException("`max` must equal at least " + MIN);
		}

		if (numOfThreads < 1) {
			throw new IllegalArgumentException("`numOfThreads` must equal at least 1");
		}

		// Set variables:
		this.max = max;
		this.numOfThreads = numOfThreads;
		// Set states:
		this.numToCheck = MIN;
		this.primes = new TreeSet<Integer>();
	}

	public void start() {
		Thread[] threads = new Thread[numOfThreads];
		// Create and start isPrime threads:
		for (int i = 0; i < numOfThreads; i++) {
			IsPrime isPrime = new IsPrime(this);
			String threadName = Integer.toString(isPrime.id);
			threads[i] = new Thread(isPrime, threadName);
			threads[i].start();
		}
		// Join current thread to all isPrime threads:
		for (int i = 0; i < numOfThreads; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// isPrime threads are done -> print:
		this.printPrimes();
	}

	/**
	 * - This method is set to {@code synchronized} to prevent getting and setting
	 * the wrong (i.e a not up-to-date) {@code numToCheck} attribute.
	 * 
	 * @return The next number that needs checking
	 * @throws ReachedMaxNumberException If all numbers have been checked and
	 *                                   therefore there is no number that needs
	 *                                   checking
	 */
	public synchronized int getNumToCheck() throws ReachedMaxNumberException {
		int numToCheck = this.numToCheck;
		if (numToCheck > max) {
			throw new ReachedMaxNumberException();
		}
		this.numToCheck++;
		return numToCheck;
	}

	/**
	 * Update the manager with the result: whether {@code num} {@code isPrime} or
	 * not.
	 * 
	 * - This method is set to {@code synchronized} to prevent updating the wrong
	 * (i.e a not up-to-date) {@code primes} list.
	 */
	public synchronized void update(int num, boolean isPrime) {
		if (isPrime) {
			primes.add(num);
		}
	}

	/**
	 * Print the list of primary integers the program found
	 */
	public void printPrimes() {
		System.out.println(
				"The prime numbers between " + MIN + " and " + max + " (using " + numOfThreads + " threads) are: ");
		Iterator<Integer> primesIt = primes.iterator();
		while (primesIt.hasNext()) {
			System.out.print(primesIt.next());
			if (primesIt.hasNext()) {
				System.out.print(", ");
			}
		}
		System.out.println();
	}
}
