package com.afis.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.afis.web.exception.WebBusinessException;
import com.afis.web.modal.WebResponse;
import com.afis.web.utils.ExceptionRenderUtil;

public class AbstractController {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected ExceptionRenderUtil exceptionRenderUtil;

	@ExceptionHandler(value = WebBusinessException.class)
	@ResponseBody
	public WebResponse catchWebBusinessExceptionHandler(WebBusinessException ex) {
		logger.debug("AuthenticationException异常处理:{}", ex);
		WebResponse response = new WebResponse();
		response.setResponseCode(ex.getErrorCode());
		response.setResponseDesc(exceptionRenderUtil.getMessage(ex));
		return response;
	}
}
