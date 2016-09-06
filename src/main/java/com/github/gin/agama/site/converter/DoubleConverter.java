package com.github.gin.agama.site.converter;

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
