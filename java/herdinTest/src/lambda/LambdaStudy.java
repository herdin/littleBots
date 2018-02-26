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
		 * �������� ���� �Լ��� �ϳ��� �ִ� �������̽� -> �Լ��� �������̽�
		 * @FunctionalInterface ������̼��� �ٿ������ν� �Լ��� �������̽����� ����� �� �ִ�.
		 */
		Runnable task = () -> { for(int i=0; i<1000; i++) System.out.println("do work"); };
		
		/*
		 * Template ���� �Ķ���Ͱ� String �� ���� �߷��� �� �����Ƿ� ���� �����ϴ�.
		 * ��ȯ Ÿ�Ե� ��������.
		 */
		Comparator<String> comp = (first, second) -> first.length() - second.length();
		
		/**
		 * Comparator �� ���� ǥ������ ���� �Ẹ�Ҵ�.
		 */
		String[] arr = new String[3];
		arr[0] = "123";
		arr[1] = "12";
		arr[2] = "1";
		System.out.println("���� ��");
		for(String str : arr) System.out.println(str);
		Arrays.sort(arr, (first, second) -> first.length() - second.length());
		System.out.println("���� ��");
		for(String str : arr) System.out.println(str);
		
		/**
		 *  removeIf �Լ��� �Ķ���ʹ� Predicate �������̽��� ����ü�̰�,
		 *  Predicate �������̽��� �Լ��� �������̽��̴�.
		 *  �������� ���� �Լ��� test �ϳ��� ���� ����. true �� ��� �����ȴ�.
		 *  
		 */
		
		ArrayList<String> arrayList = new ArrayList<>();
		for(int i=0; i<10; i++) {
			if(2 < i && i < 6)
				arrayList.add(String.valueOf(i));
			else
				arrayList.add(null);
		}
		System.out.println("���� ��");
		for(String str : arrayList) System.out.println(str);
		arrayList.removeIf(e -> e == null);
		System.out.println("���� ��");
		for(String str : arrayList) System.out.println(str);
		
		
		
		
		
//		LambdaStudy l1 = new LambdaStudy(1);
//		LambdaStudy l2 = new LambdaStudy(5);
//		l1.accessTest(l2);
		
	}//END OF FUNCTION
	
	public void accessTest(LambdaStudy otherObject) {
		System.out.println(otherObject.privateInt);
	}//END OF FUNCTION
}//END OF CLASS
