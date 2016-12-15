package com.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateList {

	/**
	 * 根据开始时间和结束时间返回时间段内的时间集合
	 * 
	 * @param beginDate 
	 * @param endDate 
	 * @return List 
	 * @throws ParseException 
	 */
	private List<String> getDatesBetweenTwoDate(String beginDate, String endDate) throws ParseException {
		List<String> lDate = new ArrayList<String>();
		lDate.add(beginDate);// 把开始时间加入集合
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dBegin = sdf.parse(beginDate);
		Date dEnd = sdf.parse(endDate);
		Calendar cal = Calendar.getInstance();
		// 使用给定的 Date 设置此 Calendar 的时间
		cal.setTime(dBegin);
		boolean bContinue = true;
		while (bContinue) {
			// 根据日历的规则，为给定的日历字段添加或减去指定的时间量
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// 测试此日期是否在指定日期之后
			if (dEnd.after(cal.getTime())) {
				lDate.add(sdf.format(cal.getTime()));
			} else {
				break;
			}
		}
		lDate.add(endDate);// 把结束时间加入集合
		return lDate;
	}


	/**
	 * 通过对比找出缺失的日期
	 * @param start 开始时间
	 * @param end 结束时间
	 * @param doneDate 已经完成的日期
	 */
	public void getmissdate(String start, String end, List<String> doneDate) {
		try {
			List<String> baseDate = getDatesBetweenTwoDate(start, end);
			List<String> noneDate = new ArrayList<String>();
			File miss = new File("result_pic", "miss.txt");
			if (miss.exists()) {
				miss.delete();
				miss.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(miss, true));
			for (int i = 0; i < baseDate.size(); i++) {
				if (doneDate.indexOf(baseDate.get(i)) == -1) {
					noneDate.add(baseDate.get(i));
					writer.write(baseDate.get(i) + "\r\n");
					writer.flush();
					System.out.println(baseDate.get(i));
				}
			}
			writer.close();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
