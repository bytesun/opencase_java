package org.orcsun.sunspace;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * Supper controller to define common methods
 * @author Sun
 * 2014/4/13
 */
public class SunController {

	/**
	 * Get default language from session or locale.
	 * @param locale
	 * @param req
	 * @return
	 */
	String getLanguage(Locale locale,HttpServletRequest req){
		Object lang = req.getParameter("lang");
		if(lang == null){
			lang = req.getSession().getAttribute("lang");
			if(lang == null){
				if (locale.getLanguage().equalsIgnoreCase("zh")){
					lang = "zh";
				}else{
					lang = "en";
				}
				req.getSession().setAttribute("lang", lang);
			}
		}
		return (String)lang;
	}
		
}
