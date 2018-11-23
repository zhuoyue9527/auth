package com.afis.web.auth.controller.cust;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.web.auth.controller.admin.IndexController;
import com.afis.web.auth.protocol.cust.request.CustLoginCust2WebRequest;
import com.afis.web.auth.protocol.cust.request.CustLoginWeb2CloudRequest;
import com.afis.web.auth.protocol.cust.request.OnlineUserCust2WebRequest;
import com.afis.web.auth.protocol.cust.request.OnlineUserWeb2CloudRequest;
import com.afis.web.auth.protocol.cust.response.CustLoginCloud2WebResponse;
import com.afis.web.auth.protocol.cust.response.OnlineUserWeb2CustResponse;
import com.afis.web.config.WebProperties;
import com.afis.web.exception.AuthenticationException;
import com.afis.web.exception.CommonException;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.CommonConstants;
import com.afis.web.modal.UserApplications;
import com.afis.web.modal.UserDetails;
import com.afis.web.modal.WebResponse;
import com.afis.web.model.DefaultCloudRequest;
import com.afis.web.security.inf.TokenService;
import com.afis.web.utils.HttpOrignalUtil;
import com.afis.web.utils.OkHttpUtil;
import com.afis.web.utils.SessionUtil;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 给外部应用调用的
 * 
 * @author Chengen
 *
 */
@Api("给外部应用调用的")
@Controller
public class AuthorizeController extends AbstractOpenController {

	@Autowired
	private WebProperties webProperties;

	@Autowired
	private TokenService tokenService;

	@RequestMapping(value = "/auth/authorize", method = RequestMethod.GET)
	@ApiOperation("用户跳转到登录页面")
	public String getAuthorize(HttpServletRequest request, HttpServletResponse response, @RequestParam String app_code,
			@RequestParam long time, @RequestParam String encrypt, @RequestParam String redirect_url, ModelMap map)
			throws IOException {
		if (checkEncrypt(app_code, time, redirect_url, encrypt)) {// 验证有效
			ApplicationModel application = getApplication(app_code);
			
			map.addAttribute("redirectUrl", redirect_url);
			map.addAttribute("appCode", app_code);
			map.addAttribute("appName",application.getAppName());
			map.addAttribute("appLogoPath", application.getLogoPath());

			List<UserDetails> list = getCurrentUserDetails(request);
			if (list == null || list.isEmpty()) {// 跳转到登录页
				return "login";
			}

			List<OnlineUserWeb2CustResponse> authorizeList = new ArrayList<OnlineUserWeb2CustResponse>();
			List<OnlineUserWeb2CustResponse> unAuthorizeList = new ArrayList<OnlineUserWeb2CustResponse>();

			// 在线用户分组
			groupUserDetails(list, authorizeList, unAuthorizeList, app_code);

			if (authorizeList.isEmpty() && unAuthorizeList.isEmpty()) {
				return "login";
			}

			map.addAttribute("authorizeList", authorizeList);
			map.addAttribute("unAuthorizeList", unAuthorizeList);

			logger.debug("map:{}", map);

			return "auth";
		} else {
			response.sendError(HttpServletResponse.SC_REQUEST_TIMEOUT, "url验证不通过");
			return null;
		}
	}

	@RequestMapping(value = "/auth/loginPage", method = RequestMethod.GET)
	@ApiOperation("直接跳转到用户的登录页")
	public String gotoLoginPage(ModelMap map, @RequestParam String appCode, @RequestParam String redirectUrl) {
		ApplicationModel application = getApplication(appCode);
		map.addAttribute("redirectUrl", redirectUrl);
		map.addAttribute("appCode", appCode);
		map.addAttribute("appName",application.getAppName());
		map.addAttribute("appLogoPath", application.getLogoPath());
		logger.debug("map:{}", map);
		return "login";
	}

	/**
	 * 在线用户分组
	 * 
	 * @param list
	 * @param authorizeList
	 * @param unAuthorizeList
	 * @param appCode
	 */
	private void groupUserDetails(List<UserDetails> list, List<OnlineUserWeb2CustResponse> authorizeList,
			List<OnlineUserWeb2CustResponse> unAuthorizeList, String appCode) {
		for (UserDetails user : list) {
			List<UserApplications> appList = user.getAppList();
			if (appList != null) {
				for (UserApplications app : appList) {
					if (app.getAppCode().equals(appCode)) {
						if (CommonConstants.TrueOrFalse.TRUE.getKey().equals(app.getWarrantPermit())) {// 是否允许授权
							if (CommonConstants.TrueOrFalse.TRUE.getKey().equals(app.getWarrant())) {// 是否已授权
								authorizeList.add(new OnlineUserWeb2CustResponse(user));
							} else {
								unAuthorizeList.add(new OnlineUserWeb2CustResponse(user));
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 根据request中的cookie获取在线的用户
	 * 
	 * @param request
	 * @return
	 */
	private List<UserDetails> getCurrentUserDetails(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		List<UserDetails> list = new ArrayList<UserDetails>();
		for (Cookie cookie : cookies) {
			if (cookie.getName().startsWith(webProperties.getLogin().getTokenCookiePrefix())) {
				String loginKey = cookie.getValue();
				UserDetails user = tokenService.getToken(loginKey);
				if (user != null) {
					list.add(user);
				}
			}
		}
		return list;
	}

	/**
	 * 用户端登录
	 * 
	 * @param loginRequest
	 * @throws WebBusinessException
	 * @throws IOException
	 */
	@RequestMapping(value = "/auth/authorize", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation("用户端登录")
	public WebResponse postAuthorize(HttpServletRequest request, HttpServletResponse response,
			@RequestBody CustLoginCust2WebRequest loginRequest) throws WebBusinessException, IOException {
		// 校验登录名、密码、验证码
		checkInput(request, loginRequest);

		// 请求认证中心生成ticket
		WebResponse res = this.sendCloudLogin(request, loginRequest);

		if (ExceptionResultCode.SUCCESS != res.getResponseCode()) {// 登录成功
			return res;
		}
		CustLoginCloud2WebResponse resp = JSONObject.parseObject(res.getData().toString(),
				CustLoginCloud2WebResponse.class);

		UserDetails current = resp.getUserDetails();
		logger.debug("current:{}", current);
		// 删除同一帐号的登录信息
		List<UserDetails> cookieUsers = getCurrentUserDetails(request);
		if (!cookieUsers.isEmpty()) {
			for (UserDetails user : cookieUsers) {
				if (user.getUserName().equals(current.getUserName())) {
					logger.debug("user:{}", user);
					// 删除这个用户
					sendcloudLogout(request, user);
					// 删除cookie
					deleteCookie(response, webProperties.getLogin().getTokenCookiePrefix() + user.getLoginKey());
				}
			}
		}

		// 存储cookie
		response.addCookie(generateCookie(webProperties.getLogin().getTokenCookiePrefix() + current.getLoginKey(),
				current.getLoginKey()));
		// 生成重定向url返回给页面，页面做重定向跳转
		String redirectUrl = generateRedirectUrl(resp.getRedirectUrl(), resp.getTicket());

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, redirectUrl);
	}

	private void sendcloudLogout(HttpServletRequest request, UserDetails user) throws WebBusinessException {
		OkHttpUtil.webDeleteToCloudService(request, webProperties.getLogin().getCloudUrl() + IndexController.CLOUD_LOGOUT_URL,
				new DefaultCloudRequest(user));
	}

	@ApiOperation("选择已授权的登录帐号登录")
	@RequestMapping(value = "/auth/authorize", method = RequestMethod.PATCH)
	@ResponseBody
	public WebResponse patchAuthorize(HttpServletRequest request, @RequestBody OnlineUserCust2WebRequest loginRequest)
			throws WebBusinessException {
		checkOnlineInput(loginRequest);
		// 判断cookie中是否有这条loginKey
		checkLoginKey(request, loginRequest.getLoginKey());

		WebResponse res = OkHttpUtil.webPatchToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(),
				new OnlineUserWeb2CloudRequest(loginRequest));

		if (ExceptionResultCode.SUCCESS != res.getResponseCode()) {// 登录成功
			return res;
		}
		CustLoginCloud2WebResponse resp = JSONObject.parseObject(res.getData().toString(),
				CustLoginCloud2WebResponse.class);
		// 生成重定向url返回给页面，页面做重定向跳转
		String redirectUrl = generateRedirectUrl(resp.getRedirectUrl(), resp.getTicket());

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, redirectUrl);
	}

	/**
	 * 判断传过来的loginKey是否在request的cookie中
	 * 
	 * @param request
	 * @param loginKey
	 * @throws CommonException
	 */
	private void checkLoginKey(HttpServletRequest request, String loginKey) throws CommonException {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			throw new CommonException(CommonException.ANY_DESC, "传递loginKey错误");
		}
		boolean hasLoginKey = false;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(webProperties.getLogin().getTokenCookiePrefix() + loginKey)) {
				hasLoginKey = true;
				break;
			}
		}
		if (!hasLoginKey) {
			throw new CommonException(CommonException.ANY_DESC, "传递loginKey错误");
		}

		UserDetails userDetails = tokenService.getToken(loginKey);
		if (userDetails == null) {
			throw new CommonException(CommonException.ANY_DESC, "所选用户已掉线");
		}
	}

	private void checkOnlineInput(OnlineUserCust2WebRequest loginRequest) throws AuthenticationException {
		if (loginRequest.getLoginKey() == null || loginRequest.getLoginKey().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"loginKey not exits");
			exception.setDesc("loginKey");
			throw exception;
		}
		if (loginRequest.getAppCode() == null || loginRequest.getAppCode().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"appCode not exits");
			exception.setDesc("appCode");
			throw exception;
		}
		if (loginRequest.getRedirectUrl() == null || loginRequest.getRedirectUrl().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"redirectUrl not exits");
			exception.setDesc("redirectUrl");
			throw exception;
		}
	}

	private String generateRedirectUrl(String redirectUrl, String ticket) {
		StringBuffer sb = new StringBuffer();
		sb.append(redirectUrl);
		sb.append("?").append("ticket=").append(ticket);
		return sb.toString();
	}

	/**
	 * 删除cookie
	 * 
	 * @param response
	 * @param cookieName
	 */
	private void deleteCookie(HttpServletResponse response, String cookieName) {
		Cookie cookie = new Cookie(cookieName, null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	private Cookie generateCookie(String cookieName, String loginKey) {
		Cookie cookie = new Cookie(cookieName, loginKey);
		cookie.setPath("/");
		return cookie;
	}

	private WebResponse sendCloudLogin(HttpServletRequest request, CustLoginCust2WebRequest loginRequest)
			throws WebBusinessException {
		// 构建提交给cloud的参数对象
		CustLoginWeb2CloudRequest cloudRequest = createCloudRequest(request, loginRequest);
		// 请求cloud的认证服务
		return OkHttpUtil.webPostToCloudService(request,
				webProperties.getLogin().getCloudUrl() + request.getRequestURI(), cloudRequest);

	}

	private CustLoginWeb2CloudRequest createCloudRequest(HttpServletRequest request,
			CustLoginCust2WebRequest loginRequest) {
		CustLoginWeb2CloudRequest cloudRequest = new CustLoginWeb2CloudRequest(loginRequest);
		cloudRequest.setLoginIp(HttpOrignalUtil.getRemoteIpReal(request));
		cloudRequest.setBrowserInfo(HttpOrignalUtil.getBrowserInfo(request));
		cloudRequest.setSystemInfo(HttpOrignalUtil.getSystemInfo(request));
		return cloudRequest;
	}

	private void checkInput(HttpServletRequest request, CustLoginCust2WebRequest loginRequest)
			throws AuthenticationException {
		// check username parameter
		if (loginRequest.getUsername() == null || loginRequest.getUsername().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"username not exits");
			exception.setDesc("userName");
			throw exception;
		}
		// check parameter password.
		if (loginRequest.getPassword() == null || loginRequest.getPassword().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"password not exist");
			exception.setDesc("password");
			throw exception;
		}
		// check captcha.
		if (webProperties.getSecurity().isUseCaptcha()) {
			if (loginRequest.getCaptcha() == null || loginRequest.getCaptcha().trim().length() == 0) {
				AuthenticationException exception = new AuthenticationException(
						AuthenticationException.PARAMS_NOT_EXIST, "captcha not exist");
				exception.setDesc("captcha");
				throw exception;
			}
			Object loginCaptcha = request.getSession().getAttribute("LOGIN_CAPTCHA");
			if (loginCaptcha == null || !loginRequest.getCaptcha().trim().equalsIgnoreCase(loginCaptcha.toString())) {
				throw new AuthenticationException(AuthenticationException.CAPTCHA_NOT_RIGHT);
			}
		}
		// check appCode
		if (loginRequest.getAppCode() == null || loginRequest.getAppCode().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST,
					"appCode not exist");
			exception.setDesc("appCode");
			throw exception;
		}
	}
}
