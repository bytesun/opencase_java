/*    */ package org.orcsun.sunspace.object;
/*    */ 
/*    */ public class Question
/*    */ {
/* 22 */   private long qid = 0L; private long pid = 0L; private long cid = 0L;
/*    */   private long qtime;
/* 23 */   private int rate = 0; private int answercnt = 0;
/*    */   private int status;
/* 24 */   private String question = ""; private String description = ""; private String tag = "";
/*    */   private User user;
/*    */ 
/*    */   public long getQid()
/*    */   {
/* 28 */     return this.qid;
/*    */   }
/*    */   public void setQid(long qid) {
/* 31 */     this.qid = qid;
/*    */   }
/*    */   public long getPid() {
/* 34 */     return this.pid;
/*    */   }
/*    */   public void setPid(long pid) {
/* 37 */     this.pid = pid;
/*    */   }
/*    */   public long getCid() {
/* 40 */     return this.cid;
/*    */   }
/*    */   public void setCid(long cid) {
/* 43 */     this.cid = cid;
/*    */   }
/*    */   public int getRate() {
/* 46 */     return this.rate;
/*    */   }
/*    */   public void setRate(int rate) {
/* 49 */     this.rate = rate;
/*    */   }
/*    */   public int getAnswercnt() {
/* 52 */     return this.answercnt;
/*    */   }
/*    */   public void setAnswercnt(int answercnt) {
/* 55 */     this.answercnt = answercnt;
/*    */   }
/*    */   public String getQuestion() {
/* 58 */     return this.question;
/*    */   }
/*    */   public void setQuestion(String question) {
/* 61 */     this.question = question;
/*    */   }
/*    */   public String getDescription() {
/* 64 */     return this.description;
/*    */   }
/*    */   public void setDescription(String description) {
/* 67 */     this.description = description;
/*    */   }
/*    */   public String getTag() {
/* 70 */     return this.tag;
/*    */   }
/*    */   public void setTag(String tag) {
/* 73 */     this.tag = tag;
/*    */   }
/*    */   public long getQtime() {
/* 76 */     return this.qtime;
/*    */   }
/*    */   public void setQtime(long qtime) {
/* 79 */     this.qtime = qtime;
/*    */   }
/*    */ 
/*    */   public User getUser()
/*    */   {
/* 84 */     return this.user;
/*    */   }
/*    */   public void setUser(User user) {
/* 87 */     this.user = user;
/*    */   }
/*    */   public int getStatus() {
/* 90 */     return this.status;
/*    */   }
/*    */   public void setStatus(int status) {
/* 93 */     this.status = status;
/*    */   }
/*    */   public String toString() {
/* 96 */     return this.question;
/*    */   }
/*    */ }

/* Location:           E:\2014097ois-Sunorth-1.1.6\WEB-INF\classes\
 * Qualified Name:     org.orcsun.sunspace.object.Question
 * JD-Core Version:    0.6.2
 */