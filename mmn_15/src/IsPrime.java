/**
 * The class checks whether an integer is a prime integer. The integer is
 * provided via a {@code primesFinderManager} instance.
 * 
 * The class should run in its own thread.
 */
public class IsPrime implements Runnable {
    private PrimesFinderManager primesFinderManager;
    private static int totalId = 0;
    public int id = 0;

    public IsPrime(int num, PrimesFinderManager primesFinderManager) {
        totalId++;
        id = totalId;
        this.primesFinderManager = primesFinderManager;
    }

    private static boolean isPrime(int numToCheck) {
        int n = 2;
        while (n <= (numToCheck / 2)) {
            /** {@code num} divides by {@code i} */
            if (numToCheck % n == 0) {
                /** {@code num} is not a prime */
                return false;
            }
            n++;
        }
        /** {@code num} is a prime */
        return true;
    }

    /**
     * Gets a number from the {@code primesFinderManager} and checks whether or not
     * it's a prime number and returns the answer to the
     * {@code primesFinderManager}.
     */
    @Override
    public void run() {
        while (true) {

            int numToCheck;
            try {
                numToCheck = primesFinderManager.getNumToCheck();
            } catch (Exception e) {
                break;
            }

            // Check if number is a prime
            boolean isPrime = isPrime(numToCheck);

            // Update manager
            primesFinderManager.update(numToCheck, isPrime);
        }
    }
}