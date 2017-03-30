package com.github.gin.agama.site.converter;


/**
 * @author  GinPonson
 */
public class LongConverter implements Converter{

	@Override
	public Long convert(String arg) {
		try{
			return Long.parseLong(arg);
		} catch (NumberFormatException e){
			return 0l;
		}
	}

}
