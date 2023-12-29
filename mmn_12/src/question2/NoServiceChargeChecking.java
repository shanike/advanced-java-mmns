package question2;

/**
 * A bank account that doesn't require a monthly fee, but has a minimum balance.
 */
public class NoServiceChargeChecking extends CheckingAccount {

	protected static final double DEFAULT_MINIMUM_BALANCE = 50;

	private double minimumBalance;

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
	 * Create a no service charge checking bank account with the default minimum
	 * balance
	 */
	public NoServiceChargeChecking(String accountNum, String ownerName, String ownerId, double balance) {
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

	public void monthlyManagement() {
		// No charging...
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
		if (!(obj instanceof NoServiceChargeChecking)) {
			return false;
		}
		NoServiceChargeChecking otherNoServiceChargeChecking = (NoServiceChargeChecking) obj;
		return super.equals(obj) && this.minimumBalance == otherNoServiceChargeChecking.minimumBalance;
	}
}
