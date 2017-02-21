package com.calculate;

public class tet {

	public static void main(String[] args) {
//		 CsvUtil util =new CsvUtil("./calculation/1.csv");
		float[][] a=new float[3][4];
		System.out.println(a[0].length);
		a[0][0]=1752.96f;
		a[0][1]=1085.61f;
		a[0][2]=1200f;
		a[0][3]=3231.48f;
		
		a[1][0]=1085.61f;
		a[1][1]=3155.78f;
		a[1][2]=3364f;
		a[1][3]=2216.44f;
		
		a[2][0]=1200f;
		a[2][1]=3364f;
		a[2][2]=35572;
		a[2][3]=7593;
		
		
		float[][]c=MDao.Mrinv3(a);
		for(int i=0;i<3;i++){
			for(int j=0;j<4;j++){
				System.out.print(c[i][j]+"\t");
			}
			System.out.println();
		}
			
	}
}
