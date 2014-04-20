package org.orcsun.sunspace;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.dao.impl.TodoDaoImpl;
import org.orcsun.sunspace.object.Todo;
import org.orcsun.sunspace.object.User;
import org.orcsun.sunspace.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Task management controller
 * @author Sun
 *
 */
@Controller
@RequestMapping({ "/todo" })
public class TodoController {

	@Autowired
	TodoDaoImpl todoDao;
	private static final Logger logger = Logger
			.getLogger(TodoController.class);

	/**
	 * Create a new task
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/add" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String newTodo(HttpServletRequest req, Model model) {
		Object user = req.getSession().getAttribute("user");
		if (user != null) {
			try {
				Todo todo = new Todo();
				todo.setTodo(StringUtil.iso2utf8(req.getParameter("todo")));
				todo.setNote(StringUtil.iso2utf8(req.getParameter("note")));

				todo.setPriority(Short.parseShort(req.getParameter("priority")));
				if ((req.getParameter("ttype") != null)
						&& (!req.getParameter("ttype").equals("")))
					todo.setTtype(Short.parseShort(req.getParameter("ttype")));
				Object deadline = req.getParameter("deadline");
				if ((deadline != null) && (!deadline.equals(""))) {
					logger.info(req.getParameter("deadline"));
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyy-MM-dd");
					Date date = formatter.parse((String) deadline);
					todo.setDeadline(date);
				} else {
					todo.setDeadline(new Date());
				}
				todo.setUser((User) user);
				this.todoDao.addTodo(todo);
			} catch (Exception e) {
				logger.error("Failed to conver string date to a Date type:"
						+ e.getMessage());
				model.addAttribute("errormsg", e.getMessage());
			}
		}
		return "redirect:/user/admin";
	}

	/**
	 * Set a flg which indicate the task has been done.
	 * 
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/done" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String doneTodo(HttpServletRequest req) {
		Object user = req.getSession().getAttribute("user");
		if (user != null) {
			Object[] tids = req.getParameterValues("tid");
			if (tids != null) {
				try{
				this.todoDao.doneTodo(tids);
				}catch(Exception e){
					logger.error("Failed to add todo since "+e.getMessage());
				}
			}
		}
		return "redirect:/user/admin";
	}
}
