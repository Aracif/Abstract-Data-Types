
public class Driver {

	public static void main(String[] args){
		String hex = HexNumber.positiveDecimalToBinary(93484, 64);		
		String hexNeg = HexNumber.negativeDecimalToBinary(-93484, 64);
		

		
		String binToHex = HexNumber.binaryToHexString(hexNeg);
		System.out.println(binToHex);
		
		
		
		
		
	}	
}
