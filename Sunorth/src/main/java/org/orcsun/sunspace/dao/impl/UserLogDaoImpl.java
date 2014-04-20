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
	public int newLog(UserLog ulog) {
		String sql = "insert into userlog(uid,tag,subject,ulog,status) values(?,?,?,?,?)";
		logger.debug(sql);
		Object[] args=new Object[]{ulog.getUid(),ulog.getTag(),ulog.getSubject(),ulog.getUlog(),ulog.getStatus()};
		return this.getJdbcTemplate().update(sql, args);
	}

	@Override
	public List<UserLog> findMyLogs(long uid,int start,int end) {
		String sql ="select uid,ltime,tag,subject,ulog,status from userlog where uid="+uid+" order by ltime desc limit "+start+","+end;
		logger.debug(sql);
		return this.getJdbcTemplate().query(sql, new UserLogMapper());
	}

	private static final class UserLogMapper implements RowMapper<UserLog>{

		@Override
		public UserLog mapRow(ResultSet rs, int rowNum) throws SQLException {
			UserLog ulog = new UserLog();
			ulog.setUid(rs.getLong("uid"));
			ulog.setLtime(rs.getTimestamp("ltime"));
			ulog.setTag(rs.getString("tag"));
			ulog.setSubject(rs.getString("subject"));
			ulog.setUlog(rs.getString("ulog"));
			ulog.setStatus(rs.getInt("status"));
			return ulog;
		}
		
	}
}