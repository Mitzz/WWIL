<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.global.utils.GlobalUtils"%>
<%
    response.setHeader("Cache-Control", "no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control", "no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma", "no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.enercon.security.utils.SecurityUtils;"%>
<%@ include file="FusionCharts.jsp"%>

<HTML><HEAD>

        <%
            if (session.getAttribute("loginID") == null) {
                response.sendRedirect(request.getContextPath());
            }
        %>

        <TITLE>DGR DashBoard Report</TITLE>
        <META http-equiv=Content-Type content="text/html; charset=windows-1252">

        <LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
        <LINK href="<%=request.getContextPath()%>/resources/resources\deluxe-menu.files/style.css" type=text/css rel=stylesheet>

        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script> 
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/ess.files/dtree.js"></script>
        <script type="text/javascript" src="<%=request.getContextPath()%>/resources/ess.files/dtabs.js"></script>
        <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>  

        <%
            response.setHeader("Pragma", "no-cache");
        //response.getOutputStream().flush();
        //response.getOutputStream().close();
            String userid = session.getAttribute("loginID").toString();
        // String Customeridtxt = "";

        %>

        <script type="text/javascript">
                     
         $(document).ready(function() {             
        	   $("#button").click(function () {       		   
        	   $("#button").hide();

	 	              /* $("#dynamictable").html("Dear Customer, These Key Account Managers have been assigned to you as per your Head office").css({"background-color": "#D2EBAB",
	 	                  "font-size": "12pt" , "text-align" : "center","font-weight" : "bold" ,"border":"1px",
	 	                 "color":"#000" ,"font-face":"Times New Roman" ,"width":"90%"}); */
	                      	 
	 	              $("<table/>", {id:"table"}).appendTo("#dynamictable").css({"width":"90%" ,"background":"#555555","border":"1px","cellspacing":"1","cellpadding":"2"});
	 	              $("<thead/>", {id:"thea"}).appendTo("#table");
	 	              $("<tr/>", {id:"tr"}).appendTo("#thea").css({"font-size": "12pt" , "text-align" : "center","font-weight" : "bold","font-family":"Times New Roman" ,"color":"#000","background":"#D2EBAB"});
	 	              $("<th/>", {text:"Key Account Managers(KAMs) have been assigned as per Head office Location",colspan:"5"}).appendTo("#tr");
	 	              $("<tr/>", {id:"tr1"}).appendTo("#thea").css({"font-size":"12px"}).css({"color":"#000","background":"#D2EBAB"}); 	           
	 	              $("<th/>", {text:"Office Location"}).appendTo("#tr1");
	                  $("<th/>", {text:"Name"}).appendTo("#tr1");
	                  $("<th/>", {text:"Designation"}).appendTo("#tr1");
	                  $("<th/>", {text:"Email id"}).appendTo("#tr1");
	                  $("<th/>", {text:"Contact Number/Extn."}).appendTo("#tr1");
	           
	                  var xmlData = "  <contact-hierarchy>	<contact>		<office-location>			<office>Maharashtra</office> 			<office>Madhya Pradesh</office> 		</office-location>		<name> Prashant Yevale</name>		<designation>  Manager </designation>		<email-id> prashant.yevale@windworldindia.com </email-id>		<contact-number> 9820943142 - 7417</contact-number>	</contact>		<contact>		<office-location>		    <office> Tamil Nadu</office> 		</office-location>		<name> N Muthu </name>		<designation> Dy. Manager </designation>		<email-id> muthu.n@windworldindia.com</email-id>		<contact-number> 9920709839 - 7416</contact-number>	</contact>		<contact>		<office-location> 		    <office>Gujarat</office> 		    <office>Rajasthan</office> 		    <office>Delhi</office> 		    <office>&amp; Other Northern Parts</office> 		</office-location>		<name> Kaushik Guha </name>		<designation> Sr. Manager </designation>		<email-id> kaushik.guha@windworldindia.com </email-id>		<contact-number> 9930065602 - 7418</contact-number>	</contact>		<contact>		<office-location> 		    <office>Andhra Pradesh</office> <office>Karnataka</office>		   </office-location><name> Ritu Thakur</name><designation>Asst. Manager</designation><email-id> ritu.thakur@windworldindia.com </email-id><contact-number> 7506342191 - 7387</contact-number></contact></contact-hierarchy>";
	
	 		           var xmlDoc = $.parseXML(xmlData);
	 		           var $xml = $(xmlDoc);
	 		           var $contact = $xml.find('contact');
	 	          
	 		           var className = "";
	 		           $contact.each(function(index){

		           		className = "contact" + index;
			           	var contactName = $(this).find('name').text();
			           	var designation = $(this).find('designation').text();
			           	var emailId = $(this).find('email-id').text();
			           	var contactNo = $(this).find('contact-number').text();			           	
			           	var officeLocation = $(this).find('office-location');
			           	var offices = officeLocation.find('office');
			           	var officeName = [];
			           	var noOfOffices = offices.length;
			           	
			           	for(var z = 0; z < noOfOffices; z++){
			           		officeName.push($(offices[z]).first().text());
			           	}
		           		
						$("<tr/>", {class:className}).appendTo("#table").css({"color":"#000","background":"white"});
						$("<td/>", {text:officeName[0]}).appendTo("." + className);
						$("<td/>", {text:contactName, rowspan:noOfOffices }).appendTo("." + className);
						$("<td/>", {text:designation, rowspan:noOfOffices }).appendTo("." + className);
						$("<td/>", {text:emailId, rowspan:noOfOffices }).appendTo("." + className);
						$("<td/>", {text:contactNo, rowspan:noOfOffices }).appendTo("." + className ).css({"text-align":"right"});
						
						for(var i = 1; i < noOfOffices; i++){
							$("<td/>", {text:officeName[i]}).appendTo($("<tr/>").appendTo("#table")).css({"color":"#000","background":"white"});	
						}	
		           });
   
	    	      /*  $("#dynamictable1").html("Incase you are not able to reach the KAM, Please feel free to get in touch with the following officials").css({"background-color": "#D2EBAB",
		                 "font-size": "12pt" , "text-align" : "center","font-weight" : "bold" , "color":"#000" ,"font-face":"Times New Roman" ,"width":"90%"}); */
                   $('#dynamictable1').append('<table class="x" id="tb1"></table>').css({"width":"90%"});
		           $("<tr/>", {id:"tr2"}).appendTo("#tb1").css({"font-size": "12pt" , "text-align" : "center","font-weight" : "bold","font-family":"Times New Roman" ,"color":"#000","background":"#D2EBAB"});
		 	       $("<th/>", {text:"For Queries regarding invoices & Payment, Please contact",colspan:"4"}).appendTo("#tr2");
		 	       var table = $('#dynamictable1').children();	 
		 	       var xmlData ="<contact-hierarchy><contact><name> Archana Mane </name><designation> Officer </designation><email-id> archana.mane@windworldindia.com</email-id><contact-number>7506408518 - 7397 </contact-number>   </contact><contact><name> Abhishek Gupta </name><designation> Officer </designation><email-id> abhishek.gupta@windworldindia.com</email-id><contact-number>7506408497 - 7411 </contact-number>   </contact><contact><name> Pranav Khombhadia  </name><designation> Officer </designation><email-id> pranav.khombhadia@windworldindia.com</email-id><contact-number>7506408519 - 7415 </contact-number>   </contact></contact-hierarchy>";        
		   	       var xmlDoc = $.parseXML(xmlData);
		   	       var $xml = $(xmlDoc);
		   	       var $contact = $xml.find('contact');
		     	      $contact.each(function(i){  
		     	        	 table.append("<tr><td class='a'>" + $(this).find('name').text()+"</td><td class='a'>" + $(this).find('designation').text() +"</td><td class='a' >" +   $(this).find('email-id').text()+"</td><td class='b'>" + $(this).find('contact-number').text()+"</td></tr>");
		     	          });       
		                 
		                 
		                 
		           $('#dynamictable2').append('<table class="x" id="tb2"></table>').css({"width":"90%"});
		           $("<tr/>", {id:"tr3"}).appendTo("#tb2").css({"font-size": "12pt" , "text-align" : "center","font-weight" : "bold","font-family":"Times New Roman" ,"color":"#000","background":"#D2EBAB"});
		 	       $("<th/>", {text:"In case you are not able to reach the KAMs, please feel free to get in touch with the following officials",colspan:"5"}).appendTo("#tr3");
	 	           var table = $('#dynamictable2').children();	 
		  	       var xmlData ="<contact-hierarchy><contact><name> Manjit Bhagria </name><designation> Head - Central Support </designation><email-id> manjit.bhagria@windworldindia.com</email-id><contact-number>9967783313 - 7169 </contact-number>   </contact><contact><name> Brig. YVR Vijay </name><designation> Head - WWO </designation><email-id> yvr.vijay@windworldindia.com</email-id><contact-number>9167617817 - 7371 </contact-number>   </contact></contact-hierarchy>";        
		   	       var xmlDoc = $.parseXML(xmlData);
		   	       var $xml = $(xmlDoc);
		   	       var $contact = $xml.find('contact');
		     	      $contact.each(function(i){  
		     	        	 table.append("<tr><td class='a'>" + $(this).find('name').text()+"</td><td class='a'>" + $(this).find('designation').text() +"</td><td class='a' >" +   $(this).find('email-id').text()+"</td><td class='b'>" + $(this).find('contact-number').text()+"</td></tr>");
		     	          });
  	               
	    	       $('#dynamictable3').append('<table class="x"></table>').css({"width":"90%"});;
	   	           var table = $('#dynamictable3').children();	     
	   	           var xmlData ="<contact-hierarchy><contact><name> Wind World India Limited  </name><address>022 - 66924848 </address></contact>	<contact> <name>  WWIL Address </name> <address> Wind World Towers, Plot No. A - 9, Veera Industrial Estate, Veera Desai Road, Andheri (West), Mumbai 400053, MH. </address></contact></contact-hierarchy>";	        
		       	   var xmlDoc = $.parseXML(xmlData);
		           var $xml = $(xmlDoc);
		           var $contact = $xml.find('contact');
	 	   	           $contact.each(function(i){  
	 	   	        	   table.append("<tr><th class='c'>" + $(this).find('name').text()+"<td class='a'>" + $(this).find('address').text()+" </td></tr>");  	   
		 	   	              });                               
		          });
		     }); 

             function displayMessage(){
             // alert("Dear Sir,\n\nOur server will be under maintenance from 09th Dec 2011 07:00 PM to Monday, 12th Dec 2011, 06:00 AM.\nInconvenience caused to you is regretted.");
             // window.open("http://172.18.25.144:7001/ECARE/index_temp.jsp");
             // alert("Dear Sir, \n\nWe are under process of system up-gradation & automation, for effective reporting, due to the same you may face some difficulty in the reports.\n\nIn case any difficulty  please write us at :- manoj.tiwari@windworldindia.com\n\nWe will address all your queries once automation process will get stabilized.\n\nInconvenience caused to you is deeply regretted.");

             // var url="http://172.18.16.27:7001/ECARE/index_temp.jsp";
             var width = 665;
                     var height = 200;
                     var left = (screen.width - width) / 1;
                     var top = (screen.height - height) / 2;
                     var params = 'width=' + width + ', height=' + height;
                     params += ', top=' + top + ', right=' + left;
                     params += ', directories=no';
                     params += ', location=no';
                     params += ', menubar=no';
                     params += ', resizable=no';
                     params += ', scrollbars=no';
                     params += ', status=no';
                     params += ', toolbar=no';
                     // newwin=window.open(url,'windowname5', params);
                     // if (window.focus) {newwin.focus()}
                     // return false;

             }

            function myFunction(sid, rdateA)
            {
            var req = newXMLHttpRequest();
                    // var list = document.forms[0].Statetxt;
                    var ApplicationId = sid + "," + rdateA;
                    req.onreadystatechange = getReadyStateHandler(req, showAppDetails);
                    req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
                    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    req.send("Admin_Input_Type=CUST_UPLOADSITE&AppId=" + ApplicationId);
            }
            function showAppDetails(dataXml)
            {
            var cart = dataXml.getElementsByTagName("sumaster")[0];
                    var items = cart.getElementsByTagName("mtcode");
                    var divdetails = document.getElementById("sitedetails");
                    divdetails.innerHTML = "";
                    var str = "<table border='1' cellpadding='2' cellspacing='1' width='100%'>"
                    str += "<tr align='center' class='TableTitleRow' height='20'><td class='TableCell' width='30'>S.N.</td><td class='TableCell' width='100'>Site Name</td><td class='TableCell' width='100'>Total WEC</td><td class='TableCell' width='100'>Uploaded WEC</td><td class='TableCell' width='100'>Balance</td><td class='TableCell' width='100'>Publish</td>"
                    str += "</tr>";
                    for (var I = 0; I < items.length; I++)
            {
            var item = items[I];
                    var nname = item.getElementsByTagName("sid")[0].firstChild;
                    if (nname != null){
            if (I % 2 == 0)
            {
            str += "<tr align='center' height='20' class='TableRow1'><td ALIGN='center'>" + (I + 1) + "</td>"
            }
            else
            {
            str += "<tr align='center' height='20' class='TableRow2'><td ALIGN='center'>" + (I + 1) + "</td>"
            }
            str += "<td class='TableCell' align='left'>" + item.getElementsByTagName("sname")[0].firstChild.nodeValue + "</td>"
                    str += "<td class='TableCell' align='left'>" + item.getElementsByTagName("scnt")[0].firstChild.nodeValue + "</td>"
                    str += "<td class='TableCell' align='left'>" + item.getElementsByTagName("supcnt")[0].firstChild.nodeValue + "</td>"
                    str += "<td class='TableCell' align='left'>" + item.getElementsByTagName("diff")[0].firstChild.nodeValue + "</td>"
                    str += "<td class='TableCell' align='left'>" + item.getElementsByTagName("pcount")[0].firstChild.nodeValue + "</td>"

            }
            }

            divdetails.innerHTML = str;
            }
            function myFunction1(sid, rdateA)
            {
            var req = newXMLHttpRequest();
                    // var list = document.forms[0].Statetxt;
                    var ApplicationId = sid + "," + rdateA;
                    req.onreadystatechange = getReadyStateHandler(req, showAppDetails1);
                    req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
                    req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                    req.send("Admin_Input_Type=CUST_UPLOAD_TIME&AppId=" + ApplicationId);
            }
            function showAppDetails1(dataXml)
            {
            var cart = dataXml.getElementsByTagName("sumaster")[0];
                    var items = cart.getElementsByTagName("mtcode");
                    var divdetails = document.getElementById("sitedetails1");
                    divdetails.innerHTML = "";
                    var str = "<table border='1' cellpadding='2' cellspacing='1' width='500'>"
                    str += "<tr align='center' class='TableTitleRow' height='20'><td class='TableCell' width='30'>S.N.</td><td class='TableCell' width='100'>Site Name</td><td class='TableCell' width='100'>Uploaded By</td><td class='TableCell' width='150'>Start Time</td><td class='TableCell' width='150'>End Time</td>"
                    str += "</tr>";
                    for (var I = 0; I < items.length; I++)
            {
            var item = items[I];
                    var nname = item.getElementsByTagName("sid")[0].firstChild;
                    if (nname != null){
            if (I % 2 == 0)
            {
            str += "<tr align='center' height='20' class='TableRow1'><td ALIGN='center'>" + (I + 1) + "</td>"
            }
            else
            {
            str += "<tr align='center' height='20' class='TableRow2'><td ALIGN='center'>" + (I + 1) + "</td>"
            }

            str += "<td class='TableCell' align='left'>" + item.getElementsByTagName("sid")[0].firstChild.nodeValue + "</td>"
                    str += "<td class='TableCell' align='left'>" + item.getElementsByTagName("sname")[0].firstChild.nodeValue + "</td>"
                    str += "<td class='TableCell' align='left'>" + item.getElementsByTagName("scnt")[0].firstChild.nodeValue + "</td>"
                    str += "<td class='TableCell' align='left'>" + item.getElementsByTagName("scnt1")[0].firstChild.nodeValue + "</td>"

            }
            }

            divdetails.innerHTML = str;
            }
   
        </script>
        
        <style>       
	         table.x{background:#555555;border:1px;width:100%;cellspacing="1";cellpadding="2" ; }
	         table.x th{background:#D2EBAB;color:black;}
	         table.x td{background:#F6F6EC;color:black;text-align:center;}
	         table.x td.a{text-align:left;}
	         table.x td.b{text-align:right;}
	         table.x th.c{font-family:Times New Roman;}   
       </style>
        
    </HEAD>
    <BODY bottomMargin=0 leftMargin=0   topMargin=0 rightMargin=0 marginheight="0" marginwidth="0" bgcolor="white">

        <FORM name="frmcustboard" post="DisplayCustomerBoard.jsp">
            <%
                String cls = "TableRow1";
            //System.out.println("LoginType"+session.getAttribute("LoginType").toString());

                int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String ardate = "";
                String rdate = "";
                String prevdate = "";
                String nextDate = "";

                if (request.getParameter("rdate") != null) {

                    java.util.Date date1 = dateFormat.parse(request.getParameter("rdate"));

                    rdate = dateFormat.format(date1.getTime());
                    ardate = dateFormat.format(date1.getTime());
                    prevdate = dateFormat.format(date1.getTime() - MILLIS_IN_DAY);
                    nextDate = dateFormat.format(date1.getTime() + MILLIS_IN_DAY);
                    // System.out.println("prevdate..."+prevdate);

                } else {
                    rdate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
                    ardate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
                    prevdate = dateFormat.format(date.getTime() - (MILLIS_IN_DAY + MILLIS_IN_DAY));
                    nextDate = dateFormat.format(date.getTime());
                    // System.out.println("rdate..."+rdate);
                    // System.out.println("prevdate..."+prevdate);
                    // System.out.println("nextDate..."+nextDate);
                }

            //System.out.println(request.getParameter("Datetxt"));
                if (request.getParameter("Datetxt") != null) {
                    ardate = request.getParameter("Datetxt");
                }

            //CustomerUtil custUtil = new CustomerUtil();
            //int ttlUnpublishedData = custUtil.getTotalUnpublishedData(ardate);
                if (session.getAttribute("LoginType") == null) {
                    response.sendRedirect(request.getContextPath());
                } else if (session.getAttribute("LoginType").equals("C")) {
               //System.out.println("LoginType"+session.getAttribute("LoginType").toString());

            %>
            <CENTER>

                <h4></h4>
                <%     //System.out.println("rdate"+rdate);
                    SecurityUtils secUtil = new SecurityUtils();
                    List tranList1 = new ArrayList();
                    String logid = session.getAttribute("loginID").toString();

                    tranList1 = (List) session.getAttribute("custtypee");
                    //System.out.println("tranList1"+tranList1);
                    if (tranList1.size() == 0) {
                        tranList1 = secUtil.getcustomerdetails(logid);
                    }
                    //String custid=request.getParameter("id");
                    //String rdate=request.getParameter("rd");
                    // String custid="";

                    List tranList = new ArrayList();
                    // List sitetranList = new ArrayList();
                    List msgList = new ArrayList();
                    CustomerUtil secutils = new CustomerUtil();

                   //  tranList = (List)secutils.getCustomerDetail(logid); 
                    //  // System.out.println("customer:"+tranList.size());
                    //  for (int j=0; j <tranList1.size(); j++)
                    //	{
                    //	Vector v11 = new Vector();
                    //	v11 = (Vector)tranList1.get(j);
                    //	custid=(String)v11.get(2);
                    //System.out.println("custid"+custid);
                    //	session.setAttribute(custid,"Customerid");
                       //	}*/
                    // System.out.println("customer:"+custid);     
                    // tranList = (List)secutils.getStateTotal(custid,rdate,"D"); 
                %>


                <%    msgList = (List) secutils.getMessageByLogid(logid);
     int msgListsize = msgList.size();
     if (msgListsize > 0) {%> 
                <TABLE cellSpacing=0 align="center" cellPadding=0 width="90%"  border="0">

                    <TBODY>

                        <TR class=TableTitleRow>

                        <TR class=TableTitleRow><TD class=TableCell width="100%"><font  size=4 face='Times New Roman'>Message For You</font></TD>

                            <%    for (int z = 0; z < msgList.size(); z++) {
                                    Vector vmsg = new Vector();
                                    vmsg = (Vector) msgList.get(z);
                                    int rem = 1;
                                    rem = z % 2;

                                    if (rem == 0) {
                                        cls = "TableRow2";
                                    } else {
                                        cls = "TableRow1";
                                    }

                            %>


                        <TR bgcolor='white'><td><b><font size=4 face='Times New Roman'>Dear Sir,</font></b></td></TR>
                        <TR bgcolor='white'>
                            <TD class=white width="100%"><font color=black size=3 face='Times New Roman'><pre><%= ((String) vmsg.get(0)).replace("\\N", "\n") %></pre></font></TD>
                        <TR bgcolor='white'><td><b><font  size=4 face='Times New Roman'>Regard,</font></b></td></TR>
                        <TR bgcolor='white'><td><b><font  size=4 face='Times New Roman'>Wind World(India) CRM TEAM.</font></b></td></TR>
                                        <%}
                        msgList.clear();%>   
                                        <%}%> 
                    </TBODY>
                </TABLE>
                <p></p>

                <TABLE cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=0>
                    <TBODY>
                        <TR>
                            <TD>
                                <TABLE cellSpacing=1 cellPadding=2 width="100%" border=0>
                                    <TBODY>
                                        <TR>
                                            <td class=SectionTitleLeft colspan="1" align="left"><input type="button" style="color:white;font=bold;background-color:green" value="Previous"  onClick=location.href="DisplayCustomerBoard.jsp?rdate=<%=prevdate%>" class="printbutton"></TD>
                                            <td class=SectionTitle colspan="6" ><font  size=4 face='Times New Roman'>Power Generation For The Date:<%=rdate%> </font></td>
                                            <td class=SectionTitleRight colspan="1"  align="right"><input type="button" style="color:white;font=bold;background-color:green" value="Next" onClick=location.href="DisplayCustomerBoard.jsp?rdate=<%=nextDate%>" class="printbutton"></TD>
                                        </TR>

                                        <%

                                        //String custid=request.getParameter("id");
                                            //String rdate=request.getParameter("rd");
                                            tranList.clear();
                                            tranList = (List) secutils.getStateTotalByLogid(logid, rdate, "D");

                                            msgListsize = tranList.size();
           if (msgListsize > 0) {%>

                                        <TR class=TableTitleRow>
                                            <TD class=TableCell1 width="10%">State</TD>
                                            <TD class=TableCell1 width="30%">Project</TD>

                                            <TD class=TableCell width="8%">Generation(KWH)</TD>
                                            <TD class=TableCell width="8%">Gen. Hours (Hrs.)</TD>
                                            <TD class=TableCell width="8%">Lull Hours (Hrs.)</TD>
                                            <TD class=TableCell width="8%">Capaciity Factor(%) </TD>
                                            <TD class=TableCell width="8%">MachineAvail (%)</TD>

                                            <TD class=TableCell width="8%">Grid Avail (%) </TD>
                                        </TR>


                                        <%
                                            String pcname = "", ncname = "";

                                            for (int i = 0; i < tranList.size(); i++) {
                                                Vector v = new Vector();
                                                v = (Vector) tranList.get(i);
                                                String name = (String) v.get(0);
                                                                     //String gen =String.parse(v.get(1));
                                                //String gen ="23.00";
                                                //String gen2 =gen.toString();

                                                                     //String ghrs = (String)v.get(2);
                                                //String lhrs = (String)v.get(3);
                                                //String mavail = (String)v.get(4);
                                                //String gavail = (String)v.get(5);
                                                //String cfactor = (String)v.get(6);
                                                //String stateid = (String)v.get(7);
                                                int rem = 1;
                                                rem = i % 2;

                                                if (rem == 0) {
                                                    cls = "TableRow2";
                                                } else {
                                                    cls = "TableRow1";
                                                }

                                                ncname = (String) v.get(0);
                                        %>

                                        <TR class=<%=cls%>>
                                            <% if (!pcname.equals(ncname)) {%>
                                            <TD class=TableCell1 align="left"><%=v.get(0)%></TD>
                                                <%} else {%>
                                            <TD class=TableCell1 align="left">-</TD>
                                                <%}%>
                                            <TD class=TableCell1 align="left"><a href=CustomerSiteBoard.jsp?stateid=<%=v.get(7)%>&id=<%=v.get(11)%>&rd=<%=rdate%> target='_blank'><%=(String) v.get(10)%> </a></TD>

                                            <TD class=TableCell><%=v.get(1)%></TD>
                                            <TD class=TableCell><%=v.get(2)%></TD>


                                            <%
                                                if (v.get(12).toString().equals("0")) {
                                            %>

                                            <TD class=TableCell><%=v.get(3)%></TD>
                                            <TD class=TableCell><%=v.get(6)%></TD>
                                            <TD class=TableCell><%=v.get(4)%></TD>
                                            <TD class=TableCell><%=v.get(5)%></TD>

                                            <%
                                            } else {
                                            %>

                                            <TD class=TableCell colspan="4"></TD>


                                            <%
                                                }
                                            %>

                                        </TR>

                                        <% pcname = ncname;
                }
            }%> 
                                        <%tranList.clear();
                                            tranList = (List) secutils.getOverallTotal("0", rdate, "D");
                                            for (int i = 0; i < tranList.size(); i++) {
                                                Vector v = new Vector();
                                                v = (Vector) tranList.get(i);
                                                String name = (String) v.get(0);
                                        %> 
                                        <TR class=TableSummaryRow>
                                            <TD class=TableCell colspan="2">Total</TD>
                                            <TD class=TableCell><%=v.get(1)%></TD>
                                            <TD class=TableCell><%=v.get(2)%></TD>
                                            <TD class=TableCell><%=v.get(3)%></TD>
                                            <TD class=TableCell><%=v.get(6)%></TD>
                                            <TD class=TableCell><%=v.get(4)%></TD>
                                            <TD class=TableCell><%=v.get(5)%></TD></TR>

                                        <% }%> 

                                    </TBODY></TABLE></TD></TR></TBODY></TABLE>
                                    <br/>
											 <input type="button" value="Contact Us" id="button" >
											 <div id="dynamictable"> </div>	
											 <br>
											 <div id="dynamictable1"> </div>										 
											 <br>
											 <div id="dynamictable2"> </div>
											 <br>
											 <div id="dynamictable3"> </div>
                                    <% }%> 
                <P><BR>
                <P>&nbsp;</P><A name=By_Month></A>

                <%
                if (session.getAttribute("LoginType").equals("E")) {
                %>
                <center>
                    <SPAN class=SectionTitle>Details of Data upload as on <%=ardate%></SPAN><BR>
                    <table cellSpacing=0 cellPadding=0 width="90%" bgColor=#555555 border=1>
                        <tr class="TableRow2"> 
                            <td id="Tablecell">&nbsp;</td>
                            <td class="Tablecell">Select&nbsp;Date:
                                <input type="text" name="Datetxt" id="Datetxt" size="20" class="ctrl" maxlength="10" value=<%=ardate%>  onfocus="dc.focus()" />
                                <a href="javascript:void(0)" id="dc" onClick="if (self.gfPop)gfPop.fPopCalendar(document.frmcustboard.Datetxt); return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a>
                            </td>
                            <td id="Tablecell"><input type="submit" value="Generate"></td>
                        </tr>

                        <%
                            CustomerUtil secUtil = new CustomerUtil();
                            List tranList = new ArrayList();
                            tranList = (List) secUtil.getUploadStatus(userid, ardate);
	
                            //System.out.println("sssssssssssssss");
                            //GlobalUtils.displayVectorMember(tranList);
                            String strXML = "";
                            strXML += "<chart labelDisplay='Rotate' slantLabels='1' palette='1' caption='WEC Uploaded Status' shownames='1' showvalues='0' numberPrefix='' sYAxisValuesDecimals='2' connectNullData='0' PYAxisName='Upload Data' SYAxisName='Publish Data'  numDivLines='4' formatNumberScale='1'>";

                            int totalwec = 0, tlupload = 0, tlbal = 0, tlpublish = 0;

                            strXML += "<categories>";

                            for (int i = 0; i < tranList.size() - 1; i++) {
                                Vector v = new Vector();
                                v = (Vector) tranList.get(i);
                                strXML += "<category label='" + v.get(0) + "' />";
                            }
                            strXML += "</categories>";

                            strXML += "<dataset seriesName='Uploaded Data' color='8EAC41' showValues='0'>";
                            for (int i = 0; i < tranList.size() - 1; i++) {
                                Vector v = new Vector();
                                v = (Vector) tranList.get(i);
                                strXML += "<set value='" + v.get(2) + "' />";

                            }
                            strXML += "</dataset>";

                            strXML += "<dataset seriesName='Balanced Data' color='F6BD0F' showValues='0'>";
                            for (int i = 0; i < tranList.size() - 1; i++) {
                                Vector v = new Vector();
                                v = (Vector) tranList.get(i);
                                strXML += "<set value='" + v.get(3) + "' />";

                            }
                            strXML += "</dataset>";

                            strXML += "<dataset seriesName='Published Data' color='607142' showValues='0' parentYAxis='S' >";
                            for (int i = 0; i < tranList.size() - 1; i++) {
                                Vector v = new Vector();
                                v = (Vector) tranList.get(i);
                                strXML += "<set value='" + v.get(5) + "' />";

                            }
                            strXML += "</dataset></chart>";
                            String chartCode = "";
                            chartCode = createChartHTML("myChart.swf", "", strXML, "chart13", 600, 350, false);

                            String strXML1 = "<chart   useRoundEdges='1'  caption='Total WEC Upload Status'  yAxisName='WEC Count' showValues='0' decimals='0' formatNumberScale='0'>";
                            strXML1 = strXML1 + "<categories><category label='WEC Status' /></categories>";

                            for (int i = tranList.size() - 1; i < tranList.size(); i++) {
                                Vector v = new Vector();
                                v = (Vector) tranList.get(i);
                         		//strXML1+="<set label='Total WEC' value='"+v.get(1)+"' />";
                                //strXML1+="<set label='Uploaded WEC' value='"+v.get(2)+"' />";
                                //strXML1+="<set label='Published WEC' value='"+v.get(5)+"' />";
                                //strXML1+="<set label='Balanced WEC' value='"+v.get(3)+"' />";

                                strXML1 += "<dataset seriesName='Total' color='AFD8F8' showValues='0'><set value='" + v.get(1) + "' /> </dataset>";
                                strXML1 += "<dataset seriesName='Uploaded' color='8EAC41' showValues='0'><set value='" + v.get(2) + "' /> </dataset>";
                                strXML1 += "<dataset seriesName='Published' color='607142' showValues='0'><set value='" + v.get(5) + "' /> </dataset>";
                                strXML1 += "<dataset seriesName='Balanced' color='F6BD0F' showValues='0'><set value='" + v.get(3) + "' /> </dataset>";

                            }

                            strXML1 += "</chart>";
                            String chartCode1 = createChartHTML("MSColumn2D.swf", "", strXML1, "chart14", 300, 350, false);
                        %>

                        <TR class="TableRow2"> 
                            <TD  align="CENTER"> <%=chartCode1%> </TD><TD COLSPAN="2" align="CENTER"> <%=chartCode%> </TD>

                        </TR>

                    </TABLE>
                    <table cellSpacing=0 cellPadding=0 width="95%" bgColor=#555555 border=1><tbody>
                            <TR class=TableTitleRow>
                                <TD class=TableCell1 width="11.28%">State</TD>
                                <TD class=TableCell  width="11.28%">Total WEC</TD>
                                <TD class=TableCell  width="11.28%">Uploaded WEC</TD>
                                <TD class=TableCell  width="11.28%">Balance</TD>
                                <TD class=TableCell  width="11.28%">Publish</TD>
                                <TD class=TableCell  width="11.28%">Scada Connected WEC</TD>
                                <TD class=TableCell  width="11.28%">Downloaded Scada Data</TD>
                                <TD class=TableCell  width="11.28%">Uploaded Scada Data</TD>
                            </TR>         


                            <%

                                for (int i = 0; i < tranList.size() - 1; i++) {
                                    Vector v = new Vector();
                                    v = (Vector) tranList.get(i);
                                    int rem = 1;
                                    rem = i % 2;

                                    if (rem == 0) {
                                        cls = "TableRow2";
                                    } else {
                                        cls = "TableRow1";
                                    }
                            %>




                            <TR class=<%=cls%>>

                                <TD class=TableCell1><a
                                        href="javascript:onClick=myFunction('<%=v.get(4)%>','<%=ardate%>');myFunction1('<%=v.get(4)%>','<%=ardate%>')"><%=v.get(0)%></a></TD>

                                <TD class=TableCell><%=v.get(1)%></TD>
                                <TD class=TableCell><%=v.get(2)%></TD>	           
                                <TD class=TableCell><%=v.get(3)%></TD>
                                <TD class=TableCell><%=v.get(5)%></TD>
                                <TD class=TableCell><%=v.get(6)%></TD>
                                <TD class=TableCell><%=v.get(8)%></TD>
                                <TD class=TableCell><%=v.get(7)%></TD>
                            </TR>
                            <%

                 }%>



                            <%
                                for (int i = tranList.size() - 1; i < tranList.size(); i++) {
                                    Vector v = new Vector();
                                    v = (Vector) tranList.get(i);
                                    int rem = 1;
                                    rem = i % 2;

                                    if (rem == 0) {
                                        cls = "TableRow2";
                                    } else {
                                        cls = "TableRow1";
                                    }
                            %>

                            <TR class=TableSummaryRow>

                                <TD class=TableCell><%=v.get(0)%></TD>
                                <TD class=TableCell><%=v.get(1)%></TD>
                                <TD class=TableCell><%=v.get(2)%></TD>	           
                                <TD class=TableCell><%=v.get(3)%></TD>
                                <TD class=TableCell><%=v.get(5)%></TD>
                                <TD class=TableCell><%=v.get(6)%></TD>
                                <TD class=TableCell><%=v.get(8)%></TD>
                                <TD class=TableCell><%=v.get(7)%></TD>
                            </TR>
                            <%

                 }%>


                        </TBODY></TABLE>
                </center>
                <%}%>



                <P><BR>
                <P>&nbsp;</P></CENTER></FORM>

        <table border="0" align="center" cellpadding="0"  cellspacing="0" width="400">
            <tbody>
                <tr>		
                    <td align="center" valign="top">					
                        <div id="sitedetails"></div>	
                    </td>
                    <td align="center" width="100">
                        &nbsp;&nbsp;					
                    </td>
                    <td align="center" valign="top">					
                        <div id="sitedetails1"></div>	
                    </td>
                </tr>			
            </tbody>
        </table>	

    </BODY>
    <iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
    </iframe>

</HTML>



