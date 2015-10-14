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
	private boolean showMasterData = false;
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
int yearReceived = Integer.parseInt(request.getParameter("year"));
//System.out.println("DGRViewByYear_WECWise.jsp Year:" + yearReceived);
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
if (type.equals("YR")) {
	type = "Y";
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
if(showMasterData){
	siteID_siteDetail = new HashMap<String, ArrayList<String>>();
	stateID_siteID = new HashMap<String, ArrayList<String>>();
}
List tranList_1 = new ArrayList();
List sitetranList_3 = new ArrayList();
CustomerUtil secutils = new CustomerUtil();
// String st="_blank";
String cls = "TableRow1";
int list_1_counter = 0;
tranList_1 = (List) CustomerUtility.getStateWiseTotalForOneFiscalYearBasedOnCustomerId(custid, yearReceived);

if(type.equals("D") && showMasterData){
	//Getting HashMap with key as siteID and value as ArrayList of its detail
	siteID_siteDetail = new CustomerDao().getSiteIdSiteDetailsBasedOnCustomerID(custid);
	//System.out.println("siteID_siteDetail" + siteID_siteDetail);
	
	//Getting HashMap with key as stateID and value as ArrayList of siteID for particular Customer
	stateID_siteID = new CustomerDao().getStateIdSiteIdMappingBasedOnCustomerID(custid);
	//System.out.println("stateID_siteID" + stateID_siteID);
}

for (int i = 0; i < tranList_1.size(); i++) {
	ArrayList<String> siteIDsBasedOnStateId = new ArrayList<String>();
	list_1_counter++;
	Vector vector_12 = new Vector();
	vector_12 = (Vector) tranList_1.get(i);
	
	String name = (String) vector_12.get(0);
	String stateid = (String) vector_12.get(7);
	if(type.equals("D") && showMasterData){
		siteIDsBasedOnStateId = stateID_siteID.get(stateid);
		//System.out.println(siteIDsBasedOnStateId);
	}
	sitetranList_3.clear();
	sitetranList_3 = (List) CustomerUtility.getSiteWiseTotalForOneFiscalYearBasedOnCustomerIdStateId(custid, stateid, yearReceived);
	
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

		String siteid = (String) vector_34.get(7);
		if(type.equals("D") && showMasterData){
			//Removing siteid from hashMap and arrayList
			siteIDsBasedOnStateId.remove(siteid);
			siteID_siteDetail.remove(siteid);
			//System.out.println("Site Removed:" + siteIDsBasedOnStateId);
		}
		if (true) {
			if (diff < 0) {
				if (true) {
					strXML += "<tr class=" + cls + " align=left>"
							+ "<td class=TableCell1>" 
							+ "<a href=CustomerReportByYear_WECWise.jsp?stateid=" + stateid + "&siteid=" + siteid + "&id=" + custid + "&type=" + type + "&rd=" + rdate + "&year=" + yearReceived + "  target='_blank'>" + sname + "</a>"
							+ "</td>" 
							+ "<td align=center>" + vector_34.get(1) + "</td>" 
							+ "<td align=center>" + vector_34.get(2) + "</td>";
				}
				if (vector_34.get(10).toString().equals("0")) {
					strXML += "<td align=center>" + vector_34.get(3) + "</td>"
							+ "<td align=center>" + vector_34.get(6) + "</td>"
							+ "<td align=center>" + vector_34.get(4) + "</td>"
							+ "<td align=center>" + vector_34.get(5) + "</td>"
							+ "</tr>";
				} else {
					strXML += "<TD colspan='4' align=center>WEC Is In Stabilization Phase</td></tr>";
				}

			} else {
				if (true) {
					strXML += "<tr class="
							+ cls
							+ " align=left><td class=TableCell1><a   href=CustomerReport.jsp?stateid="
							+ stateid + "&siteid=" + siteid + "&id="
							+ custid + "&type=" + type + "&rd=" + rdate
							+ " target='_blank'>" + sname
							+ "</td><td align=center>" + vector_34.get(1)
							+ "</td><td align=center>" + vector_34.get(2)
							+ "</td>";
				}
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
	
	if(type.equals("D") && showMasterData){
		/* 
		If particular site in given state is missed then it will be present in ArrayList
		Then processing is here and it is removed from the ArrayList
		By this  time, all sites of a particular state have been displayed
		and that stateID is removed from HashMap
		*/
		if(siteIDsBasedOnStateId.size() != 0){
			//System.out.println("All Sites Not Processed");
			
			ArrayList<String> siteIDsBasedOnStateId_duplicate = new ArrayList<String>();
			siteIDsBasedOnStateId_duplicate = (ArrayList<String>)siteIDsBasedOnStateId.clone();
			
			for(String siteIDNotPresent:siteIDsBasedOnStateId_duplicate){
				siteIDsBasedOnStateId.remove(siteIDNotPresent);
				ArrayList<String> siteIDNotPresentDetail = siteID_siteDetail.get(siteIDNotPresent);
				if(cls.equalsIgnoreCase("TableRow1"))
					cls = "TableRow2";
				else
					cls = "TableRow1";
				// strXML += "<tr class=\"missing\" align=left>" 
				strXML += "<tr class= " + cls+" align=left>"
						+"<td class=TableCell1>"
						+"<a href=CustomerReport.jsp?stateid="
						+ stateid + "&siteid=" + siteIDNotPresent + "&id="
						+ custid + "&type=" + "D" + "&rd=" + rdate
						+ " target='_blank'>" + siteIDNotPresentDetail.get(0)
						+ "</td><td align=center>" + "-"
						+ "</td><td align=center>" + "-"
						+ "</td><TD align=center>" + "-"
						+ "</td><td align=center>" + "-"
						+ "</td><td align=center>" + "-"
						+ "</td><td align=center>" + "-"
						+ "</td></tr>";
			}
			if(siteIDsBasedOnStateId.size()== 0){
				//System.out.println("Every Site Processed with missing");
			}
		}
		else{
			//System.out.println("All Sites Processed without missing");
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
	if(type.equals("D") && showMasterData){
		//Removing State
		stateID_siteID.remove(stateid);
		
	}
	sitetranList_3.clear();
}
if(type.equals("D") && showMasterData){
	
	/*
	-If hashMap is not empty,then particular state is missed
	-Display With its State Name alongwith Site Name 
	*/
	if(stateID_siteID.size() != 0){
		//System.out.println("State is missing");
		//System.out.println("stateID_siteID" + stateID_siteID);
		//System.out.println("siteID_siteDetail" + siteID_siteDetail);
		for ( String missingStateId : stateID_siteID.keySet() ) {
		    ArrayList<String> siteIDs = new ArrayList<String>();
		    siteIDs = stateID_siteID.get(missingStateId);
		    String stateName = new String();
		    for(String missingSiteId:siteIDs){
		    	ArrayList<String> siteIDNotPresentDetail = siteID_siteDetail.get(missingSiteId);
		    	stateName = siteIDNotPresentDetail.get(1);
		    	if(cls.equalsIgnoreCase("TableRow1"))
					cls = "TableRow2";
				else
					cls = "TableRow1";
				strXML += "<tr class=" + cls +" align=left>"
						+"<td class=TableCell1>"
						+"<a href=CustomerReport.jsp?stateid="
						+ missingStateId + "&siteid=" + missingSiteId + "&id="
						+ custid + "&type=" + "D" + "&rd=" + rdate
						+ " target='_blank'>" + siteIDNotPresentDetail.get(0)
						+ "</td><td align=center>" + "-"
						+ "</td><td align=center>" + "-"
						+ "</td><TD align=center>" + "-"
						+ "</td><td align=center>" + "-"
						+ "</td><td align=center>" + "-"
						+ "</td><td align=center>" + "-"
						+ "</td></tr>";
		    }
		    strXML += "<tr class=TableSummaryRow align=left>"
					+"<td class=TableCell1>" + stateName
					+ "</td><td align=center>" + "-"
					+ "</td><td align=center>" + "-"
					+ "</td><TD align=center>" + "-"
					+ "</td><td align=center>" + "-"
					+ "</td><td align=center>" + "-"
					+ "</td><td align=center>" + "-"
					+ "</td></tr>";
		    
		}
		
	}
	
	//System.out.println("Everything Done");
	
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
