<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<%@ page import="com.enercon.global.utils.DynaBean"%>
<%@ page import="com.enercon.customer.util.CustomerUtil" %>
<%@ page import="java.util.*" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/screen.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/resources/js/ajax.js"></script>
<%
if (session.getAttribute("loginID") == null)
{
      response.sendRedirect(request.getContextPath());
}
%>
<%
String rn = "";
String de = "";
String ri = "";
DynaBean dynabean = (DynaBean)session.getAttribute("dynabean");
if(dynabean != null){
	rn = dynabean.getProperty("MsgHeadtxt").toString();
	de = dynabean.getProperty("MsgDescriptiontxt").toString();
	ri = dynabean.getProperty("MsgIdtxt").toString();
	session.removeAttribute("dynabean");
}
%>
<%
response.setHeader("Pragma","no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();
//String rolename = AdminUtil.fillMaster("TBL_ROLE_MASTER",ex);
%>
<script type="text/javascript">
function checkWholeForm(){

    var why = "";
     	   why += checkUsername(document.forms[0].MsgHeadtxt);
     	
     	    why += checkUsername(document.forms[0].MsgDescriptiontxt);
     	 
     	   why += checkUsername(document.forms[0].MsgSigntxt);
     	
     	  
     	    
        if (why != "") {
                     alert(why);
                       return false;
                   }
    
    return true;
    
}
function checkDropdown(choice) {
    var error = "";
    if (choice == 0) {
       error = "You didn't choose an option from the drop-down list.\n";
    }    
return error;
}  
function checkUsername(strng) {
	var SDate = strng.value; 
	       
 	var error = "";
 	if (SDate=='') {
   	 error = "All Field Are Mandatory.\n";
 		}
 		return error;
 }    
 
</script>
</head>
<body>
<div align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0" width="700">
<tr width="100%">
<td width="100%" align="center">
<form action="ReplyMail.jsp" name="StdMail" method="post" >
<table align="center" border="0" cellpadding="0" cellspacing="0" width="662">
	<tbody><tr>
	<td class="newhead1"></td>
	<th width="352" class="headtext">An Email To Service Support</th>
	<td width="65"><img src="<%=request.getContextPath()%>/resources/images/formtab_r.gif" border="0" height="21" width="10"></td>
	<td width="173" class="newhead3">&nbsp;</td>
	<td width="36" class="newhead4"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="1" width="10"></td>
</tr>
<tr>
			<%  String Custid =request.getParameter("aid");
			    CustomerUtil custutils = new CustomerUtil();
				List tranListData = new ArrayList();
         		tranListData = (List)custutils.getQueryDetail(Custid,"ID");  
                // System.out.println(Custid);
         		
         		%>
        		
		<%  String subject="",cname="",desc="",sign="",email="",status="";
		for (int k=0; k <tranListData.size(); k++)
			{   
				Vector wecdata = new Vector();
				wecdata = (Vector)tranListData.get(k);
				
				subject=wecdata.get(1).toString();
				cname=wecdata.get(7).toString();
				desc=wecdata.get(2).toString();
				sign=wecdata.get(3).toString();
				email= wecdata.get(6).toString();
				status= wecdata.get(5).toString();
				System.out.println(status);
				
			}
			%>	
	<td class="newheadl"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	<td colspan="3">
	<img src="<%=request.getContextPath()%>/<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	<table border="0" cellpadding="0" cellspacing="0" width="100%"><tbody><tr><td bgcolor="#dbeaf5">
		<table border="0" cellpadding="2" cellspacing="1" width="100%">
		<tbody>						
		
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Subject:</td>
			<td class="bgcolor"><%=subject%></td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Customer Name:</td>
			<td class="bgcolor"><%=cname%></td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Complaint Description:</td>
			<td class="bgcolor"><%=desc%></td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Sender Signature:</td>
			<td class="bgcolor"><%=sign%></td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Sender Emailid:</td>
			<td class="bgcolor"><%=email%></td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Status</td>
			   <td class="bgcolor">
				<select size="1" id="Status" name="Status" class="t_street_address">
		            <%if(status.equals("OPEN")){ %>
		             <option value="PROCESSING">PROCESSING</option>
		             <option value="CLOSE">CLOSE</option>
		            <%}else{ %>
		             <option value="CLOSE">CLOSE</option>
		            <%} %>
		             
		        </select>
		 </td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address" width="234">&nbsp;Reply &nbsp;Subject:</td>
			<td class="bgcolor" width="345"><input type="text" id="MsgHeadtxt" name="MsgHeadtxt" size="55" value="<%=rn%>" class="BoxBorder" maxlength="55" /></td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Reply Description:</td>
			<td class="bgcolor"><textarea rows="5" cols="60" id="MsgDescriptiontxt" name="MsgDescriptiontxt" class="BoxBorder" ><%=de%></textarea></td>
		</tr>
		<tr class="bgcolor"> 
			<td id="t_street_address">&nbsp;Sender Signature(Please Specify your Name,Designation and Contact No.):</td>
			<td class="bgcolor"><textarea rows="3" cols="50" id="MsgSigntxt" name="MsgSigntxt" class="BoxBorder" ><%=de%></textarea></td>
		</tr>
				
		<tr class="bgcolor"> 
			<td colspan="2">
				<html:errors /><B><font color="red">
				<%String str=(String)request.getParameter("msg");%>
				<%if(str != null){%>
				<%=str%>
				<%}%>
				</font></B>
			</td>
		</tr>	
		</tbody></table></td></tr></tbody></table>
		<img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="10" width="1"><br>
	
		</td>
	<td class="newheadr"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0"></td>
	</tr>
<tr>
	<td width="36"><img src="<%=request.getContextPath()%>/resources/images/formtab_b.gif" border="0" height="20" width="10"></td>
	<td colspan="4" align="right" bgcolor="#006633">
	<table border="0" cellpadding="0" cellspacing="0">
	<tbody><tr>
		<td class="btn" width="100">
			<input type="hidden" id="MsgIdtxt" name="MsgIdtxt" value="<%=Custid%>" />
			<input type="hidden" id="msgemail" name="msgemail" value="<%=email%>" />
				
			<input name="Submit" type="submit" class="btnform" value="Sent" onClick="return checkWholeForm()"/>
		</td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
		<td class="btn" width="100"><input name="Reset" value="Cancel" class="btnform" type="reset"></td>
		<td width="1"><img src="<%=request.getContextPath()%>/resources/images/pixel.gif" border="0" height="18" width="1"></td>
	</tr>
	</tbody></table>
	</td>
</tr>
</tbody></table>
</form>	
</td>		
</tr>

</table>
</div>
</body>
</html>