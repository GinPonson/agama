package com.github.gin.agama.site.converter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author  GinPonson
 */
public class TypeConverter {

	public static final Map<Class<?>,Converter> TYPE_HOLDER = new HashMap<>();
	
	static{
		TYPE_HOLDER.put(boolean.class, new BooleanConverter());
		TYPE_HOLDER.put(Boolean.class, new BooleanConverter());
		TYPE_HOLDER.put(int.class, new IntegerConverter());
		TYPE_HOLDER.put(Integer.class, new IntegerConverter());
		TYPE_HOLDER.put(float.class, new FloatConverter());
		TYPE_HOLDER.put(Float.class, new FloatConverter());
		TYPE_HOLDER.put(double.class, new DoubleConverter());
		TYPE_HOLDER.put(Double.class, new DoubleConverter());
		TYPE_HOLDER.put(long.class, new LongConverter());
		TYPE_HOLDER.put(Long.class, new LongConverter());
		TYPE_HOLDER.put(String.class, new StringConverter());
		TYPE_HOLDER.put(Date.class, new DateConverter());
	}
	
	public static Object convert(String dataText,Class<?> fieldType){
		Converter converter = TYPE_HOLDER.get(fieldType);
		return converter.convert(dataText);
	}
	
	
}
