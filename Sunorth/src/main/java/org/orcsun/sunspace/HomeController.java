package org.orcsun.sunspace;


import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.orcsun.sunspace.dao.CaseDAO;
import org.orcsun.sunspace.dao.SolutionDAO;
import org.orcsun.sunspace.object.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.apache.log4j.Logger;

@Controller
public class HomeController extends SunController {

	@Autowired
	CaseDAO caseDao;
	@Autowired
	SolutionDAO solutionDao;
	
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
//		String lang = this.getLanguage(locale, req);
		Case c = caseDao.get(1);
		model.addAttribute("comments", solutionDao.listForPhase(c.getCaseid(), c.getPhaseid()));
//		model.addAttribute("cases", caseDao.listAll(10));
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
