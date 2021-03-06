package com.harm.unmani;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class JangHelper extends JFrame{
	//no meaning
	private int lineCnt = 0;
	private ArrayList<String> empNumList = null;			
	private ArrayList<String> empNmList = null;
	private int[][] insaneChkList = null;
	private int[] insaneChkSum = null;

	public static boolean debug = true;
	public static String	CONFIG_FILE			= "insaneMonthlyCommentAdder_config_TEST.txt";
	public static String	DEFAULT_XML_FOLDER	= "D:\\data\\";
	public static String	DEFAULT_XML_NAME	= "MessageComment";
	public static String	FILE_EXTENSION		= ".xls";
	public static String	FROM_DATE			= "2015-04-01 00:00:00";
	public static String	TO_DATE				= "2015-06-31 23:59:59";
	
//	public JangHelper() {}
	public JangHelper(	int lineCnt,
						ArrayList<String> empNumList,
						ArrayList<String> empNmList,
						int[][] insaneChkList,
						int[] insaneChkSum) {
		super("JangHelper");
		this.lineCnt = lineCnt;
		this.empNumList = empNumList;
		this.empNmList = empNmList;
		this.insaneChkList = insaneChkList;
		this.insaneChkSum = insaneChkSum;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//END OF CONSTRUCTOR
	
	public void startSwing(){
		println("JangHelper swing start");
		
		if(	empNumList == null		||
			empNmList == null		||
			insaneChkList == null	||
			insaneChkSum == null	||
			lineCnt == 0	) {
			println("NEED INIT TABLE DATA.");
			this.setTitle("NEED INIT TABLE DATA.");
			this.setVisible(true);
			return;
		}
			
		String[] header = new String[lineCnt+2];
		header[0] = "";
		for(int i=1; i<lineCnt+1; i++) {
			header[i] = empNmList.get(i-1);
		}
		header[header.length-1] = "SUM";
		
		String[][] data = new String[lineCnt][];
		for(int i=0; i<lineCnt; i++) {
			data[i] = new String[lineCnt+2];
			data[i][0] = empNmList.get(i);
			for(int j=1; j<lineCnt+1; j++) {
				data[i][j] = insaneChkList[i][j-1] == 0 ? "X":"O";
			}
			data[i][data[0].length-1] = String.valueOf(insaneChkSum[i]);
		}
		
		DefaultTableModel dtm = new DefaultTableModel(data, header);
		JTable table = new JTable(dtm);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(15);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		for(int i=1; i<table.getColumnModel().getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(10);
		}
		table.getColumnModel().getColumn(table.getColumnCount()-1).setPreferredWidth(20);
		
		println("#" + table.getRowHeight() + "/" + table.getColumnModel().getColumn(3).getWidth());
		
		//Dimension d = new Dimension(722, 539);
		this.setSize(
				table.getColumnModel().getColumn(0).getPreferredWidth() +
				(table.getColumnModel().getColumn(1).getPreferredWidth()*(table.getColumnCount()-1)) +
				table.getColumnModel().getColumn(table.getColumnCount()-1).getPreferredWidth() + 5
				, (table.getRowHeight()*(table.getRowCount())) +
					table.getTableHeader().getPreferredSize().height + 45
				);
//		println("#"
//				+ table.getRowHeight() + "/"
//				+ table.getRowCount() + "/"
//				+ table.getTableHeader().getPreferredSize().height
//				);

		JScrollPane sp = new JScrollPane(table);
		this.add(sp);

//		addComponentListener(new ComponentListener() {
//			@Override
//			public void componentResized(ComponentEvent e) {
//				println(e.getComponent().getBounds().width + "/" +
//						e.getComponent().getBounds().height
//						);
//			}
//			@Override
//			public void componentHidden(ComponentEvent e) {}
//			@Override
//			public void componentMoved(ComponentEvent e) {}
//
//			@Override
//			public void componentShown(ComponentEvent e) {}
//		});
		this.setVisible(true);
		println("JangHelper swing end");
	}//END OF startSwing
	
	public static void print(String msg)	{ if(debug) System.out.print(msg);		}
	public static void println()			{ if(debug) System.out.println();		}
	public static void println(String msg)	{ if(debug) System.out.println(msg);	}
	public static void catchErrorPrintln(Exception e, String reason) {
		if(debug)
			System.err.println("catch " + e.getClass().getSimpleName() + " : " + reason);
	}
	
	public static void main(String[] args) {
		
		try {
			
			BufferedReader reader = null;
			String line = null;
			String defaultXlsFullPath = DEFAULT_XML_FOLDER + DEFAULT_XML_NAME;
			String fileExtension = FILE_EXTENSION;
	
			int lineCnt = 0;
			
			ArrayList<String> empNumList = new ArrayList<String>();			
			ArrayList<String> empNmList = new ArrayList<String>();
			int[][] insaneChkList = null;
			ArrayList<String> fileList = new ArrayList<String>();
//			ArrayList<String> contactList = null;
			HashSet<String> contactSet = null;
			int[] fileStatus = null;
			
			long appStartTime = System.currentTimeMillis();
			//read config file
			//74053 EmpName1
			//74054 EmpName2
			try {
				reader = new BufferedReader(
							new InputStreamReader(new FileInputStream(CONFIG_FILE), "UTF-8"));
				
			} catch (FileNotFoundException e) {
				catchErrorPrintln(e, "config file not found : " + e.getMessage());
				throw new Exception();
			}
			
			try {
				//ORGANIZE EMP NUM, NAME TO ARRAYS
				while((line=reader.readLine()) != null) {
					empNumList.add(line.substring(0, line.indexOf(" ")));
					empNmList.add(line.substring(line.indexOf(" ") + 1, line.length()));	
					lineCnt++;
				}
				reader.close();
			} catch (IOException e) {
				catchErrorPrintln(e, "readLine exception : " + e.getMessage());
				throw new Exception();
			}
			
			println("CONFIG FILE LOAD OK.");
			
			//INIT CHECK TABLE
			insaneChkList = new int[lineCnt][];
			//INIT FILE STATUS ARRAY
			fileStatus = new int[lineCnt];
			for(int i=0; i<lineCnt; i++) {
				insaneChkList[i] = new int[lineCnt];
				fileStatus[i] = -1;
				for(int j=0; j<lineCnt; j++) {
					insaneChkList[i][j] = 0;
				}
			}
			
			//init comment file name list
			//MessageComment.xls
			//MessageComment (1).xls
			//MessageComment (2).xls ...
			fileList.add(defaultXlsFullPath + fileExtension);
			for(int i=1; i<lineCnt; i++) {
				fileList.add(defaultXlsFullPath + "(" + i + ")" + fileExtension); 
			}
			
//        <td class="blog_reply_info" align="right" id='td_1' >
//            임베디드솔루션팀
//        </td>
//        <td class="blog_reply_info" align="right" id='td_1' >
//            최창완 사원
//        </td>
//        <td class="blog_reply_info" align="right" id='td_1' >
//            2014-09-13 10:33:56 
//        </td>
			//collect contact list
			Calendar cal = Calendar.getInstance();
			int fileIndex;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date toDate = dateFormat.parse(TO_DATE);
			Date fromDate = dateFormat.parse(FROM_DATE);
			cal.setTime(fromDate);
			int fromMonth = cal.get(Calendar.MONTH) + 1;
			cal.setTime(toDate);
			int toMonth = cal.get(Calendar.MONTH) + 1;
			
			//START OF WHILE-LOOP : ALL FILE
			for(fileIndex = 0; fileIndex<fileList.size(); fileIndex++) {
//				println(fileList.get(fileIndex));
				
				try {
					reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileList.get(fileIndex)), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					catchErrorPrintln(e, "input stream reader exception : " + e.getMessage());
					throw new Exception();
				} catch (FileNotFoundException e) {
					catchErrorPrintln(e, "file input stream exception : " + e.getMessage());
					throw new Exception();
				}
				
//				contactList = new ArrayList<String>();
				contactSet = new HashSet<String>();
				
				try {
					
					//boolean fileStatusFlag = false;
					//START OF WHILE-LOOP : ONE FILE READ
					while((line=reader.readLine()) != null) {
														//println(line);
						if(line.indexOf("<td class=\"blog_reply_info") != -1) {
							
							reader.readLine();			// team name
							reader.readLine();			// </td>
							reader.readLine();			// <td class ...>
							line = reader.readLine();	// emp name => 최창완 사원
							line = line.trim();
							String contacEmpName = line.substring(0, line.indexOf(" "));
							
							reader.readLine();			// </td>
							reader.readLine();			// <td class ...>
							line = reader.readLine();	// time
							line = line.trim();
							
							Date writtenDate = dateFormat.parse(line);
							cal.setTime(writtenDate);
							int writtenMonth = cal.get(Calendar.MONTH) + 1;
							
//							cal.setTime(fromDate);
//							cal.add(Calendar.MONTH, -1);
//							int measureMonth = cal.get(Calendar.MONTH) + 1;
//							cal.add(Calendar.MONTH, -1);
//							int exceptLimitMonth = cal.get(Calendar.MONTH) + 1;
							
							/**
							 * 엑셀파일에 댓글은 최신 순서로 적힌다.
							 * 따라서,
							 * 1. 댓글의 입력날짜가 댓글계산시작날짜(fromDate) 보다 이전이라면, 정상파일을 모두 읽은 것으로 간주.
							 *    -> 다음파일을 읽는다.
							 * 2. 이전이 아니고, 댓글계산시작/종료날짜 사이에 있다면, contactList 에 해당 댓글입력자를 추가.
							 */
							if(writtenDate.before(fromDate)) {
								fileStatus[fileIndex] = 0;
								break;
							} else if(fromMonth <= writtenMonth && writtenMonth <= toMonth) {
								//ArrayList 말고 HashSet같은걸 쓰는게 좋을것같다. > 수정완료
								//두번 댓글남겨도 어차피 1번 카운트니까..
								//contactList.add(contacEmpName);
								contactSet.add(contacEmpName);
							} 
							
						}//END OF IF
					}//END OF WHILE-LOOP : ONE FILE READ
					
				} catch (IOException e) {
					catchErrorPrintln(e, "read line exception : " + e.getMessage());
					throw new Exception();
				} catch (ParseException e) {
					catchErrorPrintln(e, "simple date formatter parse exception : " + e.getMessage());
					throw new Exception();
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						catchErrorPrintln(e, "reader close exception : " + e.getMessage());
						throw new Exception();
					}
				}//END OF TRY/CATCH/FINALLY
				
				/**
				 * insaneChkList[i][j]
				 * i : 팀원의 인덱스
				 * j : i 팀원이 j 팀원에게 댓글 입력 유무 1/0
				 */
				//START OF FOR-LOOP : CHECK CONTACT LIST
				Iterator<String> itor = contactSet.iterator();
				while(itor.hasNext()) {
					String contacNm = itor.next();
					int contacIndex = empNmList.indexOf(contacNm);
					if(contacIndex != -1)
						insaneChkList[contacIndex][fileIndex] = 1;
				}
				//START OF FOR-LOOP : CHECK CONTACT LIST
				/*for(int i=0; i<contactList.size(); i++) {	//연락한놈들만큼 반복
					String contacNm = contactList.get(i);
					for(int j=0; j<empNmList.size(); j++) {		//config파일에 적혀있는 팀원만큼 반복
						if(insaneChkList[j][fileIndex] == 1) {
							continue;
						} else if(empNmList.get(j).equals(contacNm)) {		//config파일에 적혀있는 팀원이면,
							insaneChkList[j][fileIndex] = 1;		//[팀원순서][파일주인순서] 행렬에 마킹함.
							break;
						}
					}
				}*///END OF FOR-LOOP : CHECK CONTACT LIST
			
			}//END OF FOR-LOOP : ALL FILE

			println("FILE STATUSES : NORMAL(0) ABNORMAL(-1)");
			for(int i=0; i<lineCnt; i++) {
				println(empNmList.get(i) + "\t FILE STATUS : " + fileStatus[i]);
			}
			
			//sum contact check
			int[] insaneChkSum = new int[lineCnt];
			for(int i=0; i<lineCnt; i++) {
				for(int j=0; j<lineCnt; j++) {
					print(insaneChkList[i][j] + " " );
					insaneChkSum[i] = insaneChkSum[i] + insaneChkList[i][j];
				}
				println();
			}
			
			for(int i=0; i<lineCnt; i++) {
				println(empNmList.get(i) + " : " + insaneChkSum[i]);
			}
			
			//show result via swing
			new JangHelper(lineCnt, empNumList, empNmList, insaneChkList, insaneChkSum).startSwing();
			println("NORMAL EXIT.");
			long appEndTime = System.currentTimeMillis();
			println("APP PROCESSING TIME : " + String.format("%.2f", (appEndTime-appStartTime)/1000f ) );
		 } catch (Exception e) {
			 catchErrorPrintln(e, e.getMessage());
		 }//END OF TRY/CATCH
		
			
	}//END OF main()
	
}//END OF CLASS
