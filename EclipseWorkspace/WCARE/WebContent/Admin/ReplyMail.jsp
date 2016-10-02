<%@ page import="java.io.*,java.sql.*,com.enercon.global.utils.JDBCUtils,com.enercon.global.utils.CodeGenerate,java.sql.CallableStatement,java.sql.PreparedStatement,java.sql.ResultSet,java.sql.SQLException" %>


<%@ page import="com.enercon.customer.util.CustomerUtil" %>

<%@ page import="com.enercon.global.utils.*"%>
<%@page import="com.enercon.connection.WcareConnector"%>
<%@page import="com.enercon.dao.DaoUtility" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Arrays" %>
<html>
<%  
    /*  JDBCUtils conmanager = new JDBCUtils();  */
     Connection conn = null;
    conn = WcareConnector.wcareConnector.getConnectionFromPool();
	String contentType = request.getContentType();
	String Remarks="Message Not Sent Due To some Problem";
	PreparedStatement psUpdate=null;	
	boolean flag=false;  
	String Status =request.getParameter("Status");
	String msgHead = request.getParameter("MsgHeadtxt");
	String msgid = request.getParameter("MsgIdtxt");
	String msgdesc=request.getParameter("MsgDescriptiontxt").trim();
	String msgsignature=request.getParameter("MsgSigntxt").trim();
	String userid = session.getAttribute("loginID").toString();
	
	String msgEmail = request.getParameter("msgemail");
try{
	CustomerUtil cd=new CustomerUtil(); 
	String fromemail = "manoj.tiwari@windworldindia.com"; 
	
    SendMail sm =new SendMail();
	//String toemails="Hardik.Dixit@windworldindia.com";
	//String ccmails="Manoj.Tiwari@windworldindia.com";
	
	
	
	String toemails=msgEmail;
	//String ccmails="abhishek.thakur@windworldindia.com";
	String subject=msgHead;
	String Message="<table border='0'><tr><td colspan='3'><b><i>Dear Customer,</i></b></td><tr><td colspan='3'><b><i>"+msgdesc+"</i></b></td><tr><td colspan='3'><br><br><b>Sender's Signature:</b><i>"+msgsignature+"<br><br><b>From :-Customer Care</b><br>ENERCON (INDIA) LTD<i></td></tr></table>";
    flag=sm.sendMail(toemails,fromemail,subject,Message);	
    if(flag)
    {
    	Remarks="Message Sent Sucessfully";
    }
			
    
   // String msgid = CodeGenerate.NewCodeGenerate("TBL_CUSTOMER_QUERY");
    psUpdate=conn.prepareStatement("UPDATE TBL_CUSTOMER_QUERY SET S_MESSAGE_REPLYDESC=?,S_REPLIED_BY=?,S_STATUS=?,D_REPLIED_DATE=sysdate WHERE S_CUSTOMER_QUERY_ID=?");
    psUpdate.setObject(1,msgdesc);
	psUpdate.setObject(2,userid);
	psUpdate.setObject(3,Status);
	psUpdate.setObject(4,msgid);
	
	psUpdate.executeUpdate();
    psUpdate.close();
   // conn.close();
	//conn = null;
	}finally{
		DaoUtility.releaseResources(psUpdate, conn);
	}
	/* conmanager.closeConnection();
	conmanager = null; */
    
    
    %>   
		   
		<script type="text/javascript">
		alert("Sent Successfully");
		self.close(); 
		</script>
	
</html>


