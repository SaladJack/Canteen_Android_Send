package com.kai.distribution.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;



public class TimeUtils {
	

	public static String MillisToString(long time){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		long now = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		return formatter.format(calendar.getTime()).substring(11,16);//前闭后开
	}

	//XXXX.XX.XX XX:XX
	public static String MillisToString_Point(long time){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//		long now = System.currentTimeMillis();
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(time);
		String s = formatter.format(calendar.getTime()).substring(0,16);
		String[] ss = s.split("-");
		return ss[0]+"."+ss[1]+"."+ss[2];

	}


	//获得所选日期的毫秒数
    public static long getDayMillis(String time){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date curDate = dateFormat.parse(time + " " + "00:00:00");

			return curDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	//获得所选月份的毫秒数
	public static long getMonthMillis(String time){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date curDate = dateFormat.parse(time.substring(0,8) + "01 00:00:00");

			return curDate.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static String getTime(String year,String month,String day){


		month = month.length()==1 ? "0"+month.charAt(0):month;
		day = day.length()==1 ? "0"+day.charAt(0):day;

		return year + "-" + month + "-" + day;

	}

}
