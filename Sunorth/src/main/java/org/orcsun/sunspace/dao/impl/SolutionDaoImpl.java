package org.orcsun.sunspace.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.orcsun.sunspace.dao.SolutionDAO;
import org.orcsun.sunspace.object.Solution;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

public class SolutionDaoImpl extends SunJdbcDaoSupport  implements SolutionDAO {

	@Override
	public long add(Solution s) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(getJdbcTemplate());
		jdbcInsert.withTableName("COMMENT").usingGeneratedKeyColumns(
				new String[] { "COMMENTID" });
		Map parameters = new HashMap();
		parameters.put("COMMENT", s.getComment());
		parameters.put("CASEID", s.getCaseid());
		parameters.put("PHASEID", s.getPhaseid());
		parameters.put("CREATETIME", s.getCreatetime());
		parameters.put("UID", s.getUid());
		
		Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(
				parameters));
		return key.intValue();
	}

	@Override
	public List<Solution> listForCase(long caseid, int count) {
		String sql = "SELECT * FROM COMMENT WHERE CASEID="+caseid+" AND PHASEID=0 ORDER BY CREATETIME DESC LIMIT "+count;
		return this.getJdbcTemplate().query(sql, new SolutionMapper());
	}

	/**
	 * List all comments under a phase and include case's comments
	 */
	@Override
	public List<Solution> listForPhase(long caseid, int phaseid,int pagesize) {
		String sql = "SELECT * FROM COMMENT WHERE CASEID="+caseid+" AND (PHASEID= "+phaseid+" OR PHASEID=0) ORDER BY CREATETIME DESC LIMIT "+pagesize;
		return this.getJdbcTemplate().query(sql, new SolutionMapper());
	}

	private static final class SolutionMapper implements RowMapper<Solution>{

		@Override
		public Solution mapRow(ResultSet rs, int rowNum) throws SQLException {
			Solution s = new Solution();
			s.setComment(rs.getString("COMMENT"));
			s.setCommentid(rs.getLong("COMMENTID"));
			s.setCreatetime(rs.getTimestamp("CREATETIME"));
			s.setCaseid(rs.getLong("CASEID"));
			s.setPhaseid(rs.getInt("PHASEID"));
			s.setUid(rs.getLong("UID"));
			return s;
		}
		
	}
}
