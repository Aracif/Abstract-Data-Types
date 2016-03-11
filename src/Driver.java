
public class Driver {

	public static void main(String[] args){
		HexNumber h = new HexNumber(-91);
		System.out.println(h.getHexValue());

		HexNumber.binaryToDecimal(HexNumber.hexToBinary("A5"));

	}
}
