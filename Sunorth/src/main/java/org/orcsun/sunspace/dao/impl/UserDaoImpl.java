package org.orcsun.sunspace.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.dao.UserDAO;
import org.orcsun.sunspace.object.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class UserDaoImpl extends SunJdbcDaoSupport implements UserDAO {
	private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
	
	@Override
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
	
	@Override
	public int updateUser(User u) {
		String sql = "update users set name=?,email=?,title=?,profile=?,resume=?,skill=? where uid=?";
		logger.debug(sql);
		Object[] args = new Object[] { u.getName(),u.getEmail(),u.getTitle(),
				u.getProfile(), u.getResume(),u.getSkill(), u.getUid() };
		return this.getJdbcTemplate().update(sql, args);
	}

	@Override
	public int updatePasswd(long uid, String pwd) {
		String sql = "update users set passwd='"+pwd+"' where uid="+uid;
		return this.getJdbcTemplate().update(sql);
	}

	@Override
	public int updateRefreshToken(long uid, String token) {
		String sql = "update users set refreshtoken='"+token+"' where uid="+uid;
		return this.getJdbcTemplate().update(sql);
	}
	public User findUserByID(long uid) {
		String sql = "select uid,openid,name,passwd,email,credit,reputation,regtime,status,title,profile,resume,skill from users where uid='"
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
		String sql = "select uid,openid,name,passwd,email,credit,reputation,regtime,status,title,profile,resume,skill from users where email='"
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
	@Override
	public User findUserByOpenid(String openid) {
		String sql = "select uid,openid,name,passwd,email,credit,reputation,regtime,status,title,profile,resume,skill from users where openid='"
				+ openid + "'";
		logger.info(sql);
		User u = null;
		try {
			u = (User) getJdbcTemplate().queryForObject(sql, new UserMapper());
		} catch (Exception e) {
			logger.warn("No user found :" + openid + ":" + e.getMessage());
		}
		return u;
	}
	public List<User> findUsersBySkill(String skill){
		String sql = "select uid,openid,name,passwd,email,credit,reputation,regtime,status,title,profile,resume,skill from users where skill like '%"
				+ skill + "%'";
		logger.info(sql);
		return this.getJdbcTemplate().query(sql, new UserMapper());
	}
	
	@Override
	public String getRefreshToken(long uid) {
		String sql = "select refreshtoken from users where uid="+uid;
		return this.getJdbcTemplate().queryForObject(sql, String.class);
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
			u.setSkill(rs.getString("skill"));

			u.setResume(rs.getString("resume"));
			return u;
		}
	}

	/**
	
	 * UID BIGINT NOT NULL,
	FID BIGINT NOT NULL,
	LANG VARCHAR(6),
	FTYPE INT, -- 1 USER,2 CAT,3 TAG,4 ISSUE
	ALIAS VARCHAR(50)	
	 */
	@Override
	public int follow(long uid, long fid, int ftype, String lang, String alias) {
		String sql = "insert into follow(uid,fid,ftype,lang,alias) values(?,?,?,?,?) ";
		logger.debug(sql);
		Object[] args = new Object[]{uid,fid,ftype,lang,alias};
		return this.getJdbcTemplate().update(sql, args);
	}

	@Override
	public int unfollow(long uid, long fid, int ftype, String lang) {
		String sql = "delete follow where uid=?,fid=?,ftype=?,alias=?";
		logger.debug(sql);
		Object[] args = new Object[]{uid,fid,ftype,lang};
		return this.getJdbcTemplate().update(sql, args);
	}

	
}
