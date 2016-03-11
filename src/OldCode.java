
public class OldCode {
	//Convert hex string to decimal
	private long decimalValue(String hexString){
		int startIndex = 0;
		int nextIndex = 1;
		int stringSize = hexString.length();
		int exponent = hexString.length()-1;
		String currentChar = hexString.substring(startIndex, nextIndex);
		long currentVal = 0;
		long currentNum = 0;
		boolean isNeg = false;
		for(int i = 0; i<=stringSize; i++){
			if(currentChar.matches(REGEX)){
				currentNum = Integer.valueOf(HEX_SYMBOLS.substring(HEX_SYMBOLS.indexOf(currentChar)-2, HEX_SYMBOLS.indexOf(currentChar)));
				currentVal += (long)(currentNum * Math.pow(HEX_BASE, exponent));
			}
			else{
				currentNum = Integer.valueOf(currentChar);
				currentVal += (long)currentNum * Math.pow(HEX_BASE, exponent);

			}
			if(i==0 && currentNum >=8){

				isNeg=true;
			}

			exponent--;
			startIndex++;
			if(nextIndex + 1 > stringSize){
				i = stringSize;
				if(isNeg){
					return currentVal*=-1;
				}
				else{
					return currentVal;
				}
			}
			nextIndex++;

			currentChar = hexString.substring(startIndex, nextIndex);					
		}
		return -1;
	}

}
