package com.enercon.global.utility;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class FilePathUtility {
	private final static Logger logger = Logger.getLogger(FilePathUtility.class);
	private static FilePathUtility filePathUtility = new FilePathUtility();
	
	private FilePathUtility(){
		
	}
	
	public static FilePathUtility getInstance(){
		return filePathUtility;
	}
	
	public static void main(String[] args) {
		for(String file: getInstance().getFilesName("C:\\Users\\91014863\\Desktop\\Naval\\logger\\model\\Modified")){
			System.out.println(file);
		}
		
		/*try {
			System.out.println(new FilePathUtility().getPath("spring\\bean-config.xml"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
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

	public List<String> getFilesName(String directoryPath){ 
		File folder = new File(directoryPath);
		List<String> fileNames = new ArrayList<String>();
		File[] listOfFiles = folder.listFiles();

	    for (int i = 0; i < listOfFiles.length; i++) {
	    	if (listOfFiles[i].isFile()) {
	    	  
	        System.out.println("File " + listOfFiles[i].getName());
	        fileNames.add(listOfFiles[i].getName());
	        
	    	} else if (listOfFiles[i].isDirectory()) {
	    		System.out.println("Directory " + listOfFiles[i].getName());
	    	}
	    }

	   return fileNames;
	}

}
