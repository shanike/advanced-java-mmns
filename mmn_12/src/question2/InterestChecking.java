package question2;

/**
 * A bank account with an interest rate. With no monthly fee and with a minimum
 * balance (i.e extends NoServiceChargeChecking)
 */
public class InterestChecking extends NoServiceChargeChecking {

	/**
	 * {@code InterestChecking}'s minimum balance is higher than
	 * {@code NoServiceChargeChecking}'s.
	 */
	private static final double HIGHER_MINIMUM_BALANCE = NoServiceChargeChecking.DEFAULT_MINIMUM_BALANCE + 10;

	private static final double DEFAULT_INTEREST_RATE = 0.05;

	private double interestRate;

	// ------------------ Constructors ------------------

	/**
	 * Create an account with interest rate.
	 * 
	 * @param interestRate   set the interest rate.
	 * @param minimumBalance set the minimum balance.
	 * @throws IllegalBalanceException      if minimum balance is under zero.
	 * @throws IllegalInterestRateException if interest rate is under zero.
	 */
	public InterestChecking(String accountNum, String ownerName, String ownerId, double balance, double minimumBalance,
			double interestRate) throws IllegalBalanceException, IllegalInterestRateException {
		super(accountNum, ownerName, ownerId, balance);
		setMinimumBalance(minimumBalance);
		setInterestRate(interestRate);
	}

	/**
	 * Create an account with interest rate.
	 * 
	 * Set either the interest rate, or the minimum balance. The other will be set
	 * to default.
	 * 
	 * @param isMinimumBalance             declare whether you're setting the
	 *                                     interest rate or the minimum balance.
	 * @param interestRateOrMinimumBalance depending on {@code isMinimumRate}, this
	 *                                     param is either the interest rate or the
	 *                                     minimum balance.
	 * @throws IllegalBalanceException      if minimum balance is under zero.
	 * @throws IllegalInterestRateException if interest rate is under zero.
	 */
	public InterestChecking(String accountNum, String ownerName, String ownerId, double balance,
			boolean isMinimumBalance, double interestRateOrMinimumBalance)
			throws IllegalBalanceException, IllegalInterestRateException {
		this(accountNum, ownerName, ownerId, balance,
				isMinimumBalance ? interestRateOrMinimumBalance : DEFAULT_MINIMUM_BALANCE,
				!isMinimumBalance ? interestRateOrMinimumBalance : DEFAULT_INTEREST_RATE);
	}

	/**
	 * Create an account with interest rate. The interest rate and the minimum
	 * balance are both set to default.
	 * 
	 * @throws IllegalBalanceException      if minimum balance is under zero.
	 * @throws IllegalInterestRateException if interest rate is under zero.
	 */
	public InterestChecking(String accountNum, String ownerName, String ownerId, double balance)
			throws IllegalBalanceException, IllegalInterestRateException {
		this(accountNum, ownerName, ownerId, balance, HIGHER_MINIMUM_BALANCE, DEFAULT_INTEREST_RATE);
	}

	// ------------------ Other Methods ------------------

	/**
	 * @return the interestRate
	 */
	public double getInterestRate() {
		return interestRate;
	}

	/**
	 * @param interestRate the interestRate to set
	 * @throws IllegalInterestRateException if interest balance is under zero.
	 * @throws
	 */
	public void setInterestRate(double interestRate) throws IllegalInterestRateException {
		if (interestRate < 0) {
			throw new IllegalInterestRateException(ERROR_UNDER_ZERO);
		}
		this.interestRate = interestRate;
	}

	/**
	 * @param minimumBalance the interestRate to set
	 * @throws IllegalBalanceException if minimal balance is lower than zero, or is
	 *                                 not higher than NoServiceChargeChecking's
	 *                                 default minimal balance.
	 */
	@Override
	public void setMinimumBalance(double minimumBalance) throws IllegalBalanceException {
		if (minimumBalance < NoServiceChargeChecking.DEFAULT_MINIMUM_BALANCE) {
			throw new IllegalBalanceException(ERROR_UNDER_MINIMUM);
		}
		super.setMinimumBalance(minimumBalance);
	}

	/**
	 * @return the calculated interest value
	 */
	public double calculateInterest() {
		return this.interestRate * getBalance();
	}

	/**
	 * The monthly management adds the interest to the account's balance.
	 */
	@Override
	public void monthlyManagement() {
		deposit(calculateInterest());
	}

	@Override
	public String toString() {
		return super.toString() + "\nInterest Rate: " + this.interestRate;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof InterestChecking)) {
			return false;
		}
		InterestChecking otherInterestChecking = (InterestChecking) obj;
		return super.equals(obj) && this.interestRate == otherInterestChecking.interestRate;
	}

}
