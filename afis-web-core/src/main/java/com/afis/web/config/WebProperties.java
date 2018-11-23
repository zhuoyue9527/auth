package com.afis.web.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by hsw on 2017/1/12.
 */
@Configuration
@ConfigurationProperties(prefix = "afis")
public class WebProperties {

	private final Security security = new Security();
	private final Uploader uploader = new Uploader();
	private final AuthorizeParam auth = new AuthorizeParam();
	private final AuthorizeCenterParam login = new AuthorizeCenterParam();
	private final System system = new System();

	public System getSystem() {
		return system;
	}

	public Security getSecurity() {
		return security;
	}

	public Uploader getUploader() {
		return uploader;
	}

	public AuthorizeCenterParam getLogin() {
		return login;
	}

	public AuthorizeParam getAuth() {
		return auth;
	}

	public static class Security {

		private String loginPage = "/login";
		private String logoutPage = "/logout";
		private String logoutSuccessPage = "/";
		private String loginSuccessPage = "/";
		private String loginType = "form";
		private boolean useCaptcha = false;
		private int captchaHeight = 30;
		private String captchaFontFamily = "Helvetica Helvetica";
		private int captchaType = 1;
		private int captchaFontSize = 18;
		private String cookieName = "afis.web.token";
		private List<Access> accessControl;
		private List<String> internalControl;
		private List<String> permitAll;

		public String getLoginPage() {
			return loginPage;
		}

		public void setLoginPage(String loginPage) {
			this.loginPage = loginPage;
		}

		public String getLogoutPage() {
			return logoutPage;
		}

		public void setLogoutPage(String logoutPage) {
			this.logoutPage = logoutPage;
		}

		public String getLogoutSuccessPage() {
			return logoutSuccessPage;
		}

		public void setLogoutSuccessPage(String logoutSuccessPage) {
			this.logoutSuccessPage = logoutSuccessPage;
		}

		public String getLoginSuccessPage() {
			return loginSuccessPage;
		}

		public void setLoginSuccessPage(String loginSuccessPage) {
			this.loginSuccessPage = loginSuccessPage;
		}

		public String getLoginType() {
			return loginType;
		}

		public void setLoginType(String loginType) {
			this.loginType = loginType;
		}

		public List<Access> getAccessControl() {
			return accessControl;
		}

		public List<String> getInternalControl() {
			return internalControl;
		}

		public List<String> getPermitAll() {
			if (this.permitAll == null) {
				this.permitAll = new ArrayList<>();
			}
			return permitAll;
		}

		public void setAccessControl(List<Access> accessControl) {
			this.accessControl = accessControl;
		}

		public void setInternalControl(List<String> internalControl) {
			this.internalControl = internalControl;
		}

		public void setPermitAll(List<String> permitAll) {
			this.permitAll = permitAll;
		}

		public boolean isUseCaptcha() {
			return useCaptcha;
		}

		public void setUseCaptcha(boolean useCaptcha) {
			this.useCaptcha = useCaptcha;
		}

		public int getCaptchaHeight() {
			return captchaHeight;
		}

		public void setCaptchaHeight(int captchaHeight) {
			this.captchaHeight = captchaHeight;
		}

		public String getCaptchaFontFamily() {
			return captchaFontFamily;
		}

		public void setCaptchaFontFamily(String captchaFontFamily) {
			this.captchaFontFamily = captchaFontFamily;
		}

		public int getCaptchaFontSize() {
			return captchaFontSize;
		}

		public void setCaptchaFontSize(int captchaFontSize) {
			this.captchaFontSize = captchaFontSize;
		}

		public int getCaptchaType() {
			return captchaType;
		}

		public void setCaptchaType(int captchaType) {
			this.captchaType = captchaType;
		}

		public String getCookieName() {
			return cookieName;
		}

		public void setCookieName(String cookieName) {
			this.cookieName = cookieName;
		}

	}

	public static class Uploader {
		private String destinationPath;
		private String virtualPath;//虚拟路径
		private String[] allowedFileTypes;
		private int maxUploadPathDepth = 5;

		public String getDestinationPath() {
			return destinationPath;
		}

		public void setDestinationPath(String destinationPath) {
			this.destinationPath = destinationPath;
		}

		public String[] getAllowedFileTypes() {
			return allowedFileTypes;
		}

		public void setAllowedFileTypes(String[] allowedFileTypes) {
			this.allowedFileTypes = allowedFileTypes;
		}

		public int getMaxUploadPathDepth() {
			return maxUploadPathDepth;
		}

		public void setMaxUploadPathDepth(int maxUploadPathDepth) {
			this.maxUploadPathDepth = maxUploadPathDepth;
		}

		public String getVirtualPath() {
			return virtualPath;
		}

		public void setVirtualPath(String virtualPath) {
			this.virtualPath = virtualPath;
		}
	}

	public static class Access {
		private String urlPattern;
		private List<String> roles;

		public String getUrlPattern() {
			return urlPattern;
		}

		public void setUrlPattern(String urlPattern) {
			this.urlPattern = urlPattern;
		}

		public List<String> getRoles() {
			return roles;
		}

		public void setRoles(List<String> roles) {
			this.roles = roles;
		}
	}

	/**
	 * 认证web服务使用的参数
	 * 
	 * @author Administrator
	 *
	 */
	public class AuthorizeCenterParam {
		private String cloudUrl;// 认证cloud提供的url
		private String tokenCookiePrefix;// 用户端用户登录时存储的cookie name的前缀

		public String getCloudUrl() {
			return cloudUrl;
		}

		public void setCloudUrl(String cloudUrl) {
			this.cloudUrl = cloudUrl;
		}

		public String getTokenCookiePrefix() {
			return tokenCookiePrefix;
		}

		public void setTokenCookiePrefix(String tokenCookiePrefix) {
			this.tokenCookiePrefix = tokenCookiePrefix;
		}
	}

	/**
	 * 使用认证中心时需要提交的参数(集成应用使用)
	 * 
	 * @author Chengen
	 *
	 */
	public class AuthorizeParam {
		private String redirectUrl;// 应用提供的回调url
		private String loginUrl;// 认证提供的登录url
		private String tokenUrl;// 认证提供的获取token的url

		public String getLoginUrl() {
			return loginUrl;
		}

		public void setLoginUrl(String loginUrl) {
			this.loginUrl = loginUrl;
		}

		public String getTokenUrl() {
			return tokenUrl;
		}

		public void setTokenUrl(String tokenUrl) {
			this.tokenUrl = tokenUrl;
		}

		public String getRedirectUrl() {
			return redirectUrl;
		}

		public void setRedirectUrl(String redirectUrl) {
			this.redirectUrl = redirectUrl;
		}
	}

	/**
	 * 应用相关信息
	 * 
	 * @author Administrator
	 *
	 */
	public class System {
		private long appId;// 应用ID
		private String appCode = "";// 应用编码
		private String appKey = "";// 秘钥

		public long getAppId() {
			return appId;
		}

		public void setAppId(long appId) {
			this.appId = appId;
		}

		public String getAppCode() {
			return appCode;
		}

		public void setAppCode(String appCode) {
			this.appCode = appCode;
		}

		public String getAppKey() {
			return appKey;
		}

		public void setAppKey(String appKey) {
			this.appKey = appKey;
		}
	}
}
