package net.osc.gin.agama.util;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectUtils {
	
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
			method.invoke(instance, args);
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
	
	public static <T> Object invokeSetter(String fieldName,Object instance,Object...args){
		Method method = getSetter(fieldName, instance.getClass());
		Object res = invokeMethod(instance, method,args);
		return res;
	}


    public static <T>Object getValue(String fieldName,Object instance){
        Method method = getSetter(fieldName, instance.getClass());
        Object res = invokeMethod(instance, method,null);
        return res;
    }
}
