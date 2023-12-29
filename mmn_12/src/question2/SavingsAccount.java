package question2;

/**
 * A savings bank account, with an interest.
 */
public class SavingsAccount extends BankAccount {

    protected static final double DEFAULT_INTEREST_RATE = 0.1;

    private double interestRate;

    /**
     * Create a savings account.
     * 
     * @param interestRate set the interest rate.
     */
    public SavingsAccount(String accountNum, String ownerName, String ownerId, double balance, double interestRate) {
        super(accountNum, ownerName, ownerId, balance);
        this.setInterestRate(interestRate);
    }

    /**
     * Create a savings account. The interest rate is set to default.
     */
    public SavingsAccount(String accountNum, String ownerName, String ownerId, double balance) {
        this(accountNum, ownerName, ownerId, balance, DEFAULT_INTEREST_RATE);
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
     * Add the interest to the savings-account's balance
     */
    public void monthlyManagement() {
        this.deposit(this.calcInterest());
    }

    public String toString() {
        return super.toString() + "\nInterest Rate: " + this.interestRate;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SavingsAccount)) {
            return false;
        }
        SavingsAccount otherSavingsAccount = (SavingsAccount) obj;
        return super.equals(obj)
                && this.interestRate == otherSavingsAccount.interestRate;
    }

}
