package com.calculate;

import java.util.ArrayList;

public class CBIndex {
	// ȷ��Fֵ��׼
	static float F_in = 4.0f;
	static float F_out = 4.4f;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CsvUtil util = new CsvUtil("./calculation/1.csv");
		int rowNum = util.getRowNum();// �ļ�������������ͷ��
		int colNum = util.getColNum();// �ļ���������������У����һ����Y��
		float[][] source = new float[rowNum - 1][colNum - 1];// �ļ��ж�ȡ���� �ݾ���
		for (int i = 0; i < rowNum - 1; i++) {
			for (int j = 0; j < colNum - 1; j++) {
				source[i][j] = Float.parseFloat(util.getString(i + 1, j + 1));
			}
		}

		// �����ֵ
//		float[] mean=MDao.getmean(source);// ÿ�о�ֵ
		// �����׼��
//		float[] dev=MDao.getdev(source);// ÿ�б�׼��
		// ���������(Э�������)sp
//		float[][] sp=MDao.getsp(source);// �����
		// �������ϵ����
		float[][] r=MDao.getr(source);// ���ϵ������

//		System.out.println("���ϵ������");
//		Output(r);
//		System.out.println();

		float [] r1=MDao.getr1(source);
//		Output2(r1);
		////////////////////////////////////////////////////////////////////
		float[][] target = new float[rowNum - 1][colNum - 1];
		for (int i = 0; i < rowNum - 1; i++) {
			for (int j = 0; j < colNum - 1; j++) {
				target[i][j] = source[i][j];
			}
		}
		
		for (int i = 0; i < rowNum - 1; i++) {
			for (int j = 0; j < colNum - 2; j++) {
				source[i][j] = source[i][j] * r[colNum - 2][j];
				target[i][j] = source[i][j];
			}
		}
//		Output(source);
//		Output(target);
		int in=MDao.getMaxIndex(r1);
//		System.out.println(in);
		ArrayList<Integer>choose=new ArrayList<Integer>();
		float w1=r1[in];
		System.out.println("w1="+w1);
		choose.add(in);
		
		float[][]m1=MDao.makeM(source, choose);
//		Output(m1);
		
		float ft1=MDao.getR2(m1);  //F����
		System.out.println("ft1="+ft1);
		
		float []w=MDao.getcol(source, in);
		MDao.addVal(target, w);
//		Output(target);
		r1=MDao.getr1(target);
//		Output2(r1);
		in=MDao.getMaxIndex(r1);
		float w2=r1[in];
		
		//����һ����F����
		choose.add(in);
		float[][]m2=MDao.makeM(source, choose);
//		Output(m2);
		float ft2=MDao.getR2(m2);
		System.out.println("ft2="+ft2);
		float f=(ft2-ft1)/((1-ft1)/(source.length-2));
		
		//�ж��м���ƫF���顣
		while(w2>w1 && f>F_in){
			System.out.println("w2="+w2);
			w1=w2;
			ft1=ft2;          //F����
//			choose.add(in);   //����ҪF�������ע�ͣ����粻������⿪
			w=MDao.getcol(source, in);
			MDao.addVal(target, w);
			r1=MDao.getr1(target);
			in=MDao.getMaxIndex(r1);
			w2=r1[in];
			
			//F����
			choose.add(in);
			m2=MDao.makeM(source, choose);
//			Output(m2);
			ft2=MDao.getR2(m2);
			System.out.println("ft2="+ft2);
			f=(ft2-ft1)/((1-ft1)/(source.length-2));
		}
		choose.remove(choose.size()-1);//F�����Ҫ
		System.out.println("�������ϵ��="+ft1);
		System.out.println("û�д��ڵ���");
		
		

	}	
/////////////////////////////////////////////////////////////////////////////////
	public static void Output(float[][] a) {
		System.out.println();
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				System.out.print(a[i][j] + "\t");
			}
			System.out.println();
		}
	}

	public static void Output2(float[] a) {
		System.out.println();
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + "\t");
		}
		System.out.println();
	}

}
