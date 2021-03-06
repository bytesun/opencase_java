package org.orcsun.sunspace.third;

/*
 * Copyright 2013 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.PeopleFeed;
import com.google.api.services.plus.model.Person;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.orcsun.sunspace.SunConstants;
import org.orcsun.sunspace.dao.impl.UserDaoImpl;
import org.orcsun.sunspace.object.User;
import org.orcsun.sunspace.utils.RestfulUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/google")
public class GoogleAPI extends CommonAPI{
	/*
	 * Default HTTP transport to use to make HTTP requests.
	 */
	private static final HttpTransport TRANSPORT = new NetHttpTransport();

	/*
	 * Default JSON factory to use to deserialize JSON.
	 */
	private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

	/*
	 * Gson object to serialize JSON responses to requests to this servlet.
	 */
	private static final Gson GSON = new Gson();


	private static final String APPLICATION_NAME = "Sunorth";
	static final Logger logger = Logger.getLogger(GoogleAPI.class);


	@Autowired 
	UserDaoImpl userDao;

	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String login(HttpServletRequest req,Model model){
		String state = new BigInteger(130, new SecureRandom()).toString(32);
		req.getSession().setAttribute("state", state);
//		model.addAttribute("clientid", SunConstants.GOOGLE_API_CLIENT_ID);
		return "redirect:https://accounts.google.com/o/oauth2/auth?scope=email%20profile&approval_prompt=auto&response_type=code&client_id="+
		SunConstants.GOOGLE_API_CLIENT_ID+"&redirect_uri="+SunConstants.AUTH_REDIRECT_URL_GOOGLE+"&state="+state;
	}
	
	/**
	 * Upgrade given auth code to token, and store it in the session. POST body
	 * of request should be the authorization code. Example URI:
	 * /connect?state=...&gplus_id=...
	 * @throws Exception 
	 */
		@RequestMapping(value="/oauth2callback",method=RequestMethod.GET)
		protected String validateToken(HttpServletRequest request,HttpServletRequest response,
				Model model) throws Exception {
			String error = request.getParameter("error");
			if(error !=null){//wrong 
				logger.error(error);
				model.addAttribute("msg", error);
				return "redirect:/user/redirectLogin";
			}else{
				String code = request.getParameter("code");
				logger.info("==========code"+code);
				String original_state = (String)request.getSession().getAttribute("state");
				String state = request.getParameter("state");
				if(code != null && state.equals(original_state)){//request access token

					GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(
							TRANSPORT, JSON_FACTORY, SunConstants.GOOGLE_API_CLIENT_ID, SunConstants.GOOGLE_API_CLIENT_SECRET,
							code, SunConstants.AUTH_REDIRECT_URL_GOOGLE).execute();
	
					GoogleIdToken idToken = tokenResponse.parseIdToken();
					
					String gplusId = idToken.getPayload().getSubject();
					String email = idToken.getPayload().getEmail();
	
					if(email == null)return "redirect:/user/redirectLogin";
					else{
						logger.info("gplusid:"+gplusId);
						logger.info("email:"+email);
						User user = userDao.findUserByEmail(email);
						if(user == null){
							user = new User();
							user.setName("GoogleUser");
							user.setOpenid(gplusId);
							user.setEmail(email);
							user.setPasswd(tokenResponse.toString());
							long uid = userDao.addUser(user);
							user.setUid(uid);
						}
	
						// Store the token in the session for later use.
						request.getSession().setAttribute("token",
								tokenResponse.toString());
						
						//get user profile
						GoogleCredential credential = new GoogleCredential.Builder()
						.setJsonFactory(JSON_FACTORY)
						.setTransport(TRANSPORT)
						.setClientSecrets(SunConstants.GOOGLE_API_CLIENT_ID, SunConstants.GOOGLE_API_CLIENT_SECRET)
						.build()
						.setFromTokenResponse(
								JSON_FACTORY.fromString(tokenResponse.toString(),
										GoogleTokenResponse.class));
						// Create a new authorized API client.
						Plus service = new Plus.Builder(TRANSPORT, JSON_FACTORY,
								credential).setApplicationName(APPLICATION_NAME)
								.build();
						// Get a list of people that this user has shared with this app.
						Person person = service.people().get("me").execute();	
						user.setPhoto1(person.getImage().getUrl());
						user.setName(person.getDisplayName());
						user.setPhoto2(person.getCover().getCoverPhoto().getUrl());
						
						
						request.getSession().setAttribute("user", user);
					}					
				}
				return "redirect:/user/admin";
			}
		}
		


		/*
		 * Read the content of an InputStream.
		 * 
		 * @param inputStream the InputStream to be read.
		 * 
		 * @return the content of the InputStream as a ByteArrayOutputStream.
		 * 
		 * @throws IOException
		 */
		static void getContent(InputStream inputStream,
				ByteArrayOutputStream outputStream) throws IOException {
			// Read the response into a buffered stream
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			int readChar;
			while ((readChar = reader.read()) != -1) {
				outputStream.write(readChar);
			}
			reader.close();
		}
//	}

	/**
	 * Revoke current user's token and reset their session.
	 */
//	public static class DisconnectServlet extends HttpServlet {
		@RequestMapping(value="/disconn",method=RequestMethod.POST)
		protected void disConn(HttpServletRequest request,
				HttpServletResponse response) throws ServletException,
				IOException {
			response.setContentType("application/json");

			// Only disconnect a connected user.
			String tokenData = (String) request.getSession().getAttribute(
					"token");
			if (tokenData == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().print(
						GSON.toJson("Current user not connected."));
				return;
			}
			try {
				// Build credential from stored token data.
				GoogleCredential credential = new GoogleCredential.Builder()
						.setJsonFactory(JSON_FACTORY)
						.setTransport(TRANSPORT)
						.setClientSecrets(SunConstants.GOOGLE_API_CLIENT_ID, SunConstants.GOOGLE_API_CLIENT_SECRET)
						.build()
						.setFromTokenResponse(
								JSON_FACTORY.fromString(tokenData,
										GoogleTokenResponse.class));
				// Execute HTTP GET request to revoke current token.
				HttpResponse revokeResponse = TRANSPORT
						.createRequestFactory()
						.buildGetRequest(
								new GenericUrl(
										String.format(
												"https://accounts.google.com/o/oauth2/revoke?token=%s",
												credential.getAccessToken())))
						.execute();
				// Reset the user's session.
				request.getSession().removeAttribute("token");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().print(
						GSON.toJson("Successfully disconnected."));
			} catch (IOException e) {
				// For whatever reason, the given token was invalid.
				response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
				response.getWriter().print(
						GSON.toJson("Failed to revoke token for given user."));
			}
		}
//	}

	/**
	 * Get list of people user has shared with this app.
	 */
//	public static class PeopleServlet extends HttpServlet {
		@RequestMapping(value="/people",method=RequestMethod.GET)
		protected void getPeople(HttpServletRequest request,
				HttpServletResponse response) throws ServletException,
				IOException {
			response.setContentType("application/json");

			// Only fetch a list of people for connected users.
			String tokenData = (String) request.getSession().getAttribute(
					"token");
			if (tokenData == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().print(
						GSON.toJson("Current user not connected."));
				return;
			}
			try {
				// Build credential from stored token data.
				GoogleCredential credential = new GoogleCredential.Builder()
						.setJsonFactory(JSON_FACTORY)
						.setTransport(TRANSPORT)
						.setClientSecrets(SunConstants.GOOGLE_API_CLIENT_ID, SunConstants.GOOGLE_API_CLIENT_SECRET)
						.build()
						.setFromTokenResponse(
								JSON_FACTORY.fromString(tokenData,
										GoogleTokenResponse.class));
				// Create a new authorized API client.
				Plus service = new Plus.Builder(TRANSPORT, JSON_FACTORY,
						credential).setApplicationName(APPLICATION_NAME)
						.build();
				// Get a list of people that this user has shared with this app.
				PeopleFeed people = service.people().list("me", "visible")
						.execute();
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().print(GSON.toJson(people));
			} catch (IOException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().print(
						GSON.toJson("Failed to read data from Google. "
								+ e.getMessage()));
			}
		}

		@RequestMapping(value="/person",method=RequestMethod.GET)
		protected void getPerson(HttpServletRequest request,
				HttpServletResponse response) throws ServletException,
				IOException {
			response.setContentType("application/json");

			// Only fetch a list of people for connected users.
			String tokenData = (String) request.getSession().getAttribute(
					"token");
			if (tokenData == null) {
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().print(
						GSON.toJson("Current user not connected."));
				return;
			}
			try {
				// Build credential from stored token data.
				GoogleCredential credential = new GoogleCredential.Builder()
						.setJsonFactory(JSON_FACTORY)
						.setTransport(TRANSPORT)
						.setClientSecrets(SunConstants.GOOGLE_API_CLIENT_ID, SunConstants.GOOGLE_API_CLIENT_SECRET)
						.build()
						.setFromTokenResponse(
								JSON_FACTORY.fromString(tokenData,
										GoogleTokenResponse.class));
				// Create a new authorized API client.
				Plus service = new Plus.Builder(TRANSPORT, JSON_FACTORY,
						credential).setApplicationName(APPLICATION_NAME)
						.build();
				// Get a list of people that this user has shared with this app.
				Person person = service.people().get("me").execute();
				User u = (User)request.getSession().getAttribute("user");
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().print(GSON.toJson(person));
			} catch (IOException e) {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().print(
						GSON.toJson("Failed to read data from Google. "
								+ e.getMessage()));
			}
		}
}
