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
    /**  */
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
    /** 체커에서 최대로 얻을 수 있는 점수 p = 1 q = 1, (0, 0) 이 black, black 에만 있을 수 있음 */
    public static int Manganum_2017() {
//      int[] X = {};
//      int[] Y = {};
//      String T = "";
        int[] X = null;
        int[] Y = null;
        String T = null;
        int expectResult = -1;
        final int TEST_CASE_NUMBER = 21;
        switch(TEST_CASE_NUMBER) {
            //ex test
            case 1 :
                int[] X1 = {3, 5, 1, 6};
                int[] Y1 = {1, 3, 3, 8};
                String T1 = "Xpqp";
                expectResult = 10;
                X = X1;
                Y = Y1;
                T = T1;
                break;
            case 2 :
                int[] X2 = {0, 3, 5, 1, 6};
                int[] Y2 = {4, 1, 3, 3, 8};
                String T2 = "pXpqp";
                expectResult = 2;
                X = X2;
                Y = Y2;
                T = T2;
                break;
            case 3 :
                int[] X3 = {0, 6, 2, 5, 3, 0};
                int[] Y3 = {4, 8, 2, 3, 1, 6};
                String T3 = "ppqpXp";
                expectResult = 12;
                X = X3;
                Y = Y3;
                T = T3;
                break;
            //user test
            case 11 :
                int[] X11 = {1, 9, 3, 6, 4};
                int[] Y11 = {6, 4, 4, 1, 3};
                String T11 = "qppXp";
                expectResult = 1;
                X = X11;
                Y = Y11;
                T = T11;
                break;
            //after 10, custom test case
            case 12 :
                int[] X12 = {1, 3, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4, 5, 4};
                int[] Y12 = {3, 1, 3+(5*0), 3+(5*1), 3+(5*2), 3+(5*3), 3+(5*4), 3+(5*5), 3+(5*6), 3+(5*7), 3+(5*8), 3+(5*9), 3+(5*10), 3+(5*11)};
                String T12 = "qXpppppppppppp";
                expectResult = 21;
                X = X12;
                Y = Y12;
                T = T12;
                break;
            case 13 :
                int[] X13 = {3, 5, 6, 7, 9};
                int[] Y13 = {4, 2, 1, 2, 4};
                String T13 = "ppXqp";
                expectResult = 11;
                X = X13;
                Y = Y13;
                T = T13;
                break;
            case 14 :
                int[] X14 = {1,  2, 3,  3,  4, 4, 5, 7, 8};
                int[] Y14 = {3, 13, 1, 15, 14, 8, 4, 9, 6};
                String T14 = "ppXppppqp";
                expectResult = 12;
                X = X14;
                Y = Y14;
                T = T14;
                break;
            case 15 :
                int[] X15 = {5, 5, 6};
                int[] Y15 = {6, 2, 1};
                String T15 = "ppX";
                expectResult = 2;
                X = X15;
                Y = Y15;
                T = T15;
                break;
            case 16 :
                int[] X16 = {5, 5, 5, 5, 5, 6, 7, 7, 7, 7, 7};
                int[] Y16 = {7, 6, 5, 4, 3, 1, 7, 6, 5, 4, 3};
                String T16 = "pppppXppppp";
                expectResult = 0;
                X = X16;
                Y = Y16;
                T = T16;
                break;
            case 17 :
                int[] X17 = {2, 4, 6};
                int[] Y17 = {5, 3, 1};
                String T17 = "ppX";
                expectResult = 2;
                X = X17;
                Y = Y17;
                T = T17;
                break;
            //performance test, wrong answer case
            case 21 :
                int[] X21 = {1, 1, 1, 3, 3, 3, 5, 5, 5, 2,  28,  26,  30,  26,  30,  28,  27,  29,  28};
                int[] Y21 = {1, 3, 5, 1, 3, 5, 1, 3, 5, 0, 476, 478, 478, 480, 480, 482, 483, 483, 486};
                String T21 = "qqqppppqqXqqqqqqppq";
                expectResult = 61;
                X = X21;
                Y = Y21;
                T = T21;
                break;
        }
        
//        import java.util.ArrayList;
//        import java.util.HashMap;
//        import java.util.Iterator;
        final boolean isLocal = true;
        int maxScore = -1;
        char[] Tch = T.toCharArray();
        if(X.length != Y.length || X.length != Tch.length) return maxScore;
        
        //sort by y coordinate
        for(int i=0; i<X.length; i++) {
            for(int j=i+1; j<X.length; j++) {
                if(Y[j] > Y[i]) {
                    int tmp = Y[i];
                    Y[i] = Y[j];
                    Y[j] = tmp;
                    
                    tmp = X[i];
                    X[i] = X[j];
                    X[j] = tmp;
                    
                    char ch = Tch[i];
                    Tch[i] = Tch[j];
                    Tch[j] = ch;
                }
            }
        }
        
        //check test data
        if(isLocal) {
            System.out.println("TEST DATA : ");
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
        
        HashMap<String, Integer> maxScores = new HashMap<>();
        HashMap<Integer, ArrayList<Integer>> hashCoord = new HashMap<>();
        int curX = 0;
        int curY = 0;
        int minY = 0;
        int maxY = 0;
        for(int i=0; i<X.length; i++) {
            if(Tch[i] == 'X') {
                curX = X[i];
                curY = Y[i];
            }
            if(i==0) {
                minY = Y[i];
                maxY = Y[i];
            }
            if(maxY < Y[i]) {
                maxY = Y[i];
            }
            if(minY > Y[i]) {
                minY = Y[i];
            }
            ArrayList<Integer> ys = null;
            if(hashCoord.get(Integer.valueOf(X[i])) == null) {
                ys = new ArrayList<>();
                hashCoord.put(Integer.valueOf(X[i]), ys);
            } else {
                ys = hashCoord.get(Integer.valueOf(X[i]));
            }
            ys.add(Integer.valueOf(Y[i]));
        }
        
        if(isLocal) {
            System.out.println("curX, curY : " + curX + ", " + curY);
            System.out.println("minY, maxY : " + minY + ", " + maxY);
            System.out.println("hashCoord : ");
            Iterator iter = hashCoord.keySet().iterator();
            while(iter.hasNext()) {
                Integer key = (Integer) iter.next();
                System.out.println(key + " : " + hashCoord.get(key));
            }
        }
        
        class point {
            public int x;
            public int y;
            public point(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
        HashMap<Integer, ArrayList<point>> hashInclL = new HashMap<>();
        HashMap<Integer, ArrayList<point>> hashInclR = new HashMap<>();
        for(int i=0; i<X.length; i++) {
            int inclL = X[i]+Y[i];
            int inclR = X[i]-Y[i];
            
            ArrayList<point> pointsR = null;
            if(hashInclR.get(Integer.valueOf(inclR)) == null) {
                pointsR = new ArrayList<>();
                hashInclR.put(Integer.valueOf(inclR), pointsR);
            } else {
                pointsR = hashInclR.get(Integer.valueOf(inclR));
            }
            point point = new point(X[i], Y[i]);
            pointsR.add(point);
            
            ArrayList<point> pointsL = null;
            if(hashInclL.get(Integer.valueOf(inclL)) == null) {
                pointsL = new ArrayList<>();
                hashInclL.put(Integer.valueOf(inclL), pointsL);
            } else {
                pointsL = hashInclL.get(Integer.valueOf(inclL));
            }
            point = new point(X[i], Y[i]);
            pointsL.add(point);
        }
        
        if(isLocal) {
            Iterator iter = hashInclR.keySet().iterator();
            System.out.println("hashInclR : ");
            while(iter.hasNext()) {
                Integer key = (Integer)iter.next();
                System.out.print(key);
                for(point p : hashInclR.get(key)) {
                    System.out.print( " : " + p.x + ", " + p.y);
                }
                System.out.println();
            }
            
            iter = hashInclL.keySet().iterator();
            System.out.println("hashInclL : ");
            while(iter.hasNext()) {
                Integer key = (Integer)iter.next();
                System.out.print(key);
                for(point p : hashInclL.get(key)) {
                    System.out.print( " : " + p.x + ", " + p.y);
                }
                System.out.println();
            }
            System.out.println();
        }
        
        
        
        class littleSolution {
            public int[] X;
            public int[] Y;
            public char[] Tch;
            public HashMap<String, Integer> maxScores;
            public int maxY;
            public int minY;
            public HashMap<Integer, ArrayList<Integer>> hashCoord;
            public HashMap<Integer, ArrayList<point>> hashInclL;
            public HashMap<Integer, ArrayList<point>> hashInclR;
            public int depth = 0;
            public littleSolution(
                    int[] X, int[] Y, char[] Tch, HashMap<String, Integer> maxScores, int minY, int maxY
                    , HashMap<Integer, ArrayList<Integer>> hashCoord
                    , HashMap<Integer, ArrayList<point>> hashInclL
                    , HashMap<Integer, ArrayList<point>> hashInclR) {
                this.X = X;
                this.Y = Y;
                this.Tch = Tch;
                this.maxScores = maxScores;
                this.minY = minY;
                this.maxY = maxY;
                this.hashCoord = hashCoord;
                this.hashInclL = hashInclL;
                this.hashInclR = hashInclR;
            }//END OF FUNCTION
            
            public void printDebug(String msg) {
                if(!isLocal) return;
                for(int di=0; di<depth; di++)
                    System.out.print("####" + di);
                System.out.print(msg);
                System.out.println();
            }//END OF FUNCTION
            
            
            //TODO Y가 높은 점부터 max score 를 다 계산해 놓은 뒤, 뒤에서 점수 계산 시작.
            public int getMaxScore(int preX, int preY, int curX, int curY) {
                depth++;
                //up-left, fixed : x1-n, y1+n = x3, y3 : x1+y1 = x3+y3
                //fixed up-right : x1+n, y1+n = x2, y2 : x1-y1 = x2-y2
                int maxScore = 0;
                
                for(int i=0; i<X.length && curY<maxY; i++) {
                    int tmpScore = 0;
                    
                    if(Y[i] < minY) {
                        printDebug(" : posY is under minY : " + Y[i] + " is under " + minY);
                        break;
                    }
                    if(curY > Y[i]) {
                        printDebug(" : curY is over posY : " + curY + " is over " + Y[i]);
                        break;
                    }
                    point met = getMeetPoint(preX, preY, curX, curY, X[i], Y[i]);
                    if(met == null) {
                        printDebug(" : i : " + i + " : (preX, preY) (" + preX + ", " + preY + ") : (curX, curY) : (" + curX + ", " + curY + ") : (posX, posY) : (" + X[i] + ", " + Y[i] + ") : start : (metX, metY) : (null) : (minY, maxY) : (" + minY + ", " + maxY + ")");
                    } else {
                        printDebug(" : i : " + i + " : (preX, preY) (" + preX + ", " + preY + ") : (curX, curY) : (" + curX + ", " + curY + ") : (posX, posY) : (" + X[i] + ", " + Y[i] + ") : start : (metX, metY) : (" + met.x + ", " + met.y + ") : (minY, maxY) : (" + minY + ", " + maxY + ")");
                    }
                    
                    if(Tch[i] != 'X' && curY < Y[i] && met !=  null && isPromise(curX, curY, met.x, met.y, X[i], Y[i])) {
                        
                        printDebug(" : promise point.");
                        
                        point lan = getLandingPoint(preX, preY, curX, curY, X[i], Y[i]);
                        if(lan != null) {
                            printDebug(" : landing point : " + lan.x + ", " + lan.y);
                            tmpScore = (Tch[i] == 'p')? 1:10;
                            String maxScoreKey = "" + lan.x + "," + lan.y;
                            Integer nextMaxScore = maxScores.get(maxScoreKey);
                            if(nextMaxScore == null) {
                                printDebug(" : landing point score calc..");
                                nextMaxScore = getMaxScore(curX, curY, lan.x, lan.y);
                                maxScores.put(maxScoreKey, nextMaxScore);
//                                System.out.println(" : landing point score calc.");
                            } else {
                                printDebug(" : landing point score already calc.");
//                                System.out.println(" : landing point score already calc.");
                            }
                            
                            printDebug(" : landing point score : " + nextMaxScore);
                            tmpScore += nextMaxScore;
                        }
                    }
                    
                    if(tmpScore > maxScore) {
                        maxScore = tmpScore;
                    }
                    
                    printDebug(" : i : " + i + " : (preX, preY) (" + preX + ", " + preY + ") : (curX, curY) : (" + curX + ", " + curY + ") : (posX, posY) : (" + X[i] + ", " + Y[i] + ") : end : maxScore : " + maxScore);
                }//END OF FOR-LOOP
                depth--;
                
                return maxScore;
            }//END OF FUNCTION
            
            /** true  : if there is no middle point between (curX, curY) and (posX, posY) */
            public boolean isPromise(int curX, int curY, Integer metX, Integer metY, int posX, int posY) {
                int incl = 0;
                ArrayList<point> points = null;
                int startX, endX;
                int startY, endY;
                
                if(metX != null && metY != null && curY < metY && metY < posY) {
                    return isPromise(curX, curY, null, null, metX, metY) & isPromise(metX, metY, null, null, posX, posY);
                }
                
                //left
                if(curX > posX) {
                    incl = curX+curY;
                    points = hashInclL.get(Integer.valueOf(incl));
                    startX = posX;
                    startY = curY;
                    endX = curX;
                    endY = posY;
                }
                //right
                else {
                    incl = curX-curY;
                    points = hashInclR.get(Integer.valueOf(incl));
                    startX = curX;
                    startY = curY;
                    endX = posX;
                    endY = posY;
                }
                
                if(points != null) {
                    for(point p : points) {
                        if(startX < p.x && p.x < endX && startY < p.y && p.y < endY) {
                            return false;
                        }
                    }
                }
                
                return true;
            }//END OF FUNCTION
            
            public boolean isDirectionLeft(int preX, int preY, int curX, int curY) {
                return (curX <= preX && curY >= preY);
            }//END OF FUNCTION
            
            /** get meet point between (curX, curY) and (posX, posY) */
            public point getMeetPoint(int preX, int preY, int curX, int curY, int posX, int posY) {
                point meetPoint = null;
                //default, up-left
                if(isDirectionLeft(preX, preY, curX, curY)) {
                    if( ((curX-curY)-(posX-posY))%2 == 0 ) {
                        int n = ((curX-curY)-(posX-posY))/2;
                        meetPoint = new point(curX-n, curY+n);
                    }
                }
                //up-right
                else {
                    if( ((posX+posY)-(curX+curY))%2 == 0 ) {
                        int n = ((posX+posY)-(curX+curY))/2;
                        meetPoint = new point(curX+n, curY+n);
                    }
                }
                return meetPoint;
            }//END OF FUNCTION
            
            public point getLandingPoint(int preX, int preY, int curX, int curY, int posX, int posY) {
                //up-left  : (x1-n, y1+n) = (x3-m, y3-m) : n = {(x1-y1)-(x3-y3)}/2
                //up-right : (x1+n, y1+n) = (x2+m, y2-m) : n = {(x2+y2)-(x1+y1)}/2
                point landingPoint = null;
                
                point met = getMeetPoint(preX, preY, curX, curY, posX, posY);
                if(met != null) {
                    int tlandingX;
                    int tlandingY;
                    if( (met.x == curX && met.y == curY) || (curY < met.y && met.y < posY && !(preX == curX && preY == curY)) ) { // or 뒤에 조건이 없으면, 시작점(fixed point) 에서 갈 수 없는 landing point 를 구하게됨  
                        if(isDirectionLeft(preX, preY, curX, curY)) {
                            tlandingX = posX+1;
                            tlandingY = posY+1;
                        } else {
                            tlandingX = posX-1;
                            tlandingY = posY+1;
                        }
                        
                        if(!isOccupied(tlandingX, tlandingY)) {
                            landingPoint = new point(tlandingX, tlandingY);
                        }
                    } else if(met.x == posX && met.y == posY) {
                        if(isDirectionLeft(preX, preY, curX, curY)) {
                            tlandingX = posX-1;
                            tlandingY = posY+1;
                        } else {
                            tlandingX = posX+1;
                            tlandingY = posY+1;
                        }
                        
                        if(!isOccupied(tlandingX, tlandingY)) {
                            landingPoint = new point(tlandingX, tlandingY);
                        }
                    }
                }
                
                
                
//                if(isDirectionLeft(preX, preY, curX, curY)) {
//                    point met = getMeetPoint(preX, preY, curX, curY, posX, posY);
//                    if(met != null) {
//                        if( (met.x == curX && met.y == curY) || (curY < met.y && met.y < posY && !(preX == curX && preY == curY)) ) {
//                            if(!isOccupied(posX+1, posY+1)) {
//                                landingPoint = new point(posX+1, posY+1);
//                            }
//                        } else if(met.x == posX && met.y == posY) {
//                            if(!isOccupied(posX-1, posY+1)) {
//                                landingPoint = new point(posX-1, posY+1);
//                            }
//                        }
//                    }
//                }
//                else {
//                    point met = getMeetPoint(preX, preY, curX, curY, posX, posY);
//                    if(met != null ) {
//                        if( (met.x == curX && met.y == curY) || (curY < met.y && met.y < posY) ) {
//                            if(!isOccupied(posX-1, posX+1)) {
//                                landingPoint = new point(posX-1, posY+1);
//                            }
//                        } else if(met.x == posX && met.y == posY) {
//                            if(!isOccupied(posX+1, posY+1)) {
//                                landingPoint = new point(posX+1, posY+1);
//                            }
//                        }
//                    }
//                }
                return landingPoint;
            }//END OF FUNCTION
            
            public boolean isOccupied(int posX, int posY) {
                ArrayList<Integer> ys = hashCoord.get(Integer.valueOf(posX));
                if(ys != null) {
                    for(Integer y : ys) {
                        if(y == posY) {
                            return true;
                        }
                    }
                }
                return false;
            }//END OF FUNCTION
            
        }//END OF INNER-CLASS
        
        
        littleSolution ls = new littleSolution(X, Y, Tch, maxScores, minY, maxY, hashCoord, hashInclL, hashInclR);
        
        for(int i=0; i<X.length; i++) {
            if(isLocal) System.out.println(i + " ####");
            if(Tch[i] != 'X') {
                point leftPoint = new point(X[i]-1, Y[i]+1);
                point preLeftPoint = new point(X[i]+1, Y[i]-1);
                point rightPoint = new point(X[i]+1, Y[i]+1);
                point preRightPoint = new point(X[i]-1, Y[i]+1);
                String leftKey = "" + leftPoint.x + "," + leftPoint.y;
                String rightKey = "" + rightPoint.x + "," + rightPoint.y;
                if(maxScores.get(leftKey) == null) {
                    ls.minY = leftPoint.y-1;
                    maxScores.put(leftKey, Integer.valueOf(ls.getMaxScore(preLeftPoint.x, preLeftPoint.y, leftPoint.x, leftPoint.y)));
                }
                if(maxScores.get(rightKey) == null) {
                    ls.minY = rightPoint.y-1;
                    maxScores.put(rightKey, Integer.valueOf(ls.getMaxScore(preRightPoint.x, preRightPoint.y, rightPoint.x, rightPoint.y)));
                }
            }
        }
        
        ls.minY = minY;
        ls.maxY = maxY;
        
        if(isLocal) {
            System.out.println("CALC MAX SCORES : ");
            Iterator iter = maxScores.keySet().iterator();
            while(iter.hasNext()) {
                String key = (String)iter.next();
                System.out.println(key + " : " + maxScores.get(key));
            }
        }
        
        maxScore = ls.getMaxScore(curX, curY, curX, curY);
        
        if(isLocal) {
            System.out.flush();
            System.err.println(TEST_CASE_NUMBER + "th TEST CASE, EXPECTED RESULT : " + expectResult + " : RESULT : " + maxScore + " : " + (expectResult == maxScore));
            System.err.flush();
        }
        
        return maxScore;
    }//END OF FUNCTION
}//END OF CLASS
