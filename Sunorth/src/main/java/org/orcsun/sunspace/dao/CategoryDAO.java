package org.orcsun.sunspace.dao;

import java.util.List;
import org.orcsun.sunspace.object.Category;

public abstract interface CategoryDAO
{
  public abstract int addCategory(Category paramCategory, String paramString);

  public abstract int updateCategory(Category paramCategory, String paramString);

  public abstract List<Category> findSubCategory(long paramLong, String paramString);

  public abstract Category getCategory(long paramLong, String paramString);
}

