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
	 * ���ݿ�ʼʱ��ͽ���ʱ�䷵��ʱ����ڵ�ʱ�伯��
	 * 
	 * @param beginDate 
	 * @param endDate 
	 * @return List 
	 * @throws ParseException 
	 */
	private List<String> getDatesBetweenTwoDate(String beginDate, String endDate) throws ParseException {
		List<String> lDate = new ArrayList<String>();
		lDate.add(beginDate);// �ѿ�ʼʱ����뼯��
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date dBegin = sdf.parse(beginDate);
		Date dEnd = sdf.parse(endDate);
		Calendar cal = Calendar.getInstance();
		// ʹ�ø����� Date ���ô� Calendar ��ʱ��
		cal.setTime(dBegin);
		boolean bContinue = true;
		while (bContinue) {
			// ���������Ĺ���Ϊ�����������ֶ���ӻ��ȥָ����ʱ����
			cal.add(Calendar.DAY_OF_MONTH, 1);
			// ���Դ������Ƿ���ָ������֮��
			if (dEnd.after(cal.getTime())) {
				lDate.add(sdf.format(cal.getTime()));
			} else {
				break;
			}
		}
		lDate.add(endDate);// �ѽ���ʱ����뼯��
		return lDate;
	}


	/**
	 * ͨ���Ա��ҳ�ȱʧ������
	 * @param start ��ʼʱ��
	 * @param end ����ʱ��
	 * @param doneDate �Ѿ���ɵ�����
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
