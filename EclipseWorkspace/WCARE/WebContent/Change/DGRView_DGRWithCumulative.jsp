<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%@page import="com.enercon.customer.util.CustomerUtility"%>
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
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>

<%! 
	//private boolean showMasterData = false;
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
String adate = "01/04/3009";
SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
// System.out.println(adate);
java.util.Date ffd = format.parse(rdate);
java.util.Date afd = format.parse(adate);
long diff = (ffd.getTime() - afd.getTime()) / (24 * 60 * 60 * 1000);
//System.out.println("In Change/DGRView_DGRWithCumulative.jsp");
if(debug){
	
	System.out.println("custid:" + custid);
	System.out.println("rdate:" + rdate);
	System.out.println("type:" + type);
	System.out.println("atype:" + atype);
	System.out.println("adate:" + adate);
	System.out.println("diff:" + diff);
}
if (type.equals("DG")) {
	type = "D";
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

HashMap<String,ArrayList<String>> siteID_siteDetail = null;
HashMap<String,ArrayList<String>> stateID_siteID = null;
List tranList_1 = new ArrayList();
List sitetranList_3 = new ArrayList();
CustomerUtil secutils = new CustomerUtil();
// String st="_blank";
String cls = "TableRow1";
int list_1_counter = 0;
/* tranList_1 = (List) secutils.getStateTotal(custid, rdate, type); */
tranList_1 = (List) CustomerUtility.getStateWiseTotalForOneDayBasedOnCustomerId(custid, rdate);

for (int i = 0; i < tranList_1.size(); i++) {
	ArrayList<String> siteIDsBasedOnStateId = new ArrayList<String>();
	list_1_counter++;
	Vector vector_12 = new Vector();
	vector_12 = (Vector) tranList_1.get(i);
	if(vector_12.size() == 1){
		list_1_counter--;
		strXML += "<tr class=TableSummaryRow><td class=TableCell1>" + vector_12.get(0) + "</td><td style='text-align:center;' colspan = '6'>Site Data Not Available</td></tr>";
		continue;
	}
	String name = (String) vector_12.get(0);
	String stateid = (String) vector_12.get(7);
	
	sitetranList_3.clear();
	/* sitetranList_3 = (List) secutils.getSiteTotal(custid, rdate, stateid,type); */
	sitetranList_3 = (List) CustomerUtility.getSiteWiseTotalForOneDayBasedOnStateIdCustomerId(custid, stateid, rdate);
	
	for (int j = 0; j < sitetranList_3.size(); j++) {
		Vector vector_34 = new Vector();
		vector_34 = (Vector) sitetranList_3.get(j);
		
		String sname = (String) vector_34.get(0);
		int rem = 1;
		rem = j % 2;
		if (rem == 0)
			cls = "TableRow2";
				else
			cls = "TableRow1";
		if(vector_34.size() == 1){
			strXML += "<tr class=TableRow1><td class="+cls+">" + vector_34.get(0) + "</td><td style='text-align:center;' colspan = '6'>Data Not Available</td></tr>";
			continue;
		}

		String siteid = (String) vector_34.get(7);
		
		if (atype.equals("DG")) {
			if (diff < 0) {
				strXML += "<tr class="
						+ cls
						+ " align=left><td class=TableCell1><a href=NewDGRReport_DGRWithCumulative.jsp?stateid="
						+ stateid + "&siteid=" + siteid + "&id="
						+ custid + "&type=" + type + "&rd=" + rdate
						+ " target='_blank'>" + sname
						+ "</td><td align=center>" + vector_34.get(1)
						+ "</td><td align=center>" + vector_34.get(2)
						+ "</td>";
				if (vector_34.get(10).toString().equals("0")) {
					strXML += "<TD align=center>" + vector_34.get(3)
							+ "</td><td align=center>" + vector_34.get(6)
							+ "</td><TD align=center>" + vector_34.get(4)
							+ "</td><td align=center>" + vector_34.get(5)
							+ "</td></tr>";
				} else {
					strXML += "<TD colspan='4' align=center>WEC Is In Stabilization Phase</td></tr>";

				}

			} else {
				strXML += "<tr class="
						+ cls
						+ " align=left><td><a href=NewDGRReport.jsp?stateid="
						+ stateid + "&siteid=" + siteid + "&id="
						+ custid + "&type=" + type + "&rd=" + rdate
						+ " target='_blank'>" + sname
						+ "</td><td align=center>" + vector_34.get(1)
						+ "</td><td align=center>" + vector_34.get(2)
						+ "</td>";

				if (vector_34.get(10).toString().equals("0")) {
					strXML += "<TD align=center>" + vector_34.get(3)
							+ "</td><td align=center>" + vector_34.get(6)
							+ "</td><td align=center>" + vector_34.get(8)
							+ "</td><td align=center>" + vector_34.get(9)
							+ "</td><td align=center>" + vector_34.get(5)
							+ "</td></tr>";
				} else {
					strXML += "<TD colspan='5' align=center>WEC Is In Stabilization Phase</td></tr>";

				}
			}
		} 
	}
	
	if (diff < 0) {
		strXML += "<tr class=TableSummaryRow>";
		if (true) {
			strXML += "<td class=TableCell1><b>" + name + "</b></td>";
		}
		strXML += "<td align=center><b>" + vector_12.get(1)
				+ "</b></td><td align=center><b>" + vector_12.get(2)
				+ "</b></td>";
		if (vector_12.get(10).toString().equals("0")) {
			strXML += "<td align=center><b>" + vector_12.get(3)
					+ "</b></td><td align=center><b>" + vector_12.get(6)
					+ "</b></td><td align=center><b>" + vector_12.get(4)
					+ "</b></td><td align=center><b>" + vector_12.get(5)
					+ "</b></td></tr>";
		} else {
			strXML += "<TD colspan='4' align=center>WEC Is In Stabilization Phase</td></tr>";

		}

	} else {
		strXML += "<tr class=TableSummaryRow>";
		if (true) {
			strXML += "<td class=TableCell1><b>" + name + "</b></td>";
		}

		strXML += "<td align=center><b>" + vector_12.get(1)
				+ "</b></td><td  align=center><b>" + vector_12.get(2)
				+ "</b></td>";

		if (vector_12.get(10).toString().equals("0")) {
			strXML += "<td  align=center><b>" + vector_12.get(3)
					+ "</b></td><td  align=center><b>" + vector_12.get(6)
					+ "</b></td><td align=center><b>" + vector_12.get(8)
					+ "</b></td><td align=center><b>" + vector_12.get(9)
					+ "</b></td><td align=center><b>" + vector_12.get(5)
					+ "</b></td></tr>";
		} else {
			strXML += "<TD colspan='5' align=center>WEC Is In Stabilization Phase</td></tr>";

		}
	}
	
	sitetranList_3.clear();
}
tranList_1.clear();
List tranList_5 = new ArrayList();
// System.out.println(list_1_counter);
if (list_1_counter > 1) {
	tranList_5 = (List) secutils.getOverallTotal(custid, rdate, type);
	if(debug){
		System.out.println("-----------------5------------------");
		GlobalUtils.displayVectorMember(tranList_5);
	}
	for (int i = 0; i < tranList_5.size(); i++) {
		Vector vector_56 = new Vector();
		vector_56 = (Vector) tranList_5.get(i);
		if(debug){
			System.out.println("-----------------6------------------");
			GlobalUtils.displayVectorMember(vector_56);
		}
		String name = (String) vector_56.get(0);

		list_1_counter = 1;
	}
}
/* if(list_1_counter == 1 && type.equals("D") ){
	//Display the overall Total
} */
tranList_5.clear();
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