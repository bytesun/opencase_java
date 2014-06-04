package org.orcsun.sunspace.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.dao.ActivityDAO;
import org.orcsun.sunspace.object.Activity;
import org.orcsun.sunspace.object.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class ActivityDaoImpl extends SunJdbcDaoSupport implements ActivityDAO {

	@Autowired 
	UserDaoImpl userDao;
	
	static Logger logger = Logger.getLogger(ActivityDaoImpl.class);
	
	/**
	 * new Activity
	 */
	@Override
	public int addActivity(Activity activity,String lang) {
		
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
		jdbcInsert.withTableName("activity_"+lang).usingGeneratedKeyColumns(
				new String[] { "aid" });
		Map parameters = new HashMap();
		parameters.put("subject", activity.getSubject());
		parameters.put("description", activity.getDescription());
		parameters.put("location", activity.getLocation());
		parameters.put("atype", activity.getAtype());
		parameters.put("astime", activity.getAstime());
		parameters.put("aetime", activity.getAetime());

		parameters.put("attcnt",activity.getAttcnt());
		parameters.put("cost", activity.getCost());
		parameters.put("uid", activity.getOrganizer().getUid());

		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
				parameters));
		return key.intValue();
		
//		String sql = "insert into activity_"+lang+"(subject,description,location,atype,astime,aetime,attcnt,cost,uid) values(?,?,?,?,?,?,?,?,?)";
//		logger.debug(sql);
//		Object[] args = new Object[]{activity.getSubject(),activity.getDescription(),activity.getLocation(),
//				activity.getAtype(),activity.getAstime(),activity.getAetime(),
//				activity.getAttcnt(),activity.getCost(),activity.getUid()};
//		return this.getJdbcTemplate().update(sql, args);
	}

	/**
	 * Get a specific activity
	 */
	@Override
	public Activity getActivity(long aid, String lang) {
		String sql ="select aid,subject,description,location,atype,astime,aetime,attcnt,cost,uid from activity_"+lang+" where aid="+aid;
		logger.debug(sql);
		return this.getJdbcTemplate().queryForObject(sql, new ActivityMapper(userDao));
	}

	/**
	 * find the latest activity
	 */
	@Override
	public List<Activity> getLatestActivities(int cnt, String lang) {
		String sql ="select aid,subject,description,location,atype,astime,aetime,attcnt,cost,uid from activity_"+lang+" where CURRENT_TIMESTAMP<=aetime  order by astime  limit "+cnt;
		logger.debug(sql);
		return this.getJdbcTemplate().query(sql, new ActivityMapper(null));
	}

	@Override
	public List<Activity> getMyActivities(long uid, String lang) {
		String sql ="select aid,subject,description,location,atype,astime,aetime,attcnt,cost,uid from activity_"+lang+" where uid="+uid+" and CURRENT_TIMESTAMP<=aetime order by astime  ";
		logger.debug(sql);
		return this.getJdbcTemplate().query(sql, new ActivityMapper(null));
	}
	
	public static final class ActivityMapper implements RowMapper<Activity>{

		UserDaoImpl userDao;
		public ActivityMapper(UserDaoImpl userDao){
			this.userDao=userDao;
		}
		@Override
		public Activity mapRow(ResultSet rs, int rowNum) throws SQLException {
			Activity activity = new Activity();
			activity.setAid(rs.getLong("aid"));
			activity.setSubject(rs.getString("subject"));
			activity.setDescription(rs.getString("description"));
			activity.setLocation(rs.getString("location"));
			activity.setAtype(rs.getInt("atype"));
			activity.setAstime(rs.getTimestamp("astime"));
			activity.setAetime(rs.getTimestamp("aetime"));
			activity.setAttcnt(rs.getInt("attcnt"));
			activity.setCost(rs.getDouble("cost"));
			long uid=rs.getLong("uid");
			User organizer = new User();
			if(userDao == null)
				organizer.setUid(uid);
			else
				organizer = userDao.findUserByID(uid);
			activity.setOrganizer(organizer);
			
			return activity;
		}
		
	}

}
