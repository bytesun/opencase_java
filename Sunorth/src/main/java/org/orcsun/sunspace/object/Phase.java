package org.orcsun.sunspace.object;

import java.util.Date;



/**
 * PHASEID INT NOT NULL,
	CASEID BIGINT NOT NULL,
	PHASE VARCHAR(100),
	NOTE TEXT,
	STARTDATE DATE,
	ENDDATE DATE,
	TAG VARCHAR(100),
 * @author sun
 *
 */
public class Phase {

	private int phaseid;
	private long caseid;
	private String phase;
	private String note;
	private Date startdate;
	private Date enddate;
	private String tag;
	
	public int getPhaseid() {
		return phaseid;
	}
	public void setPhaseid(int phaseid) {
		this.phaseid = phaseid;
	}
	public long getCaseid() {
		return caseid;
	}
	public void setCaseid(long caseid) {
		this.caseid = caseid;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
