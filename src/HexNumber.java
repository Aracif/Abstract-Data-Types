import java.util.Iterator;

import java.text.*;


public class HexNumber {
	private static final long HEX_BASE = 16;
	private static final long BINARY_BASE = 2;
	private static final int BIT_NUMBER = 64;
	private static final String HEX_SYMBOLS = "10A11B12C13D14E15F"; 
	private static final String HEX_INDEX ="ABCDEFabcdef";
	private static final int[] values = new int[]{10,11,12,13,14,15,10,11,12,13,14,15};
	private static final String BINARY_REGEX = "[(01)]|[(10)]+";
	private static final String HEX_VERIFY = "[0-9a-fA-F]";
	private String hexString;
	private long hexDecimalValue;

	public HexNumber(String hexValue){
		this.hexString = hexValue;
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


	private void setHexString(String hexVal){
		hexString = hexVal;
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
		String hexValue = "";
		if(decimal>=0){
			String positiveBinary = positiveDecimalToBinary(decimal,BIT_NUMBER);
			hexValue = binaryToHexString(positiveBinary);
		}
		else{
			String negativeBinary = negativeDecimalToBinary(decimal, BIT_NUMBER);
			hexValue = binaryToHexString(negativeBinary);
		}
		return hexValue;
	}

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
		if(Integer.valueOf(hexValueGetter(hex.substring(0, 1)))>=8){
			if(hex.length()<16){
				hex = paddingHexString(hex);
				setHexString(hex);
			}
			String hexToBin = convertHexToBinary(hex);
			
			StringBuffer reverseBits = reverseBits(hexToBin);
			String negativeBinary = twosComplement(reverseBits);
			//System.out.println(negativeBinary);
			val = binaryArithmetic(negativeBinary);

		}
		else{
			if(hex.length()<16){
				hex = paddingHexString(hex);
				setHexString(hex);
			}
			val = convertHexStringToDecimal(hex);
		}
		//System.out.println(hex);
		return val;
	}

	//Hex padding
	private String paddingHexString(String hex){
		String paddingValue = "0";
		if(Integer.valueOf(hexValueGetter(hex.substring(0, 1)))>=8){
			paddingValue = "F";
		}
			for(int i = 0; hex.length()!=16; i++){
				hex = paddingValue + hex;
			}		
		return hex;
	}
	
	//Perform binary arithmetic
	private long binaryArithmetic(String bin){
		long total = 0;
		String currentChar;
		//int lastOccurenceOfOne = bin.lastIndexOf("1");
		int start = bin.length() - 1;
		int next = bin.length();
		for(int i = 0; i<bin.length(); i++){
			currentChar = bin.substring(start, next);
			
			if(hexValueGetter(currentChar)==1){
				
				total += Math.pow(BINARY_BASE, i);
			}
			start--;
			next--;
		}
		return total * -1;
	}

	//Converts a positive decimal value to binary
	private String positiveDecimalToBinary(long decimal, int bits){

		String binary = "";
		long currentDecimal = Math.abs(decimal);
		long currentBit;

		while(binary.length()!= bits){
			currentBit = currentDecimal%BINARY_BASE;
			binary = currentBit + binary;
			currentDecimal /= 2;
		}
		return binary;
	}

	//Calls 3 methods positiveDecimalToBinary, positiveDecimalToBinary, and twosComplement
	//these methods each perform a critical step in converting a negative number to binary
	private String negativeDecimalToBinary(long decimal, int bits){
		String binary = positiveDecimalToBinary(decimal, bits);
		StringBuffer binaryReversed = reverseBits(binary);
		String negativeBinary = twosComplement(binaryReversed);
		return negativeBinary;
	}

	private StringBuffer reverseBits(String bin){
		StringBuffer binary = new StringBuffer(bin);
		int startIndex = 0;
		int nextIndex = 1;
		String currentBit;
		for(int i = 0; i < binary.length(); i++){
			currentBit = binary.substring(startIndex, nextIndex);
			if(currentBit.equals("0")){
				binary.replace(startIndex, nextIndex, "1");
			}
			else{
				binary.replace(startIndex, nextIndex, "0");
			}
			startIndex++;
			nextIndex++;
		}
		return binary;
	}



	private String twosComplement(StringBuffer bin){
		int startIndex = bin.length()-1;
		int nextIndex = bin.length();
		String currentBit = bin.substring(startIndex,nextIndex);
		if(bin.toString().contains("0")&& bin.toString().contains("1")){
			for(int i = 0; i < bin.length(); i++){
				if(currentBit.equals("0")){
					bin.replace(startIndex, nextIndex, "1");
					i = bin.length();
				}
				else{
					bin.replace(startIndex, nextIndex, "0");
				}
				startIndex--;
				nextIndex--;
				currentBit = bin.substring(startIndex,nextIndex);
			}
		}
		else{
			bin = reverseBits(bin.toString());
			bin.replace(bin.length()-1, bin.length(), "1");
		}

		return bin.toString();
	}

	private String binaryToHexString(String binary){
		StringBuffer hexNumbers = new StringBuffer();
		int currentHexValue = 0;
		int exponent = 0;
		int indexStart = binary.length() - 1;
		int nextIndex = binary.length() ;
		int currentBit = 0;
		for(int i = 0; i<binary.length()/4 ; i++){
			while(exponent<=3){
				currentBit = Integer.valueOf(binary.substring(indexStart, nextIndex));
				if(currentBit>0){
					currentHexValue += Math.pow(BINARY_BASE, exponent);
				}
				exponent++;
				indexStart--;
				nextIndex--;
			}
			if(currentHexValue>=10){
				hexNumbers.insert(0,convertNumberToLetter(currentHexValue));
			}
			else{
				hexNumbers.insert(0,currentHexValue);
			}

			currentHexValue=0;
			exponent = 0;
		}
		return hexNumbers.toString();
	}

	//Not used but may be useful in the future
	public static String fourBitBinary(int val){
		
		String binary = "";
		String currentBit ="";
		while(binary.length()<4){
			binary = val%2 + binary;
			val /= 2;
		}
		
		return binary;
	}

	//Convert hex to binary
	public static String convertHexToBinary(String hex){
		String binary = "";
		String currentChar;
		int start = hex.length()-1;
		int next = hex.length();
		while(binary.length()<64){
			currentChar = hex.substring(start, next);			
			
			int currentVal = hexValueGetter(currentChar);
			binary = fourBitBinary(currentVal) + binary;

			start--;
			next--;
		}
		return binary;
	}

	//Hex value getter
	public static int hexValueGetter(String hexChar){
		int hexVal = 0;
		if(HEX_INDEX.contains(hexChar)){
			hexVal = values[HEX_INDEX.indexOf(hexChar)];
		}
		else{
			hexVal = Integer.valueOf(hexChar);	
		}	
		return hexVal;
	}

	//Convert a signed hex string to a decimal
	private long convertHexStringToDecimal(String hex){
		int startIndex = hex.length()-1;
		int nextIndex = hex.length();
		String currentHexChar; 
		long currentHexValue = 0;
		long total = 0;
		long leftMostHex = 0;
		int hexLength = hex.length();

		for(int i = 0; i<hexLength; i++){
			currentHexChar = hex.substring(startIndex, nextIndex);

			currentHexValue = hexValueGetter(currentHexChar);
			if(i==hexLength-1){
				leftMostHex = currentHexValue;
			}


			total += currentHexValue * (long)Math.pow(HEX_BASE, i);
			startIndex--;
			nextIndex--;
		}
		if(leftMostHex>=8){
			return negativeHexToDecimalConversion(total, hexLength);
		}
		else{
			return total;
		}
	}

	private long negativeHexToDecimalConversion(long value, int hexLength){
		long nextHighestBit = (long)Math.pow(BINARY_BASE, hexLength*4);
		return (value-nextHighestBit);
	}

	private String convertNumberToLetter(int number){
		return HEX_SYMBOLS.substring(HEX_SYMBOLS.indexOf(number+"")+2, HEX_SYMBOLS.indexOf(number+"")+3);		
	}

}


