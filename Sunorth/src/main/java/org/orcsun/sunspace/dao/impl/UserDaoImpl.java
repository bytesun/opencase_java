package org.orcsun.sunspace.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.dao.UserDAO;
import org.orcsun.sunspace.object.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class UserDaoImpl extends SunJdbcDaoSupport implements UserDAO {
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);

	public long addUser(User u) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
		jdbcInsert.withTableName("users").usingGeneratedKeyColumns(
				new String[] { "uid" });
		Map parameters = new HashMap();
		parameters.put("openid", u.getOpenid());
		parameters.put("name", u.getName());
		parameters.put("passwd", u.getPasswd());
		parameters.put("email", u.getEmail());
		parameters.put("credit", u.getCredit());
		parameters.put("reputation", u.getReputation());

		parameters.put("regtime", new Date());
		parameters.put("status", u.getStatus());

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
				parameters));
		return key.intValue();
	}

	public int updateUser(User u) {
		String sql = "update users set name=?,title=?,profile=?,resume=? where uid=?";
		logger.debug(sql);
		Object[] args = new Object[] { u.getName(), u.getTitle(),
				u.getProfile(), u.getResume(), u.getUid() };
		return this.getJdbcTemplate().update(sql, args);
	}

	public User findUserByID(long uid) {
		String sql = "select uid,openid,name,passwd,email,credit,reputation,regtime,status,title,profile,resume from users where uid='"
				+ uid + "'";
		logger.info(sql);
		User u = null;
		try {
			u = (User) getJdbcTemplate().queryForObject(sql, new UserMapper());
		} catch (Exception e) {
			logger.warn("No user found :" + uid + ":" + e.getMessage());
		}
		return u;
	}

	public User findUserByEmail(String email) {
		String sql = "select uid,openid,name,passwd,email,credit,reputation,regtime,status,title,profile,resume from users where email='"
				+ email + "'";
		logger.info(sql);
		User u = null;
		try {
			u = (User) getJdbcTemplate().queryForObject(sql, new UserMapper());
		} catch (Exception e) {
			logger.warn("No user found :" + email + ":" + e.getMessage());
		}
		return u;
	}

	private static final class UserMapper implements RowMapper<User> {
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User u = new User();
			u.setUid(rs.getLong("uid"));
			u.setOpenid(rs.getString("openid"));
			u.setName(rs.getString("name"));
			u.setPasswd(rs.getString("passwd"));
			u.setEmail(rs.getString("email"));
			u.setCredit(rs.getInt("credit"));
			u.setReputation(rs.getInt("reputation"));
			u.setRegtime(rs.getTimestamp("regtime"));
			u.setStatus(rs.getInt("status"));
			u.setTitle(rs.getString("title"));
			u.setProfile(rs.getString("profile"));
			u.setResume(rs.getString("resume"));
			return u;
		}
	}
}
