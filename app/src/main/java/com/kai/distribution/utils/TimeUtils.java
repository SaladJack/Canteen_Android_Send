package com.kai.distribution.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;



public class TimeUtils extends Thread {
	




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
