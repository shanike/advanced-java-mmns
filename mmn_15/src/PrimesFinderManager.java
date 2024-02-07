import java.util.Iterator;
import java.util.TreeSet;

/**
 * The class finds all prime integers from 1 to {@code max}
 */
public class PrimesFinderManager {
    public static int MIN = 1;
    public static int DEFAULT_MAX = 1;
    public static int DEFAULT_NUMBER_OF_THREADS = 0;

    private int max;
    private int numOfThreads;

    private int numToCheck;

    private TreeSet<Integer> primes;

    /**
     * Create an instance with default values
     */
    public PrimesFinderManager() {
        this(DEFAULT_MAX, DEFAULT_NUMBER_OF_THREADS);
    }

    /**
     * Create an instance and set values
     * 
     * @param max          max prime number to find
     * @param numOfThreads to use while finding all prime numbers
     */
    public PrimesFinderManager(int max, int numOfThreads) {
        this.max = max;
        this.numOfThreads = numOfThreads;
        this.numToCheck = MIN;
        // this.primesMap = new HashMap<Integer, Boolean>();
        this.primes = new TreeSet<Integer>();
    }

    public void start() {
        Thread[] threads = new Thread[numOfThreads];
        System.out.println("start");
        for (int i = 0; i < numOfThreads; i++) {
            IsPrime isPrime = new IsPrime(numToCheck, this);
            // System.out.println("Create and start IsPrime-Thread id " + isPrime.id);
            threads[i] = new Thread(isPrime, Integer.toString(isPrime.id));
            threads[i].start();
        }
        for (int i = 0; i < numOfThreads; i++) {
            System.out.println("Joining Thread id " + threads[i].getName());
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getNumToCheck() throws Exception {
        int numToCheck = this.numToCheck;
        if (numToCheck > max) {
            throw new Exception();
        }
        this.numToCheck++;
        return numToCheck;
    }

    public void update(int num, boolean isPrime) {
        System.out.print("Updating: " + num);
        if (isPrime) {
            System.out.println("; Prime!");
            primes.add(num);
        }
        else {
            System.out.println("; Not prime!");
        }
    }

    /**
     * Print the list of primary integers the program found
     */
    public void printPrimes() {
        System.out.println("The primes are: ");
        Iterator<Integer> primesIt = primes.iterator();
        while (primesIt.hasNext()) {
            System.out.println(primesIt.next());
        }
    }
}
