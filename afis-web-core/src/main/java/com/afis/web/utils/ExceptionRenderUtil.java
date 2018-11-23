package com.afis.web.utils;

import java.text.MessageFormat;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.afis.ExceptionResultCode;
import com.afis.web.exception.WebBusinessException;
import com.afis.web.exception.WebSystemException;

/**
 * 异常码翻译 Created by hsw on 2017/1/19.
 */
public class ExceptionRenderUtil {

	private static Logger logger = LoggerFactory.getLogger(ExceptionRenderUtil.class);

	private MessageSource messageSource;

	private Locale locale = null;

	public ExceptionRenderUtil() {
		locale = LocaleContextHolder.getLocale();
	}

	/**
	 * 获取异常码对应的错误描述信息，描述信息记录在classpath:messages.properties文件中 <br/>
	 * 多语言环境时翻译时，可配置多个message描述信息文件，如：
	 * messages_zh-CN.properties文件对应中文翻译，messages_en-US.properties对应英文翻译。
	 * 将根据HTTP请求的语言进行对应语言的翻译。详见{@link LocaleContextHolder} <br/>
	 * <br/>
	 * 错误码读取，对于自定义异常已设置异常码的，将直接读取异常码，对于JDK异常或其他第三方组件的未知异常，异常码已有部分预定义， 具体异常码读取参见：
	 * {@link ExceptionResultCode#getCode(Throwable)} <br/>
	 * <br/>
	 * 错误码对应的翻译内容可携带参数，如 {@code 1={0} }不存在,其中 {@code {0} }将被Exception中的描述信息替换，只有继承自
	 * {@link WebSystemException} 和 {@link WebBusinessException} 的异常才可以设置参数描述信息，详见：
	 * {@link WebBusinessException#setDesc(String...)} ，
	 * {@link WebSystemException#setDesc(String...)}, 参数替换原则详见：
	 * {@link MessageFormat#format(String, Object...)}
	 * 
	 * @param ex
	 *            异常
	 * @return
	 */
	public String getMessage(Exception ex) {
		ExceptionResultCode.Code code = ExceptionResultCode.getCode(ex);
		String message = null;
		try {
			if (ex instanceof WebBusinessException) {
				WebBusinessException webBusinessException = (WebBusinessException) ex;
				if (webBusinessException.getMessage().startsWith(webBusinessException.getNamePrefix())) {
					message = webBusinessException.getMessage();
					message = message.substring(message.indexOf(":")+1);
					if ("null".equals(message)) {
						message = this.messageSource.getMessage(String.valueOf(code.getCode()), null, locale);
						if (message != null && webBusinessException.getDesc() != null) {
							message = formatMessage(message, webBusinessException.getDesc());
						}
					}
				} else {
					message = this.messageSource.getMessage(String.valueOf(code.getCode()), null, locale);
					if (message != null && webBusinessException.getDesc() != null) {
						message = formatMessage(message, webBusinessException.getDesc());
					}
				}
			} else if (ex instanceof WebSystemException) {
				WebSystemException webBusinessException = (WebSystemException) ex;
				if (webBusinessException.getMessage().startsWith(webBusinessException.getNamePrefix())) {
					message = webBusinessException.getMessage();
					message = message.substring(message.indexOf(":")+1);
					if ("null".equals(message)) {
						message = this.messageSource.getMessage(String.valueOf(code.getCode()), null, locale);
						if (message != null && webBusinessException.getDesc() != null) {
							message = formatMessage(message, webBusinessException.getDesc());
						}
					}
				} else {
					message = this.messageSource.getMessage(String.valueOf(code.getCode()), null, locale);
					if (message != null && webBusinessException.getDesc() != null) {
						message = formatMessage(message, webBusinessException.getDesc());
					}
				}
			}
		} catch (Exception e) {
			logger.info("Exception render casuse: {}", e.getMessage(), e);
		}
		return message;
	}

	private String formatMessage(String message, String... replace) {
		return MessageFormat.format(message, replace);
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
