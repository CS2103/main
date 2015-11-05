package Logic;

public class InvalidTimeException extends Exception {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// Parameterless Constructor
	public InvalidTimeException() {
	}

	// Constructor that accepts a message
	public InvalidTimeException(String message) {
		super(message);
	}
}