package question1;

public class ReachedMaxNumberException extends Exception {
	private static final long serialVersionUID = -4520466865633469415L;

	public ReachedMaxNumberException(String errorMessage) {
        super(errorMessage);
    }

    public ReachedMaxNumberException() {
        super();
    }
}