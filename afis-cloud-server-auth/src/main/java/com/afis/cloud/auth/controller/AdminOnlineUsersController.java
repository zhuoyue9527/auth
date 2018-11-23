package com.afis.cloud.auth.controller;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.business.store.OnlineUserManagements;
import com.afis.cloud.auth.business.store.UserManagements;
import com.afis.cloud.auth.business.store.impl.OnlineUserManagementsImpl;
import com.afis.cloud.auth.business.store.impl.UserManagementsImpl;
import com.afis.cloud.auth.config.AuthCloudConfig;
import com.afis.cloud.auth.model.protocol.AdminOnlineUserFrontRequest;
import com.afis.cloud.auth.model.protocol.AdminUserFrontRequest;
import com.afis.cloud.auth.model.protocol.AdminUserResponse;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.User;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.exception.WebBusinessException;
import com.afis.cloud.model.CommonConstants;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;
import com.afis.web.modal.UserDetails;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/1 16:22
 */
@RestController
@RequestMapping("/auth/admin/onlineUsers")
public class AdminOnlineUsersController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private KafkaTemplate kafkaTemplate;

	@Autowired
	private AuthCacheUtil authCacheUtil;

	@GetMapping
	@ApiOperation("在线用户列表")
	public WebResponse getUsers(@RequestParam(required = false) String userAccount,
			@RequestParam(required = false) String userName, @RequestParam(required = false) String clientType,
			@RequestParam(required = false) String registerTimeStart,
			@RequestParam(required = false) String registerTimeEnd,
			@RequestParam(required = false) String lastLoginTimeStart,
			@RequestParam(required = false) String lastLoginTimeEnd, @RequestParam(required = false) String registerAPP,
			@RequestParam Integer start, @RequestParam Integer limit) throws WebBusinessException, SQLException {
		// 非空校验分页信息
		checkPageParam(start, limit);
		// 封装页面传递的参数
		Map<String, Object> map = packParm(userAccount, userName, clientType, lastLoginTimeStart, lastLoginTimeEnd,
				registerAPP, registerTimeStart, registerTimeEnd);

		OnlineUserManagements onlineUserService = new OnlineUserManagementsImpl(kafkaTemplate, authCacheUtil);

		PageResult<AdminUserResponse> usersList = onlineUserService.getOnlineUsers(map, start, limit);

		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, usersList);
	}

	@DeleteMapping
	@ApiOperation("在线用户踢出")
	public WebResponse deleteUsers(HttpServletRequest request, @RequestBody AdminOnlineUserFrontRequest param)
			throws WebBusinessException, SQLException {

		OnlineUserManagements onlineUserService = new OnlineUserManagementsImpl(kafkaTemplate, authCacheUtil);
		onlineUserService.removeOnlineUser(SessionUtil.getFunctionUrl(request), param);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, null);
	}

	private void checkPageParam(Integer start, Integer limit) throws CommonException {

		SessionUtil.getIntegerFromWebRequest(String.valueOf(start), "起始条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);
		SessionUtil.getIntegerFromWebRequest(String.valueOf(limit), "每页条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);

	}

	private Map<String, Object> packParm(String userAccount, String userName, String clientType,
			String lastLoginTimeStart, String lastLoginTimeEnd, String registerAPP, String registerTimeStart,
			String registerTimeEnd) throws CommonException {
		Map<String, Object> map = new HashMap<String, Object>();

		userAccount = SessionUtil.validateString(userAccount, "用户账号", false);
		if (!"".equals(userAccount)) {
			map.put("userAccount", userAccount);
		}
		userName = SessionUtil.validateString(userName, "用户名称", false);
		if (!"".equals(userName)) {
			map.put("userName", userName);
		}
		clientType = SessionUtil.validateString(clientType, "类型", false);
		if (!"".equals(clientType)) {
			map.put("clientType", clientType);
		}
		lastLoginTimeStart = SessionUtil.validateString(lastLoginTimeStart, "状态", false);
		if (!"".equals(lastLoginTimeStart)) {
			map.put("lastLoginTimeStart", lastLoginTimeStart);
		}
		lastLoginTimeEnd = SessionUtil.validateString(lastLoginTimeEnd, "状态", false);
		if (!"".equals(lastLoginTimeEnd)) {
			map.put("lastLoginTimeEnd", lastLoginTimeEnd);
		}
		if (registerAPP != null) {
			map.put("registerAPP", registerAPP);
		}
		registerTimeStart = SessionUtil.validateString(registerTimeStart, "操作开始时间", false);
		if (!"".equals(registerTimeStart)) {
			map.put("registerTimeStart", registerTimeStart);
		}
		registerTimeEnd = SessionUtil.validateString(registerTimeEnd, "操作结束时间", false);
		if (!"".equals(registerTimeEnd)) {
			map.put("registerTimeEnd", registerTimeEnd);
		}
		return map;
	}

}
