package utils;

/**
 * 
 * @author walter
 * This class will help us return an error message for the client
 */
public class Error {
	
	private final String error;

    public Error(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
    
}
