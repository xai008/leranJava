package com.calculate;

import java.util.ArrayList;

public class CStepwise {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CsvUtil util = new CsvUtil("./calculation/1.csv");
		int rowNum = util.getRowNum();// 文件行数（包括标头）
		int colNum = util.getColNum();// 文件列数（包括序号列）
		float[][] source = new float[rowNum - 1][colNum - 1];// 文件中读取的数据矩阵
		float[] mean = new float[colNum - 1];// 每列均值
		float[] dev = new float[colNum - 1];// 每列标准差
		float[][] sp = new float[colNum - 1][colNum - 1];// 离差阵
		float[][] r = new float[colNum - 1][colNum - 1];// 相关系数矩阵
		ArrayList<String> choose = new ArrayList<String>();// 被选中的自变量
		for (int i = 0; i < rowNum - 1; i++) {
			for (int j = 0; j < colNum - 1; j++) {
				source[i][j] = Float.parseFloat(util.getString(i + 1, j + 1));
			}
		}

		// 计算均值
		for (int i = 0; i < colNum - 1; i++) {
			float[] listfloat = getfloatbycolumn(source, i);
			float sum = 0;
			for (int j = 0; j < listfloat.length; j++)
				sum += listfloat[j];
			mean[i] = sum / listfloat.length;
		}

		// 计算标准差
		for (int i = 0; i < colNum - 1; i++) {
			float[] listfloat = getfloatbycolumn(source, i);
			float sum = 0;
			for (int j = 0; j < listfloat.length; j++)
				sum += Math.pow((listfloat[j] - mean[i]), 2);
			dev[i] = (float) Math.sqrt(sum / listfloat.length);
		}

		// 计算离差阵(协方差矩阵)sp
		for (int i = 0; i < colNum - 1; i++) {

			for (int j = 0; j <= i; j++) {
				sp[i][j] = 0;
				for (int k = 0; k < rowNum - 1; k++)
					sp[i][j] += (source[k][i] - mean[i]) * (source[k][j] - mean[j]);
				sp[j][i] = sp[i][j];

			}
		}

		System.out.println("协方差矩阵");
		for (int i = 0; i < colNum - 1; i++) {

			for (int j = 0; j < colNum - 1; j++) {
				System.out.print(sp[i][j] + "\t");

			}
			System.out.println();
		}

		// 计算相关系数阵
		for (int i = 0; i < colNum - 1; i++) {

			for (int j = 0; j <= i; j++) {
				r[i][j] = (float) (sp[i][j] / Math.sqrt(sp[i][i] * sp[j][j]));
				r[j][i] = r[i][j];
			}
		}

		System.out.println("相关系数矩阵");
		for (int i = 0; i < colNum - 1; i++) {

			for (int j = 0; j < colNum - 1; j++) {
				System.out.print(r[i][j] + "\t");
			}
			System.out.println();
		}
		System.out.println();
		System.out.println();

	}
	
	public static float[] getfloatbycolumn(float[][] strarray, int column) {
		int rowlength = strarray.length;
		int columnlength = strarray[0].length;
		float[] templist = new float[rowlength];
		for (int i = 0; i < rowlength; i++)
			templist[i] = (strarray[i][column]);
		return templist;
	}

}
