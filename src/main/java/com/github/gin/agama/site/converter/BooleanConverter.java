package com.github.gin.agama.site.converter;

/**
 * @author  GinPonson
 */
public class BooleanConverter implements Converter{

	@Override
	public Boolean convert(String arg) {
		Boolean bool = Boolean.parseBoolean(arg);
		return bool;
	}

}
