package question2;

/**
 * A bank account that doesn't require a monthly fee, but has a minimum balance.
 */
public class NoServiceChargeChecking extends CheckingAccount {

	protected final static String ERROR_UNDER_MINIMUM = "Cannot be under minimum";

	protected static final double DEFAULT_MINIMUM_BALANCE = 50;

	private double minimumBalance;

	/**
	 * Create a no service charge checking bank account
	 * 
	 * @param minimumBalance to set the minimum balance
	 * 
	 * @throws IllegalBalanceException if balance or minimum balance is below zero.
	 */
	public NoServiceChargeChecking(String accountNum, String ownerName, String ownerId, double balance,
			double minimumBalance) throws IllegalBalanceException {
		super(accountNum, ownerName, ownerId, balance);
		setMinimumBalance(minimumBalance);
	}

	/**
	 * Create a no service charge checking bank account with the default minimum
	 * balance
	 * 
	 * @throws IllegalBalanceException if balance or minimum balance is below zero.
	 */
	public NoServiceChargeChecking(String accountNum, String ownerName, String ownerId, double balance)
			throws IllegalBalanceException {
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
	 * @throws IllegalBalanceException if minimum balance
	 */
	public void setMinimumBalance(double minimumBalance) throws IllegalBalanceException {
		if (minimumBalance < 0) {
			throw new IllegalBalanceException(ERROR_UNDER_ZERO);
		}
		this.minimumBalance = minimumBalance;
	}

	public void monthlyManagement() {
		// No charging...
	}

	/**
	 * Makes sure the minimum balance isn't passed, then invokes the
	 * {@code withdrawal} method
	 * 
	 * @throws IllegalBalanceException if the withdrawal value would bring the back
	 *                                 account's balance under the minimum balance
	 */
	@Override
	public void withdrawal(double value) throws IllegalBalanceException {
		double balanceToBe = getBalance() - value;
		if (balanceToBe < this.minimumBalance) {
			throw new IllegalBalanceException(ERROR_UNDER_MINIMUM);
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
