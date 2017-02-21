package com.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class MonthUrl {

	private String tpye;
	private String area;
	private String starttime;
	private String endtime;
	private String word;
	private int day;
	
	public int getDay() {
		return day;
	}

	public MonthUrl(String tpye, String area, int year, int month, String word) {		
		this.tpye = tpye;
		this.area = area;
		this.word = word;
		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month-1,1);
		DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.US);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		this.starttime=df.format(calendar.getTime());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		this.endtime=df.format(calendar.getTime());
		this.day=calendar.getActualMaximum(Calendar.DATE);
		
		//移动了一下为了消除不存在
//		calendar.add(Calendar.DATE,3);
//		this.endtime=df.format(calendar.getTime());
//		this.day+=3;		
	}

	public String getURL() {
		String url = null;
		try {
			url = "http://index.baidu.com/?tpl=trend&type=" + this.tpye + "&area="
					+ URLEncoder.encode(this.area, "gb2312") + "&time=" + this.starttime + "%7C" + this.endtime
					+ "&word=" + URLEncoder.encode(this.word, "gb2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	
}
