package question2;

public class IllegalInterestRateException extends Exception {

	private static final long serialVersionUID = 8400012268668268752L;

	public IllegalInterestRateException() {
		super();
	}

	public IllegalInterestRateException(String message) {
		super(message);
	}
}
