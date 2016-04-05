
public class Driver {

	/**
	 * Main method, tests out ADT functionality to show correctness 
	 * @param args
	 */
	public static void main(String[] args){
		HexNumber numOne = new HexNumber("-A");
		System.out.println(numOne.getDecimalValue());
		HexNumber numTwo = new HexNumber("1B");
		System.out.println(numTwo.getDecimalValue());
		//ADD
		HexNumber hexThree = numOne.add(numTwo);
		System.out.println("Hex: " + hexThree.getHexValue());
		System.out.println("Decimal: " + hexThree.getDecimalValue());		
		//SUBTRACT
		HexNumber hexFour = numOne.subtract(numTwo);
		System.out.println("Hex: " + hexFour.getHexValue());
		System.out.println("Decimal: " + hexFour.getDecimalValue());
		//Multiply
		HexNumber hexFive = numOne.multiply(numTwo);
		System.out.println("Hex: " + hexFive.getHexValue());
		System.out.println("Decimal: " + hexFive.getDecimalValue());
		//Divide
		HexNumber hexSix = numOne.divide(numTwo);
		System.out.println("Hex: " + hexSix.getHexValue());
		System.out.println("Decimal: " + hexSix.getDecimalValue());
		//  (A + (40 / 4) - 14) * 2 calc
		HexNumber hex1 = new HexNumber("A");
		HexNumber hex2 = new HexNumber("40");
		HexNumber hex3 = new HexNumber("4");
		HexNumber hex4 = new HexNumber("14");
		HexNumber hex5 = new HexNumber("2");
		HexNumber hex6 = (hex1.add((hex2.divide(hex3).subtract(hex4))).multiply(hex5));
		System.out.println("Hex: " + hex6.getHexValue());
		System.out.println("Decimal: " + hex6.getDecimalValue());
	}	
}
