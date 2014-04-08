/*    */ package org.orcsun.sunspace.utils;
/*    */ 
/*    */ import java.io.UnsupportedEncodingException;
/*    */ 
/*    */ public class StringUtil
/*    */ {
/*    */   public static String utf8(String s, String encode)
/*    */     throws UnsupportedEncodingException
/*    */   {
/* 17 */     return new String(s.getBytes(encode), "utf-8");
/*    */   }
/*    */ }

/* Location:           E:\2014097ois-Sunorth-1.1.6\WEB-INF\classes\
 * Qualified Name:     org.orcsun.sunspace.utils.StringUtil
 * JD-Core Version:    0.6.2
 */