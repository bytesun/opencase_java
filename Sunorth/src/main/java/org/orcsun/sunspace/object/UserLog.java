package org.orcsun.sunspace.object;

import java.util.Date;

/**
 * 
 * @author Sun
 *	UID BIGINT DEFAULT 0 NOT NULL,
	LTIME TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
	TAG VARCHAR(100),
	SUBJECT VARCHAR(1000) NOT NULL,
	ULOG MEDIUMTEXT NOT NULL
 */
public class UserLog {
	private long uid;
	private Date ltime;
	private String tag,subject,ulog;
	private int status;
	
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
	public String toString(){
		return subject;
	}
}
