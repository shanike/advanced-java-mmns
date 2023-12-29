package question2;

/**
 * An abstract bank account
 */
public abstract class BankAccount {
	private String accountNum;
	private String ownerName;
	private String ownerId;
	private double balance;

	public BankAccount(String accountNum, String ownerName, String ownerId, double balance) {
		this.accountNum = accountNum;
		this.ownerName = ownerName;
		this.ownerId = ownerId;
		this.balance = balance;
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
	 * @param value to add to current balance
	 */
	public void deposit(double value) {
		this.balance += value;
	}

	/**
	 * @param value to subtract from current balance
	 * @throws IllegalBalanceException if balance is less than {@code value}
	 */
	public void withdrawal(double value) throws IllegalBalanceException {
		if (value > this.balance) {
			throw new IllegalBalanceException();
		}
		this.balance -= value;
	}

	public String toString() {
		return "Account Number: " + this.accountNum + "\nOwner Name: " + this.ownerName + "\nOwner Id: " + this.ownerId
				+ "\nBalance: " + this.balance;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof BankAccount)) {
			return false;
		}
		BankAccount otherBankAccount = (BankAccount) obj;
		return this.accountNum == otherBankAccount.accountNum && this.ownerName == otherBankAccount.ownerName
				&& this.ownerId == otherBankAccount.ownerId && this.balance == otherBankAccount.balance;
	}
}
