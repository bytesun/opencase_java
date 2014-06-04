package org.orcsun.sunspace.dao;

import java.util.List;

import org.orcsun.sunspace.object.Activity;

public abstract interface ActivityDAO {

	public abstract int addActivity(Activity activity,String lang);
	
	public abstract Activity getActivity(long aid,String lang);
	
	public abstract List<Activity> getLatestActivities(int cnt,String lang);
	
	public abstract List<Activity> getMyActivities(long uid,String lang);
}
