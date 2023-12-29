package question2;

/**
 * An (abstract) bank account with an option to write checks.
 */
public abstract class CheckingAccount extends BankAccount {

    /**
     * Create a checking bank account
     */
    public CheckingAccount(String accountNum, String ownerName, String ownerId, double balance) {
        super(accountNum, ownerName, ownerId, balance);
    }

    /**
     * Write a check
     * @param checkValue the value of the check
     * @throws IllegalBalanceException if {@code checkedValue} is higher than
     *                                 current account balance
     */
    public void writeCheck(double checkValue) throws IllegalBalanceException {
        this.withdrawal(checkValue);
    }

}
