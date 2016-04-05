/**
 * A custom exception
 * 
 * @author Sal Ficara
 *
 */
public class InvalidHexException extends IllegalArgumentException {
	public InvalidHexException(String s, Throwable t){
		super(s,t);
		
	}
	
}
