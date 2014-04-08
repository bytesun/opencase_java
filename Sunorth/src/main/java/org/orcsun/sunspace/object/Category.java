/*    */ package org.orcsun.sunspace.object;
/*    */ 
/*    */ public class Category
/*    */ {
/*    */   private long cid;
/*    */   private long pid;
/*    */   private String catname;
/*    */ 
/*    */   public long getCid()
/*    */   {
/* 15 */     return this.cid;
/*    */   }
/*    */   public void setCid(long cid) {
/* 18 */     this.cid = cid;
/*    */   }
/*    */   public long getPid() {
/* 21 */     return this.pid;
/*    */   }
/*    */   public void setPid(long pid) {
/* 24 */     this.pid = pid;
/*    */   }
/*    */   public String getCatname() {
/* 27 */     return this.catname;
/*    */   }
/*    */   public void setCatname(String catname) {
/* 30 */     this.catname = catname;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 35 */     return this.catname;
/*    */   }
/*    */ }

/* Location:           E:\2014097ois-Sunorth-1.1.6\WEB-INF\classes\
 * Qualified Name:     org.orcsun.sunspace.object.Category
 * JD-Core Version:    0.6.2
 */