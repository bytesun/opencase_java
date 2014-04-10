package org.orcsun.sunspace.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.dao.TodoDAO;
import org.orcsun.sunspace.object.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;

public class TodoDaoImpl extends SunJdbcDaoSupport implements TodoDAO {

	@Autowired
	UserDaoImpl userDao;
	static Logger logger = Logger.getLogger(TodoDaoImpl.class);

	public int addTodo(Todo td) {
		String sql = "insert into todo(todo,note,ttype,uid,priority,deadline) values(?,?,?,?,?,?)";

		Object[] args = { td.getTodo(), td.getNote(),
				Short.valueOf(td.getTtype()),
				Long.valueOf(td.getUser().getUid()),
				Short.valueOf(td.getPriority()), td.getDeadline() };
		return getJdbcTemplate().update(sql, args);
	}

	public int delTodo(long tid) {
		return getJdbcTemplate().update("delete from todo where tid=" + tid);
	}

	public int updateTodo(Todo todo) {
		String sql = "update todo set todo=?, note=?, priority=?,deadline=?,status=? where tid=?";
		Object[] args = { todo.getTodo(), todo.getNote(),
				Short.valueOf(todo.getPriority()), todo.getDeadline(),
				todo.isDone(), Long.valueOf(todo.getTid()) };
		return getJdbcTemplate().update(sql, args);
	}

	public Todo getTodo(long tid) {
		String sql = "select tid,todo,note,ttime,ttype,uid,priority,deadline,status from todo where tid="
				+ tid;
		return (Todo) getJdbcTemplate().queryForObject(sql,
				new TodoMapper(null));
	}

	public List<Todo> findTodayTodos(long uid) {
		String sql = "select tid,todo,note,ttime,ttype,priority,deadline,status from todo where uid="
				+ uid
				+ " and status<>9 and  CURRENT_TIMESTAMP >= deadline order by priority";
		logger.info(sql);
		return getJdbcTemplate().query(sql, new TodoMapper(null));
	}

	public int doneTodo(Object[] tids) {
		StringBuffer sb = new StringBuffer();
		for (Object tid : tids)
			sb.append(tid + ",");
		sb.append("0");
		logger.info("Done all these todos : " + sb);
		return getJdbcTemplate().update(
				"update todo set status=9,deadline=CURRENT_TIMESTAMP where tid in ("
						+ sb.toString() + ")");
	}

	public static final class TodoMapper implements RowMapper<Todo> {
		UserDaoImpl userDao;

		public TodoMapper(UserDaoImpl userDao) {
			this.userDao = userDao;
		}

		public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
			Todo td = new Todo();
			td.setTid(rs.getLong("tid"));
			td.setTodo(rs.getString("todo"));
			td.setNote(rs.getString("note"));
			td.setPriority(rs.getShort("priority"));
			td.setTtime(rs.getTimestamp("ttime"));
			td.setTtype(rs.getShort("ttype"));
			td.setDone(rs.getBoolean("status"));
			if (this.userDao != null)
				td.setUser(this.userDao.findUserByID(rs.getLong("uid")));
			td.setDeadline(rs.getTimestamp("deadline"));
			return td;
		}
	}
}
