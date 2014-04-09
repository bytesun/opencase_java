/*    */ package org.orcsun.sunspace.object;

import java.util.Date;

/*    */ 
/*    */ public class Todo
/*    */ {
/* 16 */   private long tid = 0L; private Date ttime; private Date deadline;
/* 17 */   private String todo = ""; private String note = "";
/* 18 */   private short ttype = 0; private boolean isDone; private short priority = 0;
/*    */   private User user;
/*    */ 
/*    */   public long getTid()
/*    */   {
/* 21 */     return this.tid;
/*    */   }
/*    */   public void setTid(long tid) {
/* 24 */     this.tid = tid;
/*    */   }

/*    */   public String getTodo() {
/* 40 */     return this.todo;
/*    */   }
/*    */   public void setTodo(String todo) {
/* 43 */     this.todo = todo;
/*    */   }
/*    */   public String getNote() {
/* 46 */     return this.note;
/*    */   }
/*    */   public void setNote(String note) {
/* 49 */     this.note = note;
/*    */   }
/*    */   public short getTtype() {
/* 52 */     return this.ttype;
/*    */   }
/*    */   public void setTtype(short ttype) {
/* 55 */     this.ttype = ttype;
/*    */   }

/*    */   public short getPriority() {
/* 64 */     return this.priority;
/*    */   }
/*    */   public void setPriority(short priority) {
/* 67 */     this.priority = priority;
/*    */   }
/*    */ 

/*    */   public User getUser() {
/* 71 */     return this.user;
/*    */   }
public Date getTtime() {
	return ttime;
}
public void setTtime(Date ttime) {
	this.ttime = ttime;
}
public Date getDeadline() {
	return deadline;
}
public void setDeadline(Date deadline) {
	this.deadline = deadline;
}
public boolean isDone() {
	return isDone;
}
public void setDone(boolean isDone) {
	this.isDone = isDone;
}
/*    */   public void setUser(User user) {
/* 74 */     this.user = user;
/*    */   }
/*    */   public String toString() {
/* 77 */     return this.todo;
/*    */   }
/*    */ }
