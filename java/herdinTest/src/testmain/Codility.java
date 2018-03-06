package testmain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Codility {
    
    public static void main(String[] args) {
//      Codility.Ferrum_2018();
//      Codility.Cobaltum_2018();
        System.out.println("Codility.Manganum_2017() : " + Codility.Manganum_2017());
    }//END OF FUNCTION
    
    public static void Lesson1_Iterations() {
        ;
    }//END OF FUNCTION
    public static void Lesson2_CyclicRotation() {
        //int[] A = {3, 8, 9, 7, 6};
        int[] A = {5, -1000};
        int K = 3;
        int[] B = null;
        if(A.length == 0 || K == 0 || (A.length <= K && K%A.length == 0)) {
        B = A;
        } else {
        if(A.length < K) {
          K = K%A.length;
        }
        B = new int[A.length];
        int Bi = 0;
        for(int Ai=A.length-K; Ai<A.length; Ai++) {
          B[Bi++] = A[Ai];
        }
        for(int Ai=0; Ai<A.length-K; Ai++) {
          B[Bi++] = A[Ai];
        }
        }
        for(int b : B) {
        System.out.print(b + " ");
        }
    }//END OF FUNCTION
    public static void Lesson2_OddOccurrencesInArray() {
        int[] A = {9, 3, 9, 3, 9, 7, 9};
        int unpaired = -1;
        HashMap<Integer, Boolean> tmpMap = new HashMap<>();
        for(int i=0; i<A.length; i++) {
            if(tmpMap.get(new Integer(A[i])) == null || tmpMap.get(new Integer(A[i])).equals(Boolean.TRUE))
                tmpMap.put(new Integer(A[i]), Boolean.FALSE);
            else
                tmpMap.put(new Integer(A[i]), Boolean.TRUE);
        }
        
        Iterator<Integer> iter = tmpMap.keySet().iterator();
        while(iter.hasNext()) {
            Integer keyInt = (Integer)iter.next();
            if(tmpMap.get(keyInt).equals(Boolean.FALSE))
                unpaired = keyInt;
        }
        System.out.println(unpaired);
    }//END OF FUNCTION
    public static void Lesson3_TapeEquilibrium() {
//        int[] A = {3, 1, 2, 4, 3};
//        int[] A = {-3, 1, -2, -4, -3};
        int[] A = {-100, 100};
        int[] frontSum = new int[A.length-1];
        int[] backSum = new int[A.length-1];
        int tmpSum = 0;
        for(int i=0; i<A.length-1; i++) {
        tmpSum += A[i];
        frontSum[i] = tmpSum;//(tmpSum < 0)? tmpSum*-1:tmpSum;
        }
        tmpSum = 0;
        for(int i=A.length-1; i>0; i--) {
        tmpSum += A[i];
        backSum[A.length-1-i] = tmpSum;//(tmpSum < 0)? tmpSum*-1:tmpSum;
        }
        int minDiff = Integer.MAX_VALUE;
        for(int i=0; i<A.length-1; i++) {
        int tmpDiff = frontSum[i] - backSum[A.length-2-i];
        if(tmpDiff < 0) {
          tmpDiff *= -1;
        }
        if(minDiff > tmpDiff) {
          minDiff = tmpDiff;
        }
        }
        for(int fs : frontSum)
        System.out.print(fs + " ");
        System.out.println();
        for(int bs : backSum)
        System.out.print(bs + " ");
        System.out.println();
        System.out.println(minDiff);
    }//END OF FUNCTION
    /** 두 배열의 같은 인덱스만 최소한 변경하여 증가하는 두 배열을 만들고 최소 변경횟수 반환. 불가능하면 -1 */
    public static void Cobaltum_2018() {
        System.out.println();
        //배열의 크기 : 2-100,000
        //배열값의 크기 : -1,000,000,000 - 1,000,000,000
//        int[] A = {};
//        int[] B = {};
//        int[] A = {5, 3, 7, 7, 10};
//        int[] B = {1, 6, 6, 9, 9}; // return 2
//        int[] A = {5, -3, 6, 4, 8};
//        int[] B = {2, 6, -5, 1, 0}; // return -1
//        int[] A = {100, 001, 102, 103, 004, 005, 006};
//        int[] B = {000, 101, 002, 003, 104, 105, 106}; // return 3
//        int[] A = {100,   1, 102,   3, 104,   5, 106,   7, 108,   9, 110};
//        int[] B = {  0, 101,   2, 103,   4, 105,   6, 107,   8, 109,  10}; // return 3
        int[] A = {100,   1, 102, 102, 104, 104, 106, 106, 108, 108, 110};
        int[] B = {  0, 101,   2, 103, 103, 105, 105, 107, 107, 109, 109}; // return 3
//        int[] A = {1, 5, 6};
//        int[] B = {-2, 0, 2}; // return 0
//        int[] A = {3, 7, 5};
//        int[] B = {6, 4, 8}; // return 1
//        int[] A = {1, 5, 6};
//        int[] B = {-2, 0, 2}; // return 0
//        int[] A = {1, 5};
//        int[] B = {-2, 5}; // return 0
//        int[] A = {12, 11};
//        int[] B = {9, 13}; // return 1
//        int[] A = {6, 5};
//        int[] B = {8, 7}; // return -1
        
        boolean local = true;
        if(local) {
            for(int a : A)
                System.out.print(a + " ");
            System.out.println();
            for(int b : B)
                System.out.print(b + " ");
            System.out.println();
        }
        int swapCnt = 0;
        for(int i=0; i<A.length-1; i++) {
            //비정상
            if(A[i] >= A[i+1] || B[i] >= B[i+1]) {
                //교체하면 정상가능
                if(A[i] < B[i+1] && B[i] < A[i+1]) {
                    int tmp = A[i+1];
                    A[i+1] = B[i+1];
                    B[i+1] = tmp;
                    swapCnt++;
                } else {
                    swapCnt = -1;
                    break;
                }
            //정상
            } else {
                ;
            }
        }
        
        if(local) {
            System.out.println();
            for(int a : A)
                System.out.print(a + " ");
            System.out.println();
            for(int b : B)
                System.out.print(b + " ");
            System.out.println();
        }
        if(swapCnt > 0 && A.length-swapCnt < swapCnt) {
            swapCnt = A.length-swapCnt;
        }
        if(local) {
            System.out.println("swapCnt : " + swapCnt);
        }
        //이상하케 조금식 크게나옴 결과가
    }//END OF FUNCTION
    
    public static void Ferrum_2018() {
        int[] A = {-1, -1, 1, -1, 1, 0, 1, -1, -1};
//      int[] A = {1, 1, -1, -1, -1, -1, -1, 1, 1};
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
//      for(int i=0; i<A.length; i++) {
//          for(int j=0; j<A.length; j++) {
//              int tmpSum = 0;
//              for(int k=i; k<=j; k++) {
//                  tmpSum += A[k];
//              }
//              if(tmpSum >= 0 && maxLength < (j-i+1)) {
//                  maxLength = (j-i+1);
//              }
//          }
//      }
        System.out.println(maxLength);
    }//END OF FUNCTION
    /** 체커에서 최대로 얻을 수 있는 점수 p = 1 q = 1, (0, 0) 이 black, black 에만 있을 수 있음*/
    public static int Manganum_2017() {
//      int[] X = {};
//      int[] Y = {};
//      String T = "";
      int[] X = {3, 5, 1, 6};
      int[] Y = {1, 3, 3, 8};
      String T = "Xpqp"; // return 10
//      int[] X = {0, 3, 5, 1, 6};
//      int[] Y = {4, 1, 3, 3, 8};
//      String T = "pXpqp"; // return 2
//        int[] X = {0, 6, 2, 5, 3, 0};
//        int[] Y = {4, 8, 2, 3, 1, 6};
//        String T = "ppqpXp"; // return 12
        
        final boolean isLocal = true;
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
            System.out.println();
        }
         
        class littleSolution {
            class point {
                public int x;
                public int y;
            }
            private int[] X;
            private int[] Y;
            private char[] Tch;
            private HashMap<Integer, ArrayList<Integer>> hashCoord;
            public littleSolution(int[] X, int[] Y, char[] Tch, HashMap<Integer, ArrayList<Integer>> hashCoord) {
                this.X = X;
                this.Y = Y;
                this.Tch = Tch;
                this.hashCoord = hashCoord;
            }//END OF FUNCTION
            
            public int getMaxScore(int preX, int preY, int curX, int curY) {
                //up-left, fixed : x1-n, y1+n = x3, y3 : x1+y1 = x3+y3
                //fixed up-right : x1+n, y1+n = x2, y2 : x1-y1 = x2-y2
                int maxScore = 0;
                
                for(int i=0; i<X.length; i++) {
                    int tmpScore = 0;
                    if(isLocal) System.out.println("i : " + i + " : X, Y : " + X[i] + ", " + Y[i]);
                    if(Tch[i] != 'X' && curY < Y[i]) {
                        if(isLocal) System.out.println("ok 1");
                        point p = getLandingPoint(preX, preY, curX, curY, X[i], Y[i]);
                        if(p != null) {
                            if(isLocal) System.out.println("ok 2 : " + p.x + ", " + p.y);
                            tmpScore = (Tch[i] == 'p')? 1:10;
                            tmpScore += getMaxScore(curX, curY, p.x, p.y);
                        }
                    }
                    
                    if(tmpScore > maxScore) {
                        maxScore = tmpScore;
                    }
                    
                    if(isLocal) System.out.println("maxScore : " + maxScore);
                    
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
            }//END OF FUNCTION
            
            public point getLandingPoint(int preX, int preY, int curX, int curY, int posX, int posY) {
                //up-left  : (x1-n, y1+n) = (x3-m, y3-m) : n = {(x1-y1)-(x3-y3)}/2
                //up-right : (x1+n, y1+n) = (x2+m, y2-m) : n = {(x2+y2)-(x1+y1)}/2
                point p = null;
                //default, up-left
                if(curX <= preX && curY >= preY) {
                    if( ((curX-curY)-(posX-posY))%2 == 0 ) {
                        int n = ((curX-curY)-(posX-posY))/2;
                        int metX = curX - n;
                        int metY = curY + n;
                        if( (metX == curX && metY == curY) || (metY <= posY && metY > curY && !(curX == preX && curY == preY)) ) {
                            if(isValidCoordinate(posX-1, posY-1) && isValidCoordinate(posX+1, posY+1)) {
                                p = new point();
                                p.x = posX+1;
                                p.y = posY+1;
                            }
                        } else if(metX == posX && metY == posY) {
                            if(isValidCoordinate(posX+1, posY-1) && isValidCoordinate(posX-1, posY+1)) {
                                p = new point();
                                p.x = posX-1;
                                p.y = posY+1;
                            }
                        }
                    }
                }
                //up-right
                else {
                    if( ((posX+posY)-(curX+curY))%2 == 0 ) {
                        int n = ((posX+posY)-(curX+curY))/2;
                        int metX = curX + n;
                        int metY = curY + n;
                        if( (metX == curX && metY == curY) || (metY <= posY && metY > curY) ) {
                            if(isValidCoordinate(posX+1, posY-1) && isValidCoordinate(posX-1, posY+1)) {
                                p = new point();
                                p.x = posX-1;
                                p.y = posY+1;
                            }
                        } else if(metX == posX && metY == posY) {
                            if(isValidCoordinate(posX-1, posY-1) && isValidCoordinate(posX+1, posY+1)) {
                                p = new point();
                                p.x = posX+1;
                                p.y = posY+1;
                            }
                        }
                    }
                }
                return p;
            }//END OF FUNCTION
        }//END OF INNER-CLASS
        
        maxScore = new littleSolution(X, Y, Tch, hashCoord).getMaxScore(curX, curY, curX, curY);
        
        if(isLocal) System.out.println("max score : " + maxScore);
        
        return maxScore;
    }//END OF FUNCTION
}//END OF CLASS
