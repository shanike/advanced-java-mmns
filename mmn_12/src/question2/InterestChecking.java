package question2;

/**
 * A bank account with an interest rate, with no monthly fee and with a minimum
 * balance (i.e extends NoServiceChargeChecking)
 */
public class InterestChecking extends NoServiceChargeChecking {

    private final double DEFAULT_INTEREST_RATE = 0.05;
    
    private double interestRate;

    /**
     * Create an account with interest rate. The interest rate is set to the default
     */
    public InterestChecking(String accountNum, String ownerName, String ownerId, double balance) {
        super(accountNum, ownerName, ownerId, balance);
        this.setMinimumBalance(this.getDefaultMinimumBalance() + 10);
        this.setInterestRate(DEFAULT_INTEREST_RATE);
    }

    /**
     * Create an account with interest rate.
     * @param interestRate set the interest rate
     */
    public InterestChecking(String accountNum, String ownerName, String ownerId, double balance, double interestRate) {
        this(accountNum, ownerName, ownerId, balance);
        this.setInterestRate(interestRate);
    }

    /**
     * @return the interestRate
     */
    public double getInterestRate() {
        return interestRate;
    }

    /**
     * @param interestRate the interestRate to set
     */
    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    /**
     * @return the interest value
     */
    public double calcInterest() {
        return this.interestRate * this.getBalance();
    }

    /**
     * Add the interest to the account's balance
     */
    public void monthlyManagement() {
        this.setBalance(this.calcInterest());
    }


    public String toString() {
        return super.toString() + "\nInterest Rate: " + this.interestRate;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof InterestChecking)) {
            return false;
        }
        InterestChecking otherInterestChecking = (InterestChecking) obj;
        return super.equals(obj)
                && this.interestRate == otherInterestChecking.interestRate;
    }

}
