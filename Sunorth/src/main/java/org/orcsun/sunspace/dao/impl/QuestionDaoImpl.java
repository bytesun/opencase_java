package org.orcsun.sunspace.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.SunConstants;
import org.orcsun.sunspace.dao.QuestionDAO;
import org.orcsun.sunspace.object.Comment;
import org.orcsun.sunspace.object.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;

public class QuestionDaoImpl extends SunJdbcDaoSupport implements QuestionDAO {

	@Autowired
	UserDaoImpl userDao;
	private static final Logger logger = Logger
			.getLogger(QuestionDaoImpl.class);

	public int addQuestion(Question q, String lang) {
		String sql = null;
		Object[] args = null;
		sql = "insert into question_"
				+ lang
				+ "(pid,cid,question,tag,answercnt,uid,description) values(?,?,?,?,?,?,?)";
		args = new Object[] { Long.valueOf(q.getPid()),
				Long.valueOf(q.getCid()), q.getQuestion(), q.getTag(),
				Integer.valueOf(q.getAnswercnt()),
				Long.valueOf(q.getUser().getUid()), q.getDescription() };
		logger.info("Add a new question :" + sql);
		int retn= getJdbcTemplate().update(sql, args);
		
		String[] tags = q.getTag().split(" ");
		if(tags!=null && tags.length>0){
			int iupdate = this.addTags(tags, lang,false);
			logger.debug("insert/update "+iupdate+" tags");
		}
		return retn;
	}

	private int addTags(String[] tags,String lang,boolean isUpdated){
		String[] sqls=new String[tags.length];
		for(int i=0;i<tags.length;i++){
			String tag = tags[i];
			int cnt = this.getJdbcTemplate().queryForInt("select count(*) from tag_"+lang+" where name='"+tag+"'");
			if(cnt == 0){
				sqls[i] = "insert into tag_"+lang+ "(name,cnt_use) values('"+tag+"',1)";
			}else if(!isUpdated){
				sqls[i]="update tag_"+lang+" set cnt_use=(cnt_use+1) where name='"+tag+"'";
			}
			
		}
		return this.getJdbcTemplate().batchUpdate(sqls).length;
	}
	
//	private int updateTags(String[] tags,String lang){
//		
//	}
	
	public int updateQuestion(Question q, String lang) {
		String sql = null;
		Object[] args = null;
		sql = "update question_" + lang
				+ " set question=?,tag=?,description=? where qid=?";
		args = new Object[] { q.getQuestion(), q.getTag(), q.getDescription(),
				Long.valueOf(q.getQid()) };
		String[] tags = q.getTag().split(" ");
		if(tags!=null && tags.length>0){
			int iupdate = this.addTags(tags, lang,true);
			logger.debug("insert/update "+iupdate+" tags");
		}
		logger.info("Update node :" + sql);
		return getJdbcTemplate().update(sql, args);
	}

	public int deleteQuestion(long id, String lang) {
		String sql = "delete from question_" + lang + " where qid=" + id;
		logger.info("Delete a question :" + sql);
		return getJdbcTemplate().update(sql);
	}

	public List<Question> findQuestionsByPID(long pid, String lang,int start,int end) {
		String sql = "select qid,pid,cid,question,tag,rate,answercnt,uid,description,qtime,status from question_"
				+ lang + " where pid=" + pid + " order by rate desc limit " + start + "," + end;

		return getJdbcTemplate()
				.query(sql, new QuestionMapper(this.userDao));
	}

	public Question getQuestion(long id, String lang) {
		String sql = "select qid,pid,cid,question,tag,rate,answercnt,uid,description,qtime,status from question_"
				+ lang + " where qid=" + id;
		logger.info("Retrieve a question : " + sql);
		Question n = null;
		try {
			n = (Question) getJdbcTemplate().queryForObject(sql,
					new QuestionMapper(this.userDao));
		} catch (EmptyResultDataAccessException e) {
			logger.warn("No data for " + sql + " :" + e.getMessage());
		}
		return n;
	}

	public List<Question> findQuestionsByCID(long cid, String lang,int start,int end) {
		String sql = "select qid,pid,cid,question,tag,rate,answercnt,uid,description,qtime,status from question_"
				+ lang + " where cid=" + cid + " order by rate desc limit " + start + "," + end;
		logger.info(sql);
		return getJdbcTemplate().query(sql, new QuestionMapper(this.userDao));
	}

	public List<Question> findLatestQuestions(int start, int end, String lang) {
		String sql = "select qid,pid,cid,question,tag,rate,answercnt,uid,description,qtime,status from question_"
				+ lang + " where status=0 order by qtime desc limit " + start + "," + end;
		logger.info(sql);
		return getJdbcTemplate().query(sql, new QuestionMapper(this.userDao));
	}

	public List<Question> search(String keys, String lang,int start,int end) {
		String sql = "select qid,pid,cid,question,tag,rate,answercnt,uid,description,qtime,status from question_"
				+ lang
				+ " where question like '%"
				+ keys
				+ "%' or tag like '%"
				+ keys + "%' order by rate desc limit "+start+","+end;
		logger.info(sql);

		return getJdbcTemplate().query(sql, new QuestionMapper(this.userDao));
	}
	
	/**
	 * My unsolved issues
	 */
	public List<Question> findMyQuestions(String lang,long uid) {
		String sql = "select qid,pid,cid,question,tag,rate,answercnt,uid,description,qtime,status from question_"+lang
				+ " where uid=" + uid + " and status=0 order by qtime desc ";
		logger.info(sql);

		return  getJdbcTemplate().query(sql, new QuestionMapper(this.userDao));
	}

	public int addComment(Comment c, String lang) {
		String sql = "insert into comment_" + lang
				+ "(qid,comment,uid) values(?,?,?)";
		logger.debug(sql);
		return getJdbcTemplate().update(
				sql,
				new Object[] { Long.valueOf(c.getQid()), c.getComment(),
						Long.valueOf(c.getUser().getUid()) });
	}

//	public int vote(long qid, int vote, long uid, String lang) {
//		String sql = "update question_" + lang + " set rate=(rate+" + vote
//				+ ") where qid=" + qid
//				+ " and qid not in (select qaid from vote where uid=" + uid
//				+ " and vtype=1)";
//		logger.debug(sql);
//		getJdbcTemplate().update(sql);
//		sql = "insert into vote(qaid,vtype,uid) values (?,?,?)";
//		return getJdbcTemplate().update(
//				sql,
//				new Object[] { Long.valueOf(qid), Integer.valueOf(1),
//						Long.valueOf(uid) });
//	}

	public List<Comment> findComments(long qid, String lang,int start,int end) {
		String sql = "select qid,ctime,comment,uid from comment_" + lang
				+ " where qid=" + qid+" limit "+  start + "," + end;
		logger.debug(sql);
		return getJdbcTemplate().query(sql, new CommentMapper(this.userDao));
	}

	private static final class CommentMapper implements RowMapper<Comment> {
		UserDaoImpl userDao;

		private CommentMapper(UserDaoImpl userDao) {
			this.userDao = userDao;
		}

		public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
			Comment c = new Comment();
			c.setQid(rs.getLong("qid"));
			c.setCtime(rs.getTimestamp("ctime"));
			c.setComment(rs.getString("comment"));
			c.setUser(this.userDao.findUserByID(rs.getLong("uid")));

			return c;
		}
	}

	private static final class QuestionMapper implements RowMapper<Question> {
		UserDaoImpl userDao;

		private QuestionMapper(UserDaoImpl userDao) {
			this.userDao = userDao;
		}

		public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
			Question q = new Question();
			q.setQid(rs.getLong("qid"));
			q.setPid(rs.getLong("pid"));
			q.setCid(rs.getLong("cid"));
			q.setQuestion(rs.getString("question"));
			q.setTag(rs.getString("tag"));

			q.setRate(rs.getInt("rate"));
			q.setAnswercnt(rs.getInt("answercnt"));
			q.setUser(this.userDao.findUserByID(rs.getLong("uid")));
			q.setQtime(rs.getTimestamp("qtime"));
			q.setDescription(rs.getString("description"));
			q.setResolved(rs.getBoolean("status"));
			return q;
		}
	}

	@Override
	public int addAnswerCnt(long qid, int cnt, String lang) {
		String sql = "update question_" + lang + " set answercnt=(answercnt+"
				+ cnt + ") where qid=" + qid;
		logger.info(sql);
		return this.getJdbcTemplate().update(sql);
	}

	@Override
	public int updateIssueStatus(long qid, String lang,int status) {
		String sql = "update question_" + lang + " set status="+status+" where qid="
				+ qid;
		logger.info(sql);
		return this.getJdbcTemplate().update(sql);
	}

	@Override
	public int vote(long uid,long qid, String lang, int vote) {
		String svote = "+1";
		if(vote==-1)svote="-1";
		
		String sql = "insert into vote(uid,nvote,vtype,vid) values(?,?,?,?)"; 
		Object[] args = new Object[]{uid,svote,SunConstants.TYPE_QUESTION,qid};		
		try{
			this.getJdbcTemplate().update(sql, args);
		}catch(Exception e){
			logger.error(e.getMessage());
			return -1;
		}
		sql = "update question_"+lang+" set rate=(rate"+svote+") where qid="+qid;
		
		return this.getJdbcTemplate().update(sql);
	}
}
