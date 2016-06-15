package net.osc.gin.agama.site.converter;

public class BooleanConverter implements Converter{

	@Override
	public Boolean convert(String arg) {
		Boolean bool = Boolean.parseBoolean(arg);
		return bool;
	}

}
