package harm;

import java.awt.AWTException;
import java.awt.Robot;
import java.io.BufferedReader;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class TESTMAIN {
	
	public String b = "java";
	public String c = new String("java");
	
	@Override
	public String toString() {
		return super.toString();
	}
	
	
	
	public static void main(String[] args) throws Exception{
		
		////
		String a = "java";
		String b = new String("java");
		
		TESTMAIN t = new TESTMAIN();
		String d = t.b;
		
		if(a==d) {
			System.out.println("??");
		} else {
			System.out.println("!!");
		}
		
		System.out.println(t.toString());
		
/* java wrapping class test
        Integer wi = new Integer(3);
        wi = 4;
        System.out.println(wi);
        int si = wi;
        
        System.out.println(si);
        si++;
        System.out.println("" + wi + "/" + si);
        TESTMAIN tm = new TESTMAIN();
        tm.javaIntegerClassParamTest(wi, si);
        System.out.println("" + wi + "/" + si);
        
        Boolean wb = new Boolean(true);
        boolean sb = true;
        System.out.println("" + wb + "/" + sb);
        tm.javaBooleanClassParamTest(wb, sb);
        System.out.println("" + wb + "/" + sb);
*/
/* function name test
		TESTMAIN main = new TESTMAIN();
		System.out.println(main.getClass());
		main.getFucntionName();
*/
		
		
/* for loop index test
		int i2 = 0;
		int i3 = 0;
		//9 times
		for(int i = 0; i<10; i++, i2++) {
			i3++;
			System.out.println("========lab : " + i);
			System.out.println("i2 : " + i2);
			System.out.println("i3 : " + i3);
		}
		System.out.println("result i2 : " + i2);
		System.out.println("result i3 : " + i3);

*/
/* Integer.parseInt test
		System.out.println(Integer.parseInt("haha"));
		*/
/* hashtable test
		Hashtable<String, Hashtable> htable = new Hashtable<String, Hashtable>();
		Hashtable<String, String> ht1 = new Hashtable<>(); ht1.put("id", "t01"); ht1.put("pw", "1234");
		Hashtable<String, String> ht2 = new Hashtable<>(); ht2.put("id", "t02"); ht2.put("pw", "4321");
		htable.put("001", ht1);
		htable.put("002", ht2);
		Hashtable<String, String> temp = null;
		temp = htable.get("001");
		temp.put("id", "t03");
		System.out.println(htable.get("001").get("id"));
		
*/
		
/* java param test
		TESTMAIN main = new TESTMAIN();
		CClass c = new CClass();
		c.cname = "new name!";
		int cn = 5;
		System.out.println(c.cname + "/" + cn);
		main.javaParamTest(c, cn);
		System.out.println(c.cname + "/" + cn);
*/
		
/* system tray test
		System.out.println("tray test start");
		String iconPath = "heartBulb.png";
		String trayTitle = "Java Tray";
		if(SystemTray.isSupported()) {
			final SystemTray systemTray = SystemTray.getSystemTray();
			final TrayIcon trayIcon = new TrayIcon(new ImageIcon(iconPath, "omt").getImage(), trayTitle);
			trayIcon.setImageAutoSize(true);
			ActionListener al = new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					System.out.println(e.getActionCommand());
				}
			};
			trayIcon.addActionListener(al);
			MouseAdapter mouseAdapter = new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					systemTray.remove(trayIcon);
				}
			};
			trayIcon.addMouseListener(mouseAdapter);
			try {
				systemTray.add(trayIcon);
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			for(int i=0; i<100; i++) {
				System.out.println(i);
			}
		}//END OF IF ISSUPPORT
		
		System.out.println("tray test end");
*/
		
/* java substring test
		String str = "            2014-09-13 10:33:56";
		str = str.trim();

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		Date date = df.parse(str);
		cal.setTime(date);
		System.out.println(date.compareTo(new Date()));		//-1
		System.out.println((new Date().compareTo(date)));	//1
		System.out.println(cal.get(Calendar.MONTH));
*/
		
/* get function name test
        StackTraceElement[] steArray = Thread.currentThread().getStackTrace();
        TESTMAIN tm = new TESTMAIN();
        tm.functionCheck();
        for(int i=0; i<steArray.length; i++) {
        	System.out.println(steArray[i].getMethodName());
        }
*/
		
/* query beautify test
		String queryString = "SELECT \nAAAA.DEVICE_CLASS_CD,\nAAAA.COMN_DETAIL_CD_NM,\nCOUNT(AAAA.DEVICE_ID) AS NB_DEVIC,\nSUM(AAAA.DEVICE_FAIL_CD) AS NB_DEVICE_FAIL\nFROM\nTBADM120 D\nWHERE 1=1";
		String queryString2 = "SELECT \nA.REGION_ID,\nA.EVENT_OCCUR_DATE,\nA.DEVICE_ID,\nA.EVENT_CD,\nA.AGENCY_ID,\nA.DEVICE_CLASS_CD,\nA.STATION_ID,\nA.START_END_TYPE_CD,\nA.LAST_STAT_REGIST_DATE,\nB.EVENT_NM,\nB.EVENT_PRIOR_LVEL,\n(SELECT\nDETAILCODE.COMN_DETAIL_CD_NM\nFROM\nTBAED141 DETAILCODE\nWHERE 1=1\nAND DETAILCODE.COMN_DETAIL_CD=A.DEVICE_CLASS_CD\nAND DETAILCODE.COMN_CD='AF002'\n) AS DEVICE_CLASS_NM ,\n(SELECT\nDETAILCODE.COMN_DETAIL_CD_NM\nFROM\nTBAED141 DETAILCODE\nWHERE 1=1\nAND DETAILCODE.COMN_DETAIL_CD=A.START_END_TYPE_CD\nAND DETAILCODE.COMN_CD='AF011'\n) AS EVENT_STATUS_NM ,\n(SELECT\nDETAILCODE.COMN_DETAIL_CD_NM\nFROM\nTBAED141 DETAILCODE\nWHERE 1=1\nAND DETAILCODE.COMN_DETAIL_CD=B.EVENT_PRIOR_LVEL\nAND DETAILCODE.COMN_CD='AF004'\n) AS EVENT_PRIOR_LVEL_NM ,\nCASE WHEN A.DEVICE_CLASS_CD = '92' THEN (SELECT\nTrunkStation.STATION_NM\nFROM\nTBAFM102 TrunkStation\nWHERE 1=1\nAND TrunkStation.REGION_ID=A.REGION_ID\nAND TrunkStation.DEVICE_ID=A.DEVICE_ID\n) WHEN A.DEVICE_CLASS_CD = '93' THEN (SELECT \nAgency.AGENCY_NM\nFROM\nTBAHM110 Agency\nWHERE 1=1\nAND Agency.REGION_ID=A.REGION_ID\nAND Agency.AGENCY_ID=A.AGENCY_ID\n) WHEN A.DEVICE_CLASS_CD = '91' THEN (SELECT\nZCS.DEPOT_NM\nFROM\nTBAHM140 ZCS\nWHERE 1=1\nAND ZCS.REGION_ID=A.REGION_ID\nAND ZCS.DEVICE_ID=A.DEVICE_ID\n) END  AS INS_LOC_NM,\nSUBSTR(CASE WHEN A.REGION_ID = '92' THEN (SELECT\nTrunkStation.STATION_NM\nFROM\nTBAFM102 TrunkStation\nWHERE 1=1\nAND TrunkStation.REGION_ID=A.REGION_ID\nAND TrunkStation.DEVICE_ID=A.DEVICE_ID\n) WHEN A.REGION_ID = '93' THEN (SELECT\nAgency.AGENCY_NM\nFROM\nTBAHM110 Agency\nWHERE 1=1\nAND Agency.REGION_ID=A.REGION_ID\nAND Agency.DEVICE_ID=A.DEVICE_ID\n) WHEN A.REGION_ID = '91' THEN (SELECT\nZCS.DEPOT_NM\nFROM\nTBAHM140 ZCS\nWHERE 1=1\nAND ZCS.REGION_ID=A.REGION_ID\nAND ZCS.DEVICE_ID=A.DEVICE_ID\n) END , 0, 20) AS INS_LOC_SHORT_NM\nFROM\nTBADD163 A\nJOIN\nTBADC510 B\nON A.DEVICE_CLASS_CD=B.DEVICE_CLASS_CD\nAND A.EVENT_CD=B.EVENT_CD\nAND B.EVENT_PRIOR_LVEL=\'0\'";

		String input = queryString2;
		input = input.replaceAll("[(]", "(\n");
		Pattern p = Pattern.compile("[\\w_.()=><-]+");
		Matcher m = p.matcher(input);
		
		int depth_select = 0;
		int depth_from = 0;
		int depth_where = 0;
		int depth_as = 0;
		int depth_bucket = 0;
		
		StringBuffer output = new StringBuffer();
		
		while(m.find()) {
			System.out.println(m.group());
			String part = m.group();
			
			if(part.equals("SELECT")) {
				depth_select++;
			} else if(part.equals("FROM")) {
				depth_from++;
			} else if(part.equals("WHERE")) {
				depth_where++;
			}else if(part.equals("AS")) {
				depth_as++;
			} else if(part.contains("(")) {
				depth_bucket++;
			}
			
			if(depth_as != 0) {
				output.append(" ").append(part);
				depth_as++;
				if(depth_as == 3)
					depth_as = 0;
			} else {
				if(depth_select > depth_from) {
					for(int j=0; j<depth_select-1; j++) {
						output.append("\t");
					}
				}
				output.append(part);
				output.append("\n");		
			}
			
		}//END OF WHILE LOOP
		
		System.out.println("==========================");
		System.out.println(output.toString());
		System.out.println("==========================");

*/		
		
/*	java regular expression test
		String str = "   <DT><A HREF=\"http://www.lgcns.com\" ADD_DATE=\"1399877991\">LG CNS</A>";
	    Pattern pUrl = Pattern.compile("http://[\\w.]+");  
	    Pattern mark = Pattern.compile(">[\\w ]+<");
	    Matcher m = pUrl.matcher(str);
	    Matcher m2 = mark.matcher(str);
	    if(m2.find()){
	    	System.out.println(m2.group());
	    }
 */
		
/*	//13192 // 39
	//cns hackerton algorithm track sample 2 // sample 1 is trash
		int input = 13192;
		int result = -1;
		Integer i = new Integer(input);
		ArrayList<Integer> intList = new TESTMAIN().getPrimeNumberList(i); 
		//System.out.println(new TESTMAIN().getPrimeNumberList(i));
		for(Integer ic : intList) {
			if(input%ic == 0) {
				result = ic;
			}
		}
		System.out.println(result);

/* extends test
		PClass p = new PClass("p");
		CClass c = new CClass("c");
		//p.printName();
		//c.printPname();
		
		PClass p2 = c;
		p2.printName();
		((CClass)p2).printPname();
		
		
		
/* append test
		ArrayList<String> conditions = new ArrayList<String>();
		conditions.add("90");
		conditions.add("91");
		conditions.add("92");
		conditions.add("93");
		
		StringBuffer InCondition = new StringBuffer();

        InCondition.append("DEVICE_CLASS_CD").append(" ").append("IN").append(" (");
        for(String s : conditions) {
            InCondition.append("'").append(s).append("'").append(",");
        }
        InCondition.setCharAt(InCondition.length()-1, ')');
        
		System.out.println(InCondition);
*/
/* parseInt toString test
		String sseq = "100";
		sseq = Integer.toString((Integer.parseInt(sseq) + 1));
		System.out.println(sseq);
*/
//simple calendar test3////////////////////////////////////////////////////////////////
//		String test = "20140303111213";
//		String closing_time = "20140322010001";
//		String current_time = "20140323010000";
//		System.out.println(closing_time + "\n" + current_time);
//		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
//		
//		Date clsDate = df.parse(closing_time);
//		Date curDate = df.parse(current_time);
//		
//		long diffSec = (curDate.getTime()-clsDate.getTime())/1000;
//
//		final int YEAR_CONVERT_CONSTANT = 60*60*24*30*12;
//		final int MONTH_CONVERT_CONSTANT = 60*60*24*30;
//		final int DAY_CONVERT_CONSTANT = 60*60*24;
//		System.out.println(diffSec/DAY_CONVERT_CONSTANT);
//		int diffY = (int)(diffSec/YEAR_CONVERT_CONSTANT);
//		int diffM = (int)(diffSec%YEAR_CONVERT_CONSTANT/MONTH_CONVERT_CONSTANT);
//		int diffD = (int)(diffSec/DAY_CONVERT_CONSTANT);
//		System.out.println("" + diffY + "/" + diffM + "/" + diffD);
//		
//		System.out.println(df.format(new Date()));
//////////////////////////////////////////////////////////////////////////////////////
/* simple calendar test3
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String amount = "006";
		Calendar cal = Calendar.getInstance();
		System.out.println( df.format(cal.getTime()) );
		cal.add(Calendar.HOUR_OF_DAY, -1*(Integer.parseInt(amount)));
		System.out.println( df.format(cal.getTime()) );
*/
/* simple calendar test2
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MMdd/HHmmss");
		String sEndDate   = "2014/0803/091233";
		String sOccurDate = "2014/0803/010203";
		Date dOccurDate   = dateFormat.parse(sOccurDate);
		Date dEndDate     = dateFormat.parse(sEndDate);

		long diff = dEndDate.getTime() - dOccurDate.getTime();
		
		System.out.println(
				((Long.toString(diff/(1000*60*60)).length() < 2) ?  "0" + diff/(1000*60*60) : diff/(1000*60*60)) + ":" +
				diff%(1000*60*60)/(1000*60) + ":" +
				diff%(1000*60*60)%(1000*60)/(1000));
		
		//19700101090000
*/
		
///* simple calendar test
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
//		String time = null;
//		Date dat1, dat2;
//		Calendar cal = Calendar.getInstance(); 
//		cal.setTime(new Date());
//		dat1 = cal.getTime();
//		//cal.add(Calendar.MINUTE, +31);
//		dat2 = cal.getTime();
//				
//		System.out.println("dat1 : " + dateFormat.format(dat1));
//		System.out.println("dat2 : " + dateFormat.format(dat2));
//		
//		System.out.println(dat1.before(dat2));
//		System.out.println(dat2.compareTo(dat1)); // -1 return means dat2 is before than dat1
		
//		cal.setTime((Date)dateFormat.parse(time));
//		dat1 = cal.getTime();
//		cal.add(Calendar.MINUTE, -20);
//		dat2 = cal.getTime();
//		
//		System.out.println("dat1 : " + dateFormat.format(dat1));
//		System.out.println("dat2 : " + dateFormat.format(dat2));
//		
//		System.out.println(dat1.compareTo(dat2)); // 1 return means dat1 is after than dat1
//		System.out.println(dat2.compareTo(dat1)); // -1 return means dat2 is before than dat1
//*/
		
/*//variable test
		TESTCLASS t1 = new TESTCLASS();
		
		t1.number = 1;
		TESTCLASS t2 = t1;
		t2.number = 3;
		
		System.out.println(t1.number);
		System.out.println(t2.number);
		
		TESTMAIN tm = new TESTMAIN();
		tm.testFunction(t1);
		
		System.out.println(t1.number);
		System.out.println(t2.number);
		System.out.println("=================================");
		tm.functionCheck();
		System.out.println("=================================");
		ArrayList<TESTCLASS> arr = new ArrayList<TESTCLASS>();
		for(int i=0; i<100; i++){
			arr.add(new TESTCLASS());
			arr.get(i).number = i;
		}
		for(int i=0; i<100; i++){
			System.out.print(arr.get(i).number + " ");
		}
		System.out.println();
		arr.clear();
		for(int i=0; i<100; i++){
			//System.out.print(arr.get(i).number + " ");
		}
		System.out.println("=================================");
		String str = "039";
		String str2 = String.valueOf((Integer.parseInt(str) + 1));
		int j = str.length()-str2.length();
		for(int i=0; i<j; i++){
			str2 = "0" + str2;
		}
		System.out.println(str2);
		System.out.println("=================================");
		System.out.println(tm.getClass().getName());
		//System.out.println(Integer.parseInt(str));
		System.out.println("=================================");
		System.out.println("20140804111213".substring(0, 8));
		System.out.println("=================================");
*/
		
	}//END OF MAIN
	
	public void getChromeDuplicatedBookmark() throws Exception {

		BufferedReader reader = new BufferedReader(new FileReader("bookmarks_14. 9. 24..html"));
		String line = null;
		int cnt = 0;
		ArrayList<String> urlList = new ArrayList<String>();
		ArrayList<String> markList = new ArrayList<String>();
		ArrayList<String> dupList = new ArrayList<String>();
		//http://blog.naver.com/ivory82?Redirect=Log&logNo=40202338785
	    Pattern pUrl = Pattern.compile("(http|https)://[%\\w./?=&_:-]+");  
	    Pattern pMark = Pattern.compile(">[\\wㄱ-힣 :-]+<");
	    
		while((line=reader.readLine()) != null) {
			//System.out.println(line);
			if(line.indexOf("A HREF") != -1) {
				Matcher mUrl = pUrl.matcher(line);
				Matcher mMark = pMark.matcher(line);
				
				if(mUrl.find() && mMark.find()) {
					String url = mUrl.group();
					String mark = mMark.group();
					mark.replace(">", "");
					mark.replace("<", "");
					//System.out.println(+ "\t\t" + mMark.group());
					boolean flag = false;
					
					for(String s : urlList) {
						if(s.equals(url)) {
							flag = true;
							break;
						}
					}
					if(!flag) {
						urlList.add(url);
						markList.add(mark);						
					} else {
						flag = false;
						for(String s : dupList) {
							if(s.equals(url)) {
								flag = true;
								break;
							}
						}
						if(!flag) {
							dupList.add(url);
						}
					}

				}
			}
			cnt++;
		}
		
		for(String s : dupList) {
			System.out.println(s);
		}
		System.out.println("total : " + cnt + "\t dup : " + dupList.size());
	}//END OF getChromeDuplicatedBookmark
	
	public String getFucntionName() {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e = stacktrace[1];
		String methodName = e.getMethodName();
		for(int i=0; i<stacktrace.length; i++) {
			System.out.println("stacktrace[" + i + "] : " + stacktrace[i].getMethodName());
		}
		System.out.println( "test 1: " + methodName.indexOf("getFucntion"));
		System.out.println( "test 2 : " + methodName.replaceAll("getFucntion", ""));
		
		
		return methodName;		
	}
	
	public ArrayList<Integer> getPrimeNumberList(Integer max) throws AWTException {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(int i = 1; i<max; i++) {
			boolean flag = true;
			for(int p = 1; p < i && flag; p++) {
				System.out.print(p + " ");
				if(i%p == 0) {
					if(i != 1 && p != 1 ) {
						flag = false;
					}
				}
			}
			if(flag) {
				result.add(new Integer(i));
				System.out.println("\"" + i + "\"");
				(new Robot()).delay(50);
			} else {
				System.out.println(i);
			}
			
		}
			
		return result;
	}
	
	public void javaBooleanClassParamTest(Object wb, boolean sb) {
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " start");
		wb = (!(Boolean)wb);
		sb = !sb;
		System.out.println("" + wb + "/" + sb);
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " end");
	}
	
	public void javaIntegerClassParamTest(Object wi, int si) {
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " start");
		wi = (Integer)wi + 1;
		++si;
		System.out.println("" + wi + "/" + si);
		System.out.println(Thread.currentThread().getStackTrace()[1].getMethodName() + " end");
	}
	
	public ArrayList<Integer> getPrimeNumberList2(Integer max) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		//not to do x/2 x/4 x/6
		//		 do x/2 N/A N/A
		
		return result;
	}
	
	public void javaParamTest(CClass c, int n) {
		c.cname = "after Test";
		n++;
	}
	

		
	public void functionCheck(){
		System.out.println("=================================");
		System.out.println("============functionCheck() start");
		System.out.println(this.getClass());
		System.out.println(this.getClass().toString().length());
		System.out.println(this.getClass().toString().substring	(
															this.getClass().toString().lastIndexOf(".")+1,
															this.getClass().toString().length()
																)
							);
		System.out.println("============functionCheck() end");
		System.out.println("=================================");
	}//END OF functionCheck()

}//END OF CLASS
