package com.github.gin.agama.site.converter;

public class FloatConverter implements Converter{

	@Override
	public Float convert(String arg) {
		try{
			return Float.parseFloat(arg);

		}catch(NumberFormatException e){
			return 0f;
		}
	}
}
