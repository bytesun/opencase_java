package org.orcsun.sunspace.object;

import java.util.Date;

/**
 * 
 * @author Sun
 * 
 *	UID BIGINT DEFAULT 0 NOT NULL,
	LTIME TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
	TAG VARCHAR(100),
	SUBJECT VARCHAR(1000) NOT NULL,
	ULOG MEDIUMTEXT NOT NULL
	STATUS CHAR(5)
	LTYPE CHAR(5)
	LID PRIMARY KEY 
 */
public class UserLog {
	private long uid,lid;
	private Date ltime;
	private String tag,subject,ulog;
	private int status, //0-draft/private,1-publish
				ltype; //0-user write log, 1-task log
	
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public Date getLtime() {
		return ltime;
	}
	public void setLtime(Date ltime) {
		this.ltime = ltime;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getUlog() {
		return ulog;
	}
	public void setUlog(String ulog) {
		this.ulog = ulog;
	}
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public long getLid() {
		return lid;
	}
	public void setLid(long lid) {
		this.lid = lid;
	}
	public int getLtype() {
		return ltype;
	}
	public void setLtype(int ltype) {
		this.ltype = ltype;
	}
	public String toString(){
		return this.subject;
	}
}
