package org.orcsun.sunspace.dao;

import java.util.List;

import org.orcsun.sunspace.object.Item;

public interface ItemDAO {
	public abstract long add(Item item);
	public abstract int changeStatus(long itemid,int status);
	public abstract List<Item> list(int pid,long caseid);
}
