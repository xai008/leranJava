package com.calculate;

public class Multlinear {

	// ȷ��Fֵ��׼
		double F_in = 3.280;
		double F_out = 3.280;
		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CsvUtil util = new CsvUtil("./calculation/1.csv");
		int rowNum = util.getRowNum();// �ļ�������������ͷ��
		int colNum = util.getColNum();// �ļ���������������У�
		float[][] source = new float[rowNum - 1][colNum - 1];// �ļ��ж�ȡ���� �ݾ���
		float[] mean = new float[colNum - 1];// ÿ�о�ֵ
		float[] dev = new float[colNum - 1];// ÿ�б�׼��
		float[][] sp = new float[colNum - 1][colNum - 1];// �����
		float[][] r = new float[colNum - 1][colNum - 1];// ���ϵ������
		for (int i = 0; i < rowNum - 1; i++) {
			for (int j = 0; j < colNum - 1; j++) {
				source[i][j] = Float.parseFloat(util.getString(i + 1, j + 1));
			}
		}

		// �����ֵ
		for (int i = 0; i < colNum - 1; i++) {
			float[] listfloat = MDao.getfloatbycolumn(source, i);
			float sum = 0;
			for (int j = 0; j < listfloat.length; j++)
				sum += listfloat[j];
			mean[i] = sum / listfloat.length;
		}
		
		// �����׼��
		for (int i = 0; i < colNum - 1; i++) {
			float[] listfloat = MDao.getfloatbycolumn(source, i);
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
		
		
//		System.out.println("��Ԫ���Իع�");
//		//Э�������������
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
//		//���㷽��ϵ��
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
		
		System.out.println("��Ԫ���Իع�");
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
		
		//����
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
