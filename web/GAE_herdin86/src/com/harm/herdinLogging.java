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
	private String className = null;
	public herdinLogging() {}
	public herdinLogging(Object object) {
		this.className = object.getClass().getSimpleName();
	}
	
	public void ENTER() {
		System.out.println(
				"#herdin : " +
				this.className + " : " +
				Thread.currentThread().getStackTrace()[2].getMethodName() + " : ENTER"
		);
	}//END OF ENTER()
	
	public void LEAVE() {
		System.out.println(
				"#herdin : " +
				this.className + " : " +
				Thread.currentThread().getStackTrace()[2].getMethodName() + " : LEAVE"
		);		
	}//END OF LEAVE()
	
}//END OF CLASS
