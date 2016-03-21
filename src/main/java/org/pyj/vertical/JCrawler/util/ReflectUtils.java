package org.pyj.vertical.JCrawler.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.pyj.vertical.JCrawler.annotation.DescendantElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReflectUtils {
	
	private static final Logger log = LoggerFactory.getLogger(ReflectUtils.class);

	public static <T>Map<String, String> nodeAnnotations(Class<T> target){
		Map<String, String> map = new HashMap<>();
    	for(Field field : target.getDeclaredFields()){
			if(field.isAnnotationPresent(DescendantElement.class)){
				DescendantElement e = field.getAnnotation(DescendantElement.class);
				map.put(field.getName(), e.xpath());
			}
		}
    	return map;
	}

	public static <T>T getInstance(Class<T> clazz,Map<String, Object> valueMap) {
		T target = null;
		try {
			target = clazz.newInstance();
			for(Map.Entry<String, Object> fieldValue : valueMap.entrySet()){
				String fieldName = fieldValue.getKey();
				String methodName = getMethodName("set",fieldName);
				Class<?> fieldType = getfieldTypeMap(clazz).get(fieldName);
				Method method = clazz.getMethod(methodName,fieldType);
				method.invoke(target,fieldValue.getValue());
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			log.error("no such method,please check you method name is similar to [setMethod(Arg arg)]");
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			log.error("please check you method 's argument type matche or not"
					+ "and has any arguments in it");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return target;
	}

	public static <T>Object convert(Class<T> clazz, String fieldName, String text) {
		Object value = text;
		for(Field field : clazz.getDeclaredFields()){
			if(field.getName().equals(fieldName)){
				Class<?> fieldClass = field.getType();
				try {
					if(fieldClass.getName().equals(Integer.class.getName()) || 
							fieldClass.getName().equals("int")){
						value = Integer.parseInt(text);
					} else if(fieldClass.getName().equals(Long.class.getName()) || 
							fieldClass.getName().equals("long")){
						value = Long.parseLong(text);
					} else if(fieldClass.getName().equals(Float.class.getName()) || 
							fieldClass.getName().equals("float")){
						value = Float.parseFloat(text);
					} else if(fieldClass.getName().equals(Double.class.getName()) || 
							fieldClass.getName().equals("double")){
						value = Double.parseDouble(text);
					} else if(fieldClass.getName().equals(Date.class.getName())){
						value = new SimpleDateFormat("yyyy-MM-dd hh:ss").parse(text);
					}
				} catch (ParseException e) {
					value = null;
					log.error("VALUE["+text+"] is an illegal date pattern!");
				} catch (NumberFormatException e){
					value = 0;
					log.error("VALUE ["+text+"] cannot parse to a number,"
							+ "auto set FIELD ["+fieldName+"] value 0");
				}
			}
		}
		return value;
	}
	
	public static String getMethodName(String prefix,String fieldName){
		return prefix + fieldName.substring(0, 1).toUpperCase() + 
				fieldName.substring(1, fieldName.length());
	}
	
	public static <T>Map<String,Class<?>> getfieldTypeMap(Class<T> clazz){
		Map<String,Class<?>> fieldTypeMap = new HashMap<>();
		for(Field field : clazz.getDeclaredFields()){
			fieldTypeMap.put(field.getName(), field.getType());
		}
		return fieldTypeMap;
	}
	
}
