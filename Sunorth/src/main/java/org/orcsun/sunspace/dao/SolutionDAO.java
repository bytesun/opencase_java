package org.orcsun.sunspace.dao;

import java.util.List;

import org.orcsun.sunspace.object.Solution;

public interface SolutionDAO {

	public abstract long add(Solution s);
	public abstract List<Solution> listForCase(long caseid, int pageSize);
	public abstract List<Solution> listForPhase(long caseid,int phaseid,int pageSize);
}
