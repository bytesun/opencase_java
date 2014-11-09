package org.orcsun.sunspace.dao;

import java.util.List;

import org.orcsun.sunspace.object.Phase;

public interface PhaseDAO {

	public abstract int add(Phase p);
	public abstract Phase get(int pid,long caseid);
	public abstract List<Phase> listForCase(long caseid);
	
}
