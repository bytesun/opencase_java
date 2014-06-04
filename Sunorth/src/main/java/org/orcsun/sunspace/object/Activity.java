package org.orcsun.sunspace.object;

import java.util.Date;


public class Activity {

	/**
	 * 	AID BIGINT NOT NULL AUTO_INCREMENT,
	SUBJECT VARCHAR(1000) NOT NULL,
	DESCRIPTION TEXT NOT NULL,
	ATYPE CHAR(10) DEFAULT 0 NOT NULL,
	LOCATION VARCHAR(1000) NOT NULL,
	ASTIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	AETIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	COST INT,
	ATTCNT INT,
	 */
	private long aid;
	private String subject,description,location;
	private int atype,attcnt;
	private double cost;
	private Date astime,aetime;
	private User organizer;
	
	public long getAid() {
		return aid;
	}
	public void setAid(long aid) {
		this.aid = aid;
	}
	

	public User getOrganizer() {
		return organizer;
	}
	public void setOrganizer(User organizer) {
		this.organizer = organizer;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getAtype() {
		return atype;
	}
	public void setAtype(int atype) {
		this.atype = atype;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getAttcnt() {
		return attcnt;
	}
	public void setAttcnt(int attcnt) {
		this.attcnt = attcnt;
	}
	public Date getAstime() {
		return astime;
	}
	public void setAstime(Date astime) {
		this.astime = astime;
	}
	public Date getAetime() {
		return aetime;
	}
	public void setAetime(Date aetime) {
		this.aetime = aetime;
	}
	
	public String toString(){
		return this.subject;
	}
}
