package com.calculate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CsvUtil {

	private int RowNum;
	private int ColNum;
	private String[][] Darray;

	public CsvUtil(String filepath) {
		RowNum = 0;
		ColNum = 0;
		File file = new File(filepath);

		try {
			FileReader filereader = new FileReader(file);
			BufferedReader br = new BufferedReader(filereader);
			String s = null;
			br.mark((int) file.length() + 1);
			while ((s = br.readLine()) != null) {
				RowNum += 1;
				String[] a = s.split(",");
				if (a.length > ColNum) {
					ColNum = a.length;
				}
			}
			System.out.println(RowNum + "行---" + ColNum + "列");
			br.reset();
			Darray = new String[RowNum][ColNum];
			int rowNum = 0;
			while ((s = br.readLine()) != null) {
				rowNum += 1;
				String[] a = s.split(",");
				for (int i = 0; i < a.length; i++) {
					Darray[rowNum - 1][i] = a[i];
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("原始矩阵是：");
		for (int i = 0; i < RowNum; i++) {
			for (int j = 0; j < ColNum; j++) {
				System.out.print(Darray[i][j] + " ");
			}
			System.out.println(" ");
		}
		System.out.println(" ");
	}

	public int getRowNum() {
		return RowNum;
	}

	public int getColNum() {
		return ColNum;
	}

	public String getString(int i, int j) {
		return Darray[i][j];
	}

}
