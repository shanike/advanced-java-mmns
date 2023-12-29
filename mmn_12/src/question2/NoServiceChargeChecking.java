package question2;

/**
 * A bank account that doesn't require a monthly fee, but has a minimum balance.
 */
public class NoServiceChargeChecking extends CheckingAccount {

    private final double DEFAULT_MINIMUM_BALANCE = 50;

    private double minimumBalance;

    /**
     * Create a no service charge checking bank account with the default minimum
     * balance
     */
    public NoServiceChargeChecking(String accountNum, String ownerName, String ownerId, double balance) {
        super(accountNum, ownerName, ownerId, balance);
        this.setMinimumBalance(DEFAULT_MINIMUM_BALANCE);
    }

    /**
     * Create a no service charge checking bank account
     * 
     * @param minimumBalance to set the minimum balance
     */
    public NoServiceChargeChecking(String accountNum, String ownerName, String ownerId, double balance,
            double minimumBalance) {
        super(accountNum, ownerName, ownerId, balance);
        this.setMinimumBalance(minimumBalance);
    }

    /**
     * @return the minimumBalance
     */
    public double getMinimumBalance() {
        return minimumBalance;
    }

    /**
     * @param minimumBalance the minimumBalance to set
     */
    public void setMinimumBalance(double minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    /**
     * @return the default minimum balance
     */
    public double getDefaultMinimumBalance() {
        return DEFAULT_MINIMUM_BALANCE;
    }

    public void monthlyManagement() {
    }

    public String toString() {
        return super.toString() + "\nMinimum Balance: " + this.minimumBalance;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof NoServiceChargeChecking)) {
            return false;
        }
        NoServiceChargeChecking otherNoServiceChargeChecking = (NoServiceChargeChecking) obj;
        return super.equals(obj)
                && this.minimumBalance == otherNoServiceChargeChecking.minimumBalance;
    }
}
