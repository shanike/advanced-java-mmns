package question2;

/**
 * A savings bank account, with a minimum balance and a high interest.
 */
public class HighInterestSavings extends SavingsAccount {

	private static final double INTEREST_RATE = SavingsAccount.DEFAULT_INTEREST_RATE + 0.05;

	private static final double DEFAULT_MINIMUM_BALANCE = 50;

	private double minimumBalance;

	/**
	 * Create a high interest savings bank account. the interest rate is set to the
	 * default higher interest rate
	 * 
	 * @param minimumBalance to set the minimum balance
	 */
	public HighInterestSavings(String accountNum, String ownerName, String ownerId, double balance,
			double minimumBalance) {
		super(accountNum, ownerName, ownerId, balance, INTEREST_RATE);
		this.setMinimumBalance(minimumBalance);
	}

	/**
	 * Create a high interest savings bank account with the default minimum balance
	 */
	public HighInterestSavings(String accountNum, String ownerName, String ownerId, double balance) {
		this(accountNum, ownerName, ownerId, balance, DEFAULT_MINIMUM_BALANCE);
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
	 * Makes sure the minimum balance isn't passed, then invokes the withdrawal
	 * method
	 */
	@Override
	public void withdrawal(double value) throws IllegalBalanceException {
		double balanceToBe = this.getBalance() - value;
		if (balanceToBe < this.minimumBalance) {
			throw new IllegalBalanceException();
		}
		super.withdrawal(value);
	}

	public String toString() {
		return super.toString() + "\nMinimum Balance: " + this.minimumBalance;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof HighInterestSavings)) {
			return false;
		}
		HighInterestSavings otherHighInterestSavings = (HighInterestSavings) obj;
		return super.equals(obj) && this.minimumBalance == otherHighInterestSavings.minimumBalance;
	}

}
