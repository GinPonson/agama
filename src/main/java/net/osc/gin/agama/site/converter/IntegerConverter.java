package net.osc.gin.agama.site.converter;

public class IntegerConverter implements Converter{

	@Override
	public Integer convert(String arg) {
		Integer integer = null; 
		try{
			integer = Integer.parseInt(arg);
		} catch (NumberFormatException e){
			integer = null;
		}
		return integer;
	}

}
