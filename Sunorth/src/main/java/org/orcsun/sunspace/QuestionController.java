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

	
	@RequestMapping(value="/newQuestions",method=RequestMethod.GET)
	public @ResponseBody List<Question> findNewQuestions(Locale locale,HttpServletRequest req){
		String start= req.getParameter("start");
		int istart = 0;
		if(start != null && !start.equals(""))
			istart = Integer.parseInt(start);
		String end = req.getParameter("end");
		int iend = 20;
		if(end != null && !end.equals(""))
			iend = istart+Integer.parseInt(end);
		
		return quesDao.findNewQuestions(istart, iend, this.getLanguage(locale, req));
	}
	
	
	@RequestMapping(value = { "/ask" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String askQuestion(Locale locale, HttpServletRequest req, Model model) {
		Object u = req.getSession().getAttribute("user");
		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		String lang = getLanguage(locale,req);
		if (u != null) {
			try {
	

				Question q = new Question();
				String question = req.getParameter("questitle");
				q.setQuestion(StringUtil.iso2utf8(question));

				if (pid != null)
					q.setPid(Long.parseLong(pid));
				else if (cid != null) {
					q.setCid(Long.parseLong(cid));
				}

				question = req.getParameter("question");
				if (question != null)
					q.setDescription(StringUtil.iso2utf8(question));

				String tag = req.getParameter("tag");
				if (tag != null)
					q.setTag(StringUtil.iso2utf8(tag));
				q.setUser((User) u);
				this.quesDao.addQuestion(q, lang);
			} catch (Exception e) {
				logger.error(e.getMessage());
				model.addAttribute("errormsg", e.getMessage());
			}
			if ((pid != null) && (!pid.equals("")))
				return "redirect:/question/"+lang+"/" + pid;
			if ((cid != null) && (!cid.equals(""))) {
				return "redirect:/cat/"+lang+"/" + cid;
			}
			return "redirect:/";
		}
		return "redirect:/user/redirectLogin?cid=" + cid + "&pid=" + pid;
	}

	@RequestMapping(value="/update",method=RequestMethod.POST)
	public String update(Locale locale,HttpServletRequest req,Model model){
		Object u = req.getSession().getAttribute("user");
		String lang = getLanguage(locale,req);
		if(u != null){

		
			String qid = req.getParameter("qid");
			Question q = new Question();
			q.setQid(Long.parseLong(qid));
			
			try {
				q.setQuestion(StringUtil.iso2utf8(req.getParameter("question")));
				q.setDescription(StringUtil.iso2utf8(req.getParameter("questiondesc")));
				q.setTag(StringUtil.iso2utf8(req.getParameter("tag")));
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
				
			}
			
			quesDao.updateQuestion(q, lang);
			
			return "redirect:/question/"+lang+"/" + qid;
		}else{
			String cid = req.getParameter("cid");
			String pid = req.getParameter("pid");
			return "redirect:/user/redirectLogin?cid=" + cid + "&pid=" + pid;
		}
	}
	
	@RequestMapping(value = { "/comment" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String addComment(Locale locale, HttpServletRequest req, Model model) {
		Object u = req.getSession().getAttribute("user");
		String lang = getLanguage(locale,req);
		if (u != null) {


			String qid = req.getParameter("qid");

			Comment c = new Comment();
			c.setQid(Long.parseLong(qid));
			c.setUser((User) u);
			try {
				c.setComment(StringUtil.iso2utf8(req.getParameter("comment")));
				this.quesDao.addComment(c, lang);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
				model.addAttribute("errormsg", e.getMessage());
			}

			return "redirect:/question/"+lang+"/" + qid;
		}

		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		return "redirect:/user/redirectLogin?cid=" + cid + "&pid=" + pid;
	}

	@RequestMapping(value = { "/answer" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String answerQuestion(Locale locale, HttpServletRequest req,
			Model model) {
		Object u = req.getSession().getAttribute("user");
		String lang =getLanguage(locale,req);
		if (u != null) {


			String qid = req.getParameter("qid");
			Answer a = new Answer();
			a.setQid(Long.parseLong(qid));
			a.setUser((User) u);

			String answer = req.getParameter("answer");
			if ((answer != null) && (!answer.equals(""))) {
				try {
					a.setAnswer(StringUtil.iso2utf8(answer));
					this.answerDao.addAnswer(a, lang);
					// increase answer cnt
					this.quesDao.addAnswerCnt(Long.parseLong(qid), 1, lang);
					// mark the question is resolved
					Object isresolved = req.getParameter("isresolved");
					if (isresolved != null)
						this.quesDao.resolveQuestion(Long.parseLong(qid), lang);
				} catch (Exception e) {
					model.addAttribute("errormsg", e.getMessage());
				}
			}

			return "redirect:/question/"+lang+"/" + qid;
		}
		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		return "redirect:/user/redirectLogin?cid=" + cid + "&pid=" + pid;
	}

	@RequestMapping(value="/ansupdt",method=RequestMethod.POST)
	public String answerUpdate(Locale locale,HttpServletRequest req, Model model){
		Object u = req.getSession().getAttribute("user");
		String lang =getLanguage(locale,req);
		if (u != null) {


			String qid = req.getParameter("qid");
			
			Answer a = new Answer();
			a.setAid(Long.parseLong(req.getParameter("aid")));
			a.setQid(Long.parseLong(qid));
			
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

			return "redirect:/question/"+lang+"/" + qid;
		}
		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		return "redirect:/user/redirectLogin?cid=" + cid + "&pid=" + pid;
	}
	
	
	
	@RequestMapping(value = { "/resolve" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String resolve(Locale locale, HttpServletRequest req) {
		Object u = req.getSession().getAttribute("user");
		String lang = getLanguage(locale,req);
		if (u != null) {

			String qid = req.getParameter("qid");
			Question q = this.quesDao.getQuestion(Long.parseLong(qid), lang);
			if (q.getUser().getUid() == ((User) u).getUid()) {
				Answer a = new Answer();
				a.setQid(Long.parseLong(qid));
				a.setUser((User) u);
				a.setAnswer(req.getParameter("answer"));
				this.answerDao.addAnswer(a, lang);
				q.setResolved(true);
				this.quesDao.updateQuestion(q, lang);
			}
			return "redirect:/question/"+lang+"/" + qid;
		}
		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		return "redirect:/user/redirectLogin?cid=" + cid + "&pid=" + pid;
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


	
	@RequestMapping(value = { "/vote" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String vote(Locale locale, HttpServletRequest req, Model model) {
		String qid = req.getParameter("qid");
		Object u = req.getSession().getAttribute("user");
		String lang = getLanguage(locale,req);
		if (u != null) {
			try {

				int vote = 1;
				if (req.getParameter("no") != null)
					vote = -1;
				this.quesDao.vote(Long.parseLong(qid), vote,
						((User) u).getUid(), lang);
			} catch (Exception e) {
				model.addAttribute("errormsg", e.getMessage());
			}
			return "redirect:/question/"+lang+"/" + qid;
		}
		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		return "redirect:/user/redirectLogin?cid=" + cid + "&pid=" + pid;
	}

	@RequestMapping(value = { "/answer/vote" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String voteAnswer(Locale locale, HttpServletRequest req, Model model) {
		String qid = req.getParameter("qid");
		String aid = req.getParameter("aid");
		logger.info("vote answer :" + aid);
		Object u = req.getSession().getAttribute("user");
		String lang = getLanguage(locale,req);
		if (u != null) {
			try {

				String vcomment = req.getParameter("vcomment");
				int vote = 1;
				if (req.getParameter("vdown") != null)
					vote = -1;
				if (aid != null) {
					if (req.getParameter("isanswer") != null) {
						this.answerDao.voteAnswer(Long.parseLong(aid), vote, 1,
								vcomment, ((User) u).getUid(), lang);
					} else {
						this.answerDao.voteAnswer(Long.parseLong(aid), vote, 0,
								vcomment, ((User) u).getUid(), lang);

					}
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
				model.addAttribute("errormsg", e.getMessage());

			}
			return "redirect:/question/"+lang+"/" + qid;
		}
		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		return "redirect:/user/redirectLogin?cid=" + cid + "&pid=" + pid;
	}

}
