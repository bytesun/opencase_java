package org.orcsun.sunspace.dao;

import java.util.List;

import org.orcsun.sunspace.object.Tag;

public interface TagDAO {

	public int addTags(String strTags);
	public List<Tag> listTag(int count);
}
