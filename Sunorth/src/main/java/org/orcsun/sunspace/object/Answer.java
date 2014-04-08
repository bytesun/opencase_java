/*    */ package org.orcsun.sunspace.object;
/*    */ 
/*    */ public class Answer
/*    */ {
/*    */   private long qid;
/*    */   private long aid;
/*    */   private long atime;
/*    */   private int rate;
/*    */   private String answer;
/*    */   private User user;
/*    */ 
/*    */   public long getAid()
/*    */   {
/* 19 */     return this.aid;
/*    */   }
/*    */   public void setAid(long aid) {
/* 22 */     this.aid = aid;
/*    */   }
/*    */   public long getQid() {
/* 25 */     return this.qid;
/*    */   }
/*    */   public void setQid(long qid) {
/* 28 */     this.qid = qid;
/*    */   }
/*    */   public long getAtime() {
/* 31 */     return this.atime;
/*    */   }
/*    */   public void setAtime(long atime) {
/* 34 */     this.atime = atime;
/*    */   }
/*    */ 
/*    */   public int getRate() {
/* 38 */     return this.rate;
/*    */   }
/*    */   public void setRate(int rate) {
/* 41 */     this.rate = rate;
/*    */   }
/*    */   public String getAnswer() {
/* 44 */     return this.answer;
/*    */   }
/*    */   public void setAnswer(String answer) {
/* 47 */     this.answer = answer;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 51 */     return this.answer;
/*    */   }
/*    */   public User getUser() {
/* 54 */     return this.user;
/*    */   }
/*    */   public void setUser(User user) {
/* 57 */     this.user = user;
/*    */   }
/*    */ }

/* Location:           E:\2014097ois-Sunorth-1.1.6\WEB-INF\classes\
 * Qualified Name:     org.orcsun.sunspace.object.Answer
 * JD-Core Version:    0.6.2
 */