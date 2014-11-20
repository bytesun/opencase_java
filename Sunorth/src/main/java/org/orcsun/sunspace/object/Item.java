package org.orcsun.sunspace.object;

import java.util.Date;

/**
 * ITEMID BIGINT NOT NULL AUTO_INCREMENT,
	ITEM VARCHAR(100) NOT NULL,
	NOTE TEXT,
	DOTIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	PHASEID INT NOT NULL,
	CASEID BIGINT NOT NULL,
 * @author Sun
 *
 */
public class Item {
	private long itemid;
	private String item;
	private String note;
	private Date dotime;
	private int phaseid;
	private long caseid;
	private long owner=0;
	private int status=0;//0-plan, 1-done
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getItemid() {
		return itemid;
	}
	public void setItemid(long itemid) {
		this.itemid = itemid;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getDotime() {
		return dotime;
	}
	public void setDotime(Date dotime) {
		this.dotime = dotime;
	}
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
	public long getOwner() {
		return owner;
	}
	public void setOwner(long owner) {
		this.owner = owner;
	}
	
}
