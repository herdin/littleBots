package testsub;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import testmain.herdinTest;

/**
 * 집계PC(클라이언트)는 윈도우, java7
 * 파일집계PC(서버)는 유닉스, java6
 * 
 */
public class PROCESS_MONITOR_SERVER extends TEST {
    
    static final String SERVER_IP   = "127.0.0.1";
    static final int SERVER_PORT    = 9988;
    static final int SIZE_OF_BUFFER = 01024;
    static final Pattern PATTERN_CLIENT_ID = Pattern.compile("CLIENT_ID[(][^(]+[)]");
    static final Pattern PATTERN_PROCESS_STATUS = Pattern.compile("PROCESS_(UP|DOWN){1}[(][^(]+[)]");
    
//    String input = ("CLIENT_ID(RANDOM_STRING)" + ((true)?"PROCESS_DOWN":"PROCESS_UP") + "(" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) +")...");
//    String[] dataRecTags = {"CLIENT_ID", "PROCESS_(UP|DOWN)"};
//    for(String dataRecTag : dataRecTags) {
//        Pattern p = Pattern.compile(dataRecTag + "[(][^()]*[)]");
//        Matcher m = p.matcher(input);
//        if(m.find()) {
//            herdinTest.PRINT_DEBUG("find");
//            herdinTest.PRINT_DEBUG(m.group());
//        } else {
//            herdinTest.PRINT_DEBUG("can't find");
//        }
//    }
    
    @Override
    protected void run() throws Exception {
        
        Selector selector = Selector.open();
        herdinTest.PRINT_DEBUG("SELECTOR OPEN OK.");
        
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        herdinTest.PRINT_DEBUG("SERVER SOCKET CHANNEL OPEN OK.");
        
        ServerSocket socket =  serverSocketChannel.socket();
        herdinTest.PRINT_DEBUG("SERVER SOCKET RETRIEVE FROM SERVER SOCKET CHANNEL OK.");
        
        socket.bind(new InetSocketAddress(SERVER_PORT));
        herdinTest.PRINT_DEBUG("SERVER SOCKET BIND OK : " + socket.getInetAddress() + ":" + socket.getLocalPort());
        
        serverSocketChannel.configureBlocking(false);
        int serverSocketOpt = SelectionKey.OP_ACCEPT;
        serverSocketChannel.register(selector, serverSocketOpt);
        herdinTest.PRINT_DEBUG("SERVER SOCKET REGISTER TO SELECTOR OK. OPTION(" + serverSocketOpt +")");
        
        int socketOps = SelectionKey.OP_CONNECT|SelectionKey.OP_READ;//|SelectionKey.OP_WRITE;
        ByteBuffer byteBuffer = ByteBuffer.allocate(SIZE_OF_BUFFER);
        byte[] bytes = new byte[byteBuffer.capacity()];
        StringBuffer stringBuffer = new StringBuffer();
        
        try {
            while(true) {
                selector.select();
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while(iter.hasNext()) {
                    herdinTest.PRINT_DEBUG("SELECT SOMETHING...");
                    SelectionKey selected = iter.next();
                    iter.remove();
                    SelectableChannel selectableChannel = selected.channel();
                    
                    //리스닝하던 서버소켓에 클라이언트 접속
                    if(selectableChannel instanceof ServerSocketChannel) {
                        herdinTest.PRINT_DEBUG("SERVER SOCKET CHANNEL SELECTED...", false);
                        ServerSocketChannel selectedServerSocketChannel = (ServerSocketChannel)selectableChannel;
                        SocketChannel socketChannel = selectedServerSocketChannel.accept();
                        if(socketChannel == null) {
                            herdinTest.PRINT_DEBUG("ACCEPT FAIL : FAIL TO CREATE SOCKET CHANNEL.", false);
                            continue;
                        } else {
                            herdinTest.PRINT_DEBUG("ACCEPT OK...(" + socketChannel.getLocalAddress() + ")...", false);
                        }
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, socketOps);
                        herdinTest.PRINT_DEBUG("ACCEPTED SOCKET CHANNEL REGISTER TO SELECTOR OK. OPTION(" + socketOps + ")", false);
                    }
                    //연결된 소켓에서 데이터 들어옴
                    else {
                        herdinTest.PRINT_DEBUG("SOCKET CHANNEL SELECTED...", false);
                        SocketChannel socketChannel = (SocketChannel)selectableChannel;
                        
                        //연결요청
                        if(selected.isConnectable()) {
                            herdinTest.PRINT_DEBUG("CONNECTABLE SOCKET CHANNEL, CANCEL SELECT.", false);
                            selected.cancel();
                        }
                        //데이터수신
                        else if(selected.isReadable()) {
                            herdinTest.PRINT_DEBUG("READABLE SOCKET CHANNEL...", false);
                            
                            int readCnt = -1;
                            try {
                                readCnt = socketChannel.read(byteBuffer);
                            } catch (IOException e) {
                                herdinTest.PRINT_DEBUG("READABLE SOCKET READ OCCUR IOEXCEPTION. : " + e.getMessage());
                                selected.cancel();
                                continue;
                            }
                            byteBuffer.flip();
                            
                            if(readCnt <= 0) {
                                selected.cancel();
                                herdinTest.PRINT_DEBUG("READ EOF, CANCEL SELECT.", false);
                                continue;
                            }
                            
                            while(byteBuffer.hasRemaining()) {
                                int remaining = byteBuffer.remaining();
                                byteBuffer.get(bytes, 0, remaining);
                                stringBuffer.append(new String(bytes, 0, remaining));
                            }
                            byteBuffer.clear();
                            String recvMessage = stringBuffer.toString();
                            herdinTest.PRINT_DEBUG("RECEIVED MESSAGE FROM [" + socketChannel.socket().getRemoteSocketAddress() + "] TO [" + socketChannel.socket().getLocalSocketAddress() + "] : " + recvMessage + "...", false);
                            //this.saveRecvData(recvMessage);
                            stringBuffer.setLength(0);
                        }
                        //셀렉트 옵션에서 SelectionKey.OP_WRITE 를 빼주면 이리로 안옴
                        else if(selected.isWritable()) {
                            herdinTest.PRINT_DEBUG("WRITABLE SOCKET CHANNEL. DO NOTHING", false);
                        }
                        
                    }//END OF IF-ELSE
                }//END OF WHILE-LOOP
            }//END OF INFINIT WHILE-LOOP
        } catch (IOException ioe) {
            ioe.printStackTrace();
            if(socket != null) try { socket.close(); } catch(Exception e) { e.printStackTrace(); }
        }
    }//END OF FUNCTION
    
    private void saveRecvData(String message) {
        Matcher m = PATTERN_CLIENT_ID.matcher(message);
        String partClientId = null;
        String partProcessStatus = null;
        String partProcessTime = null;
        if(m.find()) {
            partClientId = m.group();
            partClientId = partClientId.substring(partClientId.indexOf("("), partClientId.length()-1);
        }
        m = PATTERN_PROCESS_STATUS.matcher(message);
        if(m.find()) {
            partProcessStatus = (m.group().contains("UP"))? "UP":"DOWN";
            partProcessTime = m.group();
            partProcessTime = partProcessTime.substring(partProcessTime.indexOf("("), partProcessTime.length()-1);
        }
        herdinTest.PRINT_DEBUG("PARSING DATA : ID(" + partClientId + ")STATUS(" + partProcessStatus + ")TIME(" + partProcessTime +")...", false);
        
        /* DEV VARIABLES */
        boolean isDEV = false;
        
        /* JDBC VARIABLES */
        Connection connDev = null;
        PreparedStatement ps = null;
        final String DEV_CZZDBD_IP = "172.16.52.133";
        final String DEV_CZZDBD_PORT = "2522";
        final String DEV_CZZDBD_ID = "PCZZCZB";
        final String DEV_CZZDBD_PW = "roqkf12";
        
        try {
            
            connDev = DriverManager.getConnection("jdbc:oracle:thin:@" + DEV_CZZDBD_IP + ":" + DEV_CZZDBD_PORT + ":CZZDBD", DEV_CZZDBD_ID, DEV_CZZDBD_PW); //개발
            herdinTest.PRINT_DEBUG("DATABASE CONNECTION OK");
            
            ResultSet rs = null;
            
            long processCnt = 0;
            herdinTest.PRINT_DEBUG("CURRENT TRANSFER(OPR->DEV) DATA : " + rs.getString(4) + " : " + (++processCnt));
            ps = connDev.prepareCall(getQueryFromFile("DEV_INSERT_QUERY_TEXT"));
            ps.setString(1, partClientId);
            ps.setString(2, partProcessTime);
            ps.setString(3, partProcessStatus);
            ps.executeUpdate();
            ps.close();
        } catch ( SQLException e ) {
            e.printStackTrace();
        } finally {
            try { if(ps != null) ps.close(); } catch ( SQLException e ) { e.printStackTrace(); }
            try { if(connDev != null) connDev.close(); } catch ( SQLException e ) { e.printStackTrace(); }
        }
    }//END OF FUNCTION
    
    private String getQueryFromFile(String fileNameStr) {
        return herdinTest.GET_TEXT_FROM_FILE("C:\\KSCCDev\\workspace\\herdinProject_java7\\src\\testsub\\" + fileNameStr);
    }//END OF FUNCTION
}//END OF CLASS