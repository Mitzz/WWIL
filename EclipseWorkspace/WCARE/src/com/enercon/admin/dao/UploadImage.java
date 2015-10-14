package com.enercon.admin.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.enercon.global.utils.JDBCUtils;

public class UploadImage extends HttpServlet {
    @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    //    PrintWriter out = response.getWriter();
        
        SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
        try {
            // Apache Commons-Fileupload library classes
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload sfu  = new ServletFileUpload(factory);
            JDBCUtils conmanager = new JDBCUtils();
    	    Connection conn = conmanager.getConnection();

            if (! ServletFileUpload.isMultipartContent(request)) {
              //  System.out.println("sorry. No file uploaded");
                return;
            }

            // parse request
            List items = sfu.parseRequest(request);
            FileItem  desc = (FileItem) items.get(0);
            String imageDesc =  desc.getString();
            
            FileItem fDate = (FileItem) items.get(1);
            //String fDate = format.parse(items.get(1));
            String   fromDate1 =  fDate.getString();
            java.util.Date fromDate2 = format.parse(fromDate1);
            java.sql.Date fromDate  =   new java.sql.Date(fromDate2.getTime());
            
            
            FileItem tDate = (FileItem) items.get(2);
            String   toDate1 =  tDate.getString();
            java.util.Date toDate2 = format.parse(toDate1);
            java.sql.Date toDate  =   new java.sql.Date(toDate2.getTime());

            // get uploaded file
            FileItem file = (FileItem) items.get(3);
                        
            // Connect to Oracle
            
            if (file.getName().equals(""))
            {
            	HttpSession session = request.getSession(true);
	            session.setAttribute("msg", "<font class='errormsgtext'>No File selected!</font>");
	            
            } 
            else
            {
	            int i = file.getName().lastIndexOf(".");
	            String extension = file.getName().substring(i+1).toUpperCase();
	            String filetype = file.getName();
	           
	            if (extension.equals("JPG") && (int)file.getSize()<=1000000)
	            {

	            	PreparedStatement ps = conn.prepareStatement("update TBL_IMAGE set s_image_id = '1',  s_descr = ?,  d_from_date = ?,  d_to_date = ?,  s_created_by = 'Admin',  d_created_date = SYSDATE,  s_last_modified_by = 'Admin',  d_last_modified_date = SYSDATE,  s_title = ?");
	            	ps.setString(1, imageDesc);
	            	ps.setDate(2, fromDate);
	            	ps.setDate(3, toDate);
	            	// size must be converted to int otherwise it results in error
	            	ps.setBinaryStream(4, file.getInputStream(), (int) file.getSize());
	            	ps.executeUpdate();
	            	conn.commit();
	            	conn.close();
	            	//out.println("Proto Added Successfully. <p> <a href='listphotos'>List Photos </a>");
	            	HttpSession session = request.getSession(true);
		            session.setAttribute("msg", "<font class='sucessmsgtext'>File Added Successfully!</font>");
		           
		            	forward("/Admin/UploadImage.jsp", request, response);
		            }
	            else{
	            	if (!extension.equals("JPG")){
	            		HttpSession session = request.getSession(true);
	            		session.setAttribute("msg", "<font class='sucessmsgtext'>"+extension+" File Cannot Uploaded!</font>");
	            		forward("/Admin/UploadImage.jsp", request, response);
	            	}
	            	else{
	            		HttpSession session = request.getSession(true);
	            		session.setAttribute("msg", "<font class='sucessmsgtext'>File size is too large, cannot uploaded!</font>");
	            		forward("/Admin/UploadImage.jsp", request, response);
	            	}
	            }
            	} 
            }
            catch(Exception ex) {
	        	try {
		        	HttpSession session = request.getSession(false);
		            session.setAttribute("msg", ex.getMessage());
		            forward("/Admin/UploadImage.jsp", request, response);
	        	}
	        	catch (Exception e) {
		            //logger.equals("ENERCON: SecurityServlet: doPost: Exception: " + e.toString());
		            e.printStackTrace();
		        }
	        }
     	} 
    	public void forward(String sPath, HttpServletRequest request, HttpServletResponse response) throws Exception {
    		ServletConfig config = getServletConfig();
    		ServletContext context = config.getServletContext();
		
    		context.getRequestDispatcher(sPath).include(request, response);
		
    	}
}

