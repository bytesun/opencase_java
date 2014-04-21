package org.orcsun.sunspace.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.dao.CategoryDAO;
import org.orcsun.sunspace.object.Category;
import org.springframework.jdbc.core.RowMapper;

public class CategoryDaoImpl extends SunJdbcDaoSupport implements CategoryDAO {
	private static final Logger logger = Logger
			.getLogger(CategoryDaoImpl.class);

	public int addCategory(Category c, String lang) {
		String sql = null;
		Object[] args = null;
		sql = "insert into category_" + lang + "(pid,catname,description) values(?,?,?)";
		args = new Object[] { Long.valueOf(c.getPid()), c.getCatname() ,c.getDescription()};
		logger.info("Add a new category :" + sql);
		return getJdbcTemplate().update(sql, args);
	}

	public int updateCategory(Category c, String lang) {
		String sql = null;
		Object[] args = null;
		sql = "update category_" + lang + " set pid=?,catname=?,description=? where cid=?";
		args = new Object[] { Long.valueOf(c.getPid()), c.getCatname(),c.getDescription(),
				Long.valueOf(c.getCid()) };
		logger.info("Update node :" + sql);
		return getJdbcTemplate().update(sql, args);
	}
	/**
	 * List top 20 categories
	 * @param language
	 * @return
	 */
	public List<Category> top20(String lang) {
		String sql = "select cid,pid,catname,description,rate from category_" + lang+" order by rate desc limit 20";
		logger.info(sql);
		return getJdbcTemplate().query(sql, new CategoryMapper());
	}
	
	/**
	 * Get sub category of one category
	 */
	public List<Category> findSubCategory(long pid, String lang) {
		String sql = "select cid,pid,catname,description,rate from category_" + lang
				+ " where pid=" + pid;
		logger.info(sql);

		return getJdbcTemplate().query(sql, new CategoryMapper());
	}

	public Category getCategory(long cid, String lang) {
		String sql = "select cid,pid,catname,description,rate from category_" + lang
				+ " where cid=" + cid;
		try {
			return (Category) getJdbcTemplate().queryForObject(sql,
					new CategoryMapper());
		} catch (Exception e) {
			logger.warn("Failed to get category[ " + cid + " ]:"
					+ e.getMessage());
		}
		return null;
	}

	private static final class CategoryMapper implements RowMapper<Category> {
		public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
			Category c = new Category();
			c.setCid(rs.getLong("cid"));
			c.setPid(rs.getLong("pid"));
			c.setCatname(rs.getString("catname"));
			c.setDescription(rs.getString("description"));
			c.setRate(rs.getInt("rate"));
			return c;
		}
	}


}
