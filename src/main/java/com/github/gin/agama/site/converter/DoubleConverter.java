package com.github.gin.agama.site.converter;

/**
 * @author  GinPonson
 */
public class DoubleConverter implements Converter{

	@Override
	public Double convert(String arg) {
		try{
			return Double.parseDouble(arg);
		} catch(NumberFormatException e){
			return 0d;
		}
		
	}

}
