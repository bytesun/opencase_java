package org.orcsun.sunspace.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.SunConstants;
import org.orcsun.sunspace.dao.AnswerDAO;
import org.orcsun.sunspace.object.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

public class AnswerDaoImpl extends SunJdbcDaoSupport implements AnswerDAO {

	@Autowired
	UserDaoImpl userDao;
	private static final Logger logger = Logger.getLogger(AnswerDaoImpl.class);

	public int addAnswer(Answer a, String lang) {
		String sql = null;
		Object[] args = null;
		sql = "insert into answer_" + lang + "(qid,answer,uid) values(?,?,?)";
		args = new Object[] { Long.valueOf(a.getQid()), a.getAnswer(),
				Long.valueOf(a.getUser().getUid()) };
		logger.info("Add a new answer :" + sql);
		return getJdbcTemplate().update(sql, args);
	}


	public List<Answer> findAnswers(long qid, String lang) {
		String sql = "select aid,qid,answer,atime,rate,uid,isanswer from answer_"
				+ lang + " where qid=" + qid + " order by isanswer,rate desc";
		logger.info(sql);

		return getJdbcTemplate().query(sql, new AnswerMapper(this.userDao));
	}

	public List<Answer> findMyAnswers(long uid, String lang) {
		String sql = "select aid,qid,answer,atime,rate,uid from answer_" + lang
				+ " where uid=" + uid + " order by atime desc";
		logger.info(sql);

		return getJdbcTemplate().query(sql, new AnswerMapper(this.userDao));
	}


	@Override
	public int updateAnswer(Answer a, String lang) {
		String sql = "update answer_" + lang + " set answer='" + a.getAnswer()
				+ "' where aid=" + a.getAid();
		logger.debug(sql);
		return this.getJdbcTemplate().update(sql);
	}
	private static final class AnswerMapper implements RowMapper<Answer> {
		UserDaoImpl userDao;

		private AnswerMapper(UserDaoImpl userDao) {
			this.userDao = userDao;
		}

		public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
			Answer a = new Answer();
			a.setAid(rs.getLong("aid"));
			a.setQid(rs.getLong("qid"));
			a.setAnswer(rs.getString("answer"));
			a.setAtime(rs.getTimestamp("atime"));
			a.setRate(rs.getInt("rate"));
			a.setAnswered(rs.getBoolean("isanswer"));
			a.setUser(this.userDao.findUserByID(rs.getLong("uid")));
			return a;
		}
	}
	@Override
	public int accetpAnswer(long aid, String lang) {
		String sql = "update answer_"+lang+" set isanswer=1 where aid="+aid;
		logger.debug(sql);
		return this.getJdbcTemplate().update(sql);
	}


}
