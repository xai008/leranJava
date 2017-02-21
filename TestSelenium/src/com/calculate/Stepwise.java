package com.calculate;

import java.util.ArrayList;

public class Stepwise {

	public static void main(String[] args) throws Exception {
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

		// ȷ��Fֵ��׼
		double F_in = 3.280;
		double F_out = 3.280;

		// ����ƫ�ع�ƽ����
		for (int k = 0; k < colNum - 2; k++) {
			float[] u = new float[colNum - 2];
			for (int i = 0; i < colNum - 2; i++) {
				u[i] = r[colNum - 2][i] * r[colNum - 2][i] / r[i][i];

			}
			// �ж��Ƿ��޳�
			float Q = r[colNum - 2][colNum - 2];// ʣ��ƽ����
			int min_index = getMinIndex(u, choose);
			double pF_out = u[min_index] / (Q / (rowNum - 1 - choose.size() - 1));
			if (!(Math.abs(pF_out) > F_out)) {
				choose.remove(min_index + "");
				r = trans(r, min_index, colNum);
				System.out.println("�˳���"+(min_index+1));
				break;
			}

			for (String in : choose) {
				int ii = Integer.parseInt(in);
				u[ii] = 0;
			}

			int index = getMaxIndex(u);
			double pF = u[index] / ((r[colNum - 2][colNum - 2] - u[index]) / ((rowNum - 1) - choose.size() - 1 - 1));
			if (pF > F_in) {
				choose.add(index + "");
				System.out.println("����:"+(index+1));
				r = trans(r, index, colNum);
			}
		}
		System.out.println();
		
		// �������Żع鷽��
		float[] b = new float[choose.size()];
		int bi = 0;
		for (String in : choose) {
			int ii = Integer.parseInt(in);
			b[bi] = r[ii][colNum - 2] * dev[colNum - 2] / dev[ii];
			bi++;
		}
		float b0 = mean[colNum - 2];
		for (int i = 0; i < b.length; i++) {
			b0 -= b[i] * mean[Integer.parseInt(choose.get(i))];
		}
		String ans = "y=" + b0 + "+";
		for (int i = 0; i < b.length; i++) {
			int iii = Integer.parseInt(choose.get(i)) + 1;
			if (i < b.length - 1)
				ans += b[i] + "x" + iii + "+";
			else
				ans += b[i] + "x" + iii;
		}

		System.out.println(ans);
		// �����ϵ��
		float rr = (float) Math.sqrt(1 - r[colNum - 2][colNum - 2]);
		System.out.println("�����ϵ��: " + rr);
		// �ع鷽�̹��Ʊ�׼���
		float ssy = 0;
		for (int i = 0; i < rowNum - 1; i++)
			ssy += (source[i][colNum - 2] - mean[colNum - 2]) * (source[i][colNum - 2] - mean[colNum - 2]);
		float d = (float) Math.sqrt(r[colNum - 2][colNum - 2] * ssy / rowNum - 1 - choose.size() - 1);
		System.out.println("�ع鷽�̹��Ʊ�׼���: " + d);

	}

	//��ȡ������
	public static float[] getfloatbycolumn(float[][] strarray, int column) {
		int rowlength = strarray.length;
//		int columnlength = strarray[0].length;
		float[] templist = new float[rowlength];
		for (int i = 0; i < rowlength; i++)
			templist[i] = (strarray[i][column]);
		return templist;
	}

	public static int getMaxIndex(float[] pu) {
		int index = 0;
		float max = 0;
		for (int i = 0; i < pu.length; i++) {

			if (Math.abs(pu[i]) >= max) {
				index = i;
				max = Math.abs(pu[i]);
			}
		}

		return index;

	}

	public static int getMinIndex(float[] pu, ArrayList<String> pchoose) {
		int index = 0;
		float min = 1;
		for (String in : pchoose) {
			int ii = Integer.parseInt(in);
			if (Math.abs(pu[ii]) <= min) {
				index = ii;
				min = Math.abs(pu[ii]);
			}
		}

		return index;

	}

	// ��ȥ�任
	public static float[][] trans(float[][] pr, int pindex, int pcol) {
		float[][] r2 = new float[pcol - 1][pcol - 1];
		for (int i = 0; i < pcol - 1; i++)
			for (int j = 0; j < pcol - 1; j++) {
				if (i == pindex && j == pindex)
					r2[i][j] = 1 / pr[i][j];
				else if (i == pindex && j != pindex)
					r2[i][j] = pr[i][j] / pr[i][i];
				else if (i != pindex && j == pindex)
					r2[i][j] = -pr[i][j] / pr[j][j];
				else {
					r2[i][j] = pr[i][j] - pr[i][pindex] * pr[pindex][j] / pr[pindex][pindex];
				}
			}
		return r2;
	}
}
