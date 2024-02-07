public class Main {
    public static void main(String[] args) throws Exception {
        PrimesFinderManager primesFinder = new PrimesFinderManager(11, 3);
        primesFinder.start();
        primesFinder.printPrimes();
    }
}
