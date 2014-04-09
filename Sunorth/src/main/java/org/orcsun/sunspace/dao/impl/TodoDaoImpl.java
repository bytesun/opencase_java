/*    */ package org.orcsun.sunspace.dao.impl;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.orcsun.sunspace.dao.TodoDAO;
/*    */ import org.orcsun.sunspace.object.Todo;
/*    */ import org.orcsun.sunspace.object.User;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class TodoDaoImpl extends SunJdbcDaoSupport
/*    */   implements TodoDAO
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   UserDaoImpl userDao;
/*    */ 
/*    */   public int addTodo(Todo td)
/*    */   {
/* 30 */     String sql = "insert into todo(todo,note,ttype,uid,priority,deadline) values(?,?,?,?,?,?)";
/* 31 */     this.logger.info(sql);
/*    */ 
/* 33 */     Object[] args = { td.getTodo(), td.getNote(), Short.valueOf(td.getTtype()), Long.valueOf(td.getUser().getUid()), Short.valueOf(td.getPriority()), td.getDeadline() };
/* 34 */     return getJdbcTemplate().update(sql, args);
/*    */   }
/*    */ 
/*    */   public int delTodo(long tid)
/*    */   {
/* 39 */     return getJdbcTemplate().update("delete from todo where tid=" + tid);
/*    */   }
/*    */ 
/*    */   public int updateTodo(Todo todo)
/*    */   {
/* 44 */     String sql = "update todo set todo=?, note=?, priority=?,deadline=?,status=? where tid=?";
/* 45 */     Object[] args = { todo.getTodo(), todo.getNote(), Short.valueOf(todo.getPriority()), todo.getDeadline(), todo.isDone(), Long.valueOf(todo.getTid()) };
/* 46 */     return getJdbcTemplate().update(sql, args);
/*    */   }
/*    */ 
/*    */   public Todo getTodo(long tid)
/*    */   {
/* 51 */     String sql = "select tid,todo,note,ttime,ttype,uid,priority,deadline,status from todo where tid=" + tid;
/* 52 */     return (Todo)getJdbcTemplate().queryForObject(sql, new TodoMapper(null));
/*    */   }
/*    */ 
/*    */   public List<Todo> findTodayTodos(long uid)
/*    */   {
/* 57 */     String sql = "select tid,todo,note,ttime,ttype,priority,deadline,status from todo where uid=" + uid + " and status<>9 and  CURRENT_TIMESTAMP >= deadline order by priority";
/* 58 */     this.logger.info(sql);
/* 59 */     return getJdbcTemplate().query(sql, new TodoMapper(null));
/*    */   }
/*    */ 
/*    */   public int doneTodo(Object[] tids)
/*    */   {
/* 87 */     StringBuffer sb = new StringBuffer();
/* 88 */     for (Object tid : tids)
/* 89 */       sb.append(tid + ",");
/* 90 */     sb.append("0");
/* 91 */     this.logger.info("Done all these todos : " + sb);
/* 92 */     return getJdbcTemplate().update("update todo set status=9,deadline=CURRENT_TIMESTAMP where tid in (" + sb.toString() + ")");
/*    */   }
/*    */ 
/*    */   public static final class TodoMapper
/*    */     implements RowMapper<Todo>
/*    */   {
/*    */     UserDaoImpl userDao;
/*    */ 
/*    */     public TodoMapper(UserDaoImpl userDao)
/*    */     {
/* 66 */       this.userDao = userDao;
/*    */     }
/*    */ 
/*    */     public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
/* 70 */       Todo td = new Todo();
/* 71 */       td.setTid(rs.getLong("tid"));
/* 72 */       td.setTodo(rs.getString("todo"));
/* 73 */       td.setNote(rs.getString("note"));
/* 74 */       td.setPriority(rs.getShort("priority"));
/* 75 */       td.setTtime(rs.getTimestamp("ttime"));
/* 76 */       td.setTtype(rs.getShort("ttype"));
/* 77 */       td.setDone(rs.getBoolean("status"));
/* 78 */       if (this.userDao != null)
/* 79 */         td.setUser(this.userDao.findUserByID(rs.getLong("uid")));
/* 80 */       td.setDeadline(rs.getTimestamp("deadline"));
/* 81 */       return td;
/*    */     }
/*    */   }
/*    */ }

/* Location:           E:\2014097ois-Sunorth-1.1.6\WEB-INF\classes\
 * Qualified Name:     org.orcsun.sunspace.dao.impl.TodoDaoImpl
 * JD-Core Version:    0.6.2
 */