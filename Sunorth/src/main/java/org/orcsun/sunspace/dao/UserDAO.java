package org.orcsun.sunspace.dao;

import org.orcsun.sunspace.object.User;

public abstract interface UserDAO
{
  public abstract long addUser(User paramUser);
  public abstract int updateUser(User u);

  public abstract User findUserByID(long paramLong);

  public abstract User findUserByEmail(String paramString);
}
