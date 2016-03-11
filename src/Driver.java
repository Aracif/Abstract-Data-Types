
public class Driver {

	public static void main(String[] args){
		HexNumber h = new HexNumber(-45935);
		System.out.println(h.getHexValue());
		HexNumber h2 = new HexNumber("F4C91");
		System.out.println(h2.getDecimalValue());

		//Working  -45935	
		//Working  -195
		//Working  -455
	}
}
