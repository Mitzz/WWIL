<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="com.enercon.security.utils.SecurityUtils" %>
<%@ page import="java.util.*" %>

<%@ page import="com.enercon.admin.util.*" %>

<html>
<head>
	<title>WEC Status</title>
		<script language=JavaScript src="table.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/css.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/qna1.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/table.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/resources/css/MYCSS.css" type="text/css">
<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
	<link href="mytable.css" rel=stylesheet>
<style>
body {
	font: 11px Arial,Verdana,Helvetica,sans-serif;
}
    </style>
    
   <script type="text/javascript">
    function finApplication()
     {  var url="";
     
       if(document.forms[0].FromDatetxt.value ==""||document.forms[0].UpToDatetxt.value=="" )
       {
         alert("Date Should Not Be Empty.");
       }
       else
       {
      	var url="LoginUser.jsp?fdt="+ document.forms[0].FromDatetxt.value +"&ldt="+document.forms[0].UpToDatetxt.value;
		 location.replace(url);
		}
    

}
    </script>
</head>
<body bottommargin="0" topmargin="15" leftmargin="15" marginheight="15" marginwidth="15">
<div align="center">
<table align="center" cellpadding="0"><tr><td bgcolor="#C9C9C9">
<tr>
<table border="0" cellpadding="0" cellspacing="0" width="100%" class="border2 fntAr f11 fntGray1"  background="<%=request.getContextPath()%>/resources/images/bg21.gif" >
  <tr height=5 bgcolor="#94A664">
   <td rowspan="3" height=5 ></td>
   <td height=5></td>
   <td rowspan="3" height=5 ></td>
  </tr>
  
  
  
  <tr>
     <table border="0" cellpadding="0" cellspacing="0" width="500" class="border2 fntAr f11 fntGray1"  background="<%=request.getContextPath()%>/resources/images/bg21.gif" >
  <tr height=5 bgcolor="#94A664">
   <td rowspan="3" height=5 ></td>
   <td height=5></td>
   <td rowspan="3" height=5 ></td>
  </tr>
  <tr   bgcolor="#94A664" height=5 class="textheadline2">
   <td  align="center" >Login History</td>
  </tr>
  <tr bgcolor="#94A664" height=1>
   <td ></td>
  </tr>
  <tr>
   <td  ></td>
   <td >
   		<table width="550" cellspacing="0" cellpadding="0">
   			<tr>
   				<td>
   					<html:errors />
					<%String str=(String)application.getAttribute("msg");%>
					<% String  fdate=(String)request.getParameter("fdt"); 
	                String  ldate=(String)request.getParameter("ldt"); 
	              //  System.out.print(fdate);
	              //  System.out.print(ldate);
	                %>
	                
					<%if(str != null){%>
					<%=str%>
					<%}%>
					<%application.setAttribute("msg","");%>				
   				</td>
   			</tr>
   		</table>
   		<!-- <form action="ProcessFileUpload.jsp" method="post" enctype="multipart/form-data" > -->
		<form action="<%=request.getContextPath()%>/loginuser.do" method="post" name="frmLoginUser">
			<table width="550" cellspacing="0" cellpadding="0">
				<tr class="tabtextnormal" align="left">
					<td width="100" height="30" align="left">From Date</td>
					<td width="8">:</td>
					<td width="400"><input type="text" name="FromDatetxt"  size="20" class="tabtextnormal" maxlength="10" onFocus="fdc.focus()" />
                      <a href="javascript:void(0)" id="fdc" onClick="if(self.gfPop)gfPop.fPopCalendar(document.frmLoginUser.FromDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a></td>
				</tr>
				<tr class="tabtextnormal" align="left">
					<td width="100" height="30" align="left">To Date</td>
					<td width="8">:</td>
					<td width="400"><label>
					<input type="text" name="UpToDatetxt"  size="20" class="tabtextnormal" maxlength="10" onFocus="tdc.focus()" />
                    <a href="javascript:void(0)" id="tdc"  onClick="if(self.gfPop)gfPop.fPopCalendar(document.frmLoginUser.UpToDatetxt);return false;" ><img class="PopcalTrigger" align="absmiddle" src="<%=request.getContextPath()%>/resources/images/calbtn.gif" border="0" alt=""></a></label></td>
				</tr>				
				
				<tr class="tabtextnormal">
					<td height="20" align="center" colspan="3">&nbsp;</td>
				</tr>
				<tr class="tabtextnormal">
				  <td height="20" align="center" colspan="3">
				
                    <input type="button" name="Submitcmd" class="buttonstyle" value="Show Detail"  onClick="finApplication()"/></td>
			  </tr>										
			</table>
		</form>	
				
   </td>
   <td ></td>
  </tr>
  <tr>
   <td bgcolor="#94A664"></td>
   <td bgcolor="#94A664"></td>
   <td bgcolor="#94A664"></td>
  </tr>
</table>


  
  
  
  
  </tr>
  
  
  <tr   bgcolor="#94A664" height=5 class="textheadline2">
   <td  align="center" ></td>
  </tr>
  <tr bgcolor="#94A664" height=1>
   <td ></td>
  </tr>
  </table>
</tr>
<tr><td>
<table>
<TR class=TableTitleRow>
						<TD class=TableCell width="14.28%">Sr No.</TD>

						<TD class=TableCell width="14.28%">User ID</TD>
						<TD class=TableCell width="14.28%">Description</TD>
						<TD class=TableCell width="14.28%">Login Date Time</TD>
						<TD class=TableCell width="14.28%">IP Address</TD>
						<TD class=TableCell width="14.28%">Host</TD>
						
						

					</TR>

<%

if(fdate!=null)
{
	
List tranList = new ArrayList();
AdminUtil secutils = new AdminUtil();
tranList = (List)secutils.getLoginDetail(fdate,ldate);   
String cls="";
for (int i=0; i <tranList.size(); i++)
		{
	      
	         
	          Vector v = new Vector();
 		        v = (Vector)tranList.get(i);
 		       
 		      
 		   
			
			 
			 	int rem = 1;
							rem = i % 2;

							if (rem == 0)
								cls = "TableRow2";
							else
								cls = "TableRow1";
					%>
					<TR class=<%=cls%>>
						<TD class=TableCell width="14.28%"><%=i+1%></TD>

						<TD class=TableCell width="14.28%"><%=(String)v.get(0)%></TD>
						<TD class=TableCell width="14.28%"><%=(String)v.get(1)%></TD>
						<TD class=TableCell width="14.28%"><%=(String)v.get(2)%></TD>
						<TD class=TableCell width="14.28%"><%=(String)v.get(3)%></TD>
						<TD class=TableCell width="14.28%"><%=(String)v.get(4)%></TD>
						
						

					</TR>
			 
						
	   <%}
	   
	   
	   }%>

             
</table>




</td>
</tr>
</table>
</div>
<iframe width=174 height=189 name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="<%=request.getContextPath()%>/resources/calendar/ipopeng.htm" scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;"  >
</iframe>
</body>
</html>