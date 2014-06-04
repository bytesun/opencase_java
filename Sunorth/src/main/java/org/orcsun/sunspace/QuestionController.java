package org.orcsun.sunspace;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.dao.impl.AnswerDaoImpl;
import org.orcsun.sunspace.dao.impl.CategoryDaoImpl;
import org.orcsun.sunspace.dao.impl.QuestionDaoImpl;
import org.orcsun.sunspace.object.Answer;
import org.orcsun.sunspace.object.Comment;
import org.orcsun.sunspace.object.Question;
import org.orcsun.sunspace.object.User;
import org.orcsun.sunspace.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping({ "/question" })
public class QuestionController extends SunController{

	@Autowired
	QuestionDaoImpl quesDao;

	@Autowired
	AnswerDaoImpl answerDao;

	@Autowired
	CategoryDaoImpl catDao;
	private static final Logger logger = Logger
			.getLogger(QuestionController.class);

	@RequestMapping(value = { "/{lang}/{qid}" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String showQuestion(Locale locale, 
			@PathVariable String lang,
			@PathVariable long qid,
			
			HttpServletRequest req, Model model) {

		Question q = this.quesDao.getQuestion(qid, lang);
		if (q != null) {
			long cid = q.getCid();
			if (cid > 0L)
				model.addAttribute("pcategory",
						this.catDao.getCategory(cid, lang));
			else {
				model.addAttribute("pquestion",
						this.quesDao.getQuestion(q.getPid(), lang));
			}
			String start = req.getParameter("start");
			int istart=0;
			if(start!=null && !start.equals(""))
				istart = Integer.parseInt(start);
			int iend = istart+20;
			String end = req.getParameter("end");
			if(end !=null && !end.equals(""))
				iend = Integer.parseInt(end);
			model.addAttribute("question", q);
			model.addAttribute("answers", this.answerDao.findAnswers(qid, lang));
			model.addAttribute("comments", this.quesDao.findComments(qid, lang,istart,iend));
			model.addAttribute("followups",
					this.quesDao.findQuestionsByPID(qid, lang,istart,iend));
		}
		return "question";
	}

	
	@RequestMapping(value="/latestQuestions",method=RequestMethod.GET)
	public @ResponseBody List<Question> findLatestQuestions(Locale locale,HttpServletRequest req){
		String start= req.getParameter("start");
		int istart = 0;
		if(start != null && !start.equals(""))
			istart = Integer.parseInt(start);
		String end = req.getParameter("end");
		int iend = 20;
		if(end != null && !end.equals(""))
			iend = istart+Integer.parseInt(end);
		
		return quesDao.findLatestQuestions(istart, iend, this.getLanguage(locale, req));
	}
	
	/**
	 * qid is parent question id, if it's zero, then set 0 to category id
	 * @param locale
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value = { "/ask" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String askQuestion(Locale locale, HttpServletRequest req, Model model) {
		Object u = req.getSession().getAttribute("user");
		String lang = getLanguage(locale,req);
		if (u != null) {
			long cid = Long.parseLong(req.getParameter("cid"));
			long qid = Long.parseLong(req.getParameter("qid"));
			try {
				
				Question q = new Question();
				String qtitle = req.getParameter("questitle");
				q.setQuestion(StringUtil.iso2utf8(qtitle));
				q.setPid(qid);
				q.setCid(cid);
				String question = req.getParameter("question");
				if (question != null)
					q.setDescription(StringUtil.iso2utf8(question));

				String tag = req.getParameter("tag");
				if (tag != null)
					q.setTag(StringUtil.iso2utf8(tag));
				q.setUser((User) u);
				this.quesDao.addQuestion(q, lang);
				
			} catch (Exception e) {
				logger.error(e.getMessage());
				model.addAttribute("msg", e.getMessage());
			}
			if(qid>0){
				return "redirect:/question/"+lang+"/"+qid;
			}else
				return "redirect:/cat/"+lang+"/"+cid;
		}else{
			return "redirect:/user/loginRedirect";
		}

	}

	@RequestMapping(value="/{qid}/edit",method=RequestMethod.POST)
	public String update(Locale locale,@PathVariable long qid,HttpServletRequest req,Model model){
		Object u = req.getSession().getAttribute("user");
		String lang = getLanguage(locale,req);
		if(u != null){

			Question q = new Question();
			q.setQid(qid);
			
			try {
				q.setQuestion(StringUtil.iso2utf8(req.getParameter("question")));
				q.setDescription(StringUtil.iso2utf8(req.getParameter("questiondesc")));
				q.setTag(StringUtil.iso2utf8(req.getParameter("tag")));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
				
			}
			
			quesDao.updateQuestion(q, lang);
		}return "redirect:/question/"+lang+"/"+qid;
	}
	
	@RequestMapping(value="/{qid}/vote/{vote}", method=RequestMethod.GET)
	public @ResponseBody int voteup(Locale locale,@PathVariable long qid,@PathVariable int vote,HttpServletRequest req){
		Object u = req.getSession().getAttribute("user");
		String lang = getLanguage(locale,req);
		if (u != null) {
			User user = (User)u;
			return quesDao.vote(user.getUid(), qid, lang, vote);
		}else{
			return SunConstants.RESPONSE_CODE_NOLOGIN;
		}
	}
	@RequestMapping(value = { "/{qid}/comment/new" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String addComment(Locale locale,@PathVariable long qid, HttpServletRequest req, Model model) {
		Object u = req.getSession().getAttribute("user");
		String lang = getLanguage(locale,req);
		if (u != null) {
			Comment c = new Comment();
			c.setQid(qid);
			c.setUser((User) u);
			try {
				c.setComment(StringUtil.iso2utf8(req.getParameter("comment")));
				 this.quesDao.addComment(c, lang);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
				model.addAttribute("errormsg", e.getMessage());
			}
		}
		return "redirect:/question/"+lang+"/"+qid;
	}

	@RequestMapping(value = { "/{qid}/answer/new" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String answerQuestion(Locale locale,@PathVariable long qid, HttpServletRequest req,
			Model model) {
		Object u = req.getSession().getAttribute("user");
		String lang =getLanguage(locale,req);
		if (u != null) {

			Answer a = new Answer();
			a.setQid(qid);
			a.setUser((User) u);

			String answer = req.getParameter("answer");
			if ((answer != null) && (!answer.equals(""))) {
				try {
					a.setAnswer(StringUtil.iso2utf8(answer));
					this.answerDao.addAnswer(a, lang);
					// increase answer cnt
					this.quesDao.addAnswerCnt(qid, 1, lang);
				} catch (Exception e) {
					model.addAttribute("errormsg", e.getMessage());
				}
			}

			
		}
		return "redirect:/question/"+lang+"/"+qid;
	}

	@RequestMapping(value="/{qid}/answer/{aid}/edit",method=RequestMethod.POST)
	public String  answerUpdate(Locale locale,@PathVariable long qid,@PathVariable long aid,HttpServletRequest req, Model model){
		Object u = req.getSession().getAttribute("user");
		String lang =getLanguage(locale,req);
		if (u != null) {
		
			Answer a = new Answer();
			a.setAid(aid);
			a.setQid(qid);
			
			a.setUser((User) u);

			String answer = req.getParameter("answer");
			if ((answer != null) && (!answer.equals(""))) {
				try {
					logger.debug("update answer :"+answer);
					a.setAnswer(StringUtil.iso2utf8(answer));
					 this.answerDao.updateAnswer(a, lang);
				} catch (Exception e) {
					logger.error("failed to update answer :"+e.getMessage());
					model.addAttribute("errormsg", e.getMessage());
				}
			}
		}
		return "redirect:/question/"+lang+"/"+qid;
		

	}
	
	@RequestMapping(value="/{qid}/answer/{aid}/accept",method=RequestMethod.GET)
	public @ResponseBody int  acceptAnswer(Locale locale,@PathVariable long qid,@PathVariable long aid,HttpServletRequest req, Model model){
		Object u = req.getSession().getAttribute("user");
		String lang =getLanguage(locale,req);
		if (u != null) {
			this.answerDao.accetpAnswer(aid, lang);
			
		}
		return 0;
	}	
	
	@RequestMapping(value="/{qid}/answer/{aid}/vote/{vote}",method=RequestMethod.GET)
	public @ResponseBody int  acceptAnswer(Locale locale,@PathVariable long qid,@PathVariable long aid,@PathVariable int vote,HttpServletRequest req, Model model){
		Object u = req.getSession().getAttribute("user");
		String lang =getLanguage(locale,req);
		if (u != null) {
			try{
			User user = (User)u;
				return this.answerDao.vote(user.getUid(), aid, lang, vote);
			}catch(Exception e){
				logger.error(e.getMessage());
				return SunConstants.RESPONSE_CODE_FAILED;
			}
			
		}else{
			return SunConstants.RESPONSE_CODE_NOLOGIN;
		}
		
	}	
	
	@RequestMapping(value = { "/{qid}/resolve" }, method = { RequestMethod.GET })
	public @ResponseBody int resolve(Locale locale,@PathVariable long qid, HttpServletRequest req) {
		Object u = req.getSession().getAttribute("user");
		String lang = getLanguage(locale,req);
		if (u != null) {
			Question q = this.quesDao.getQuestion(qid, lang);
			if (q.getUser().getUid() == ((User) u).getUid()) {
				q.setResolved(true);
				return this.quesDao.updateIssueStatus(qid, lang, SunConstants.QUESTION_STATUS_RESOLVED);
			}
		}
		return 0;
	}

	@RequestMapping(value = { "/search" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String search(Locale locale, HttpServletRequest req, Model model) {
		String searchKey = req.getParameter("searchkey");
		if (searchKey != null) {
			try {
				String start = req.getParameter("start");
				int istart=0;
				if(start!=null && !start.equals(""))
					istart = Integer.parseInt(start);
				int iend = istart+20;
				String end = req.getParameter("end");
				if(end !=null && !end.equals(""))
					iend = Integer.parseInt(end);
				String lang = getLanguage(locale,req);

				String ckey = StringUtil.iso2utf8(searchKey);
				model.addAttribute("searchkey", ckey);
				model.addAttribute("questions", this.quesDao.search(ckey, lang,istart,iend));
			} catch (Exception e) {
				model.addAttribute("errormsg", e.getMessage());
			}
		}

		return "searchResult";
	}
	/**
	 * More search
	 * @param locale
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/searchMore",method=RequestMethod.GET)
	public @ResponseBody List<Question> searchMore(Locale locale, HttpServletRequest req) {
		String searchKey = req.getParameter("searchkey");
		if (searchKey != null) {
			try {
				String start = req.getParameter("start");
				int istart=0;
				if(start!=null && !start.equals(""))
					istart = Integer.parseInt(start);
				int iend = istart+20;
				String end = req.getParameter("end");
				if(end !=null && !end.equals(""))
					iend = Integer.parseInt(end);
				String lang = getLanguage(locale,req);

				String ckey = StringUtil.iso2utf8(searchKey);
				return  this.quesDao.search(ckey, lang,istart,iend);
			} catch (Exception e) {
				logger.error(e.getMessage());
//				model.addAttribute("errormsg", e.getMessage());
			}
		}

		return null;
	}

	@RequestMapping(value="/moreByCID/{cid}",method=RequestMethod.GET)
	public @ResponseBody List<Question> searchMoreByCID(Locale locale, HttpServletRequest req,@PathVariable long cid) {
				String start = req.getParameter("start");
				int istart=0;
				if(start!=null && !start.equals(""))
					istart = Integer.parseInt(start);
				int iend = istart+20;
				String end = req.getParameter("end");
				if(end !=null && !end.equals(""))
					iend = Integer.parseInt(end);
				String lang = getLanguage(locale,req);

				return  this.quesDao.findQuestionsByCID(cid, lang, istart, iend);
	}
	
	@RequestMapping(value="/moreByPID/{pid}",method=RequestMethod.GET)
	public @ResponseBody List<Question> searchMoreByPID(Locale locale, HttpServletRequest req,@PathVariable long pid) {
				String start = req.getParameter("start");
				int istart=0;
				if(start!=null && !start.equals(""))
					istart = Integer.parseInt(start);
				int iend = istart+20;
				String end = req.getParameter("end");
				if(end !=null && !end.equals(""))
					iend = Integer.parseInt(end);
				String lang = getLanguage(locale,req);

				return  this.quesDao.findQuestionsByPID(pid, lang, istart, iend);
	}	
	
	@RequestMapping(value = { "/searchtag" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String searchByTag(Locale locale, HttpServletRequest req, Model model) {
		String tag = req.getParameter("tag");

		if (tag != null && !tag.equals("")) {
			try {
				String lang = getLanguage(locale,req);
				String start = req.getParameter("start");
				int istart=0;
				if(start!=null && !start.equals(""))
					istart = Integer.parseInt(start);
				int iend = istart+20;
				String end = req.getParameter("end");
				if(end !=null && !end.equals(""))
					iend = Integer.parseInt(end);
				String ckey = StringUtil.iso2utf8(tag);
				model.addAttribute("searchkey", ckey);
				model.addAttribute("questions", this.quesDao.search(ckey, lang,istart,iend));
			} catch (Exception e) {
				model.addAttribute("errormsg", e.getMessage());
			}
		}

		return "searchResult";
	}


	


}
