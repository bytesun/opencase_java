  package org.orcsun.sunspace.object;

import java.util.Date;

  
  public class User
  {
/* 15 */   private long uid = 0L;
    private Date regtime;
    private String name;
    private String passwd;
    private String email;
    private int credit;
    private int reputation;
    private int status;
	private String title;
	private String profile;
	private String resume;

  
    public long getUid()
    {
/* 21 */     return this.uid;
    }
    public void setUid(long uid) {
/* 24 */     this.uid = uid;
    }
    public String getName() {
/* 27 */     return this.name;
    }
    public void setName(String name) {
/* 30 */     this.name = name;
    }
    public String getPasswd() {
/* 33 */     return this.passwd;
    }
    public void setPasswd(String passwd) {
/* 36 */     this.passwd = passwd;
    }
    public String getEmail() {
/* 39 */     return this.email;
    }
    public void setEmail(String email) {
/* 42 */     this.email = email;
    }
    public int getCredit() {
/* 45 */     return this.credit;
    }
    public void setCredit(int credit) {
/* 48 */     this.credit = credit;
    }
    public int getReputation() {
/* 51 */     return this.reputation;
    }
    public void setReputation(int reputation) {
/* 54 */     this.reputation = reputation;
    }

public Date getRegtime() {
	return regtime;
}
public void setRegtime(Date regtime) {
	this.regtime = regtime;
}
    public int getStatus() {
/* 63 */     return this.status;
    }
    public void setStatus(int status) {
/* 66 */     this.status = status;
    }
    
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	public String toString() {
/* 69 */     return this.name;
    }
  }

