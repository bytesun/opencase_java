package org.orcsun.sunspace.dao;

import org.orcsun.sunspace.object.User;

public abstract interface UserDAO
{
  public abstract long addUser(User paramUser);
  public abstract int updateUser(User u);

  public abstract User findUserByID(long uid);

  public abstract User findUserByEmail(String email);
  
  public abstract User findUserByOpenid(String openid);
  
  public abstract String getRefreshToken(long uid);
  
  public abstract int updatePasswd(long uid,String pwd);
  
  public abstract int updateRefreshToken(long uid,String token);
  
  public abstract int follow(long uid,long fid,int ftype,String lang,String alias);
  public abstract int unfollow(long uid,long fid,int ftype,String lang);

}
