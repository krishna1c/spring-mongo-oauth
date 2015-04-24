package sample.domain.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private final static SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	
	public static Date getDate(String str) throws ParseException {
		return format.parse(str);
	}

}