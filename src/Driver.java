
public class Driver {

	public static void main(String[] args){
		String hex = HexNumber.positiveDecimalToBinary(5342484, 64);		
		String hexNeg = HexNumber.negativeDecimalToBinary(-5342484, 64);
		

		
		
		String binToHex = HexNumber.negativeBinaryToHexString(hex);
		System.out.println(binToHex);
		
		
		
		
		
	}	
}
