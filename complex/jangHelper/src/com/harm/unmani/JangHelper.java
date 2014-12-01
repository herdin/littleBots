package com.harm.unmani;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.xml.bind.DataBindingException;

@SuppressWarnings("serial")
public class JangHelper extends JFrame{
	private ArrayList<String> empNumList = null;			
	private ArrayList<String> empNmList = null;
	private int[][] insaneChkList = null;
	private int[] insaneChkSum = null;
	private int lineCnt = 0;
	
	public JangHelper() {}
	public JangHelper(	int lineCnt,
						ArrayList<String> empNumList,
						ArrayList<String> empNmList,
						int[][] insaneChkList,
						int[] insaneChkSum) {
		super("JangHelper");
		this.lineCnt = lineCnt;
		this.empNmList = empNmList;
		this.empNumList = empNumList;
		this.insaneChkList = insaneChkList;
		this.insaneChkSum = insaneChkSum;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}//END OF CONSTRUCTOR
	
	public void startSwing(){
		println("JangHelper start");
		if(	empNumList == null		||
			empNmList == null		||
			insaneChkList == null	||
			insaneChkSum == null	||
			lineCnt == 0	) {
			println("need init table data");
			this.setTitle("need init table data");
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
		
	}//END OF startSwing
	
	public static boolean debug = true;
	public static void print(String msg) {
		if(debug)
			System.out.print(msg);
	}
	public static void println(String msg) {
		if(debug)
			System.out.println(msg);
	}
	
	
	
	public static void main(String[] args) {
		
		try {
			BufferedReader reader = null;
			String line = null;
			String defaultXlsName = "MessageComment";
			String fileExtension = ".xls";
	
			int lineCnt = 0;
			
			ArrayList<String> empNumList = new ArrayList<String>();			
			ArrayList<String> empNmList = new ArrayList<String>();
			int[][] insaneChkList = null;
			ArrayList<String> fileList = new ArrayList<String>();
			ArrayList<String> contacList = null;
			
			//read config file
			//74053 EmpName1
			//74054 EmpName2
			try {
				reader = new BufferedReader(new FileReader("jangHelper_config.txt"));
				
			} catch (FileNotFoundException e) {
				println("catch FileNotFoundException[1] : config file not found. : " + e.getStackTrace());
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
				println("catch IOException[1] : readLine exception. : " + e.getStackTrace());
				throw new Exception();
			}
			
			println("config file load ok");
			
			//init check table
			//lineCnt = EmpCnt in config file
			insaneChkList = new int[lineCnt][];
			for(int i=0; i<lineCnt; i++) {
				insaneChkList[i] = new int[lineCnt];
				for(int j=0; j<lineCnt; j++) {
					insaneChkList[i][j] = 0;
				}
			}
			
			//init comment file name list
			//MessageComment.xls
			//MessageComment (1).xls
			//MessageComment (2).xls ...
			fileList.add(defaultXlsName + fileExtension);
			for(int i=1; i<lineCnt; i++) {
				fileList.add(defaultXlsName + " (" + i + ")" + fileExtension); 
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
			
			for(fileIndex = 0; fileIndex<fileList.size(); fileIndex++) {
//				println(fileList.get(fileIndex));
				
				try {
					reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileList.get(fileIndex)), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					println("catch UnsupportedEncodingException[1] : input stream reader exception : " + e.getStackTrace());
					throw new Exception();
				} catch (FileNotFoundException e) {
					println("catch FileNotFoundException[2]  : file input stream exception : " + e.getStackTrace());
					throw new Exception();
				}
				
				contacList = new ArrayList<String>();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date writtenDate = null;
				//Date nowDate = new Date();
				Date nowDate = dateFormat.parse("2014-12-13 10:33:56");
				
				try {
					
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
							
							writtenDate = dateFormat.parse(line);
							cal.setTime(writtenDate);
							int writtenMonth = cal.get(Calendar.MONTH) + 1;
							
							cal.setTime(nowDate);
							cal.add(Calendar.MONTH, -1);
							int measureMonth = cal.get(Calendar.MONTH) + 1;
							cal.add(Calendar.MONTH, -1);
							int exceptLimitMonth = cal.get(Calendar.MONTH) + 1;
							
							if(writtenMonth == exceptLimitMonth) {
								//no need to exam past month
								break;
							} else if(writtenMonth == measureMonth) {
								contacList.add(contacEmpName);
							}
							
							//System.out.println(monthNow + " / " + monthPast);
//							if(monthNow-1 == monthPast) {
//								contacList.add(contacEmpName);
//							} else {
//								if(monthNow)
//							}
						}//END OF IF
					}//END OF INNER WHILE-LOOP : ONE FILE READ
					
				} catch (IOException e) {
					println("catch IOException[2] : read line exception : " + e.getStackTrace());
					throw new Exception();
				} catch (ParseException e) {
					println("catch ParseException[1] : simple date formatter parse exception : " + e.getStackTrace());
					throw new Exception();
				} finally {
					try {
						reader.close();
					} catch (IOException e) {
						println("catch IOException[3] : reader close exception : " + e.getStackTrace());
						throw new Exception();
					}
				}
				
				//START OF FOR-LOOP : check contact list
				for(int i=0; i<contacList.size(); i++) {
					String contacNm = contacList.get(i);
					for(int j=0; j<empNmList.size(); j++) {
						if(empNmList.get(j).equals(contacNm)) {
							insaneChkList[j][fileIndex] = 1;
							break;
						}
					}
				}//END OF INNER FORLOOP
			
			}//BE OUTER FORLOOP : FILE
			
			//sum contact check
			int[] insaneChkSum = new int[lineCnt];
			for(int i=0; i<lineCnt; i++) {
				for(int j=0; j<lineCnt; j++) {
					print(insaneChkList[i][j] + " " );
					insaneChkSum[i] = insaneChkSum[i] + insaneChkList[i][j];
				}
				println(null);
			}
			
			for(int i=0; i<lineCnt; i++) {
				println(empNmList.get(i) + " : " + insaneChkSum[i]);
			}
			
			//show result via swing
			new JangHelper(lineCnt, empNumList, empNmList, insaneChkList, insaneChkSum).startSwing();
			println("normal exit");

		 } catch (Exception e) {
			 println("catch MAIN Exception : " + e.getStackTrace());
		 }//END OF TRY/CATCH
		
	}//END OF main()
	
}//END OF CLASS
