package org.orcsun.sunspace;


import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.dao.impl.TodoDaoImpl;
import org.orcsun.sunspace.dao.impl.UserDaoImpl;
import org.orcsun.sunspace.dao.impl.UserLogDaoImpl;
import org.orcsun.sunspace.object.User;
import org.orcsun.sunspace.object.UserLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * All user relative operations
 * @author Sun
 * 2014/4/9
 * @since 1.0
 */

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserDaoImpl userDao;
	@Autowired
	TodoDaoImpl todoDao;
	@Autowired
	UserLogDaoImpl ulogDao;
	
	private static final Logger logger = Logger.getLogger(UserController.class);
	/**
	 * User page
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/admin" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String userAdmin(HttpServletRequest req, Model model) {
		Object u = req.getSession().getAttribute("user");
		if (u == null) {
			return "redirect:/redirectLogin";
		}
		long uid = ((User) u).getUid();
		String lang = (String) req.getSession().getAttribute("lang");

		model.addAttribute("todos",
				this.todoDao.findTodayTodos(((User) u).getUid()));

		return "user_console";
	}
	
	@RequestMapping(value="/{uid}",method=RequestMethod.GET)
	public String userInfo(@PathVariable long uid,Model model){

		model.addAttribute("userinfo", userDao.findUserByID(uid));
		model.addAttribute("ulogs", ulogDao.findMyLogs(uid,0,20));
		return "user_info";
	}
	
	@RequestMapping(value="/ulog/new",method=RequestMethod.POST)
	public String addUserLog(HttpServletRequest req,Model model){
		Object u = req.getSession().getAttribute("user");
		if(u!=null){
			User user = (User)u;
			UserLog ulog = new UserLog();
			ulog.setUid(user.getUid());
			ulog.setTag(req.getParameter("tag"));
			ulog.setSubject(req.getParameter("subject"));
			ulog.setUlog(req.getParameter("ulog"));
			ulogDao.newLog(ulog);
			logger.debug("Added a new log:"+ulog.getSubject());
			return "user_console";			
		}else{
			return "redirect:/redirectLogin";
		}
		
	}
}
