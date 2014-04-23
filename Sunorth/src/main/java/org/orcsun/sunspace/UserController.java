package org.orcsun.sunspace;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.dao.impl.TodoDaoImpl;
import org.orcsun.sunspace.dao.impl.UserDaoImpl;
import org.orcsun.sunspace.dao.impl.UserLogDaoImpl;
import org.orcsun.sunspace.object.User;
import org.orcsun.sunspace.object.UserLog;
import org.orcsun.sunspace.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
			return "redirect:/user/redirectLogin";
		}

		model.addAttribute("todos",
				this.todoDao.findTodayTodos(((User) u).getUid()));

		return "user_console";
	}
	
	
	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String userUpdate(HttpServletRequest req, Model model){
		Object u = req.getSession().getAttribute("user");
		if(u!=null){
			User user = (User)u;
			try {
				user.setName(StringUtil.iso2utf8(req.getParameter("name")));
				user.setTitle(StringUtil.iso2utf8(req.getParameter("title")));
				user.setProfile(StringUtil.iso2utf8(req.getParameter("profile")));
				user.setResume(StringUtil.iso2utf8(req.getParameter("resume")));
				userDao.updateUser((User)u);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
			}

			return "user_console";			
		}else{
			return "redirect:/user/redirectLogin";
		}
	}
	
	
	@RequestMapping(value="/{uid}",method=RequestMethod.GET)
	public String userInfo(@PathVariable long uid,Model model){

		model.addAttribute("userinfo", userDao.findUserByID(uid));
		model.addAttribute("ulogs", ulogDao.findMyLogs(uid,0,20));
		return "user_info";
	}
	
	@RequestMapping(value="/moreUlog/{uid}",method=RequestMethod.GET)
	public @ResponseBody List<UserLog> moreUserLog(@PathVariable long uid,HttpServletRequest req){
		String start = req.getParameter("start");
		int istart=0;
		if(start!=null && !start.equals(""))
			istart = Integer.parseInt(start);
		int iend = istart+20;
		String end = req.getParameter("end");
		if(end !=null && !end.equals(""))
			iend = Integer.parseInt(end);
		
		return ulogDao.findMyLogs(uid, istart, iend);
	}
	
	@RequestMapping(value="/ulog/new",method=RequestMethod.POST)
	public String addUserLog(HttpServletRequest req,Model model){
		Object u = req.getSession().getAttribute("user");
		if(u!=null){
			User user = (User)u;
			UserLog ulog = new UserLog();
			ulog.setUid(user.getUid());
			
			try {
				ulog.setTag(StringUtil.iso2utf8(req.getParameter("tag")));
				ulog.setSubject(StringUtil.iso2utf8(req.getParameter("subject")));
				ulog.setUlog(StringUtil.iso2utf8(req.getParameter("ulog")));
				String ispub = req.getParameter("ispublic");
				if(ispub != null && !ispub.equals(""))ulog.setStatus(1);
				
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
			}

			ulogDao.newLog(ulog);
			logger.debug("Added a new log:"+ulog.getSubject());
			return "user_console";			
		}else{
			return "redirect:/user/redirectLogin";
		}
		
	}
	/**
	 * Redirect to login page
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/redirectLogin" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String redirectLogin(HttpServletRequest req, Model model) {
		model.addAttribute("cid", req.getParameter("cid"));
		model.addAttribute("qid", req.getParameter("qid"));
		
		String state = new BigInteger(130, new SecureRandom()).toString(32);
		req.getSession().setAttribute("state", state);
		model.addAttribute("state",  state);
		model.addAttribute("clientid", SunConstants.GOOGLE_API_CLIENT_ID);
		return "login";
	}

	/**
	 * Do login 
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/login" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String login(HttpServletRequest req, Model model) {
		String email = req.getParameter("email");

		boolean islogin = false;
		if ((email != null) && (!email.equals(""))) {
			User u = this.userDao.findUserByEmail(email);
			if (u != null) {
				String passwd = req.getParameter("passwd");
				if ((passwd != null) && (!passwd.equals(""))
						&& (u.getPasswd().equals(StringUtil.encodeByMD5(passwd)))) {
					req.getSession().setAttribute("user", u);
					islogin = true;
				}
			}
		}
		String cid = req.getParameter("cid");
		String qid = req.getParameter("qid");
		if (islogin) {
			if ((cid != null) && (!cid.equals("")))
				return "redirect:/cat/"+req.getSession().getAttribute("lang") +"/"+ cid;
			if ((qid != null) && (!qid.equals(""))) {
				return "redirect:/question/"+req.getSession().getAttribute("lang") +"/" + qid;
			}
			return "redirect:/user/admin";
		}

		return "redirect:/user/redirectLogin?cid=" + cid + "&qid=" + qid;
	}

	/**
	 * Do logout
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/logout" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String logout(HttpServletRequest req) {
		req.getSession().setAttribute("user", null);
		return "redirect:/";
	}

	/**
	 * Register
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/register" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String register(HttpServletRequest req, Model model) {
		String name = req.getParameter("name");
		String pwd = req.getParameter("passwd");
		String email = req.getParameter("email");

		String cname = name;
		boolean islogin = false;
		try {
			cname = StringUtil.iso2utf8(name);

			User u = new User();

			u.setName(cname);
			u.setPasswd(StringUtil.encodeByMD5(pwd));
			u.setEmail(email);

			u.setUid(this.userDao.addUser(u));
			req.getSession().setAttribute("user", u);
			islogin = true;
		} catch (Exception e) {
			logger.error("Failed to register user:" + e.getMessage());
			model.addAttribute("errormsg", e.getMessage());
		}
		String cid = req.getParameter("cid");
		String qid = req.getParameter("qid");
		if (islogin) {
			if ((cid != null) && (!cid.equals("")))
				return "redirect:/cat/"+req.getSession().getAttribute("lang")+"/" + cid;
			if ((qid != null) && (!qid.equals(""))) {
				return "redirect:/question/"+req.getSession().getAttribute("lang")+"/"  + qid;
			}
			return "user_console";
		}

		return "redirect:/user/redirectLogin?cid=" + cid + "&qid=" + qid;
	}

	/**
	 * Initial register page
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/register/new" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String initRegister(HttpServletRequest req, Model model) {
		model.addAttribute("cid", req.getParameter("cid"));
		model.addAttribute("qid", req.getParameter("qid"));
		return "register";
	}	
}
