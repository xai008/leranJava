package com.calculate;

import java.util.ArrayList;

public class MDao {

	// ������������󡣱���׼Ѱn*n ԭ������
	public static float[][] Mrinv(float[][] dev) {
		int n = dev.length;
		float[][] a = new float[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				a[i][j] = dev[i][j];
			}
		}

		int i, j, row, col, k;
		float max, temp;
		int[] p = new int[n];
		float[][] b = new float[n][n];
		for (i = 0; i < n; i++) {
			p[i] = i;
			b[i][i] = 1;
		}

		for (k = 0; k < n; k++) {
			// ����Ԫ
			max = 0;
			row = col = i;
			for (i = k; i < n; i++) {
				for (j = k; j < n; j++) {
					temp = Math.abs(b[i][j]);
					if (max < temp) {
						max = temp;
						row = i;
						col = j;
					}
				}
			}
			// �������У�����Ԫ������ k �� k ����
			if (row != k) {
				for (j = 0; j < n; j++) {
					temp = a[row][j];
					a[row][j] = a[k][j];
					a[k][j] = temp;
					temp = b[row][j];
					b[row][j] = b[k][j];
					b[k][j] = temp;
				}
				i = p[row];
				p[row] = p[k];
				p[k] = i;
			}
			if (col != k) {
				for (i = 0; i < n; i++) {
					temp = a[i][col];
					a[i][col] = a[i][k];
					a[i][k] = temp;
				}
			}
			// ����
			for (j = k + 1; j < n; j++) {
				a[k][j] /= a[k][k];
			}
			for (j = 0; j < n; j++) {
				b[k][j] /= a[k][k];
			}
			a[k][k] = 1;

			for (j = k + 1; j < n; j++) {
				for (i = 0; i < k; i++) {
					a[i][j] -= a[i][k] * a[k][j];
				}
				for (i = k + 1; i < n; i++) {
					a[i][j] -= a[i][k] * a[k][j];
				}
			}
			for (j = 0; j < n; j++) {
				for (i = 0; i < k; i++) {
					b[i][j] -= a[i][k] * b[k][j];
				}
				for (i = k + 1; i < n; i++) {
					b[i][j] -= a[i][k] * b[k][j];
				}
			}
			for (i = 0; i < k; i++) {
				a[i][k] = 0;
			}
			a[k][k] = 1;
		}
		// �ָ����д���
		for (j = 0; j < n; j++) {
			for (i = 0; i < n; i++) {
				a[p[i]][j] = b[i][j];
			}
		}
		return a;
	}

	/**
	 * ������������� �����任 ���Դ���n*n+1,k=n
	 * 
	 * @param sp
	 * @return �����
	 */
	public static float[][] Mrinv2(float[][] sp) {
		int n = sp.length;
		float[][] a = new float[n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				a[i][j] = sp[i][j];
			}
		}
		float[][] r = new float[n][n];
		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (i == k && j == k) {
						r[i][j] = 1 / a[k][k];
					} else if (i == k && j != k) {
						r[k][j] = a[k][j] / a[k][k];
					} else if (i != k && j == k) {
						r[i][j] = -a[i][k] / a[k][k];
					} else {
						r[i][j] = a[i][j] - a[i][k] * a[k][j] / a[k][k];
					}
				}
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					a[i][j] = r[i][j];
				}
			}
		}
		return a;
	}

	/**
	 * ������� �����任 n*n+1 ,k=n-1
	 * 
	 * @param sp
	 * @return �����(���һ�о���ϵ����)
	 */
	public static float[][] Mrinv3(float[][] sp) {
		int n = sp.length;
		int m = sp[0].length;
		float[][] a = new float[n][m];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				a[i][j] = sp[i][j];
			}
		}
		float[][] r = new float[n][m];
		for (int k = 0; k < n - 1; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					if (i == k && j == k) {
						r[i][j] = 1 / a[k][k];
					} else if (i == k && j != k) {
						r[k][j] = a[k][j] / a[k][k];
					} else if (i != k && j == k) {
						r[i][j] = -a[i][k] / a[k][k];
					} else {
						r[i][j] = a[i][j] - a[i][k] * a[k][j] / a[k][k];
					}
				}
			}
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < m; j++) {
					a[i][j] = r[i][j];
				}
			}
		}
		return a;
	}

///////////////////////////////////////////////////////////////////////////////////////	
	/**
	 * ��ȡ������
	 * @param strarray ����
	 * @param column ����
	 * @return ����������
	 */
	public static float[] getfloatbycolumn(float[][] strarray, int column) {
		int rowlength = strarray.length;
		float[] templist = new float[rowlength];
		for (int i = 0; i < rowlength; i++)
			templist[i] = (strarray[i][column]);
		return templist;
	}

	
	/**
	 * �����ֵ
	 * @param source ���ݾ���
	 * @return ÿ�о�ֵ
	 */
	public static float[] getmean(float[][]source){
		float[]mean=new float[source[0].length];
		for (int i = 0; i < source[0].length; i++) {
			float[] listfloat = getfloatbycolumn(source, i);
			float sum = 0;
			for (int j = 0; j < listfloat.length; j++)
			sum += listfloat[j];
			mean[i] = sum / listfloat.length;
		}
		return mean;
	}
	
	
	/**
	 * �����׼��
	 * @param source ����
	 * @return ÿ�б�׼��
	 */
	public static float[] getdev(float[][]source){
		float[]mean=getmean(source);
		float[]dev=new float[source[0].length];
		for (int i = 0; i < source[0].length; i++) {
			float[] listfloat = getfloatbycolumn(source, i);
			float sum = 0;
			for (int j = 0; j < listfloat.length; j++)
				sum += Math.pow((listfloat[j] - mean[i]), 2);
			dev[i] = (float) Math.sqrt(sum / listfloat.length);
		}
		return dev;
	}
	
	
	/**
	 * Э�������
	 * @param source ����
	 * @return Э�������
	 */
	public static float[][] getsp(float[][]source){
		float []mean =getmean(source);
		float[][]sp=new float[source[0].length][source[0].length];
		for (int i = 0; i < source[0].length; i++) {
			for (int j = 0; j <= i; j++) {
				sp[i][j] = 0;
				for (int k = 0; k < source.length; k++)
					sp[i][j] += (source[k][i] - mean[i]) * (source[k][j] - mean[j]);
				sp[j][i] = sp[i][j];

			}
		}
		return sp;
	}
	
	/**
	 * �������ϵ������
	 * @param source 
	 * @return ���ϵ������
	 */
	public static float[][] getr(float[][]source){
		float[][]r=new float[source[0].length][source[0].length];
		float[][]sp=getsp(source);
		for (int i = 0; i < source[0].length; i++) {
			for (int j = 0; j <= i; j++) {
				r[i][j] = (float) (sp[i][j] / Math.sqrt(sp[i][i] * sp[j][j]));
				r[j][i] = r[i][j];
			}
		}
		return r;
	}
	
	/**�������ϵ��
	 * @param source
	 * @return ���ϵ������,ֻ��x��;
	 */
	public static float[]getr1(float[][]source){
		float[]r1=new float[source[0].length-1];
		float[][]r=getr(source);
		for (int i = 0; i < source[0].length-1; i++) {
			r1[i]=r[i][source[0].length-1];
		}
		return r1;
	}
	
	
	/**
	 * ��ȡ�������������
	 * @param a
	 * @return
	 */
	public static int getMaxIndex(float[] r1) {
		float temp = r1[0];
		int n = 0;
		for (int i = 0; i < r1.length; i++) {
			if (r1[i] > temp) {
				temp = r1[i];
				n = i;
			}
		}
		return n;
	}
	
	/**
	 * ��ȡ�����b��
	 * @param a ����
	 * @param b index
	 * @return �ڼ�������
	 */
	public static float[] getcol(float[][] a, int b) {
		float[] c = new float[a.length];
		for (int i = 0; i < a.length; i++) {
			c[i] = a[i][b];
		}
		return c;
	}
	
	/**
	 * ���������B�е�ֵ
	 * @param a
	 * @param b
	 */
	public static void addVal(float[][] a, float[] b) {
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length-1; j++) {
				a[i][j] =a[i][j]+ b[i];
			}
		}
	}
	
	
	/**
	 * ����ع鷽�����ϵ��R��
	 * @param source
	 * @return
	 */
	public static float getR2(float[][] source) {
		float R2;
		float[][] sp=getsp(source);
		float[]mean=getmean(source);
		float[][] a=MDao.Mrinv3(sp);//Э�������ԭ�����⣬�õ�����Ҫ�Ķ���
		
		int p=a.length-1;
		float[]b=new float[p];
		float b0=mean[mean.length-1];
		for(int i=0;i<p;i++){
			b[i]=a[i][p];
			b0 -=a[i][p]*mean[i];
		}
		float TSS=sp[p][p];
		float ESS=0;
		for(int i=0;i<p;i++){
			ESS += b[i]*sp[i][p]; 
		}
//		float RSS=TSS-ESS;
		R2=ESS/TSS;
		return R2;
	}
	
	public static float[][] makeM(float[][] source,ArrayList<Integer> c) {
		float[][] a=new float[source.length][2];
		for(int i=0;i<source.length;i++){
			a[i][1]=source[i][source[0].length-1];
		}
		for(int in: c){
			for(int i=0;i<source.length;i++){
				a[i][0]=a[i][0]+source[i][in];
			}
		}
		return a;
	}
	
}
