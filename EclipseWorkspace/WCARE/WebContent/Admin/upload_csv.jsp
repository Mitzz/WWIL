<%@ page import="java.io.*,java.sql.*,com.enercon.global.utils.JDBCUtils,com.enercon.global.utils.CodeGenerate,java.sql.CallableStatement,java.sql.PreparedStatement,java.sql.ResultSet,java.sql.SQLException" %>

<head>
<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
</head>
<%@page import="com.enercon.admin.dao.AdminDao"%>
<html>
<%  
    JDBCUtils conmanager = new JDBCUtils();
    Connection conn = conmanager.getConnection();
	String contentType = request.getContentType();
	if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0)) {
 	DataInputStream in = new DataInputStream(request.getInputStream());
	int formDataLength = request.getContentLength();
	byte dataBytes[] = new byte[formDataLength];
	int byteRead = 0;
	int totalBytesRead = 0;
	int updateCtr  = 0;
    int rejectCtr = 0;
    int insertCtr = 0;
	ResultSet rs = null;
	String RejRemarks="";
	while (totalBytesRead < formDataLength) {
	byteRead = in.read(dataBytes, totalBytesRead, formDataLength);
	totalBytesRead += byteRead;
	}
	String file = new String(dataBytes);
	String saveFile = file.substring(file.indexOf("filename=\"") + 10);
	//System.out.println("saveFile=" + saveFile);
	saveFile = saveFile.substring(saveFile.lastIndexOf("\\")+ 1,saveFile.indexOf("\""));
	//System.out.println("saveFile" + saveFile);
	saveFile = file.substring(file.indexOf("filename=\"") + 10);
		saveFile = saveFile.substring(0, saveFile.indexOf("\n"));
		saveFile = saveFile.substring(saveFile.lastIndexOf("\\")+ 1,saveFile.indexOf("\""));
		int lastIndex = contentType.lastIndexOf("=");
		String boundary = contentType.substring(lastIndex + 1,contentType.length());
		int pos;
		
		pos = file.indexOf("filename=\"");
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		pos = file.indexOf("\n", pos) + 1;
		int boundaryLocation = file.indexOf(boundary, pos) - 4;
		int startPos = ((file.substring(0, pos)).getBytes()).length;
		int endPos = ((file.substring(0, boundaryLocation)).getBytes()).length;

		FileOutputStream fileOut = new FileOutputStream(saveFile);
		fileOut.write(dataBytes, startPos, (endPos - startPos));
		%>
		<b>File Upload Status</b><br>
		<b>File Name :- 	<% out.println(saveFile); %> </b><br>
		<%
		Statement pst=null;
		PreparedStatement ps=null;
		PreparedStatement psUpdate=null;
		PreparedStatement prepStmt=null;
		AdminDao ad=new AdminDao();
        String line = null;
	    String value=null;
        try{
			StringBuilder contents = new StringBuilder();
			BufferedReader input =  new BufferedReader(new FileReader(saveFile));
			String sqlText="";
			String pid="";
			String aid="";
			String atid="";
			int lcnt=0;
			String rcdLine="";
			String uptype = request.getParameter("UploadType");
			// System.out.println("uptype:"+uptype);
			 
			 
//			.......................Upload Project Master............................//
			if(uptype.equals("ProjectMaster"))
			{		
			ps=conn.prepareStatement("Insert into TBL_PROJECT_MASTER (S_PROJECT_ID,S_PROJECT_DEFINITION,S_PROJECT_DESC,S_CUSTOMER_ID,S_SITE_ID,N_WEC_QUANTITY,S_WECTYPE,S_WEC_CAPACITY,S_PROJECT_STATUS,S_CREATED_BY,D_CREATED_DATE,S_LAST_MODIFIED_BY,D_LAST_MODIFIED_DATE)  values (?,?,?,?,?,?,?,?,'1','" + session.getAttribute("loginID").toString()+ "',sysdate,'" + session.getAttribute("loginID").toString()+ "',sysdate)");
			psUpdate=conn.prepareStatement("update  TBL_PROJECT_MASTER set S_PROJECT_DEFINITION=?,S_PROJECT_DESC=?,S_CUSTOMER_ID=?,S_SITE_ID=?,N_WEC_QUANTITY=?,S_WECTYPE=?,S_WEC_CAPACITY=?,S_LAST_MODIFIED_BY="+ session.getAttribute("loginID").toString()+",D_LAST_MODIFIED_DATE=sysdate where s_project_id=?");
			
			
			while (( line = input.readLine()) != null){
				pid  = CodeGenerate.NewCodeGenerate("TBL_PROJECT_MASTER");
			    contents.append(line);
			    lcnt=lcnt+1;
			    String comma[]=line.split(",");
			    //count the number of commas
			    int numberOfCommas = line.replaceAll("[^,]","").length();
			   // System.out.println("Count commas:"+numberOfCommas);
                if(numberOfCommas==7)
                {
        		String pdef=comma[1];
        		String pdesc=comma[2];
        		String cid=comma[0];
        		String pstate=comma[3];
        		String psite=comma[4];
        		String pqnty=comma[5];
        		String ptype=comma[6];
        		String pcapacity=comma[7];
        		
        		if(pdef.equals("")||pdef==null)
        			{rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Project definition Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
        		if(pdesc.equals("")||pdesc==null)
        		    {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Project description Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
        		if(cid.equals("")||cid==null)
        		    {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Customer ID Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
            	 if(pstate.equals("")||pstate==null)
        			
        		   {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"State Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
        		if(psite.equals("")||psite==null)
        		  {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Site Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
            		if(pqnty.equals("")||pqnty==null)
            		{rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Project Quantity Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
        		if(ptype.equals("")||ptype==null)
        		{rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Project Type Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
        		if(pcapacity.equals("")||pcapacity==null)
        		{rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Project capacity Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
        		pstate=pstate.toUpperCase();
        		psite=psite.toUpperCase();
        		//System.out.println(pstate+" "+psite);
        		String sqlquery="select a.s_site_id from tbl_site_master a,tbl_state_master b where a.s_state_id=b.s_state_id  and b.S_STATE_NAME=? and a.S_SITE_NAME=?";
            			
        		prepStmt = conn.prepareStatement(sqlquery);
        		prepStmt.setObject(1,pstate);
        		prepStmt.setObject(2,psite);
    			rs = prepStmt.executeQuery();
    			if (rs.next())
    			{
    				psite=rs.getObject("s_site_id").toString();			 				
    			}
    			else
    			 {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Site/State  Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";prepStmt.close();
	    			rs.close();continue;}
    			
    			
    			
    			sqlquery="select S_CUSTOMER_ID from tbl_customer_master  where S_SAP_CUSTOMER_CODE=?";
            	prepStmt = conn.prepareStatement(sqlquery);
            	prepStmt.setObject(1,cid);
    			rs = prepStmt.executeQuery();
    			if (rs.next())
    			{
    				cid=rs.getObject("S_CUSTOMER_ID").toString();			 				
    			}
    			else
    			 {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Customer ID Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";prepStmt.close();
	    			rs.close();continue;}
    			
    			
    			pdef=pdef.toUpperCase();
    		    sqlquery="select s_project_id from tbl_project_master  where S_PROJECT_DEFINITION=?  and S_CUSTOMER_ID=?";
    			
        		prepStmt = conn.prepareStatement(sqlquery);
        		prepStmt.setObject(1,pdef);
        		prepStmt.setObject(2,cid);
    			rs = prepStmt.executeQuery();
    			if (rs.next())
    			{
    				updateCtr=updateCtr+1;
    			
    				psUpdate.setObject(1,pdef);
    				psUpdate.setObject(2,pdesc.toUpperCase());
    				psUpdate.setObject(3,cid);
    				psUpdate.setObject(4,psite);
    				psUpdate.setObject(5,pqnty);
    				psUpdate.setObject(6,ptype);
    				psUpdate.setObject(7,pcapacity);
    				psUpdate.setObject(8,rs.getObject("s_project_id").toString());
    				psUpdate.addBatch();
            		
    				prepStmt.close();
	    			rs.close();
    				continue;
    			}
    			else
    			{
    				insertCtr=insertCtr+1;
        		ps.setObject(1,pid);
        		ps.setObject(2,pdef);
        		ps.setObject(3,pdesc.toUpperCase());
        		ps.setObject(4,cid);
        		ps.setObject(5,psite);
        		ps.setObject(6,pqnty);
        		ps.setObject(7,ptype);
        		ps.setObject(8,pcapacity);
        		ps.addBatch();
        		prepStmt.close();
    			rs.close();
    			}
    			
              }
                else
                {rcdLine= rcdLine+"->"+"Some Columns in the row are missing."+Integer.toString(lcnt)+"<br> ";
                }
                
           	}
			}
			
			
			
			
			
			
			
			//.......................Upload Activity Master............................//
			
			if(uptype.equals("ActivityMaster"))
			{				
					ps=conn.prepareStatement("Insert into TBL_PROJECT_ACTIVITY (S_ACTIVITY_ID,S_PROJECT_ID,S_NETWORK_ID,S_ACTIVITY_NO,S_ACTIVITY_DESC,S_ACTIVITY_PART_DISP,N_ACTIVITY_WEC_LEVEL,N_ACTIVITY_DISP_ORDER,S_CREATED_BY,D_CREATED_DATE,S_LAST_MODIFIED_BY,D_LAST_MODIFIED_DATE) values (?,?,?,?,?,?,?,?,'" + session.getAttribute("loginID").toString()+ "',sysdate,'" + session.getAttribute("loginID").toString()+ "',sysdate)");
					psUpdate=conn.prepareStatement("update  TBL_PROJECT_ACTIVITY set S_PROJECT_ID=?,S_NETWORK_ID=?,S_ACTIVITY_NO=?,S_ACTIVITY_DESC=?,S_ACTIVITY_PART_DISP=?,N_ACTIVITY_WEC_LEVEL=?,N_ACTIVITY_DISP_ORDER=?,S_LAST_MODIFIED_BY="+ session.getAttribute("loginID").toString()+",D_LAST_MODIFIED_DATE=sysdate where S_ACTIVITY_ID=?");
					
					
					while (( line = input.readLine()) != null){
						aid  = CodeGenerate.NewCodeGenerate("TBL_PROJECT_ACTIVITY");
					    contents.append(line);
					    lcnt=lcnt+1;
					    String comma[]=line.split(",");
					    //count the number of commas
					    int numberOfCommas = line.replaceAll("[^,]","").length();
					    //System.out.println("Count commas:"+numberOfCommas);
		                if(numberOfCommas==7)
		                {
		        		
		                	String cid=comma[0]==null?"NA":comma[0];
			        		String pdef=comma[1]==null?"NA":comma[1];
	                        String ndesc=comma[2]==null?"NA":comma[2];
	 		        		String ano=comma[3]==null?"NA":comma[3];

			        		String adesc=comma[4]==null?"NA":comma[4];
			        		String apart=comma[5]==null?"NA":comma[5];
			        		String alvl=comma[6]==null?"NA":comma[6];
			        		String aorder=comma[7]==null?"NA":comma[7];
			        		
			        		if(pdef.equals("")||pdef==null)
			        			{rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Project Definition Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(ndesc.equals("")||ndesc==null)
			        		    {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Network ID Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(cid.equals("")||cid==null)
			        			{rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Customer ID Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(ano.equals("")||ano==null)
			        		   {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Activity No Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(adesc.equals("")||adesc==null)
			        		   {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Activity Description Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(apart.equals("")||apart==null)
			        		  {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Activity Part Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(alvl.equals("")||alvl==null)
			        		  {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Activity Level Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(aorder.equals("")||aorder==null)
			        		  {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Activity Order No Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		
			        		
			        		String sqlquery="select S_CUSTOMER_ID from tbl_customer_master  where S_SAP_CUSTOMER_CODE=?";
			            	prepStmt = conn.prepareStatement(sqlquery);
			            	prepStmt.setObject(1,cid);
			    			rs = prepStmt.executeQuery();
			    			if (rs.next())
			    			{
			    				cid=rs.getObject("S_CUSTOMER_ID").toString();	 				
			    			}
			    			else
			    			 {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Customer Code Does Not Exist."+Integer.toString(lcnt)+"<br> ";prepStmt.close();
				    			rs.close();continue;}
			    			
			    			
			    			prepStmt.close();
			    			rs.close();
			    			sqlquery="select s_project_id from tbl_project_master  where S_PROJECT_DEFINITION=?  and S_CUSTOMER_ID=?";
			    			prepStmt = conn.prepareStatement(sqlquery);
			        		prepStmt.setObject(1,pdef);
			        		prepStmt.setObject(2,cid);
			    			rs = prepStmt.executeQuery();
			    			if (rs.next())
			    			{
			    				pdef=rs.getObject("s_project_id").toString();			 				
			    			}
			    			else
			    			 {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Project Does Not Exist."+Integer.toString(lcnt)+"<br> ";prepStmt.close();
				    			rs.close();continue;}
			    			
			    			
			    			pdef=pdef.toUpperCase();
			    			
 							sqlquery="select S_ACTIVITY_ID from TBL_PROJECT_ACTIVITY  where S_PROJECT_ID<>?  and S_NETWORK_ID=?";
			    			
			        		prepStmt = conn.prepareStatement(sqlquery);
			        		prepStmt.setObject(1,pdef);
			        		prepStmt.setObject(2,ndesc);
			        		
			    			rs = prepStmt.executeQuery();
			    			if (rs.next())
			    			{
			    				rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Network No already Assigned To Another Customer."+Integer.toString(lcnt)+"<br> ";prepStmt.close();
				    			rs.close();continue;
			    			}
			    			else
			    			{
			    				prepStmt.close();
				    			rs.close();
			    		    sqlquery="select S_ACTIVITY_ID from TBL_PROJECT_ACTIVITY  where S_PROJECT_ID=?  and S_NETWORK_ID=? and S_ACTIVITY_NO=?";
			    			
			        		prepStmt = conn.prepareStatement(sqlquery);
			        		prepStmt.setObject(1,pdef);
			        		prepStmt.setObject(2,ndesc);
			        		prepStmt.setObject(3,ano);
			    			rs = prepStmt.executeQuery();
			    			if (rs.next())
			    			{
			    				updateCtr=updateCtr+1;
			    				psUpdate.setObject(1,pdef);
			    				psUpdate.setObject(2,ndesc);
			    				psUpdate.setObject(3,ano);
			    				psUpdate.setObject(4,adesc);
			    				psUpdate.setObject(5,apart);
			    				psUpdate.setObject(6,alvl);
			    				psUpdate.setObject(7,aorder);
			    				psUpdate.setObject(8,rs.getObject("S_ACTIVITY_ID").toString());
			    				psUpdate.addBatch();
			    				
			    				
			    				prepStmt.close();
				    			rs.close();
			    				continue;
			    			}
			    			else
			    			{
			    				insertCtr=insertCtr+1;
			        		ps.setObject(1,aid);
			        		ps.setObject(2,pdef);
			        		ps.setObject(3,ndesc);
			        		ps.setObject(4,ano);
			        		ps.setObject(5,adesc);
			        		ps.setObject(6,apart);
			        		ps.setObject(7,alvl);
			        		ps.setObject(8,aorder);
			        		ps.addBatch();
			        		prepStmt.close();
			    			rs.close();
			    			}
			    			
			              }
			           	}
		                else
		                {rcdLine= rcdLine+"->"+"Some Columns in the row are missing."+Integer.toString(lcnt)+"<br> ";
		                }
					}
			}
			
			////////////////Upload Project Activity Transaction////////////////
			
			if(uptype.equals("ActivityTrans"))
			{				
					ps=conn.prepareStatement("Insert into TBL_ACTIVITY_TRANSACTION(S_ACTIVITY_TRAN_ID,S_ACTIVITY_ID,D_PLAN_STARTDATE,D_PLAN_ENDDATE,D_ACTUAL_STARTDATE,D_ACTUAL_ENDDATE,S_ACTIVITY_ACTUAL_STATUS,S_ACTIVITY_REMARKS) values (?,?,to_date(?,'DD.mm.yyyy'),to_date(?,'DD.mm.yyyy'),to_date(?,'DD.mm.yyyy'),to_date(?,'DD.mm.yyyy'),?,?)");
					psUpdate=conn.prepareStatement("update  TBL_ACTIVITY_TRANSACTION set S_ACTIVITY_ID=?,D_PLAN_STARTDATE=?,D_PLAN_ENDDATE=?,D_ACTUAL_STARTDATE=?,D_ACTUAL_ENDDATE=?,S_ACTIVITY_ACTUAL_STATUS=?,S_ACTIVITY_REMARKS=? WHERE  S_ACTIVITY_TRAN_ID=?");
					
					
					while (( line = input.readLine()) != null){
						atid  = CodeGenerate.NewCodeGenerate("TBL_ACTIVITY_TRANSACTION");
					    contents.append(line);
					    lcnt=lcnt+1;
					    String comma[]=line.split(",");
					    //count the number of commas
					    int numberOfCommas = line.replaceAll("[^,]","").length();
					   // System.out.println("Count commas:"+numberOfCommas);
		                if(numberOfCommas==8)
		                {
		        		
		                	
			        		String pdef=comma[0]==null?"NA":comma[0];
	                        String ndesc=comma[1]==null?"NA":comma[1];
	 		        		String ano=comma[2]==null?"NA":comma[2];

	 		        		String pdate=comma[3]==null?"NA":comma[3];
			        		String pedate=comma[4]==null?"NA":comma[4];
			        		String adt=comma[5]==null?"NA":comma[5];
			        		String aedt=comma[6]==null?"NA":comma[6];
               				String st=comma[7]==null?"NA":comma[7];
			        		String arem=comma[8]==null?"NA":comma[8];
			        		//System.out.println(adt);
			        		//System.out.println(aedt);
			        		if(pdef.equals("")||pdef==null)
			        			{rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Project Definition Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(ndesc.equals("")||ndesc==null)
			        		    {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Network ID Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		
			        		if(ano.equals("")||ano==null)
			        		   {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Activity No Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(pdate.equals("")||pdate==null)
			        		   {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Planning start DateS should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(pedate.equals("")||pedate==null)
			        		  {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Planning End Date should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(adt.equals("")||adt==null)
			        		  {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Actual start Date should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(aedt.equals("")||aedt==null)
			        		  {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Actual End Date should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
                            if(st.equals("")||st==null)
			        			{rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"status should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(arem.equals("")||arem==null)
			        		  {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Activity Remarks should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		String adtc="";
			        		String aedtc="";
			        		if(adt.equals("00.00.0000"))
			        			adtc=null;
			        		else
			        			 adtc=ad.convertDateFormat(adt,"dd.MM.yyyy","dd-MMM-yyyy");
			        		
			        			
			        		if(aedt.equals("00.00.0000"))
			        			aedtc=null;
			        		else
			        			aedtc=ad.convertDateFormat(aedt,"dd.MM.yyyy","dd-MMM-yyyy");
			        		//System.out.println(aedtc);
			        		String pdatec =ad.convertDateFormat(pdate,"dd.MM.yyyy","dd-MMM-yyyy");	
					    	String pedatec =ad.convertDateFormat(pedate,"dd.MM.yyyy","dd-MMM-yyyy");	
					    	
			        		pdef=pdef.toUpperCase();
			        		String sqlquery="SELECT PAM.S_ACTIVITY_ID from TBL_Project_Master PM, TBL_Project_activity PAM where PM.S_PROJECT_ID=PAM.S_PROJECT_ID and S_NETWORK_ID=? and S_ACTIVITY_NO=? and  PM.S_PROJECT_DEFINITION=?";
			        		prepStmt = conn.prepareStatement(sqlquery);
			        		prepStmt.setObject(1,ndesc);
			        		prepStmt.setObject(2,ano);
			        		prepStmt.setObject(3,pdef);
			    			rs = prepStmt.executeQuery();
			    			if (rs.next())
			    			{
			    				aid=rs.getObject("S_ACTIVITY_ID").toString();	
			    				//System.out.println(aid);
			    			}
			    			else
			    			 {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Activity Does Not Exist"+Integer.toString(lcnt)+"<br> ";
			    			 prepStmt.close();rs.close();continue;}
			    			
			    			prepStmt.close();
			    			rs.close();
			    		
			    		    sqlquery="select S_ACTIVITY_TRAN_ID from TBL_ACTIVITY_TRANSACTION  where S_ACTIVITY_ID=?";
			    			
			        		prepStmt = conn.prepareStatement(sqlquery);
			        		prepStmt.setObject(1,aid);
			    			rs = prepStmt.executeQuery();
			    			if (rs.next())
			    			{
			    				updateCtr=updateCtr+1;
			    				
			    				
			    				psUpdate.setObject(1,aid);
			    				psUpdate.setObject(2,pdatec);
			    				psUpdate.setObject(3,pedatec);
			    				psUpdate.setObject(4,adtc);
			    				psUpdate.setObject(5,aedtc);
			    				psUpdate.setObject(6,st);
			    				psUpdate.setObject(7,arem);
			    				psUpdate.setObject(8,rs.getObject("S_ACTIVITY_TRAN_ID").toString());
			    				
				        		psUpdate.addBatch();
				        		
			    				prepStmt.close();
				    			rs.close();
			    				continue;
			    			}
			    			else
			    			{
			    				insertCtr=insertCtr+1;
			        		ps.setObject(1,atid);
			        		ps.setObject(2,aid);
			        		ps.setObject(3,pdatec);
			        		ps.setObject(4,pedatec);
			        		ps.setObject(5,adtc);
			        		ps.setObject(6,aedtc);
			        		ps.setObject(7,st);
			        		ps.setObject(8,arem);
			        		ps.addBatch();
			        		prepStmt.close();
			    			rs.close();
			    			}
			    			
			    		
			              }
		                else
		                {rcdLine= rcdLine+"->"+"Some Columns in the row are missing."+Integer.toString(lcnt)+"<br> ";
		                }
			           	}
					}
					
			
			
			
			
			
			
			
			/////////////////Upload Material Transaction////////////////////////
			
			if(uptype.equals("MaterialTrans"))
			{				
					ps=conn.prepareStatement("Insert into TBL_MATERIAL_TRANSACTION(S_MATERIAL_TRANS_ID,S_PROJECT_ID,S_NETWORK_ID,S_ACTIVITY_NO,S_MATERIAL_ID,S_MATERIAL_STATUS,N_ISSUE_QUANTITY,N_AVAILABLE_QUANTITY,S_REMARKS) values (?,?,?,?,?,?,?,?,?)");
					psUpdate=conn.prepareStatement("update TBL_MATERIAL_TRANSACTION set S_PROJECT_ID=?,S_NETWORK_ID=?,S_ACTIVITY_NO=?,S_MATERIAL_ID=?,S_MATERIAL_STATUS=?,N_ISSUE_QUANTITY=?,N_AVAILABLE_QUANTITY=?,S_REMARKS=? where S_MATERIAL_TRANS_ID=?");

					
					while (( line = input.readLine()) != null){
						atid  = CodeGenerate.NewCodeGenerate("TBL_MATERIAL_TRANSACTION");
					    contents.append(line);
					    lcnt=lcnt+1;
					    String comma[]=line.split(",");
					    //count the number of commas
					    int numberOfCommas = line.replaceAll("[^,]","").length();
					   // System.out.println("Count commas:"+numberOfCommas);
		                if(numberOfCommas==7)
		                {
		        		
		                	
			        		String pdef=comma[0];
	                        String ndesc=comma[1];
	 		        		String ano=comma[2];
	 		        		
	 		        		String mno=comma[3];
			        		String mstatus=comma[4];
			        		String qty=comma[5];
			        		String aqty=comma[6];
               				String rem=comma[7];
               				String mid="";
               				
			        		if(pdef.equals("")||pdef==null)
			        			{rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Project Definition Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(ndesc.equals("")||ndesc==null)
			        		    {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Network ID Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(ano.equals("")||ano==null)
			        		   {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Activity No Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(mno.equals("")||mno==null)
			        		  {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Material No Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		//if(mstatus.equals("")||mstatus==null)
			        		//  {rejectCtr=rejectCtr+1;rcdLine= rcdLine+","+Integer.toString(lcnt);continue;}
			        		if(qty.equals("")||qty==null)
			        		  {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Quantity Type Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
                            if(aqty.equals("")||aqty==null)
			        			{rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Quantity Type Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		if(rem.equals("")||rem==null)
			        		  {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Remarks Should Not Be Blank."+Integer.toString(lcnt)+"<br> ";continue;}
			        		
			        		//if(mstatus.equals("")||mstatus==null)
			        		 // {}
			        		mstatus=mstatus.toUpperCase();
			        		if(mstatus.equals("AVAILABLE"))
			        			mstatus="Available";
			        		else
			        			mstatus="0";
			        		pdef=pdef.toUpperCase();
			        		String sqlquery="SELECT PM.S_PROJECT_ID from TBL_Project_Master PM, TBL_Project_activity PAM where PM.S_PROJECT_ID=PAM.S_PROJECT_ID and S_NETWORK_ID=?  and  PM.S_PROJECT_DEFINITION=?";
			        		prepStmt = conn.prepareStatement(sqlquery);
			        		prepStmt.setObject(1,ndesc);
			        		
			        		prepStmt.setObject(2,pdef);
			    			rs = prepStmt.executeQuery();
			    			if (rs.next())
			    			{
			    				pid=rs.getObject("S_PROJECT_ID").toString();	 				
			    			}
			    			else
			    			 {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Project Does Not Exist"+Integer.toString(lcnt)+"<br> ";prepStmt.close();
				    			rs.close();continue;}
			    			prepStmt.close();
			    			rs.close();
			    			sqlquery="SELECT S_MATERIAL_ID from TBL_MATERIAL_Master where S_MATERIAL_NO=?";
			        		prepStmt = conn.prepareStatement(sqlquery);
			        	    prepStmt.setObject(1,mno);
			        		rs = prepStmt.executeQuery();
			    			if (rs.next())
			    			{
			    				mid=rs.getObject("S_MATERIAL_ID").toString();	 				
			    			}
			    			else
			    			 {rejectCtr=rejectCtr+1;rcdLine= rcdLine+"->"+"Material Does Not Exist"+Integer.toString(lcnt)+"<br> ";
			    			 prepStmt.close();rs.close();continue;}
			    			
			    			prepStmt.close();
			    			rs.close();
			    		    sqlquery="select S_MATERIAL_TRANS_ID from TBL_MATERIAL_TRANSACTION  where S_PROJECT_ID=? and S_MATERIAL_ID=? and S_NETWORK_ID=? and S_ACTIVITY_NO=?";
			    			
			        		prepStmt = conn.prepareStatement(sqlquery);
			        		prepStmt.setObject(1,pid);
			        		prepStmt.setObject(2,mid);
			        		prepStmt.setObject(3,ndesc);
			        		prepStmt.setObject(4,ano);
			    			rs = prepStmt.executeQuery();
			    			if (rs.next())
			    			{
			    				updateCtr=updateCtr+1;
			    				
			    				
				        		psUpdate.setObject(1,pid);
				        		psUpdate.setObject(2,ndesc);
				        		psUpdate.setObject(3,ano);
				        		psUpdate.setObject(4,mid);
				        		psUpdate.setObject(5,mstatus);
				        		psUpdate.setObject(6,qty);
				        		psUpdate.setObject(7,aqty);
				        		psUpdate.setObject(8,rem);
				        		psUpdate.setObject(9,rs.getObject("S_MATERIAL_TRANS_ID").toString());
				        		psUpdate.addBatch();
				        		
			    				prepStmt.close();
				    			rs.close();
			    				continue;
			    			}
			    			else
			    			{
			    				insertCtr=insertCtr+1;
			        		ps.setObject(1,atid);
			        		ps.setObject(2,pid);
			        		ps.setObject(3,ndesc);
			        		ps.setObject(4,ano);
			        		ps.setObject(5,mid);
			        		ps.setObject(6,mstatus);
			        		ps.setObject(7,qty);
			        		ps.setObject(8,aqty);
			        		ps.setObject(9,rem);
			        		ps.addBatch();
			        		prepStmt.close();
			    			rs.close();
			    			}
			    			
			    		
			              }
		                else
		                {rcdLine= rcdLine+"->"+"Some Columns in the row are missing."+Integer.toString(lcnt)+"<br> ";
		                }
			           	}
					}
					
			
			
			
			
			
		
			
			
			
			
			
			
			
			
			value = contents.toString();
			//System.out.println("Value:"+value);
			//System.out.println("sqlText:"+sqlText);
			
			 int[] counts = ps.executeBatch();
			 int[] countup = psUpdate.executeBatch();
		      
		      //System.out.println("Total inserted rows: "+insertCtr);
		      //System.out.println("Total rejected rows: "+rejectCtr +"Rows:"+rcdLine);
		     // System.out.println("Total updated rows: "+updateCtr);
		   %>   
		   
		   <table width="500"> 
		   <tr class=TableSummaryRow><td  vAlign=middle class=TableCell align=left width=20%>
		   Total inserted rows:</td><td  vAlign=middle class=TableCell11 align=left width=80%> 	<% out.println(insertCtr); %>
		   </td></tr>
		   <tr class=TableSummaryRow><td  vAlign=middle class=TableCell align=left width=20%>
		         Total rejected rows: </td><td  vAlign=middle class=TableCell11 align=left width=80%> 	<% out.println(rejectCtr); %> 
		         </td></tr>
		          
		          
		           <tr class=TableSummaryRow><td  vAlign=middle class=TableCell align=left width=20%>
		           Total updated rows: </td><td  vAlign=middle class=TableCell11 align=left width=80%> 	<% out.println(updateCtr); %> 
		          </td></tr>
		          </table>
		           <table width="400"> 
		   
		          <tr class=TableSummaryRow><td  vAlign=middle class=TableCell11 align=left width=20%>
		           Rejected Remarks with line no: </td><td  vAlign=middle class=TableCell align=left width=80%> 	<% out.println(rcdLine); %> 
		          </td></tr>
		          
		          
		          </table>
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
    	
 } 
	%>
</html>


