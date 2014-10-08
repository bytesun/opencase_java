package org.orcsun.sunspace.object;

import java.util.Date;

public class User {
	private long uid = 0L;
	private String openid;
	private Date regtime;
	private String name;
	private String passwd;
	private String refreshtoken;
	private String email;
	private int credit;
	private int reputation;
	private int status;
	private String title;
	private String profile;
	private String resume;
	private String skill;
	
	private String photo1;
	private String photo2;
	
	private long[] followUsers;
	private long[] followIssues;
	private long[] followCats;
	private long[] followTags;

	public long getUid() {
		return this.uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasswd() {
		return this.passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getRefreshtoken() {
		return refreshtoken;
	}

	public void setRefreshtoken(String refreshtoken) {
		this.refreshtoken = refreshtoken;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCredit() {
		return this.credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public int getReputation() {
		return this.reputation;
	}

	public void setReputation(int reputation) {
		this.reputation = reputation;
	}

	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;
	}

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public String getPhoto1() {
		return photo1;
	}

	public void setPhoto1(String photo1) {
		this.photo1 = photo1;
	}

	public String getPhoto2() {
		return photo2;
	}

	public void setPhoto2(String photo2) {
		this.photo2 = photo2;
	}

	public long[] getFollowUsers() {
		return followUsers;
	}

	public void setFollowUsers(long[] followUsers) {
		this.followUsers = followUsers;
	}

	public long[] getFollowIssues() {
		return followIssues;
	}

	public void setFollowIssues(long[] followIssues) {
		this.followIssues = followIssues;
	}

	public long[] getFollowCats() {
		return followCats;
	}

	public void setFollowCats(long[] followCats) {
		this.followCats = followCats;
	}

	public long[] getFollowTags() {
		return followTags;
	}

	public void setFollowTags(long[] followTags) {
		this.followTags = followTags;
	}

	public String toString() {
		return this.name;
	}
}
