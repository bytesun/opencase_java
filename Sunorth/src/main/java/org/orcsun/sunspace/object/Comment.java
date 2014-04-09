/*    */ package org.orcsun.sunspace.object;

import java.util.Date;

/*    */ 
/*    */ public class Comment
/*    */ {
/*    */   private long qid;
/*    */   private Date ctime;
/*    */   private String comment;
/*    */   private User user;
/*    */ 
/*    */   public long getQid()
/*    */   {
/* 10 */     return this.qid;
/*    */   }
/*    */   public void setQid(long qid) {
/* 13 */     this.qid = qid;
/*    */   }

public Date getCtime() {
	return ctime;
}
public void setCtime(Date ctime) {
	this.ctime = ctime;
}
/*    */   public String getComment() {
/* 22 */     return this.comment;
/*    */   }
/*    */   public void setComment(String comment) {
/* 25 */     this.comment = comment;
/*    */   }
/*    */   public User getUser() {
/* 28 */     return this.user;
/*    */   }
/*    */   public void setUser(User user) {
/* 31 */     this.user = user;
/*    */   }
/*    */ }

/* Location:           E:\2014097ois-Sunorth-1.1.6\WEB-INF\classes\
 * Qualified Name:     org.orcsun.sunspace.object.Comment
 * JD-Core Version:    0.6.2
 */