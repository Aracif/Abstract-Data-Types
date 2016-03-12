
public class HexNumber {
	private static final long HEX_BASE = 16;
	private static final long BINARY_BASE = 2;
	private static final long LONG_BITS = 64;
	private static final String HEX_SYMBOLS = "10A11B12C13D14E15F"; 
	private static final String REGEX = "[ABCDEF]{1}";
	private static final String BINARY_REGEX = "[(01)]|[(10)]+";
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


	public static String hexValue(long decimal){
		return null;
	}

	public static long decimalValue(String hex){
		return -1;
	}

	public static String hexToBinary(String hex){
		return null;
	}

	public static String positiveDecimalToBinary(long decimal, int bits){

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

	public static String negativeDecimalToBinary(long decimal, int bits){
		String binary = positiveDecimalToBinary(decimal, bits);
		StringBuffer binaryReversed = reverseBits(binary);
		String negativeBinary = twosComplement(binaryReversed);
		return negativeBinary;
	}

	public static StringBuffer reverseBits(String bin){
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

	public static void testMethod(String bin){
		if(bin.matches(BINARY_REGEX)){
			System.out.println("match");
		}
		else{
			System.out.println("no match");
		}
	}

	public static String twosComplement(StringBuffer bin){
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

	public static String binaryToHexString(String binary){
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

	public static String convertHexStringToBinary(String hex){
		
		return null;
	}

	public static String convertNumberToLetter(int number){
		return HEX_SYMBOLS.substring(HEX_SYMBOLS.indexOf(number+"")+2, HEX_SYMBOLS.indexOf(number+"")+3);		
	}

}


