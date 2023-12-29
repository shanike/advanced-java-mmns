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
		this.setAccountNum(accountNum);
		this.setOwnerName(ownerName);
		this.setOwnerId(ownerId);
		this.setBalance(balance);
	}

	public abstract void monthlyManagement();

	// ------------------ Getter and Setters ------------------

	/**
	 * @return the accountNum
	 */
	protected String getAccountNum() {
		return accountNum;
	}

	/**
	 * @param accountNum the accountNum to set
	 */
	protected void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	/**
	 * @return the ownerName
	 */
	protected String getOwnerName() {
		return ownerName;
	}

	/**
	 * @param ownerName the ownerName to set
	 */
	protected void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	/**
	 * @return the ownerId
	 */
	protected String getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	protected void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the balance
	 */
	protected double getBalance() {
		return balance;
	}

	/**
	 * @param balance the balance to set
	 */
	protected void setBalance(double balance) {
		this.balance = balance;
	}

	// ------------------ Action Methods ------------------

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
