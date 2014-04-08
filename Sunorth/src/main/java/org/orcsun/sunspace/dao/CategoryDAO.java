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

/* Location:           E:\2014097ois-Sunorth-1.1.6\WEB-INF\classes\
 * Qualified Name:     org.orcsun.sunspace.dao.CategoryDAO
 * JD-Core Version:    0.6.2
 */