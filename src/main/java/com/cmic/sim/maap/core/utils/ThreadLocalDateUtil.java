package com.cmic.sim.maap.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Type ThreadLocalDateUtil.java
 * @Desc
 * @author checkStyle
 * @date 2016-6-15 下午12:55:25
 * @version
 */
public class ThreadLocalDateUtil {
    private static final String DATA_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
	    private static ThreadLocal<DateFormat> THREAD_LOCAL = new ThreadLocal<DateFormat>();

	    /**
	     *
	     * @return
	     */
	    public static DateFormat getDateFormat() {
	        DateFormat df = THREAD_LOCAL.get();
	        if(df==null){
	            df = new SimpleDateFormat(DATA_FORMAT);
	            THREAD_LOCAL.set(df);
	        }
	        return df;
	    }

	    /**
	     *
	     * @param date
	     * @return
	     * @throws ParseException
	     */
	    public static String formatDate(Date date) throws ParseException {
	        return getDateFormat().format(date);
	    }

	    /**
	     *
	     * @param strDate
	     * @return
	     * @throws ParseException
	     */
	    public static Date parse(String strDate) throws ParseException {
	        return getDateFormat().parse(strDate);
	    }
}
