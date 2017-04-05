package com.github.gin.agama.site.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author  GinPonson
 */
public class DateConverter implements Converter{

	@Override
	public Date convert(String arg) {
		Date date = null;
		try {
			Long dateLong = Long.valueOf(arg);
			date = new Date(dateLong);
		} catch (NumberFormatException e) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			try {
				date = format.parse(arg);
			} catch (ParseException e1) {}
		}

		return date;
	}

}
