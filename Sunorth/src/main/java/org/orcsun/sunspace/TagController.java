package org.orcsun.sunspace;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.orcsun.sunspace.dao.CaseDAO;
import org.orcsun.sunspace.dao.TagDAO;
import org.orcsun.sunspace.object.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TagController {
//	@Autowired
//	CaseDAO caseDao;
	@Autowired
	TagDAO tagDao;
	
//	@RequestMapping(value="/tag/search",method=RequestMethod.GET)
//	public String tagSearch(HttpServletRequest req, Model model){
//		String tag = req.getParameter("tag");
//		List cases = caseDao.search(tag,(short)0,10);
//		if(cases == null || cases.size()==0){
//			return "case_init";
//		}else{
//			model.addAttribute("cases",cases );
//			return "case_list";
//		}
//	}
	
	/**
	 * Fetch top  10 tags
	 * @return JSON data
	 */
	@RequestMapping(value ="/tag/top10", method=RequestMethod.GET)
	public @ResponseBody List<Tag> fetchTags(){
		return tagDao.listTag(10);
	}
}
