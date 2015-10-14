package com.enercon.global.utility;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class FilePathUtility {
	private static FilePathUtility filePathUtility = new FilePathUtility();
	
	private FilePathUtility(){
		
	}
	
	public static FilePathUtility getInstance(){
		return filePathUtility;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(new FilePathUtility().getPath("spring\\bean-config.xml"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getPath(String string) throws UnsupportedEncodingException {
		String path = this.getClass().getClassLoader().getResource("/").getPath();
		String fullPath = URLDecoder.decode(path, "UTF-8");
		//System.out.println("this.getClass().getClassLoader().getResource(\"/\").getPath() <-> " + fullPath);
		String pathArr[] = fullPath.split("/WEB-INF/classes/");

		fullPath = pathArr[0];
		String reponsePath = "";
		reponsePath = new File(fullPath).getPath() + File.separatorChar + string;
		//System.out.println("Repsonse path:" + reponsePath);
		return reponsePath;
	}

}
