<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.global.utility.TimeUtility"%>
<%@page import="com.enercon.global.utility.NumberUtility"%>
<%@page import="com.enercon.customer.dao.CustomerDao"%>
<%@page import="com.enercon.global.utils.GlobalUtils"%>
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>

<%@ page contentType="text/html;charset=windows-1252"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="com.enercon.customer.util.CustomerUtility"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>

<%! 
	private boolean debug = false;
%>
<html>
    <head>
     
        <script src="<%=request.getContextPath()%>/resources/Grid/GridE.js"> </script>
        <script type="text/javascript">    

			<!-- Begin 
			function popUp(custid,type,rdate) {
				location.href="OverAllCustomerReport.jsp?id="+custid+"&type="+type+"&rd="+rdate;
				//location.href="FilterReportExcel.jsp?stateid="+state+"&wectype="+wec+"&fdate="+fdate+"&tdate="+tdate+"&siteid="+siteid+"&fobjectdesc="+fobjectdesc+"&ftypedesc="+ftypedesc+"&firstparam="+firstparam+"&secondparam="+secondparam+"&ebobject="+ebobject;
					
				//day = new Date();
				//id = day.getTime();
				//eval("page" + id + " = window.open(URL, '" + id + "', 'toolbar=0,scrollbars=0,location=0,statusbar=0,men ubar=0,resizable=0,width=400,height=10,left = 262,top = 234');");
			}

		</script>
 		<LINK href="<%=request.getContextPath()%>/resources/css/Report.css" type=text/css rel=stylesheet>
 		<style type="text/css">
 			.missing{
 				background-color: lime;
 			}
 		</style>
    </head>
   
    <body style="overflow: hidden">
   
    <form>
    <input type="hidden" name="custid" value="<%=request.getParameter("id") %>" />
    <input type="hidden" name="custid" value="<%=request.getParameter("rd") %>" />
<%
String custid = request.getParameter("id");
String rdate = request.getParameter("rd");
String type = request.getParameter("type");
String atype = request.getParameter("type");
int month = Integer.parseInt(request.getParameter("month"));
int year = Integer.parseInt(request.getParameter("year"));
//System.out.println("Month:" + month);
//System.out.println("Year:" + year);
String adate = "01/04/3009";
SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
// System.out.println(adate);
java.util.Date ffd = format.parse(rdate);
java.util.Date afd = format.parse(adate);
long diff = (ffd.getTime() - afd.getTime()) / (24 * 60 * 60 * 1000);
if(debug){
	System.out.println("custid:" + custid);
	System.out.println("rdate:" + rdate);
	System.out.println("type:" + type);
	System.out.println("atype:" + atype);
	System.out.println("adate:" + adate);
	System.out.println("diff:" + diff);
}
if (type.equals("DM")) {
	type = "M";
}

// System.out.println(rdate);
// System.out.println(type);
String strXML = "";
strXML = "<div id='menu' align='left'>";
strXML += "<table cellSpacing=0 cellPadding=0 width=100% bgColor=#555555 border=0>";
strXML += "<tr class=TableTitleRow>";
strXML += "<td class=TableCell11>State wise Power Generation Summary</td></tr></table></div>";
if (diff < 0) {
	strXML += "<Table  cellSpacing=0 cellPadding=0 width=100% bgColor=#555555 border=1>";
	strXML += "<tr class=TableTitleRow>";
	strXML += "<td class=class=TableCell1>Location</td>";
	strXML += "<td class=TableCell11>Generation(KWH)</td>";
	strXML += "<td class=TableCell11>Operating Hours(hrs)</td><td class=TableCell11>Lull HRS</td><td class=TableCell11>Capacity Factor(%)</td><td class=TableCell11>Machine Avail(%)</td><td class=TableCell11>Grid Avail(%)</td></tr>";
} else {
	strXML += "<Table  cellSpacing=0 cellPadding=0 width=100% bgColor=#555555 border=1><tr class=TableTitleRow><td class=class=TableCell1>Location</td><td class=TableCell11>Generation(KWH)</td><td class=TableCell11>Operating Hours(hrs)</td><td class=TableCell11>Lull HRS</td><td class=TableCell11>Capacity Factor(%)</td><td class=TableCell11>Machine Avail(%)</td><td class=TableCell11>Grid Avail(%) Internal</td><td class=TableCell11>Grid Avail(%) External</td></tr>";

}

List tranList_1 = new ArrayList();
List sitetranList_3 = new ArrayList();
CustomerUtil secutils = new CustomerUtil();
// String st="_blank";
String cls = "TableRow1";
int list_1_counter = 0;
/* tranList_1 = (List) secutils.getStateTotal(custid, rdate, type); */
tranList_1 = (List) CustomerUtility.getStateWiseTotalForOneMonthBasedOnCustomerIdMeta(custid,month,year);

for (int i = 0; i < tranList_1.size(); i++) {
	ArrayList<String> siteIDsBasedOnStateId = new ArrayList<String>();
	list_1_counter++;
	Vector vector_12 = new Vector();
	vector_12 = (Vector) tranList_1.get(i);
	if(vector_12.size() == 4){
		continue;
	}
	if(vector_12.size() == 5){
		continue;
	}
	String name = (String) vector_12.get(29);
	String stateid = (String) vector_12.get(30);
	sitetranList_3.clear();
	sitetranList_3 = (List) CustomerUtility.getSiteWiseTotalForOneMonthBasedOnStateIdCustomerIdMeta(custid, stateid, month, year);
	
	for (int j = 0; j < sitetranList_3.size(); j++) {
		Vector vector_34 = new Vector();
		vector_34 = (Vector) sitetranList_3.get(j);
		
		String sname = (String) vector_34.get(30);
		int rem = 1;
		rem = j % 2;

		if (rem == 0)
	cls = "TableRow2";
		else
	cls = "TableRow1";

		String siteid = (String) vector_34.get(31);
		
		if (atype.equals("DM")) {
	if (diff < 0) {
		strXML += "<tr class="
		+ cls
		+ " align=left><td class=TableCell1><a href=DayWiseMonthlyForByMonth_DateWise.jsp?stateid="
		+ stateid + "&siteid=" + siteid + "&id="
		+ custid + "&type=" + type + "&rd=" + rdate + "&month=" + month + "&year=" + year
		+ " target='_blank'>" + sname
		+ "</td><td align=center>" + NumberUtility.formatNumber((Long)vector_34.get(6))
		+ "</td><td align=center>" + TimeUtility.convertMinutesToTimeStringFormat(((Long)vector_34.get(8)), ":") 
		+ "</td>";
		if (vector_34.get(18).toString().equals("0")) {
	strXML += "<TD align=center>" + TimeUtility.convertMinutesToTimeStringFormat(((Long)vector_34.get(12)), ":") 
	+ "</td><td align=center>" + vector_34.get(15)
	+ "</td><TD align=center>" + vector_34.get(13)
	+ "</td><td align=center>" + vector_34.get(14)
	+ "</td></tr>";
		} else {
	strXML += "<TD colspan='4' align=center>WEC Is In Stabilization Phase</td></tr>";

		}

	} else {
		strXML += "<tr class="
		+ cls
		+ " align=left><td class=TableCell1><a href=DayWiseMonthly.jsp?stateid="
		+ stateid + "&siteid=" + siteid + "&id="
		+ custid + "&type=" + type + "&rd=" + rdate
		+ " target='_blank'>" + sname
		+ "</td><td align=center>" + NumberUtility.formatNumber((Long)vector_34.get(6))
		+ "</td><td align=center>" + TimeUtility.convertMinutesToTimeStringFormat(((Long)vector_34.get(8)), ":") 
		+ "</td>";

		if (vector_34.get(18).toString().equals("0")) {
	strXML += "<TD align=center>" + TimeUtility.convertMinutesToTimeStringFormat(((Long)vector_34.get(8)), ":")
	+ "</td><td align=center>" + vector_34.get(15)
	+ "</td><td align=center>" + vector_34.get(17)
	+ "</td><td align=center>" + vector_34.get(16)
	+ "</td><td align=center>" + vector_34.get(14)
	+ "</td></tr>";
		} else {
	strXML += "<TD colspan='5' align=center>WEC Is In Stabilization Phase</td></tr>";

		}
	}
		}
	}
	
	if (diff < 0) {
		strXML += "<tr class=TableSummaryRow>";
		if (atype.equals("DM")){
	strXML += "<td class=TableCell1><b>" + name + "</b></td>";
		}
		strXML += "<td align=center><b>" + NumberUtility.formatNumber((Long)vector_12.get(6))
		+ "</b></td><td align=center><b>" + TimeUtility.convertMinutesToTimeStringFormat(((Long)vector_12.get(8)), ":") 
		+ "</b></td>";
		if (vector_12.get(18).toString().equals("0")) {
	strXML += "<td align=center><b>" + TimeUtility.convertMinutesToTimeStringFormat(((Long)vector_12.get(12)), ":")
	+ "</b></td><td align=center><b>" + vector_12.get(15)
	+ "</b></td><td align=center><b>" + vector_12.get(13)
	+ "</b></td><td align=center><b>" + vector_12.get(14)
	+ "</b></td></tr>";
		} else {
	strXML += "<TD colspan='4' align=center>WEC Is In Stabilization Phase</td></tr>";

		}

	} else {
		strXML += "<tr class=TableSummaryRow>";
		if (atype.equals("DM")){
	strXML += "<td class=TableCell1><b>" + name + "</b></td>";
		}

		strXML += "<td align=center><b>" + NumberUtility.formatNumber((Long)vector_12.get(6))
		+ "</b></td><td  align=center><b>" + TimeUtility.convertMinutesToTimeStringFormat(((Long)vector_12.get(8)), ":") 
		+ "</b></td>";

		if (vector_12.get(18).toString().equals("0")) {
	strXML += "<td  align=center><b>" + TimeUtility.convertMinutesToTimeStringFormat(((Long)vector_12.get(12)), ":")
	+ "</b></td><td  align=center><b>" + vector_12.get(15)
	+ "</b></td><td align=center><b>" + vector_12.get(17)
	+ "</b></td><td align=center><b>" + vector_12.get(16)
	+ "</b></td><td align=center><b>" + vector_12.get(14)
	+ "</b></td></tr>";
		} else {
	strXML += "<TD colspan='5' align=center>WEC Is In Stabilization Phase</td></tr>";

		}
	}
	
	sitetranList_3.clear();
}

tranList_1.clear();
strXML += "</table>";
%>    
   
    </form>
     <script type="text/javascript">
     
	    parent.window.document.getElementById("progressbar").style.display ="block";
		parent.window.document.getElementById("progressbar").style.display ="none";
	
	</script>
   	<% 
if(list_1_counter>0){ 
	%> 
    
        <div style="width:100%;height:100%;"> 
 		     <%=strXML%>
        </div>
	<% 
} 
else{
	%> 
		<script type="text/javascript">
          alert("Sorry! Data For Selected Date or Month Or Year Not Available.");
         
      	</script>
      	<jsp:include page="Blank.jsp"/>
     <% 
}
     %>
    </body>
</html>
