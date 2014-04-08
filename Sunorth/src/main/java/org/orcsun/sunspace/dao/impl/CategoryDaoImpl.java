/*    */ package org.orcsun.sunspace.dao.impl;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ import org.orcsun.sunspace.dao.CategoryDAO;
/*    */ import org.orcsun.sunspace.object.Category;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.jdbc.core.JdbcTemplate;
/*    */ import org.springframework.jdbc.core.RowMapper;
/*    */ 
/*    */ public class CategoryDaoImpl extends SunJdbcDaoSupport
/*    */   implements CategoryDAO
/*    */ {
/* 23 */   private static final Logger logger = LoggerFactory.getLogger(CategoryDaoImpl.class);
/*    */ 
/*    */   public int addCategory(Category c, String lang) {
/* 26 */     String sql = null;
/* 27 */     Object[] args = null;
/* 28 */     sql = "insert into category_" + lang + "(pid,catname) values(?,?)";
/* 29 */     args = new Object[] { Long.valueOf(c.getPid()), c.getCatname() };
/* 30 */     logger.info("Add a new category :" + sql);
/* 31 */     return getJdbcTemplate().update(sql, args);
/*    */   }
/*    */ 
/*    */   public int updateCategory(Category c, String lang)
/*    */   {
/* 36 */     String sql = null;
/* 37 */     Object[] args = null;
/* 38 */     sql = "update category_" + lang + " set pid=?,catname=? where cid=?";
/* 39 */     args = new Object[] { Long.valueOf(c.getPid()), c.getCatname(), Long.valueOf(c.getCid()) };
/* 40 */     logger.info("Update node :" + sql);
/* 41 */     return getJdbcTemplate().update(sql, args);
/*    */   }
/*    */ 
/*    */   public List<Category> findSubCategory(long pid, String lang)
/*    */   {
/* 46 */     String sql = "select cid,pid,catname from category_" + lang + " where pid=" + pid;
/* 47 */     logger.info(sql);
/*    */ 
/* 49 */     List ns = getJdbcTemplate().query(sql, new CategoryMapper());
/* 50 */     return ns;
/*    */   }
/*    */ 
/*    */   public Category getCategory(long cid, String lang)
/*    */   {
/* 65 */     String sql = "select cid,pid,catname from category_" + lang + " where cid=" + cid;
/*    */     try {
/* 67 */       return (Category)getJdbcTemplate().queryForObject(sql, new CategoryMapper());
/*    */     } catch (Exception e) {
/* 69 */       logger.warn("Failed to get category[ " + cid + " ]:" + e.getMessage());
/* 70 */     }return null;
/*    */   }
/*    */ 
/*    */   private static final class CategoryMapper
/*    */     implements RowMapper<Category>
/*    */   {
/*    */     public Category mapRow(ResultSet rs, int rowNum)
/*    */       throws SQLException
/*    */     {
/* 56 */       Category c = new Category();
/* 57 */       c.setCid(rs.getLong("cid"));
/* 58 */       c.setPid(rs.getLong("pid"));
/* 59 */       c.setCatname(rs.getString("catname"));
/* 60 */       return c;
/*    */     }
/*    */   }
/*    */ }

/* Location:           E:\2014097ois-Sunorth-1.1.6\WEB-INF\classes\
 * Qualified Name:     org.orcsun.sunspace.dao.impl.CategoryDaoImpl
 * JD-Core Version:    0.6.2
 */