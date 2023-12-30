package question2;

public class IllegalMonthlyFeeException extends Exception {

	private static final long serialVersionUID = 6883422289849745630L;

	public IllegalMonthlyFeeException() {
		super();
	}

	public IllegalMonthlyFeeException(String message) {
		super(message);
	}

}
