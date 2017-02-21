package com.calculate;

import java.util.ArrayList;

public class MDao {

	// 单纯的求拟矩阵。必须准寻n*n 原地求逆
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
			// 找主元
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
			// 交换行列，将主元调整到 k 行 k 列上
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
			// 处理
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
		// 恢复行列次序；
		for (j = 0; j < n; j++) {
			for (i = 0; i < n; i++) {
				a[p[i]][j] = b[i][j];
			}
		}
		return a;
	}

	/**
	 * 单纯的求拟矩阵 消除变换 可以处理n*n+1,k=n
	 * 
	 * @param sp
	 * @return 逆矩阵
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
	 * 求拟矩阵 消除变换 n*n+1 ,k=n-1
	 * 
	 * @param sp
	 * @return 逆矩阵(最后一列就是系数了)
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
	 * 获取列向量
	 * @param strarray 矩阵
	 * @param column 列数
	 * @return 列向量数组
	 */
	public static float[] getfloatbycolumn(float[][] strarray, int column) {
		int rowlength = strarray.length;
		float[] templist = new float[rowlength];
		for (int i = 0; i < rowlength; i++)
			templist[i] = (strarray[i][column]);
		return templist;
	}

	
	/**
	 * 计算均值
	 * @param source 数据矩阵
	 * @return 每列均值
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
	 * 计算标准差
	 * @param source 矩阵
	 * @return 每列标准差
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
	 * 协方差矩阵
	 * @param source 矩阵
	 * @return 协方差矩阵
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
	 * 计算相关系数矩阵
	 * @param source 
	 * @return 相关系数矩阵
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
	
	/**计算相关系数
	 * @param source
	 * @return 相关系数数组,只有x的;
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
	 * 获取数组中最大的序号
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
	 * 获取矩阵第b列
	 * @param a 矩阵
	 * @param b index
	 * @return 第几列数组
	 */
	public static float[] getcol(float[][] a, int b) {
		float[] c = new float[a.length];
		for (int i = 0; i < a.length; i++) {
			c[i] = a[i][b];
		}
		return c;
	}
	
	/**
	 * 矩阵集体加上B列的值
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
	 * 计算回归方程相关系数R方
	 * @param source
	 * @return
	 */
	public static float getR2(float[][] source) {
		float R2;
		float[][] sp=getsp(source);
		float[]mean=getmean(source);
		float[][] a=MDao.Mrinv3(sp);//协方差矩阵原地求拟，得到所有要的东西
		
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
