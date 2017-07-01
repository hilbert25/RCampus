package org.sunhp.rcampus.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 实体反射工具类
 * 
 * @author fuyufeng
 * 
 */
public final class EntityReflectionUtils {

	private static final String GET = "get";

	private static final String SET = "set";

	private static final String IS = "is";

	/**
	 * 根据实体得到实体中定义的属性名称和值
	 * 
	 * @param entity
	 * @return
	 */
	public final static <T> Map<String, Object> fields(T entity) {
		Map<String, Object> results = new HashMap<String, Object>();
		if (entity != null) {
			Field[] fields = entity.getClass().getDeclaredFields();
			if (fields != null) {
				for (Field field : fields) {

					int modifier = field.getModifiers();
					Class<?> clazzType = field.getType();
					// 排除静态字段
					if (!Modifier.isStatic(modifier)) {
						String name = field.getName();
						Object value = null;
						String right = name.substring(1);
						String head = (name.charAt(0) + "").toUpperCase();
						try {
							String prefix = clazzType.toString().equals(
									"boolean")
									|| clazzType.toString().equals(
											"class java.lang.Boolean") ? IS
									: GET;
							value = entity.getClass()
									.getMethod(prefix + (head + right))
									.invoke(entity);
						} catch (IllegalAccessException
								| IllegalArgumentException
								| InvocationTargetException
								| NoSuchMethodException | SecurityException e) {
							if (e instanceof NoSuchMethodException) {
								String prefix = clazzType.toString().equals(
										"boolean")
										|| clazzType.toString().equals(
												"class java.lang.Boolean") ? GET
										: GET;
								try {
									value = entity.getClass()
											.getMethod(prefix + (head + right))
											.invoke(entity);
								} catch (IllegalAccessException
										| IllegalArgumentException
										| InvocationTargetException
										| NoSuchMethodException
										| SecurityException e1) {
									e1.printStackTrace();
								}
							}
						}

						results.put(name, value);

					}

				}
			}
		}
		return results;
	}

	public static <T> T set(T entity, String fieldName, Object value) {
		if (entity != null) {
			Field[] fields = entity.getClass().getDeclaredFields();
			if (fields != null) {
				for (Field field : fields) {
					int modifier = field.getModifiers();
					// 排除静态字段
					if (!Modifier.isStatic(modifier)) {
						String name = field.getName();
						if (name.equals(fieldName)) {
							String right = name.substring(1);
							String head = (name.charAt(0) + "").toUpperCase();
							try {
								entity.getClass()
										.getMethod(SET + (head + right),
												field.getType())
										.invoke(entity, value);
								break;
							} catch (IllegalAccessException
									| IllegalArgumentException
									| InvocationTargetException
									| NoSuchMethodException | SecurityException e) {
								e.printStackTrace();
							}
						}

					}

				}
			}
		}
		return entity;
	}

	/**
	 * 如果entity中包含fieldName属性，则将value中设置到该属性值上，否则忽略
	 * 
	 * @param entity
	 * @param fieldName
	 * @param value
	 * @return
	 */
	public static <T> T testSet(T entity, String fieldName, Object value) {
		boolean sign = false;
		if (entity != null) {
			Field[] fields = entity.getClass().getDeclaredFields();
			if (fields != null) {
				for (Field field : fields) {
					String name = field.getName();
					if (name.equals(fieldName)) {
						sign = true;
						break;
					}
				}
			}
		}
		if (sign) {
			set(entity, fieldName, value);
		}
		return entity;
	}
}
