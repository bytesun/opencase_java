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

  public abstract List<Question> findQuestionsByPID(long paramLong, String paramString,int start,int end);

  public abstract List<Question> findQuestionsByCID(long paramLong, String paramString,int start,int end);

  public abstract List<Question> findLatestQuestions(int start,int end, String paramString);

  public abstract List<Question> search(String paramString1, String lang, int start, int end);

  public abstract List<Question> findMyQuestions(String lang,long uid);

  public abstract List<Comment> findComments(long paramLong, String paramString,int start,int end);
  
  public abstract int addAnswerCnt(long qid,int cnt,String lang);
  public abstract int updateIssueStatus(long qid,String lang,int status);
  
  public abstract int vote(long uid,long qid,String lang,int vote);
}
