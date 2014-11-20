package org.orcsun.sunspace.dao;

import java.util.List;

import org.orcsun.sunspace.object.Case;

public interface CaseDAO {

	public abstract long add(Case c);
	public abstract Case get(long id);
	public abstract List<Case> listMyActive(long uid);
	public abstract List<Case> listMyAll(long uid);
	public abstract List<Case> searchByKey(String key,int pageSize);
	public abstract List<Case> searchByTag(String tag,int pageSize);
	public abstract int nextPhase(long theCaseId);
	public abstract int changeStatus(long caseId,int status);
	public abstract List<Case> listCases(int pageSize);
	public abstract List<Case> listCasesByType(int ctype,int pageSize);	 
	public abstract List<Case> listCasesByStatus(int status,int pageSize);
	public abstract List<Case> listActiveCases(int pageSize);
	
}
