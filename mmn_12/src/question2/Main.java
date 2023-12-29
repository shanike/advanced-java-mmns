package question2;

public class Main {
	public static void main(String[] args) {
		BankAccount[] accounts = new BankAccount[6];
		accounts[0] = new ServiceChargeChecking("01", "AAA", "123", 100, 30);
		accounts[1] = new NoServiceChargeChecking("02", "BBB", "321", 100);
		accounts[2] = new NoServiceChargeChecking("03", "CCC", "123", 100, 90);
		accounts[3] = new InterestChecking("04", "DDD", "321", 100);
		accounts[4] = new SavingsAccount("05", "EEE", "123", 100);
		accounts[5] = new HighInterestSavings("06", "FFF", "456", 100);

		printAccounts(accounts);

		for (BankAccount bankAccount : accounts) {
			System.out.println();
			double randomWithdrawal = Math.random() * bankAccount.getBalance();
			try {
				System.out.println("withdrawal " + randomWithdrawal + " from " + bankAccount.getAccountNum());
				bankAccount.withdrawal(randomWithdrawal);
			} catch (IllegalBalanceException e) {
				System.out.println("ERROR to withdrawal, balance is not high enough to withdrawal " + randomWithdrawal);
			}
		}

		printAccounts(accounts);

		for (BankAccount bankAccount : accounts) {
			try {
				System.out.println("withdrawing monthly management from " + bankAccount.getAccountNum());
				bankAccount.monthlyManagement();
			} catch (IllegalBalanceException e) {
				System.out.println(
						"ERROR withdrawing monthly management, balance is not high enough to manage");
			}
		}
	}

	public static void printAccounts(BankAccount[] accounts) {
		for (BankAccount bankAccount : accounts) {
			System.out.println();
			System.out.println(bankAccount.getClass());
			System.out.println(bankAccount);
		}
	}
}
