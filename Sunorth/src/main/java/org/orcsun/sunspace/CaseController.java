package org.orcsun.sunspace;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orcsun.sunspace.dao.CaseDAO;
import org.orcsun.sunspace.object.Case;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/case")
public class CaseController {
	
	@Autowired
	CaseDAO caseDao;
	
	@RequestMapping(value="/init",method=RequestMethod.GET)
	public String initPage(HttpServletRequest req, Model model){
		return "case_init";
	}

	@RequestMapping(value="/new",method=RequestMethod.POST)
	public String newCase(HttpServletRequest req,HttpServletResponse resp, Model model){
		Case c = new Case();
		c.setSubject(req.getParameter("subject"));
		c.setDesc(req.getParameter("description"));
		c.setTag(req.getParameter("tag"));
		c.setPhaseid(0);
		c.setStartdate(new Date());
		c.setEnddate(null);
		c.setStatus((short)0);
		c.setUid(System.currentTimeMillis());
		
		long cid = caseDao.add(c);
		model.addAttribute("thecase", c);
		
		//add a cookie for user
		 Cookie mycookie = new Cookie("mycase",""+cid);
	     resp.addCookie(mycookie);
		return "case";
	}
	
	@RequestMapping(value="/{caseid}",method=RequestMethod.GET)
	public String getCase(@PathVariable long caseid, Model model){
		model.addAttribute("thecase", caseDao.get(caseid));
		return "case";
	}
	
}
