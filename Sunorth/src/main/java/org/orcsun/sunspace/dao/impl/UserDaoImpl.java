/*     */ package org.orcsun.sunspace.dao.impl;
/*     */ 
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;

import org.apache.log4j.Logger;
/*     */ import org.orcsun.sunspace.dao.UserDAO;
/*     */ import org.orcsun.sunspace.object.User;
/*     */ import org.springframework.jdbc.core.RowMapper;
/*     */ import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
/*     */ import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
/*     */ 
/*     */ public class UserDaoImpl extends SunJdbcDaoSupport
/*     */   implements UserDAO
/*     */ {
/*  31 */   private static final Logger logger = Logger.getLogger(UserDaoImpl.class);
/*     */ 
/*     */   public int addUser(User u)
/*     */   {
/*  36 */     SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
/*  37 */     jdbcInsert.withTableName("users").usingGeneratedKeyColumns(new String[] { 
/*  38 */       "uid" });
/*  39 */     Map parameters = new HashMap();
/*  40 */     parameters.put("name", u.getName());
/*  41 */     parameters.put("passwd", u.getPasswd());
/*  42 */     parameters.put("email", u.getEmail());
/*  43 */     parameters.put("credit", u.getCredit());
/*  44 */     parameters.put("reputation", u.getReputation());
/*     */ 
/*  46 */     parameters.put("regtime", new Date());
/*  47 */     parameters.put("status", u.getStatus());
/*     */ 
/*  50 */     Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
/*  51 */       parameters));
/*  52 */     return key.intValue();
/*     */   }
/*     */ 
/*     */   public boolean checkPwd(User u)
/*     */   {
/*  63 */     return false;
/*     */   }
/*     */ 
/*     */   public User findUserByID(long uid)
/*     */   {
/*  68 */     String sql = "select uid,name,passwd,email,credit,reputation,regtime,status from users where uid='" + uid + "'";
/*  69 */     logger.info(sql);
/*  70 */     User u = null;
/*     */     try {
/*  72 */       u = (User)getJdbcTemplate().queryForObject(sql, new UserMapper());
/*     */     } catch (Exception e) {
/*  74 */       logger.warn("No user found :" + uid + ":" + e.getMessage());
/*     */     }
/*  76 */     return u;
/*     */   }

/*     */   public User findUserByEmail(String email) {
/*  80 */     String sql = "select uid,name,passwd,email,credit,reputation,regtime,status from users where email='" + email + "'";
/*  81 */     logger.info(sql);
/*  82 */     User u = null;
/*     */     try {
/*  84 */       u = (User)getJdbcTemplate().queryForObject(sql, new UserMapper());
/*     */     } catch (Exception e) {
/*  86 */       logger.warn("No user found :" + email + ":" + e.getMessage());
/*     */     }
/*  88 */     return u;
/*     */   }
/*     */ 
/*     */   private static final class UserMapper implements RowMapper<User>
/*     */   {
/*     */     public User mapRow(ResultSet rs, int rowNum) throws SQLException
/*     */     {
/*  95 */       User u = new User();
/*  96 */       u.setUid(rs.getLong("uid"));
/*  97 */       u.setName(rs.getString("name"));
/*  98 */       u.setPasswd(rs.getString("passwd"));
/*  99 */       u.setEmail(rs.getString("email"));
/* 100 */       u.setCredit(rs.getInt("credit"));
/* 101 */       u.setReputation(rs.getInt("reputation"));
/* 102 */       u.setRegtime(rs.getTimestamp("regtime"));
/* 103 */       u.setStatus(rs.getInt("status"));
/* 104 */       return u;
/*     */     }
/*     */   }
/*     */ }

