package com.afis.cloud.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.afis.cloud.exception.WebSystemException;
import com.afis.utils.Converter;
import com.afis.utils.DateUtils;
import com.afis.utils.Fields;

public class BeanUtils {
	/**
	 * 数据转换，将Map数据转换为指定的JavaBean对象，支持List泛型与JavaBean的嵌套转换。用户Json字符串转换来的Map数据再次转换为JavaBean
	 * <br>
	 * 支持的基本类型转换有：
	 * String/int/Integer/long/Long/double/Double/float/Float/boolean/Boolean/char/Date/List
	 * <br>
	 * Date类型的转换根据元数据为数字或字符串分别有了两种转换方式，数字类型按照UTC毫秒数进行转换，字符串支持yyyy-MM-dd
	 * HH:mm:ss和yyyy-MM-dd两个格式解析。
	 * 
	 * @param source
	 * @param clazz
	 * @param <T>
	 * @return
	 */
	public static <T extends Object> T converter(Map<String, Object> source, Class<T> clazz) {
		T obj = null;
		try {
			obj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new WebSystemException(WebSystemException.CONVERTER_EXCEPTION, "can not initialized class ", e);
		}
		T finalObj = obj;
		source.forEach((key, value) -> {
			try {
				Field field = clazz.getDeclaredField(key);
				String methodName = Fields.toJavaField("SET_" + Fields.toDBField(field.getName()));
				Method method = clazz.getDeclaredMethod(methodName, field.getType());
				if (value != null) {
					if (field.getType().equals(String.class)) {
						method.invoke(finalObj, Converter.getString(value));
					} else if (field.getType().equals(short.class)) {
						method.invoke(finalObj, Converter.getShortPrimitive(value));
					} else if (field.getType().equals(Short.class)) {
						method.invoke(finalObj, Converter.getShort(value));
					} else if (field.getType().equals(int.class)) {
						method.invoke(finalObj, Converter.getIntPrimitive(value));
					} else if (field.getType().equals(Integer.class)) {
						method.invoke(finalObj, Converter.getInt(value));
					} else if (field.getType().equals(long.class)) {
						method.invoke(finalObj, Converter.getLongPrimitive(value));
					} else if (field.getType().equals(Long.class)) {
						method.invoke(finalObj, Converter.getLong(value));
					} else if (field.getType().equals(boolean.class)) {
						method.invoke(finalObj, Converter.getBooleanPrimitive(value));
					} else if (field.getType().equals(Boolean.class)) {
						method.invoke(finalObj, Converter.getBoolean(value));
					} else if (field.getType().equals(double.class)) {
						method.invoke(finalObj, Converter.getDoublePrimitive(value));
					} else if (field.getType().equals(Double.class)) {
						method.invoke(finalObj, Converter.getDouble(value));
					} else if (field.getType().equals(float.class)) {
						method.invoke(finalObj, Converter.getFloatPrimitive(value));
					} else if (field.getType().equals(Float.class)) {
						method.invoke(finalObj, Converter.getFloat(value));
					} else if (field.getType().equals(char.class) || field.getType().equals(Character.class)) {
						method.invoke(finalObj, Converter.getString(value).charAt(0));
					} else if (field.getType().equals(Date.class)) {
						if (value.getClass().equals(Integer.class) || value.getClass().equals(int.class)
								|| value.getClass().equals(long.class) || value.getClass().equals(Long.class)) {
							method.invoke(finalObj, new Date(Converter.getLongPrimitive(value)));
						} else if (value.getClass().equals(String.class)) {
							method.invoke(finalObj, stringToDate(Converter.getString(value)));
						}
					} else if (List.class.isAssignableFrom(field.getType())) {
						convertList(method, field, finalObj, value);
					} else if (Map.class.isAssignableFrom(field.getType())) {
						method.invoke(finalObj, value);
					} else {
						if (Map.class.isAssignableFrom(value.getClass())) {
							method.invoke(finalObj, converter((Map<String, Object>) value, field.getType()));
						} else {
							method.invoke(finalObj, value);
						}
					}
				}

			} catch (NoSuchFieldException | NoSuchMethodException | InvocationTargetException
					| IllegalAccessException e) {
				e.printStackTrace();
			}

		});
		return finalObj;
	}

	/**
	 * 转换list数据
	 * 
	 * @param setter
	 * @param sourceField
	 * @param destination
	 * @param source
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private static void convertList(Method setter, Field sourceField, Object destination, Object source)
			throws InvocationTargetException, IllegalAccessException {
		if (sourceField.getType().isAssignableFrom(source.getClass())) {
			List v = (List) source;
			if (v.size() > 0) {
				ParameterizedType parameterizedType = (ParameterizedType) sourceField.getGenericType();
				Type actualType = parameterizedType.getActualTypeArguments()[0];
				List list = new ArrayList();

				if (actualType.equals(String.class)) {
					v.forEach(item -> {
						list.add(Converter.getString(item));
					});
				} else if (actualType.equals(short.class) || actualType.equals(Short.class)) {
					v.forEach(item -> {
						list.add(Converter.getShort(item));
					});
				} else if (actualType.equals(long.class) || actualType.equals(Long.class)) {
					v.forEach(item -> {
						list.add(Converter.getLong(item));
					});
				} else if (actualType.equals(boolean.class) || actualType.equals(Boolean.class)) {
					v.forEach(item -> {
						list.add(Converter.getBoolean(item));
					});
				} else if (actualType.equals(double.class) || actualType.equals(Double.class)) {
					v.forEach(item -> {
						list.add(Converter.getDouble(item));
					});
				} else if (actualType.equals(float.class) || actualType.equals(Float.class)) {
					v.forEach(item -> {
						list.add(Converter.getDouble(item));
					});
				} else if (actualType.equals(Date.class)) {
					v.forEach(item -> {
						if (item.getClass().equals(int.class) || item.getClass().equals(long.class)
								|| item.getClass().equals(Integer.class) || item.getClass().equals(Long.class)) {
							list.add(new Date(Converter.getLongPrimitive(item)));
						} else {
							list.add(stringToDate(Converter.getString(item)));
						}
					});
				} else if (Map.class.isAssignableFrom((Class<?>) actualType)) {
					v.forEach(item -> {
						list.add(item);
					});
				} else if (List.class.isAssignableFrom((Class<?>) actualType)) {
					v.forEach(item -> {
						list.add(item);
					});
				} else {
					v.forEach(item -> {
						if (Map.class.isAssignableFrom(item.getClass())) {
							list.add(converter((Map<String, Object>) item, (Class) actualType));
						} else {
							throw new WebSystemException(WebSystemException.CONVERTER_EXCEPTION,
									"can not convert [" + item.getClass() + "] to [" + actualType.getClass() + "]");
						}
					});
				}
				setter.invoke(destination, list);
			} else {
				setter.invoke(destination, v);
			}
		} else {
			setter.invoke(destination, source);
		}
	}

	/**
	 * string转换为Date格式，默认只支持 yyyy-MM-dd HH:mm:ss 和 yyyy-MM-dd两种格式
	 * 
	 * @param source
	 * @return
	 */
	private static Date stringToDate(String source) {
		if (source == null) {
			return null;
		}
		try {
			return DateUtils.parse(source, "yyyy-MM-dd HH:mm:ss");
		} catch (ParseException e) {
			try {
				return DateUtils.parse(source, "yyyy-MM-dd");
			} catch (ParseException e1) {
				throw new WebSystemException(WebSystemException.CONVERTER_EXCEPTION,
						"format not match: yyyy-MM-dd HH:mm:ss", e);
			}
		}
	}
}
