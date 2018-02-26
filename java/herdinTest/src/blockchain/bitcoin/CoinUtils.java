package blockchain.bitcoin;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class CoinUtils {
	public static String leftPad(String input, int count, String padChar) {
		if(input == null || input.length() <= 0 || input.length() > count || padChar == null || padChar.length() != 1 || input.length() == count)
			return input;
		StringBuffer sb = new StringBuffer(input);
		for(int i=0; i<count-input.length(); i++)
			sb.append(padChar);
		return sb.toString();
	}
    public static byte[] encSHA256(byte[] bin) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bout = messageDigest.digest(bin);
        return bout;
    }//END OF FUNCTION
    
//    public static getTargetFromnNbits(byte[] nBits) {
//        
//    }//END OF FUNCTION
    
    public static String byteArrayToHexString(byte[] bs) {
        StringBuilder sb = new StringBuilder();
        for(final byte b : bs) {
            sb.append(String.format("%02x", b&0xff));
        }
        return sb.toString();
    }//END OF FUNCTION
    
    public static String numericToHexString(BigInteger bi, int bytes) {
        String tempHexStr = String.format("%02x", bi);
        if(tempHexStr.length() > bytes*2) {
            throw new NumberFormatException("INPUT LONG IS TOO BIGGER THAN EXPECTED");
        } else if(tempHexStr.length() < bytes*2) {
            tempHexStr = CoinUtils.leftPad(tempHexStr, bytes*2, "0");
        }
        return tempHexStr;
    }
    public static String numericToHexString(long l, int bytes) {
        String tempHexStr = String.format("%02x", l);
        if(tempHexStr.length() > bytes*2) {
            throw new NumberFormatException("INPUT LONG IS TOO BIGGER THAN EXPECTED");
        } else if(tempHexStr.length() < bytes*2) {
            tempHexStr = CoinUtils.leftPad(tempHexStr, bytes*2, "0");
        }
        return tempHexStr;
    }//END OF FUNCTION
    
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] byteArr = new byte[len/2];
        for(int i=0; i<len; i+=2) {
            byteArr[i/2] = (byte)
                (
                    (Character.digit(s.charAt(i), 16) << 4) +
                    (Character.digit(s.charAt(i+1), 16))
                );
        }
        return byteArr;
    }//END OF FUNCTION
    
    public static byte[] getReversedByteArray(byte[] inbs) {
        byte[] outbs = new byte[inbs.length];
        Arrays.fill(outbs, (byte)0x00);
        
        for(int i=0; i<inbs.length/2; i++) {
            outbs[inbs.length-i-1] = inbs[i];
            outbs[i] = inbs[inbs.length-i-1];
        }
        return outbs;
    }//END OF FUNCTION
}//END OF CLASS