package org.orcsun.sunspace.object;

import java.util.Date;

public class Question {
	private long qid = 0L;
	private long pid = 0L;
	private long cid = 0L;
	private Date qtime;
	private int rate = 0;
	private int answercnt = 0;
	private boolean resolved;
	private String question = "";
	private String description = "";
	private String tag = "";
	private User user;

	public long getQid() {
		return this.qid;
	}

	public void setQid(long qid) {
		this.qid = qid;
	}

	public long getPid() {
		return this.pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public long getCid() {
		return this.cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public int getRate() {
		return this.rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getAnswercnt() {
		return this.answercnt;
	}

	public void setAnswercnt(int answercnt) {
		this.answercnt = answercnt;
	}

	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTag() {
		return this.tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getQtime() {
		return qtime;
	}

	public void setQtime(Date qtime) {
		this.qtime = qtime;
	}

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

	public String toString() {
		return this.question;
	}
}
