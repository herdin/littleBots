package testmain;

public class JustTest {
	
	public static void main(String[] args) {
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
			for(int j=0; j<A.length; j++) {
				int tmpSum = 0;
				for(int k=i; k<=j; k++) {
					tmpSum += A[k];
				}
				if(tmpSum >= 0 && maxLength < (j-i+1)) {
					maxLength = (j-i+1);
				}
			}
		}
		System.out.println(maxLength);
	}//END OF FUCNTION
}//END OF CLASS
