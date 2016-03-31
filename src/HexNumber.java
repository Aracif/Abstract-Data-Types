import java.util.ArrayList;
import java.util.Iterator;

import java.text.*;


public class HexNumber {
	private static final long HEX_BASE = 16;
	private static final long BINARY_BASE = 2;
	private static final int BIT_NUMBER = 64;
	private Integer HEX_START_AT = 10;
	private static final String HEX_SYMBOLS = "10A11B12C13D14E15F"; 
	private static final String HEX_INDEX ="ABCDEFabcdef";
	private static final int[] values = new int[]{10,11,12,13,14,15,10,11,12,13,14,15};
	private static final String HEX_VERIFY = "[0-9a-fA-F]";
	private String hexString;
	private long hexDecimalValue;

	public HexNumber(String hexValue){
		setHexString(hexValue);
		hexDecimalValue = decimalValue(hexValue);
	}

	public HexNumber(long decimalValue ){

		hexDecimalValue = decimalValue;
		hexString = hexValue(decimalValue);
	}

	public long getDecimalValue(){
		return hexDecimalValue;
	}

	public String getHexValue(){
		return hexString;
	}

	private void setHexStringCatch(String hexVal){
		try{
			if(isHex(hexVal)==true){
				hexString = hexVal;
			}		
		}		
		catch(InvalidHexException e){
			e.printStackTrace();
		}		
	}
	
	private void setHexString(String hexVal){
		if(isHex(hexVal)==false){
			throw new InvalidHexException("Invalid Hex Value Detected", new Throwable());
		}
		else{
		hexString = hexVal;
		}
	}

	private void setDecimalValue(long decimalVal){
		hexDecimalValue = decimalVal;
	}
	
	//Add two hex numbers
	public void add(HexNumber hexObject){
		hexDecimalValue += hexObject.getDecimalValue();
		hexString = hexValue(hexDecimalValue);
	}

	//Multiply two hex numbers
	public void multiply(HexNumber hexObject){
		hexDecimalValue = hexDecimalValue * hexObject.getDecimalValue();
		hexString = hexValue(hexDecimalValue);
	}
	
	//Divide two hex numbers
	public void divide(HexNumber hexObject){
		hexDecimalValue = hexDecimalValue / hexObject.getDecimalValue();
		hexString = hexValue(hexDecimalValue);
	}
	
	//Substract two hex numbers
	public void subtract(HexNumber hexObject){
		hexDecimalValue = hexDecimalValue - hexObject.getDecimalValue();
		hexString = hexValue(hexDecimalValue);
	}
	
	//To string
	public String toString(){
		return hexString;
	}
	
	//convert a decimal value to a hex string
	public String hexValue(long decimal){
		String finalHexCheck = buildHexString(Math.abs(decimal));
		if(decimal < 0){
			finalHexCheck = "-" + finalHexCheck;
		}
		return finalHexCheck;
	}
	
	///////////////////////////////Start hex conversion chain///////////////////////////////////////
	//Convert numeric hex values >= 10 and <= 15 to letters
	public String buildHexString(long decimal){
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
	private ArrayList<Integer> determineHexValues(int exp, long decimal){
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
	private int getLargestHexBasedDivisor(long decimal){
		long largestDivisor = Long.MAX_VALUE;
		int exp = -1;
		for(int i = 0; largestDivisor>0; i++){
			largestDivisor = (long)(decimal / Math.pow(HEX_BASE, i));
			exp = i-1;
		}
		return exp;
	}
	/////////////////////////end hex conversion chain////////////////////////////////////////////
	
	//Verify is input is a valid hex value
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
	
	//Convert a hex string to a decimal value
	public long decimalValue(String hex){
		long val = 0;

		return val;
	}

	private String convertNumberToLetter(int number){
		return HEX_SYMBOLS.substring(HEX_SYMBOLS.indexOf(number+"")+2, HEX_SYMBOLS.indexOf(number+"")+3);		
	}

}


