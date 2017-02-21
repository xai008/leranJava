package com.calculate;

public class Multlinear {

	// 确定F值标准
		double F_in = 3.280;
		double F_out = 3.280;
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CsvUtil util = new CsvUtil("./calculation/1.csv");
		int rowNum = util.getRowNum();// 文件行数（包括标头）
		int colNum = util.getColNum();// 文件列数（包括序号列）
		float[][] source = new float[rowNum - 1][colNum - 1];// 文件中读取的数 据矩阵
		float[] mean = new float[colNum - 1];// 每列均值
		float[] dev = new float[colNum - 1];// 每列标准差
		float[][] sp = new float[colNum - 1][colNum - 1];// 离差阵
		float[][] r = new float[colNum - 1][colNum - 1];// 相关系数矩阵
		for (int i = 0; i < rowNum - 1; i++) {
			for (int j = 0; j < colNum - 1; j++) {
				source[i][j] = Float.parseFloat(util.getString(i + 1, j + 1));
			}
		}

		// 计算均值
		for (int i = 0; i < colNum - 1; i++) {
			float[] listfloat = MDao.getfloatbycolumn(source, i);
			float sum = 0;
			for (int j = 0; j < listfloat.length; j++)
				sum += listfloat[j];
			mean[i] = sum / listfloat.length;
		}
		
		// 计算标准差
		for (int i = 0; i < colNum - 1; i++) {
			float[] listfloat = MDao.getfloatbycolumn(source, i);
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
		
		
//		System.out.println("多元线性回归");
//		//协方差矩阵拆成两个
//		int n=sp.length-1;
//		float [][]lx=new float[n][n];
//		float []ly=new float[n];
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++) {
//				lx[i][j]=sp[i][j];
//			}
//			ly[i]=sp[i][n];
//		}
//		float [][]lvx=MDao.Mrinv2(lx);
//		float[]b=new float[n];
//		float b0=mean[colNum - 2];
//		//计算方程系数
//		for(int i=0;i<n;i++){
//			for(int j=0;j<n;j++){
//				b[i]+=lvx[i][j]*ly[j];
//			}
//			
//		}
//		
//		for (int i = 0; i < n; i++) {
//			System.out.println("b"+i+"="+b[i]);
//		}
//		System.out.println("b0="+b0);
		
		int n=rowNum-1;
		
		System.out.println("多元线性回归");
		float[][]a=MDao.Mrinv3(sp);
		int p=a.length-1;
		float[]b=new float[p];
		float b0=mean[colNum - 2];
		for(int i=0;i<p;i++){
			System.out.println("b"+(i+1)+"="+a[i][p]);
			b[i]=a[i][p];
			b0 -=a[i][p]*mean[i];
		}
		System.out.println("b0="+b0);
		
		//方程
		float TSS=sp[p][p];
		System.out.println("TSS="+TSS);
		float ESS=0;
		for(int i=0;i<p;i++){
			ESS += b[i]*sp[i][p]; 
		}
		System.out.println("ESS="+ESS);
		float RSS=TSS-ESS;
		System.out.println("RSS="+RSS);
		float F=(ESS/p)/(RSS/(n-p-1));
		System.out.println("F="+F);
		float R2=ESS/TSS;
		System.out.println("R2="+R2);
		float jR2=1-(1-R2)*(n-1)/(n-p-1);
		System.out.println("jR2="+jR2);
		float SEE=(float) Math.sqrt(RSS/(n-p-1));
		System.out.println("SEE="+SEE);
		
		float[]t=new float[p];
		for(int i=0;i<p;i++){
			t[i]=(float) (b[i]/Math.sqrt(a[i][i]*RSS/(n-p-1)));
			System.out.println("t"+(i+1)+"="+t[i]);
		}
	}
	
}
