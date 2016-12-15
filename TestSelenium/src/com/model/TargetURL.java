package com.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TargetURL {

	private String tpye;
	private String area;
	private int starttime;
	private int endtime;
	private String word;

	public TargetURL(String tpye, String area, int starttime, int endtime, String word) {
		this.tpye = tpye;
		this.area = area;
		this.starttime = starttime;
		this.endtime = endtime;
		this.word = word;
	}

	public String getTpye() {
		return tpye;
	}

	public void setTpye(String tpye) {
		this.tpye = tpye;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getStarttime() {
		return starttime;
	}

	public void setStarttime(int starttime) {
		this.starttime = starttime;
	}

	public int getEndtime() {
		return endtime;
	}

	public void setEndtime(int endtime) {
		this.endtime = endtime;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	/**
	 * 活的请求地址
	 * @return
	 */
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
	
	/**
	 * 计算时间之间的天数，没有就按30天计算。
	 * @return int天数
	 */
	public int getdays(){
		DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.US);
		try {
			Date start=df.parse(Integer.toString(this.starttime));
			Date end =df.parse(Integer.toString(this.endtime));
			long day=(end.getTime()-start.getTime())/ (1000 * 60 * 60 * 24);
			return (int) day;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 30;
	}
}
