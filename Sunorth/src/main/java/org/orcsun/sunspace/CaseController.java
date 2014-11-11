package org.orcsun.sunspace;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.orcsun.sunspace.dao.CaseDAO;
import org.orcsun.sunspace.dao.ItemDAO;
import org.orcsun.sunspace.dao.PhaseDAO;
import org.orcsun.sunspace.dao.SolutionDAO;
import org.orcsun.sunspace.object.Case;
import org.orcsun.sunspace.object.Item;
import org.orcsun.sunspace.object.Phase;
import org.orcsun.sunspace.object.Solution;
import org.orcsun.sunspace.object.User;
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
	@Autowired
	PhaseDAO phaseDao;
	@Autowired
	ItemDAO itemDao;
	@Autowired
	SolutionDAO solutionDao;
	
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
		Object u = req.getSession().getAttribute("user");
		long uid = System.currentTimeMillis();
		if(u != null){
			uid = ((User)u).getUid();
		}
		c.setUid(uid);
		
		long cid = caseDao.add(c);
		c.setCaseid(cid);
		model.addAttribute("thecase", c);
		
		//add a cookie for user
		 Cookie mycookie = new Cookie("mysuncase",""+cid);
	     resp.addCookie(mycookie);
		return "case";
	}
	
	@RequestMapping(value="/{caseid}",method=RequestMethod.GET)
	public String getCase(@PathVariable long caseid, Model model){
		Case c = caseDao.get(caseid);
		int phaseid = c.getPhaseid();
		model.addAttribute("thecase", c);
		model.addAttribute("theitems",itemDao.list(phaseid, caseid));
		model.addAttribute("thephase", phaseDao.get(phaseid, caseid));
		model.addAttribute("comments", solutionDao.listForPhase(caseid, phaseid));
		return "case";
	}

	@RequestMapping(value="/{caseid}/{phaseid}",method=RequestMethod.GET)
	public String getCase(@PathVariable long caseid, @PathVariable int phaseid, Model model){
		Case c = caseDao.get(caseid);
		//int phaseid = c.getPhaseid();
		model.addAttribute("thecase", c);
		model.addAttribute("theitems",itemDao.list(phaseid, caseid));
		model.addAttribute("thephase", phaseDao.get(phaseid, caseid));
		model.addAttribute("comments", solutionDao.listForPhase(caseid, phaseid));
		return "case";
	}
	
	@RequestMapping(value="/newphase",method=RequestMethod.POST)
	public String newPhase(HttpServletRequest req){
		int thePhaseId = Integer.parseInt(req.getParameter("thephaseid"));
		long theCaseId = Long.parseLong(req.getParameter("thecaseid"));
		
		//add new phase
		Phase p =  new Phase();
		p.setPhaseid(thePhaseId+1);
		p.setCaseid(theCaseId);
		p.setPhase(req.getParameter("phase"));
		p.setNote(req.getParameter("description"));
		p.setTag(req.getParameter("tag"));
		p.setStartdate(new Date());
		
		phaseDao.add(p);
		//update the current phase id in case table
		caseDao.nextPhase(theCaseId);
		
		return "redirect:/case/"+theCaseId;
		
	}
	
	@RequestMapping(value="/newitem",method=RequestMethod.POST)
	public String newItem(HttpServletRequest req,Model model){
		int thePhaseId = Integer.parseInt(req.getParameter("thephaseid"));
		long theCaseId = Long.parseLong(req.getParameter("thecaseid"));
		Item item = new Item();
		item.setItem(req.getParameter("item"));
		item.setDotime(new Date());
		item.setPhaseid(thePhaseId);
		item.setCaseid(theCaseId);
		itemDao.add(item);
		
		return "redirect:/case/"+theCaseId;
		
	}
	
	@RequestMapping(value="/newcomment",method=RequestMethod.POST)
	public String newComment(HttpServletRequest req, Model model){
		int thePhaseId = Integer.parseInt(req.getParameter("thephaseid"));
		long theCaseId = Long.parseLong(req.getParameter("thecaseid"));

		String comment = req.getParameter("comment");
		if(comment != null && !comment.trim().equals("")){
			Solution s = new Solution();
			s.setCaseid(theCaseId);
			s.setPhaseid(thePhaseId);
			s.setComment(comment);
			s.setCreatetime(new Date());
			s.setUid(System.currentTimeMillis());
			solutionDao.add(s);
		}
		
		return "redirect:/case/"+theCaseId;
	}
}
