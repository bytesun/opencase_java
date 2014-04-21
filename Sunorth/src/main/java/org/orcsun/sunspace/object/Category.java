package org.orcsun.sunspace.object;

public class Category {
	private long cid;
	private long pid;
	private String catname;
	private String description;
	private int rate;

	public long getCid() {
		return this.cid;
	}

	public void setCid(long cid) {
		this.cid = cid;
	}

	public long getPid() {
		return this.pid;
	}

	public void setPid(long pid) {
		this.pid = pid;
	}

	public String getCatname() {
		return this.catname;
	}

	public void setCatname(String catname) {
		this.catname = catname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public String toString() {
		return this.catname;
	}
}
