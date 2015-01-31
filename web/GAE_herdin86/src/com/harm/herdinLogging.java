package com.harm;

public class herdinLogging {
//	private static herdinLogging hLog;
//	private herdinLogging() {}
//	
//	public static herdinLogging getInstance() {
//		
//		if(hLog == null) {
//			hLog = new herdinLogging();
//		}
//		
//		return hLog;
//	}//END OF getInstance()

	
	
	public void ENTER(Object object) {
		System.out.println(
				"#herdin : ENTER : " +
				object.getClass().getSimpleName() + " : " +
				Thread.currentThread().getStackTrace()[2].getMethodName()
		);
	}//END OF ENTER()
	
	public void LEAVE(Object object) {
		System.out.println(
				"#herdin : LEAVE : " +
				object.getClass().getSimpleName() + " : " +
				Thread.currentThread().getStackTrace()[2].getMethodName()
		);		
	}//END OF LEAVE()
	
}//END OF CLASS
