package org.orcsun.sunspace.dao;

import java.util.List;

import org.orcsun.sunspace.object.UserLog;

public interface UserLogDAO {
	public int newLog(UserLog ulog);
	public List<UserLog> findMyLogs(long uid,int start,int end);
		
}
