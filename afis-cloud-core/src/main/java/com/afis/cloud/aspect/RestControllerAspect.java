package com.afis.cloud.aspect;

import java.sql.SQLException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.afis.ExceptionResultCode;
import com.afis.cloud.annotation.NoTransaction;
import com.afis.cloud.annotation.util.ParserUtil;
import com.afis.cloud.model.WebResponse;
import com.afis.cloud.utils.DBUtils;
import com.afis.cloud.utils.ExceptionRenderUtil;
import com.ibatis.sqlmap.client.SqlMapClient;

import io.swagger.annotations.ApiOperation;

/**
 * RestController的切面
 * 
 * @author Administrator
 *
 */
@Aspect
@Component
@Order(10)
public class RestControllerAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ExceptionRenderUtil exceptionRenderUtil;

	@Around("@within(org.springframework.web.bind.annotation.RestController)")
	public Object apiTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
		ApiOperation api = ParserUtil.getMethodAnnotation(joinPoint, ApiOperation.class);
		if (api != null) {
			if (logger.isDebugEnabled()) {
				logger.debug("method's description:{}", api.value());
			}
		}

		if (!needTransaction(joinPoint)) {
			if (logger.isDebugEnabled()) {
				logger.debug("GetMapping not need transaction,method:{}", getMethodName(joinPoint));
			}
			return joinPoint.proceed();
		}

		WebResponse result = null;
		NoTransaction annotation = ParserUtil.getMethodAnnotation(joinPoint, NoTransaction.class);
		SqlMapClient sqlMapClient = null;
		try {
			if (annotation == null) {
				sqlMapClient = DBUtils.getSqlMapClient();
				sqlMapClient.startTransaction();
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("no transaction for method: {}", getMethodName(joinPoint));
				}
			}

			result = (WebResponse) joinPoint.proceed();

			if (annotation == null && sqlMapClient != null) {
				sqlMapClient.commitTransaction();
			}
		} catch (Exception e) {
			if (logger.isWarnEnabled()) {
				logger.warn("method [{}] throws exception: {}", getMethodName(joinPoint), e.getMessage(), e);
			}
			WebResponse response1 = new WebResponse();
			response1.setResponseCode(ExceptionResultCode.getCode(e).getCode());
			response1.setResponseDesc(exceptionRenderUtil.getMessage(e));
			result = response1;

		} finally {
			if (sqlMapClient != null) {
				try {
					sqlMapClient.endTransaction();
				} catch (SQLException e1) {
					if (logger.isWarnEnabled()) {
						logger.warn("End transaction failure for method : [{}]", getMethodName(joinPoint), e1);
					}
				}
			}
		}

		return result;
	}

	/**
	 * true--需要事务;false--不需要事务
	 * 
	 * GET方式是不需要事务处理的
	 * 
	 * @param joinPoint
	 * @return
	 */
	private boolean needTransaction(ProceedingJoinPoint joinPoint) {
		return ParserUtil.getMethodAnnotation(joinPoint, GetMapping.class) == null;
	}

	private String getMethodName(ProceedingJoinPoint joinPoint) {
		String methodName = joinPoint.getSignature().toShortString();
		String SHORT_METHOD_NAME_SUFFIX = "(..)";
		if (methodName.endsWith(SHORT_METHOD_NAME_SUFFIX)) {
			methodName = methodName.substring(0, methodName.length() - SHORT_METHOD_NAME_SUFFIX.length());
		}
		return methodName;
	}
}
