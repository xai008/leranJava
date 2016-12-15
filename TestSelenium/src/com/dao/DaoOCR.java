package com.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.model.DirFilter;

public class DaoOCR {

	private final String LANG_OPTION = "-l";
	private String tessPath = "D:\\Tesseract-OCR";


	public String recognizeText(File imageFile) throws Exception {
		File outputFile = new File(imageFile.getParentFile(), "output");
		StringBuffer strB = new StringBuffer();
		List<String> cmd = new ArrayList<String>();
		cmd.add(tessPath + "\\tesseract");
		cmd.add("");
		cmd.add(outputFile.getName());
		cmd.add(LANG_OPTION);
		cmd.add("eng");
		cmd.add("-psm 3");
		cmd.add("digits");// 限制匹配的时候用
		ProcessBuilder pb = new ProcessBuilder();
		pb.directory(imageFile.getParentFile()); // 设置工作目录
		cmd.set(1, imageFile.getName());
		pb.command(cmd);
		pb.redirectErrorStream(true);
		Process process = pb.start();
		int w = process.waitFor();
		if (w == 0) {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(new FileInputStream(outputFile.getAbsolutePath() + ".txt"), "UTF-8"));

			String str;
			while ((str = in.readLine()) != null) {
				strB.append(str);
			}
			in.close();

		} else {
			String msg;
			switch (w) {
			case 1:
				msg = "Errors accessing files. There may be spaces in your image's filename.";
				break;
			case 3:
				msg = "Errors accessing files. Only the image";
				break;
			case 29:
				msg = "Cannot recognize the image or its selected region.";
				break;
			case 31:
				msg = "Unsupported image format.";
				break;
			default:
				msg = "Errors occurred.";
			}
			throw new RuntimeException(msg);
		}
		new File(outputFile.getAbsolutePath() + ".txt").delete();
		return strB.toString();
	}

	
	/**
	 *解析图片，并按图像名字保存文件
	 * @param imagesFilepath 图片文件夹路径
	 * @return
	 */
	public void recognizeMission(String imagesFilepath) {
		try {
			File testDataDir = new File(imagesFilepath);
			if (!testDataDir.exists()) {
				System.out.println("文件夹不存在");
			}
			File result = new File(testDataDir.getAbsoluteFile(), "result.txt");
			if (result.exists()) {
				result.delete();
				result.createNewFile();
			}
			BufferedWriter writer = new BufferedWriter(new FileWriter(result, true));

			DirFilter filter = new DirFilter(".png");
			System.out.println(testDataDir.listFiles(filter).length);
			int i = 0;
			for (File file : testDataDir.listFiles(filter)) {
				i++;
				String inputfilename = file.getName();
				StringBuffer strB = new StringBuffer();
				strB.append(inputfilename+"￥");
				String recognizeText = recognizeText(file);
				strB.append(recognizeText);
				System.out.print(strB.toString() + "\t");
				if (i % 5 == 0) {
					System.out.println();
				}
				writer.write(strB.toString() + ";\n");
				writer.flush();
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
