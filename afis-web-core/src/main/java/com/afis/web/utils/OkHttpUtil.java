package com.afis.web.utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMethod;

import com.afis.web.exception.CommonException;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.UserDetails;
import com.afis.web.modal.WebResponse;
import com.afis.web.model.DefaultCloudRequest;
import com.alibaba.fastjson.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * OKHttp 帮助类
 * 
 * @author afis
 *
 */
public class OkHttpUtil {

	public static final okhttp3.MediaType MEDIA_TYPE = okhttp3.MediaType.parse("application/JSON; charset=utf-8");

	private static final OkHttpClient CLIENT = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(20, TimeUnit.SECONDS).writeTimeout(20, TimeUnit.SECONDS).build();

	private static Logger logger = LoggerFactory.getLogger(OkHttpUtil.class);

	/**
	 * 以post方式请求cloud
	 * 
	 * @param url
	 * @param request
	 * @return
	 * @throws WebBusinessException
	 */
	public static WebResponse webPostToCloudService(HttpServletRequest request, String url, DefaultCloudRequest obj)
			throws WebBusinessException {
		if (obj != null && obj.getUserDetails() == null) {
			obj.setUserDetails((UserDetails) request.getAttribute(UserDetails.class.getName()));
		}
		return webToCloudService(url, obj, RequestMethod.POST);
	}

	/**
	 * 以get方式请求cloud
	 * 
	 * @param url
	 * @param obj
	 * @return
	 * @throws WebBusinessException
	 */
	public static WebResponse webGetToCloudService(HttpServletRequest request, String url, DefaultCloudRequest obj)
			throws WebBusinessException {
		if (obj != null && obj.getUserDetails() == null) {
			obj.setUserDetails((UserDetails) request.getAttribute(UserDetails.class.getName()));
		}
		return webToCloudService(url, obj, RequestMethod.GET);
	}

	/**
	 * 以put方式请求cloud
	 * 
	 * @param url
	 * @param obj
	 * @return
	 * @throws WebBusinessException
	 */
	public static WebResponse webPutToCloudService(HttpServletRequest request, String url, DefaultCloudRequest obj)
			throws WebBusinessException {
		if (obj != null && obj.getUserDetails() == null) {
			obj.setUserDetails((UserDetails) request.getAttribute(UserDetails.class.getName()));
		}
		return webToCloudService(url, obj, RequestMethod.PUT);
	}

	/**
	 * 以delete方式请求cloud
	 * 
	 * @param url
	 * @param obj
	 * @return
	 * @throws WebBusinessException
	 */
	public static WebResponse webDeleteToCloudService(HttpServletRequest request, String url, DefaultCloudRequest obj)
			throws WebBusinessException {
		if (obj != null && obj.getUserDetails() == null) {
			obj.setUserDetails((UserDetails) request.getAttribute(UserDetails.class.getName()));
		}
		return webToCloudService(url, obj, RequestMethod.DELETE);
	}

	/**
	 * 以patch方式请求cloud
	 * 
	 * @param url
	 * @param obj
	 * @return
	 * @throws WebBusinessException
	 */
	public static WebResponse webPatchToCloudService(HttpServletRequest request, String url, DefaultCloudRequest obj)
			throws WebBusinessException {
		if (obj != null && obj.getUserDetails() == null) {
			obj.setUserDetails((UserDetails) request.getAttribute(UserDetails.class.getName()));
		}
		return webToCloudService(url, obj, RequestMethod.PATCH);
	}

	private static WebResponse webToCloudService(String url, DefaultCloudRequest obj, RequestMethod type)
			throws WebBusinessException {
		String rtnMsg = sendSynMessage(url, obj, type);
		if (rtnMsg != null) {
			WebResponse response = JSONObject.parseObject(rtnMsg, WebResponse.class);
			return response;
		} else {
			throw new WebBusinessException(CommonException.TIME_OUT, "调用接口失败");
		}
	}

	/**
	 * 发送同步信息
	 * 
	 * @param url
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	private static String sendSynMessage(String url, Object obj, RequestMethod type) {
		String jsonString = JSONObject.toJSONString(obj);
		Request request = null;
		if (type.equals(RequestMethod.POST)) {// post方式
			request = new Request.Builder().url(url).post(RequestBody.create(MEDIA_TYPE, jsonString)).build();
		} else if (type.equals(RequestMethod.GET)) {// get方式
			request = new Request.Builder().url(url).get().build();
		} else if (type.equals(RequestMethod.DELETE)) {// delete方式
			request = new Request.Builder().url(url).delete(RequestBody.create(MEDIA_TYPE, jsonString)).build();
		} else if (type.equals(RequestMethod.PATCH)) {// patch方式
			request = new Request.Builder().url(url).patch(RequestBody.create(MEDIA_TYPE, jsonString)).build();
		} else if (type.equals(RequestMethod.PUT)) {// put方式
			request = new Request.Builder().url(url).put(RequestBody.create(MEDIA_TYPE, jsonString)).build();
		} else {
			if (logger.isDebugEnabled()) {
				logger.debug("not support RequestMethod:{}", type);
			}
			return null;
		}
		try {
			Response response = CLIENT.newCall(request).execute();
			if (response.isSuccessful()) {
				ResponseBody body = response.body();
				byte[] bs = body.bytes();
				String result = new String(bs);
				return result;
			} else {
				logger.debug("Response:{},Request:{}", response, request);
				response.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
