package org.orcsun.sunspace;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.orcsun.sunspace.dao.impl.ActivityDaoImpl;
import org.orcsun.sunspace.object.Activity;
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
@RequestMapping("/activity")
public class ActivityController  extends SunController{

	@Autowired 
	ActivityDaoImpl actDao;
	
	private static final Logger logger = Logger.getLogger(ActivityController.class);
	
	@RequestMapping(value="/add",method=RequestMethod.POST)
	public String startActivity(Locale locale, HttpServletRequest req){
		Object user = req.getSession().getAttribute("user");
		String lang = getLanguage(locale,req);
		long aid=0;
		if (user != null) {
			try {
				Activity activity = new Activity();
				activity.setSubject(StringUtil.iso2utf8(req.getParameter("subject")));
				activity.setDescription(StringUtil.iso2utf8(req.getParameter("description")));
				activity.setLocation(StringUtil.iso2utf8(req.getParameter("location")));
				activity.setAtype(Integer.parseInt(req.getParameter("atype")));
				activity.setAttcnt(Integer.parseInt(req.getParameter("attcnt")));
				activity.setCost(Double.parseDouble(req.getParameter("cost")));
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyy-MM-dd");
				activity.setAstime( formatter.parse((String) req.getParameter("astime")));
				activity.setAetime( formatter.parse((String) req.getParameter("aetime")));
				
				activity.setOrganizer((User)user);
				aid = this.actDao.addActivity(activity,lang);
				return "redirect:/activity/"+aid+"/"+lang;
			} catch (Exception e) {
				logger.error("Failed to conver string date to a Date type:"
						+ e.getMessage());
				return "notice";
			}
		}else{
			return "redirect:/user/redirectlogin";
		}
		
	}
	@RequestMapping(value="/{aid}/{lang}",method=RequestMethod.GET)
	public String activity(@PathVariable long aid,@PathVariable String lang,Model model){
		model.addAttribute("activity",this.actDao.getActivity(aid,lang));
		return "activity";
	}
}
