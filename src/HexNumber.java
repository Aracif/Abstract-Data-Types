
public class HexNumber {
	private static final long HEX_BASE = 16;
	private static final long BINARY_BASE = 2;
	private static final String HEX_SYMBOLS = "10A11B12C13D14E15F"; 
	private static final String REGEX = "[ABCDEF]{1}";
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


	//Convert Hex to binary
	private String hexToBinary(String hexString){
		int indexOne = -1;
		int indexTwo = 0;
		long currentVal = 0;
		String binaryString ="";
		String currentChar;
		boolean running = true;
		while(running){
			indexOne++;
			indexTwo++;
			if(indexOne == hexString.length()-1){
				running = false;
			}
			currentChar = hexString.substring(indexOne, indexTwo);
			if(currentChar.matches(REGEX)){
				currentVal = Long.valueOf("1" + (REGEX.indexOf(currentChar)-1) );
			}
			else{
				currentVal = Long.valueOf(currentChar);				
			}
			binaryString += binaryValues(currentVal);
		}
		System.out.println(binaryString);
		return binaryString;
	}


	//Convert binary to decimal
	private long binaryToDecimal(String bin){
		int last = bin.length();
		int nextToLast = last - 1;
		int exp = 0;
		String signVal = bin.substring(0, 1);
		long total = 0;
		boolean running = true;	
		String current = bin.substring(nextToLast, last);
		while(running){
			if(current.equals("1")){
				total += Math.pow(BINARY_BASE, exp);
			}
			exp++;
			last--;
			nextToLast--;
			if(nextToLast==0){
				running=false;
			}		
		current = bin.substring(nextToLast, last);
		}

		return signDetermination(total, signVal, exp);
	}
	
	//Determine if number is negative or positive
	public static long signDetermination(long decimal, String signBit, long exp){
		if(signBit.equals("1")){
			return decimal -= Math.pow(BINARY_BASE, exp);
		}
		else{
			return decimal;
		}
	}

	//Convert hex string to decimal
	private long decimalValue(String hexString){
		return binaryToDecimal(hexToBinary(hexString));
	}




	//Makes use of getLargestDivisor(), getMod(), and longToHexLetters() in order to build
	//the HexaDecimal representation of the parameter decimal value.
	private String hexValue(long decimalValue){		
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
			String twosC = twosComplementBinary(invertedBinary);
			return buildHexFromNegativeDecimalBinary(twosC);
		}
	}
	
	//Change last bit of a binary
	public String changeLastBit(String bin){
		return "0"+bin.substring(1, bin.length());
		
		
	}

	//Convert a decimal value to binary
	public static String binaryValues(long decimalValue){
		String zeroCount = "";
		String binaryString = "";
		if(decimalValue==0){
			return binaryString += 0;
		}
		if(decimalValue>=8){
			while(decimalValue>0){
				binaryString = (decimalValue%2) + binaryString;
				decimalValue /= 2;
				if(decimalValue==0){
					while(binaryString.length()%4!=0){
						binaryString = (decimalValue%2) + binaryString;
						decimalValue /= 2;	
					}
				}

			}
		}
		else{
			do{
				binaryString = (decimalValue%2) + binaryString;
				decimalValue /= 2;		
			}
			while(binaryString.length()%4!=0);
		}
		System.out.println(binaryString);
		return binaryString;
	}

	//Add one to the binary number first position to make it negative
	private String twosComplementBinary(String binString){
		String s = "";
		int lastIndex = binString.length();
		int prev = lastIndex - 1;
		String currentChar = binString.substring(prev, lastIndex);
		while(currentChar.equals("1") && prev>=1){
			s += "0";
			lastIndex--;
			prev--;
			currentChar = binString.substring(prev, lastIndex);
		}
		s = "1" + s;
		return binString.substring(0,prev) + s;
	}

	//Build the final hex string out of the twos complement binary string
	private String buildHexFromNegativeDecimalBinary(String bin){
		System.out.println("LAST STRING BEFORE CONVERSION: " + bin);
		String finalHex = "";
		int binaryLength = bin.length();
		int startIndex = 0;
		int nextIndex = 1;
		int exponent = 3;

		String currentChar = bin.substring(startIndex, nextIndex);
		if(bin.substring(startIndex, nextIndex).equals("0")){
			bin = "1111" + bin;
			binaryLength = bin.length();
			
		}
		int value = 0;

		for(int i = 0; i<=binaryLength; i++){

			if(exponent<= -1){
				if(value <= 9){
					finalHex += value + "";
				}
				else{
					finalHex += longToHexLetter(value);
				}
				exponent = 3;
				value = 0;

			}
			if(binaryLength==startIndex || nextIndex > binaryLength){
				return finalHex;
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
	private String invertABinaryValue(String binaryString){
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
	private long getLargestDivisor(long decimalValue, long base){
		long exponential = 0;	
		for(long power = 0;(decimalValue/(Math.pow(base, power)))>= 1; power++){
			exponential = power;
		}	
		return exponential;
	}

	//Get the remainder of the largest divisor and the previous number so the largest
	//divisor process can be repeated
	private long getMod(long decimalValue, long modValue){
		return decimalValue%modValue;
	}

	//Get a letter from the hexSymbols string depending on the parameter
	private String longToHexLetter(long longVal){

		//if(longVal >= 10 && longVal <= 15){
		return HEX_SYMBOLS.substring(HEX_SYMBOLS.indexOf(longVal+"")+2, HEX_SYMBOLS.indexOf(longVal+"")+3);		
	}


}
