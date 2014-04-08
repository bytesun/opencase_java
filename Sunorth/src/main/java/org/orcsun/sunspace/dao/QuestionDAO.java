package org.orcsun.sunspace.dao;

import java.util.List;
import org.orcsun.sunspace.object.Comment;
import org.orcsun.sunspace.object.Question;

public abstract interface QuestionDAO
{
  public abstract int addQuestion(Question paramQuestion, String paramString);

  public abstract int updateQuestion(Question paramQuestion, String paramString);

  public abstract int deleteQuestion(long paramLong, String paramString);

  public abstract Question getQuestion(long paramLong, String paramString);

  public abstract List<Question> findQuestionsByPID(long paramLong, String paramString);

  public abstract List<Question> findQuestionsByCID(long paramLong, String paramString);

  public abstract List<Question> findNewQuestions(int paramInt, String paramString);

  public abstract List<Question> search(String paramString1, String paramString2);

  public abstract List<Question> findMyQuestions(long paramLong, String paramString);

  public abstract List<Comment> findComments(long paramLong, String paramString);
}

/* Location:           E:\2014097ois-Sunorth-1.1.6\WEB-INF\classes\
 * Qualified Name:     org.orcsun.sunspace.dao.QuestionDAO
 * JD-Core Version:    0.6.2
 */