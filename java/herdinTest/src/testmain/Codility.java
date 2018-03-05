package testmain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Codility {
	
	public static void main(String[] args) {
//		Codility.Ferrum_2018();
//		Codility.Cobaltum_2018();
		Codility.Manganum_2017();
	}//END OF FUNCTION
	
	public static void Cobaltum_2018() {
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
	
	public static int Manganum_2017() {
//		int[] X = {};
//		int[] Y = {};
//		String T = "";
//		int[] X = {3, 5, 1, 6};
//		int[] Y = {1, 3, 3, 8};
//		String T = "Xpqp"; // return 10
//		int[] X = {0, 3, 5, 1, 6};
//		int[] Y = {4, 1, 3, 3, 8};
//		String T = "pXpqp"; // return 2
		int[] X = {0, 6, 2, 5, 3, 0};
		int[] Y = {4, 8, 2, 3, 1, 6};
		String T = "ppqpXp"; // return 12
		
		boolean isLocal = true;
		int maxScore = -1;
		char[] Tch = T.toCharArray();
		if(X.length != Y.length || X.length != Tch.length) return maxScore;
		
		if(isLocal) {
			System.out.print("X   : ");
			for(int x : X)
				System.out.print(x + " ");
			System.out.println();
			System.out.print("Y   : ");
			for(int y : Y)
				System.out.print(y + " ");
			System.out.println();
			System.out.print("Tch : ");
			for(char ch : Tch)
				System.out.print(ch + " ");
			System.out.println();
		}
		
		HashMap<Integer, ArrayList<Integer>> hashCoord = new HashMap<>();
		int curX = 0;
		int curY = 0;
		for(int i=0; i<X.length; i++) {
			if(Tch[i] == 'X') {
				curX = X[i];
				curY = Y[i];
			} else if(hashCoord.get(Integer.valueOf(X[i])) == null) {
				ArrayList<Integer> ys = new ArrayList<>();
				ys.add(Integer.valueOf(Y[i]));
				hashCoord.put(Integer.valueOf(X[i]), ys);
			} else {
				ArrayList<Integer> ys = hashCoord.get(Integer.valueOf(X[i]));
				ys.add(Integer.valueOf(Y[i]));
			}
		}
		
		if(isLocal) {
			System.out.println("curX, curY : " + curX + ", " + curY);
			System.out.println("hashMap : ");
			Iterator iter = hashCoord.keySet().iterator();
			while(iter.hasNext()) {
				Integer key = (Integer) iter.next();
				System.out.println(key + " : " + hashCoord.get(key));
			}
		}
		 
		class littleSolution {
			private int[] X;
			private int[] Y;
			private char[] Tch;
			public littleSolution(int[] X, int[] Y, char[] Tch) {
				this.X = X;
				this.Y = Y;
				this.Tch = Tch;
			}//END OF FUNCTION
			
			public int getMaxScore(int curX, int curY, boolean isCoordFixed, boolean isLeft) {
				//fixed up-left  : x1-n, y1+n = x3, y3 : x1+y1 = x3+y3
				//fixed up-right : x1+n, y1+n = x2, y2 : n = {(x2+y2)-(x1+y1)}/2
				//up-left  : (x1-n, y1+n) = (x3-m, y3-m) : n = {(x1-y1)-(x3-y3)}/2
				//up-right : (x1+n, y1+n) = (x2+m, y2-m) : n = {(x2+y2)-(x1+y1)}/2
				//point that middle of meetpoint will be ignored. cuz obviously smaller.
				int maxScore = 0;
				
				for(int i=0; i<X.length; i++) {
					int tmpScore = 0;
					
					if(Tch[i] != 'X' && )
					
					
					
					
					if(tmpScore > maxScore) {
						maxScore = tmpScore;
					}
				}//END OF FOR-LOOP
				
				return maxScore;
			}//END OF FUNCTION
			
			public boolean isValidCoordinate(int testX, int testY) {
				boolean validity = true;
				ArrayList<Integer> ys = hashCoord.get(Integer.valueOf(testX));
				if(ys != null) {
					for(Integer y : ys) {
						if(y.equals(Integer.valueOf(testY))) {
							validity = false;
							break;
						}
					}
				}
				return validity;
			}
		}//END OF INNER-CLASS
		
		
		System.out.println("max score : " + new littleSolution(X, Y, Tch).getMaxScore(curX, curY, true));
		
		return maxScore;
	}//END OF FUNCTION
}//END OF CLASS