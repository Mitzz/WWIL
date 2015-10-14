
<%@ page import = "java.io.*" %>
<%@ page import="com.enercon.admin.dao.AdminDao"%>
<%
 
  //if ( request.getParameter("imgID") != null )
  //{
  
  //  String iNumPhoto = request.getParameter("imgID").toString() ;  
   // String dep = request.getParameter("dep").toString() ;   
    try
    { 
       AdminDao ado = new AdminDao(); 
       // get the image from the database
       byte[] imgData = ado.imageDetailsDisplay();       
       response.setHeader("expires", "0");
       response.setContentType("image/jpg");

       out.clear();
       OutputStream os = response.getOutputStream();
       os.write(imgData);
       out.flush(); 
    }
    catch (Exception e)
    {
      e.printStackTrace();
      throw e;
    }
    finally
    {
		out.close();		
    } 
  
%>