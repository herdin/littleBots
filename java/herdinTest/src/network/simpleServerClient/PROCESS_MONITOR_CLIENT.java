package testsub;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import testmain.herdinTest;

public class PROCESS_MONITOR_CLIENT extends TEST {
    
    @Override
    protected void run() throws Exception {
        
        final Path CONFIG_FILE_FULL_PATH    = Paths.get("PROCESS_MONITOR_CONFIG");
        FileChannel fileChannel = null;
        ByteBuffer byteBuffer = ByteBuffer.allocate(100);
        String monitoringClientId = "";
        long monitoringDelaySecond = -1L;
        String monitoringServerIp = "";
        String monitoringServerPort = "";
        String monitoringTargetProcessName = "";
        
        //설정파일에서 감시해야할 프로세스명칭을 읽어온다.
        try {
            
            fileChannel = FileChannel.open(CONFIG_FILE_FULL_PATH, StandardOpenOption.READ);
            herdinTest.PRINT_DEBUG("CONFIG FILE FOUND.");
            
            if(fileChannel.size() > 0) {
                fileChannel.read(byteBuffer, 0L);
                byteBuffer.flip();
                byte[] bytes = new byte[byteBuffer.remaining()];
                byteBuffer.get(bytes);
                byteBuffer.clear();
                String configStr = new String(bytes, Charset.forName("UTF-8"));
                monitoringClientId = configStr.substring(0, 20).trim();
                monitoringDelaySecond = Long.parseLong(configStr.substring(20, 40).trim());
                monitoringServerIp = configStr.substring(40, 60).trim();
                monitoringServerPort = configStr.substring(60, 80).trim();
                monitoringTargetProcessName = configStr.substring(80).trim();
                herdinTest.PRINT_DEBUG("CLIENT ID FROM CONFIG FILE : " + monitoringClientId);
                herdinTest.PRINT_DEBUG("DELAY SECOND FROM CONFIG FILE : " + monitoringDelaySecond);
                herdinTest.PRINT_DEBUG("SERVER IP FROM CONFIG FILE : " + monitoringServerIp);
                herdinTest.PRINT_DEBUG("SERVER PORT FROM CONFIG FILE : " + monitoringServerPort);
                herdinTest.PRINT_DEBUG("MONITORING TARGET PROCESS NAME FROM CONFIG FILE : " + monitoringTargetProcessName);
            }
        } catch (NoSuchFileException e) {
            herdinTest.PRINT_DEBUG("CONFIG FILE ABNORMAL. PROCESS END.");
            return;
        } finally {
            if(fileChannel != null) try { fileChannel.close(); } catch(Exception e) { e.printStackTrace(); }
            if(byteBuffer != null) byteBuffer = null; //for gc
        }
        
        //서버접속 설정
        class Connector {
            private String monitoringClientId = null;
            private String monitoringServerIp = null;
            private String monitoringServerPort = null;
            private Socket sock = null;
            private OutputStream toServer = null;
            private byte[] buffer;
            private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            public Connector(String monitoringClientId, String monitoringServerIp, String monitoringServerPort) {
                this.monitoringClientId = monitoringClientId;
                this.monitoringServerIp = monitoringServerIp;
                this.monitoringServerPort = monitoringServerPort;
            }//END OF FUNCTION
            public void init() throws IOException {
                this.sock = new Socket(monitoringServerIp, Integer.parseInt(monitoringServerPort));
                herdinTest.PRINT_DEBUG("SERVER CONNECT OK : IP(" + this.monitoringServerIp + ") PORT(" + this.monitoringServerPort + ")...", false);
                this.toServer = sock.getOutputStream();
            }//END OF FUNCTION
            
            public void sendAlert(boolean isTargetProcessDown) {
                try {
                    if(this.sock == null || this.sock.isClosed())
                        this.init();
                } catch ( IOException e ) {
                    herdinTest.PRINT_DEBUG("CONNECT ERROR...", false);
                    this.clear();
                    return;
                }
                
                String currentDateTime = sdf.format(new Date());
                herdinTest.PRINT_DEBUG("CURRENT TIME(" + currentDateTime + ")...", false);
                this.buffer = ("CLIENT_ID(" + this.monitoringClientId + ")" + ((isTargetProcessDown)?"PROCESS_DOWN":"PROCESS_UP") + "(" + currentDateTime +")...").getBytes(Charset.forName("UTF-8"));
                try {
                    this.toServer.write(buffer, 0, buffer.length);
                    this.toServer.flush();
                } catch ( IOException e ) {
                    herdinTest.PRINT_DEBUG("SOCKET WRITE ERROR...", false);
                    this.clear();
                    return;
                }
                
            }//END OF FUNCTION
            
            public void clear() {
                if(this.toServer != null)   try { this.toServer.close(); }  catch (IOException e) { e.printStackTrace(); }
                if(this.sock != null)       try { this.sock.close(); }      catch (IOException e) { e.printStackTrace(); }
            }//END OF FUNCTION
        }//END OF INNER-CLASS
        
         Connector conn = new Connector(monitoringClientId, monitoringServerIp, monitoringServerPort);
        
        //모니터링 시작
        while(true) {
          boolean isTargetProcessDown = isTargetProcessDown(monitoringTargetProcessName);
            
            if(isTargetProcessDown) {
                herdinTest.PRINT_DEBUG("[" + monitoringTargetProcessName + "] PROCESS DOWN...");
                Runtime.getRuntime().exec(monitoringTargetProcessName);
            } else {
                herdinTest.PRINT_DEBUG("[" + monitoringTargetProcessName + "] PROCESS UP...");
            }
            conn.sendAlert(isTargetProcessDown);
            
            Thread.sleep(monitoringDelaySecond*1000L);
        }
        
    }//END OF FUNCTION
    
    private boolean isTargetProcessDown(String monitoringTargetProcessName) {
        final int PROCESS_CHECKER_VERSION = 1;
        boolean isTargetProcessDown = true;
        String command = null;
        switch(PROCESS_CHECKER_VERSION) {
            case 1 : //윈도우 TASKLIST 버전
                command = (command == null)? System.getenv("windir") + "\\system32\\tasklist.exe" : command;
            case 2 : //JDK jps.exe 버전
                Process p;
                command = (command == null)? "c:\\jps_jdk17_x86.exe" : command;
                try {
                    p = Runtime.getRuntime().exec(command);
                    BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
                    String line;
                    while((line=input.readLine()) != null) {
                        herdinTest.PRINT_DEBUG(line);
                        if(line.contains(monitoringTargetProcessName)) {
                            isTargetProcessDown = false;
                            break;
                        }
                    }
                    input.close();
                } catch ( IOException e ) {
                    e.printStackTrace();
                }
                break;
            case 3 : //JDK tools.jar 버전 : 좀더 정확한 java program 을 알기위함
                List<VirtualMachineDescriptor> vmList = VirtualMachine.list();
                for(VirtualMachineDescriptor vm : vmList) {
                    if(vm.displayName().contains(monitoringTargetProcessName)) {
                        isTargetProcessDown = false;
                        break;
                    }
                }
                break;
            default : //아무것도 안함
                ;
        }
        return isTargetProcessDown;
    }//END OF FUNCTION
    
}//END OF CLASS