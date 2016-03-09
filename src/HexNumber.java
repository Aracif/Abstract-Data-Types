import java.util.HashMap;

public class HexNumber {
	private static final long HEX_BASE = 16;
	private static final long BINARY_BASE = 2;
	private static final String hexSymbols = "10A11B12C13D14E15F"; 
	private String hexString;
	private long hexDecimalValue;

	public HexNumber(String hexValue){
		this.hexString = hexValue;

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




	//Makes use of getLargestDivisor(), getMod(), and longToHexLetters() in order to build
	//the HexaDecimal representation of the parameter decimal value.
	private static String hexValue(long decimalValue){		
		long currentValue = 0;
		String hexString = "";			
		long exponential = getLargestDivisor(decimalValue,HEX_BASE);
		if(decimalValue>=0){
			while(exponential>=0){
				currentValue = (long)(decimalValue/Math.pow(HEX_BASE, exponential));
				if((currentValue >= 10 && currentValue<=15)||(currentValue <= -10 && currentValue>=-15)){
					hexString += longToHexLetter(currentValue);
				}
				else{
					hexString += currentValue;
				}

				decimalValue = getMod(decimalValue, (long)Math.pow(HEX_BASE, exponential));
				exponential--;
			}
			return hexString;
		}
		else{
			String binaryNum = binaryValues(Math.abs(decimalValue));
			String invertedBinary = invertABinaryValue(binaryNum);
			return buildHexFromNegativeDecimalBinary(twosComplementBinary(invertedBinary));
		}
	}

	//Convert a decimal value to binary
	public static String binaryValues(long decimalValue){
		String binaryString = "";
		if(decimalValue==0){
			return binaryString += 0;
		}
		while(decimalValue>0){
			binaryString = (decimalValue%2) + binaryString;
			decimalValue /= 2;
		}
		if(binaryString.length()%4!=0){
			while(binaryString.length()%4!=0){
				binaryString = "0" + binaryString;
			}
		}
		return binaryString;
	}

	//Add one to the binary number first position to make it negative
	public static String twosComplementBinary(String binString){
		String s = "";
		int lastIndex = binString.length();
		int prev = lastIndex - 1;
		String currentChar = binString.substring(prev, lastIndex);
		while(currentChar.equals("1") && prev>=1){
			s += 0;
			lastIndex = prev;
			prev--;
			currentChar = binString.substring(prev, lastIndex);
		}
		s = "1" + s;
		return binString.substring(0,prev) + s;
	}

	//Build the final hex string out of the twos complement binary string
	public static String buildHexFromNegativeDecimalBinary(String bin){
		String finalHex = "";

		int binaryLength = bin.length();
		int startIndex = 0;
		int nextIndex = 1;
		int exponent = 3;

		String currentChar = bin.substring(startIndex, nextIndex);
		int value = 0;

		for(int i = 0; i<=binaryLength; i++){

			if(exponent<= -1){
				if(value <= 9){
					finalHex += value + "";
				}
				else{
					finalHex += longToHexLetter(value);
				}
				if(binaryLength==startIndex){
					return finalHex;
				}
				exponent = 3;
				value = 0;

			}
			currentChar = bin.substring(startIndex, nextIndex);
			if(currentChar.equals("1")){
				value += Math.pow(2, exponent);
				startIndex++;
				nextIndex++;
				exponent--;
			}
			else{
				startIndex++;
				nextIndex++;
				exponent--;
			}
		}
		return finalHex;	
	}

	//Invert a binary value
	public static String invertABinaryValue(String binaryString){
		String currentChar;
		String reversedBinary = "";
		int start = 0;
		int next = 1;
		while(next<=binaryString.length()){
			currentChar = binaryString.substring(start, next);
			if(currentChar.equals("0")){
				reversedBinary += "1";
			}
			else{
				reversedBinary += "0";
			}
			start++;
			next++;
		}
		return reversedBinary;
	}



	//Get the largest value of the base to a power that divides the parameter at least once
	//Easily adaptable to accommodate other systems
	private static long getLargestDivisor(long decimalValue, long base){
		long exponential = 0;	
		for(long power = 0;(decimalValue/(Math.pow(base, power)))>= 1; power++){
			exponential = power;
		}	
		return exponential;
	}

	//Get the remainder of the largest divisor and the previous number so the largest
	//divisor process can be repeated
	private static long getMod(long decimalValue, long modValue){
		return decimalValue%modValue;
	}

	//Get a letter from the hexSymbols string depending on the parameter
	private static String longToHexLetter(long longVal){
		return hexSymbols.substring(hexSymbols.indexOf(longVal+"")+2, hexSymbols.indexOf(longVal+"")+3);		
	}


}
