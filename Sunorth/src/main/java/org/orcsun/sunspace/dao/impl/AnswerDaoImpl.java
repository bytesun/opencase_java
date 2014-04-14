/*    */ package org.orcsun.sunspace.dao.impl;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;

import org.apache.log4j.Logger;
/*    */ import org.orcsun.sunspace.dao.AnswerDAO;
/*    */ import org.orcsun.sunspace.object.Answer;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class AnswerDaoImpl extends SunJdbcDaoSupport
/*    */   implements AnswerDAO
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   UserDaoImpl userDao;
/* 29 */   private static final Logger logger = Logger.getLogger(AnswerDaoImpl.class);
/*    */ 
/*    */   public int addAnswer(Answer a, String lang) {
/* 32 */     String sql = null;
/* 33 */     Object[] args = null;
/* 34 */     sql = "insert into answer_" + lang + "(qid,answer,uid) values(?,?,?)";
/* 35 */     args = new Object[] { Long.valueOf(a.getQid()), a.getAnswer(),  Long.valueOf(a.getUser().getUid()) };
/* 36 */     logger.info("Add a new answer :" + sql);
/* 37 */     return getJdbcTemplate().update(sql, args);
/*    */   }
/*    */ 
/*    */   public int delAnswer(long aid,String lang)
/*    */   {
/* 42 */     String sql = "delete from answer_" + lang + " where aid="+aid;
/* 43 */     logger.info(sql);
/* 46 */     return getJdbcTemplate().update(sql);
/*    */   }
/*    */ 
/*    */   public List<Answer> findAnswers(long qid, String lang)
/*    */   {
/* 51 */     String sql = "select aid,qid,answer,atime,rate,uid,isanswer from answer_" + lang + " where qid=" + qid+" order by isanswer,rate desc";
/* 52 */     logger.info(sql);
/*    */ 
/* 54 */     return getJdbcTemplate().query(sql, new AnswerMapper(this.userDao));
/*    */   }
/*    */ 
/*    */   public List<Answer> findMyAnswers(long uid, String lang)
/*    */   {
/* 59 */     String sql = "select aid,qid,answer,atime,rate,uid from answer_" + lang + " where uid=" + uid + " order by atime desc";
/* 60 */     logger.info(sql);
/*    */ 
/* 62 */     return getJdbcTemplate().query(sql, new AnswerMapper(this.userDao));
/*    */   }
/*    */ 
/*    */   public int voteAnswer(long aid, int vote,int isanswer, String vcomment, long uid, String lang) {
/* 66 */     String sql = "update answer_" + lang + " set rate=(rate+" + vote + "),isanswer="+isanswer+" where aid=" + aid;
/* 67 */     logger.info(sql);
/* 68 */     getJdbcTemplate().update(sql);
/* 69 */     sql = "insert into vote_" + lang + "(qaid,vtype,uid,comment) values (?,?,?,?)";
			logger.info(sql);
/* 70 */     return getJdbcTemplate().update(sql, new Object[] { Long.valueOf(aid), Integer.valueOf(vote), Long.valueOf(uid), vcomment });
/*    */   }
/*    */ 
/*    */   private static final class AnswerMapper implements RowMapper<Answer>
/*    */   {
/*    */     UserDaoImpl userDao;
/*    */ 
/*    */     private AnswerMapper(UserDaoImpl userDao) {
/* 78 */       this.userDao = userDao;
/*    */     }
/*    */ 
/*    */     public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 82 */       Answer a = new Answer();
/* 83 */       a.setAid(rs.getLong("aid"));
/* 84 */       a.setQid(rs.getLong("qid"));
/* 85 */       a.setAnswer(rs.getString("answer"));
/* 86 */       a.setAtime(rs.getTimestamp("atime"));
/* 87 */       a.setRate(rs.getInt("rate"));
			   a.setAnswered(rs.getBoolean("isanswer"));
/* 88 */       a.setUser(this.userDao.findUserByID(rs.getLong("uid")));
/* 89 */       return a;
/*    */     }
/*    */   }
/*    */
@Override
public int delAnswer(long paramLong1, long paramLong2, long paramLong3,
		String paramString) {
	// TODO Auto-generated method stub
	return 0;
} }

