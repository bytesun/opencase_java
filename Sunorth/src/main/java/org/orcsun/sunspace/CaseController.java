package org.orcsun.sunspace;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import org.orcsun.sunspace.utils.StringUtil;
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
		Object preCase = req.getSession().getAttribute("preCase");
		if(preCase != null){
			long preCaseTime = ((Long)preCase).longValue();
			if((System.currentTimeMillis()-preCaseTime)<(60*1000)){
				model.addAttribute("notice", "Be patient! ^_^ ");
				return "notice";
			}
		}else{
			req.getSession().setAttribute("preCase", System.currentTimeMillis());
		}
		Case c = new Case();
		try {
			c.setSubject(StringUtil.iso2utf8(req.getParameter("subject")));
			c.setDesc(StringUtil.iso2utf8(req.getParameter("description")));			
			c.setTag(StringUtil.iso2utf8(req.getParameter("tag")));
		} catch (UnsupportedEncodingException e) {
			model.addAttribute("notice","Failed to get utf8 code :"+ e.getMessage());
			return "notice";
		}
		c.setPhaseid(0);
		c.setStartdate(new Date());
		c.setEnddate(null);
		c.setStatus((short)0);
		c.setCtype(Short.parseShort(req.getParameter("ctype")));
		Object u = req.getSession().getAttribute("user");
		long uid = System.currentTimeMillis();
		if(u != null){
			uid = ((User)u).getUid();
		}
		c.setUid(uid);
		String isOpen = req.getParameter("isOpen");//if the case is public or private
		if(isOpen != null && !isOpen.equals(""))
			c.setOpen((boolean)Boolean.parseBoolean(isOpen));
		
		long cid = caseDao.add(c);
		c.setCaseid(cid);
		model.addAttribute("thecase", c);
		
		//add a cookie for user
		 Cookie mycookie = new Cookie("mysuncase",""+cid);
	     resp.addCookie(mycookie);
		return "case";
	}
	
	@RequestMapping(value="/{caseid}",method=RequestMethod.GET)
	public String getCase(HttpServletRequest req,@PathVariable long caseid, Model model){
		Case c = caseDao.get(caseid);
		if(!c.isOpen()){
			Object user = req.getSession().getAttribute("user");
			if(user == null || ((User)user).getUid() != c.getUid()){
				model.addAttribute("notice", "This case is private, so you can't view it without permission!");
				return "notice";
			}
		}
		int phaseid = c.getPhaseid();
		model.addAttribute("thecase", c);
		model.addAttribute("theitems",itemDao.list(phaseid, caseid));
		model.addAttribute("thephase", phaseDao.get(phaseid, caseid));
		model.addAttribute("comments", solutionDao.listForPhase(caseid, phaseid));
		return "case";
	}

	@RequestMapping(value="/{caseid}/{phaseid}",method=RequestMethod.GET)
	public String getCase(HttpServletRequest req,@PathVariable long caseid, @PathVariable int phaseid, Model model){
		Case c = caseDao.get(caseid);
		if(!c.isOpen()){
			Object user = req.getSession().getAttribute("user");
			if(user == null || ((User)user).getUid() != c.getUid()){
				model.addAttribute("notice", "This case is private, so you can't view it without permission!");
				return "notice";
			}
		}		
		//int phaseid = c.getPhaseid();
		model.addAttribute("thecase", c);
		model.addAttribute("theitems",itemDao.list(phaseid, caseid));
		model.addAttribute("thephase", phaseDao.get(phaseid, caseid));
		model.addAttribute("comments", solutionDao.listForPhase(caseid, phaseid));
		return "case";
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
		String searchKey = req.getParameter("searchKey");
		List cases = caseDao.search(searchKey,Short.parseShort(req.getParameter("ctype")),10);
		if(cases == null || cases.size()==0){
			return "case_init";
		}else{
//			model.addAttribute("searchKey", searchKey);
			model.addAttribute("cases",cases );
			return "case_list";
		}
		
	}	
	
	/**
	 * List cases base on request type 
	 * @param listType
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/list/{listType}",method=RequestMethod.GET)
	public String list(@PathVariable int listType, Model model){
		model.addAttribute("cases", caseDao.listCases(listType,10));
		return "case_list";
	}
	@RequestMapping(value="/newphase",method=RequestMethod.POST)
	public String newPhase(HttpServletRequest req, Model model){
		int thePhaseId = Integer.parseInt(req.getParameter("thephaseid"));
		long theCaseId = Long.parseLong(req.getParameter("thecaseid"));
		
		//add new phase
		Phase p =  new Phase();
		p.setPhaseid(thePhaseId+1);
		p.setCaseid(theCaseId);
		
		try {
			p.setPhase(StringUtil.iso2utf8(req.getParameter("phase")));
			p.setNote(StringUtil.iso2utf8(req.getParameter("description")));
			p.setTag(StringUtil.iso2utf8(req.getParameter("tag")));
		} catch (UnsupportedEncodingException e) {
			model.addAttribute("notice","Failed to get utf8 code :"+ e.getMessage());
			return "notice";
		}
		
		p.setStartdate(new Date());
		
		phaseDao.add(p);
		//update the current phase id in case table
		caseDao.nextPhase(theCaseId);
		Object isClose = req.getParameter("isClose");
		if(isClose != null){
			caseDao.changeStatus(theCaseId, SunConstants.CASE_STATUS_CLOSE);
		}
		
		return "redirect:/case/"+theCaseId;
		
	}
	
	@RequestMapping(value="/newitem",method=RequestMethod.POST)
	public String newItem(HttpServletRequest req,Model model){
		int thePhaseId = Integer.parseInt(req.getParameter("thephaseid"));
		long theCaseId = Long.parseLong(req.getParameter("thecaseid"));
		Item item = new Item();
		try {
			item.setItem(StringUtil.iso2utf8(req.getParameter("item")));

		} catch (UnsupportedEncodingException e) {
			model.addAttribute("notice","Failed to get utf8 code :"+ e.getMessage());
			return "notice";
		}
		item.setDotime(new Date());
		item.setPhaseid(thePhaseId);
		item.setCaseid(theCaseId);
		itemDao.add(item);
		
		return "redirect:/case/"+theCaseId;
		
	}
	
	@RequestMapping(value="/newcomment",method=RequestMethod.POST)
	public String newComment(HttpServletRequest req, Model model){
		
		Object preComment = req.getSession().getAttribute("preComment");
		if(preComment != null){
			long preCommentTime = ((Long)preComment);
			if((System.currentTimeMillis()-preCommentTime)<(new Random(60).nextInt())*1000){
				model.addAttribute("notice", "Be patient! ^_^ ");
				return "notice";
			}
		}else{
			req.getSession().setAttribute("preComment", System.currentTimeMillis());
		}
		
		int thePhaseId = Integer.parseInt(req.getParameter("thephaseid"));
		long theCaseId = Long.parseLong(req.getParameter("thecaseid"));

		String comment = req.getParameter("comment");
		if(comment != null && !comment.trim().equals("")){
			Solution s = new Solution();
			s.setCaseid(theCaseId);
			s.setPhaseid(thePhaseId);
			try {
				s.setComment(StringUtil.iso2utf8(comment));
			} catch (UnsupportedEncodingException e) {
				model.addAttribute("notice","Failed to get utf8 code :"+ e.getMessage());
				return "notice";
			}
			s.setCreatetime(new Date());
			s.setUid(System.currentTimeMillis());
			solutionDao.add(s);
		}
		
		return "redirect:/case/"+theCaseId;
	}
}
