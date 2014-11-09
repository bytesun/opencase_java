package org.orcsun.sunspace.dao.impl;

import java.util.List;

import org.orcsun.sunspace.dao.SolutionDAO;
import org.orcsun.sunspace.object.Solution;

public class SolutionDaoImpl extends SunJdbcDaoSupport  implements SolutionDAO {

	@Override
	public long add(Solution s) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Solution> listForCase(long caseid, int count) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Solution> listForPhase(long caseid, int phaseid) {
		// TODO Auto-generated method stub
		return null;
	}

}
