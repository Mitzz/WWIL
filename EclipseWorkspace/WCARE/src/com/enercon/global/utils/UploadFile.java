package com.enercon.global.utils;

import com.enercon.security.controller.*;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Iterator;
import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.*;
public class UploadFile extends HttpServlet
{
	private final static Logger logger = Logger.getLogger(UploadFile.class);
	private static final long serialVersionUID = 1L;
	private ServletContext application;
	public void upload(HttpServletRequest request, 
            HttpServletResponse response) throws IOException
	{
	try{
		//System.out.println("Content Type ="+request.getContentType());

        DiskFileUpload fu = new DiskFileUpload();
        // If file size exceeds, a FileUploadException will be thrown
        fu.setSizeMax(1000000);
        String msg="";
        List fileItems = fu.parseRequest(request);
        Iterator itr = fileItems.iterator();
        //application = getServletConfig().getServletContext();
        while(itr.hasNext()) {
          FileItem fi = (FileItem)itr.next();

          //Check if not form field so as to only handle the file inputs
          //else condition handles the submit button input
          if(!fi.isFormField()) {
        	  	SecurityServlet ss = new SecurityServlet();
        	  	//System.out.println("NAME: "+fi.getName());
        	  	//System.out.println("SIZE: "+fi.getSize());
  				//System.out.println("PATH: "+application.getRealPath("/"));
  				//System.out.println(fi.getOutputStream().toString());
  				File tempFileRef  = new File(fi.getName());
  				String name = tempFileRef.getName();
  				//System.out.println("File name:"+ name);
  				if (name.endsWith("xls")) {
  					File fNew = new File(application.getRealPath("/")+"Admin/upload/",tempFileRef.getName());
  					//System.out.println(fNew.getAbsolutePath());
  					fi.write(fNew);	
  					msg = "Upload Successful!";
  				}
  				else{
  					msg = "Invalid File! Please select excel file";
  				}
  				request.setAttribute("msg", msg);
                ss.include("/Admin/MonthSalaryUpload.jsp", request, response);
          }
          else {
            System.out.println("Field ="+fi.getFieldName());
          }
        }
	}catch(Exception ex)
	{
		logger.error("\nClass: " + ex.getClass() + "\nMessage: " + ex.getMessage() + "\n", ex);
	}

	}

}