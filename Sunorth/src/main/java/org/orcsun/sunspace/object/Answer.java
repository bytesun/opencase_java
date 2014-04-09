/*    */ package org.orcsun.sunspace.object;

import java.util.Date;

/*    */ 
/*    */ public class Answer
/*    */ {
/*    */   private long qid;
/*    */   private long aid;
/*    */   private Date atime;
/*    */   private int rate;
/*    */   private String answer;
/*    */   private User user;
		   private boolean answered;
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

public Date getAtime() {
	return atime;
}
public void setAtime(Date atime) {
	this.atime = atime;
}

public boolean isAnswered() {
	return answered;
}
public void setAnswered(boolean answered) {
	this.answered = answered;
}
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

