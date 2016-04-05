import java.util.ArrayList;
import java.util.Iterator;

import java.text.*;

/**
 * This class creates a HexNumber ADT. It has two constructors, one accepts a string representing a hexadecimal value,
 * and the other accepts a decimal value. It contains various methods for performing operations on HexNumber objects.
 * For those of you unfamiliar or new to the term ADT, an ADT is a set of values and a set of operations to be performed 
 * on those values without specifying what the values will be or the operations to be performed hence the term abstract.
 * 
 * @author Sal Ficara
 * @version 3.0
 */

public class HexNumber {
	private static final long HEX_BASE = 16;
	private static Integer HEX_START_AT = 10;
	private static final String HEX_SYMBOLS = "10A11B12C13D14E15F"; 
	private static final String HEX_INDEX ="ABCDEFabcdef";
	private static final int[] values = new int[]{10,11,12,13,14,15,10,11,12,13,14,15};
	private static final String HEX_VERIFY = "[-0-9a-fA-F]";
	private String hexString;
	private long hexDecimalValue;

	/**
	 * HexNumber class constructor one
	 * 
	 * @param hexValue A hexadecimal string
	 */
	public HexNumber(String hexValue){
		setHexString(hexValue);
		hexDecimalValue = decimalValue(hexValue);
	}

	/**
	 * HexNumber class constructor two
	 * 
	 * @param decimalValue A decimal value
	 */
	public HexNumber(long decimalValue ){

		hexDecimalValue = decimalValue;
		hexString = hexValue(decimalValue);
	}

	/**
	 * Get the decimal value
	 * 
	 * @return A long decimal value
	 */
	public long getDecimalValue(){
		return hexDecimalValue;
	}

	/**
	 * Get the hex string class field
	 * 
	 * @return A string of hex values
	 */
	public String getHexValue(){
		return hexString;
	}

	/**
	 * Set the hex string class instance
	 * 
	 * @param hexVal A string of hex values
	 */
	private void setHexString(String hexVal){
		if(isHex(hexVal)==false){
			throw new InvalidHexException("Invalid Hex Value Detected", new Throwable());
		}
		else{
		hexString = hexVal;
		}
	}

	
	//Add two hex numbers
	/**
	 * Perform HexNumber addition
	 * 
	 * @param hexObject A HexNumber object
	 * @return HexNumber
	 */
	public HexNumber add(HexNumber hexObject){
		long temp = hexDecimalValue;
		return new HexNumber(temp += hexObject.getDecimalValue());
	}

	//Multiply two hex numbers
	/**
	 * Perform HexNumber multiply
	 * 
	 * @param hexObject A HexNumber object
	 * @return HexNumber
	 */
	public HexNumber multiply(HexNumber hexObject){
		long temp = hexDecimalValue;
		return new HexNumber(temp *= hexObject.getDecimalValue());
	}
	
	//Divide two hex numbers
	/**
	 * Perform HexNumber divide
	 * 
	 * @param hexObject A HexNumber object
	 * @return HexNumber
	 */
	public HexNumber divide(HexNumber hexObject){
		long temp = hexDecimalValue;
		return new HexNumber(temp /= hexObject.getDecimalValue());
	}
	
	//Subtract two hex numbers
	/**
	 * Perform HexNumber subtract
	 * 
	 * @param hexObject A HexNumber object
	 * @return HexNumber
	 */
	public HexNumber subtract(HexNumber hexObject){
		long temp = hexDecimalValue;
		return new HexNumber(temp - hexObject.getDecimalValue());
	}
	
	//To string
	
	/**
	 * Get the hex string
	 * 
	 *@return String 
	 *@override
	 */
	public String toString(){
		return hexString;
	}
	
	/**
	 * 
	 * @param hex A string of hex values
	 * @return Decimal value
	 */
	public static long decimalValue(String hex){
		return convertHexStringToDecimal(hex);
	}
	
	//convert a decimal value to a hex string
	/**
	 * 
	 * @param long
	 * @return String of hex values
	 */
	public static String hexValue(long decimal){
		String finalHexCheck = buildHexString(Math.abs(decimal));
		if(decimal < 0){
			finalHexCheck = "-" + finalHexCheck;
		}
		else if(decimal==0){
			finalHexCheck = "0";
		}
		return finalHexCheck;
	}
	
	///////////////////////////////Start decimal to hex conversion chain///////////////////////////////////////
	//Convert numeric hex values >= 10 and <= 15 to letters
	/**
	 * 
	 * @param decimal A long value
	 * @return String
	 */
	public static String buildHexString(long decimal){
		ArrayList<Integer> s = determineHexValues(getLargestHexBasedDivisor(decimal),decimal);
		int size = s.size();
		String hexString = "";
		for(int i = 0; i<size; i++){
			int currentInt = s.get(i);
			if(currentInt>=HEX_START_AT){
				hexString += convertNumberToLetter(currentInt);
			}
			else{
				hexString += String.valueOf(currentInt);
			}		
		}
		return hexString;
	}
	
	//Get the hex string numeric values
	/**
	 * 
	 * @param exp int value
	 * @param decimal long value
	 * @return ArrayList<Integer>
	 */
	private static ArrayList<Integer> determineHexValues(int exp, long decimal){
		ArrayList<Integer> s = new ArrayList<Integer>();
		long newDecimal = decimal;
		for(int i = exp; i>=0; i--){		
			int hexValue = (int) (decimal/=Math.pow(HEX_BASE, i));
			decimal = (long)(newDecimal%Math.pow(HEX_BASE, i));
			s.add(hexValue);
		}
		return s;	
	}
	
	//Get largest base 16 to a power that divides the decimal
	/**
	 * 
	 * @param decimal long value
	 * @return int
	 */
	private static int getLargestHexBasedDivisor(long decimal){
		long largestDivisor = Long.MAX_VALUE;
		int exp = -1;
		for(int i = 0; largestDivisor>0; i++){
			largestDivisor = (long)(decimal / Math.pow(HEX_BASE, i));
			exp = i-1;
		}
		return exp;
	}
	/////////////////////////end decimal to hex conversion chain////////////////////////////////////////////
	
	/////////////////////////Start hex to decimal conversion////////////////////////////////////
	//Hex value getter
	/**
	 * 
	 * @param hexChar String
	 * @return int
	 */
	private static int hexValueGetter(String hexChar){
		int hexVal = 0;
		if(HEX_INDEX.contains(hexChar)){
			hexVal = values[HEX_INDEX.indexOf(hexChar)];
		}
		else{
			if(!hexChar.equals("-")){
				hexVal = Integer.valueOf(hexChar);	
			}
		}	
		return hexVal;
	}

	//Convert a signed hex string to a decimal
	/**
	 * 
	 * @param hex String
	 * @return long
	 */
	private static long convertHexStringToDecimal(String hex){
		int startIndex = hex.length()-1;
		int nextIndex = hex.length();
		String currentHexChar; 
		long currentHexValue = 0;
		long total = 0;
		
		int hexLength = hex.length();

		for(int i = 0; i<hexLength; i++){
			currentHexChar = hex.substring(startIndex, nextIndex);
			currentHexValue = hexValueGetter(currentHexChar);
			total += currentHexValue * (long)Math.pow(HEX_BASE, i);
			startIndex--;
			nextIndex--;
		}
		if(hex.substring(0,1).equals("-")){
			total = -total;
		}		
			return total;
		
	}
/////////////////////////end hex to decimal conversion////////////////////////////////////
	
	//Verify is input is a valid hex value
	/**
	 * 
	 * @param hex String
	 * @return boolean
	 */
	public static boolean isHex(String hex){
		StringCharacterIterator s = new StringCharacterIterator(hex);
		Character currentChar=s.first();
					
		for(int i = 0; i<hex.length(); i++ ){		
			if(String.valueOf(currentChar).matches(HEX_VERIFY)){
			}
			else{
				return false;
			}
			currentChar = s.next();
		}
		return true;
	}
	
	/**
	 * 
	 * @param number int
	 * @return String
	 */
	private static String  convertNumberToLetter(int number){
		return HEX_SYMBOLS.substring(HEX_SYMBOLS.indexOf(number+"")+2, HEX_SYMBOLS.indexOf(number+"")+3);		
	}

}


