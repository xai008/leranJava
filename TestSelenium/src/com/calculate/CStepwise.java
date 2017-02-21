package com.calculate;

import java.util.ArrayList;

public class CStepwise {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CsvUtil util = new CsvUtil("./calculation/1.csv");
		int rowNum = util.getRowNum();// �ļ�������������ͷ��
		int colNum = util.getColNum();// �ļ���������������У�
		float[][] source = new float[rowNum - 1][colNum - 1];// �ļ��ж�ȡ�����ݾ���
		float[] mean = new float[colNum - 1];// ÿ�о�ֵ
		float[] dev = new float[colNum - 1];// ÿ�б�׼��
		float[][] sp = new float[colNum - 1][colNum - 1];// �����
		float[][] r = new float[colNum - 1][colNum - 1];// ���ϵ������
		ArrayList<String> choose = new ArrayList<String>();// ��ѡ�е��Ա���
		for (int i = 0; i < rowNum - 1; i++) {
			for (int j = 0; j < colNum - 1; j++) {
				source[i][j] = Float.parseFloat(util.getString(i + 1, j + 1));
			}
		}

		// �����ֵ
		for (int i = 0; i < colNum - 1; i++) {
			float[] listfloat = getfloatbycolumn(source, i);
			float sum = 0;
			for (int j = 0; j < listfloat.length; j++)
				sum += listfloat[j];
			mean[i] = sum / listfloat.length;
		}

		// �����׼��
		for (int i = 0; i < colNum - 1; i++) {
			float[] listfloat = getfloatbycolumn(source, i);
			float sum = 0;
			for (int j = 0; j < listfloat.length; j++)
				sum += Math.pow((listfloat[j] - mean[i]), 2);
			dev[i] = (float) Math.sqrt(sum / listfloat.length);
		}

		// ���������(Э�������)sp
		for (int i = 0; i < colNum - 1; i++) {

			for (int j = 0; j <= i; j++) {
				sp[i][j] = 0;
				for (int k = 0; k < rowNum - 1; k++)
					sp[i][j] += (source[k][i] - mean[i]) * (source[k][j] - mean[j]);
				sp[j][i] = sp[i][j];

			}
		}

		System.out.println("Э�������");
		for (int i = 0; i < colNum - 1; i++) {

			for (int j = 0; j < colNum - 1; j++) {
				System.out.print(sp[i][j] + "\t");

			}
			System.out.println();
		}

		// �������ϵ����
		for (int i = 0; i < colNum - 1; i++) {

			for (int j = 0; j <= i; j++) {
				r[i][j] = (float) (sp[i][j] / Math.sqrt(sp[i][i] * sp[j][j]));
				r[j][i] = r[i][j];
			}
		}

		System.out.println("���ϵ������");
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
