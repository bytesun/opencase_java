package org.orcsun.sunspace;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.dao.impl.CategoryDaoImpl;
import org.orcsun.sunspace.dao.impl.QuestionDaoImpl;
import org.orcsun.sunspace.object.Category;
import org.orcsun.sunspace.object.User;
import org.orcsun.sunspace.utils.StringUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping({ "/cat" })
public class CatController extends SunController {
	private static final Logger logger = Logger.getLogger(CatController.class);

	@Autowired
	CategoryDaoImpl catDao;

	@Autowired
	QuestionDaoImpl quesDao;

	@RequestMapping(value = { "/{lang}/{cid}" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String listCats(Locale locale, @PathVariable String lang,
			@PathVariable long cid, Model model, HttpServletRequest req) {

		if (cid != 0L) {
			Category thecat = this.catDao.getCategory(cid, lang);
			model.addAttribute("thecat", thecat);
			if (thecat.getPid() != 0L) {
				model.addAttribute("upcat",
						this.catDao.getCategory(thecat.getPid(), lang));
			}
		}
		List subcats = this.catDao.findSubCategory(cid, lang);
		logger.debug("size of sub category:" + subcats.size());

		if ((subcats != null) && (subcats.size() > 0)) {
			model.addAttribute("subcats", subcats);
		}
		model.addAttribute("questions",
				this.quesDao.findQuestionsByCID(cid, lang));

		return "question_list";
	}

	@RequestMapping(value = { "/add" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String addCat(Locale locale, HttpServletRequest req, Model model) {
		User u = (User) req.getSession().getAttribute("user");
		if (u != null) {
			String lang = getLanguage(locale, req);

			String spid = req.getParameter("pid");
			String catName = "";
			try {
				catName = StringUtil.utf8(req.getParameter("catName"),
						"iso-8859-1");
			} catch (UnsupportedEncodingException e) {
				logger.warn("Can't convert the string to utf8:" + catName
						+ e.getMessage());
			}
			Category c = new Category();
			c.setCatname(catName);
			c.setPid(Long.parseLong(spid));
			this.catDao.addCategory(c, lang);

			return "redirect:/cat/admin/" + spid;
		}
		return "redirect:/redirectLogin";
	}

	@RequestMapping(value = { "/admin/{pid}" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String listCat(@PathVariable long pid, Locale locale,
			HttpServletRequest req, Model model) {
		User u = (User) req.getSession().getAttribute("user");
		if (u != null) {
			String lang = getLanguage(locale, req);

			model.addAttribute("categories",
					this.catDao.findSubCategory(pid, lang));
			return "admin_category";
		}
		return "redirect:/redirectLogin";
	}
}
