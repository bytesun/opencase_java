package org.orcsun.sunspace.object;

import java.util.Date;

/**
 * CASEID BIGINT NOT NULL AUTO_INCREMENT,
	SUBJECT VARCHAR(100) NOT NULL,
	DESCRIPTION TEXT,
	TAG VARCHAR(100),
	PHASEID INT NOT NULL,
	STARTDATE DATE,
	ENDDATE DATE,
	STATUS CHAR(5),
	UID BIGINT NOT NULL,
	PRIMARY KEY (CASEID)
 * @author Sun
 *
 */
public class Case {
	private long caseid;
	private String subject;
	private String desc;
	private String tag;
	private int phaseid;
	private Date startdate;
	private Date enddate;
	private short status;//0-init,1-progress,-1-issue/question,9-done
	private short ctype;//1-idea,2-project,3-issue
	private boolean isOpen=true;
	private long uid;
	public long getCaseid() {
		return caseid;
	}
	public void setCaseid(long caseid) {
		this.caseid = caseid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public int getPhaseid() {
		return phaseid;
	}
	public void setPhaseid(int phaseid) {
		this.phaseid = phaseid;
	}
	public Date getStartdate() {
		return startdate;
	}
	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}
	public Date getEnddate() {
		return enddate;
	}
	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}
	public short getStatus() {
		return status;
	}
	public void setStatus(short status) {
		this.status = status;
	}
	
	public short getCtype() {
		return ctype;
	}
	public void setCtype(short ctype) {
		this.ctype = ctype;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	public boolean isOpen() {
		return isOpen;
	}
	public void setOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}
	
	
}
