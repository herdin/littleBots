/*TEST MAIN*/
package testmain;
import java.awt.AWTException;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.apache.commons.lang.StringUtils;

import routinework.DAILY_MONITORING_PUTTY;
import routinework.DAILY_REPORT_SETTING;
import routinework.DELETE_BACKUP_QUERY_FILE;
import routinework.MONITORING;
import routinework.MONITORING.SERVERS;
import testsub.OOME_TEST;
import testsub.SYNC_TEST;

public class herdinTest {
    /* GENERIC TEST */
    public static <T> void swap(T[] array, int i, int j) {}
    public static <T extends Comparable<? super T>> void sort(List<T> list) {}
    public static <T extends Comparable<T>> void sort2(List<T> list) {}
    
    public void mousePointStudy() throws InterruptedException, AWTException {
      Thread.sleep(5000);
      while(true) {
          Point p = MouseInfo.getPointerInfo().getLocation();
          herdinTest.PRINT_DEBUG(p.x + ", " + p.y);
          
          Robot bot = new Robot();
          bot.mouseMove(p.x, p.y);
          bot.mousePress(InputEvent.BUTTON1_MASK);
          Thread.sleep(500);
          bot.mouseRelease(InputEvent.BUTTON1_MASK);
          Thread.sleep(500);
          bot.keyPress(KeyEvent.VK_SPACE);
          Thread.sleep(500);
          bot.keyRelease(KeyEvent.VK_SPACE);
          Thread.sleep(5000);
      }
    }
    
    public String testSHA256(String str){
        String SHA = ""; 
        try{
            MessageDigest sh = MessageDigest.getInstance("SHA-256"); 
            sh.update(str.getBytes()); 
            byte byteData[] = sh.digest();
            StringBuffer sb = new StringBuffer(); 
            for(int i = 0 ; i < byteData.length ; i++){
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
        }catch(NoSuchAlgorithmException e){
            e.printStackTrace(); 
            SHA = null; 
        }
        return SHA;
        /*
0000000000000000002a230af7c72fdb0cdf00880a00cc09b3e75589bdd70429

        */
    }

    /* **** */
    /* MAIN */
    /* **** */
    public static void main( String[] args ) throws Exception {
        herdinTest.PRINT_DEBUG("MAIN : START : TOTAL PROGRAM MEMORY : " + Runtime.getRuntime().totalMemory()/1024/1024 + "MB");
        if(false) {
            /******************************TEST HERE START******************************/
            /******************************TEST HERE START******************************/
            /******************************TEST HERE START******************************/
            new SYNC_TEST(); 
            /******************************TEST HERE END******************************/
            /******************************TEST HERE END******************************/
            /******************************TEST HERE END******************************/
            /******************************TEST HERE END******************************/
            herdinTest.PRINT_DEBUG("MAIN : END");
            return;
        }
        
        new DELETE_BACKUP_QUERY_FILE();
        new MONITORING();
        
        //10시이후엔 일일보고파일 복사와 MIMCI모니터링실행하지 않도록
        if(new Date().before(new SimpleDateFormat("yyyyMMddHHmmss").parse(new SimpleDateFormat("yyyyMMdd").format(new Date())+"100000"))) {
            new DAILY_REPORT_SETTING();
            new DAILY_MONITORING_PUTTY();
        }
        
        //KBizRuntimeException -> LRuntimeException -> RuntimeException -> Exception
        //                            LBizException ->       LException -> Exception
        //                           DevonException ->       LException -> Exception
        
        //공부 프로젝트
//        new TRY_CATCH();
//        new SIMPLE_DATE_FORMAT_THREAD_SAFE();
//        new CALENDAR_THREAD_SAFE();
//        new SORT();
//        new REGEXP();
//        new JDBC_CONNECT();
//        new LINE_FEED();
//        new APACHE_API();
//        new DEVON();
//        new FILE_DELETE();
//        new URL_ENCODE();
//        new CHAR_SET();
//        new AWT_STUDY();
//        new ARRAY_STUDY();
//        new THREAD_STUDY();
        
        //망한 프로젝트
//        new PROJECT_FILE_LIST_VIEW();
//        new BATCH_FILE_PARSE(); //그냥 울트라에딧쓰면 편함
//        new MIMCI_LOG_PARSE();
//        new FILE_IO();
//        new PUTTY_INTERFACE();
//      new MONITORING(); //미완성
        
        //실용 프로젝트
//        new MIMCI_BACTH();
//        new PROCESS_MONITOR_SERVER();
//        new PROCESS_MONITOR_CLIENT();
//        new DATA_TRANSFEROR();
//        new MILEAGE_CHECK();
        
        herdinTest.PRINT_DEBUG("MAIN : END");
//        System.exit(0);
    }//END OF FUNCTION
    
    public static void testFunc(Integer integer) {
        integer = new Integer(3);
    }
    
    public static void printCurrentMethod() {
        int i = 0;
        for(StackTraceElement ste : Thread.currentThread().getStackTrace()) {
            System.out.println(i++);
            System.out.println(ste.getMethodName());
        }
    }//END OF FUNCTION
    
    /* **************** */
    /* DEBUG PRINT AREA */
    /* **************** */
    public static PrintStream loggingStream = System.out;
    public static int DEPTH = 0;
    public static final String DEPTH_DIV = "##";
    public static String TEST_TYPE = "";
    public static void SET_TEST_TYPE(String testType)   { herdinTest.TEST_TYPE = testType; }
    public static void DEPTH_DOWN()                     { herdinTest.DEPTH++; }
    public static void DEPTH_UP()                       { herdinTest.DEPTH--; }
    
    public static void FUNCTION_START(String testType) {
        herdinTest.SET_TEST_TYPE(testType);
        herdinTest.PRINT_DEBUG("START");
        herdinTest.DEPTH_DOWN();
    }//END OF FUNCTION
    public static void FUNCTION_END() {
        herdinTest.DEPTH_UP();
        herdinTest.PRINT_DEBUG("END");
        System.out.println();
    }//END OF FUNCTION
    public static void FUNCTION_END(long durationMiliTime) {
        herdinTest.DEPTH_UP();
        int durationSecTime = (int)(durationMiliTime/1000);
        String durationTime = "" + (durationSecTime/(60*60)) + " HOUR " + (durationSecTime%(60*60)/60) + " MIN " + (durationSecTime%(60*60)%60) + " SEC";
        herdinTest.PRINT_DEBUG("END : RUNNING TIME : " + durationTime);
        herdinTest.SET_TEST_TYPE("");
    }//END OF FUNCTION
    public static void PRINT_DEBUG(Object obj) {
        herdinTest.PRINT_DEBUG(obj, true, herdinTest.loggingStream);
    }//END OF FUNCTION
    public static void PRINT_DEBUG(Object obj, boolean lineFeed) {
        herdinTest.PRINT_DEBUG(obj, lineFeed, herdinTest.loggingStream);
    }//END OF FUNCTION
    public static void PRINT_DEBUG(Object obj, PrintStream ps) {
        herdinTest.PRINT_DEBUG(obj, true, ps);
    }//END OF FUNCTION
    public static void PRINT_DEBUG(Object obj, boolean lineFeed, PrintStream ps) {
        String msg = (obj==null)? "":obj.toString();
        if(lineFeed) {
            ps.println();
            ps.print(DEPTH_DIV);
            for(int i=0; i<DEPTH; i++)
                ps.print(DEPTH_DIV);
            if(StringUtils.isEmpty(TEST_TYPE))
                TEST_TYPE = "DEFAULT_TEST_TYPE";
            ps.print(DEPTH + " : " + TEST_TYPE + " : " + msg);
        } else {
            ps.print(msg);
        }
        ps.flush();
    }//END OF FUNCTION
    
    synchronized public static void PRINT_DEBUG_SYNC(Object obj) {
        String msg = obj.toString();
        System.out.print(DEPTH_DIV);
        for(int i=0; i<DEPTH; i++) {
            System.out.print(DEPTH_DIV);
        }
        System.out.println(DEPTH + " : " + msg);
        System.out.flush();
    }//END OF FUNCTION
    
    public static String GET_TEXT_FROM_FILE(String fileFullPathStr) {
        String output = "";
        Path fileFullPath = Paths.get(fileFullPathStr);
        try {
            FileChannel fileChannel = FileChannel.open(fileFullPath, StandardOpenOption.READ);
            StringBuffer stringBuffer = new StringBuffer();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            byte[] bytes = new byte[byteBuffer.capacity()];
            while(fileChannel.position() < fileChannel.size()) {
                fileChannel.read(byteBuffer);
                byteBuffer.flip();
                while(byteBuffer.hasRemaining()) {
                    int remaining = byteBuffer.remaining();
                    byteBuffer.get(bytes, 0, remaining);
                    stringBuffer.append(new String(bytes, 0, remaining));
                }
                byteBuffer.clear();
            }
            output = stringBuffer.toString();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
        return output;
    }//END OF FUNCTION
}//END OF CLASS