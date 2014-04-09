package org.orcsun.sunspace.dao;

import org.orcsun.sunspace.object.User;

public abstract interface UserDAO
{
  public abstract int addUser(User paramUser);

  public abstract boolean checkPwd(User paramUser);

  public abstract User findUserByID(long paramLong);

  public abstract User findUserByEmail(String paramString);
}
