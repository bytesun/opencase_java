/*     */ package org.orcsun.sunspace.dao.impl;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.List;
/*     */ import org.orcsun.sunspace.dao.QuestionDAO;
/*     */ import org.orcsun.sunspace.object.Comment;
/*     */ import org.orcsun.sunspace.object.Question;
/*     */ import org.orcsun.sunspace.object.User;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.dao.EmptyResultDataAccessException;
/*     */ import org.springframework.jdbc.core.JdbcTemplate;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ 
/*     */ public class QuestionDaoImpl extends SunJdbcDaoSupport
/*     */   implements QuestionDAO
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   UserDaoImpl userDao;
/*  35 */   private static final Logger logger = LoggerFactory.getLogger(QuestionDaoImpl.class);
/*     */ 
/*     */   public int addQuestion(Question q, String lang) {
/*  38 */     String sql = null;
/*  39 */     Object[] args = null;
/*  40 */     sql = "insert into question_" + lang + "(pid,cid,question,tag,answercnt,uid,description) values(?,?,?,?,?,?,?)";
/*  41 */     args = new Object[] { Long.valueOf(q.getPid()), Long.valueOf(q.getCid()), q.getQuestion(), q.getTag(), Integer.valueOf(q.getAnswercnt()), Long.valueOf(q.getUser().getUid()), q.getDescription()};
/*  42 */     logger.info("Add a new question :" + sql);
/*  43 */     return getJdbcTemplate().update(sql, args);
/*     */   }
/*     */ 
/*     */   public int updateQuestion(Question q, String lang)
/*     */   {
/*  48 */     String sql = null;
/*  49 */     Object[] args = null;
/*  50 */     sql = "update question_" + lang + " set pid=?,cid=?,question=?,rate=?,tag=?,answercnt=?,uid=?,description=?,status=? where qid=?";
/*  51 */     args = new Object[] { Long.valueOf(q.getPid()), Long.valueOf(q.getCid()), q.getQuestion(), Integer.valueOf(q.getRate()), q.getTag(), Integer.valueOf(q.getAnswercnt()), Long.valueOf(q.getUser().getUid()), q.getDescription(), q.isResolved(), Long.valueOf(q.getQid()) };
/*  52 */     logger.info("Update node :" + sql);
/*  53 */     return getJdbcTemplate().update(sql, args);
/*     */   }
/*     */ 
/*     */   public int deleteQuestion(long id, String lang)
/*     */   {
/*  58 */     String sql = "delete from question_" + lang + " where qid=" + id;
/*  59 */     logger.info("Delete a question :" + sql);
/*  60 */     return getJdbcTemplate().update(sql);
/*     */   }
/*     */ 
/*     */   public List<Question> findQuestionsByPID(long pid, String lang)
/*     */   {
/*  65 */     String sql = "select qid,pid,cid,question,tag,rate,answercnt,uid,description,qtime,status from question_" + lang + " where pid=" + pid + " order by rate desc";
/*  66 */     List ns = getJdbcTemplate().query(sql, new QuestionMapper(this.userDao));
/*  67 */     return ns;
/*     */   }
/*     */ 
/*     */   public Question getQuestion(long id, String lang)
/*     */   {
/*  72 */     String sql = "select qid,pid,cid,question,tag,rate,answercnt,uid,description,qtime,status from question_" + lang + " where qid=" + id;
/*  73 */     logger.info("Retrieve a question : " + sql);
/*  74 */     Question n = null;
/*     */     try {
/*  76 */       n = (Question)getJdbcTemplate().queryForObject(sql, new QuestionMapper(this.userDao));
/*     */     } catch (EmptyResultDataAccessException e) {
/*  78 */       logger.warn("No data for " + sql + " :" + e.getMessage());
/*     */     }
/*  80 */     return n;
/*     */   }
/*     */ 
/*     */   public List<Question> findQuestionsByCID(long cid, String lang)
/*     */   {
/* 111 */     String sql = "select qid,pid,cid,question,tag,rate,answercnt,uid,description,qtime,status from question_" + lang + " where cid=" + cid + " order by rate desc limit 10";
/* 112 */     logger.info(sql);
/* 113 */     return getJdbcTemplate().query(sql, new QuestionMapper(this.userDao));
/*     */   }
/*     */ 
/*     */   public List<Question> findNewQuestions(int cnt, String lang)
/*     */   {
/* 118 */     String sql = "select qid,pid,cid,question,tag,rate,answercnt,uid,description,qtime,status from question_" + lang + " where status=0 order by qtime,rate desc limit " + cnt;
/* 119 */     logger.info(sql);
/* 120 */     return getJdbcTemplate().query(sql, new QuestionMapper(this.userDao));
/*     */   }
/*     */ 
/*     */   public List<Question> search(String keys, String lang)
/*     */   {
/* 125 */     String sql = "select qid,pid,cid,question,tag,rate,answercnt,uid,description,qtime,status from question_" + lang + " where question like '%" + keys + "%' or tag like '%" + keys + "%'";
/* 126 */     logger.info(sql);
/*     */ 
/* 128 */     return getJdbcTemplate().query(sql, new QuestionMapper(this.userDao));
/*     */   }
/*     */ 
/*     */   public List<Question> findMyQuestions(long uid, String lang)
/*     */   {
/* 133 */     String sql = "select qid,pid,cid,question,tag,rate,answercnt,uid,description,qtime,status from question_" + lang + 
/* 134 */       " where uid=" + uid + " order by qtime desc";
/* 135 */     logger.info(sql);
/*     */ 
/* 137 */     return getJdbcTemplate().query(sql, new QuestionMapper(this.userDao));
/*     */   }
/*     */ 
/*     */   public int addComment(Comment c, String lang) {
/* 141 */     String sql = "insert into comment_" + lang + "(qid,comment,uid) values(?,?,?)";
/* 142 */     logger.debug(sql);
/* 143 */     return getJdbcTemplate().update(sql, new Object[] { Long.valueOf(c.getQid()), c.getComment(), Long.valueOf(c.getUser().getUid()) });
/*     */   }
/*     */ 
/*     */   public int vote(long qid, int vote, long uid, String lang)
/*     */   {
/* 149 */     String sql = "update question_" + lang + " set rate=(rate+" + vote + ") where qid=" + qid + " and qid not in (select qaid from vote where uid=" + uid + " and vtype=1)";
/* 150 */     logger.debug(sql);
/* 151 */     getJdbcTemplate().update(sql);
/* 152 */     sql = "insert into vote(qaid,vtype,uid) values (?,?,?)";
/* 153 */     return getJdbcTemplate().update(sql, new Object[] { Long.valueOf(qid), Integer.valueOf(1), Long.valueOf(uid) });
/*     */   }
/*     */ 
/*     */   public List<Comment> findComments(long qid, String lang)
/*     */   {
/* 158 */     String sql = "select qid,ctime,comment,uid from comment_" + lang + " where qid=" + qid;
/* 159 */     logger.debug(sql);
/* 160 */     return getJdbcTemplate().query(sql, new CommentMapper(this.userDao));
/*     */   }
/*     */ 
/*     */   private static final class CommentMapper implements RowMapper<Comment> {
/*     */     UserDaoImpl userDao;
/*     */ 
/*     */     private CommentMapper(UserDaoImpl userDao) {
/* 167 */       this.userDao = userDao;
/*     */     }
/*     */ 
/*     */     public Comment mapRow(ResultSet rs, int rowNum) throws SQLException
/*     */     {
/* 172 */       Comment c = new Comment();
/* 173 */       c.setQid(rs.getLong("qid"));
/* 174 */       c.setCtime(rs.getTimestamp("ctime"));
/* 175 */       c.setComment(rs.getString("comment"));
/* 176 */       c.setUser(this.userDao.findUserByID(rs.getLong("uid")));
/*     */ 
/* 178 */       return c;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static final class QuestionMapper
/*     */     implements RowMapper<Question>
/*     */   {
/*     */     UserDaoImpl userDao;
/*     */ 
/*     */     private QuestionMapper(UserDaoImpl userDao)
/*     */     {
/*  87 */       this.userDao = userDao;
/*     */     }
/*     */ 
/*     */     public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
/*  91 */       Question q = new Question();
/*  92 */       q.setQid(rs.getLong("qid"));
/*  93 */       q.setPid(rs.getLong("pid"));
/*  94 */       q.setCid(rs.getLong("cid"));
/*  95 */       q.setQuestion(rs.getString("question"));
/*  96 */       q.setTag(rs.getString("tag"));
/*     */ 
/*  98 */       q.setRate(rs.getInt("rate"));
/*  99 */       q.setAnswercnt(rs.getInt("answercnt"));
/* 100 */       q.setUser(this.userDao.findUserByID(rs.getLong("uid")));
/* 101 */       q.setQtime(rs.getTimestamp("qtime"));
/* 102 */       q.setDescription(rs.getString("description"));
/* 103 */       q.setResolved(rs.getBoolean("status"));
/* 104 */       return q;
/*     */     }
/*     */   }
/*     */
@Override
public int addAnswerCnt(long qid,int cnt,String lang) {
	String sql ="update question_"+lang+" set answercnt=(answercnt+"+cnt+") where qid="+qid;
	logger.info(sql);
	return this.getJdbcTemplate().update(sql);
}
@Override
public int resolveQuestion(long qid,String lang) {
	String sql = "update question_"+lang+" set status=1 where qid="+qid;
	logger.info(sql);
	return this.getJdbcTemplate().update(sql);
} }
