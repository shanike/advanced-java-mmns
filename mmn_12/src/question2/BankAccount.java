package question2;

/**
 * An abstract bank account
 */
public abstract class BankAccount {

	protected final static String ERROR_UNDER_ZERO = "Cannot be under 0";

	/**
	 * Bank account's number
	 */
	private String accountNum;

	/**
	 * Name of the bank account's owner
	 */
	private String ownerName;

	/**
	 * Identity number of the bank account's owner
	 */
	private String ownerId;

	private double balance;

	/**
	 * Create a bank account. - Reminder: this is an abstract class
	 * 
	 * @throws IllegalBalanceException if balance is under zero
	 */
	public BankAccount(String accountNum, String ownerName, String ownerId, double balance)
			throws IllegalBalanceException {
		this.accountNum = accountNum;
		this.ownerName = ownerName;
		this.ownerId = ownerId;
		setBalance(balance);
	}

	public abstract void monthlyManagement() throws IllegalBalanceException;

	/**
	 * @return the accountNum
	 */
	protected String getAccountNum() {
		return accountNum;
	}

	/**
	 * @return the ownerName
	 */
	protected String getOwnerName() {
		return ownerName;
	}

	/**
	 * @return the ownerId
	 */
	protected String getOwnerId() {
		return ownerId;
	}

	/**
	 * @return the balance
	 */
	protected double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set.
	 * @throws IllegalBalanceException if balance is less than 0.
	 */
	private void setBalance(double balance) throws IllegalBalanceException {
		if (balance < 0) {
			throw new IllegalBalanceException(ERROR_UNDER_ZERO);
		}
		this.balance = balance;
	}

	/**
	 * Add money to the bank account.
	 * 
	 * @param value to add to current balance
	 */
	public void deposit(double value) {
		this.balance += value;
	}

	/**
	 * Take out money from the bank account.
	 * 
	 * @param value to subtract from current balance.
	 * @throws IllegalBalanceException if balance is less than {@code value} (i.e if
	 *                                 balance would be under zero)
	 */
	public void withdrawal(double value) throws IllegalBalanceException {
		double balanceToBe = this.balance - value;
		if (balanceToBe < 0) {
			throw new IllegalBalanceException(ERROR_UNDER_ZERO);
		}
		this.balance -= value;
	}

	public String toString() {
		return ("Account Number: " + this.accountNum) + ("\nOwner Name: " + this.ownerName)
				+ ("\nOwner Id: " + this.ownerId) + ("\nBalance: " + this.balance);
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof BankAccount)) {
			return false;
		}
		BankAccount otherBankAccount = (BankAccount) obj;
		return this.accountNum == otherBankAccount.accountNum && this.ownerName == otherBankAccount.ownerName
				&& this.ownerId == otherBankAccount.ownerId;
	}
}
