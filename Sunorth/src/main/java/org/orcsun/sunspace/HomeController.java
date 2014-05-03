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
				this.quesDao.findLatestQuestions(0,20, lang));


		return "home";
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
			return "redirect:/cat/" +req.getSession().getAttribute("lang")+"/" + cid;
		if ((qid != null) && (!qid.equals(""))) {
			return "redirect:/question/"+req.getSession().getAttribute("lang")+"/"  + qid;
		}
		return "redirect:/";
	}
}
