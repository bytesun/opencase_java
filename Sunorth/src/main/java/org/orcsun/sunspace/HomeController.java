package org.orcsun.sunspace;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.orcsun.sunspace.dao.impl.AnswerDaoImpl;
import org.orcsun.sunspace.dao.impl.CategoryDaoImpl;
import org.orcsun.sunspace.dao.impl.QuestionDaoImpl;
import org.orcsun.sunspace.dao.impl.TodoDaoImpl;
import org.orcsun.sunspace.dao.impl.UserDaoImpl;
import org.orcsun.sunspace.object.User;
import org.orcsun.sunspace.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.log4j.Logger;

@Controller
public class HomeController {

	
	@Autowired
	QuestionDaoImpl quesDao;

	@Autowired
	UserDaoImpl userDao;

	@Autowired
	CategoryDaoImpl catDao;

	@Autowired
	AnswerDaoImpl ansDao;

	@Autowired
	TodoDaoImpl todoDao;
	
	static Logger logger = Logger.getLogger(HomeController.class);
	/**
	 * Home page
	 * @param locale
	 * @param req
	 * @param model
	 * @return
	 */
	
	@RequestMapping(value = { "/" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String home(Locale locale, HttpServletRequest req, Model model) {
		logger.info("The locale is " + locale);
		String lang = "en";
		Object olang = req.getSession().getAttribute("lang");
		if (olang != null) {
			lang = (String) olang;
		} else if (locale.getLanguage().equalsIgnoreCase("zh")) {
			lang = "zh";
		}
		req.getSession().setAttribute("lang", lang);

		model.addAttribute("topcats", this.catDao.findSubCategory(0L, lang));

		model.addAttribute("newquestions",
				this.quesDao.findNewQuestions(10, lang));


		return "home";
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
				return "redirect:/cat/" + cid;
			if ((qid != null) && (!qid.equals(""))) {
				return "redirect:/question/" + qid;
			}
			return "redirect:/user/admin";
		}

		return "redirect:/redirectLogin?cid=" + cid + "&qid=" + qid;
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
			cname = StringUtil.utf8(name, "iso-8859-1");

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
				return "redirect:/cat/" + cid;
			if ((qid != null) && (!qid.equals(""))) {
				return "redirect:/question/" + qid;
			}
			return "user_console";
		}

		return "redirect:/redirectLogin?cid=" + cid + "&qid=" + qid;
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

	/**
	 * Change repository language
	 * @param req
	 * @return
	 */
	@RequestMapping(value = { "/chglang" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String changeLang(HttpServletRequest req) {
		String newlang = req.getParameter("lang");
		logger.info("change to new language:" + newlang);
		req.getSession().setAttribute("lang", newlang);

		String cid = req.getParameter("cid");
		String qid = req.getParameter("qid");
		if ((cid != null) && (!cid.equals("")))
			return "redirect:/cat/" + cid;
		if ((qid != null) && (!qid.equals(""))) {
			return "redirect:/question/" + qid;
		}
		return "redirect:/";
	}
}
