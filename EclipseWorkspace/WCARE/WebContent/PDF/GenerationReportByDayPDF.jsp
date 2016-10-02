<%@page import="com.enercon.customer.util.CustomerUtility"%>
<% 
    response.setHeader("Cache-Control","no-cache"); //Forces caches to obtain a new copy of the page from the origin server
    response.setHeader("Cache-Control","no-store"); //Directs caches not to store the page under any circumstance
    response.setDateHeader("Expires", 0); //prevents caching at the proxy server
    response.setHeader("Pragma","no-cache"); //HTTP 1.0 backward compatibility
%>
<%@ page contentType="application/pdf"%>
<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.lowagie.text.Image"%>

<%@ page import="com.lowagie.text.Font,com.lowagie.text.FontFactory,com.lowagie.text.*,com.lowagie.text.pdf.*,java.awt.*" %>
<%@ page import="com.enercon.admin.dao.AdminDao"%>
<%@ page import="com.enercon.customer.util.CustomerUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>

<%
if (session.getAttribute("loginID") == null) {
	response.sendRedirect(request.getContextPath());
}

response.setContentType( "application/pdf" );

String file = request.getParameter("File");
String rdate = request.getParameter("rd");
//System.out.println("rdare: " + rdate);
request.setCharacterEncoding("utf-8");
if (file == null || file.equals(""))
	file = "Daily Generation for " + rdate + ".pdf";
response.addHeader("Content-Disposition", "attachment; filename=\""	+ file + "\"");

Document document=new Document();
PdfWriter.getInstance(document,response.getOutputStream());
document.open();

String relativeWebPath = "/images/windworldlogo.GIF";

//relativeWebPath = "/img/enercon-logo.png";

String absoluteDiskPath = config.getServletContext().getRealPath(relativeWebPath);
//System.out.println(absoluteDiskPath);
//Image img = Image.getInstance("am.GIF");
Image logoImg = Image.getInstance(absoluteDiskPath);

response.setHeader("Pragma", "no-cache");
response.getOutputStream().flush();
response.getOutputStream().close();

String custid = request.getParameter("id");
String stateid = request.getParameter("stateid");
String siteid = request.getParameter("siteid");

//System.out.println(rdate);
String type = request.getParameter("type");

//System.out.println("id: " + custid);
//System.out.println("stateid: " + stateid);
//System.out.println("siteid: " + siteid);


int monthReceived = 0;
int yearReceived = 0;
int fiscalYear = 0;
if(type.equalsIgnoreCase("M")){
	monthReceived = Integer.parseInt(request.getParameter("month"));
	yearReceived = Integer.parseInt(request.getParameter("year"));
}
if(type.equalsIgnoreCase("Y")){
	fiscalYear = Integer.parseInt(request.getParameter("year"));
}
%>

<%
 	List tranList = new ArrayList();
 	List sitetranList = new ArrayList();
 	CustomerUtil secutils = new CustomerUtil();
 	tranList = (List) secutils.getEBHeading(custid, rdate, stateid,	siteid);
 	String cls = "TableRow1";
 	String ebid = "";
 	String cname = "";
 	String state = "";
 	String Remarks = "";
	String RemarksWEC = "";
 	for (int i = 0; i < tranList.size(); i++) {
 		Remarks = "";
 		RemarksWEC="";
 		Vector v = new Vector();
 		v = (Vector) tranList.get(i);
 		ebid = (String) v.get(5);

 		cname = (String) v.get(0).toString().replaceAll("&", "and");
 		state = (String) v.get(3) + "-" + (String) v.get(4);
 %> 
 <%
 		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
 		String FromDatetxt = AdminDao.convertDateFormat(rdate,"dd/MM/yyyy", "dd-MMM-yyyy");

 		String adate="01/04/2009";
 		java.util.Date ffd = format.parse(rdate);
 		java.util.Date afd = format.parse(adate);
 		long diff=(ffd.getTime()-afd.getTime())/(24 * 60 * 60 * 1000);
 		int month = ffd.getMonth() + 1;
 		int day = ffd.getDay();

 		String year = rdate.substring(6);
 		//String syear="";

 		int cyear = 2000 + ffd.getYear() - 100;
 		int nyear = cyear;
 		//System.out.println("Month: " + month);
 		//System.out.println("Year: " + cyear);
 		if (month >= 4) {
 			nyear = cyear + 1;
 		} else {
 			nyear = cyear;
 			cyear = cyear - 1;
 		}
 		String pdate = Integer.toString(cyear);
 		String ndate = Integer.toString(nyear);

 		String monthname = "";
 		if (month == 4) {
 			monthname = "APRIL";
 		}
 		if (month == 3) {
 			monthname = "MARCH";
 		}
 		if (month == 1) {
 			monthname = "JANUARY";
 		}
 		if (month == 2) {
 			monthname = "FEBRUARY";
 		}

 		if (month == 5) {
 			monthname = "MAY";
 		}

 		if (month == 6) {
 			monthname = "JUNE";
 		}

 		if (month == 7) {
 			monthname = "JULY";
 		}

 		if (month == 8) {
 			monthname = "AUGUST";
 		}

 		if (month == 9) {
 			monthname = "SEPTEMBER";
 		}

 		if (month == 10) {
 			monthname = "OCTOBER";
 		}

 		if (month == 11) {
 			monthname = "NOVEMBER";
 		}

 		if (month == 12) {
 			monthname = "DECEMBER";
 		}
 %>
 
 <%
PdfPTable table=new PdfPTable(8);

table.setWidthPercentage(100f);

//header data table

PdfPCell cell = new PdfPCell(logoImg);
cell.setColspan(8);
cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//cell.setBackgroundColor (new Color (128, 200, 128));
cell.setPadding(5.0f);
if(i==0){
table.addCell(cell);
if(type.equals("D")){
	cell = new PdfPCell (new Paragraph ("Daily Generation Report",FontFactory.getFont(FontFactory.COURIER, 15, Font.BOLD)));
	cell.setColspan (8);
	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
	cell.setBackgroundColor (new Color (128, 200, 128));
	cell.setPadding (01.2f);
	table.addCell (cell);
}else{
	cell = new PdfPCell (new Paragraph ("Monthly Generation Report",FontFactory.getFont(FontFactory.COURIER, 15, Font.BOLD)));
	cell.setColspan (8);
	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
	cell.setBackgroundColor (new Color (128, 200, 128));
	cell.setPadding (01.2f);
	table.addCell (cell);	
}
}
document.add(table);

table=new PdfPTable(8);
table.setWidthPercentage(100f);
cell = new PdfPCell (new Paragraph (v.get(0).toString(),FontFactory.getFont(FontFactory.COURIER, 15, Font.BOLD)));
cell.setColspan (8);
cell.setHorizontalAlignment (Element.ALIGN_CENTER);
cell.setBackgroundColor (new Color (128, 200, 128));
cell.setPadding (01.2f);
table.addCell (cell);

cell = new PdfPCell (new Paragraph (""));
cell.setColspan (8);
cell.setHorizontalAlignment (Element.ALIGN_CENTER);
cell.setBackgroundColor (new Color (128, 200, 128));
cell.setPadding (01.2f);
//2, 8, 24, 60, 136, 292, 608
table.addCell (cell);

cell = new PdfPCell (new Paragraph ("Location",FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD)));
cell.setHorizontalAlignment (Element.ALIGN_CENTER);
cell.setBackgroundColor (new Color (255, 200, 0));
cell.setPadding (01.2f);
table.addCell (cell);

cell = new PdfPCell (new Paragraph (v.get(3).toString()+"-"+v.get(4).toString(),FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD)));
cell.setColspan (4);
cell.setHorizontalAlignment (Element.ALIGN_CENTER);
cell.setBackgroundColor (new Color (255, 200, 0));
cell.setPadding (01.2f);
table.addCell (cell);

cell = new PdfPCell (new Paragraph ("Machine",FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD)));
cell.setHorizontalAlignment (Element.ALIGN_CENTER);
cell.setBackgroundColor (new Color (255, 200, 0));
cell.setPadding (01.2f);
table.addCell (cell);

cell = new PdfPCell (new Paragraph (v.get(8).toString(),FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD)));
cell.setColspan (3);
cell.setHorizontalAlignment (Element.ALIGN_CENTER);
cell.setBackgroundColor (new Color (255, 200, 0));
cell.setPadding (01.2f);
table.addCell (cell);

cell = new PdfPCell (new Paragraph (""));
cell.setColspan (8);
cell.setHorizontalAlignment (Element.ALIGN_CENTER);
cell.setBackgroundColor (new Color (128, 200, 128));
cell.setPadding (01.2f);
table.addCell (cell);

cell = new PdfPCell (new Paragraph ("Location Capacity",FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD)));
cell.setHorizontalAlignment (Element.ALIGN_CENTER);
cell.setBackgroundColor (new Color (255, 200, 0));
cell.setPadding (01.2f);
table.addCell (cell);

cell = new PdfPCell (new Paragraph (v.get(7).toString()+" MW",FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD)));
cell.setColspan (4);
cell.setHorizontalAlignment (Element.ALIGN_CENTER);
cell.setBackgroundColor (new Color (255, 200, 0));
cell.setPadding (01.2f);
table.addCell (cell);

if (type.equals("D")) {
	cell = new PdfPCell (new Paragraph ("Date",FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD)));
	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
	cell.setBackgroundColor (new Color (255, 200, 0));
	cell.setPadding (01.2f);
	table.addCell (cell);
	
	cell = new PdfPCell (new Paragraph (FromDatetxt,FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD)));
	cell.setColspan (2);
	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
	cell.setBackgroundColor (new Color (255, 200, 0));
	cell.setPadding (01.2f);
	table.addCell (cell);
}else{
	cell = new PdfPCell (new Paragraph ("Month",FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD)));
	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
	cell.setBackgroundColor (new Color (255, 200, 0));
	cell.setPadding (01.2f);
	table.addCell (cell);

	cell = new PdfPCell (new Paragraph (monthname+"-"+year,FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD)));
	cell.setColspan (2);
	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
	cell.setBackgroundColor (new Color (255, 200, 0));
	cell.setPadding (01.2f);
	table.addCell (cell);
}

if(v.get(8).toString()!="0")
	document.add(new Paragraph("                                                                                       WEC Generation",
        FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD)));;

List tranListData = new ArrayList();
tranListData = (List) secutils.getWECData(ebid, rdate, type);
if(type.equalsIgnoreCase("D")){
	tranListData = (List)CustomerUtility.getOneEBWECWiseInfoForOneDay(ebid, rdate);
}
else if(type.equalsIgnoreCase("M")){
	tranListData = (List)CustomerUtility.getOneEBWECWiseTotalForOneMonth(ebid, monthReceived, yearReceived);
}
else if(type.equalsIgnoreCase("Y")){
	tranListData = (List) CustomerUtility.getOneEBWECWiseTotalForOneFiscalYear(ebid, fiscalYear);
}
else{
	tranListData = (List) secutils.getWECData(ebid, rdate, type);
}
int wecsize = tranListData.size();
if (wecsize > 0) {
	cell = new PdfPCell (new Paragraph ("WEC Name",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
	cell.setColspan (1);
	cell.setBackgroundColor (new Color (128, 200, 128));
	cell.setPadding (0.9f);
	table.addCell (cell);

	cell = new PdfPCell (new Paragraph ("Generation",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
	cell.setBackgroundColor (new Color (128, 200, 128));
	cell.setPadding (0.9f);
	table.addCell (cell);

	cell = new PdfPCell (new Paragraph ("Operating Hours",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));	
	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
	cell.setBackgroundColor (new Color (128, 200, 128));
	cell.setPadding (0.9f);
	table.addCell (cell);
	
	cell = new PdfPCell (new Paragraph ("Lull Hours",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));	
	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
	cell.setBackgroundColor (new Color (128, 200, 128));
	cell.setPadding (0.9f);
	table.addCell (cell);
	
	cell = new PdfPCell (new Paragraph ("Capacity Factor(%)",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));	
	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
	cell.setBackgroundColor (new Color (128, 200, 128));
	cell.setPadding (0.9f);
	table.addCell (cell);
	
	cell = new PdfPCell (new Paragraph ("Machine Avail(%)",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));	
	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
	cell.setBackgroundColor (new Color (128, 200, 128));
	cell.setPadding (0.9f);
	table.addCell (cell);
	diff = -1;
	if(diff<0){ 
		cell = new PdfPCell (new Paragraph ("Grid Availbility(%)",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
		cell.setHorizontalAlignment (Element.ALIGN_CENTER);
		cell.setColspan (2);
		cell.setBackgroundColor (new Color (128, 200, 128));
		cell.setPadding (0.5f);
		table.addCell (cell);
		
	}else{ 
			cell = new PdfPCell (new Paragraph ("Grid Availbility Internal(%)",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
			//cell.setHorizontalAlignment (Element.ALIGN_CENTER);
			cell.setBackgroundColor (new Color (128, 200, 128));
			cell.setPadding (0.5f);
			table.addCell (cell);
			
			cell = new PdfPCell (new Paragraph ("Grid Availbility External(%)",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
			//cell.setHorizontalAlignment (Element.ALIGN_CENTER);
			cell.setBackgroundColor (new Color (128, 200, 128));
			cell.setPadding (0.5f);
			table.addCell (cell);
	}	
	for (int j = 0; j < tranListData.size(); j++) {
		Vector vdata = new Vector();
		vdata = (Vector) tranListData.get(j);
		String name = (String) vdata.get(0);
		
		if (!vdata.get(9).toString().equals("NIL")) {
			if (!RemarksWEC.equals(".")) {
				RemarksWEC = RemarksWEC + (String) vdata.get(9);

			}
		}
		
		cell = new PdfPCell (new Paragraph (vdata.get(0).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));
		cell.setHorizontalAlignment (Element.ALIGN_CENTER);
		//cell.setColspan (1);
		cell.setPadding (01.2f);
		table.addCell (cell);

		cell = new PdfPCell (new Paragraph (vdata.get(2).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));	
		cell.setHorizontalAlignment (Element.ALIGN_CENTER);		
		cell.setPadding (01.2f);
		table.addCell (cell);

		cell = new PdfPCell (new Paragraph (vdata.get(3).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));		
		cell.setHorizontalAlignment (Element.ALIGN_CENTER);		
		cell.setPadding (01.2f);
		table.addCell (cell);
		
		if (!vdata.get(10).toString().equals("1")) {
			
			cell = new PdfPCell (new Paragraph (vdata.get(4).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));			
			cell.setHorizontalAlignment (Element.ALIGN_CENTER);			
			cell.setPadding (01.2f);
			table.addCell (cell);
		
			cell = new PdfPCell (new Paragraph (vdata.get(7).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));			
			cell.setHorizontalAlignment (Element.ALIGN_CENTER);			
			cell.setPadding (01.2f);
			table.addCell (cell);
			
			if(diff<0){ 
				 	cell = new PdfPCell (new Paragraph (vdata.get(5).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));				
					cell.setHorizontalAlignment (Element.ALIGN_CENTER);					
					cell.setPadding (01.2f);
					table.addCell (cell);					
					
					}else{ 
						
						cell = new PdfPCell (new Paragraph (vdata.get(11).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));						
						cell.setHorizontalAlignment (Element.ALIGN_CENTER);						
						cell.setPadding (01.2f);
						table.addCell (cell);

						cell = new PdfPCell (new Paragraph (vdata.get(12).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));						
						cell.setHorizontalAlignment (Element.ALIGN_CENTER);						
						cell.setPadding (01.2f);
						table.addCell (cell);
						
					}
						
						cell = new PdfPCell (new Paragraph (vdata.get(6).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));
						cell.setColspan (2);
						cell.setHorizontalAlignment (Element.ALIGN_CENTER);						
						cell.setPadding (01.2f);
						table.addCell (cell);
						
						//cell = new PdfPCell (new Paragraph (vdata.get(13).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));						
						//cell.setHorizontalAlignment (Element.ALIGN_CENTER);						
						//cell.setPadding (01.2f);
						//table.addCell (cell);
						
						//cell = new PdfPCell (new Paragraph (vdata.get(14).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));						
						//cell.setHorizontalAlignment (Element.ALIGN_CENTER);						
						//cell.setPadding (01.2f);
						//table.addCell (cell);						
						
						//cell = new PdfPCell (new Paragraph (vdata.get(6).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));						
						//cell.setHorizontalAlignment (Element.ALIGN_CENTER);						
						//cell.setPadding (01.2f);
						//table.addCell (cell);
					//}
			
		}
		else
		{
			
				cell = new PdfPCell (new Paragraph ("WEC Is In Stabilization Phase",FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));
				if(diff<0){
					cell.setColspan (4);	
				}else
				{
					cell.setColspan (4);
				}
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				cell.setPadding (01.2f);
				table.addCell (cell);
			
		}
	    
	}
	tranListData.clear();
	if (wecsize > 1) {
		if(type.equalsIgnoreCase("D")){
			tranListData = (List) CustomerUtility.getOneEBTotalForOneday(ebid, rdate);
		}
		else if(type.equalsIgnoreCase("M")){
			tranListData = (List) CustomerUtility.getOneEBTotalForOneMonth(ebid, monthReceived, yearReceived);
		}
		else if(type.equalsIgnoreCase("Y")){
			tranListData = (List) CustomerUtility.getOneEBTotalForOneFiscalYear(ebid, fiscalYear);
		}
		else{
			tranListData = (List) secutils.getEBWiseTotal(ebid,rdate, type);
		}
		for (int j = 0; j < tranListData.size(); j++) {
				Vector vdata = new Vector();
				vdata = (Vector) tranListData.get(j);
				
				cell = new PdfPCell (new Paragraph ("Total:"+vdata.get(0).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				cell.setPadding (01.2f);
				table.addCell (cell);
				
				cell = new PdfPCell (new Paragraph (vdata.get(1).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				cell.setPadding (01.2f);
				table.addCell (cell);
				
				cell = new PdfPCell (new Paragraph (vdata.get(2).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				cell.setPadding (01.2f);
				table.addCell (cell);
				
				if(vdata.get(9).toString().equals("0")){
					if(diff<0){
				
				cell = new PdfPCell (new Paragraph (vdata.get(3).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				cell.setPadding (01.2f);
				table.addCell (cell);
				
				cell = new PdfPCell (new Paragraph (vdata.get(6).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				cell.setPadding (01.2f);
				table.addCell (cell);
				
				cell = new PdfPCell (new Paragraph (vdata.get(4).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
				//cell.setColspan (2);
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				cell.setPadding (01.2f);
				table.addCell (cell);				
				}
				else
				{
					cell = new PdfPCell (new Paragraph (vdata.get(3).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
					cell.setHorizontalAlignment (Element.ALIGN_CENTER);
					cell.setPadding (01.2f);
					table.addCell (cell);
					
					cell = new PdfPCell (new Paragraph (vdata.get(6).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
					cell.setHorizontalAlignment (Element.ALIGN_CENTER);
					cell.setPadding (01.2f);
					table.addCell (cell);
					
					cell = new PdfPCell (new Paragraph (vdata.get(7).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
					cell.setHorizontalAlignment (Element.ALIGN_CENTER);
					cell.setPadding (01.2f);
					table.addCell (cell);
					
					cell = new PdfPCell (new Paragraph (vdata.get(8).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));					
					cell.setHorizontalAlignment (Element.ALIGN_CENTER);
					cell.setPadding (01.2f);
					table.addCell (cell);
				}
					cell = new PdfPCell (new Paragraph (vdata.get(5).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
					cell.setColspan (2);
					cell.setHorizontalAlignment (Element.ALIGN_CENTER);					
					cell.setPadding (01.2f);
					table.addCell (cell);					
			}	
			else{
				cell = new PdfPCell (new Paragraph ("WEC is in Stabilization phase..",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
				cell.setColspan (4);
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);				
				cell.setPadding (01.2f);
				table.addCell (cell);				

			}				
		}		
	}
	
	document.add(table);
	tranListData.clear();
	
	tranListData = (List) secutils.getEBData(ebid, rdate, type);
	
	if (tranListData.size() > 0) {		
			document.add(new Paragraph("                                                                                       EB Generation",
		               FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD)));;
		       
				table=new PdfPTable(8);
				table.setWidthPercentage(100f);
				cell = new PdfPCell (new Paragraph ("Description",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
				//cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				cell.setBackgroundColor (new Color (128, 200, 128));
				cell.setPadding (0.5f);
				table.addCell (cell);

				cell = new PdfPCell (new Paragraph ("KWH Export",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
				cell.setColspan (2);
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				cell.setBackgroundColor (new Color (128, 200, 128));
				cell.setPadding (0.5f);
				table.addCell (cell);

				cell = new PdfPCell (new Paragraph ("KWH Import",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
				cell.setColspan (2);
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				cell.setBackgroundColor (new Color (128, 200, 128));
				cell.setPadding (0.5f);
				table.addCell (cell);	
				
				cell = new PdfPCell (new Paragraph ("Net KWH",FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
				cell.setColspan (3);
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				cell.setBackgroundColor (new Color (128, 200, 128));
				cell.setPadding (0.5f);
				table.addCell (cell);	
				}
				for (int j = 0; j < tranListData.size(); j++) {
					Vector vdata = new Vector();
					vdata = (Vector) tranListData.get(j);
			
				if (!vdata.get(4).toString().equals("NIL"))					
						if (!Remarks.equals(".")) {
							{
								Remarks = (String) vdata.get(4);
			
							}
						}
				cell = new PdfPCell (new Paragraph (vdata.get(0).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				
				cell.setPadding (01.2f);
				table.addCell (cell);
		
				cell = new PdfPCell (new Paragraph (vdata.get(1).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));
				cell.setColspan (2);
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				
				cell.setPadding (01.2f);
				table.addCell (cell);
		
				cell = new PdfPCell (new Paragraph (vdata.get(2).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));
				cell.setColspan (2);
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				
				cell.setPadding (01.2f);
				table.addCell (cell);	
				
				cell = new PdfPCell (new Paragraph (vdata.get(3).toString(),FontFactory.getFont(FontFactory.COURIER, 8, Font.NORMAL)));
				cell.setColspan (3);
				cell.setHorizontalAlignment (Element.ALIGN_CENTER);
				
				cell.setPadding (01.2f);
				table.addCell (cell);
				
				document.add(table);	
		}
		
}
else {
	cell = new PdfPCell (new Paragraph ("Data Not Available",FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD)));
	cell.setColspan (3);
	cell.setHorizontalAlignment (Element.ALIGN_CENTER);
	cell.setBackgroundColor (new Color (255, 200, 0));
	cell.setPadding (01.2f);
	table.addCell (cell);	
}
if(v.get(8).toString()!="0")
	document.add(new Paragraph("                                                                                         Remarks",
        FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD)));;
	

		if(Remarks.equals("")){ 
            Remarks="";
		}
		if(Remarks.equals(".")){ 
			Remarks="";
		}
		if(RemarksWEC.equals("")){ 
			RemarksWEC="";
		}
		
		table=new PdfPTable(8);
		table.setWidthPercentage(100f);
		cell = new PdfPCell (new Paragraph (Remarks,FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
		cell.setColspan (8);
		cell.setHorizontalAlignment (Element.ALIGN_CENTER);
		
		cell.setPadding (01.2f);
		table.addCell (cell);

		cell = new PdfPCell (new Paragraph (RemarksWEC,FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD)));
		cell.setColspan (8);
		cell.setHorizontalAlignment (Element.ALIGN_CENTER);
		
		cell.setPadding (01.2f);
		table.addCell (cell);
		document.add(table);
}
	tranList.clear();
	document.close();
%>

