package lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class LambdaStudy {
	private int privateInt = -1;
	public LambdaStudy(int privateInt) {
		this.privateInt = privateInt;
	}//END OF FUNCTION
	
	public static void main(String[] args) {
		/*
		 * 구현되지 않은 함수가 하나만 있는 인터페이스 -> 함수형 인터페이스
		 * @FunctionalInterface 어노테이션을 붙여줌으로써 함수형 인터페이스임을 명시할 수 있다.
		 */
		Runnable task = () -> { for(int i=0; i<1000; i++) System.out.println("do work"); };
		
		/*
		 * Template 에서 파라미터가 String 인 것을 추론할 수 있으므로 생략 가능하다.
		 * 반환 타입도 마찬가지.
		 */
		Comparator<String> comp = (first, second) -> first.length() - second.length();
		
		/**
		 * Comparator 의 람다 표현식을 직접 써보았다.
		 */
		String[] arr = new String[3];
		arr[0] = "123";
		arr[1] = "12";
		arr[2] = "1";
		System.out.println("정렬 전");
		for(String str : arr) System.out.println(str);
		Arrays.sort(arr, (first, second) -> first.length() - second.length());
		System.out.println("정렬 후");
		for(String str : arr) System.out.println(str);
		
		/**
		 *  removeIf 함수의 파라미터는 Predicate 인터페이스의 구현체이고,
		 *  Predicate 인터페이스는 함수형 인터페이스이다.
		 *  구현되지 않은 함수로 test 하나를 갖고 있음. true 일 경우 삭제된다.
		 *  
		 */
		
		ArrayList<String> arrayList = new ArrayList<>();
		for(int i=0; i<10; i++) {
			if(2 < i && i < 6)
				arrayList.add(String.valueOf(i));
			else
				arrayList.add(null);
		}
		System.out.println("삭제 전");
		for(String str : arrayList) System.out.println(str);
		arrayList.removeIf(e -> e == null);
		System.out.println("삭제 후");
		for(String str : arrayList) System.out.println(str);
		
		
		
		
		
//		LambdaStudy l1 = new LambdaStudy(1);
//		LambdaStudy l2 = new LambdaStudy(5);
//		l1.accessTest(l2);
		
	}//END OF FUNCTION
	
	public void accessTest(LambdaStudy otherObject) {
		System.out.println(otherObject.privateInt);
	}//END OF FUNCTION
}//END OF CLASS
