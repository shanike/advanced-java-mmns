package question2;

/**
 * A bank account with an interest rate, with no monthly fee and with a minimum
 * balance (i.e extends NoServiceChargeChecking)
 */
public class InterestChecking extends NoServiceChargeChecking {

	private static final double MINIMUM_BALANCE = NoServiceChargeChecking.DEFAULT_MINIMUM_BALANCE + 10;

	private static final double DEFAULT_INTEREST_RATE = 0.05;

	private double interestRate;

	/**
	 * Create an account with interest rate.
	 * 
	 * @param interestRate set the interest rate
	 */
	public InterestChecking(String accountNum, String ownerName, String ownerId, double balance, double interestRate) {
		super(accountNum, ownerName, ownerId, balance, MINIMUM_BALANCE);
		this.setInterestRate(interestRate);
	}

	/**
	 * Create an account with interest rate. The interest rate is set to the default
	 */
	public InterestChecking(String accountNum, String ownerName, String ownerId, double balance) {
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
	 * Add the interest to the account's balance
	 */
	public void monthlyManagement() {
		this.deposit(this.calcInterest());
	}

	public String toString() {
		return super.toString() + "\nInterest Rate: " + this.interestRate;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof InterestChecking)) {
			return false;
		}
		InterestChecking otherInterestChecking = (InterestChecking) obj;
		return super.equals(obj) && this.interestRate == otherInterestChecking.interestRate;
	}

}
