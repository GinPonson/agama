package com.github.gin.agama.util;

import org.reflections.ReflectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.Set;

/**
 * @author  GinPonson
 */
public class ReflectUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectUtils.class);

	public static <T> T newInstance(Class<T> clazz){
		T instance = null;
		try {
			instance = clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	public static <T> Object invokeMethod(Object instance,Method method,Object...args){
		Object res = null;
		try {
            res = method.invoke(instance, args);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static <T> Method getSetter(String fieldName,Class<T> clazz){
		Method method = null;
		try {
			PropertyDescriptor fieldDesc = new PropertyDescriptor(fieldName,clazz);
			method = fieldDesc.getWriteMethod();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return method;
	}

    public static <T> Method getGetter(String fieldName,Class<T> clazz){
        Method method = null;
        try {
            PropertyDescriptor fieldDesc = new PropertyDescriptor(fieldName,clazz);
            method = fieldDesc.getReadMethod();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return method;
    }
	
	public static <T> Object setValue(String fieldName, Object instance, Object... args){
		Method method = getSetter(fieldName, instance.getClass());
		Object res = invokeMethod(instance, method,args);
		return res;
	}


    public static <T>Object getValue(String fieldName,Object instance,Object...args){
        Method method = getGetter(fieldName, instance.getClass());
        Object res = invokeMethod(instance, method,args);
        return res;
    }

	public static Set<Class<?>> getAllSuperType(Class clazz) {
		return ReflectionUtils.getAllSuperTypes(clazz);
	}

	public static boolean haveSuperType(Class childClazz, Class superClazz) {
		for (Class<?> clazz : getAllSuperType(childClazz)) {
			if (clazz.equals(superClazz)) {
				return true;
			}
		}
		return false;
	}

	public static Class getGenericClass(Type type, int i) {
		if (type instanceof ParameterizedType) { // 处理泛型类型
			return getGenericClass((ParameterizedType) type, i);
		} else if (type instanceof TypeVariable) { // 处理泛型擦拭对象
			return getGenericClass(((TypeVariable) type).getBounds()[0], 0);
		} else {// class本身也是type，强制转型
			return (Class) type;
		}
	}

	private static Class getGenericClass(ParameterizedType parameterizedType, int i) {
		Object genericClass = parameterizedType.getActualTypeArguments()[i];
		if (genericClass instanceof ParameterizedType) { // 处理多级泛型
			return (Class) ((ParameterizedType) genericClass).getRawType();
		} else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
			return (Class) ((GenericArrayType) genericClass).getGenericComponentType();
		} else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
			return getGenericClass(((TypeVariable) genericClass).getBounds()[0], 0);
		} else {
			return (Class) genericClass;
		}
	}
}
