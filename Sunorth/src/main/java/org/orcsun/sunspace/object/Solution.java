package org.orcsun.sunspace.object;

import java.util.Date;

/**
 * refer to table - COMMENT
 * 
 * COMMENTID BIGINT NOT NULL AUTO_INCREMENT,
	COMMENT TEXT NOT NULL,
	CASEID
	PHASEID
	CREATETIME  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
	UID BIGINT,
 * @author Sun
 *
 */
public class Solution {

	private long commentid;
	private String comment;
	private long caseid;
	private int phaseid=0;
	private Date createtime;
	private long uid=0;
	public long getCommentid() {
		return commentid;
	}
	public void setCommentid(long commentid) {
		this.commentid = commentid;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}

	public long getCaseid() {
		return caseid;
	}
	public void setCaseid(long caseid) {
		this.caseid = caseid;
	}
	public int getPhaseid() {
		return phaseid;
	}
	public void setPhaseid(int phaseid) {
		this.phaseid = phaseid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public long getUid() {
		return uid;
	}
	public void setUid(long uid) {
		this.uid = uid;
	}
	
}
