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
	 * @throws IllegalBalanceException if balance is under zero.
	 * @throws IllegalInterestRateException if interest rate is under zero.
	 */
	public SavingsAccount(String accountNum, String ownerName, String ownerId, double balance, double interestRate)
			throws IllegalInterestRateException, IllegalBalanceException {
		super(accountNum, ownerName, ownerId, balance);
		setInterestRate(interestRate);
	}

	/**
	 * Create a savings account. The interest rate is set to default.
	 * 
	 * @throws IllegalBalanceException if balance is under zero.
	 * @throws IllegalInterestRateException if interest rate is under zero.
	 */
	public SavingsAccount(String accountNum, String ownerName, String ownerId, double balance)
			throws IllegalBalanceException, IllegalInterestRateException {
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
	 * @throws IllegalInterestRateException if interest rate is under zero.
	 */
	public void setInterestRate(double interestRate) throws IllegalInterestRateException {
		if (interestRate < 0) {
			throw new IllegalInterestRateException(ERROR_UNDER_ZERO);
		}
		this.interestRate = interestRate;
	}

	/**
	 * @return the interest value
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
		if (!(obj instanceof SavingsAccount)) {
			return false;
		}
		SavingsAccount otherSavingsAccount = (SavingsAccount) obj;
		return super.equals(obj) && this.interestRate == otherSavingsAccount.interestRate;
	}

}
