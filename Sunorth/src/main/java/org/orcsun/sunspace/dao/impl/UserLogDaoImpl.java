package org.orcsun.sunspace.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.HomeController;
import org.orcsun.sunspace.dao.UserLogDAO;
import org.orcsun.sunspace.object.UserLog;
import org.springframework.jdbc.core.RowMapper;

/**
 * 	UID BIGINT DEFAULT 0 NOT NULL,
	LTIME TIMESTAMP DEFAULT 'CURRENT_TIMESTAMP' NOT NULL,
	TAG VARCHAR(100),
	SUBJECT VARCHAR(1000) NOT NULL,
	ULOG MEDIUMTEXT NOT NULL
 * @author sunfeng
 *
 */
public class UserLogDaoImpl extends SunJdbcDaoSupport implements UserLogDAO {

	static Logger logger = Logger.getLogger(UserLogDaoImpl.class);
	@Override
	public int newLog(UserLog ulog,String lang) {
		String sql = "insert into userlog_"+lang+" (uid,tag,subject,ulog,status,ltype) values(?,?,?,?,?,?)";
		logger.debug(sql);
		Object[] args=new Object[]{ulog.getUid(),ulog.getTag(),ulog.getSubject(),ulog.getUlog(),ulog.getStatus(),ulog.getLtype()};
		return this.getJdbcTemplate().update(sql, args);
	}
	
	@Override
	public int updateLog(UserLog ulog,String lang) {
		String sql ="update userlog_"+lang+"  set subject=?,ulog=?,tag=?,status=?,ltype=? where lid=?";
		logger.debug(sql);
		Object[] args = new Object[]{ulog.getSubject(),ulog.getUlog(),ulog.getTag(),ulog.getStatus(),ulog.getLtype(),ulog.getLid()};
		return this.getJdbcTemplate().update(sql,args);
	}

	@Override
	public List<UserLog> findMyLogs(long uid,int start,int end,String lang) {
		String sql ="select lid,uid,ltime,tag,subject,ulog,status,ltype from userlog_"+lang+" where uid="+uid+" order by ltime desc limit "+start+","+end;
		logger.debug(sql);
		return this.getJdbcTemplate().query(sql, new UserLogMapper());
	}


	@Override
	public List<UserLog> lastestLogs(String lang) {
		String sql ="select lid,uid,ltime,tag,subject,ulog,status,ltype from userlog_"+lang+"  where status>0 order by ltime desc limit 5";
		logger.debug(sql);
		return this.getJdbcTemplate().query(sql, new UserLogMapper());
	}	
	private static final class UserLogMapper implements RowMapper<UserLog>{

		@Override
		public UserLog mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserLog ulog = new UserLog();
			ulog.setLid(rs.getLong("lid"));
			ulog.setUid(rs.getLong("uid"));
			ulog.setLtime(rs.getTimestamp("ltime"));
			ulog.setTag(rs.getString("tag"));
			ulog.setSubject(rs.getString("subject"));
			ulog.setUlog(rs.getString("ulog"));
			ulog.setStatus(rs.getInt("status"));
			ulog.setLtype(rs.getInt("ltype"));
			return ulog;
		}
		
	}




}
