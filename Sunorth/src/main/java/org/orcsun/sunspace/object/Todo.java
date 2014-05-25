package org.orcsun.sunspace.object;

import java.util.Date;

public class Todo {
	private long tid = 0L;
	private Date ttime;
	private Date deadline;
	private String todo = "";
	private String note = "";
	private short ttype = 0;
	private boolean isDone;
	private short priority = 0;
	private User user;

	public long getTid() {
		return this.tid;
	}

	public void setTid(long tid) {
		this.tid = tid;
	}

	public String getTodo() {
		return this.todo;
	}

	public void setTodo(String todo) {
		this.todo = todo;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public short getTtype() {
		return this.ttype;
	}

	public void setTtype(short ttype) {
		this.ttype = ttype;
	}

	public short getPriority() {
		return this.priority;
	}

	public void setPriority(short priority) {
		this.priority = priority;
	}

	public User getUser() {
		return this.user;
	}

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

	public void setUser(User user) {
		this.user = user;
	}

	public String toString() {
		return this.todo;
	}
}
