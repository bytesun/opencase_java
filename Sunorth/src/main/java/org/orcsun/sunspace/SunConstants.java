package org.orcsun.sunspace;

public class SunConstants
{
  public static final String GOOGLE_API_CLIENT_ID="902137185992-74tltkpbsqjose7e98o6mqjvuhb1beq6.apps.googleusercontent.com";
  public static final String GOOGLE_API_CLIENT_SECRET="eokan1I0JGKcR9AzE9hG4HGL";
  
  public static final String LANG_EN = "en";
  public static final String LANG_ZH = "zh";
  
  public static final int QUESTION_STATUS_FOLLOWUP = 0;
  public static final int QUESTION_STATUS_RESOLVED = 1;
  public static final int QUESTION_STATUS_CLOSED=10;
  
  public static final int PROPOSAL_STATUS_NORMAL=0;
  public static final int PROPOSAL_STATUS_ACCEPTED=1;
  public static final int PROPOSAL_STATUS_REJECTED=-1;
  
  public static final int TYPE_QUESTION=1;
  public static final int TYPE_ANSWER=2;
  public static final int TYPE_COMMENT=3;
      
  public static final int USER_STATUS_UNVERIFIED=0;
  public static final int USER_STATUS_NORMAL=1;
  public static final int USER_STATUS_BLOCKED=-1;
  
  public static final int RESPONSE_CODE_SUCCESS=1;
  public static final int RESPONSE_CODE_FAILED=-1;
  public static final int RESPONSE_CODE_HAVEDONE=0;
  public static final int RESPONSE_CODE_NOLOGIN=99;
}

