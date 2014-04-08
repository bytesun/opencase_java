/*     */ package org.orcsun.sunspace;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;
/*     */ import org.orcsun.sunspace.dao.impl.AnswerDaoImpl;
/*     */ import org.orcsun.sunspace.dao.impl.CategoryDaoImpl;
/*     */ import org.orcsun.sunspace.dao.impl.QuestionDaoImpl;
/*     */ import org.orcsun.sunspace.dao.impl.TodoDaoImpl;
/*     */ import org.orcsun.sunspace.dao.impl.UserDaoImpl;
/*     */ import org.orcsun.sunspace.object.User;
/*     */ import org.orcsun.sunspace.utils.StringUtil;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.Model;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ public class HomeController
/*     */ {
/*  31 */   private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
/*     */ 
/*     */   @Autowired
/*     */   QuestionDaoImpl quesDao;
/*     */ 
/*     */   @Autowired
/*     */   UserDaoImpl userDao;
/*     */ 
/*     */   @Autowired
/*     */   CategoryDaoImpl catDao;
/*     */ 
/*     */   @Autowired
/*     */   AnswerDaoImpl ansDao;
/*     */ 
/*     */   @Autowired
/*     */   TodoDaoImpl todoDao;
/*     */ 
/*  50 */   @RequestMapping(value={"/"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String home(Locale locale, HttpServletRequest req, Model model) { String lang = "en";
/*  51 */     Object olang = req.getSession().getAttribute("lang");
/*  52 */     if (olang != null) {
/*  53 */       lang = (String)olang;
/*     */     }
/*  55 */     else if (locale.getLanguage().equalsIgnoreCase("zh")) {
/*  56 */       lang = "zh";
/*     */     }
/*  58 */     req.getSession().setAttribute("lang", lang);
/*     */ 
/*  60 */     model.addAttribute("topcats", this.catDao.findSubCategory(0L, lang));
/*     */ 
/*  62 */     model.addAttribute("newquestions", this.quesDao.findNewQuestions(10, lang));
/*     */ 
/*  64 */     logger.info("The locale is " + locale.getLanguage());
/*  65 */     return "home"; }
/*     */ 
/*     */   @RequestMapping(value={"/user/admin"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String user(HttpServletRequest req, Model model)
/*     */   {
/*  70 */     Object u = req.getSession().getAttribute("user");
/*  71 */     if (u == null) {
/*  72 */       return "redirect:/redirectLogin";
/*     */     }
/*  74 */     long uid = ((User)u).getUid();
/*  75 */     String lang = (String)req.getSession().getAttribute("lang");
/*     */ 
/*  77 */     model.addAttribute("todos", this.todoDao.findTodayTodos(((User)u).getUid()));
/*     */ 
/*  84 */     return "user_console";
/*     */   }
/*     */   @RequestMapping(value={"/redirectLogin"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String redirectLogin(HttpServletRequest req, Model model) {
/*  88 */     model.addAttribute("cid", req.getParameter("cid"));
/*  89 */     model.addAttribute("qid", req.getParameter("qid"));
/*  90 */     return "login";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/login"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String login(HttpServletRequest req, Model model)
/*     */   {
/*  96 */     String email = req.getParameter("email");
/*     */ 
/*  98 */     boolean islogin = false;
/*  99 */     if ((email != null) && (!email.equals(""))) {
/* 100 */       User u = this.userDao.findUserByEmail(email);
/* 101 */       if (u != null) {
/* 102 */         String passwd = req.getParameter("passwd");
/* 103 */         if ((passwd != null) && (!passwd.equals("")) && (u.getPasswd().equals(passwd))) {
/* 104 */           req.getSession().setAttribute("user", u);
/* 105 */           islogin = true;
/*     */         }
/*     */       }
/*     */     }
/* 109 */     String cid = req.getParameter("cid");
/* 110 */     String qid = req.getParameter("qid");
/* 111 */     if (islogin) {
/* 112 */       if ((cid != null) && (!cid.equals("")))
/* 113 */         return "redirect:/cat/" + cid;
/* 114 */       if ((qid != null) && (!qid.equals(""))) {
/* 115 */         return "redirect:/question/" + qid;
/*     */       }
/* 117 */       return "redirect:/user/admin";
/*     */     }
/*     */ 
/* 120 */     return "redirect:/redirectLogin?cid=" + cid + "&qid=" + qid;
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/logout"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String logout(HttpServletRequest req)
/*     */   {
/* 126 */     req.getSession().setAttribute("user", null);
/* 127 */     return "redirect:/";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/register"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String register(HttpServletRequest req, Model model) {
/* 132 */     String name = req.getParameter("name");
/* 133 */     String pwd = req.getParameter("passwd");
/* 134 */     String email = req.getParameter("email");
/*     */ 
/* 136 */     String cname = name;
/* 137 */     boolean islogin = false;
/*     */     try
/*     */     {
/* 140 */       cname = StringUtil.utf8(name, "iso-8859-1");
/*     */ 
/* 143 */       User u = new User();
/*     */ 
/* 145 */       u.setName(cname);
/* 146 */       u.setPasswd(pwd);
/* 147 */       u.setEmail(email);
/*     */ 
/* 149 */       u.setUid(this.userDao.addUser(u));
/* 150 */       req.getSession().setAttribute("user", u);
/* 151 */       islogin = true;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 155 */       model.addAttribute("errormsg", e.getMessage());
/*     */     }
/* 157 */     String cid = req.getParameter("cid");
/* 158 */     String qid = req.getParameter("qid");
/* 159 */     if (islogin) {
/* 160 */       if ((cid != null) && (!cid.equals("")))
/* 161 */         return "redirect:/cat/" + cid;
/* 162 */       if ((qid != null) && (!qid.equals(""))) {
/* 163 */         return "redirect:/question/" + qid;
/*     */       }
/* 165 */       return "user_console";
/*     */     }
/*     */ 
/* 168 */     return "redirect:/redirectLogin?cid=" + cid + "&qid=" + qid;
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/register/new"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String initRegister(HttpServletRequest req, Model model)
/*     */   {
/* 175 */     model.addAttribute("cid", req.getParameter("cid"));
/* 176 */     model.addAttribute("qid", req.getParameter("qid"));
/* 177 */     return "register";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/chglang"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String changeLang(HttpServletRequest req) {
/* 182 */     String newlang = req.getParameter("lang");
/* 183 */     logger.info("change to new language:" + newlang);
/* 184 */     req.getSession().setAttribute("lang", newlang);
/*     */ 
/* 186 */     String cid = req.getParameter("cid");
/* 187 */     String qid = req.getParameter("qid");
/* 188 */     if ((cid != null) && (!cid.equals("")))
/* 189 */       return "redirect:/cat/" + cid;
/* 190 */     if ((qid != null) && (!qid.equals(""))) {
/* 191 */       return "redirect:/question/" + qid;
/*     */     }
/* 193 */     return "redirect:/";
/*     */   }
/*     */ }

/* Location:           E:\2014097ois-Sunorth-1.1.6\WEB-INF\classes\
 * Qualified Name:     org.orcsun.sunspace.HomeController
 * JD-Core Version:    0.6.2
 */