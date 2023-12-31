package question2;

/**
 * A bank account that can use checks and requires a monthly fee
 */
public class ServiceChargeChecking extends CheckingAccount {

	private static final double DEFAULT_MONTHLY_FEE = 10;

	private double monthlyFee;

	/**
	 * Create a bank account that requires a monthly fee.
	 * 
	 * @param monthlyFee set the monthly fee
	 * @throws IllegalBalanceException    if balance is under zero.
	 * @throws IllegalMonthlyFeeException if monthly fee is under zero.
	 */
	public ServiceChargeChecking(String accountNum, String ownerName, String ownerId, double balance, double monthlyFee)
			throws IllegalBalanceException, IllegalMonthlyFeeException {
		super(accountNum, ownerName, ownerId, balance);
		setMonthlyFee(monthlyFee);
	}

	/**
	 * Create a bank account that requires a monthly fee. The monthly fee is set to
	 * default
	 * 
	 * @throws IllegalBalanceException    if balance is under zero.
	 * @throws IllegalMonthlyFeeException if monthly fee is under zero.
	 */
	public ServiceChargeChecking(String accountNum, String ownerName, String ownerId, double balance)
			throws IllegalBalanceException, IllegalMonthlyFeeException {
		this(accountNum, ownerName, ownerId, balance, DEFAULT_MONTHLY_FEE);
	}

	/**
	 * @return the monthlyFee
	 */
	public double getMonthlyFee() {
		return monthlyFee;
	}

	/**
	 * @param monthlyFee the monthlyFee to set
	 * @throws IllegalMonthlyFeeException if monthly fee is less than zero.
	 */
	public void setMonthlyFee(double monthlyFee) throws IllegalMonthlyFeeException {
		if (monthlyFee < 0) {
			throw new IllegalMonthlyFeeException(ERROR_UNDER_ZERO);
		}

		this.monthlyFee = monthlyFee;
	}

	/**
	 * The monthly management charges the monthly fee.
	 * 
	 * @throws IllegalBalanceException
	 */
	public void monthlyManagement() throws IllegalBalanceException {
		this.withdrawal(this.monthlyFee);
	}

	@Override
	public String toString() {
		return super.toString() + "\nMonthly Fee: " + this.monthlyFee;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ServiceChargeChecking)) {
			return false;
		}
		ServiceChargeChecking otherServiceChargeChecking = (ServiceChargeChecking) obj;
		return super.equals(obj) && this.monthlyFee == otherServiceChargeChecking.monthlyFee;
	}

}
