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
		String lang = "en";
		if(req == null)return lang;
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
		return lang;
	}
		
}
