package org.orcsun.sunspace.dao;

import java.util.List;
import org.orcsun.sunspace.object.Answer;

public abstract interface AnswerDAO
{
  public abstract int addAnswer(Answer paramAnswer, String paramString);

  public abstract int delAnswer(long paramLong1, long paramLong2, long paramLong3, String paramString);

  public abstract List<Answer> findAnswers(long paramLong, String paramString);

  public abstract List<Answer> findMyAnswers(long paramLong, String paramString);
}

/* Location:           E:\2014097ois-Sunorth-1.1.6\WEB-INF\classes\
 * Qualified Name:     org.orcsun.sunspace.dao.AnswerDAO
 * JD-Core Version:    0.6.2
 */