package testmain;

public class Codility {

	public static void main(String[] args) {
		Codility.Ferrum_2018();
	}//END OF FUNCTION
	
	public static void Ferrum_2018() {
		int[] A = new int[9];
		//     1   2           6   7
		//[−1, −1, 1, −1, 1, 0, 1, −1, −1]
		A[0] = -1;
		A[1] = -1;
		A[2] = 1;
		A[3] = -1;
		A[4] = 1;
		A[5] = 0;
		A[6] = 1;
		A[7] = -1;
		A[8] = -1;
		//[1, 1, −1, −1, −1, −1, −1, 1, 1]
//		A[0] = 1;
//		A[1] = 1;
//		A[2] = -1;
//		A[3] = -1;
//		A[4] = -1;
//		A[5] = -1;
//		A[6] = -1;
//		A[7] = 1;
//		A[8] = 1;
		int maxLength = 0;
		for(int i=0; i<A.length; i++) {
			int tmpSum = 0;
			int tmpEnd = A.length-1;
			boolean negativeFlag = false;
			for(int j=i; j<A.length; j++) {
				tmpSum += A[j];
				if(tmpSum >= 0) {
					negativeFlag = false;
					tmpEnd = j;
					continue;
				} else {
					if(!negativeFlag) {
						negativeFlag = true;
						tmpEnd = j-1;
					}
 					if(A.length-1-j+tmpSum < 0)
						break;
				}
			}
			System.out.println(i + " : " + tmpEnd);
			if(i < tmpEnd && (tmpEnd-i+1) > maxLength)
				maxLength = (tmpEnd-i+1);
		}
//		for(int i=0; i<A.length; i++) {
//			for(int j=0; j<A.length; j++) {
//				int tmpSum = 0;
//				for(int k=i; k<=j; k++) {
//					tmpSum += A[k];
//				}
//				if(tmpSum >= 0 && maxLength < (j-i+1)) {
//					maxLength = (j-i+1);
//				}
//			}
//		}
		System.out.println(maxLength);
	}//END OF FUNCTION
}//END OF CLASS
