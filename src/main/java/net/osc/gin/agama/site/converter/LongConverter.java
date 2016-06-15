package net.osc.gin.agama.site.converter;

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
