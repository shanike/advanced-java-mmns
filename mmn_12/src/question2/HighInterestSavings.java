package question2;

/**
 * A savings bank account, with a minimum balance and a high interest.
 */
public class HighInterestSavings extends SavingsAccount {

	private static final double HIGHER_INTEREST_RATE = SavingsAccount.DEFAULT_INTEREST_RATE + 0.05;

	private static final double DEFAULT_MINIMUM_BALANCE = 50;

	private double minimumBalance;

	/**
	 * Create a high interest savings bank account. the interest rate is set to the
	 * default higher interest rate
	 * 
	 * @param minimumBalance     to set the minimum balance.
	 * @param higherInterestRate set the interest rate - higher than the basic
	 *                           savings account's.
	 * @throws IllegalBalanceException if balance is under zero.
	 * @throws IllegalInterestRateException if interest rate is under zero, or not high enough.
	 */
	public HighInterestSavings(String accountNum, String ownerName, String ownerId, double balance,
			double minimumBalance, double higherInterestRate) throws IllegalBalanceException, IllegalInterestRateException {
		super(accountNum, ownerName, ownerId, balance);
		setInterestRate(higherInterestRate);
		setMinimumBalance(minimumBalance);
	}

	/**
	 * Create a high interest savings bank account.
	 * Set either the minimum balance XOR the interest rate.
	 * 
	 * @param isMinimumBalance             declare whether you're setting the
	 *                                     interest rate or the minimum balance.
	 * @param minimumBalanceOrInterestRate depending on {@code isMinimumRate}, this
	 *                                     param is either the interest rate or the
	 *                                     minimum balance.
	 * @throws IllegalBalanceException if balance is under zero.
	 * @throws IllegalInterestRateException if interest rate is under zero, or not high enough.
	 */
	public HighInterestSavings(String accountNum, String ownerName, String ownerId, double balance,
			boolean isMinimumBalance, double minimumBalanceOrInterestRate) throws IllegalBalanceException, IllegalInterestRateException {
		this(accountNum, ownerName, ownerId, balance,
				isMinimumBalance ? minimumBalanceOrInterestRate : DEFAULT_MINIMUM_BALANCE,
				!isMinimumBalance ? minimumBalanceOrInterestRate : HIGHER_INTEREST_RATE);
	}

	/**
	 * Create a high interest savings bank account.
	 * Both minimum balance and interest rate are set to default.
	 * 
	 * @throws IllegalBalanceException if balance is under zero.
	 * @throws IllegalInterestRateException if interest rate is under zero, or not high enough.
	 */
	public HighInterestSavings(String accountNum, String ownerName, String ownerId, double balance)
			throws IllegalBalanceException, IllegalInterestRateException {
		this(accountNum, ownerName, ownerId, balance, DEFAULT_MINIMUM_BALANCE, HIGHER_INTEREST_RATE);
	}

	/**
	 * @return the minimumBalance
	 */
	public double getMinimumBalance() {
		return minimumBalance;
	}

	/**
	 * @param minimumBalance the minimumBalance to set
	 * @throws IllegalBalanceException if minimum balance is lower than zero.
	 */
	public void setMinimumBalance(double minimumBalance) throws IllegalBalanceException {
		if (minimumBalance < 0) {
			throw new IllegalBalanceException(ERROR_UNDER_ZERO);
		}
		this.minimumBalance = minimumBalance;
	}

	/**
	 * @param higherInterestRate the higherInterestRate to set.
	 * @throws IllegalArgumentException if interest rate is not higher than
	 *                                  SavingsAccount's default interest rate, or
	 *                                  smaller than zero.
	 */
	@Override
	public void setInterestRate(double higherInterestRate) throws IllegalInterestRateException {
		if (higherInterestRate < SavingsAccount.DEFAULT_INTEREST_RATE) {
			throw new IllegalInterestRateException("Cannot be less than DEFAULT_INTEREST_RATE");
		}
		if (higherInterestRate < 0) {
			throw new IllegalInterestRateException(ERROR_UNDER_ZERO);
		}
		super.setInterestRate(higherInterestRate);
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
			throw new IllegalBalanceException(ERROR_UNDER_ZERO);
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
