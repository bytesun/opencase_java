package org.orcsun.sunspace;

import java.io.UnsupportedEncodingException;
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

@Controller
@RequestMapping({ "/question" })
public class QuestionController {

	@Autowired
	QuestionDaoImpl quesDao;

	@Autowired
	AnswerDaoImpl answerDao;

	@Autowired
	CategoryDaoImpl catDao;
	private static final Logger logger = Logger
			.getLogger(QuestionController.class);

	@RequestMapping(value = { "/{qid}" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String showQuestion(Locale locale, @PathVariable long qid,
			HttpServletRequest req, Model model) {
		String lang = "en";
		Object olang = req.getSession().getAttribute("lang");
		Object plang = req.getParameter("lang");
		if (olang != null) {
			lang = (String) olang;
		} else if ((plang != null) && (!plang.equals(""))) {
			if (((String) plang).equals("zh"))
				lang = "zh";
			req.getSession().setAttribute("lang", lang);
		} else if (locale.getLanguage().equalsIgnoreCase("zh")) {
			lang = "zh";
		}
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
			model.addAttribute("question", q);
			model.addAttribute("answers", this.answerDao.findAnswers(qid, lang));
			model.addAttribute("comments", this.quesDao.findComments(qid, lang));
			model.addAttribute("followups",
					this.quesDao.findQuestionsByPID(qid, lang));
		}
		return "question";
	}

	@RequestMapping(value = { "/ask" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String askQuestion(Locale locale, HttpServletRequest req, Model model) {
		Object u = req.getSession().getAttribute("user");
		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		if (u != null) {
			try {
				String lang = "en";
				Object olang = req.getSession().getAttribute("lang");
				if (olang != null) {
					lang = (String) olang;
				} else if (locale.getLanguage().equalsIgnoreCase("zh")) {
					lang = "zh";
				}
				Question q = new Question();
				String question = req.getParameter("questitle");
				q.setQuestion(StringUtil.utf8(question, "iso-8859-1"));

				if (pid != null)
					q.setPid(Long.parseLong(pid));
				else if (cid != null) {
					q.setCid(Long.parseLong(cid));
				}

				question = req.getParameter("question");
				if (question != null)
					q.setDescription(StringUtil.utf8(question, "iso-8859-1"));

				String tag = req.getParameter("tag");
				if (tag != null)
					q.setTag(StringUtil.utf8(tag, "iso-8859-1"));
				q.setUser((User) u);
				this.quesDao.addQuestion(q, lang);
			} catch (Exception e) {
				logger.error(e.getMessage());
				model.addAttribute("errormsg", e.getMessage());
			}
			if ((pid != null) && (!pid.equals("")))
				return "redirect:/question/" + pid;
			if ((cid != null) && (!cid.equals(""))) {
				return "redirect:/cat/" + cid;
			}
			return "redirect:/";
		}
		return "redirect:/redirectLogin?cid=" + cid + "&pid=" + pid;
	}

	@RequestMapping(value = { "/comment" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String addComment(Locale locale, HttpServletRequest req, Model model) {
		Object u = req.getSession().getAttribute("user");
		if (u != null) {
			String lang = "en";
			Object olang = req.getSession().getAttribute("lang");
			if (olang != null) {
				lang = (String) olang;
			} else if (locale.getLanguage().equalsIgnoreCase("zh")) {
				lang = "zh";
			}
			String qid = req.getParameter("qid");

			Comment c = new Comment();
			c.setQid(Long.parseLong(qid));
			c.setUser((User) u);
			try {
				c.setComment(StringUtil.utf8(req.getParameter("comment"),
						"iso-8859-1"));
				this.quesDao.addComment(c, lang);
			} catch (UnsupportedEncodingException e) {
				logger.error(e.getMessage());
				model.addAttribute("errormsg", e.getMessage());
			}

			return "redirect:/question/" + qid;
		}

		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		return "redirect:/redirectLogin?cid=" + cid + "&pid=" + pid;
	}

	@RequestMapping(value = { "/answer" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String answerQuestion(Locale locale, HttpServletRequest req,
			Model model) {
		Object u = req.getSession().getAttribute("user");
		if (u != null) {
			String lang = "en";
			Object olang = req.getSession().getAttribute("lang");
			if (olang != null) {
				lang = (String) olang;
			} else if (locale.getLanguage().equalsIgnoreCase("zh")) {
				lang = "zh";
			}

			String qid = req.getParameter("qid");
			Answer a = new Answer();
			a.setQid(Long.parseLong(qid));
			a.setUser((User) u);

			String answer = req.getParameter("answer");
			if ((answer != null) && (!answer.equals(""))) {
				try {
					a.setAnswer(StringUtil.utf8(answer, "iso-8859-1"));
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

			return "redirect:/question/" + qid;
		}
		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		return "redirect:/redirectLogin?cid=" + cid + "&pid=" + pid;
	}

	@RequestMapping(value = { "/resolve" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String resolve(Locale locale, HttpServletRequest req) {
		Object u = req.getSession().getAttribute("user");
		if (u != null) {
			String lang = "en";
			Object olang = req.getSession().getAttribute("lang");
			if (olang != null) {
				lang = (String) olang;
			} else if (locale.getLanguage().equalsIgnoreCase("zh")) {
				lang = "zh";
			}
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
			return "redirect:/question/" + qid;
		}
		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		return "redirect:/redirectLogin?cid=" + cid + "&pid=" + pid;
	}

	@RequestMapping(value = { "/search" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String search(Locale locale, HttpServletRequest req, Model model) {
		String searchKey = req.getParameter("searchkey");
		if (searchKey != null) {
			try {
				String lang = "en";
				Object olang = req.getSession().getAttribute("lang");
				if (olang != null) {
					lang = (String) olang;
				} else if (locale.getLanguage().equalsIgnoreCase("zh")) {
					lang = "zh";
				}
				String ckey = StringUtil.utf8(searchKey, "iso-8859-1");
				model.addAttribute("searchkey", ckey);
				model.addAttribute("questions", this.quesDao.search(ckey, lang));
			} catch (Exception e) {
				model.addAttribute("errormsg", e.getMessage());
			}
		}

		return "searchResult";
	}

	@RequestMapping(value = { "/searchtag" }, method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String searchByTag(Locale locale, HttpServletRequest req, Model model) {
		String tag = req.getParameter("tag");

		if (tag != null) {
			try {
				String lang = "en";
				Object olang = req.getSession().getAttribute("lang");
				if (olang != null) {
					lang = (String) olang;
				} else if (locale.getLanguage().equalsIgnoreCase("zh")) {
					lang = "zh";
				}
				String ckey = StringUtil.utf8(tag, "iso-8859-1");
				model.addAttribute("searchkey", ckey);
				model.addAttribute("questions", this.quesDao.search(ckey, lang));
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
		if (u != null) {
			try {
				String lang = "en";
				Object olang = req.getSession().getAttribute("lang");
				if (olang != null) {
					lang = (String) olang;
				} else if (locale.getLanguage().equalsIgnoreCase("zh")) {
					lang = "zh";
				}
				int vote = 1;
				if (req.getParameter("no") != null)
					vote = -1;
				this.quesDao.vote(Long.parseLong(qid), vote,
						((User) u).getUid(), lang);
			} catch (Exception e) {
				model.addAttribute("errormsg", e.getMessage());
			}
			return "redirect:/question/" + qid;
		}
		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		return "redirect:/redirectLogin?cid=" + cid + "&pid=" + pid;
	}

	@RequestMapping(value = { "/answer/vote" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	public String voteAnswer(Locale locale, HttpServletRequest req, Model model) {
		String qid = req.getParameter("qid");
		String aid = req.getParameter("aid");
		logger.info("vote answer :" + aid);
		Object u = req.getSession().getAttribute("user");
		if (u != null) {
			try {
				String lang = "en";
				Object olang = req.getSession().getAttribute("lang");
				if (olang != null) {
					lang = (String) olang;
				} else if (locale.getLanguage().equalsIgnoreCase("zh")) {
					lang = "zh";
				}
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
			return "redirect:/question/" + qid;
		}
		String cid = req.getParameter("cid");
		String pid = req.getParameter("pid");
		return "redirect:/redirectLogin?cid=" + cid + "&pid=" + pid;
	}

}
