<%@ page import="java.io.*,java.sql.*,com.enercon.global.utils.JDBCUtils,com.enercon.global.utils.CodeGenerate,java.sql.CallableStatement,java.sql.PreparedStatement,java.sql.ResultSet,java.sql.SQLException" %>


<head>

</head>
<%@page import="com.enercon.admin.dao.AdminDao"%>
<html>
<%  
    JDBCUtils conmanager = new JDBCUtils();
    Connection conn = conmanager.getConnection();
	String contentType = request.getContentType();
	
	ResultSet rs = null;
	
		
		
		Statement pst=null;
		PreparedStatement ps=null;
		PreparedStatement psUpdate=null;
		PreparedStatement prepStmt=null;
		AdminDao ad=new AdminDao();
        String line = null;
	    String value=null;
        try{
			
			String sqlquery="";
			String cid="";
			String Remarks="";
			String pid="";
			int lcnt=0;
			String rcdLine="";
			String uptype = request.getParameter("SelTypetxt");
			String projdesc=request.getParameter("Projecttxt").trim();
			String ccode=request.getParameter("custcodetxt").trim();
			
			 //System.out.println("uptype:"+uptype);
			 
			 
//			.......................Upload Project Master............................//
			if(uptype.equals("ProjectMaster"))
			{		
			    sqlquery="select S_CUSTOMER_ID from tbl_customer_master  where S_SAP_CUSTOMER_CODE=?";
            	prepStmt = conn.prepareStatement(sqlquery);
            	prepStmt.setObject(1,ccode);
    			rs = prepStmt.executeQuery();
    			if (rs.next())
    			{
    				cid=rs.getObject("S_CUSTOMER_ID").toString();			 				
    			}
    			
    			prepStmt.close();
    			rs.close();
    			if(!cid.equals(""))
    			{
    			projdesc=projdesc.toUpperCase();
    		    sqlquery="select s_project_id from tbl_project_master  where S_PROJECT_DEFINITION=?  and S_CUSTOMER_ID=?";
    			
        		prepStmt = conn.prepareStatement(sqlquery);
        		prepStmt.setObject(1,projdesc);
        		prepStmt.setObject(2,cid);
    			rs = prepStmt.executeQuery();
    			if (rs.next())
    			{
    				pid=rs.getObject("s_project_id").toString();			 				
    			
    			
    			
    			 
   			//Delete Project activity Master//
    			 ps=conn.prepareStatement("delete from  TBL_PROJECT_ACTIVITY where S_PROJECT_ID=?");
    			 ps.setObject(1,pid);
    			 ps.executeQuery();
    			 ps.close();
    			 
    		//	Delete Project activity Transaction Master//		 
    			
    			 ps=conn.prepareStatement("delete from  TBL_ACTIVITY_TRANSACTION where S_ACTIVITY_ID in(select S_ACTIVITY_ID from TBL_Project_activity where s_project_id=?)");
				 ps.setObject(1,pid);
    			 ps.executeQuery();
    			 ps.close();
    			 
    			// Delete Project Material Transaction Master//
    			 ps=conn.prepareStatement("Delete from TBL_MATERIAL_TRANSACTION where S_PROJECT_ID=?");
    			 ps.setObject(1,pid);
    			 ps.executeQuery();
    			 ps.close();
    			 
   		//	Delete Project  Master//
     			
    			 ps=conn.prepareStatement("delete from  TBL_PROJECT_MASTER where S_PROJECT_ID=?");
    			 ps.setObject(1,pid);
    			 ps.executeQuery();
    			 ps.close();
    			 Remarks="Deleted Successfuly";
    			}else {Remarks="Project Does Not Exist";}
    			prepStmt.close();
    			rs.close();
    			}else {Remarks="Customer Does Not Exist";}
   				
			}
			
			
			
			
			
			
			
			//.......................Upload Activity Master............................//
			
			if(uptype.equals("ActivityMaster"))
			{				
				    sqlquery="select S_CUSTOMER_ID from tbl_customer_master  where S_SAP_CUSTOMER_CODE=?";
	            	prepStmt = conn.prepareStatement(sqlquery);
	            	prepStmt.setObject(1,ccode);
	    			rs = prepStmt.executeQuery();
	    			if (rs.next())
	    			{
	    				cid=rs.getObject("S_CUSTOMER_ID").toString();			 				
	    			}
	    			
	    			prepStmt.close();
	    			rs.close();
	    			if(!cid.equals(""))
	    			{
	    			projdesc=projdesc.toUpperCase();
	    		    sqlquery="select s_project_id from tbl_project_master  where S_PROJECT_DEFINITION=?  and S_CUSTOMER_ID=?";
	    			
	        		prepStmt = conn.prepareStatement(sqlquery);
	        		prepStmt.setObject(1,projdesc);
	        		prepStmt.setObject(2,cid);
	    			rs = prepStmt.executeQuery();
	    			if (rs.next())
	    			{
	    				pid=rs.getObject("s_project_id").toString();			 				
	    			
//	    				Delete Project activity Transaction Master//		 
	        			
	       			 ps=conn.prepareStatement("delete from  TBL_ACTIVITY_TRANSACTION where S_ACTIVITY_ID in(select S_ACTIVITY_ID from TBL_Project_activity where s_project_id=?)");
	   				 ps.setObject(1,pid);
	       			 ps.executeQuery();
	       			 ps.close();
	    			
	    			 
	   			//Delete Project activity Master//
	    			 ps=conn.prepareStatement("delete from  TBL_PROJECT_ACTIVITY where S_PROJECT_ID=?");
	    			 ps.setObject(1,pid);
	    			 ps.executeQuery();
	    			 ps.close();
	    			 
	    			 Remarks="Deleted Successfuly";
	    			}else {Remarks="Project Does Not Exist";}
	    			prepStmt.close();
	    			rs.close();
	    			}else {Remarks="Customer Does Not Exist";}
	   					
			      }
			           
					
					
			
			////////////////Upload Project Activity Transaction////////////////
			
			if(uptype.equals("ActivityTrans"))
			{				
				sqlquery="select S_CUSTOMER_ID from tbl_customer_master  where S_SAP_CUSTOMER_CODE=?";
            	prepStmt = conn.prepareStatement(sqlquery);
            	prepStmt.setObject(1,ccode);
    			rs = prepStmt.executeQuery();
    			if (rs.next())
    			{
    				cid=rs.getObject("S_CUSTOMER_ID").toString();			 				
    			}
    			
    			prepStmt.close();
    			rs.close();
    			if(!cid.equals(""))
    			{
    			projdesc=projdesc.toUpperCase();
    		    sqlquery="select s_project_id from tbl_project_master  where S_PROJECT_DEFINITION=?  and S_CUSTOMER_ID=?";
    			
        		prepStmt = conn.prepareStatement(sqlquery);
        		prepStmt.setObject(1,projdesc);
        		prepStmt.setObject(2,cid);
    			rs = prepStmt.executeQuery();
    			if (rs.next())
    			{
    				pid=rs.getObject("s_project_id").toString();			 				
    			
    			
    			 
   			
    			 
    		//	Delete Project activity Transaction Master//		 
    			
    			 ps=conn.prepareStatement("delete from  TBL_ACTIVITY_TRANSACTION where S_ACTIVITY_ID in(select S_ACTIVITY_ID from TBL_Project_activity where s_project_id=?)");
				 ps.setObject(1,pid);
    			 ps.executeQuery();
    			 ps.close();
    			 
    			 Remarks="Deleted Successfuly";
    			}else {Remarks="Project Does Not Exist";}
    			prepStmt.close();
    			rs.close();
    			}else {Remarks="Customer Does Not Exist";}
   				
    			 
					
					
			}
					
			
			
			
			
			
			
			
			/////////////////Upload Material Transaction////////////////////////
			
			if(uptype.equals("MaterialTrans"))
			{				
				sqlquery="select S_CUSTOMER_ID from tbl_customer_master  where S_SAP_CUSTOMER_CODE=?";
            	prepStmt = conn.prepareStatement(sqlquery);
            	prepStmt.setObject(1,ccode);
    			rs = prepStmt.executeQuery();
    			if (rs.next())
    			{
    				cid=rs.getObject("S_CUSTOMER_ID").toString();			 				
    			}
    			
    			prepStmt.close();
    			rs.close();
    			if(!cid.equals(""))
    			{
    			projdesc=projdesc.toUpperCase();
    		    sqlquery="select s_project_id from tbl_project_master  where S_PROJECT_DEFINITION=?  and S_CUSTOMER_ID=?";
    			
        		prepStmt = conn.prepareStatement(sqlquery);
        		prepStmt.setObject(1,projdesc);
        		prepStmt.setObject(2,cid);
    			rs = prepStmt.executeQuery();
    			if (rs.next())
    			{
    				pid=rs.getObject("s_project_id").toString();			 				
    			
    			
    			 
   			
    	
    			 
    			// Delete Project Material Transaction Master//
    			 ps=conn.prepareStatement("Delete from TBL_MATERIAL_TRANSACTION where S_PROJECT_ID=?");
    			 ps.setObject(1,pid);
    			 ps.executeQuery();
    			 ps.close();
    			 Remarks="Deleted Successfuly";
    			}else {Remarks="Project Does Not Exist";}
    			prepStmt.close();
    			rs.close();
    			}else {Remarks="Customer Does Not Exist";}
   				
			
			}
					
			
		    
		   %>   
		   
		  <jsp:forward page="DeleteProject.jsp"><jsp:param name="msg"  value="<%=Remarks%>"  />
 </jsp:forward>
		   <% 
		   
           }
        catch (SQLException sqlExp) {
            sqlExp.printStackTrace();
            Exception exp = new Exception("EXECUTE_QUERY_ERROR", sqlExp);
            throw exp;
        } finally {
            try {
                if (pst != null)
                    pst.close();
                if (ps != null)
                    ps.close();
                if (rs != null)
                    rs.close();
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            } catch (Exception e) {
                prepStmt = null;
                ps = null;
                rs = null;
                if (conn != null) {
                	conn.close();
                	conn = null;
                	
                    conmanager.closeConnection();conmanager = null;
                }
            }
        }
    	

	%>
</html>


