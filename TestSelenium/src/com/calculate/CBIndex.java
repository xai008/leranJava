package com.calculate;

import java.util.ArrayList;

public class CBIndex {
	// 确定F值标准
	static float F_in = 4.0f;
	static float F_out = 4.4f;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CsvUtil util = new CsvUtil("./calculation/1.csv");
		int rowNum = util.getRowNum();// 文件行数（包括标头）
		int colNum = util.getColNum();// 文件列数（包括序号列，最后一列是Y）
		float[][] source = new float[rowNum - 1][colNum - 1];// 文件中读取的数 据矩阵
		for (int i = 0; i < rowNum - 1; i++) {
			for (int j = 0; j < colNum - 1; j++) {
				source[i][j] = Float.parseFloat(util.getString(i + 1, j + 1));
			}
		}

		// 计算均值
//		float[] mean=MDao.getmean(source);// 每列均值
		// 计算标准差
//		float[] dev=MDao.getdev(source);// 每列标准差
		// 计算离差阵(协方差矩阵)sp
//		float[][] sp=MDao.getsp(source);// 离差阵
		// 计算相关系数阵
		float[][] r=MDao.getr(source);// 相关系数矩阵

//		System.out.println("相关系数矩阵");
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
		
		float ft1=MDao.getR2(m1);  //F检验
		System.out.println("ft1="+ft1);
		
		float []w=MDao.getcol(source, in);
		MDao.addVal(target, w);
//		Output(target);
		r1=MDao.getr1(target);
//		Output2(r1);
		in=MDao.getMaxIndex(r1);
		float w2=r1[in];
		
		//下面一段是F检验
		choose.add(in);
		float[][]m2=MDao.makeM(source, choose);
//		Output(m2);
		float ft2=MDao.getR2(m2);
		System.out.println("ft2="+ft2);
		float f=(ft2-ft1)/((1-ft1)/(source.length-2));
		
		//判断中加入偏F检验。
		while(w2>w1 && f>F_in){
			System.out.println("w2="+w2);
			w1=w2;
			ft1=ft2;          //F检验
//			choose.add(in);   //假如要F检验这个注释，假如不用这个解开
			w=MDao.getcol(source, in);
			MDao.addVal(target, w);
			r1=MDao.getr1(target);
			in=MDao.getMaxIndex(r1);
			w2=r1[in];
			
			//F检验
			choose.add(in);
			m2=MDao.makeM(source, choose);
//			Output(m2);
			ft2=MDao.getR2(m2);
			System.out.println("ft2="+ft2);
			f=(ft2-ft1)/((1-ft1)/(source.length-2));
		}
		choose.remove(choose.size()-1);//F检验才要
		System.out.println("最后的拟合系数="+ft1);
		System.out.println("没有大于的了");
		
		

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
