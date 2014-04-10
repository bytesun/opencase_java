/*     */ package org.orcsun.sunspace;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.util.List;
/*     */ import java.util.Locale;

/*     */ import javax.servlet.http.HttpServletRequest;
/*     */ import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
/*     */ import org.orcsun.sunspace.dao.impl.CategoryDaoImpl;
/*     */ import org.orcsun.sunspace.dao.impl.QuestionDaoImpl;
/*     */ import org.orcsun.sunspace.object.Category;
/*     */ import org.orcsun.sunspace.object.User;
/*     */ import org.orcsun.sunspace.utils.StringUtil;

/*     */ import org.springframework.beans.factory.annotation.Autowired;
/*     */ import org.springframework.stereotype.Controller;
/*     */ import org.springframework.ui.Model;
/*     */ import org.springframework.web.bind.annotation.PathVariable;
/*     */ import org.springframework.web.bind.annotation.RequestMapping;
/*     */ 
/*     */ @Controller
/*     */ @RequestMapping({"/cat"})
/*     */ public class CatController
/*     */ {
/*  38 */   private static final Logger logger = Logger.getLogger(CatController.class);
/*     */ 
/*     */   @Autowired
/*     */   CategoryDaoImpl catDao;
/*     */ 
/*     */   @Autowired
/*     */   QuestionDaoImpl quesDao;
/*     */ 
/*  49 */   @RequestMapping(value={"/{cid}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String listCats(Locale locale, @PathVariable long cid, Model model, HttpServletRequest req) { String lang = "en";
/*  50 */     Object olang = req.getSession().getAttribute("lang");
/*  51 */     if (olang != null) {
/*  52 */       lang = (String)olang;
/*     */     }
/*  54 */     else if (locale.getLanguage().equalsIgnoreCase("zh")) {
/*  55 */       lang = "zh";
/*     */     }
/*     */ 
/*  58 */     if (cid != 0L) {
/*  59 */       Category thecat = this.catDao.getCategory(cid, lang);
/*  60 */       model.addAttribute("thecat", thecat);
/*  61 */       if (thecat.getPid() != 0L) {
/*  62 */         model.addAttribute("upcat", this.catDao.getCategory(thecat.getPid(), lang));
/*     */       }
/*     */     }
/*  65 */     List subcats = this.catDao.findSubCategory(cid, lang);
/*  66 */     logger.debug("size of sub category:" + subcats.size());
/*     */ 
/*  68 */     if ((subcats != null) && (subcats.size() > 0)) {
/*  69 */       model.addAttribute("subcats", subcats);
/*     */     }
/*  71 */     model.addAttribute("questions", this.quesDao.findQuestionsByCID(cid, lang));
/*     */ 
/*  73 */     return "question_list";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*     */   public String addCat(Locale locale, HttpServletRequest req, Model model)
/*     */   {
/*  81 */     User u = (User)req.getSession().getAttribute("user");
/*  82 */     if (u != null) {
/*  83 */       String lang = "en";
/*  84 */       Object olang = req.getSession().getAttribute("lang");
/*  85 */       if (olang != null) {
/*  86 */         lang = (String)olang;
/*     */       }
/*  88 */       else if (locale.getLanguage().equalsIgnoreCase("zh")) {
/*  89 */         lang = "zh";
/*     */       }
/*  91 */       String spid = req.getParameter("pid");
/*  92 */       String catName = "";
/*     */       try {
/*  94 */         catName = StringUtil.utf8(req.getParameter("catName"), "iso-8859-1");
/*     */       } catch (UnsupportedEncodingException e) {
/*  96 */         logger.warn("Can't convert the string to utf8:" + catName + e.getMessage());
/*     */       }
/*  98 */       Category c = new Category();
/*  99 */       c.setCatname(catName);
/* 100 */       c.setPid(Long.parseLong(spid));
/* 101 */       this.catDao.addCategory(c, lang);
/*     */ 
/* 103 */       return "redirect:/cat/admin/" + spid;
/*     */     }
/* 105 */     return "redirect:/redirectLogin";
/*     */   }
/*     */ 
/*     */   @RequestMapping(value={"/admin/{pid}"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
/*     */   public String listCat(@PathVariable long pid, Locale locale, HttpServletRequest req, Model model)
/*     */   {
/* 112 */     User u = (User)req.getSession().getAttribute("user");
/* 113 */     if (u != null) {
/* 114 */       String lang = "en";
/* 115 */       Object olang = req.getSession().getAttribute("lang");
/* 116 */       if (olang != null) {
/* 117 */         lang = (String)olang;
/*     */       }
/* 119 */       else if (locale.getLanguage().equalsIgnoreCase("zh")) {
/* 120 */         lang = "zh";
/*     */       }
/* 122 */       model.addAttribute("categories", this.catDao.findSubCategory(pid, lang));
/* 123 */       return "admin_category";
/*     */     }
/* 125 */     return "redirect:/redirectLogin";
/*     */   }
/*     */ }

