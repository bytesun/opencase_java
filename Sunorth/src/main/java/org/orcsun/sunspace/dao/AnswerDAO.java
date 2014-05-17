package org.orcsun.sunspace.dao;

import java.util.List;
import org.orcsun.sunspace.object.Answer;

public abstract interface AnswerDAO
{
  public abstract int addAnswer(Answer paramAnswer, String paramString);

  public abstract int updateAnswer(Answer a,String lang);

  public abstract int accetpAnswer(long aid,String lang);
  
  public abstract List<Answer> findAnswers(long paramLong, String paramString);

  public abstract List<Answer> findMyAnswers(long paramLong, String paramString);
  
  public abstract int vote(long uid,long aid,String lang,int vote);
}

