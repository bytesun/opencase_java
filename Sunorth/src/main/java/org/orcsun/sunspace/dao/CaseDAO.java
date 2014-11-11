package org.orcsun.sunspace.dao;

import java.util.List;

import org.orcsun.sunspace.object.Case;

public interface CaseDAO {

	public abstract long add(Case c);
	public abstract Case get(long id);
	public abstract List<Case> listAll(int count);
	public abstract List<Case> listMy(long uid);
	public abstract List<Case> search(String tag);
	public abstract int nextPhase(long theCaseId);
}
