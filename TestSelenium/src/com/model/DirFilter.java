package com.model;

import java.io.File;
import java.io.FilenameFilter;

public class DirFilter implements FilenameFilter{
	
	private String type;
	public DirFilter(String tp){
		this.type=tp;
	}
	@Override
	public boolean accept(File fl, String path) {
		File file=new File(path);
		String filename=file.getName();
		return filename.indexOf(type)!=-1;
	}
}
