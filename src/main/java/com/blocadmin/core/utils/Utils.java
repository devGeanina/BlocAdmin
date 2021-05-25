package com.blocadmin.core.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Utils {

	public static String convertDateToString(Date date) {
		Calendar cal = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		cal.setTime(date);
		return sdf.format(date);
	}
}
