/*    */ package org.orcsun.sunspace;
/*    */ 
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpSession;
/*    */ import org.orcsun.sunspace.dao.impl.TodoDaoImpl;
/*    */ import org.orcsun.sunspace.object.Todo;
/*    */ import org.orcsun.sunspace.object.User;
/*    */ import org.orcsun.sunspace.utils.StringUtil;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
/*    */ import org.springframework.beans.factory.annotation.Autowired;
/*    */ import org.springframework.stereotype.Controller;
/*    */ import org.springframework.ui.Model;
/*    */ import org.springframework.web.bind.annotation.RequestMapping;
/*    */ 
/*    */ @Controller
/*    */ @RequestMapping({"/todo"})
/*    */ public class TodoController
/*    */ {
/*    */ 
/*    */   @Autowired
/*    */   TodoDaoImpl todoDao;
/* 27 */   private static final Logger logger = LoggerFactory.getLogger(TodoController.class);
/*    */ 
/*    */   @RequestMapping(value={"/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*    */   public String newTodo(HttpServletRequest req, Model model) {
/* 31 */     Object user = req.getSession().getAttribute("user");
/* 32 */     if (user != null) {
/*    */       try {
/* 34 */         Todo todo = new Todo();
/* 35 */         todo.setTodo(StringUtil.utf8(req.getParameter("todo"), "iso-8859-1"));
/* 36 */         todo.setNote(StringUtil.utf8(req.getParameter("note"), "iso-8859-1"));
/*    */ 
/* 38 */         todo.setPriority(Short.parseShort(req.getParameter("priority")));
/* 39 */         if ((req.getParameter("ttype") != null) && (!req.getParameter("ttype").equals("")))
/* 40 */           todo.setTtype(Short.parseShort(req.getParameter("ttype")));
/* 41 */         Object deadline = req.getParameter("deadline");
/* 42 */         if ((deadline != null) && (!deadline.equals(""))) {
/* 43 */           logger.info(req.getParameter("deadline"));
/* 44 */           SimpleDateFormat formatter = new SimpleDateFormat("yyy-MM-dd");
/* 45 */           Date date = formatter.parse((String)deadline);
/* 46 */           todo.setDeadline(date);
/*    */         }
/* 48 */         todo.setUser((User)user);
/* 49 */         this.todoDao.addTodo(todo);
/*    */       } catch (Exception e) {
/* 51 */         logger.error("Failed to conver string date to a Date type:" + e.getMessage());
/* 52 */         model.addAttribute("errormsg", e.getMessage());
/*    */       }
/*    */     }
/* 55 */     return "redirect:/user/admin";
/*    */   }
/*    */ 
/*    */   @RequestMapping(value={"/done"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
/*    */   public String doneTodo(HttpServletRequest req) {
/* 60 */     Object user = req.getSession().getAttribute("user");
/* 61 */     if (user != null) {
/* 62 */       Object[] tids = req.getParameterValues("tid");
/* 63 */       if (tids != null) {
/* 64 */         this.todoDao.doneTodo(tids);
/*    */       }
/*    */     }
/* 67 */     return "redirect:/user/admin";
/*    */   }
/*    */ }

/* Location:           E:\2014097ois-Sunorth-1.1.6\WEB-INF\classes\
 * Qualified Name:     org.orcsun.sunspace.TodoController
 * JD-Core Version:    0.6.2
 */