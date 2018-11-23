package com.afis.web.utils;

import javax.servlet.http.HttpServletRequest;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 获取HTTP协议原始信息
 * 
 * @author Chengen
 *
 */
public class HttpOrignalUtil {

	/**
	 * 获取最接近真实的IP地址，排除代理转发。
	 * 
	 * @param request
	 * @return
	 */
	public static String getRemoteIpReal(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	/**
	 * 获取用户代理，用户使用的浏览器、操作系统
	 * 
	 * @param request
	 * @return
	 */
	private static String getUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	/**
	 * 获取操作系统信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getSystemInfo(HttpServletRequest request) {
		UserAgent agent = new UserAgent(getUserAgent(request));
		return agent.getOperatingSystem().getName();
	}

	/**
	 * 获取浏览器信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getBrowserInfo(HttpServletRequest request) {
		String userAgent = getUserAgent(request);
		UserAgent agent = new UserAgent(userAgent);
		String browser = agent.getBrowser().getName();
		if (browser.startsWith(Browser.IE.getName())) {
			return browser;
		} else {
			return browser + " " + agent.getBrowserVersion();
		}
	}
}
