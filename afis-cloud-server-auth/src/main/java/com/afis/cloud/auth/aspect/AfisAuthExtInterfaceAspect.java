package com.afis.cloud.auth.aspect;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.afis.ExceptionResultCode;
import com.afis.cloud.auth.business.store.AppFunctionManagements;
import com.afis.cloud.auth.business.store.impl.AppFunctionManagementsImpl;
import com.afis.cloud.auth.model.ApplicationModel;
import com.afis.cloud.auth.model.protocol.open.request.AbstractInterfaceRequest;
import com.afis.cloud.auth.util.AuthCacheUtil;
import com.afis.cloud.entities.model.auth.AppFunction;
import com.afis.cloud.entities.model.auth.Function;
import com.afis.cloud.exception.AuthenticationException;
import com.afis.cloud.exception.CommonException;
import com.afis.cloud.model.CommonConstants;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.ExceptionRenderUtil;
import com.afis.cloud.utils.SessionUtil;

@Order(1)
@Aspect
@Component
public class AfisAuthExtInterfaceAspect {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AuthCacheUtil authCacheUtil;

	@Autowired
	private ExceptionRenderUtil exceptionRenderUtil;

	private AppFunctionManagements AppFunctionManagements = new AppFunctionManagementsImpl();

	@Around("@annotation(AfisAuthExtInterface)")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		SessionUtil.addDebugLog(logger, "我在AfisAuthExtInterface的切面中");

		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		HttpMethod httpMethod = HttpMethod.resolve(request.getMethod());
		if (httpMethod == HttpMethod.GET) {// GET方式时，appCode和token放在url后面传递
			String appCode = request.getParameter("appCode");
			String token = request.getParameter("token");
			AbstractInterfaceRequest argRequest = new AbstractInterfaceRequest(appCode, token);
			try {
				// 校验参数完整性
				checkInput(argRequest);
				// 校验参数是否有效
				checkInputValid(argRequest);
			} catch (Exception e) {
				WebResponse response = new WebResponse();
				response.setResponseCode(ExceptionResultCode.getCode(e).getCode());
				response.setResponseDesc(exceptionRenderUtil.getMessage(e));
				return response;
			}
		} else {
			Object[] args = pjp.getArgs();
			if (args == null || args.length == 0) {
				return pjp.proceed();
			}
			for (Object arg : args) {
				if (arg instanceof AbstractInterfaceRequest) {
					try {
						AbstractInterfaceRequest argRequest = (AbstractInterfaceRequest) arg;
						// 校验参数完整性
						checkInput(argRequest);
						// 校验参数是否有效
						checkInputValid(argRequest);
					} catch (Exception e) {
						WebResponse response = new WebResponse();
						response.setResponseCode(ExceptionResultCode.getCode(e).getCode());
						response.setResponseDesc(exceptionRenderUtil.getMessage(e));
						return response;
					}
				}
			}
		}
		return pjp.proceed();
	}

	private void checkInputValid(AbstractInterfaceRequest argRequest)
			throws CommonException, AuthenticationException, SQLException {
		// 校验appCode对应的应用是否存在
		ApplicationModel application = authCacheUtil.getApplicationFromRedis(argRequest.getAppCode().trim());
		if (application == null || !CommonConstants.Status.VALID.getKey().equals(application.getStatus())) {
			SessionUtil.addDebugLog(logger, "应用未注册或应用状态非正常");
			throw new CommonException(CommonException.ANY_DESC, "应用未注册或应用状态非正常");
		}
		// 校验权限
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		String functionUrl = SessionUtil.getFunctionUrl(request);

		Function function = authCacheUtil.getFunctionByFunctionUrl(functionUrl);
		if (function == null) {
			throw new CommonException(CommonException.ANY_DESC, "此功能尚未开发");
		}
		AppFunction appFunction = AppFunctionManagements.getAppFunctionByUK(function.getId(), application.getId());
		if (appFunction == null) {
			throw new CommonException(CommonException.ANY_DESC, "应用未取得该功能的使用权限");
		}

		// 校验token是否有效
		authCacheUtil.getToken(argRequest.getToken().trim(), argRequest.getAppCode().trim());
	}

	private void checkInput(AbstractInterfaceRequest argRequest) throws AuthenticationException {
		if (argRequest.getAppCode() == null || argRequest.getAppCode().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST);
			exception.setDesc("appCode");
			throw exception;
		}
		if (argRequest.getToken() == null || argRequest.getToken().trim().length() == 0) {
			AuthenticationException exception = new AuthenticationException(AuthenticationException.PARAMS_NOT_EXIST);
			exception.setDesc("token");
			throw exception;
		}

	}
}
