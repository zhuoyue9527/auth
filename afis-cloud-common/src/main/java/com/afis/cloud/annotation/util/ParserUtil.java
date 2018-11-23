package com.afis.cloud.annotation.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class ParserUtil {
	/**
	 * 获取方法上的某个注解
	 * null 表示没有这个注解作用在方法上
	 * 
	 * @param jp
	 * @param clazz
	 * @return
	 */
	public static <T extends Annotation> T getMethodAnnotation(JoinPoint jp, Class<T> clazz) {
		Method method = null;
		MethodSignature signature2 = (MethodSignature) jp.getSignature();
		try {
			method = signature2.getMethod();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		if (method != null && method.isAnnotationPresent(clazz)) {
			return (T) method.getAnnotation(clazz);
		}
		return null;
	}
}
