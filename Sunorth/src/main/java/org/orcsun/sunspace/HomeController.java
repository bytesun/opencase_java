package org.orcsun.sunspace;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.orcsun.sunspace.dao.CaseDAO;
import org.orcsun.sunspace.dao.impl.ActivityDaoImpl;
import org.orcsun.sunspace.dao.impl.AnswerDaoImpl;
import org.orcsun.sunspace.dao.impl.CategoryDaoImpl;
import org.orcsun.sunspace.dao.impl.QuestionDaoImpl;
import org.orcsun.sunspace.dao.impl.TodoDaoImpl;
import org.orcsun.sunspace.dao.impl.UserDaoImpl;
import org.orcsun.sunspace.object.User;
import org.orcsun.sunspace.utils.RestfulUtils;
import org.orcsun.sunspace.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.log4j.Logger;

@Controller
public class HomeController extends SunController {

	
	@Autowired
	CaseDAO caseDao;
	
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
		String lang = this.getLanguage(locale, req);
//		model.addAttribute("topcats", this.catDao.findSubCategory(0L, lang));

		//model.addAttribute("newquestions",
			//	this.quesDao.findLatestQuestions(0,5, lang));
		
		//model.addAttribute("activities", this.actDao.getLatestActivities(10,lang));
		model.addAttribute("cases", caseDao.listAll(10));
		return "home";
	}

	/**
	 * Search an existed case, if no, then navigate to a new one.
	 * @param locale
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value ="/search", method =RequestMethod.POST)
	public String home(HttpServletRequest req, Model model) {
		String tag = req.getParameter("tag");
		List cases = caseDao.search(tag);
		if(cases == null || cases.size()==0){
			return "case_init";
		}else{
			model.addAttribute("cases",cases );
			return "case_list";
		}
		
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
