package org.orcsun.sunspace.dao;

import java.util.List;

import org.orcsun.sunspace.object.UserLog;

public interface UserLogDAO {
	public int newLog(UserLog ulog,String lang);
	public int updateLog(UserLog ulog,String lang);
	public List<UserLog> findMyLogs(long uid,int start,int end,String lang);
	public List<UserLog> lastestLogs(String lang);
		
}
