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
	 */
	public ServiceChargeChecking(String accountNum, String ownerName, String ownerId, double balance,
			double monthlyFee) {
		super(accountNum, ownerName, ownerId, balance);
		this.setMonthlyFee(monthlyFee);
	}

	/**
	 * Create a bank account that requires a monthly fee. The monthly fee is set as
	 * default
	 */
	public ServiceChargeChecking(String accountNum, String ownerName, String ownerId, double balance) {
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
	 */
	public void setMonthlyFee(double monthlyFee) {
		this.monthlyFee = monthlyFee;
	}

	/**
	 * The monthly management charges the monthly fee
	 * @throws IllegalBalanceException 
	 */
	public void monthlyManagement() throws IllegalBalanceException {
		this.withdrawal(this.monthlyFee);
	}

	public String toString() {
		return super.toString() + "\nMonthly Fee: " + this.monthlyFee;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof ServiceChargeChecking)) {
			return false;
		}
		ServiceChargeChecking otherServiceChargeChecking = (ServiceChargeChecking) obj;
		return super.equals(obj) && this.monthlyFee == otherServiceChargeChecking.monthlyFee;
	}

}
