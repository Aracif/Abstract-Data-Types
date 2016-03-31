
public class Driver {

	public static void main(String[] args){
		HexNumber numOne = new HexNumber(23423);
		System.out.println(numOne.getHexValue());
		HexNumber numTwo = new HexNumber("1B");
		System.out.println(numTwo.getDecimalValue());
	}	
}
