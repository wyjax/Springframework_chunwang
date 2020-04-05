package com.java.myproject.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeCome {

	public String getTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd  hh:mm:ss");  
		Date dTime = new Date();
		TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
		sdf.setTimeZone(tz);
		return sdf.format(dTime);
	}
}
