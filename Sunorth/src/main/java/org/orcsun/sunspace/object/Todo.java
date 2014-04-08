/*    */ package org.orcsun.sunspace.object;
/*    */ 
/*    */ public class Todo
/*    */ {
/* 16 */   private long tid = 0L; private long ttime = 0L; private long deadline = 0L;
/* 17 */   private String todo = ""; private String note = "";
/* 18 */   private short ttype = 0; private short status = 0; private short priority = 0;
/*    */   private User user;
/*    */ 
/*    */   public long getTid()
/*    */   {
/* 21 */     return this.tid;
/*    */   }
/*    */   public void setTid(long tid) {
/* 24 */     this.tid = tid;
/*    */   }
/*    */   public long getTtime() {
/* 27 */     return this.ttime;
/*    */   }
/*    */   public void setTtime(long ttime) {
/* 30 */     this.ttime = ttime;
/*    */   }
/*    */ 
/*    */   public long getDeadline() {
/* 34 */     return this.deadline;
/*    */   }
/*    */   public void setDeadline(long deadline) {
/* 37 */     this.deadline = deadline;
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
/*    */   public short getStatus() {
/* 58 */     return this.status;
/*    */   }
/*    */   public void setStatus(short status) {
/* 61 */     this.status = status;
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
/*    */   public void setUser(User user) {
/* 74 */     this.user = user;
/*    */   }
/*    */   public String toString() {
/* 77 */     return this.todo;
/*    */   }
/*    */ }

/* Location:           E:\2014097ois-Sunorth-1.1.6\WEB-INF\classes\
 * Qualified Name:     org.orcsun.sunspace.object.Todo
 * JD-Core Version:    0.6.2
 */