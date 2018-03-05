package testmain;

public class Codility {

	public static void main(String[] args) {
//		Codility.Ferrum_2018();
		Codility.Cobaltum_2018();
	}//END OF FUNCTION
	
	public static void Cobaltum_2018() {
		int[] A = {5, 3, 7, 7, 10};
		int[] B = {1, 6, 6, 9, 9};
		
		for(int a : A)
			System.out.print(a + " ");
		System.out.println();
		for(int b : B)
			System.out.print(b + " ");
		System.out.println();
//		int[] diffA = new int[A.length];
//		int[] diffB = new int[A.length];
//		for(int i=0; i<A.length; i++) {
//			if(i==0) {
//				diffA[i] = A[i];
//				diffB[i] = B[i];
//			} else {
//				diffA[i] = A[i]-A[i-1];
//				diffB[i] = B[i]-B[i-1];
//			}
//		}
//		
//		for(int a : diffA)
//			System.out.print(a + " ");
//		System.out.println();
//		for(int b : diffB)
//			System.out.print(b + " ");
//		System.out.println();
		int swapCnt = 0;
		for(int i=0; i<A.length; i++) {
			if(
				(i<=A.length-2) &&
				(	(	A[i]>=A[i+1] && A[i]<B[i+1] && B[i]<A[i+1] &&
						(0<A.length && A[i-1]<B[i]) &&
						(i<=A.length-3 && )
					)
					||
					(	B[i] >= B[i+1] && B[i] < A[i+1] && A[i] < B[i+1] &&
					)
				)
			){
				int tmp = A[i];
				A[i] = B[i];
				B[i] = tmp;
				swapCnt++;
			}
		}
		System.out.println(swapCnt);
		for(int a : A)
			System.out.print(a + " ");
		System.out.println();
		for(int b : B)
			System.out.print(b + " ");
		System.out.println();
	}//END OF FUNCTION
	
	public static void Ferrum_2018() {
		int[] A = {-1, -1, 1, -1, 1, 0, 1, -1, -1};
//		int[] A = {1, 1, -1, -1, -1, -1, -1, 1, 1};
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
