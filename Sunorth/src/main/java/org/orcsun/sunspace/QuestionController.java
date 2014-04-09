/*     */ package org.orcsun.sunspace;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.Locale;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.orcsun.sunspace.dao.impl.AnswerDaoImpl;
/*     */ import org.orcsun.sunspace.dao.impl.CategoryDaoImpl;
/*     */ import org.orcsun.sunspace.dao.impl.QuestionDaoImpl;
/*     */ import org.orcsun.sunspace.object.Answer;
/*     */ import org.orcsun.sunspace.object.Comment;
/*     */ import org.orcsun.sunspace.object.Question;
/*     */ import org.orcsun.sunspace.object.User;
/*     */ import org.orcsun.sunspace.utils.StringUtil;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.Model;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ @RequestMapping({"/question"})
/*     */ public class QuestionController
/*     */ {
/*     */ 
/*     */   @Autowired
/*     */   QuestionDaoImpl quesDao;
/*     */ 
/*     */   @Autowired
/*     */   AnswerDaoImpl answerDao;
/*     */ 
/*     */   @Autowired
/*     */   CategoryDaoImpl catDao;
/*  46 */   private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
/*     */ 
/*     */   @RequestMapping(value={"/{qid}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String showQuestion(Locale locale, @PathVariable long qid, HttpServletRequest req, Model model) {
/*  50 */     String lang = "en";
/*  51 */     Object olang = req.getSession().getAttribute("lang");
/*  52 */     Object plang = req.getParameter("lang");
/*  53 */     if (olang != null) {
/*  54 */       lang = (String)olang;
/*  55 */     } else if ((plang != null) && (!plang.equals(""))) {
/*  56 */       if (((String)plang).equals("zh")) lang = "zh";
/*  57 */       req.getSession().setAttribute("lang", lang);
/*     */     }
/*  59 */     else if (locale.getLanguage().equalsIgnoreCase("zh")) {
/*  60 */       lang = "zh";
/*     */     }
/*  62 */     Question q = this.quesDao.getQuestion(qid, lang);
/*  63 */     if (q != null) {
/*  64 */       long cid = q.getCid();
/*  65 */       if (cid > 0L)
/*  66 */         model.addAttribute("pcategory", this.catDao.getCategory(cid, lang));
/*     */       else {
/*  68 */         model.addAttribute("pquestion", this.quesDao.getQuestion(q.getPid(), lang));
/*     */       }
/*  70 */       model.addAttribute("question", q);
/*  71 */       model.addAttribute("answers", this.answerDao.findAnswers(qid, lang));
/*  72 */       model.addAttribute("comments", this.quesDao.findComments(qid, lang));
/*  73 */       model.addAttribute("followups", this.quesDao.findQuestionsByPID(qid, lang));
/*     */     }
/*  75 */     return "question";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/ask"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String askQuestion(Locale locale, HttpServletRequest req, Model model)
/*     */   {
/*  83 */     Object u = req.getSession().getAttribute("user");
/*  84 */     String cid = req.getParameter("cid");
/*  85 */     String pid = req.getParameter("pid");
/*  86 */     if (u != null)
/*     */     {
/*     */       try {
/*  89 */         String lang = "en";
/*  90 */         Object olang = req.getSession().getAttribute("lang");
/*  91 */         if (olang != null) {
/*  92 */           lang = (String)olang;
/*     */         }
/*  94 */         else if (locale.getLanguage().equalsIgnoreCase("zh")) {
/*  95 */           lang = "zh";
/*     */         }
/*  97 */         Question q = new Question();
/*  98 */         String question = req.getParameter("questitle");
/*  99 */         q.setQuestion(StringUtil.utf8(question, "iso-8859-1"));
/*     */ 
/* 101 */         if (pid != null)
/* 102 */           q.setPid(Long.parseLong(pid));
/* 103 */         else if (cid != null) {
/* 104 */           q.setCid(Long.parseLong(cid));
/*     */         }
/*     */ 
/* 107 */         question = req.getParameter("question");
/* 108 */         if (question != null) q.setDescription(StringUtil.utf8(question, "iso-8859-1"));
/*     */ 
/* 110 */         String tag = req.getParameter("tag");
/* 111 */         if (tag != null) q.setTag(StringUtil.utf8(tag, "iso-8859-1"));
/* 112 */         q.setUser((User)u);
/* 113 */         this.quesDao.addQuestion(q, lang);
/*     */       }
/*     */       catch (Exception e) {
/* 116 */         logger.error(e.getMessage());
/* 117 */         model.addAttribute("errormsg", e.getMessage());
/*     */       }
/* 119 */       if ((pid != null) && (!pid.equals("")))
/* 120 */         return "redirect:/question/" + pid;
/* 121 */       if ((cid != null) && (!cid.equals(""))) {
/* 122 */         return "redirect:/cat/" + cid;
/*     */       }
/* 124 */       return "redirect:/";
/*     */     }
/* 126 */     return "redirect:/redirectLogin?cid=" + cid + "&pid=" + pid;
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/comment"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String addComment(Locale locale, HttpServletRequest req, Model model)
/*     */   {
/* 134 */     Object u = req.getSession().getAttribute("user");
/* 135 */     if (u != null) {
/* 136 */       String lang = "en";
/* 137 */       Object olang = req.getSession().getAttribute("lang");
/* 138 */       if (olang != null) {
/* 139 */         lang = (String)olang;
/*     */       }
/* 141 */       else if (locale.getLanguage().equalsIgnoreCase("zh")) {
/* 142 */         lang = "zh";
/*     */       }
/* 144 */       String qid = req.getParameter("qid");
/*     */ 
/* 146 */       Comment c = new Comment();
/* 147 */       c.setQid(Long.parseLong(qid));
/* 148 */       c.setUser((User)u);
/*     */       try {
/* 150 */         c.setComment(StringUtil.utf8(req.getParameter("comment"), "iso-8859-1"));
/* 151 */         this.quesDao.addComment(c, lang);
/*     */       } catch (UnsupportedEncodingException e) {
/* 153 */         logger.error(e.getMessage());
/* 154 */         model.addAttribute("errormsg", e.getMessage());
/*     */       }
/*     */ 
/* 157 */       return "redirect:/question/" + qid;
/*     */     }
/*     */ 
/* 160 */     String cid = req.getParameter("cid");
/* 161 */     String pid = req.getParameter("pid");
/* 162 */     return "redirect:/redirectLogin?cid=" + cid + "&pid=" + pid;
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/answer"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String answerQuestion(Locale locale, HttpServletRequest req, Model model)
/*     */   {
/* 169 */     Object u = req.getSession().getAttribute("user");
/* 170 */     if (u != null) {
/* 171 */       String lang = "en";
/* 172 */       Object olang = req.getSession().getAttribute("lang");
/* 173 */       if (olang != null) {
/* 174 */         lang = (String)olang;
/*     */       }
/* 176 */       else if (locale.getLanguage().equalsIgnoreCase("zh")) {
/* 177 */         lang = "zh";
/*     */       }
/*     */ 
/* 180 */       String qid = req.getParameter("qid");
/* 181 */       Answer a = new Answer();
/* 182 */       a.setQid(Long.parseLong(qid));
/* 183 */       a.setUser((User)u);
/*     */ 
/* 185 */       String answer = req.getParameter("answer");
/* 186 */       if ((answer != null) && (!answer.equals(""))) {
/*     */         try {
/* 188 */           a.setAnswer(StringUtil.utf8(answer, "iso-8859-1"));
/* 189 */           this.answerDao.addAnswer(a, lang);
					//increase answer cnt
/* 191 */           this.quesDao.addAnswerCnt(Long.parseLong(qid),1,lang);
					//mark the question is resolved
/* 194 */           Object isresolved = req.getParameter("isresolved");
/* 195 */           if (isresolved != null)
	/* 200 */           this.quesDao.resolveQuestion(Long.parseLong(qid), lang);
/*     */         } catch (Exception e) {
/* 202 */           model.addAttribute("errormsg", e.getMessage());
/*     */         }
/*     */       }
/*     */ 
/* 206 */       return "redirect:/question/" + qid;
/*     */     }
/* 208 */     String cid = req.getParameter("cid");
/* 209 */     String pid = req.getParameter("pid");
/* 210 */     return "redirect:/redirectLogin?cid=" + cid + "&pid=" + pid;
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/resolve"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String resolve(Locale locale, HttpServletRequest req)
/*     */   {
/* 218 */     Object u = req.getSession().getAttribute("user");
/* 219 */     if (u != null) {
/* 220 */       String lang = "en";
/* 221 */       Object olang = req.getSession().getAttribute("lang");
/* 222 */       if (olang != null) {
/* 223 */         lang = (String)olang;
/*     */       }
/* 225 */       else if (locale.getLanguage().equalsIgnoreCase("zh")) {
/* 226 */         lang = "zh";
/*     */       }
/* 228 */       String qid = req.getParameter("qid");
/* 229 */       Question q = this.quesDao.getQuestion(Long.parseLong(qid), lang);
/* 230 */       if (q.getUser().getUid() == ((User)u).getUid())
/*     */       {
/* 232 */         Answer a = new Answer();
/* 233 */         a.setQid(Long.parseLong(qid));
/* 234 */         a.setUser((User)u);
/* 235 */         a.setAnswer(req.getParameter("answer"));
/* 236 */         this.answerDao.addAnswer(a, lang);
/* 237 */         q.setResolved(true);
/* 238 */         this.quesDao.updateQuestion(q, lang);
/*     */       }
/* 240 */       return "redirect:/question/" + qid;
/*     */     }
/* 242 */     String cid = req.getParameter("cid");
/* 243 */     String pid = req.getParameter("pid");
/* 244 */     return "redirect:/redirectLogin?cid=" + cid + "&pid=" + pid;
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/search"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String search(Locale locale, HttpServletRequest req, Model model)
/*     */   {
/* 250 */     String searchKey = req.getParameter("searchkey");
/* 251 */     if (searchKey != null) {
/*     */       try
/*     */       {
/* 254 */         String lang = "en";
/* 255 */         Object olang = req.getSession().getAttribute("lang");
/* 256 */         if (olang != null) {
/* 257 */           lang = (String)olang;
/*     */         }
/* 259 */         else if (locale.getLanguage().equalsIgnoreCase("zh")) {
/* 260 */           lang = "zh";
/*     */         }
/* 262 */         String ckey = StringUtil.utf8(searchKey, "iso-8859-1");
/* 263 */         model.addAttribute("searchkey", ckey);
/* 264 */         model.addAttribute("questions", this.quesDao.search(ckey, lang));
/*     */       } catch (Exception e) {
/* 266 */         model.addAttribute("errormsg", e.getMessage());
/*     */       }
/*     */     }
/*     */ 
/* 270 */     return "searchResult";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/searchtag"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String searchByTag(Locale locale, HttpServletRequest req, Model model) {
/* 275 */     String tag = req.getParameter("tag");
/*     */ 
/* 277 */     if (tag != null) {
/*     */       try
/*     */       {
/* 280 */         String lang = "en";
/* 281 */         Object olang = req.getSession().getAttribute("lang");
/* 282 */         if (olang != null) {
/* 283 */           lang = (String)olang;
/*     */         }
/* 285 */         else if (locale.getLanguage().equalsIgnoreCase("zh")) {
/* 286 */           lang = "zh";
/*     */         }
/* 288 */         String ckey = StringUtil.utf8(tag, "iso-8859-1");
/* 289 */         model.addAttribute("searchkey", ckey);
/* 290 */         model.addAttribute("questions", this.quesDao.search(ckey, lang));
/*     */       } catch (Exception e) {
/* 292 */         model.addAttribute("errormsg", e.getMessage());
/*     */       }
/*     */     }
/*     */ 
/* 296 */     return "searchResult";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/vote"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String vote(Locale locale, HttpServletRequest req, Model model) {
/* 301 */     String qid = req.getParameter("qid");
/* 302 */     Object u = req.getSession().getAttribute("user");
/* 303 */     if (u != null) {
/*     */       try {
/* 305 */         String lang = "en";
/* 306 */         Object olang = req.getSession().getAttribute("lang");
/* 307 */         if (olang != null) {
/* 308 */           lang = (String)olang;
/*     */         }
/* 310 */         else if (locale.getLanguage().equalsIgnoreCase("zh")) {
/* 311 */           lang = "zh";
/*     */         }
/* 313 */         int vote = 1;
/* 314 */         if (req.getParameter("no") != null) vote = -1;
/* 315 */         this.quesDao.vote(Long.parseLong(qid), vote, ((User)u).getUid(), lang);
/*     */       } catch (Exception e) {
/* 317 */         model.addAttribute("errormsg", e.getMessage());
/*     */       }
/* 319 */       return "redirect:/question/" + qid;
/*     */     }
/* 321 */     String cid = req.getParameter("cid");
/* 322 */     String pid = req.getParameter("pid");
/* 323 */     return "redirect:/redirectLogin?cid=" + cid + "&pid=" + pid;
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/answer/vote"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String voteAnswer(Locale locale, HttpServletRequest req, Model model)
/*     */   {
/* 329 */     String qid = req.getParameter("qid");
/* 330 */     String aid = req.getParameter("aid");
/* 331 */     logger.info("vote answer :" + aid);
/* 332 */     Object u = req.getSession().getAttribute("user");
/* 333 */     if (u != null) {
/*     */       try {
/* 335 */         String lang = "en";
/* 336 */         Object olang = req.getSession().getAttribute("lang");
/* 337 */         if (olang != null) {
/* 338 */           lang = (String)olang;
/*     */         }
/* 340 */         else if (locale.getLanguage().equalsIgnoreCase("zh")) {
/* 341 */           lang = "zh";
/*     */         }
/* 343 */         String vcomment = req.getParameter("vcomment");
/* 344 */         int vote = 1;
/* 345 */         if (req.getParameter("vdown") != null) vote = -1;
/* 346 */         if (aid != null){
					if(req.getParameter("isanswer")!=null){
						 this.answerDao.voteAnswer(Long.parseLong(aid), vote,1, vcomment, ((User)u).getUid(), lang);
					}else{
						 this.answerDao.voteAnswer(Long.parseLong(aid), vote,0, vcomment, ((User)u).getUid(), lang);
						
					}
				}
/*     */       }catch (Exception e) {
/* 349 */         logger.error(e.getMessage());
/* 350 */         model.addAttribute("errormsg", e.getMessage());
	/* 352 */  
				}
				return "redirect:/question/" + qid;
			}			
/* 354 */     String cid = req.getParameter("cid");
/* 355 */     String pid = req.getParameter("pid");
/* 356 */     return "redirect:/redirectLogin?cid=" + cid + "&pid=" + pid;
/*     */   }

}
