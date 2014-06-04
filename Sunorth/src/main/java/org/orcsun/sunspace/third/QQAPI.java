package org.orcsun.sunspace.third;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.orcsun.sunspace.SunConstants;
import org.orcsun.sunspace.UserController;
import org.orcsun.sunspace.dao.impl.UserDaoImpl;
import org.orcsun.sunspace.object.User;
import org.orcsun.sunspace.utils.RestfulUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestClientException;


@Controller
@RequestMapping("/qq")
public class QQAPI extends CommonAPI{
	private static final Logger logger = Logger.getLogger(QQAPI.class);
	
	@Autowired
	UserDaoImpl userDao;

	
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(HttpServletRequest req){
		String state = new BigInteger(130, new SecureRandom()).toString(32);
		req.getSession().setAttribute("state", state);
		return "redirect:https://graph.qq.com/oauth2.0/authorize?response_type=code&client_id="+SunConstants.QQ_API_CLIENT_ID+"&redirect_uri="+SunConstants.AUTH_REDIRECT_URL_QQ+"&state="+state;
	}
	
	@RequestMapping(value="/oauth2",method=RequestMethod.GET)
	protected String validateToken(HttpServletRequest req,
			Model model) throws Exception {
		String msg = req.getParameter("msg");
		if(msg !=null){//wrong 
			logger.error(msg);
			model.addAttribute("msg", msg);
			
		}else{
			String code = req.getParameter("code");
			String original_state = (String)req.getSession().getAttribute("state");
			String state = req.getParameter("state");
			if(code != null && state.equals(original_state)){//request access token
	
				String[] tokens = this.getToken(code, 0);	
				//access_token=57498D7C0993EEAC1FF0C5FECD10C292&expires_in=7776000&refresh_token=9138A160A46A6BF5C3C484280FD7D6BD

				if(tokens[0].equals("-1")){
					model.addAttribute("msg", tokens[1]);
					
				}else{
					//success
						req.getSession().setAttribute("token", tokens[0]);

						String openid = this.getOpenid(tokens[0]);
						if(openid.indexOf("msg")>0){
							if(openid.equals("100014")){//token expired
								//refresh token
								tokens = this.getToken(tokens[1], 1);
							}else{
								model.addAttribute("msg", openid);
								return "redirect:/user/redirectLogin";
							}
						}

						
						//get user info
						JSONObject obj = this.getUserInfo(tokens[0], openid);
						
						User user = userDao.findUserByOpenid(openid);
		
						if(user == null){//new user
							user = new User();
							user.setOpenid(openid);
							user.setPasswd(tokens[0]);
							user.setEmail(openid+"@qq.com");
							user.setName(obj.getString("nickname"));
							user.setPhoto1(obj.getString("figureurl"));
							user.setPhoto2(obj.getString("figureurl_2"));
							user.setRefreshtoken(tokens[1]);
							user.setUid(userDao.addUser(user));
						}else{
							if(!tokens[0].equals(user.getPasswd())){//update token
								user.setPasswd(tokens[0]);
								userDao.updatePasswd(user.getUid(), tokens[0]);
							}
							userDao.updateRefreshToken(user.getUid(), tokens[1]);
							
							user.setName(obj.getString("nickname"));
							user.setPhoto1(obj.getString("figureurl"));
							user.setPhoto2(obj.getString("figureurl_2"));					
						}
						req.getSession().setAttribute("user", user);
					}
				
			}
			return "redirect:/user/admin";
		}
		return "redirect:/user/redirectLogin";
	}
	
	
	private String getCode(String qqResp){
		if(qqResp.indexOf("msg")>0){
		//code=xxx&msg=xxx
		String[] paras = qqResp.split("&");
		return paras[0].substring(5);
		}else{
			return null;
		}
	}
	
	private String getOpenid(String token) throws RestClientException, UnsupportedEncodingException{
		String meRequest = "https://graph.qq.com/oauth2.0/me?access_token="+token;
		String openidStr=java.net.URLDecoder.decode(RestfulUtils.rest.getForObject(meRequest, String.class),"UTF-8");

		if(openidStr.indexOf("msg")<0){
			String JSON = openidStr.substring(openidStr.indexOf("(")+1,openidStr.indexOf(")"));
			JSONObject obj = new JSONObject(JSON);
			return obj.getString("openid");
		}else{
			return openidStr;
		}
	}
	
	private JSONObject getUserInfo(String token,String openid){
		/**
		 * { "ret": 0, "msg": "", "is_lost":0, "nickname": "", "gender": "ÄÐ", 
		 * "figureurl": "http:\/\/qzapp.qlogo.cn\/qzapp\/\/\/30",
		 *  "figureurl_1": "http:\/\/qzapp.qlogo.cn\/qzapp\/\/\/50", 
		 *  "figureurl_2": "http:\/\/qzapp.qlogo.cn\/qzapp\/\/\/100", 
		 *  "figureurl_qq_1": "http:\/\/q.qlogo.cn\/qqapp\/\/\/40", 
		 *  "figureurl_qq_2": "http:\/\/q.qlogo.cn\/qqapp\/\/\/100", 
		 *  "is_yellow_vip": "0", "vip": "0", "yellow_vip_level": "0", "level": "0", "is_yellow_year_vip": "0" }
		 */	
		String JSON=RestfulUtils.rest.getForObject("https://graph.qq.com/user/get_user_info?access_token="+token+
				"&oauth_consumer_key="+SunConstants.QQ_API_CLIENT_ID+
				"&openid="+openid, String.class);
		return new JSONObject(JSON);
	}
	private String[] getToken(String codeOrToken,int grantType) throws RestClientException, UnsupportedEncodingException{
		String tokenRequest="https://graph.qq.com/oauth2.0/token?grant_type=refresh_token&client_id="+SunConstants.QQ_API_CLIENT_ID+
				"&client_secret="+SunConstants.QQ_API_CLIENT_SECRET+"&refresh_token="+codeOrToken;
		if(grantType == 0){
			tokenRequest="https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id="+SunConstants.QQ_API_CLIENT_ID+
			"&client_secret="+SunConstants.QQ_API_CLIENT_SECRET+"&code="+codeOrToken+"&redirect_uri="+SunConstants.AUTH_REDIRECT_URL_QQ;
		}
		String tokenString = java.net.URLDecoder.decode(RestfulUtils.rest.getForObject(tokenRequest, String.class), "UTF-8");
		if(tokenString.indexOf("msg")>0){
			return new String[]{"-1",tokenString};
		}else{
			////access_token=57498D7C0993EEAC1FF0C5FECD10C292&expires_in=7776000&refresh_token=9138A160A46A6BF5C3C484280FD7D6BD
			String[] paras = tokenString.split("&");
			return new String[]{paras[0].substring(13),paras[2].substring(13)};
		}
	}

}
