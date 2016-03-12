
public class Driver {

	public static void main(String[] args){
		String hex = HexNumber.positiveDecimalToBinary(58439, 64);
		String hexNeg = HexNumber.negativeDecimalToBinary(-58439, 64);
		System.out.println("POSITIVE DECIMAL TO BINARY: " + hex);
		System.out.println("NEGATVE DECIMAL TO BINARY:  " + hexNeg);

		System.out.println(hex.length());
		String comp = HexNumber.twosComplement(HexNumber.reverseBits("10010110"));
		System.out.println(comp);
		
		
	}	
}
