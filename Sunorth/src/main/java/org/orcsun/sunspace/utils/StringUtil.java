package org.orcsun.sunspace.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class StringUtil {
	private static String[] hexDigits = new String[] { "0", "1", "2", "3", "4",
			"5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public static String utf8(String s, String encode)
			throws UnsupportedEncodingException {
		return new String(s.getBytes(encode), "utf-8");
	}

	public static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n / 16;
		return hexDigits[d1] + hexDigits[d2];
	}
	
    public static String byteArrayToHexString(byte[] b)  
    {  
        StringBuffer resultsb = new StringBuffer();  
        int i=0;  
        for(i=0;i<b.length;i++)  
        {  
            resultsb.append(byteToHexString(b[i]));  
        }  
        return resultsb.toString();  
    }  
	  public static String encodeByMD5(String originstr)  
	    {  
	        if(originstr !=null)  
	        {  
	            try{  
	                MessageDigest md = MessageDigest.getInstance("MD5");  
	                byte[] results = md.digest(originstr.getBytes());  
	                String resultString = StringUtil.byteArrayToHexString(results);  
	                return resultString.toUpperCase();  
	            }catch(Exception ex){  
	                ex.printStackTrace();  
	            }  
	        }  
	        return null;  
	    }  
}
