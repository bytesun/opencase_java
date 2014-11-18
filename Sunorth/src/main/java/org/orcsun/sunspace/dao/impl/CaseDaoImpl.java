package org.orcsun.sunspace.dao.impl;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.orcsun.sunspace.dao.CaseDAO;
import org.orcsun.sunspace.object.Case;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class CaseDaoImpl extends SunJdbcDaoSupport  implements CaseDAO {
	@Override
	public long add(Case c) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
		jdbcInsert.withTableName("CASES").usingGeneratedKeyColumns(
				new String[] { "CASEID" });
		Map parameters = new HashMap();
		parameters.put("SUBJECT", c.getSubject());
		parameters.put("DESCRIPTION", c.getDesc());
		parameters.put("TAG", c.getTag());
		parameters.put("PHASEID", c.getPhaseid());
		parameters.put("STARTDATE", c.getStartdate());
		parameters.put("ENDDATE", c.getEnddate());
		parameters.put("STATUS", c.getStatus());
		parameters.put("CTYPE", c.getCtype());
		parameters.put("UID", c.getUid());
		parameters.put("ISOPEN", c.isOpen());
		
		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
				parameters));
		return key.intValue();
	}

	@Override
	public Case get(long id) {
		String sql = "SELECT * FROM CASES WHERE CASEID="+id;
		try{
			return this.getJdbcTemplate().queryForObject(sql, new CaseMapper());
		}catch(Exception e){
			return null;
		}
	}


	@Override
	public List<Case> listMy(long uid) {
		String sql = "SELECT * FROM CASES WHERE UID="+uid+" ORDER BY STATUS ";
		return this.getJdbcTemplate().query(sql,new CaseMapper());
	}
	
	/**
	 * 1-latest
	 * 2-active
	 * 3-need help
	 */
	@Override
	public List<Case> listCases(int listType, int count) {
		String sql = null;
		switch(listType){
		case 1: 
			sql = "SELECT * FROM CASES WHERE ISOPEN=true ORDER BY STARTDATE DESC LIMIT "+count;
			break;
		case 2:
			sql = "SELECT * FROM CASES WHERE ISOPEN=true AND CASEID IN (SELECT CASEID FROM ITEM GROUP BY CASEID  ORDER BY DOTIME DESC) LIMIT "+count;
			break;
//		case 3:
//			sql = "SELECT * FROM CASES CASEID IN (SELECT CASEID, MAX(PHASEID)  FROM PHASE WHERE ISQUESTION <> 0 GROUP BY CASEID ) LIMIT "+count;
		}
		return this.getJdbcTemplate().query(sql, new CaseMapper());
	}
	

	@Override
	public List<Case> search(String tag,short ctype,int count) {
		String sql = "SELECT * FROM CASES WHERE  ISOPEN=true AND (SUBJECT LIKE '%"+tag+"%')";
		if(ctype>0)
			sql = sql + " AND CTYPE="+ctype;
		sql = sql +" LIMIT "+count;
		return this.getJdbcTemplate().query(sql,new CaseMapper());
	}

	private static final class CaseMapper implements RowMapper<Case> {

		@Override
		public Case mapRow(ResultSet rs, int rowNum) throws SQLException {
			Case c = new Case();
			c.setCaseid(rs.getLong("CASEID"));
			c.setSubject(rs.getString("SUBJECT"));
			c.setDesc(rs.getString("DESCRIPTION"));
			c.setPhaseid(rs.getInt("PHASEID"));
			c.setStartdate(rs.getDate("STARTDATE"));
			c.setEnddate(rs.getDate("ENDDATE"));
			c.setTag(rs.getString("TAG"));
			c.setStatus(rs.getShort("STATUS"));
			c.setCtype(rs.getShort("CTYPE"));
			c.setUid(rs.getLong("UID"));
			c.setOpen(rs.getBoolean("ISOPEN"));
			return c;
		}
		
	}

	@Override
	public int nextPhase(long theCaseId) {
		String sql = "UPDATE CASES SET PHASEID=PHASEID+1 WHERE CASEID="+theCaseId;
		return this.getJdbcTemplate().update(sql);
	}

	@Override
	public int changeStatus(long caseId,int status) {
		String sql = "UPDATE CASES SET STATUS="+status+" WHERE CASEID="+caseId;
		return this.getJdbcTemplate().update(sql);
	}


}
