<%@ page import="java.sql.*,com.enercon.global.utils.JDBCUtils,com.enercon.global.utils.CodeGenerate,java.sql.PreparedStatement" %>


<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@ page import="com.enercon.global.utils.*"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.enercon.admin.util.JSPErrorLogger"%>
<%! private final static Logger logger = Logger.getLogger(JSPErrorLogger.class); %>
<%@page import="com.enercon.connection.WcareConnector"%>
<%@page import="com.enercon.dao.DaoUtility" %>
<html>
<%  
    /* JDBCUtils conmanager = new JDBCUtils(); */
    
     Connection conn = null;
    conn = WcareConnector.wcareConnector.getConnectionFromPool();
	// String contentType = request.getContentType();
	String Remarks="Message Not Sent Due To some Problem";
	 ResultSet rs = null;
	PreparedStatement psUpdate=null;
	boolean flag=false;  
	String msgHead = request.getParameter("MsgHeadtxt").trim();
	String msgdesc= request.getParameter("MsgDescriptiontxt").trim();
	String msgsignature=request.getParameter("MsgSigntxt").trim();
	String userid = session.getAttribute("loginID").toString();
	try{
	CustomerUtil cd=new CustomerUtil();   
	String fromemail = cd.getCustemail(userid);
	String comma[] = fromemail.split(",");
    SendMail sm =new SendMail();
	String toemails="manoj.tiwari@windworldindia.com";
	String toemails1="crm@windworldindia.com";
	//dinesh.sharma
	String ccmails="manjit.bhagria@windworldindia.com";
	// String toemails="vivek.chaudhary@windworldindia.com";
	// String ccmails="vivek.chaudhary@windworldindia.com";
	String subject=msgHead;
	String Message="<table border='1'><tr><td colspan='3'><b><i>"+msgdesc+"</i></b></td><tr><td colspan='3'><b>Sender's Signature:</b><i>"+msgsignature+"<br><b>Sender's Name</b>"+comma[1]+"<i></td></tr></table>";
	flag=sm.sendMaill(toemails1,comma[0],subject,Message,"manoj.tiwari@windworldindia.com");
    flag=sm.sendMaill(toemails,comma[0],subject,Message,ccmails);
    
    Remarks="Message Sent Sucessfully";
    	
    String msgid = CodeGenerate.NewCodeGenerate("TBL_CUSTOMER_QUERY");
    psUpdate=conn.prepareStatement("INSERT INTO TBL_CUSTOMER_QUERY(S_CUSTOMER_QUERY_ID,S_MESSAGE_HEAD,S_MESSAGE_DESC,S_MESSAGE_SIGN,S_STATUS,S_SENDER_EMAILID,S_CUSTOMER_NAME,S_SUBMITTED_BY,D_SUBMITTED_DATE) "+
    		" VALUES(?,?,?,?,?,?,?,?,SYSDATE)");
    psUpdate.setObject(1,msgid);
    psUpdate.setObject(2,msgHead);
	psUpdate.setObject(3,msgdesc);
	psUpdate.setObject(4,msgsignature);

	psUpdate.setObject(5,"OPEN");
	psUpdate.setObject(6,comma[0]);
	psUpdate.setObject(7,comma[1]);
	psUpdate.setObject(8,userid);
	
	
	psUpdate.executeUpdate();
    psUpdate.close();
    //conn.close();
	//conn = null;
	}
	catch(Exception e){
		logger.error("StdMail:" +  "\nClass: " + e.getClass() + "\nMessage: " + e.getMessage() + "\n", e);	
	}finally{
			DaoUtility.releaseResources(psUpdate, rs, conn);
	}
    %>   
		   
		  <jsp:forward page="StdMessage.jsp"><jsp:param name="msg"  value="<%=Remarks %>"  />
 </jsp:forward>
	
</html>