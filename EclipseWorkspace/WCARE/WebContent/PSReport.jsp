<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>

<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.admin.util.AdminUtil" %>
<%@ page import="java.util.*" %>
<%@ page import="com.enercon.security.utils.SecurityUtils"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<LINK href="<%=request.getContextPath()%>/resources/css/GridReport.css" type=text/css rel=stylesheet>
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>

<%
int status = 0;
String eid = "";
String sname = "";
String ctid = "";
String ebshort="" ;
String ebdesc="";
String ebtype="";
String ebstype="" ;	
String ebmfactor = "";	
String ebstatus ="";	
String ebcapacity = "";
String ebsite = "";	
	
DynaBean dynaBean = (DynaBean)session.getAttribute("dynabean");
if(dynaBean != null){
	ebshort = dynaBean.getProperty("EBShorttxt").toString();
	ebdesc = dynaBean.getProperty("EBDesctxt").toString();
	ebtype = dynaBean.getProperty("EBTypetxt").toString();
	ebstype = dynaBean.getProperty("EBSubTypetxt").toString();	
	ebmfactor = dynaBean.getProperty("EBMFactortxt").toString();	
  	//ebstatus = dynaBean.getProperty("EBStatustxt").toString();
  	if(dynaBean.getProperty("EBStatustxt") == null){
  		status = 0;
	}
	else if((dynaBean.getProperty("EBStatustxt")).toString().equals("1")){
		status = 1;
	}

	ebcapacity = dynaBean.getProperty("EBCapacitytxt").toString();
	ebsite = dynaBean.getProperty("EBSitetxt").toString();	
	eid = dynaBean.getProperty("EBIdtxt").toString();
	ctid = dynaBean.getProperty("Custtxt").toString();
	session.removeAttribute("dynabean");
}
//String sitename = AdminUtil.fillMaster("TBL_SITE_MASTER",stid);


response.getOutputStream().flush();
response.getOutputStream().close();
String userid=session.getAttribute("loginID").toString();
String Customeridtxt = "";

String custid ="";
String tablevis="";
StringBuffer buffer = new StringBuffer();
SecurityUtils secUtil = new SecurityUtils();
String got="0";
 	if(session.getAttribute("LoginType").equals("E"))
 	{
		 custid= AdminUtil.fillMaster("TBL_CUSTOMER_MASTER",ctid);
		 tablevis="1";
 	}
 	else
 	{ 
 		
 		
     	List tranList1 = new ArrayList(); 
     	tranList1 = (List)session.getAttribute("custtypee");
     	 if(tranList1.size()==0)
     	 {
     		 tranList1=secUtil.getcustomerdetails(userid);
     	 }
     	 
     	//System.out.println("custtypee"+session.getAttribute("custtypee")); 
	 	//System.out.println("tranList1.size():"+tranList1.size());
  		 if(tranList1.size()==1)
   			{
	  		 got="1";  
   			}
  
   	//System.out.println(tranList1.size());
	    for (int j=0; j <tranList1.size(); j++)
	     {
	    	Vector v11 = new Vector();
			 v11 = (Vector)tranList1.get(j);
	   	  String customerid=(String)v11.get(2);
	   	 String customername=(String)v11.get(3);
	   	 //System.out.println("customername"+customername);
	   	   buffer.append("<OPTION VALUE='" + customerid + "' >" + customername + "</OPTION>");
	     }
	     custid=buffer.toString();
	     //System.out.println("cc"+custid);
	   }



%>
<script type="text/javascript">

function replaceit(str,Instring,findString)
 {
var s="";
 var s = replaceAll(str,Instring,findString);
 return s;
}

function replaceAll(oldStr,findStr,repStr) {
  var srchNdx = 0;  // srchNdx will keep track of where in the whole line
                    // of oldStr are we searching.
  var newStr = "";  // newStr will hold the altered version of oldStr.
  while (oldStr.indexOf(findStr,srchNdx) != -1)  
                    // As long as there are strings to replace, this loop
                    // will run. 
  {
    newStr += oldStr.substring(srchNdx,oldStr.indexOf(findStr,srchNdx));
                    // Put it all the unaltered text from one findStr to
                    // the next findStr into newStr.
    newStr += repStr;
                    // Instead of putting the old string, put in the
                    // new string instead. 
    srchNdx = (oldStr.indexOf(findStr,srchNdx) + findStr.length);
                    // Now jump to the next chunk of text till the next findStr.           
  }
  newStr += oldStr.substring(srchNdx,oldStr.length);
                    // Put whatever's left into newStr.             
  return newStr;
}
function findProject() 
{
	 var req = newXMLHttpRequest();	 
	 var list = document.forms[0].Custtxt;
     var ApplicationId = list.options[list.selectedIndex].value;
     
	 req.onreadystatechange = getReadyStateHandler(req, showProject);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=PRJ_getProject&AppId="+ApplicationId);
}

function showProject(dataXml)
{  
	var cart = dataXml.getElementsByTagName("projhead")[0];
	var items = cart.getElementsByTagName("projcode");
	
	document.forms[0].Projecttxt.options.length = 0;
	document.forms[0].Projecttxt.options[0] = new Option("--Make a Selection--","");
	for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("pdef")[0].firstChild;
	     	if (nname != null)
	     	{
	     	document.forms[0].Projecttxt.options[I + 1] = new Option(item.getElementsByTagName("pdef")[0].firstChild.nodeValue,item.getElementsByTagName("pid")[0].firstChild.nodeValue);
   	 		 	          	 				
	        }
	     	
    	 		
    	 				
        }
}


function findApplication() 
{
	 var req = newXMLHttpRequest();
	 var cust = document.forms[0].Custtxt;
	 var proj = document.forms[0].Projecttxt;
     var custId = cust.options[cust.selectedIndex].value;
     var projId = proj.options[proj.selectedIndex].value;
     var ApplicationId = custId+","+projId;
	 req.onreadystatechange = getReadyStateHandler(req, showProjDetails);	  
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=PRJ_findProjectDetails&AppId="+ApplicationId);
}
function showProjDetails(dataXml)
{  
	var cart = dataXml.getElementsByTagName("projhead")[0];
	
	var items = cart.getElementsByTagName("projcode");	
	
		var divdetails = document.getElementById("psReportdetails");
		divdetails.innerHTML = "";
		var str = "";
		var s= "";
		
		
		for (var I = 0 ; I < items.length ; I++)
	   	{   
	     	var item = items[I]
	     	var nname = item.getElementsByTagName("pdef")[0].firstChild;
	     	if (nname != null)
	     	{
	     	str = item.getElementsByTagName("strData")[0].firstChild.nodeValue;
	     	s=str;
	     	
            s = replaceit(s," amp ","&");
        	s = replaceit(s," apos ","'");
       		s = replaceit(s," ltthan ","<");
        	s = replaceit(s," gtthan ",">");
   	 			          	 				
	        }
	    }
		
		divdetails.innerHTML = s;
}
function findDetails(ebid)
{    
	 var req = newXMLHttpRequest();
     var ApplicationId = ebid;
	 req.onreadystatechange = getReadyStateHandler(req, showCustMaster);
	 req.open("POST", "<%=request.getContextPath()%>/Ajax.do", true);
	 req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 req.send("Admin_Input_Type=findEBMasterByID&AppId="+ApplicationId);
}
function showCustMaster(dataXml)
{
	var cart = dataXml.getElementsByTagName("ebmaster")[0];
	var items = cart.getElementsByTagName("ebcode");	
	for (var I = 0 ; I < items.length ; I++)
   	{   
     	var item = items[I];
     	var nname = item.getElementsByTagName("ebname")[0].firstChild;
     	
     	if (nname != null){
     	
     	
   	 		document.forms[0].EBDesctxt.value = item.getElementsByTagName("ebname")[0].firstChild.nodeValue;
   	 		document.forms[0].EBShorttxt.value = item.getElementsByTagName("ebshort")[0].firstChild.nodeValue;
   	 		document.forms[0].EBTypetxt.value = item.getElementsByTagName("ebtype")[0].firstChild.nodeValue;  
   	 		document.forms[0].EBSubTypetxt.value = item.getElementsByTagName("ebstype")[0].firstChild.nodeValue;  
   	 		document.forms[0].EBCapacitytxt.value = item.getElementsByTagName("ebcapacity")[0].firstChild.nodeValue;
   	 		document.forms[0].EBMFactortxt.value = item.getElementsByTagName("ebmfact")[0].firstChild.nodeValue;  
   	 		document.forms[0].EBIdtxt.value = item.getElementsByTagName("ebid")[0].firstChild.nodeValue;  
   	 		 if(item.getElementsByTagName("ebstatus")[0].firstChild.nodeValue=="0")
   	 		 {
  		       document.forms[0].EBStatustxt.checked=false;
	          }
	        else
	          document.forms[0].EBStatustxt.checked=true;
   	 		
   	 		document.forms[0].Submit.value="Update";	 		
   	 	}
   	 	else{
   	 		document.forms[0].EBDesctxt.value = "";
   	 		document.forms[0].EBShorttxt.value = "";
   	 		document.forms[0].EBTypetxt.value = "";  
   	 		document.forms[0].EBSubTypetxt.value = "";  
   	 		document.forms[0].EBCapacitytxt.value = "";
   	 		document.forms[0].EBMFactortxt.value = "";
   	 		document.forms[0].EBIdtxt.value ="";
   	 	    document.forms[0].EBStatustxt.checked=false;
	        	 		
   	 	} 			
 	}
}
function chkFunction()
{
        if(document.forms[0].EBStatustxt.checked=true)
   	 		{document.forms[0].EBStatustxt.value=1; } 
	    else{
	        document.forms[0].EBStatustxt.value=0;
	        }
	        
}

function myFunction(pid,wid)
{

var url="Gantchart.jsp?projid="+pid+"&wecid="+wid;
newwindow=window.open(url,'name','height=600,width=900,top=100,resizable=yes,scrollbars=yes,toolbar=yes,status=yes');

}
</script>
</head>
<body>
<div align="center">
<form action="<%=request.getContextPath()%>/EBMaster.do" method="post" onSubmit="chkFunction()">

<table align="center" border="0" cellpadding="0" cellspacing="0" width="600">
<tbody>

<tr>
	<td class="newhead1"></td>
	<th class="headtext">Project Status Report</th>
	<td><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td class="newhead3">&nbsp;</td>
	<td class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
	<td background="<%=request.getContextPath()%>/resources/images/line_l.gif">&nbsp;</td>
	<td colspan="3">
	<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">
		<tbody>
		
			<%if(got.equals("0")){ %>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Select&nbsp;Customer:</td>
			<td class="bgcolor">
				<select size="1" id="Custtxt" name="Custtxt" class="ctrl" onChange="findProject()">
		            <option value="">--Make a Selection--</option>
		            <%=custid %>
		        </select>
			</td>
		</tr>
		<%}else{ %>	
		<tr class="bgcolor"> 
			<td id="t_street_address">Customer:</td>
			<td class="bgcolor">
			<select size="1" id="Custtxt" name="Custtxt"  class="ctrl" font:color="BLack" disabled>
		           <option value="">--Make a Selection--</option>
		           <%=custid %>
		        </select>
				
			</td>
		</tr>
		<script>
		document.forms[0].Custtxt.selectedIndex=1;
		findProject();
		</script>
	  <%} %>	
			<tr bgcolor="#ffffff">
		  <td id="t_general_information" width="40%">Select Project:           </td>
		  <td bgcolor="#ffffff" width="60%"><label>
		    <select name="Projecttxt" id="Projecttxt" class="ctrl">
              <option value="">--Make a Selection--</option>
			</select><input type="button" name="btnGenerate" value="Generate Report" onClick="findApplication()"/>
		  </td>
		  </tr>
		
		  <tr class="bgcolor"> 
			<td colspan="2">
				<html:errors />
				<%String str=(String)session.getAttribute("msg");%>
				<%if(str != null){%>
				<%=str%>
				<%}%>
				<%session.setAttribute("msg","");%>			</td>
		</tr>	
		</tbody></table></td></tr></tbody></table>
		<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	</td>
	<td background="<%=request.getContextPath()%>/resources/images/line_r.gif"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	</tr>
<tr>
	<td width="10"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633">
	<input type="hidden" name="Admin_Input_Type" value="EBMaster" />		
    <input type="hidden" name="EBIdtxt" value="<%=eid %>" />
	
	</td>
</tr>

</tbody></table>

</form>
<div id="psReportdetails"></div>
<!-- Form end -->

</div>
</body>
</html>