package com.afis.cloud.auth.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.business.store.FunctionManagements;
import com.afis.cloud.auth.business.store.impl.FunctionManagementsImpl;
import com.afis.cloud.auth.model.FunctionListModel;
import com.afis.cloud.db.PageResult;
import com.afis.cloud.entities.model.auth.Function;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.SessionUtil;

import io.swagger.annotations.ApiOperation;

/**
 * @Description:
 * @Author: lizheng
 * @Date: 2018/11/8 10:15
 */
@RestController
@RequestMapping("/auth/admin/functions")
public class AdminFunctionController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	FunctionManagements functionService = new FunctionManagementsImpl();

	@GetMapping
	@ApiOperation("应用授权列表")
	public WebResponse getFunctions(@RequestParam(required = false) String type,
			@RequestParam Integer start, @RequestParam Integer limit)
			throws CommonException, SQLException {

		// 非空校验分页信息
		checkPageParam(start, limit);
		// 封装页面传递的参数
		type = SessionUtil.validateString(type, "类型", false);
		Map<String, Object> map = new HashMap<String, Object>();
		if (!"".equals(type)) {
			map.put("userName", type);
		}
		// 数据查询
		PageResult<Function> functions = functionService.getFunctions(map, start, limit);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, functions);
	}

	@GetMapping("/byGroups")
	@ApiOperation("应用授权列表")
	public WebResponse getFunctionsByGroup() throws  SQLException {
		// 数据查询
		List<FunctionListModel> functionsByGroups = functionService.getFunctionsByGroups();
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, functionsByGroups);
	}

	@GetMapping("/byAppId/{id}")
	@ApiOperation("应用授权列表")
	public WebResponse getFunctionsByAppId(@PathVariable Long id)
			throws SQLException {
		// 数据查询
		List<FunctionListModel> functionsByAppId = functionService.getFunctionsByAppId(id);
		return SessionUtil.generateWebResponse(ExceptionResultCode.SUCCESS, null, functionsByAppId);
	}

	private void checkPageParam(Integer start, Integer limit) throws CommonException {
		SessionUtil.getIntegerFromWebRequest(String.valueOf(start), "起始条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);
		SessionUtil.getIntegerFromWebRequest(String.valueOf(limit), "每页条数", null, Boolean.TRUE, Boolean.TRUE,
				Boolean.TRUE, null);
	}
}
