package org.orcsun.sunspace.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.orcsun.sunspace.dao.PhaseDAO;
import org.orcsun.sunspace.object.Phase;
import org.springframework.jdbc.core.RowMapper;

public class PhaseDaoImpl extends SunJdbcDaoSupport  implements PhaseDAO {

	@Override
	public int add(Phase p) {
		String sql = "INSERT INTO PHASE(PHASEID,CASEID,PHASE,NOTE,STARTDATE,ENDDATE,TAG) values(?,?,?,?,?,?,?)";
		Object[] args = new Object[] { Long.valueOf(p.getPhaseid()), Long.valueOf(p.getCaseid()) ,p.getPhase(),p.getNote(),p.getStartdate(),p.getEnddate(),p.getTag()};
		logger.info("new phase :" + sql);
		return getJdbcTemplate().update(sql, args);
	}

	@Override
	public Phase get(int pid, long caseid) {
		String sql = "SELECT * FROM PHASE WHERE PHASEID="+pid+" AND CASEID="+caseid;
		try{
			return this.getJdbcTemplate().queryForObject(sql, new PhaseMapper());
		}catch(Exception e){
			return null;
		}
	}

	@Override
	public List<Phase> listForCase(long caseid) {
		String sql = "SELECT * FROM PHASE WHERE CASEID="+caseid+" ORDER BY PHASEID";
		return this.getJdbcTemplate().query(sql, new PhaseMapper());
	}

	private static final class PhaseMapper implements RowMapper<Phase>{

		@Override
		public Phase mapRow(ResultSet rs, int rowNum) throws SQLException {
			Phase p = new Phase();
			p.setPhaseid(rs.getInt("PHASEID"));
			p.setCaseid(rs.getLong("CASEID"));
			p.setPhase(rs.getString("PHASE"));
			p.setNote(rs.getString("NOTE"));
			p.setStartdate(rs.getDate("STARTDATE"));
			p.setEnddate(rs.getDate("ENDDATE"));
			p.setTag(rs.getString("TAG"));
			
			return p;
		}
		
	}
}
